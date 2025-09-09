package com.aliyun.player.nativeclass;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Surface;
import com.aliyun.aio_stat.AioStat;
import com.aliyun.player.FilterConfig;
import com.aliyun.player.IPlayer;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.bean.InfoCode;
import com.aliyun.player.videoview.AliDisplayView;
import com.aliyun.player.videoview.a.a;
import com.aliyun.utils.DeviceInfoUtils;
import com.aliyun.utils.f;
import com.cicada.player.utils.FrameInfo;
import com.cicada.player.utils.Logger;
import com.cicada.player.utils.NativeUsed;
import com.cicada.player.utils.media.DrmCallback;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class NativePlayerBase {
    private static final String TAG = "NativePlayerBase";
    private static final int UPDATE_CURRENT_POSITION = 1000;
    private static final int VIDEO_TYPE_FAIRPLAY = 16;
    private static final int VIDEO_TYPE_HDR10 = 2;
    private static final int VIDEO_TYPE_NONE = 0;
    private static final int VIDEO_TYPE_SDR = 1;
    private static final int VIDEO_TYPE_WIDEVINE_L1 = 4;
    private static final int VIDEO_TYPE_WIDEVINE_L3 = 8;
    private static String libPath;
    private static Context mContext;
    private static IPlayer.ConvertURLCallback sConvertURLCallback;
    private MainHandler mCurrentThreadHandler;
    private long mNativeContext;
    private boolean mSurfaceFromUser = false;
    private boolean mEnableTunnelMode = false;
    private IPlayer.OnRenderFrameCallback mRenderFrameCallback = null;
    private IPlayer.OnPreRenderFrameCallback mPreRenderFrameCallback = null;
    private IPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = null;
    private IPlayer.OnVideoRenderedListener mOnVideoRenderedListener = null;
    private IPlayer.OnInfoListener mOnInfoListener = null;
    private IPlayer.OnTrackReadyListener mOnTrackReadyListener = null;
    private IPlayer.OnSubTrackReadyListener mOnSubTrackReadyListener = null;
    private IPlayer.OnChooseTrackIndexListener mOnChooseTrackIndexListener = null;
    private IPlayer.OnPreparedListener mOnPreparedListener = null;
    private IPlayer.OnCompletionListener mOnCompletionListener = null;
    private IPlayer.OnErrorListener mOnErrorListener = null;
    private IPlayer.OnRenderingStartListener mOnRenderingStartListener = null;
    private IPlayer.OnStreamSwitchedListener mOnStreamSwitchedListener = null;
    private IPlayer.OnTrackChangedListener mOnTrackChangedListener = null;
    private IPlayer.OnSeiDataListener mOnSeiDataListener = null;
    private IPlayer.OnLoadingStatusListener mOnLoadingStatusListener = null;
    private IPlayer.OnAVNotSyncStatusListener mOnAVNotSyncStatusListener = null;
    private IPlayer.OnSeekCompleteListener mOnSeekCompleteListener = null;
    private IPlayer.OnSubtitleDisplayListener mOnSubtitleDisplayListener = null;
    private IPlayer.OnStateChangedListener mOnStateChangedListener = null;
    private IPlayer.OnSnapShotListener mOnSnapShotListener = null;
    private IPlayer.OnReportEventListener mOnEventReportListner = null;
    private DrmCallback mDrmCallback = null;
    private boolean mDirectRender = false;
    private boolean mAllowResetDisplayView = false;
    private int mVideoType = 0;
    private AliDisplayView mAliDisplayView = null;
    private DisplayViewHelper mDisplayViewHelper = null;

    private static class MainHandler extends Handler {
        private WeakReference<NativePlayerBase> playerWeakReference;

        public MainHandler(NativePlayerBase nativePlayerBase, Looper looper) {
            super(looper);
            this.playerWeakReference = new WeakReference<>(nativePlayerBase);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            NativePlayerBase nativePlayerBase = this.playerWeakReference.get();
            if (nativePlayerBase != null) {
                nativePlayerBase.handleMessage(message);
            }
            super.handleMessage(message);
        }
    }

    static {
        f.f();
        mContext = null;
        sConvertURLCallback = null;
    }

    public NativePlayerBase(Context context) {
        mContext = context;
        if (libPath == null) {
            libPath = getUserNativeLibPath(context);
            loadPlugins();
        }
        DeviceInfoUtils.setSDKContext(context);
        if (f.b()) {
            AioStat.init(context);
        }
        this.mCurrentThreadHandler = new MainHandler(this, Looper.getMainLooper());
        construct(context);
    }

    private void construct(Context context) {
        if (f.b()) {
            nConstruct();
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public static String getSdkVersion() {
        return !f.b() ? "" : nGetSdkVersion();
    }

    private static String getUserNativeLibPath(Context context) throws PackageManager.NameNotFoundException {
        String packageName = context.getPackageName();
        String str = "/data/data/" + packageName + "/lib/";
        try {
            str = context.getPackageManager().getPackageInfo(packageName, 0).applicationInfo.dataDir + "/lib/";
        } catch (PackageManager.NameNotFoundException unused) {
        }
        File file = new File(str);
        if (file.exists() && file.listFiles() != null) {
            return str;
        }
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0).applicationInfo.nativeLibraryDir + "/";
        } catch (PackageManager.NameNotFoundException unused2) {
            return str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMessage(Message message) {
        if (message.what != 1000 || this.mOnInfoListener == null) {
            return;
        }
        InfoBean infoBean = new InfoBean();
        infoBean.setCode(InfoCode.CurrentPosition);
        infoBean.setExtraValue(message.arg1);
        this.mOnInfoListener.onInfo(infoBean);
    }

    public static void loadClass() {
    }

    private void loadPlugins() {
        File[] fileArrListFiles;
        if (TextUtils.isEmpty(libPath)) {
            return;
        }
        File file = new File(libPath);
        if (!file.exists() || (fileArrListFiles = file.listFiles()) == null || fileArrListFiles.length == 0) {
            return;
        }
        for (File file2 : fileArrListFiles) {
            String name = file2.getName();
            if (name.contains("cicada_plugin_")) {
                try {
                    System.loadLibrary(name.substring(name.indexOf("lib") + 3, name.lastIndexOf(".so")));
                } catch (Throwable th) {
                    Logger.e("NativePlayerBase", th.getMessage());
                }
            }
        }
    }

    protected static String nConvertURLCallback(String str, String str2) {
        IPlayer.ConvertURLCallback convertURLCallback = sConvertURLCallback;
        if (convertURLCallback != null) {
            return convertURLCallback.convertURL(str, str2);
        }
        return null;
    }

    protected static native String nGetSdkVersion();

    protected static native void nSetBlackType(int i2);

    /* JADX WARN: Removed duplicated region for block: B:20:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0064  */
    @com.cicada.player.utils.NativeUsed
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean nUpdateViewCallback(int r7) {
        /*
            r6 = this;
            java.lang.String r0 = com.aliyun.player.nativeclass.NativePlayerBase.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "nUpdateViewCallback videoType = "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            com.cicada.player.utils.Logger.i(r0, r1)
            r1 = 0
            r6.mDirectRender = r1
            r6.mVideoType = r7
            boolean r2 = r6.mSurfaceFromUser
            if (r2 == 0) goto L29
            boolean r2 = r6.mAllowResetDisplayView
            if (r2 != 0) goto L29
            java.lang.String r7 = "do not update view callback"
        L25:
            com.cicada.player.utils.Logger.e(r0, r7)
            return r1
        L29:
            boolean r2 = r6.mEnableTunnelMode
            r6.mDirectRender = r2
            com.aliyun.player.videoview.AliDisplayView$DisplayViewType r2 = com.aliyun.player.videoview.AliDisplayView.DisplayViewType.Either
            r3 = r7 & 2
            r4 = 1
            r5 = 2
            if (r3 != r5) goto L3a
            com.aliyun.player.videoview.AliDisplayView$DisplayViewType r2 = com.aliyun.player.videoview.AliDisplayView.DisplayViewType.TextureView
        L37:
            r6.mDirectRender = r4
            goto L47
        L3a:
            r3 = r7 & 4
            r5 = 4
            if (r3 == r5) goto L44
            r3 = 8
            r7 = r7 & r3
            if (r7 != r3) goto L47
        L44:
            com.aliyun.player.videoview.AliDisplayView$DisplayViewType r2 = com.aliyun.player.videoview.AliDisplayView.DisplayViewType.SurfaceView
            goto L37
        L47:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r3 = "mDirectRender  = "
            r7.append(r3)
            boolean r3 = r6.mDirectRender
            r7.append(r3)
            java.lang.String r7 = r7.toString()
            com.cicada.player.utils.Logger.i(r0, r7)
            com.aliyun.player.videoview.AliDisplayView r7 = r6.mAliDisplayView
            if (r7 != 0) goto L64
            java.lang.String r7 = "nCreateViewCallback but view is null"
            goto L25
        L64:
            com.aliyun.player.nativeclass.DisplayViewHelper r7 = r6.mDisplayViewHelper
            if (r7 == 0) goto L6d
            boolean r1 = r7.needUpdateView(r2)
            goto L72
        L6d:
            java.lang.String r7 = "nUpdateViewCallback: mDisplayViewHelper is null"
            com.cicada.player.utils.Logger.e(r0, r7)
        L72:
            if (r1 == 0) goto L7e
            com.aliyun.player.videoview.AliDisplayView r7 = r6.mAliDisplayView
            com.aliyun.player.nativeclass.NativePlayerBase$34 r0 = new com.aliyun.player.nativeclass.NativePlayerBase$34
            r0.<init>()
            r7.post(r0)
        L7e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.player.nativeclass.NativePlayerBase.nUpdateViewCallback(int):boolean");
    }

    @NativeUsed
    private void native_onEventReport(Object obj) {
        Map<String, String> map = (Map) obj;
        IPlayer.OnReportEventListener onReportEventListener = this.mOnEventReportListner;
        if (onReportEventListener != null) {
            onReportEventListener.onEventParam(map);
        }
    }

    @NativeUsed
    private boolean native_onPreRenderFrameCallback(Object obj) {
        IPlayer.OnPreRenderFrameCallback onPreRenderFrameCallback = this.mPreRenderFrameCallback;
        if (onPreRenderFrameCallback != null) {
            return onPreRenderFrameCallback.onPreRenderFrame((FrameInfo) obj);
        }
        return false;
    }

    @NativeUsed
    private boolean native_onRenderFrameCallback(Object obj) {
        IPlayer.OnRenderFrameCallback onRenderFrameCallback = this.mRenderFrameCallback;
        if (onRenderFrameCallback != null) {
            return onRenderFrameCallback.onRenderFrame((FrameInfo) obj);
        }
        return false;
    }

    public static void setBlackType(int i2) {
        if (f.b()) {
            nSetBlackType(i2);
        }
    }

    public static void setConvertURLCb(IPlayer.ConvertURLCallback convertURLCallback) {
        sConvertURLCallback = convertURLCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSurfaceInner(Surface surface, boolean z2) {
        if (f.b()) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                Logger.w(TAG, "set surface not at main thread");
            }
            this.mSurfaceFromUser = z2;
            nSetSurface(surface);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trySetProjectionExtraInfo() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        PlayerConfig config = getConfig();
        if (config == null || !config.mEnableProjection) {
            return;
        }
        String propertyString = getPropertyString(1000);
        try {
            if (!new JSONObject(propertyString).getBoolean("projectionLicenseEnable")) {
                Logger.w(TAG, "projection config enabled but license not activated");
                return;
            }
            try {
                Method declaredMethod = Class.forName("com.aliyun.player.aliplayerscreenprojection.AliPlayerScreenProjectionHelper").getDeclaredMethod("setExtraInfo", String.class, String.class);
                declaredMethod.setAccessible(true);
                String userData = getUserData();
                declaredMethod.invoke(null, userData, propertyString);
                Logger.i(TAG, "setExtraInfo to projection sdk success, userData:" + userData);
            } catch (Exception e2) {
                Logger.e(TAG, "setExtraInfo to projection sdk failed, seems projection sdk is not integrated. Error:" + e2.getMessage());
            }
        } catch (JSONException unused) {
            Logger.e(TAG, "Invalid prepared info:" + propertyString);
        }
    }

    public void addExtSubtitle(String str) {
        if (f.b()) {
            nAddExtSubtitle(str);
        }
    }

    public synchronized void clearScreen() {
        if (f.b()) {
            nClearScreen();
        }
    }

    protected void clearScreenIfNeed() {
        DisplayViewHelper displayViewHelper;
        PlayerConfig config = getConfig();
        if (config == null || !config.mClearFrameWhenStop || (displayViewHelper = this.mDisplayViewHelper) == null) {
            return;
        }
        displayViewHelper.clearScreen();
    }

    public synchronized void enableHardwareDecoder(boolean z2) {
        if (f.b()) {
            nEnableHardwareDecoder(z2);
        }
    }

    public synchronized IPlayer.AlphaRenderMode getAlphaRenderMode() {
        if (!f.b()) {
            return IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_NONE;
        }
        int iNGetAlphaRenderMode = nGetAlphaRenderMode();
        IPlayer.AlphaRenderMode alphaRenderMode = IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_NONE;
        if (iNGetAlphaRenderMode == alphaRenderMode.getValue()) {
            return alphaRenderMode;
        }
        IPlayer.AlphaRenderMode alphaRenderMode2 = IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_AT_RIGHT;
        if (iNGetAlphaRenderMode == alphaRenderMode2.getValue()) {
            return alphaRenderMode2;
        }
        IPlayer.AlphaRenderMode alphaRenderMode3 = IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_AT_LEFT;
        if (iNGetAlphaRenderMode == alphaRenderMode3.getValue()) {
            return alphaRenderMode3;
        }
        IPlayer.AlphaRenderMode alphaRenderMode4 = IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_AT_TOP;
        if (iNGetAlphaRenderMode == alphaRenderMode4.getValue()) {
            return alphaRenderMode4;
        }
        IPlayer.AlphaRenderMode alphaRenderMode5 = IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_AT_BOTTOM;
        return iNGetAlphaRenderMode == alphaRenderMode5.getValue() ? alphaRenderMode5 : alphaRenderMode;
    }

    public synchronized long getBufferedPosition() {
        if (!f.b()) {
            return 0L;
        }
        return nGetBufferedPosition();
    }

    public String getCacheFilePath(String str) {
        return nGetCacheFilePath(str);
    }

    public synchronized PlayerConfig getConfig() {
        if (!f.b()) {
            return new PlayerConfig(0);
        }
        Object objNGetConfig = nGetConfig();
        if (objNGetConfig == null) {
            return null;
        }
        return (PlayerConfig) objNGetConfig;
    }

    public synchronized long getCurrentPosition() {
        if (!f.b()) {
            return 0L;
        }
        return nGetCurrentPosition();
    }

    public synchronized TrackInfo getCurrentTrackInfo(int i2) {
        if (f.b()) {
            return (TrackInfo) nGetCurrentStreamInfo(i2);
        }
        return new TrackInfo();
    }

    public synchronized long getDuration() {
        if (!f.b()) {
            return 0L;
        }
        return nGetDuration();
    }

    public synchronized IPlayer.MirrorMode getMirrorMode() {
        if (!f.b()) {
            return IPlayer.MirrorMode.MIRROR_MODE_NONE;
        }
        int iNGetMirrorMode = nGetMirrorMode();
        IPlayer.MirrorMode mirrorMode = IPlayer.MirrorMode.MIRROR_MODE_NONE;
        if (iNGetMirrorMode == mirrorMode.getValue()) {
            return mirrorMode;
        }
        IPlayer.MirrorMode mirrorMode2 = IPlayer.MirrorMode.MIRROR_MODE_HORIZONTAL;
        if (iNGetMirrorMode == mirrorMode2.getValue()) {
            return mirrorMode2;
        }
        IPlayer.MirrorMode mirrorMode3 = IPlayer.MirrorMode.MIRROR_MODE_VERTICAL;
        return iNGetMirrorMode == mirrorMode3.getValue() ? mirrorMode3 : mirrorMode;
    }

    public long getNativeContext() {
        return this.mNativeContext;
    }

    public synchronized Object getOption(IPlayer.Option option) {
        if (!f.b()) {
            return null;
        }
        String strNGetOption = nGetOption(option.getValue());
        if (strNGetOption == null) {
            return null;
        }
        if (option != IPlayer.Option.RenderFPS && option != IPlayer.Option.DownloadBitrate && option != IPlayer.Option.VideoBitrate && option != IPlayer.Option.AudioBitrate) {
            return strNGetOption;
        }
        try {
            return Float.valueOf(strNGetOption);
        } catch (Exception unused) {
            return Float.valueOf("0");
        }
    }

    public synchronized long getPlayedDuration() {
        if (!f.b()) {
            return 0L;
        }
        return nGetPlayedDuration();
    }

    public String getPlayerName() {
        return !f.b() ? "" : nGetPlayerName();
    }

    public synchronized int getPlayerStatus() {
        if (!f.b()) {
            return 0;
        }
        return nGetPlayerStatus();
    }

    public synchronized String getPropertyString(int i2) {
        if (!f.b()) {
            return "";
        }
        return nGetPropertyString(i2);
    }

    public synchronized IPlayer.RotateMode getRotateMode() {
        if (!f.b()) {
            return IPlayer.RotateMode.ROTATE_0;
        }
        int iNGetRotateMode = nGetRotateMode();
        IPlayer.RotateMode rotateMode = IPlayer.RotateMode.ROTATE_0;
        if (iNGetRotateMode == rotateMode.getValue()) {
            return rotateMode;
        }
        IPlayer.RotateMode rotateMode2 = IPlayer.RotateMode.ROTATE_90;
        if (iNGetRotateMode == rotateMode2.getValue()) {
            return rotateMode2;
        }
        IPlayer.RotateMode rotateMode3 = IPlayer.RotateMode.ROTATE_180;
        if (iNGetRotateMode == rotateMode3.getValue()) {
            return rotateMode3;
        }
        IPlayer.RotateMode rotateMode4 = IPlayer.RotateMode.ROTATE_270;
        return iNGetRotateMode == rotateMode4.getValue() ? rotateMode4 : rotateMode;
    }

    public synchronized IPlayer.ScaleMode getScaleMode() {
        if (!f.b()) {
            return IPlayer.ScaleMode.SCALE_TO_FILL;
        }
        int iNGetScaleMode = nGetScaleMode();
        IPlayer.ScaleMode scaleMode = IPlayer.ScaleMode.SCALE_TO_FILL;
        if (iNGetScaleMode == scaleMode.getValue()) {
            return scaleMode;
        }
        IPlayer.ScaleMode scaleMode2 = IPlayer.ScaleMode.SCALE_ASPECT_FIT;
        if (iNGetScaleMode == scaleMode2.getValue()) {
            return scaleMode2;
        }
        IPlayer.ScaleMode scaleMode3 = IPlayer.ScaleMode.SCALE_ASPECT_FILL;
        return iNGetScaleMode == scaleMode3.getValue() ? scaleMode3 : scaleMode;
    }

    public synchronized float getSpeed() {
        if (!f.b()) {
            return 0.0f;
        }
        return nGetSpeed();
    }

    public synchronized String getUserData() {
        if (!f.b()) {
            return "";
        }
        return nGetUserData();
    }

    public synchronized int getVideoHeight() {
        if (!f.b()) {
            return 0;
        }
        return nGetVideoHeight();
    }

    public synchronized float getVideoRotation() {
        if (!f.b()) {
            return 0.0f;
        }
        return nGetVideoRotation();
    }

    public synchronized int getVideoWidth() {
        if (!f.b()) {
            return 0;
        }
        return nGetVideoWidth();
    }

    public synchronized float getVolume() {
        if (!f.b()) {
            return 0.0f;
        }
        return nGetVolume();
    }

    public int invokeComponent(String str) {
        if (f.b()) {
            return nInvokeComponent(str);
        }
        return 0;
    }

    public synchronized boolean isAutoPlay() {
        if (!f.b()) {
            return false;
        }
        return nIsAutoPlay();
    }

    public synchronized boolean isLoop() {
        if (!f.b()) {
            return false;
        }
        return nIsLoop();
    }

    public synchronized boolean isMuted() {
        if (!f.b()) {
            return false;
        }
        return nIsMuted();
    }

    protected native void nAddExtSubtitle(String str);

    protected native void nClearScreen();

    protected native void nConstruct();

    protected native void nEnableFrameCb(boolean z2);

    protected native void nEnableHardwareDecoder(boolean z2);

    protected native void nEnablePreFrameCb(boolean z2);

    protected native void nEnableVideoRenderedCallback(boolean z2);

    protected native int nGetAlphaRenderMode();

    protected native long nGetBufferedPosition();

    protected native String nGetCacheFilePath(String str);

    protected native String nGetCacheFilePath(String str, String str2, String str3, int i2);

    protected native Object nGetConfig();

    protected native long nGetCurrentPosition();

    protected native Object nGetCurrentStreamInfo(int i2);

    protected native long nGetDuration();

    protected native int nGetMirrorMode();

    protected native String nGetOption(String str);

    protected native long nGetPlayedDuration();

    protected native String nGetPlayerName();

    protected native int nGetPlayerStatus();

    protected native String nGetPropertyString(int i2);

    protected native int nGetRotateMode();

    protected native int nGetScaleMode();

    protected native float nGetSpeed();

    protected native String nGetUserData();

    protected native int nGetVideoHeight();

    protected native int nGetVideoRotation();

    protected native int nGetVideoWidth();

    protected native float nGetVolume();

    protected native int nInvokeComponent(String str);

    protected native boolean nIsAutoPlay();

    protected native boolean nIsLoop();

    protected native boolean nIsMuted();

    protected native void nPause();

    protected native void nPrepare();

    protected native void nRelease();

    protected native void nReload();

    protected native void nSeekTo(long j2, int i2);

    protected native void nSelectExtSubtitle(int i2, boolean z2);

    protected native void nSelectTrack(int i2);

    protected native void nSelectTrackA(int i2, boolean z2);

    protected native void nSendCustomEvent(String str);

    protected native void nSetAlphaRenderMode(int i2);

    protected native void nSetAutoPlay(boolean z2);

    protected native void nSetCacheConfig(Object obj);

    protected native void nSetConfig(Object obj);

    protected native void nSetConnectivityManager(Object obj);

    protected native void nSetDefaultBandWidth(int i2);

    protected native void nSetDefaultResolution(int i2);

    protected native void nSetFastStart(boolean z2);

    protected native void nSetFilterConfig(String str);

    protected native void nSetFilterInvalid(String str, boolean z2);

    protected native void nSetFrameCbConfig(boolean z2, boolean z3);

    protected native void nSetGlobalTime(String str);

    protected native void nSetIPResolveType(int i2);

    protected native void nSetLoop(boolean z2);

    protected native void nSetMaxAccurateSeekDelta(int i2);

    protected native void nSetMirrorMode(int i2);

    protected native void nSetMute(boolean z2);

    protected native void nSetOption(String str, String str2);

    protected native void nSetOutputAudioChannel(int i2);

    protected native void nSetPlayerScene(int i2);

    protected native void nSetPreferPlayerName(String str);

    protected native void nSetRotateMode(int i2);

    protected native void nSetScaleMode(int i2);

    protected native void nSetSpeed(float f2);

    protected native void nSetStartTime(long j2, int i2);

    protected native void nSetStreamDelayTime(int i2, int i3);

    protected native void nSetSurface(Surface surface);

    protected native void nSetTraceID(String str);

    protected native void nSetUserData(String str);

    protected native void nSetVideoBackgroundColor(int i2);

    protected native void nSetVideoTag(int[] iArr);

    protected native void nSetVolume(float f2);

    protected native void nSnapShot();

    protected native void nStart();

    protected native void nStop();

    protected native void nSurfaceChanged();

    protected native void nSwitchStream(String str);

    protected native void nUpdateFilterConfig(String str, String str2);

    protected void onAVNotSyncEnd() {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.23
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnAVNotSyncStatusListener != null) {
                    NativePlayerBase.this.mOnAVNotSyncStatusListener.onAVNotSyncEnd();
                }
            }
        });
    }

    protected void onAVNotSyncStart(final int i2) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.22
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnAVNotSyncStatusListener != null) {
                    NativePlayerBase.this.mOnAVNotSyncStatusListener.onAVNotSyncStart(i2);
                }
            }
        });
    }

    protected void onAutoPlayStart() {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.6
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnInfoListener != null) {
                    InfoBean infoBean = new InfoBean();
                    infoBean.setCode(InfoCode.AutoPlayStart);
                    NativePlayerBase.this.mOnInfoListener.onInfo(infoBean);
                }
            }
        });
    }

    protected void onBufferedPositionUpdate(final long j2) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.19
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnInfoListener != null) {
                    InfoBean infoBean = new InfoBean();
                    infoBean.setCode(InfoCode.BufferedPosition);
                    infoBean.setExtraValue(j2);
                    NativePlayerBase.this.mOnInfoListener.onInfo(infoBean);
                }
            }
        });
    }

    protected void onCaptureScreen(final int i2, final int i3, byte[] bArr) {
        final Bitmap bitmapCreateBitmap = null;
        if (i2 > 0 && i3 > 0 && bArr != null && bArr.length > 0) {
            try {
                bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
                bitmapCreateBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bArr));
            } catch (Exception unused) {
            }
        }
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.33
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnSnapShotListener != null) {
                    NativePlayerBase.this.mOnSnapShotListener.onSnapShot(bitmapCreateBitmap, i2, i3);
                }
            }
        });
    }

    protected int onChooseTrackIndex(TrackInfo[] trackInfoArr) {
        IPlayer.OnChooseTrackIndexListener onChooseTrackIndexListener = this.mOnChooseTrackIndexListener;
        if (onChooseTrackIndexListener != null) {
            return onChooseTrackIndexListener.onChooseTrackIndex(trackInfoArr);
        }
        return -1;
    }

    protected void onCircleStart() {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.5
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnInfoListener != null) {
                    InfoBean infoBean = new InfoBean();
                    infoBean.setCode(InfoCode.LoopingStart);
                    NativePlayerBase.this.mOnInfoListener.onInfo(infoBean);
                }
            }
        });
    }

    protected void onCompletion() {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.4
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnCompletionListener != null) {
                    NativePlayerBase.this.mOnCompletionListener.onCompletion();
                }
            }
        });
    }

    protected void onCurrentDownloadSpeed(final long j2) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.24
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnInfoListener != null) {
                    InfoBean infoBean = new InfoBean();
                    infoBean.setCode(InfoCode.CurrentDownloadSpeed);
                    infoBean.setExtraValue(j2);
                    NativePlayerBase.this.mOnInfoListener.onInfo(infoBean);
                }
            }
        });
    }

    protected void onCurrentPositionUpdate(long j2) {
        this.mCurrentThreadHandler.sendMessage(this.mCurrentThreadHandler.obtainMessage(1000, (int) j2, 0));
    }

    protected void onError(int i2, final String str, final String str2) {
        final ErrorCode errorCode = ErrorCode.ERROR_UNKNOWN;
        ErrorCode[] errorCodeArrValues = ErrorCode.values();
        int length = errorCodeArrValues.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                break;
            }
            ErrorCode errorCode2 = errorCodeArrValues[i3];
            if (errorCode2.getValue() == i2) {
                errorCode = errorCode2;
                break;
            }
            i3++;
        }
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.7
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnErrorListener != null) {
                    ErrorInfo errorInfo = new ErrorInfo();
                    errorInfo.setCode(errorCode);
                    errorInfo.setMsg(str);
                    errorInfo.setExtra(str2);
                    NativePlayerBase.this.mOnErrorListener.onError(errorInfo);
                }
            }
        });
    }

    protected void onEvent(int i2, final String str, Object obj) {
        final InfoCode infoCode = InfoCode.Unknown;
        InfoCode[] infoCodeArrValues = InfoCode.values();
        int length = infoCodeArrValues.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                break;
            }
            InfoCode infoCode2 = infoCodeArrValues[i3];
            if (infoCode2.getValue() == i2) {
                infoCode = infoCode2;
                break;
            }
            i3++;
        }
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.8
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnInfoListener != null) {
                    InfoBean infoBean = new InfoBean();
                    infoBean.setCode(infoCode);
                    infoBean.setExtraMsg(str);
                    NativePlayerBase.this.mOnInfoListener.onInfo(infoBean);
                }
            }
        });
    }

    protected void onFirstFrameShow() {
        DisplayViewHelper displayViewHelper;
        if (this.mAliDisplayView != null && (displayViewHelper = this.mDisplayViewHelper) != null) {
            displayViewHelper.firstFrameRender(getVideoWidth() > 0);
        }
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.9
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnRenderingStartListener != null) {
                    NativePlayerBase.this.mOnRenderingStartListener.onRenderingStart();
                }
            }
        });
    }

    protected void onHideSubtitle(final int i2, final long j2) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.31
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnSubtitleDisplayListener != null) {
                    NativePlayerBase.this.mOnSubtitleDisplayListener.onSubtitleHide(i2, j2);
                }
            }
        });
    }

    protected void onLoadingEnd() {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.27
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnLoadingStatusListener != null) {
                    NativePlayerBase.this.mOnLoadingStatusListener.onLoadingEnd();
                }
            }
        });
    }

    protected void onLoadingProgress(final float f2) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.21
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnLoadingStatusListener != null) {
                    NativePlayerBase.this.mOnLoadingStatusListener.onLoadingProgress((int) f2, 0.0f);
                }
            }
        });
    }

    protected void onLoadingStart() {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.20
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnLoadingStatusListener != null) {
                    NativePlayerBase.this.mOnLoadingStatusListener.onLoadingBegin();
                }
            }
        });
    }

    protected void onLocalCacheLoad(final long j2) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.26
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnInfoListener != null) {
                    InfoBean infoBean = new InfoBean();
                    infoBean.setCode(InfoCode.LocalCacheLoaded);
                    infoBean.setExtraValue(j2);
                    NativePlayerBase.this.mOnInfoListener.onInfo(infoBean);
                }
            }
        });
    }

    protected void onPrepared() {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.3
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                NativePlayerBase.this.trySetProjectionExtraInfo();
                if (NativePlayerBase.this.mOnPreparedListener != null) {
                    NativePlayerBase.this.mOnPreparedListener.onPrepared();
                }
            }
        });
    }

    protected void onSeekEnd() {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.28
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnSeekCompleteListener != null) {
                    NativePlayerBase.this.mOnSeekCompleteListener.onSeekComplete();
                }
            }
        });
    }

    protected void onSeiDataCallback(final int i2, final byte[] bArr, final byte[] bArr2) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.16
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnSeiDataListener != null) {
                    NativePlayerBase.this.mOnSeiDataListener.onSeiData(i2, bArr, bArr2);
                }
            }
        });
    }

    protected void onShowSubtitle(final int i2, final long j2, final String str, Object obj) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.29
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnSubtitleDisplayListener != null) {
                    NativePlayerBase.this.mOnSubtitleDisplayListener.onSubtitleShow(i2, j2, str);
                }
            }
        });
    }

    public void onStatusChanged(final int i2, int i3) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.18
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnStateChangedListener != null) {
                    NativePlayerBase.this.mOnStateChangedListener.onStateChanged(i2);
                }
            }
        });
    }

    protected void onStreamInfoGet(final MediaInfo mediaInfo) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.12
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnTrackReadyListener != null) {
                    NativePlayerBase.this.mOnTrackReadyListener.onTrackReady(mediaInfo);
                }
            }
        });
    }

    protected void onSubStreamInfoGet(final MediaInfo mediaInfo) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.13
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnSubTrackReadyListener != null) {
                    NativePlayerBase.this.mOnSubTrackReadyListener.onSubTrackReady(mediaInfo);
                }
            }
        });
    }

    protected void onSubtitleExtAdded(final int i2, final String str) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.30
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnSubtitleDisplayListener != null) {
                    NativePlayerBase.this.mOnSubtitleDisplayListener.onSubtitleExtAdded(i2, str);
                }
            }
        });
    }

    protected void onSubtitleHeader(final int i2, final String str) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.32
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnSubtitleDisplayListener != null) {
                    NativePlayerBase.this.mOnSubtitleDisplayListener.onSubtitleHeader(i2, str);
                }
            }
        });
    }

    protected void onSwitchStreamFail(final TrackInfo trackInfo, final int i2, final String str) {
        final ErrorCode errorCode;
        ErrorCode errorCode2 = ErrorCode.ERROR_UNKNOWN;
        ErrorCode[] errorCodeArrValues = ErrorCode.values();
        int length = errorCodeArrValues.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                errorCode = errorCode2;
                break;
            }
            ErrorCode errorCode3 = errorCodeArrValues[i3];
            if (errorCode3.getValue() == i2) {
                errorCode = errorCode3;
                break;
            }
            i3++;
        }
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.17
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnTrackChangedListener != null) {
                    ErrorInfo errorInfo = new ErrorInfo();
                    errorInfo.setCode(errorCode);
                    errorInfo.setMsg(i2 + ":" + str);
                    NativePlayerBase.this.mOnTrackChangedListener.onChangedFail(trackInfo, errorInfo);
                }
            }
        });
    }

    protected void onSwitchStreamSuccess(final TrackInfo trackInfo) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.15
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                NativePlayerBase.this.trySetProjectionExtraInfo();
                if (NativePlayerBase.this.mOnTrackChangedListener != null) {
                    NativePlayerBase.this.mOnTrackChangedListener.onChangedSuccess(trackInfo);
                }
            }
        });
    }

    protected void onSwitchStreamUrlResult(final String str, final int i2, final String str2) {
        final ErrorCode errorCode;
        ErrorCode errorCode2 = ErrorCode.ERROR_UNKNOWN;
        ErrorCode[] errorCodeArrValues = ErrorCode.values();
        int length = errorCodeArrValues.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                errorCode = errorCode2;
                break;
            }
            ErrorCode errorCode3 = errorCodeArrValues[i3];
            if (errorCode3.getValue() == i2) {
                errorCode = errorCode3;
                break;
            }
            i3++;
        }
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.14
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnStreamSwitchedListener != null) {
                    if (i2 == 0) {
                        NativePlayerBase.this.mOnStreamSwitchedListener.onSwitchedSuccess(str);
                        return;
                    }
                    ErrorInfo errorInfo = new ErrorInfo();
                    errorInfo.setCode(errorCode);
                    errorInfo.setMsg(i2 + ":" + str2);
                    NativePlayerBase.this.mOnStreamSwitchedListener.onSwitchedFail(str, errorInfo);
                }
            }
        });
    }

    protected void onUtcTimeUpdate(final long j2) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.25
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnInfoListener != null) {
                    InfoBean infoBean = new InfoBean();
                    infoBean.setCode(InfoCode.UtcTime);
                    infoBean.setExtraValue(j2);
                    NativePlayerBase.this.mOnInfoListener.onInfo(infoBean);
                }
            }
        });
    }

    protected void onVideoRendered(final long j2, final long j3) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.11
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnVideoRenderedListener != null) {
                    NativePlayerBase.this.mOnVideoRenderedListener.onVideoRendered(j2, j3);
                }
            }
        });
    }

    protected void onVideoSizeChanged(final int i2, final int i3) {
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.10
            @Override // java.lang.Runnable
            public void run() {
                if (NativePlayerBase.this.mOnVideoSizeChangedListener != null) {
                    NativePlayerBase.this.mOnVideoSizeChangedListener.onVideoSizeChanged(i2, i3);
                }
            }
        });
    }

    public synchronized void pause() {
        if (f.b()) {
            nPause();
        }
    }

    public synchronized void prepare() {
        if (f.b()) {
            nPrepare();
        }
    }

    public synchronized void release() {
        if (f.b()) {
            nRelease();
            mContext = null;
        }
    }

    public synchronized void reload() {
        if (f.b()) {
            nReload();
        }
    }

    @NativeUsed
    protected byte[] requestKey(String str, byte[] bArr) {
        DrmCallback drmCallback = this.mDrmCallback;
        if (drmCallback == null) {
            return null;
        }
        return drmCallback.requestKey(str, bArr);
    }

    @NativeUsed
    protected byte[] requestProvision(String str, byte[] bArr) {
        DrmCallback drmCallback = this.mDrmCallback;
        if (drmCallback == null) {
            return null;
        }
        return drmCallback.requestProvision(str, bArr);
    }

    public synchronized void seekTo(long j2) {
        if (f.b()) {
            this.mCurrentThreadHandler.removeMessages(1000);
            nSeekTo(j2, 16);
        }
    }

    public void selectExtSubtitle(int i2, boolean z2) {
        if (f.b()) {
            nSelectExtSubtitle(i2, z2);
        }
    }

    public synchronized void selectTrack(int i2) {
        if (f.b()) {
            nSelectTrack(i2);
        }
    }

    public void sendCustomEvent(String str) {
        if (f.b()) {
            nSendCustomEvent(str);
        }
    }

    public synchronized void setAlphaRenderMode(IPlayer.AlphaRenderMode alphaRenderMode) {
        if (f.b()) {
            nSetAlphaRenderMode(alphaRenderMode.getValue());
        }
    }

    public synchronized void setAutoPlay(boolean z2) {
        if (f.b()) {
            nSetAutoPlay(z2);
        }
    }

    public synchronized void setCacheConfig(CacheConfig cacheConfig) {
        if (f.b()) {
            nSetCacheConfig(cacheConfig);
        }
    }

    public synchronized void setConfig(PlayerConfig playerConfig) {
        if (f.b()) {
            this.mEnableTunnelMode = playerConfig.mEnableVideoTunnelRender;
            nSetConfig(playerConfig);
        }
    }

    public synchronized void setDefaultBandWidth(int i2) {
        if (f.b()) {
            nSetDefaultBandWidth(i2);
        }
    }

    public synchronized void setDefaultResolution(int i2) {
        if (f.b()) {
            nSetDefaultResolution(i2);
        }
    }

    public void setDisplayView(AliDisplayView aliDisplayView) {
        this.mAliDisplayView = aliDisplayView;
        if (aliDisplayView == null) {
            this.mDisplayViewHelper = null;
            return;
        }
        DisplayViewHelper displayViewHelper = aliDisplayView.getDisplayViewHelper();
        this.mDisplayViewHelper = displayViewHelper;
        if (displayViewHelper != null) {
            displayViewHelper.setOnViewStatusListener(new a.h() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.35
                @Override // com.aliyun.player.videoview.a.a.h
                public void onSurfaceCreated(Surface surface) {
                    NativePlayerBase.this.setSurfaceInner(surface, false);
                }

                @Override // com.aliyun.player.videoview.a.a.h
                public void onSurfaceDestroy() {
                    NativePlayerBase.this.setSurfaceInner(null, false);
                }

                @Override // com.aliyun.player.videoview.a.a.h
                public void onSurfaceSizeChanged() {
                    NativePlayerBase.this.surfaceChanged();
                }

                @Override // com.aliyun.player.videoview.a.a.h
                public void onViewCreated(AliDisplayView.DisplayViewType displayViewType) {
                }
            });
        }
        if (getVideoWidth() > 0 || getVideoHeight() > 0) {
            nUpdateViewCallback(this.mVideoType);
        }
    }

    public void setDrmCallback(DrmCallback drmCallback) {
        this.mDrmCallback = drmCallback;
    }

    public synchronized void setFastStart(boolean z2) {
        if (f.b()) {
            nSetFastStart(z2);
        }
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        if (f.b()) {
            nSetFilterConfig(filterConfig == null ? null : filterConfig.toString());
        }
    }

    public void setFilterInvalid(String str, boolean z2) {
        if (f.b()) {
            nSetFilterInvalid(str, z2);
        }
    }

    public void setGlobalTime(String str) {
        if (f.b()) {
            nSetGlobalTime(str);
        }
    }

    public synchronized void setIPResolveType(IPlayer.IPResolveType iPResolveType) {
        if (f.b()) {
            nSetIPResolveType(iPResolveType.ordinal());
        }
    }

    public synchronized void setLoop(boolean z2) {
        if (f.b()) {
            nSetLoop(z2);
        }
    }

    public void setMaxAccurateSeekDelta(int i2) {
        if (f.b()) {
            nSetMaxAccurateSeekDelta(i2);
        }
    }

    public synchronized void setMirrorMode(IPlayer.MirrorMode mirrorMode) {
        DisplayViewHelper displayViewHelper;
        try {
            if (f.b()) {
                if (this.mAliDisplayView != null && this.mDirectRender && (displayViewHelper = this.mDisplayViewHelper) != null) {
                    displayViewHelper.setMirrorMode(mirrorMode);
                }
                nSetMirrorMode(mirrorMode.getValue());
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void setMute(boolean z2) {
        if (f.b()) {
            nSetMute(z2);
        }
    }

    protected void setNativeContext(long j2) {
        this.mNativeContext = j2;
    }

    public void setOnAVNotSyncStatusListener(IPlayer.OnAVNotSyncStatusListener onAVNotSyncStatusListener) {
        this.mOnAVNotSyncStatusListener = onAVNotSyncStatusListener;
    }

    public void setOnChooseTrackIndexListener(IPlayer.OnChooseTrackIndexListener onChooseTrackIndexListener) {
        this.mOnChooseTrackIndexListener = onChooseTrackIndexListener;
    }

    public void setOnCompletionListener(IPlayer.OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    public void setOnErrorListener(IPlayer.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(IPlayer.OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    public void setOnLoadingStatusListener(IPlayer.OnLoadingStatusListener onLoadingStatusListener) {
        this.mOnLoadingStatusListener = onLoadingStatusListener;
    }

    public void setOnPreRenderFrameCallback(IPlayer.OnPreRenderFrameCallback onPreRenderFrameCallback) {
        if (f.b()) {
            this.mPreRenderFrameCallback = onPreRenderFrameCallback;
            nEnablePreFrameCb(onPreRenderFrameCallback != null);
        }
    }

    public void setOnPreparedListener(IPlayer.OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public void setOnRenderFrameCallback(IPlayer.OnRenderFrameCallback onRenderFrameCallback) {
        if (f.b()) {
            this.mRenderFrameCallback = onRenderFrameCallback;
            nEnableFrameCb(onRenderFrameCallback != null);
        }
    }

    public void setOnRenderingStartListener(IPlayer.OnRenderingStartListener onRenderingStartListener) {
        this.mOnRenderingStartListener = onRenderingStartListener;
    }

    public void setOnReportEventListener(IPlayer.OnReportEventListener onReportEventListener) {
        this.mOnEventReportListner = onReportEventListener;
    }

    public void setOnSeekCompleteListener(IPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        this.mOnSeekCompleteListener = onSeekCompleteListener;
    }

    public void setOnSeiDataListener(IPlayer.OnSeiDataListener onSeiDataListener) {
        this.mOnSeiDataListener = onSeiDataListener;
    }

    public void setOnSnapShotListener(IPlayer.OnSnapShotListener onSnapShotListener) {
        this.mOnSnapShotListener = onSnapShotListener;
    }

    public void setOnStateChangedListener(IPlayer.OnStateChangedListener onStateChangedListener) {
        this.mOnStateChangedListener = onStateChangedListener;
    }

    public void setOnStreamSwitchedListener(IPlayer.OnStreamSwitchedListener onStreamSwitchedListener) {
        this.mOnStreamSwitchedListener = onStreamSwitchedListener;
    }

    public void setOnSubTrackInfoGetListener(IPlayer.OnSubTrackReadyListener onSubTrackReadyListener) {
        this.mOnSubTrackReadyListener = onSubTrackReadyListener;
    }

    public void setOnSubtitleDisplayListener(IPlayer.OnSubtitleDisplayListener onSubtitleDisplayListener) {
        this.mOnSubtitleDisplayListener = onSubtitleDisplayListener;
    }

    public void setOnTrackInfoGetListener(IPlayer.OnTrackReadyListener onTrackReadyListener) {
        this.mOnTrackReadyListener = onTrackReadyListener;
    }

    public void setOnTrackSelectRetListener(IPlayer.OnTrackChangedListener onTrackChangedListener) {
        this.mOnTrackChangedListener = onTrackChangedListener;
    }

    public void setOnVideoRenderedListener(IPlayer.OnVideoRenderedListener onVideoRenderedListener) {
        this.mOnVideoRenderedListener = onVideoRenderedListener;
        nEnableVideoRenderedCallback(onVideoRenderedListener != null);
    }

    public void setOnVideoSizeChangedListener(IPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.mOnVideoSizeChangedListener = onVideoSizeChangedListener;
    }

    public synchronized void setOption(String str, String str2) {
        try {
            if (f.b()) {
                if (str.equals("player_option_" + String.valueOf(4) + "_int")) {
                    boolean z2 = true;
                    if (Integer.parseInt(str2) != 1) {
                        z2 = false;
                    }
                    this.mAllowResetDisplayView = z2;
                }
                nSetOption(str, str2);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void setOutputAudioChannel(int i2) {
        if (f.b()) {
            nSetOutputAudioChannel(i2);
        }
    }

    public synchronized void setPlayerScene(PlayerScene playerScene) {
        if (f.b()) {
            nSetPlayerScene(playerScene.ordinal());
        }
    }

    public void setPreferPlayerName(String str) {
        if (f.b()) {
            nSetPreferPlayerName(str);
        }
    }

    public void setRenderFrameCallbackConfig(IPlayer.RenderFrameCallbackConfig renderFrameCallbackConfig) {
        if (f.b()) {
            nSetFrameCbConfig(!renderFrameCallbackConfig.mVideoDataAddr, !renderFrameCallbackConfig.mAudioDataAddr);
        }
    }

    public synchronized void setRotateMode(IPlayer.RotateMode rotateMode) {
        DisplayViewHelper displayViewHelper;
        try {
            if (f.b()) {
                if (this.mAliDisplayView != null && this.mDirectRender && (displayViewHelper = this.mDisplayViewHelper) != null) {
                    displayViewHelper.setRotateMode(rotateMode);
                }
                nSetRotateMode(rotateMode.getValue());
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void setScaleMode(final IPlayer.ScaleMode scaleMode) {
        try {
            if (f.b()) {
                AliDisplayView aliDisplayView = this.mAliDisplayView;
                if (aliDisplayView != null && this.mDirectRender) {
                    aliDisplayView.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (NativePlayerBase.this.mDisplayViewHelper != null) {
                                NativePlayerBase.this.mDisplayViewHelper.setScaleMode(scaleMode);
                            }
                        }
                    });
                }
                nSetScaleMode(scaleMode.ordinal());
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void setSpeed(float f2) {
        if (f.b()) {
            nSetSpeed(f2);
        }
    }

    public synchronized void setStartTime(long j2, int i2) {
        if (f.b()) {
            this.mCurrentThreadHandler.removeMessages(1000);
            nSetStartTime(j2, i2);
        }
    }

    public void setStreamDelayTime(int i2, int i3) {
        if (f.b()) {
            nSetStreamDelayTime(i2, i3);
        }
    }

    public synchronized void setSurface(Surface surface) {
        if (this.mAliDisplayView != null) {
            return;
        }
        this.mAliDisplayView = null;
        this.mDisplayViewHelper = null;
        setSurfaceInner(surface, true);
    }

    public synchronized void setTraceId(String str) {
        if (f.b()) {
            nSetTraceID(str);
        }
    }

    public synchronized void setUserData(String str) {
        if (f.b()) {
            nSetUserData(str);
        }
    }

    public synchronized void setVideoBackgroundColor(int i2) {
        DisplayViewHelper displayViewHelper;
        try {
            if (f.b()) {
                if (this.mAliDisplayView != null && this.mDirectRender && (displayViewHelper = this.mDisplayViewHelper) != null) {
                    displayViewHelper.setBackgroundColor(i2);
                }
                nSetVideoBackgroundColor(i2);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void setVideoTag(int[] iArr) {
        if (f.b()) {
            nSetVideoTag(iArr);
        }
    }

    public synchronized void setVolume(float f2) {
        if (f.b()) {
            nSetVolume(f2);
        }
    }

    public synchronized void snapShot() {
        try {
            if (f.b()) {
                AliDisplayView aliDisplayView = this.mAliDisplayView;
                if (aliDisplayView == null || !this.mDirectRender) {
                    nSnapShot();
                } else {
                    aliDisplayView.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.2
                        @Override // java.lang.Runnable
                        public void run() {
                            final Bitmap bitmapSnapshot;
                            final int width;
                            final int height;
                            if (NativePlayerBase.this.mDisplayViewHelper != null) {
                                bitmapSnapshot = NativePlayerBase.this.mDisplayViewHelper.snapshot();
                            } else {
                                Logger.e(NativePlayerBase.TAG, "snapshot: mDisplayViewHelper is null");
                                bitmapSnapshot = null;
                            }
                            if (bitmapSnapshot != null) {
                                width = bitmapSnapshot.getWidth();
                                height = bitmapSnapshot.getHeight();
                            } else {
                                width = 0;
                                height = 0;
                            }
                            NativePlayerBase.this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.player.nativeclass.NativePlayerBase.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (NativePlayerBase.this.mOnSnapShotListener == null || bitmapSnapshot == null) {
                                        return;
                                    }
                                    NativePlayerBase.this.mOnSnapShotListener.onSnapShot(bitmapSnapshot, width, height);
                                }
                            });
                        }
                    });
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void start() {
        if (f.b()) {
            nStart();
        }
    }

    public synchronized void stop() {
        try {
            if (f.b()) {
                if (this.mAliDisplayView != null && this.mDirectRender) {
                    clearScreenIfNeed();
                }
                nStop();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void surfaceChanged() {
        if (f.b()) {
            nSurfaceChanged();
        }
    }

    public synchronized void switchStream(String str) {
        if (f.b()) {
            nSwitchStream(str);
        }
    }

    public void updateFilterConfig(String str, FilterConfig.FilterOptions filterOptions) {
        if (f.b()) {
            nUpdateFilterConfig(str, filterOptions == null ? null : filterOptions.toString());
        }
    }

    public String getCacheFilePath(String str, String str2, String str3, int i2) {
        return nGetCacheFilePath(str, str2, str3, i2);
    }

    public synchronized void seekTo(long j2, int i2) {
        if (f.b()) {
            this.mCurrentThreadHandler.removeMessages(1000);
            nSeekTo(j2, i2);
        }
    }

    public synchronized void selectTrack(int i2, boolean z2) {
        if (f.b()) {
            nSelectTrackA(i2, z2);
        }
    }
}
