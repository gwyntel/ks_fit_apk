package com.aliyun.player;

import android.graphics.Bitmap;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.aliyun.player.FilterConfig;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.nativeclass.CacheConfig;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.aliyun.player.nativeclass.PlayerScene;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.videoview.AliDisplayView;
import com.aliyun.thumbnail.ThumbnailBitmapInfo;
import com.cicada.player.utils.FrameInfo;
import com.cicada.player.utils.media.DrmCallback;
import com.yc.utesdk.ble.close.KeyType;
import java.util.Map;

/* loaded from: classes3.dex */
public interface IPlayer {
    public static final int ALLOW_PRE_RENDER = 2;
    public static final int PLAYED_DURATION_INCLUDE_SPEED = 3;
    public static final int PLAY_ALLOW_RESET_DISPLAY_VIEW = 4;
    public static final int SET_MEDIA_TYPE = 0;
    public static final int completion = 6;
    public static final int error = 7;
    public static final int idle = 0;
    public static final int initalized = 1;
    public static final int paused = 4;
    public static final int prepared = 2;
    public static final int started = 3;
    public static final int stopped = 5;
    public static final int unknow = -1;

    public enum AlphaRenderMode {
        RENDER_MODE_ALPHA_NONE(0),
        RENDER_MODE_ALPHA_AT_RIGHT(1),
        RENDER_MODE_ALPHA_AT_LEFT(2),
        RENDER_MODE_ALPHA_AT_TOP(3),
        RENDER_MODE_ALPHA_AT_BOTTOM(4);

        private int mValue;

