package androidx.media3.exoplayer;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.AuxEffectInfo;
import androidx.media3.common.C;
import androidx.media3.common.DeviceInfo;
import androidx.media3.common.Effect;
import androidx.media3.common.Format;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.PriorityTaskManager;
import androidx.media3.common.VideoSize;
import androidx.media3.common.text.CueGroup;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Clock;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.DefaultLivePlaybackSpeedControl;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.PlayerMessage;
import androidx.media3.exoplayer.analytics.AnalyticsCollector;
import androidx.media3.exoplayer.analytics.AnalyticsListener;
import androidx.media3.exoplayer.analytics.DefaultAnalyticsCollector;
import androidx.media3.exoplayer.image.ImageOutput;
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ShuffleOrder;
import androidx.media3.exoplayer.source.TrackGroupArray;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import androidx.media3.exoplayer.trackselection.TrackSelectionArray;
import androidx.media3.exoplayer.trackselection.TrackSelector;
import androidx.media3.exoplayer.upstream.BandwidthMeter;
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter;
import androidx.media3.exoplayer.video.VideoFrameMetadataListener;
import androidx.media3.exoplayer.video.spherical.CameraMotionListener;
import androidx.media3.extractor.DefaultExtractorsFactory;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.List;

/* loaded from: classes.dex */
public interface ExoPlayer extends Player {

    @UnstableApi
    public static final long DEFAULT_DETACH_SURFACE_TIMEOUT_MS = 2000;

    @UnstableApi
    public static final long DEFAULT_RELEASE_TIMEOUT_MS = 500;

    @UnstableApi
    @Deprecated
    public interface AudioComponent {
        @Deprecated
        void clearAuxEffectInfo();

        @Deprecated
        AudioAttributes getAudioAttributes();

        @Deprecated
        int getAudioSessionId();

        @Deprecated
        boolean getSkipSilenceEnabled();

        @Deprecated
        float getVolume();

        @Deprecated
        void setAudioAttributes(AudioAttributes audioAttributes, boolean z2);

        @Deprecated
        void setAudioSessionId(int i2);

        @Deprecated
        void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

        @Deprecated
        void setSkipSilenceEnabled(boolean z2);

        @Deprecated
        void setVolume(float f2);
    }

    @UnstableApi
    public interface AudioOffloadListener {
        void onOffloadedPlayback(boolean z2);

        void onSleepingForOffloadChanged(boolean z2);
    }

    public static final class Builder {
        long A;
        long B;
        boolean C;
        boolean D;
        Looper E;
        boolean F;
        boolean G;
        String H;
        boolean I;

        /* renamed from: a, reason: collision with root package name */
        final Context f4895a;

        /* renamed from: b, reason: collision with root package name */
        Clock f4896b;

        /* renamed from: c, reason: collision with root package name */
        long f4897c;

        /* renamed from: d, reason: collision with root package name */
        Supplier f4898d;

        /* renamed from: e, reason: collision with root package name */
        Supplier f4899e;

        /* renamed from: f, reason: collision with root package name */
        Supplier f4900f;

        /* renamed from: g, reason: collision with root package name */
        Supplier f4901g;

        /* renamed from: h, reason: collision with root package name */
        Supplier f4902h;

        /* renamed from: i, reason: collision with root package name */
        Function f4903i;

        /* renamed from: j, reason: collision with root package name */
        Looper f4904j;

        /* renamed from: k, reason: collision with root package name */
        int f4905k;

        /* renamed from: l, reason: collision with root package name */
        PriorityTaskManager f4906l;

        /* renamed from: m, reason: collision with root package name */
        AudioAttributes f4907m;

        /* renamed from: n, reason: collision with root package name */
        boolean f4908n;

        /* renamed from: o, reason: collision with root package name */
        int f4909o;

        /* renamed from: p, reason: collision with root package name */
        boolean f4910p;

        /* renamed from: q, reason: collision with root package name */
        boolean f4911q;

        /* renamed from: r, reason: collision with root package name */
        boolean f4912r;

        /* renamed from: s, reason: collision with root package name */
        int f4913s;

        /* renamed from: t, reason: collision with root package name */
        int f4914t;

        /* renamed from: u, reason: collision with root package name */
        boolean f4915u;

        /* renamed from: v, reason: collision with root package name */
        SeekParameters f4916v;

