package com.aliyun.player.a;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.media.TimedText;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Surface;
import com.aliyun.player.ApasaraExternalPlayer;
import com.aliyun.player.IPlayer;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.Options;
import com.aliyun.player.nativeclass.TrackInfo;
import com.cicada.player.utils.Logger;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class a extends ApasaraExternalPlayer {
    private ApasaraExternalPlayer.OnStatusChangedListener A;
    private ApasaraExternalPlayer.OnVideoRenderedListener B;
    private ApasaraExternalPlayer.OnErrorListener C;
    private ApasaraExternalPlayer.OnEventListener D;
    private ApasaraExternalPlayer.OnStreamInfoGetListener E;
    private ApasaraExternalPlayer.OnStreamSwitchSucListener F;
    private ApasaraExternalPlayer.OnCaptureScreenListener G;
    private ApasaraExternalPlayer.OnSubtitleListener H;
    private ApasaraExternalPlayer.OnDRMCallback I;
    private long J;
    private boolean K;
    private Map<String, String> L;

    /* renamed from: a, reason: collision with root package name */
    private final int f12055a;

    /* renamed from: b, reason: collision with root package name */
    private Context f12056b;

    /* renamed from: c, reason: collision with root package name */
    private MediaPlayer f12057c;

    /* renamed from: d, reason: collision with root package name */
    private MediaPlayer.TrackInfo[] f12058d;

    /* renamed from: e, reason: collision with root package name */
    private Handler f12059e;

    /* renamed from: f, reason: collision with root package name */
    private ApasaraExternalPlayer.PlayerStatus f12060f;

    /* renamed from: g, reason: collision with root package name */
    private String f12061g;

    /* renamed from: h, reason: collision with root package name */
    private long f12062h;

    /* renamed from: i, reason: collision with root package name */
    private float f12063i;

    /* renamed from: j, reason: collision with root package name */
    private IPlayer.ScaleMode f12064j;

    /* renamed from: k, reason: collision with root package name */
    private boolean f12065k;

    /* renamed from: l, reason: collision with root package name */
    private boolean f12066l;

    /* renamed from: m, reason: collision with root package name */
    private String f12067m;

    /* renamed from: n, reason: collision with root package name */
    private String f12068n;

    /* renamed from: o, reason: collision with root package name */
    private float f12069o;

    /* renamed from: p, reason: collision with root package name */
    private boolean f12070p;

    /* renamed from: q, reason: collision with root package name */
    private ApasaraExternalPlayer.OnPreparedListener f12071q;

    /* renamed from: r, reason: collision with root package name */
    private ApasaraExternalPlayer.OnLoopingStartListener f12072r;

    /* renamed from: s, reason: collision with root package name */
    private ApasaraExternalPlayer.OnCompletionListener f12073s;

    /* renamed from: t, reason: collision with root package name */
    private ApasaraExternalPlayer.OnFirstFrameRenderListener f12074t;

    /* renamed from: u, reason: collision with root package name */
    private ApasaraExternalPlayer.OnLoadStatusListener f12075u;

    /* renamed from: v, reason: collision with root package name */
    private ApasaraExternalPlayer.OnAutoPlayStartListener f12076v;

    /* renamed from: w, reason: collision with root package name */
    private ApasaraExternalPlayer.OnSeekStatusListener f12077w;

    /* renamed from: x, reason: collision with root package name */
    private ApasaraExternalPlayer.OnPositionUpdateListener f12078x;

    /* renamed from: y, reason: collision with root package name */
    private ApasaraExternalPlayer.OnBufferPositionUpdateListener f12079y;

    /* renamed from: z, reason: collision with root package name */
    private ApasaraExternalPlayer.OnVideoSizeChangedListener f12080z;

    /* renamed from: com.aliyun.player.a.a$a, reason: collision with other inner class name */
    class HandlerC0098a extends Handler {
        HandlerC0098a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1000) {
                if (a.this.f12060f.getValue() >= ApasaraExternalPlayer.PlayerStatus.PLAYER_PREPARED.getValue() && a.this.f12060f.getValue() <= ApasaraExternalPlayer.PlayerStatus.PLAYER_STOPPED.getValue()) {
                    if (a.this.f12079y != null) {
                        a.this.f12079y.onBufferPositionUpdate(a.this.getBufferPosition());
                    }
                    if (a.this.f12078x != null) {
                        a.this.f12078x.onPositionUpdate(a.this.getPlayingPosition());
                    }
                }
                a.this.b();
            }
            super.handleMessage(message);
        }
    }

    class b implements MediaPlayer.OnBufferingUpdateListener {
        b() {
        }

        @Override // android.media.MediaPlayer.OnBufferingUpdateListener
        public void onBufferingUpdate(MediaPlayer mediaPlayer, int i2) {
            a.this.f12062h = (long) ((i2 * r5.getDuration()) / 100.0f);
        }
    }

    class c implements MediaPlayer.OnCompletionListener {
        c() {
        }

        @Override // android.media.MediaPlayer.OnCompletionListener
        public void onCompletion(MediaPlayer mediaPlayer) {
            if (a.this.f12073s != null) {
                a.this.f12073s.onCompletion();
            }
            a.this.a(ApasaraExternalPlayer.PlayerStatus.PLAYER_COMPLETION, true);
        }
    }

    class d implements MediaPlayer.OnErrorListener {
        d() {
        }

        @Override // android.media.MediaPlayer.OnErrorListener
        public boolean onError(MediaPlayer mediaPlayer, int i2, int i3) {
            if (a.this.C != null) {
                a.this.C.onError(ErrorCode.ERROR_UNKNOWN.getValue(), "what=" + i2 + ", extra=" + i3);
            }
            a.this.a(ApasaraExternalPlayer.PlayerStatus.PLAYER_ERROR, true);
            return true;
        }
    }

    class e implements MediaPlayer.OnInfoListener {
        e() {
        }

        @Override // android.media.MediaPlayer.OnInfoListener
        public boolean onInfo(MediaPlayer mediaPlayer, int i2, int i3) {
            if (i2 == 702) {
                if (a.this.f12075u == null) {
                    return false;
                }
                a.this.f12075u.onLoadingEnd();
                return false;
            }
            if (i2 == 701) {
                if (a.this.f12075u == null) {
                    return false;
                }
                a.this.f12075u.onLoadingStart();
                return false;
            }
            if (i2 != 3 || a.this.f12074t == null) {
                return false;
            }
            a.this.f12074t.onFirstFrameRender();
            return false;
        }
    }

    class f implements MediaPlayer.OnPreparedListener {
        f() {
        }

        @Override // android.media.MediaPlayer.OnPreparedListener
        public void onPrepared(MediaPlayer mediaPlayer) throws IllegalStateException {
            a aVar = a.this;
            aVar.f12058d = aVar.f12057c.getTrackInfo();
            a.this.a();
            if (a.this.f12070p) {
                if (a.this.f12076v != null) {
                    a.this.f12076v.onAutoPlayStart();
                }
                a.this.a(ApasaraExternalPlayer.PlayerStatus.PLAYER_PREPARED, false);
                a.this.start();
            } else {
                a.this.a(ApasaraExternalPlayer.PlayerStatus.PLAYER_PREPARED, true);
                if (a.this.f12071q != null) {
                    a.this.f12071q.onPrepared();
                }
            }
            if (a.this.J >= 0) {
                a aVar2 = a.this;
                aVar2.seekTo(aVar2.J, a.this.K);
                a.this.J = -1L;
            }
        }
    }

    class g implements MediaPlayer.OnSeekCompleteListener {
        g() {
        }

        @Override // android.media.MediaPlayer.OnSeekCompleteListener
        public void onSeekComplete(MediaPlayer mediaPlayer) {
            if (a.this.f12077w != null) {
                a.this.f12077w.onSeekEnd(false);
            }
        }
    }

    class h implements MediaPlayer.OnTimedTextListener {
        h() {
        }

        @Override // android.media.MediaPlayer.OnTimedTextListener
        public void onTimedText(MediaPlayer mediaPlayer, TimedText timedText) {
        }
    }

    class i implements MediaPlayer.OnVideoSizeChangedListener {
        i() {
        }

        @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i2, int i3) {
            if (a.this.f12080z != null) {
                a.this.f12080z.onVideoSizeChanged(i2, i3);
            }
        }
    }

    public a() {
        this.f12055a = 1000;
        this.f12056b = null;
        this.f12057c = null;
        this.f12058d = null;
        this.f12059e = null;
        this.f12060f = ApasaraExternalPlayer.PlayerStatus.PLAYER_IDLE;
        this.f12061g = null;
        this.f12062h = 0L;
        this.f12063i = 1.0f;
        this.f12064j = IPlayer.ScaleMode.SCALE_ASPECT_FIT;
        this.f12065k = false;
        this.f12066l = false;
        this.f12067m = null;
        this.f12068n = null;
        this.f12069o = 1.0f;
        this.f12070p = false;
        this.f12071q = null;
        this.f12072r = null;
        this.f12073s = null;
        this.f12074t = null;
        this.f12075u = null;
        this.f12076v = null;
        this.f12077w = null;
        this.f12078x = null;
        this.f12079y = null;
        this.f12080z = null;
        this.A = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.E = null;
        this.F = null;
        this.G = null;
        this.H = null;
        this.I = null;
        this.J = -1L;
        this.K = false;
        this.L = new HashMap();
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void addCustomHttpHeader(String str) {
        Logger.v("MediaPlayer", "addCustomHttpHeader() " + str);
        if (!TextUtils.isEmpty(str) || str.contains(":")) {
            String[] strArrSplit = str.split(":");
            this.L.put(strArrSplit[0], strArrSplit[1]);
        }
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void addExtSubtitle(String str) {
        Logger.v("MediaPlayer", "addExtSubtitle() " + str);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void captureScreen() {
        Logger.v("MediaPlayer", "captureScreen() ");
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public ApasaraExternalPlayer create(Context context, Options options) {
        return new a(context, options);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void enterBackGround(boolean z2) {
        Logger.v("MediaPlayer", "enterBackGround() " + z2);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public long getBufferPosition() {
        Logger.v("MediaPlayer", "getBufferPosition() ");
        return this.f12062h;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public int getCurrentStreamIndex(ApasaraExternalPlayer.StreamType streamType) {
        MediaPlayer mediaPlayer;
        Logger.v("MediaPlayer", "getCurrentStreamIndex() " + streamType);
        ApasaraExternalPlayer.PlayerStatus playerStatus = this.f12060f;
        if ((playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PREPARED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PLAYING || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PAUSED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_STOPPED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_COMPLETION) && (mediaPlayer = this.f12057c) != null) {
            return mediaPlayer.getSelectedTrack(streamType == ApasaraExternalPlayer.StreamType.ST_TYPE_AUDIO ? 2 : streamType == ApasaraExternalPlayer.StreamType.ST_TYPE_VIDEO ? 1 : streamType == ApasaraExternalPlayer.StreamType.ST_TYPE_SUB ? 4 : 0);
        }
        return -1;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public TrackInfo getCurrentStreamInfo(ApasaraExternalPlayer.StreamType streamType) {
        MediaPlayer.TrackInfo[] trackInfoArr;
        Logger.v("MediaPlayer", "getCurrentStreamInfo() " + streamType);
        int currentStreamIndex = getCurrentStreamIndex(streamType);
        if (currentStreamIndex < 0 || (trackInfoArr = this.f12058d) == null || currentStreamIndex >= trackInfoArr.length) {
            return null;
        }
        return a(trackInfoArr[currentStreamIndex], currentStreamIndex);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public ApasaraExternalPlayer.DecoderType getDecoderType() {
        Logger.v("MediaPlayer", "getDecoderType() ");
        return ApasaraExternalPlayer.DecoderType.DT_HARDWARE;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public long getDuration() {
        MediaPlayer mediaPlayer;
        Logger.v("MediaPlayer", "getDuration() ");
        ApasaraExternalPlayer.PlayerStatus playerStatus = this.f12060f;
        if ((playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PREPARED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PLAYING || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PAUSED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_STOPPED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_COMPLETION) && (mediaPlayer = this.f12057c) != null) {
            return Math.max(playerStatus != ApasaraExternalPlayer.PlayerStatus.PLAYER_ERROR ? mediaPlayer.getDuration() : 0, 0);
        }
        return 0L;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public long getMasterClockPts() {
        Logger.v("MediaPlayer", "getMasterClockPts() ");
        return 0L;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public IPlayer.MirrorMode getMirrorMode() {
        Logger.v("MediaPlayer", "getMirrorMode() ");
        return IPlayer.MirrorMode.MIRROR_MODE_NONE;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public String getName() {
        return "MediaPlayer";
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public String getOption(String str) {
        Logger.v("MediaPlayer", "getOption() " + str);
        return null;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public ApasaraExternalPlayer.PlayerStatus getPlayerStatus() {
        Logger.v("MediaPlayer", "getPlayerStatus() ");
        return this.f12060f;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public long getPlayingPosition() {
        MediaPlayer mediaPlayer;
        Logger.v("MediaPlayer", "getPlayingPosition() ");
        long j2 = this.J;
        if (j2 >= 0) {
            return j2;
        }
        ApasaraExternalPlayer.PlayerStatus playerStatus = this.f12060f;
        if ((playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PREPARED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PLAYING || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PAUSED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_STOPPED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_COMPLETION || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_IDLE || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_INITIALZED) && (mediaPlayer = this.f12057c) != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0L;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public long getPropertyInt(ApasaraExternalPlayer.PropertyKey propertyKey) {
        Logger.v("MediaPlayer", "getPropertyInt() " + propertyKey);
        return 0L;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public long getPropertyLong(ApasaraExternalPlayer.PropertyKey propertyKey) {
        Logger.v("MediaPlayer", "getPropertyLong() " + propertyKey);
        return 0L;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public String getPropertyString(ApasaraExternalPlayer.PropertyKey propertyKey) {
        Logger.v("MediaPlayer", "getPropertyString() " + propertyKey);
        return null;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public IPlayer.RotateMode getRotateMode() {
        Logger.v("MediaPlayer", "getRotateMode() ");
        return IPlayer.RotateMode.ROTATE_0;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public IPlayer.ScaleMode getScaleMode() {
        Logger.v("MediaPlayer", "getScaleMode() ");
        return IPlayer.ScaleMode.SCALE_TO_FILL;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public float getSpeed() {
        Logger.v("MediaPlayer", "getSpeed() ");
        return this.f12069o;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public float getVideoDecodeFps() {
        Logger.v("MediaPlayer", "getVideoDecodeFps() ");
        return 0.0f;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public int getVideoHeight() {
        MediaPlayer mediaPlayer;
        Logger.v("MediaPlayer", "getVideoHeight() ");
        if (this.f12060f == ApasaraExternalPlayer.PlayerStatus.PLAYER_ERROR || (mediaPlayer = this.f12057c) == null) {
            return 0;
        }
        return mediaPlayer.getVideoHeight();
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public float getVideoRenderFps() {
        Logger.v("MediaPlayer", "getVideoRenderFps() ");
        return 0.0f;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public int getVideoRotation() {
        Logger.v("MediaPlayer", "getVideoRotation() ");
        return 0;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public int getVideoWidth() {
        MediaPlayer mediaPlayer;
        Logger.v("MediaPlayer", "getVideoWidth() ");
        if (this.f12060f == ApasaraExternalPlayer.PlayerStatus.PLAYER_ERROR || (mediaPlayer = this.f12057c) == null) {
            return 0;
        }
        return mediaPlayer.getVideoWidth();
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public float getVolume() {
        Logger.v("MediaPlayer", "getVolume() ");
        return this.f12063i;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public int invokeComponent(String str) {
        Logger.v("MediaPlayer", "invokeComponent() " + str);
        return 0;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public boolean isAutoPlay() {
        Logger.v("MediaPlayer", "isAutoPlay() ");
        return this.f12070p;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public boolean isLooping() {
        Logger.v("MediaPlayer", "isLooping() ");
        MediaPlayer mediaPlayer = this.f12057c;
        if (mediaPlayer != null) {
            return mediaPlayer.isLooping();
        }
        return false;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public boolean isMute() {
        Logger.v("MediaPlayer", "isMute() ");
        return this.f12065k;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public boolean isSupport(Options options) {
        return options != null && "MediaPlayer".equals(options.get("name"));
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void mute(boolean z2) {
        MediaPlayer mediaPlayer;
        Logger.v("MediaPlayer", "mute() " + z2);
        this.f12065k = z2;
        if (this.f12060f == ApasaraExternalPlayer.PlayerStatus.PLAYER_ERROR || (mediaPlayer = this.f12057c) == null) {
            return;
        }
        float f2 = z2 ? 0.0f : this.f12063i;
        mediaPlayer.setVolume(f2, f2);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void pause() throws IllegalStateException {
        MediaPlayer mediaPlayer;
        Logger.v("MediaPlayer", "pause() ");
        ApasaraExternalPlayer.PlayerStatus playerStatus = this.f12060f;
        if ((playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PLAYING || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PAUSED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_COMPLETION) && (mediaPlayer = this.f12057c) != null) {
            mediaPlayer.pause();
            a(ApasaraExternalPlayer.PlayerStatus.PLAYER_PAUSED, true);
        }
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void prepare() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        Logger.v("MediaPlayer", "prepare() ");
        MediaPlayer mediaPlayer = this.f12057c;
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            c();
            setScaleMode(this.f12064j);
            setSpeed(this.f12069o);
            setLooping(this.f12066l);
            mute(this.f12065k);
            setVolume(this.f12063i);
            a(ApasaraExternalPlayer.PlayerStatus.PLAYER_PREPARING, true);
            this.f12057c.prepareAsync();
        }
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void reLoad() {
        Logger.v("MediaPlayer", "reLoad() ");
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void release() {
        Logger.v("MediaPlayer", "release() ");
        a(ApasaraExternalPlayer.PlayerStatus.PLAYER_IDLE, false);
        MediaPlayer mediaPlayer = this.f12057c;
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        this.f12057c = null;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void removeAllCustomHttpHeader() {
        Logger.v("MediaPlayer", "removeAllCustomHttpHeader() ");
        this.L.clear();
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void seekTo(long j2, boolean z2) throws IllegalStateException {
        Logger.v("MediaPlayer", "seekTo() " + j2 + " , accurate = " + z2);
        ApasaraExternalPlayer.PlayerStatus playerStatus = this.f12060f;
        if (playerStatus != ApasaraExternalPlayer.PlayerStatus.PLAYER_PREPARED && playerStatus != ApasaraExternalPlayer.PlayerStatus.PLAYER_PLAYING && playerStatus != ApasaraExternalPlayer.PlayerStatus.PLAYER_PAUSED && playerStatus != ApasaraExternalPlayer.PlayerStatus.PLAYER_COMPLETION) {
            this.J = j2;
            this.K = z2;
            return;
        }
        MediaPlayer mediaPlayer = this.f12057c;
        if (mediaPlayer != null) {
            if (Build.VERSION.SDK_INT < 26 || !z2) {
                mediaPlayer.seekTo((int) j2);
            } else {
                mediaPlayer.seekTo(j2, 3);
            }
            ApasaraExternalPlayer.OnSeekStatusListener onSeekStatusListener = this.f12077w;
            if (onSeekStatusListener != null) {
                onSeekStatusListener.onSeekStart(false);
            }
        }
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public int selectExtSubtitle(int i2, boolean z2) {
        Logger.v("MediaPlayer", "selectExtSubtitle() " + i2 + " , bSelect = " + z2);
        return 0;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setAutoPlay(boolean z2) {
        Logger.v("MediaPlayer", "setAutoPlay() " + z2);
        this.f12070p = z2;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setDataSource(String str) {
        Logger.v("MediaPlayer", "setDataSource() " + str);
        this.f12061g = str;
        if (this.f12057c != null) {
            a(ApasaraExternalPlayer.PlayerStatus.PLAYER_INITIALZED, true);
        }
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setDecoderType(ApasaraExternalPlayer.DecoderType decoderType) {
        Logger.v("MediaPlayer", "setDecoderType() " + decoderType);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setDrmCallback(ApasaraExternalPlayer.OnDRMCallback onDRMCallback) {
        this.I = onDRMCallback;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setDropBufferThreshold(int i2) {
        Logger.v("MediaPlayer", "setDropBufferThreshold() " + i2);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setLooping(boolean z2) {
        MediaPlayer mediaPlayer;
        Logger.v("MediaPlayer", "setLooping() " + z2);
        this.f12066l = z2;
        if (this.f12060f == ApasaraExternalPlayer.PlayerStatus.PLAYER_ERROR || (mediaPlayer = this.f12057c) == null) {
            return;
        }
        mediaPlayer.setLooping(z2);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setMirrorMode(IPlayer.MirrorMode mirrorMode) {
        Logger.v("MediaPlayer", "setMirrorMode() " + mirrorMode);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnAutoPlayStartListener(ApasaraExternalPlayer.OnAutoPlayStartListener onAutoPlayStartListener) {
        this.f12076v = onAutoPlayStartListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnBufferPositionUpdateListener(ApasaraExternalPlayer.OnBufferPositionUpdateListener onBufferPositionUpdateListener) {
        this.f12079y = onBufferPositionUpdateListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnCaptureScreenListener(ApasaraExternalPlayer.OnCaptureScreenListener onCaptureScreenListener) {
        this.G = onCaptureScreenListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnCompletionListener(ApasaraExternalPlayer.OnCompletionListener onCompletionListener) {
        this.f12073s = onCompletionListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnErrorListener(ApasaraExternalPlayer.OnErrorListener onErrorListener) {
        this.C = onErrorListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnEventListener(ApasaraExternalPlayer.OnEventListener onEventListener) {
        this.D = onEventListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnFirstFrameRenderListener(ApasaraExternalPlayer.OnFirstFrameRenderListener onFirstFrameRenderListener) {
        this.f12074t = onFirstFrameRenderListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnLoadStatusListener(ApasaraExternalPlayer.OnLoadStatusListener onLoadStatusListener) {
        this.f12075u = onLoadStatusListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnLoopingStartListener(ApasaraExternalPlayer.OnLoopingStartListener onLoopingStartListener) {
        this.f12072r = onLoopingStartListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnPositionUpdateListener(ApasaraExternalPlayer.OnPositionUpdateListener onPositionUpdateListener) {
        this.f12078x = onPositionUpdateListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnPreparedListener(ApasaraExternalPlayer.OnPreparedListener onPreparedListener) {
        this.f12071q = onPreparedListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnSeekStatusListener(ApasaraExternalPlayer.OnSeekStatusListener onSeekStatusListener) {
        this.f12077w = onSeekStatusListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnStatusChangedListener(ApasaraExternalPlayer.OnStatusChangedListener onStatusChangedListener) {
        this.A = onStatusChangedListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnStreamInfoGetListener(ApasaraExternalPlayer.OnStreamInfoGetListener onStreamInfoGetListener) {
        this.E = onStreamInfoGetListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnStreamSwitchSucListener(ApasaraExternalPlayer.OnStreamSwitchSucListener onStreamSwitchSucListener) {
        this.F = onStreamSwitchSucListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnSubtitleListener(ApasaraExternalPlayer.OnSubtitleListener onSubtitleListener) {
        this.H = onSubtitleListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnVideoRenderedListener(ApasaraExternalPlayer.OnVideoRenderedListener onVideoRenderedListener) {
        this.B = onVideoRenderedListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setOnVideoSizeChangedListener(ApasaraExternalPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.f12080z = onVideoSizeChangedListener;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public int setOption(String str, String str2) {
        Logger.v("MediaPlayer", "setOption() " + str + " : " + str2);
        return 0;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setRefer(String str) {
        Logger.v("MediaPlayer", "setRefer() " + str);
        this.f12067m = str;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setRotateMode(IPlayer.RotateMode rotateMode) {
        Logger.v("MediaPlayer", "setRotateMode() " + rotateMode);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setScaleMode(IPlayer.ScaleMode scaleMode) {
        MediaPlayer mediaPlayer;
        Logger.v("MediaPlayer", "setScaleMode() " + scaleMode);
        this.f12064j = scaleMode;
        ApasaraExternalPlayer.PlayerStatus playerStatus = this.f12060f;
        if (playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_ERROR || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_IDLE || (mediaPlayer = this.f12057c) == null) {
            return;
        }
        mediaPlayer.setVideoScalingMode(scaleMode == IPlayer.ScaleMode.SCALE_ASPECT_FILL ? 2 : 1);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setSpeed(float f2) {
        Logger.v("MediaPlayer", "setSpeed() " + f2);
        this.f12069o = f2;
        ApasaraExternalPlayer.PlayerStatus playerStatus = this.f12060f;
        if (playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_IDLE || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_STOPPED || this.f12057c == null) {
            return;
        }
        PlaybackParams playbackParams = new PlaybackParams();
        playbackParams.setSpeed(f2);
        this.f12057c.setPlaybackParams(playbackParams);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setSurface(Surface surface) {
        Logger.v("MediaPlayer", "setSurface() " + surface);
        MediaPlayer mediaPlayer = this.f12057c;
        if (mediaPlayer != null) {
            mediaPlayer.setSurface(surface);
        }
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setTimeout(int i2) {
        Logger.v("MediaPlayer", "setTimeout() " + i2);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setUserAgent(String str) {
        Logger.v("MediaPlayer", "setUserAgent() " + str);
        this.f12068n = str;
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setVideoBackgroundColor(long j2) {
        Logger.v("MediaPlayer", "setVideoBackgroundColor() " + j2);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void setVolume(float f2) {
        MediaPlayer mediaPlayer;
        Logger.v("MediaPlayer", "setVolume() " + f2);
        this.f12063i = f2;
        if (this.f12060f == ApasaraExternalPlayer.PlayerStatus.PLAYER_ERROR || this.f12065k || (mediaPlayer = this.f12057c) == null) {
            return;
        }
        mediaPlayer.setVolume(f2, f2);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void start() throws IllegalStateException {
        MediaPlayer mediaPlayer;
        Logger.v("MediaPlayer", "start() ");
        ApasaraExternalPlayer.PlayerStatus playerStatus = this.f12060f;
        if ((playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PREPARED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PLAYING || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PAUSED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_COMPLETION) && (mediaPlayer = this.f12057c) != null) {
            mediaPlayer.start();
            a(ApasaraExternalPlayer.PlayerStatus.PLAYER_PLAYING, true);
        }
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public void stop() throws IllegalStateException {
        MediaPlayer mediaPlayer;
        ApasaraExternalPlayer.PlayerStatus playerStatus;
        Logger.v("MediaPlayer", "stop() ");
        ApasaraExternalPlayer.PlayerStatus playerStatus2 = this.f12060f;
        if ((playerStatus2 != ApasaraExternalPlayer.PlayerStatus.PLAYER_PREPARED && playerStatus2 != ApasaraExternalPlayer.PlayerStatus.PLAYER_PLAYING && playerStatus2 != ApasaraExternalPlayer.PlayerStatus.PLAYER_PAUSED && playerStatus2 != ApasaraExternalPlayer.PlayerStatus.PLAYER_STOPPED && playerStatus2 != ApasaraExternalPlayer.PlayerStatus.PLAYER_COMPLETION) || (mediaPlayer = this.f12057c) == null || playerStatus2 == (playerStatus = ApasaraExternalPlayer.PlayerStatus.PLAYER_STOPPED)) {
            return;
        }
        mediaPlayer.stop();
        a(playerStatus, true);
    }

    @Override // com.aliyun.player.ApasaraExternalPlayer
    public ApasaraExternalPlayer.StreamType switchStream(int i2) throws IllegalStateException {
        MediaPlayer mediaPlayer;
        Logger.v("MediaPlayer", "switchStream() " + i2);
        ApasaraExternalPlayer.PlayerStatus playerStatus = this.f12060f;
        if ((playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PREPARED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PLAYING || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_PAUSED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_STOPPED || playerStatus == ApasaraExternalPlayer.PlayerStatus.PLAYER_COMPLETION) && (mediaPlayer = this.f12057c) != null) {
            mediaPlayer.selectTrack(i2);
            MediaPlayer.TrackInfo[] trackInfoArr = this.f12058d;
            if (trackInfoArr == null) {
                return ApasaraExternalPlayer.StreamType.ST_TYPE_UNKNOWN;
            }
            int trackType = trackInfoArr[i2].getTrackType();
            return trackType == 2 ? ApasaraExternalPlayer.StreamType.ST_TYPE_AUDIO : trackType == 4 ? ApasaraExternalPlayer.StreamType.ST_TYPE_SUB : trackType == 1 ? ApasaraExternalPlayer.StreamType.ST_TYPE_VIDEO : trackType == 0 ? ApasaraExternalPlayer.StreamType.ST_TYPE_UNKNOWN : ApasaraExternalPlayer.StreamType.ST_TYPE_UNKNOWN;
        }
        return ApasaraExternalPlayer.StreamType.ST_TYPE_UNKNOWN;
    }

    private a(Context context, Options options) {
        this.f12055a = 1000;
        this.f12056b = null;
        this.f12057c = null;
        this.f12058d = null;
        this.f12059e = null;
        this.f12060f = ApasaraExternalPlayer.PlayerStatus.PLAYER_IDLE;
        this.f12061g = null;
        this.f12062h = 0L;
        this.f12063i = 1.0f;
        this.f12064j = IPlayer.ScaleMode.SCALE_ASPECT_FIT;
        this.f12065k = false;
        this.f12066l = false;
        this.f12067m = null;
        this.f12068n = null;
        this.f12069o = 1.0f;
        this.f12070p = false;
        this.f12071q = null;
        this.f12072r = null;
        this.f12073s = null;
        this.f12074t = null;
        this.f12075u = null;
        this.f12076v = null;
        this.f12077w = null;
        this.f12078x = null;
        this.f12079y = null;
        this.f12080z = null;
        this.A = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.E = null;
        this.F = null;
        this.G = null;
        this.H = null;
        this.I = null;
        this.J = -1L;
        this.K = false;
        this.L = new HashMap();
        this.f12056b = context;
        Looper looperMyLooper = Looper.myLooper();
        this.f12059e = new HandlerC0098a(looperMyLooper == null ? Looper.getMainLooper() : looperMyLooper);
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.f12057c = mediaPlayer;
        mediaPlayer.setOnBufferingUpdateListener(new b());
        this.f12057c.setOnCompletionListener(new c());
        this.f12057c.setOnErrorListener(new d());
        this.f12057c.setOnInfoListener(new e());
        this.f12057c.setOnPreparedListener(new f());
        this.f12057c.setOnSeekCompleteListener(new g());
        this.f12057c.setOnTimedTextListener(new h());
        this.f12057c.setOnVideoSizeChangedListener(new i());
    }

    private void c() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (this.f12057c == null) {
            return;
        }
        HashMap map = new HashMap();
        String str = this.f12067m;
        if (str != null) {
            map.put(HttpHeaders.REFERER, str);
        }
        String str2 = this.f12068n;
        if (str2 != null) {
            map.put("User-Agent", str2);
        }
        map.putAll(this.L);
        try {
            this.f12057c.setDataSource(this.f12056b, Uri.parse(this.f12061g), map);
        } catch (IOException e2) {
            e2.printStackTrace();
            ApasaraExternalPlayer.OnErrorListener onErrorListener = this.C;
            if (onErrorListener != null) {
                onErrorListener.onError(ErrorCode.ERROR_GENERAL_EIO.getValue(), "set dataSource error :" + e2.getMessage());
            }
        }
    }

    private TrackInfo a(MediaPlayer.TrackInfo trackInfo, int i2) {
        TrackInfo trackInfo2 = new TrackInfo();
        trackInfo2.index = i2;
        int trackType = trackInfo.getTrackType();
        trackInfo2.mType = trackType == 2 ? TrackInfo.Type.TYPE_AUDIO : trackType == 1 ? TrackInfo.Type.TYPE_VIDEO : trackType == 4 ? TrackInfo.Type.TYPE_SUBTITLE : TrackInfo.Type.TYPE_VOD;
        trackInfo2.description = trackInfo.getLanguage();
        return trackInfo2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        this.f12059e.removeMessages(1000);
        if (this.f12060f.getValue() < ApasaraExternalPlayer.PlayerStatus.PLAYER_PREPARED.getValue() || this.f12060f.getValue() > ApasaraExternalPlayer.PlayerStatus.PLAYER_STOPPED.getValue()) {
            return;
        }
        this.f12059e.sendEmptyMessageDelayed(1000, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        if (this.E == null || this.f12058d == null) {
            return;
        }
        MediaInfo mediaInfo = new MediaInfo();
        TrackInfo[] trackInfoArr = new TrackInfo[this.f12058d.length];
        int i2 = 0;
        while (true) {
            MediaPlayer.TrackInfo[] trackInfoArr2 = this.f12058d;
            if (i2 >= trackInfoArr2.length) {
                mediaInfo.setTrackInfos(trackInfoArr);
                mediaInfo.setDuration((int) getDuration());
                this.E.OnStreamInfoGet(mediaInfo);
                return;
            }
            trackInfoArr[i2] = a(trackInfoArr2[i2], i2);
            i2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ApasaraExternalPlayer.PlayerStatus playerStatus, boolean z2) {
        ApasaraExternalPlayer.OnStatusChangedListener onStatusChangedListener;
        if (this.f12060f != playerStatus) {
            this.f12060f = playerStatus;
            if (z2 && (onStatusChangedListener = this.A) != null) {
                onStatusChangedListener.onStatusChanged(playerStatus.getValue(), playerStatus.getValue());
            }
        }
        b();
    }
}
