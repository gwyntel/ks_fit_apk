package com.aliyun.player.nativeclass;

import android.content.Context;
import android.view.Surface;
import com.aliyun.player.ApasaraExternalPlayer;
import com.aliyun.player.IPlayer;
import com.aliyun.utils.f;
import com.cicada.player.utils.NativeUsed;
import com.kingsmith.miot.KsAction;

@NativeUsed
/* loaded from: classes3.dex */
public class NativeExternalPlayer {
    private static Context sContext;
    private ApasaraExternalPlayer mExternPlayer = null;
    private long mNativeInstance = 0;

    static {
        f.f();
        sContext = null;
    }

    private int getCurrentStreamIndex(int i2) {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return -1;
        }
        ApasaraExternalPlayer.StreamType streamType = ApasaraExternalPlayer.StreamType.ST_TYPE_UNKNOWN;
        if (i2 == 0) {
            streamType = ApasaraExternalPlayer.StreamType.ST_TYPE_VIDEO;
        } else if (i2 == 1) {
            streamType = ApasaraExternalPlayer.StreamType.ST_TYPE_AUDIO;
        } else if (i2 == 2) {
            streamType = ApasaraExternalPlayer.StreamType.ST_TYPE_SUB;
        }
        return apasaraExternalPlayer.getCurrentStreamIndex(streamType);
    }

    private String getOption(String str) {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return null;
        }
        return apasaraExternalPlayer.getOption(str);
    }

    private long getPropertyInt(ApasaraExternalPlayer.PropertyKey propertyKey) {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return 0L;
        }
        return apasaraExternalPlayer.getPropertyInt(propertyKey);
    }

    private long getPropertyLong(ApasaraExternalPlayer.PropertyKey propertyKey) {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return 0L;
        }
        return apasaraExternalPlayer.getPropertyLong(propertyKey);
    }

    private String getPropertyString(ApasaraExternalPlayer.PropertyKey propertyKey) {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return null;
        }
        return apasaraExternalPlayer.getPropertyString(propertyKey);
    }

    private int invokeComponent(String str) {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return -1;
        }
        return apasaraExternalPlayer.invokeComponent(str);
    }

    @NativeUsed
    public static boolean isSupport(Options options) {
        return ApasaraExternalPlayer.isSupportExternal(options) != null;
    }

    public static void loadClass() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnAutoPlayStart(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnBufferPositionUpdate(long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnCaptureScreen(long j2, int i2, int i3, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnCompletion(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnErrorCallback(long j2, long j3, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnEventCallback(long j2, long j3, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnFirstFrameShow(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnLoadingEnd(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnLoadingProgress(long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnLoadingStart(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnLoopingStart(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnPositionUpdate(long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnPrepared(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnSeekEnd(long j2, boolean z2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnSeeking(long j2, boolean z2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnStatusChanged(long j2, int i2, int i3);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnStreamInfoGet(long j2, MediaInfo mediaInfo);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnStreamSwitchSuc(long j2, int i2, TrackInfo trackInfo);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnSubtitleExtAdd(long j2, long j3, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnSubtitleHide(long j2, long j3, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnSubtitleShow(long j2, long j3, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnVideoRendered(long j2, long j3, long j4);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnVideoSizeChanged(long j2, int i2, int i3);

    /* JADX INFO: Access modifiers changed from: private */
    public native byte[] nativeRequestKey(long j2, String str, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    public native byte[] nativeRequestProvision(long j2, String str, byte[] bArr);

    private int selectExtSubtitle(int i2, boolean z2) {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return -1;
        }
        return apasaraExternalPlayer.selectExtSubtitle(i2, z2);
    }

    public static void setContext(Context context) {
        if (sContext != null || context == null) {
            return;
        }
        sContext = context.getApplicationContext();
    }

    private void setDecoderType(int i2) {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return;
        }
        ApasaraExternalPlayer.DecoderType decoderType = ApasaraExternalPlayer.DecoderType.DT_SOFTWARE;
        if (i2 == 0 || i2 == 1) {
            decoderType = ApasaraExternalPlayer.DecoderType.DT_HARDWARE;
        }
        apasaraExternalPlayer.setDecoderType(decoderType);
    }

    private void setMirrorMode(int i2) {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return;
        }
        IPlayer.MirrorMode mirrorMode = IPlayer.MirrorMode.MIRROR_MODE_NONE;
        if (i2 != 0) {
            if (i2 == 1) {
                mirrorMode = IPlayer.MirrorMode.MIRROR_MODE_HORIZONTAL;
            } else if (i2 == 2) {
                mirrorMode = IPlayer.MirrorMode.MIRROR_MODE_VERTICAL;
            }
        }
        apasaraExternalPlayer.setMirrorMode(mirrorMode);
    }

    private int setOption(String str, String str2) {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return -1;
        }
        return apasaraExternalPlayer.setOption(str, str2);
    }

    private void setRotateMode(int i2) {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return;
        }
        IPlayer.RotateMode rotateMode = IPlayer.RotateMode.ROTATE_0;
        if (i2 == 90) {
            rotateMode = IPlayer.RotateMode.ROTATE_90;
        } else if (i2 == 180) {
            rotateMode = IPlayer.RotateMode.ROTATE_180;
        } else if (i2 == 270) {
            rotateMode = IPlayer.RotateMode.ROTATE_270;
        }
        apasaraExternalPlayer.setRotateMode(rotateMode);
    }

    private void setScaleMode(int i2) {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return;
        }
        IPlayer.ScaleMode scaleMode = IPlayer.ScaleMode.SCALE_ASPECT_FIT;
        if (i2 != 0) {
            if (i2 == 1) {
                scaleMode = IPlayer.ScaleMode.SCALE_ASPECT_FILL;
            } else if (i2 == 2) {
                scaleMode = IPlayer.ScaleMode.SCALE_TO_FILL;
            }
        }
        apasaraExternalPlayer.setScaleMode(scaleMode);
    }

    @NativeUsed
    ApasaraExternalPlayer.StreamType SwitchStream(int i2) {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        return apasaraExternalPlayer == null ? ApasaraExternalPlayer.StreamType.ST_TYPE_UNKNOWN : apasaraExternalPlayer.switchStream(i2);
    }

    public boolean callRbPvD(String str, boolean z2) {
        return this.mExternPlayer == null ? z2 : "IsMute".equals(str) ? this.mExternPlayer.isMute() : "isLooping".equals(str) ? this.mExternPlayer.isLooping() : "IsAutoPlay".equals(str) ? this.mExternPlayer.isAutoPlay() : z2;
    }

    public float callRfPvD(String str, float f2) {
        return this.mExternPlayer == null ? f2 : "GetVideoRenderFps".equals(str) ? this.mExternPlayer.getVideoRenderFps() : "GetVolume".equals(str) ? this.mExternPlayer.getVolume() : "getSpeed".equals(str) ? this.mExternPlayer.getSpeed() : "GetVideoDecodeFps".equals(str) ? this.mExternPlayer.getVideoDecodeFps() : f2;
    }

    public int callRiPiD(String str, int i2, int i3) {
        return this.mExternPlayer == null ? i3 : "GetCurrentStreamIndex".equals(str) ? getCurrentStreamIndex(i2) : "SwitchStream".equals(str) ? SwitchStream(i2).getValue() : i3;
    }

    public int callRiPvD(String str, int i2) {
        if (this.mExternPlayer == null) {
            return i2;
        }
        if (!"Stop".equals(str)) {
            return "GetScaleMode".equals(str) ? this.mExternPlayer.getScaleMode().getValue() : "GetRotateMode".equals(str) ? this.mExternPlayer.getRotateMode().getValue() : "GetMirrorMode".equals(str) ? this.mExternPlayer.getMirrorMode().getValue() : "GetDecoderType".equals(str) ? this.mExternPlayer.getDecoderType().getValue() : "getVideoWidth".equals(str) ? this.mExternPlayer.getVideoWidth() : "getVideoHeight".equals(str) ? this.mExternPlayer.getVideoHeight() : "GetVideoRotation".equals(str) ? this.mExternPlayer.getVideoRotation() : i2;
        }
        this.mExternPlayer.stop();
        return 0;
    }

    public long callRlPvD(String str, long j2) {
        return this.mExternPlayer == null ? j2 : "GetDuration".equals(str) ? this.mExternPlayer.getDuration() : "GetPlayingPosition".equals(str) ? this.mExternPlayer.getPlayingPosition() : "GetBufferPosition".equals(str) ? this.mExternPlayer.getBufferPosition() : "GetMasterClockPts".equals(str) ? this.mExternPlayer.getMasterClockPts() : j2;
    }

    public Object callRoPi(String str, int i2) {
        if (this.mExternPlayer == null) {
            return null;
        }
        if ("GetCurrentStreamInfo".equals(str)) {
            return getCurrentStreamInfo(i2);
        }
        if ("getName".equals(str)) {
            return getName();
        }
        return null;
    }

    public void callRvPf(String str, float f2) {
        if (this.mExternPlayer == null) {
            return;
        }
        if ("SetVolume".equals(str)) {
            this.mExternPlayer.setVolume(f2);
        }
        if (KsAction.setSpeed.equals(str)) {
            this.mExternPlayer.setSpeed(f2);
        }
    }

    public void callRvPi(String str, int i2) {
        if (this.mExternPlayer == null) {
            return;
        }
        if ("SetVolume".equals(str)) {
            this.mExternPlayer.setVolume(i2);
            return;
        }
        if ("SetScaleMode".equals(str)) {
            setScaleMode(i2);
            return;
        }
        if ("SetRotateMode".equals(str)) {
            setRotateMode(i2);
            return;
        }
        if ("SetMirrorMode".equals(str)) {
            setMirrorMode(i2);
            return;
        }
        if ("SetTimeout".equals(str)) {
            this.mExternPlayer.setTimeout(i2);
        } else if ("SetDropBufferThreshold".equals(str)) {
            this.mExternPlayer.setDropBufferThreshold(i2);
        } else if ("SetDecoderType".equals(str)) {
            setDecoderType(i2);
        }
    }

    public void callRvPlb(String str, long j2, boolean z2) {
        if (this.mExternPlayer == null) {
            return;
        }
        if ("SeekTo".equals(str)) {
            this.mExternPlayer.seekTo(j2, z2);
            return;
        }
        if ("SetVideoBackgroundColor".equals(str)) {
            this.mExternPlayer.setVideoBackgroundColor(j2);
            return;
        }
        if ("Mute".equals(str)) {
            this.mExternPlayer.mute(z2);
            return;
        }
        if ("EnterBackGround".equals(str)) {
            this.mExternPlayer.enterBackGround(z2);
            return;
        }
        if ("SetLooping".equals(str)) {
            this.mExternPlayer.setLooping(z2);
        } else if ("SetAutoPlay".equals(str)) {
            this.mExternPlayer.setAutoPlay(z2);
        } else if ("selectExtSubtitle".equals(str)) {
            this.mExternPlayer.selectExtSubtitle((int) j2, z2);
        }
    }

    public void callRvPo(String str, Object obj) {
        if (this.mExternPlayer != null && "SetView".equals(str)) {
            this.mExternPlayer.setSurface((Surface) obj);
        }
    }

    public void callRvPs(String str, String str2) {
        if (this.mExternPlayer == null) {
            return;
        }
        if ("SetDataSource".equals(str)) {
            this.mExternPlayer.setDataSource(str2);
            return;
        }
        if ("addExtSubtitle".equals(str)) {
            this.mExternPlayer.addExtSubtitle(str2);
            return;
        }
        if ("AddCustomHttpHeader".equals(str)) {
            this.mExternPlayer.addCustomHttpHeader(str2);
        } else if ("SetUserAgent".equals(str)) {
            this.mExternPlayer.setUserAgent(str2);
        } else if ("SetRefer".equals(str)) {
            this.mExternPlayer.setRefer(str2);
        }
    }

    public void callRvPv(String str) {
        if (this.mExternPlayer == null) {
            return;
        }
        if ("Release".equals(str)) {
            this.mExternPlayer.release();
            this.mNativeInstance = 0L;
            this.mExternPlayer = null;
            return;
        }
        if ("Prepare".equals(str)) {
            this.mExternPlayer.prepare();
            return;
        }
        if ("Start".equals(str)) {
            this.mExternPlayer.start();
            return;
        }
        if ("Pause".equals(str)) {
            this.mExternPlayer.pause();
            return;
        }
        if ("CaptureScreen".equals(str)) {
            this.mExternPlayer.captureScreen();
        } else if ("reLoad".equals(str)) {
            this.mExternPlayer.reLoad();
        } else if ("RemoveAllCustomHttpHeader".equals(str)) {
            this.mExternPlayer.removeAllCustomHttpHeader();
        }
    }

    @NativeUsed
    public void create(long j2, Options options) {
        ApasaraExternalPlayer apasaraExternalPlayerIsSupportExternal = ApasaraExternalPlayer.isSupportExternal(options);
        if (apasaraExternalPlayerIsSupportExternal != null) {
            this.mExternPlayer = apasaraExternalPlayerIsSupportExternal.create(sContext, options);
        }
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return;
        }
        this.mNativeInstance = j2;
        apasaraExternalPlayer.setOnPreparedListener(new ApasaraExternalPlayer.OnPreparedListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.1
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnPreparedListener
            public void onPrepared() {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnPrepared(nativeExternalPlayer.mNativeInstance);
            }
        });
        this.mExternPlayer.setOnLoopingStartListener(new ApasaraExternalPlayer.OnLoopingStartListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.2
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnLoopingStartListener
            public void onLoopingStart() {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnLoopingStart(nativeExternalPlayer.mNativeInstance);
            }
        });
        this.mExternPlayer.setOnCompletionListener(new ApasaraExternalPlayer.OnCompletionListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.3
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnCompletionListener
            public void onCompletion() {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnCompletion(nativeExternalPlayer.mNativeInstance);
            }
        });
        this.mExternPlayer.setOnFirstFrameRenderListener(new ApasaraExternalPlayer.OnFirstFrameRenderListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.4
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnFirstFrameRenderListener
            public void onFirstFrameRender() {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnFirstFrameShow(nativeExternalPlayer.mNativeInstance);
            }
        });
        this.mExternPlayer.setOnLoadStatusListener(new ApasaraExternalPlayer.OnLoadStatusListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.5
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnLoadStatusListener
            public void onLoadingEnd() {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnLoadingEnd(nativeExternalPlayer.mNativeInstance);
            }

            @Override // com.aliyun.player.ApasaraExternalPlayer.OnLoadStatusListener
            public void onLoadingProgress(int i2) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnLoadingProgress(nativeExternalPlayer.mNativeInstance, i2);
            }

            @Override // com.aliyun.player.ApasaraExternalPlayer.OnLoadStatusListener
            public void onLoadingStart() {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnLoadingStart(nativeExternalPlayer.mNativeInstance);
            }
        });
        this.mExternPlayer.setOnAutoPlayStartListener(new ApasaraExternalPlayer.OnAutoPlayStartListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.6
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnAutoPlayStartListener
            public void onAutoPlayStart() {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnAutoPlayStart(nativeExternalPlayer.mNativeInstance);
            }
        });
        this.mExternPlayer.setOnSeekStatusListener(new ApasaraExternalPlayer.OnSeekStatusListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.7
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnSeekStatusListener
            public void onSeekEnd(boolean z2) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnSeekEnd(nativeExternalPlayer.mNativeInstance, z2);
            }

            @Override // com.aliyun.player.ApasaraExternalPlayer.OnSeekStatusListener
            public void onSeekStart(boolean z2) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnSeeking(nativeExternalPlayer.mNativeInstance, z2);
            }
        });
        this.mExternPlayer.setOnPositionUpdateListener(new ApasaraExternalPlayer.OnPositionUpdateListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.8
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnPositionUpdateListener
            public void onPositionUpdate(long j3) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnPositionUpdate(nativeExternalPlayer.mNativeInstance, j3);
            }
        });
        this.mExternPlayer.setOnBufferPositionUpdateListener(new ApasaraExternalPlayer.OnBufferPositionUpdateListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.9
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnBufferPositionUpdateListener
            public void onBufferPositionUpdate(long j3) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnBufferPositionUpdate(nativeExternalPlayer.mNativeInstance, j3);
            }
        });
        this.mExternPlayer.setOnVideoSizeChangedListener(new ApasaraExternalPlayer.OnVideoSizeChangedListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.10
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(int i2, int i3) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnVideoSizeChanged(nativeExternalPlayer.mNativeInstance, i2, i3);
            }
        });
        this.mExternPlayer.setOnStatusChangedListener(new ApasaraExternalPlayer.OnStatusChangedListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.11
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnStatusChangedListener
            public void onStatusChanged(int i2, int i3) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnStatusChanged(nativeExternalPlayer.mNativeInstance, i2, i3);
            }
        });
        this.mExternPlayer.setOnVideoRenderedListener(new ApasaraExternalPlayer.OnVideoRenderedListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.12
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnVideoRenderedListener
            public void onVideoRendered(long j3, long j4) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnVideoRendered(nativeExternalPlayer.mNativeInstance, j3, j4);
            }
        });
        this.mExternPlayer.setOnErrorListener(new ApasaraExternalPlayer.OnErrorListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.13
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnErrorListener
            public void onError(int i2, String str) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnErrorCallback(nativeExternalPlayer.mNativeInstance, i2, str);
            }
        });
        this.mExternPlayer.setOnEventListener(new ApasaraExternalPlayer.OnEventListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.14
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnEventListener
            public void onEvent(int i2, String str) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnEventCallback(nativeExternalPlayer.mNativeInstance, i2, str);
            }
        });
        this.mExternPlayer.setOnStreamInfoGetListener(new ApasaraExternalPlayer.OnStreamInfoGetListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.15
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnStreamInfoGetListener
            public void OnStreamInfoGet(MediaInfo mediaInfo) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnStreamInfoGet(nativeExternalPlayer.mNativeInstance, mediaInfo);
            }
        });
        this.mExternPlayer.setOnStreamSwitchSucListener(new ApasaraExternalPlayer.OnStreamSwitchSucListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.16
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnStreamSwitchSucListener
            public void onStreamSwitchSuc(ApasaraExternalPlayer.StreamType streamType, TrackInfo trackInfo) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnStreamSwitchSuc(nativeExternalPlayer.mNativeInstance, streamType.ordinal(), trackInfo);
            }
        });
        this.mExternPlayer.setOnCaptureScreenListener(new ApasaraExternalPlayer.OnCaptureScreenListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.17
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnCaptureScreenListener
            public void onCaptureScreen(int i2, int i3, byte[] bArr) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnCaptureScreen(nativeExternalPlayer.mNativeInstance, i2, i3, bArr);
            }
        });
        this.mExternPlayer.setOnSubtitleListener(new ApasaraExternalPlayer.OnSubtitleListener() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.18
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnSubtitleListener
            public void onSubtitleExtAdded(int i2, String str) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnSubtitleExtAdd(nativeExternalPlayer.mNativeInstance, i2, str);
            }

            @Override // com.aliyun.player.ApasaraExternalPlayer.OnSubtitleListener
            public void onSubtitleHide(int i2, long j3) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnSubtitleHide(nativeExternalPlayer.mNativeInstance, i2, null);
            }

            @Override // com.aliyun.player.ApasaraExternalPlayer.OnSubtitleListener
            public void onSubtitleShow(int i2, long j3, String str) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                nativeExternalPlayer.nativeOnSubtitleShow(nativeExternalPlayer.mNativeInstance, i2, str.getBytes());
            }
        });
        this.mExternPlayer.setDrmCallback(new ApasaraExternalPlayer.OnDRMCallback() { // from class: com.aliyun.player.nativeclass.NativeExternalPlayer.19
            @Override // com.aliyun.player.ApasaraExternalPlayer.OnDRMCallback
            public byte[] onRequestKey(String str, byte[] bArr) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                return nativeExternalPlayer.nativeRequestKey(nativeExternalPlayer.mNativeInstance, str, bArr);
            }

            @Override // com.aliyun.player.ApasaraExternalPlayer.OnDRMCallback
            public byte[] onRequestProvision(String str, byte[] bArr) {
                NativeExternalPlayer nativeExternalPlayer = NativeExternalPlayer.this;
                return nativeExternalPlayer.nativeRequestProvision(nativeExternalPlayer.mNativeInstance, str, bArr);
            }
        });
    }

    public Object getCurrentStreamInfo(int i2) {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return null;
        }
        ApasaraExternalPlayer.StreamType streamType = ApasaraExternalPlayer.StreamType.ST_TYPE_UNKNOWN;
        if (i2 == 0) {
            streamType = ApasaraExternalPlayer.StreamType.ST_TYPE_VIDEO;
        } else if (i2 == 1) {
            streamType = ApasaraExternalPlayer.StreamType.ST_TYPE_AUDIO;
        } else if (i2 == 2) {
            streamType = ApasaraExternalPlayer.StreamType.ST_TYPE_SUB;
        }
        return apasaraExternalPlayer.getCurrentStreamInfo(streamType);
    }

    public String getName() {
        ApasaraExternalPlayer apasaraExternalPlayer = this.mExternPlayer;
        if (apasaraExternalPlayer == null) {
            return null;
        }
        return apasaraExternalPlayer.getName();
    }
}