        /* renamed from: w, reason: collision with root package name */
        long f4917w;

        /* renamed from: x, reason: collision with root package name */
        long f4918x;

        /* renamed from: y, reason: collision with root package name */
        long f4919y;

        /* renamed from: z, reason: collision with root package name */
        LivePlaybackSpeedControl f4920z;

        public Builder(final Context context) {
            this(context, (Supplier<RenderersFactory>) new Supplier() { // from class: androidx.media3.exoplayer.q
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$0(context);
                }
            }, (Supplier<MediaSource.Factory>) new Supplier() { // from class: androidx.media3.exoplayer.s
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$1(context);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$0(Context context) {
            return new DefaultRenderersFactory(context);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSource.Factory lambda$new$1(Context context) {
            return new DefaultMediaSourceFactory(context, new DefaultExtractorsFactory());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ TrackSelector lambda$new$10(TrackSelector trackSelector) {
            return trackSelector;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ LoadControl lambda$new$11(LoadControl loadControl) {
            return loadControl;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ BandwidthMeter lambda$new$12(BandwidthMeter bandwidthMeter) {
            return bandwidthMeter;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ AnalyticsCollector lambda$new$13(AnalyticsCollector analyticsCollector, Clock clock) {
            return analyticsCollector;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ TrackSelector lambda$new$14(Context context) {
            return new DefaultTrackSelector(context);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$2(RenderersFactory renderersFactory) {
            return renderersFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSource.Factory lambda$new$3(Context context) {
            return new DefaultMediaSourceFactory(context, new DefaultExtractorsFactory());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$4(Context context) {
            return new DefaultRenderersFactory(context);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSource.Factory lambda$new$5(MediaSource.Factory factory) {
            return factory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$6(RenderersFactory renderersFactory) {
            return renderersFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSource.Factory lambda$new$7(MediaSource.Factory factory) {
            return factory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$8(RenderersFactory renderersFactory) {
            return renderersFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSource.Factory lambda$new$9(MediaSource.Factory factory) {
            return factory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ AnalyticsCollector lambda$setAnalyticsCollector$21(AnalyticsCollector analyticsCollector, Clock clock) {
            return analyticsCollector;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ BandwidthMeter lambda$setBandwidthMeter$20(BandwidthMeter bandwidthMeter) {
            return bandwidthMeter;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ LoadControl lambda$setLoadControl$19(LoadControl loadControl) {
            return loadControl;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSource.Factory lambda$setMediaSourceFactory$17(MediaSource.Factory factory) {
            return factory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$setRenderersFactory$16(RenderersFactory renderersFactory) {
            return renderersFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ TrackSelector lambda$setTrackSelector$18(TrackSelector trackSelector) {
            return trackSelector;
        }

        public ExoPlayer build() {
            Assertions.checkState(!this.F);
            this.F = true;
            return new ExoPlayerImpl(this, null);
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder experimentalSetDynamicSchedulingEnabled(boolean z2) {
            Assertions.checkState(!this.F);
            this.I = z2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder experimentalSetForegroundModeTimeoutMs(long j2) {
            Assertions.checkState(!this.F);
            this.f4897c = j2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setAnalyticsCollector(final AnalyticsCollector analyticsCollector) {
            Assertions.checkState(!this.F);
            Assertions.checkNotNull(analyticsCollector);
            this.f4903i = new Function() { // from class: androidx.media3.exoplayer.p
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    return ExoPlayer.Builder.lambda$setAnalyticsCollector$21(analyticsCollector, (Clock) obj);
                }
            };
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setAudioAttributes(AudioAttributes audioAttributes, boolean z2) {
            Assertions.checkState(!this.F);
            this.f4907m = (AudioAttributes) Assertions.checkNotNull(audioAttributes);
            this.f4908n = z2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setBandwidthMeter(final BandwidthMeter bandwidthMeter) {
            Assertions.checkState(!this.F);
            Assertions.checkNotNull(bandwidthMeter);
            this.f4902h = new Supplier() { // from class: androidx.media3.exoplayer.a0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$setBandwidthMeter$20(bandwidthMeter);
                }
            };
            return this;
        }

        @CanIgnoreReturnValue
        @VisibleForTesting
        @UnstableApi
        public Builder setClock(Clock clock) {
            Assertions.checkState(!this.F);
            this.f4896b = clock;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setDetachSurfaceTimeoutMs(long j2) {
            Assertions.checkState(!this.F);
            this.B = j2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setDeviceVolumeControlEnabled(boolean z2) {
            Assertions.checkState(!this.F);
            this.f4912r = z2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setHandleAudioBecomingNoisy(boolean z2) {
            Assertions.checkState(!this.F);
            this.f4910p = z2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setLivePlaybackSpeedControl(LivePlaybackSpeedControl livePlaybackSpeedControl) {
            Assertions.checkState(!this.F);
            this.f4920z = (LivePlaybackSpeedControl) Assertions.checkNotNull(livePlaybackSpeedControl);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setLoadControl(final LoadControl loadControl) {
            Assertions.checkState(!this.F);
            Assertions.checkNotNull(loadControl);
            this.f4901g = new Supplier() { // from class: androidx.media3.exoplayer.o
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$setLoadControl$19(loadControl);
                }
            };
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setLooper(Looper looper) {
            Assertions.checkState(!this.F);
            Assertions.checkNotNull(looper);
            this.f4904j = looper;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setMaxSeekToPreviousPositionMs(@IntRange(from = 0) long j2) {
            Assertions.checkArgument(j2 >= 0);
            Assertions.checkState(!this.F);
            this.f4919y = j2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setMediaSourceFactory(final MediaSource.Factory factory) {
            Assertions.checkState(!this.F);
            Assertions.checkNotNull(factory);
            this.f4899e = new Supplier() { // from class: androidx.media3.exoplayer.m0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$setMediaSourceFactory$17(factory);
                }
            };
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setName(String str) {
            Assertions.checkState(!this.F);
            this.H = str;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setPauseAtEndOfMediaItems(boolean z2) {
            Assertions.checkState(!this.F);
            this.C = z2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setPlaybackLooper(Looper looper) {
            Assertions.checkState(!this.F);
            this.E = looper;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setPriority(int i2) {
            Assertions.checkState(!this.F);
            this.f4905k = i2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager) {
            Assertions.checkState(!this.F);
            this.f4906l = priorityTaskManager;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setReleaseTimeoutMs(long j2) {
            Assertions.checkState(!this.F);
            this.A = j2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setRenderersFactory(final RenderersFactory renderersFactory) {
            Assertions.checkState(!this.F);
            Assertions.checkNotNull(renderersFactory);
            this.f4898d = new Supplier() { // from class: androidx.media3.exoplayer.t
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$setRenderersFactory$16(renderersFactory);
                }
            };
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setSeekBackIncrementMs(@IntRange(from = 1) long j2) {
            Assertions.checkArgument(j2 > 0);
            Assertions.checkState(!this.F);
            this.f4917w = j2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setSeekForwardIncrementMs(@IntRange(from = 1) long j2) {
            Assertions.checkArgument(j2 > 0);
            Assertions.checkState(!this.F);
            this.f4918x = j2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setSeekParameters(SeekParameters seekParameters) {
            Assertions.checkState(!this.F);
            this.f4916v = (SeekParameters) Assertions.checkNotNull(seekParameters);
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setSkipSilenceEnabled(boolean z2) {
            Assertions.checkState(!this.F);
            this.f4911q = z2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setSuppressPlaybackOnUnsuitableOutput(boolean z2) {
            Assertions.checkState(!this.F);
            this.G = z2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setTrackSelector(final TrackSelector trackSelector) {
            Assertions.checkState(!this.F);
            Assertions.checkNotNull(trackSelector);
            this.f4900f = new Supplier() { // from class: androidx.media3.exoplayer.l0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$setTrackSelector$18(trackSelector);
                }
            };
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setUseLazyPreparation(boolean z2) {
            Assertions.checkState(!this.F);
            this.f4915u = z2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setUsePlatformDiagnostics(boolean z2) {
            Assertions.checkState(!this.F);
            this.D = z2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setVideoChangeFrameRateStrategy(int i2) {
            Assertions.checkState(!this.F);
            this.f4914t = i2;
            return this;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Builder setVideoScalingMode(int i2) {
            Assertions.checkState(!this.F);
            this.f4913s = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setWakeMode(int i2) {
            Assertions.checkState(!this.F);
            this.f4909o = i2;
            return this;
        }

        SimpleExoPlayer w() {
            Assertions.checkState(!this.F);
            this.F = true;
            return new SimpleExoPlayer(this);
        }

        @UnstableApi
        public Builder(final Context context, final RenderersFactory renderersFactory) {
            this(context, (Supplier<RenderersFactory>) new Supplier() { // from class: androidx.media3.exoplayer.w
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$2(renderersFactory);
                }
            }, (Supplier<MediaSource.Factory>) new Supplier() { // from class: androidx.media3.exoplayer.x
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$3(context);
                }
            });
            Assertions.checkNotNull(renderersFactory);
        }

        @UnstableApi
        public Builder(final Context context, final MediaSource.Factory factory) {
            this(context, (Supplier<RenderersFactory>) new Supplier() { // from class: androidx.media3.exoplayer.j0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$4(context);
                }
            }, (Supplier<MediaSource.Factory>) new Supplier() { // from class: androidx.media3.exoplayer.k0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$5(factory);
                }
            });
            Assertions.checkNotNull(factory);
        }

        @UnstableApi
        public Builder(Context context, final RenderersFactory renderersFactory, final MediaSource.Factory factory) {
            this(context, (Supplier<RenderersFactory>) new Supplier() { // from class: androidx.media3.exoplayer.u
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$6(renderersFactory);
                }
            }, (Supplier<MediaSource.Factory>) new Supplier() { // from class: androidx.media3.exoplayer.v
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$7(factory);
                }
            });
            Assertions.checkNotNull(renderersFactory);
            Assertions.checkNotNull(factory);
        }

        @UnstableApi
        public Builder(Context context, final RenderersFactory renderersFactory, final MediaSource.Factory factory, final TrackSelector trackSelector, final LoadControl loadControl, final BandwidthMeter bandwidthMeter, final AnalyticsCollector analyticsCollector) {
            this(context, (Supplier<RenderersFactory>) new Supplier() { // from class: androidx.media3.exoplayer.y
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$8(renderersFactory);
                }
            }, (Supplier<MediaSource.Factory>) new Supplier() { // from class: androidx.media3.exoplayer.z
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$9(factory);
                }
            }, (Supplier<TrackSelector>) new Supplier() { // from class: androidx.media3.exoplayer.b0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$10(trackSelector);
                }
            }, (Supplier<LoadControl>) new Supplier() { // from class: androidx.media3.exoplayer.c0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$11(loadControl);
                }
            }, (Supplier<BandwidthMeter>) new Supplier() { // from class: androidx.media3.exoplayer.d0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$12(bandwidthMeter);
                }
            }, (Function<Clock, AnalyticsCollector>) new Function() { // from class: androidx.media3.exoplayer.e0
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    return ExoPlayer.Builder.lambda$new$13(analyticsCollector, (Clock) obj);
                }
            });
            Assertions.checkNotNull(renderersFactory);
            Assertions.checkNotNull(factory);
            Assertions.checkNotNull(trackSelector);
            Assertions.checkNotNull(bandwidthMeter);
            Assertions.checkNotNull(analyticsCollector);
        }

        private Builder(final Context context, Supplier<RenderersFactory> supplier, Supplier<MediaSource.Factory> supplier2) {
            this(context, supplier, supplier2, (Supplier<TrackSelector>) new Supplier() { // from class: androidx.media3.exoplayer.f0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$14(context);
                }
            }, (Supplier<LoadControl>) new Supplier() { // from class: androidx.media3.exoplayer.g0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return new DefaultLoadControl();
                }
            }, (Supplier<BandwidthMeter>) new Supplier() { // from class: androidx.media3.exoplayer.h0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return DefaultBandwidthMeter.getSingletonInstance(context);
                }
            }, (Function<Clock, AnalyticsCollector>) new Function() { // from class: androidx.media3.exoplayer.i0
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    return new DefaultAnalyticsCollector((Clock) obj);
                }
            });
        }

        private Builder(Context context, Supplier<RenderersFactory> supplier, Supplier<MediaSource.Factory> supplier2, Supplier<TrackSelector> supplier3, Supplier<LoadControl> supplier4, Supplier<BandwidthMeter> supplier5, Function<Clock, AnalyticsCollector> function) {
            this.f4895a = (Context) Assertions.checkNotNull(context);
            this.f4898d = supplier;
            this.f4899e = supplier2;
            this.f4900f = supplier3;
            this.f4901g = supplier4;
            this.f4902h = supplier5;
            this.f4903i = function;
            this.f4904j = Util.getCurrentOrMainLooper();
            this.f4907m = AudioAttributes.DEFAULT;
            this.f4909o = 0;
            this.f4913s = 1;
            this.f4914t = 0;
            this.f4915u = true;
            this.f4916v = SeekParameters.DEFAULT;
            this.f4917w = 5000L;
            this.f4918x = C.DEFAULT_SEEK_FORWARD_INCREMENT_MS;
            this.f4919y = 3000L;
            this.f4920z = new DefaultLivePlaybackSpeedControl.Builder().build();
            this.f4896b = Clock.DEFAULT;
            this.A = 500L;
            this.B = ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS;
            this.D = true;
            this.H = "";
            this.f4905k = -1000;
        }
    }

    @UnstableApi
    @Deprecated
    public interface DeviceComponent {
        @Deprecated
        void decreaseDeviceVolume();

        @Deprecated
        DeviceInfo getDeviceInfo();

        @Deprecated
        int getDeviceVolume();

        @Deprecated
        void increaseDeviceVolume();

        @Deprecated
        boolean isDeviceMuted();

        @Deprecated
        void setDeviceMuted(boolean z2);

        @Deprecated
        void setDeviceVolume(int i2);
    }

    @UnstableApi
    public static class PreloadConfiguration {
        public static final PreloadConfiguration DEFAULT = new PreloadConfiguration(C.TIME_UNSET);
        public final long targetPreloadDurationUs;

        public PreloadConfiguration(long j2) {
            this.targetPreloadDurationUs = j2;
        }
    }

    @UnstableApi
    @Deprecated
    public interface TextComponent {
        @Deprecated
        CueGroup getCurrentCues();
    }

    @UnstableApi
    @Deprecated
    public interface VideoComponent {
        @Deprecated
        void clearCameraMotionListener(CameraMotionListener cameraMotionListener);

        @Deprecated
        void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

        @Deprecated
        void clearVideoSurface();

        @Deprecated
        void clearVideoSurface(@Nullable Surface surface);

        @Deprecated
        void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

        @Deprecated
        void clearVideoSurfaceView(@Nullable SurfaceView surfaceView);

        @Deprecated
        void clearVideoTextureView(@Nullable TextureView textureView);

        @Deprecated
        int getVideoChangeFrameRateStrategy();

        @Deprecated
        int getVideoScalingMode();

        @Deprecated
        VideoSize getVideoSize();

        @Deprecated
        void setCameraMotionListener(CameraMotionListener cameraMotionListener);

        @Deprecated
        void setVideoChangeFrameRateStrategy(int i2);

        @Deprecated
        void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

        @Deprecated
        void setVideoScalingMode(int i2);

        @Deprecated
        void setVideoSurface(@Nullable Surface surface);

        @Deprecated
        void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

        @Deprecated
        void setVideoSurfaceView(@Nullable SurfaceView surfaceView);

        @Deprecated
        void setVideoTextureView(@Nullable TextureView textureView);
    }

    void addAnalyticsListener(AnalyticsListener analyticsListener);

    @UnstableApi
    void addAudioOffloadListener(AudioOffloadListener audioOffloadListener);

    @UnstableApi
    void addMediaSource(int i2, MediaSource mediaSource);

    @UnstableApi
    void addMediaSource(MediaSource mediaSource);

    @UnstableApi
    void addMediaSources(int i2, List<MediaSource> list);

    @UnstableApi
    void addMediaSources(List<MediaSource> list);

    @UnstableApi
    void clearAuxEffectInfo();

    @UnstableApi
    void clearCameraMotionListener(CameraMotionListener cameraMotionListener);

    @UnstableApi
    void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

    @UnstableApi
    PlayerMessage createMessage(PlayerMessage.Target target);

    @UnstableApi
    AnalyticsCollector getAnalyticsCollector();

    @Nullable
    @UnstableApi
    @Deprecated
    AudioComponent getAudioComponent();

    @Nullable
    @UnstableApi
    DecoderCounters getAudioDecoderCounters();

    @Nullable
    @UnstableApi
    Format getAudioFormat();

    @UnstableApi
    int getAudioSessionId();

    @UnstableApi
    Clock getClock();

    @UnstableApi
    @Deprecated
    TrackGroupArray getCurrentTrackGroups();

    @UnstableApi
    @Deprecated
    TrackSelectionArray getCurrentTrackSelections();

    @Nullable
    @UnstableApi
    @Deprecated
    DeviceComponent getDeviceComponent();

    @UnstableApi
    boolean getPauseAtEndOfMediaItems();

    @UnstableApi
    Looper getPlaybackLooper();

    @Override // androidx.media3.common.Player
    @Nullable
    /* bridge */ /* synthetic */ PlaybackException getPlayerError();

    @Override // androidx.media3.common.Player
    @Nullable
    ExoPlaybackException getPlayerError();

    @UnstableApi
    PreloadConfiguration getPreloadConfiguration();

    @UnstableApi
    Renderer getRenderer(int i2);

    @UnstableApi
    int getRendererCount();

    @UnstableApi
    int getRendererType(int i2);

    @UnstableApi
    SeekParameters getSeekParameters();

    @UnstableApi
    boolean getSkipSilenceEnabled();

    @Nullable
    @UnstableApi
    @Deprecated
    TextComponent getTextComponent();

    @Nullable
    @UnstableApi
    TrackSelector getTrackSelector();

    @UnstableApi
    int getVideoChangeFrameRateStrategy();

    @Nullable
    @UnstableApi
    @Deprecated
    VideoComponent getVideoComponent();

    @Nullable
    @UnstableApi
    DecoderCounters getVideoDecoderCounters();

    @Nullable
    @UnstableApi
    Format getVideoFormat();

    @UnstableApi
    int getVideoScalingMode();

    @UnstableApi
    boolean isReleased();

    @UnstableApi
    boolean isSleepingForOffload();

    @UnstableApi
    boolean isTunnelingEnabled();

    @UnstableApi
    @Deprecated
    void prepare(MediaSource mediaSource);

    @UnstableApi
    @Deprecated
    void prepare(MediaSource mediaSource, boolean z2, boolean z3);

    @Override // androidx.media3.common.Player
    void release();

    void removeAnalyticsListener(AnalyticsListener analyticsListener);

    @UnstableApi
    void removeAudioOffloadListener(AudioOffloadListener audioOffloadListener);

    @Override // androidx.media3.common.Player
    void replaceMediaItem(int i2, MediaItem mediaItem);

    @Override // androidx.media3.common.Player
    void replaceMediaItems(int i2, int i3, List<MediaItem> list);

    @UnstableApi
    void setAudioSessionId(int i2);

    @UnstableApi
    void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

    @UnstableApi
    void setCameraMotionListener(CameraMotionListener cameraMotionListener);

    @UnstableApi
    void setForegroundMode(boolean z2);

    void setHandleAudioBecomingNoisy(boolean z2);

    @UnstableApi
    void setImageOutput(@Nullable ImageOutput imageOutput);

    @UnstableApi
    void setMediaSource(MediaSource mediaSource);

    @UnstableApi
    void setMediaSource(MediaSource mediaSource, long j2);

    @UnstableApi
    void setMediaSource(MediaSource mediaSource, boolean z2);

    @UnstableApi
    void setMediaSources(List<MediaSource> list);

    @UnstableApi
    void setMediaSources(List<MediaSource> list, int i2, long j2);

    @UnstableApi
    void setMediaSources(List<MediaSource> list, boolean z2);

    @UnstableApi
    void setPauseAtEndOfMediaItems(boolean z2);

    @RequiresApi(23)
    @UnstableApi
    void setPreferredAudioDevice(@Nullable AudioDeviceInfo audioDeviceInfo);

    @UnstableApi
    void setPreloadConfiguration(PreloadConfiguration preloadConfiguration);

    @UnstableApi
    void setPriority(int i2);

    @UnstableApi
    void setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager);

    @UnstableApi
    void setSeekParameters(@Nullable SeekParameters seekParameters);

    @UnstableApi
    void setShuffleOrder(ShuffleOrder shuffleOrder);

    @UnstableApi
    void setSkipSilenceEnabled(boolean z2);

    @UnstableApi
    void setVideoChangeFrameRateStrategy(int i2);

    @UnstableApi
    void setVideoEffects(List<Effect> list);

    @UnstableApi
    void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

    @UnstableApi
    void setVideoScalingMode(int i2);

    void setWakeMode(int i2);
}