        AlphaRenderMode(int i2) {
            this.mValue = i2;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    public interface ConvertURLCallback {
        String convertURL(String str, String str2);
    }

    public enum IPResolveType {
        IpResolveWhatEver,
        IpResolveV4,
        IpResolveV6
    }

    public enum MirrorMode {
        MIRROR_MODE_NONE(0),
        MIRROR_MODE_HORIZONTAL(1),
        MIRROR_MODE_VERTICAL(2);

        private int mValue;

        MirrorMode(int i2) {
            this.mValue = i2;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    public interface OnAVNotSyncStatusListener {
        void onAVNotSyncEnd();

        void onAVNotSyncStart(int i2);
    }

    public interface OnChooseTrackIndexListener {
        int onChooseTrackIndex(TrackInfo[] trackInfoArr);
    }

    public interface OnCompletionListener {
        void onCompletion();
    }

    public interface OnErrorListener {
        void onError(ErrorInfo errorInfo);
    }

    public interface OnInfoListener {
        void onInfo(InfoBean infoBean);
    }

    public interface OnLoadingStatusListener {
        void onLoadingBegin();

        void onLoadingEnd();

        void onLoadingProgress(int i2, float f2);
    }

    public interface OnPreRenderFrameCallback {
        boolean onPreRenderFrame(FrameInfo frameInfo);
    }

    public interface OnPreparedListener {
        void onPrepared();
    }

    public interface OnRenderFrameCallback {
        boolean onRenderFrame(FrameInfo frameInfo);
    }

    public interface OnRenderingStartListener {
        void onRenderingStart();
    }

    public interface OnReportEventListener {
        void onEventParam(Map<String, String> map);
    }

    public interface OnSeekCompleteListener {
        void onSeekComplete();
    }

    public interface OnSeiDataListener {
        void onSeiData(int i2, byte[] bArr, byte[] bArr2);
    }

    public interface OnSnapShotListener {
        void onSnapShot(Bitmap bitmap, int i2, int i3);
    }

    public interface OnStateChangedListener {
        void onStateChanged(int i2);
    }

    public interface OnStreamSwitchedListener {
        void onSwitchedFail(String str, ErrorInfo errorInfo);

        void onSwitchedSuccess(String str);
    }

    public interface OnSubTrackReadyListener {
        void onSubTrackReady(MediaInfo mediaInfo);
    }

    public interface OnSubtitleDisplayListener {
        void onSubtitleExtAdded(int i2, String str);

        void onSubtitleHeader(int i2, String str);

        void onSubtitleHide(int i2, long j2);

        void onSubtitleShow(int i2, long j2, String str);
    }

    public interface OnThumbnailListener {
        void onGetFail(long j2, ErrorInfo errorInfo);

        void onGetSuccess(long j2, ThumbnailBitmapInfo thumbnailBitmapInfo);
    }

    public interface OnTrackChangedListener {
        void onChangedFail(TrackInfo trackInfo, ErrorInfo errorInfo);

        void onChangedSuccess(TrackInfo trackInfo);
    }

    public interface OnTrackReadyListener {
        void onTrackReady(MediaInfo mediaInfo);
    }

    public interface OnVideoRenderedListener {
        void onVideoRendered(long j2, long j3);
    }

    public interface OnVideoSizeChangedListener {
        void onVideoSizeChanged(int i2, int i3);
    }

    public static class Option {
        private String mValue;
        public static Option RenderFPS = new Option("renderFps");
        public static Option DownloadBitrate = new Option("downloadBitrate");
        public static Option VideoBitrate = new Option("videoBitrate");
        public static Option AudioBitrate = new Option("audioBitrate");
        public static Option IsAbrSwitching = new Option("isAbrSwitching");

        private Option(String str) {
            this.mValue = str;
        }

        public String getValue() {
            return this.mValue;
        }
    }

    public enum OutputAudioChannel {
        OUTPUT_AUDIO_CHANNEL_NONE(0),
        OUTPUT_AUDIO_CHANNEL_LEFT(1),
        OUTPUT_AUDIO_CHANNEL_RIGHT(2);

        private int mValue;

        OutputAudioChannel(int i2) {
            this.mValue = i2;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    public enum PropertyKey {
        RESPONSE_INFO(0),
        CONNECT_INFO(1);

        private int mValue;

        PropertyKey(int i2) {
            this.mValue = i2;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    public static class RenderFrameCallbackConfig {
        public boolean mVideoDataAddr = true;
        public boolean mAudioDataAddr = false;
    }

    public enum RotateMode {
        ROTATE_0(0),
        ROTATE_90(90),
        ROTATE_180(180),
        ROTATE_270(KeyType.QUERY_BRIGHT_SCREEN_PARAM);

        private int mValue;

        RotateMode(int i2) {
            this.mValue = i2;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    public enum ScaleMode {
        SCALE_ASPECT_FIT(0),
        SCALE_ASPECT_FILL(1),
        SCALE_TO_FILL(2);

        private int mValue;

        ScaleMode(int i2) {
            this.mValue = i2;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    public enum SeekMode {
        Accurate(1),
        Inaccurate(16);

        private int mValue;

        SeekMode(int i2) {
            this.mValue = i2;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    void addExtSubtitle(String str);

    void clearScreen();

    @Deprecated
    TrackInfo currentTrack(int i2);

    TrackInfo currentTrack(TrackInfo.Type type);

    void enableHardwareDecoder(boolean z2);

    AlphaRenderMode getAlphaRenderMode();

    long getBufferedPosition();

    String getCacheFilePath(String str);

    String getCacheFilePath(String str, String str2, String str3, int i2);

    PlayerConfig getConfig();

    long getCurrentPosition();

    long getDuration();

    MediaInfo getMediaInfo();

    MirrorMode getMirrorMode();

    long getNativeContextAddr();

    Object getOption(Option option);

    long getPlayedDuration();

    String getPlayerName();

    int getPlayerStatus();

    String getPropertyString(PropertyKey propertyKey);

    RotateMode getRotateMode();

    ScaleMode getScaleMode();

    float getSpeed();

    MediaInfo getSubMediaInfo();

    String getUserData();

    int getVideoHeight();

    int getVideoRotation();

    int getVideoWidth();

    float getVolume();

    boolean isAutoPlay();

    boolean isLoop();

    boolean isMute();

    void pause();

    void prepare();

    @Deprecated
    void redraw();

    void release();

    void releaseAsync();

    void reload();

    void reset();

    void seekTo(long j2);

    void seekTo(long j2, SeekMode seekMode);

    void selectExtSubtitle(int i2, boolean z2);

    void selectTrack(int i2);

    void selectTrack(int i2, boolean z2);

    void sendCustomEvent(String str);

    void setAlphaRenderMode(AlphaRenderMode alphaRenderMode);

    void setAutoPlay(boolean z2);

    void setCacheConfig(CacheConfig cacheConfig);

    void setConfig(PlayerConfig playerConfig);

    void setDefaultBandWidth(int i2);

    void setDefaultResolution(int i2);

    void setDisplay(SurfaceHolder surfaceHolder);

    void setDisplayView(AliDisplayView aliDisplayView);

    void setDrmCallback(DrmCallback drmCallback);

    void setFastStart(boolean z2);

    void setFilterConfig(FilterConfig filterConfig);

    void setFilterInvalid(String str, boolean z2);

    void setIPResolveType(IPResolveType iPResolveType);

    void setLoop(boolean z2);

    void setMaxAccurateSeekDelta(int i2);

    void setMirrorMode(MirrorMode mirrorMode);

    void setMute(boolean z2);

    void setOnAVNotSyncStatusListener(OnAVNotSyncStatusListener onAVNotSyncStatusListener);

    void setOnChooseTrackIndexListener(OnChooseTrackIndexListener onChooseTrackIndexListener);

    void setOnCompletionListener(OnCompletionListener onCompletionListener);

    void setOnErrorListener(OnErrorListener onErrorListener);

    void setOnInfoListener(OnInfoListener onInfoListener);

    void setOnLoadingStatusListener(OnLoadingStatusListener onLoadingStatusListener);

    void setOnPreRenderFrameCallback(OnPreRenderFrameCallback onPreRenderFrameCallback);

    void setOnPreparedListener(OnPreparedListener onPreparedListener);

    void setOnRenderFrameCallback(OnRenderFrameCallback onRenderFrameCallback);

    void setOnRenderingStartListener(OnRenderingStartListener onRenderingStartListener);

    void setOnReportEventListener(OnReportEventListener onReportEventListener);

    void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener);

    void setOnSeiDataListener(OnSeiDataListener onSeiDataListener);

    void setOnSnapShotListener(OnSnapShotListener onSnapShotListener);

    void setOnStateChangedListener(OnStateChangedListener onStateChangedListener);

    void setOnStreamSwitchedListener(OnStreamSwitchedListener onStreamSwitchedListener);

    void setOnSubTrackReadyListener(OnSubTrackReadyListener onSubTrackReadyListener);

    void setOnSubtitleDisplayListener(OnSubtitleDisplayListener onSubtitleDisplayListener);

    void setOnTrackChangedListener(OnTrackChangedListener onTrackChangedListener);

    void setOnTrackReadyListener(OnTrackReadyListener onTrackReadyListener);

    void setOnVideoRenderedListener(OnVideoRenderedListener onVideoRenderedListener);

    void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener);

    void setOption(int i2, int i3);

    void setOption(int i2, String str);

    void setOutputAudioChannel(OutputAudioChannel outputAudioChannel);

    void setPlayerScene(PlayerScene playerScene);

    void setPreferPlayerName(String str);

    void setRenderFrameCallbackConfig(RenderFrameCallbackConfig renderFrameCallbackConfig);

    void setRotateMode(RotateMode rotateMode);

    void setScaleMode(ScaleMode scaleMode);

    void setSpeed(float f2);

    void setStartTime(long j2, SeekMode seekMode);

    void setStreamDelayTime(int i2, int i3);

    void setSurface(Surface surface);

    void setTraceId(String str);

    void setUserData(String str);

    void setVideoBackgroundColor(int i2);

    void setVideoTag(int[] iArr);

    void setVolume(float f2);

    void snapshot();

    void start();

    void stop();

    void surfaceChanged();

    void switchStream(String str);

    void updateFilterConfig(String str, FilterConfig.FilterOptions filterOptions);
}
