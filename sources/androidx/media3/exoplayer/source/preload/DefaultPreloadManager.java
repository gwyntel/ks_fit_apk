package androidx.media3.exoplayer.source.preload;

import android.os.Looper;
import androidx.media3.common.C;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.RendererCapabilitiesList;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.preload.DefaultPreloadManager;
import androidx.media3.exoplayer.source.preload.PreloadMediaSource;
import androidx.media3.exoplayer.source.preload.TargetPreloadStatusControl;
import androidx.media3.exoplayer.trackselection.TrackSelector;
import androidx.media3.exoplayer.upstream.Allocator;
import androidx.media3.exoplayer.upstream.BandwidthMeter;
import com.google.common.base.Predicate;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Comparator;

@UnstableApi
/* loaded from: classes2.dex */
public final class DefaultPreloadManager extends BasePreloadManager<Integer> {
    private final PreloadMediaSource.Factory preloadMediaSourceFactory;
    private final RendererCapabilitiesList rendererCapabilitiesList;

    private static final class RankingDataComparator implements Comparator<Integer> {
        public int currentPlayingIndex = -1;

        @Override // java.util.Comparator
        public int compare(Integer num, Integer num2) {
            return Integer.compare(Math.abs(num.intValue() - this.currentPlayingIndex), Math.abs(num2.intValue() - this.currentPlayingIndex));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SourcePreloadControl implements PreloadMediaSource.PreloadControl {
        private SourcePreloadControl() {
        }

        private boolean continueOrCompletePreloading(PreloadMediaSource preloadMediaSource, Predicate<Status> predicate, boolean z2) {
            TargetPreloadStatusControl.PreloadStatus preloadStatusC = DefaultPreloadManager.this.c(preloadMediaSource);
            if (preloadStatusC != null) {
                if (predicate.apply((Status) Assertions.checkNotNull((Status) preloadStatusC))) {
                    return true;
                }
                if (z2) {
                    DefaultPreloadManager.this.b(preloadMediaSource);
                }
            }
            DefaultPreloadManager.this.d(preloadMediaSource);
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$onContinueLoadingRequested$2(long j2, Status status) {
            return status.getStage() == 2 && status.getValue() > Util.usToMs(j2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$onSourcePrepared$0(Status status) {
            return status.getStage() > 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$onTracksSelected$1(Status status) {
            return status.getStage() > 1;
        }

        @Override // androidx.media3.exoplayer.source.preload.PreloadMediaSource.PreloadControl
        public boolean onContinueLoadingRequested(PreloadMediaSource preloadMediaSource, final long j2) {
            return continueOrCompletePreloading(preloadMediaSource, new Predicate() { // from class: androidx.media3.exoplayer.source.preload.d
                @Override // com.google.common.base.Predicate
                public final boolean apply(Object obj) {
                    return DefaultPreloadManager.SourcePreloadControl.lambda$onContinueLoadingRequested$2(j2, (DefaultPreloadManager.Status) obj);
                }
            }, false);
        }

        @Override // androidx.media3.exoplayer.source.preload.PreloadMediaSource.PreloadControl
        public void onLoadedToTheEndOfSource(PreloadMediaSource preloadMediaSource) {
            DefaultPreloadManager.this.d(preloadMediaSource);
        }

        @Override // androidx.media3.exoplayer.source.preload.PreloadMediaSource.PreloadControl
        public boolean onSourcePrepared(PreloadMediaSource preloadMediaSource) {
            return continueOrCompletePreloading(preloadMediaSource, new Predicate() { // from class: androidx.media3.exoplayer.source.preload.c
                @Override // com.google.common.base.Predicate
                public final boolean apply(Object obj) {
                    return DefaultPreloadManager.SourcePreloadControl.lambda$onSourcePrepared$0((DefaultPreloadManager.Status) obj);
                }
            }, true);
        }

        @Override // androidx.media3.exoplayer.source.preload.PreloadMediaSource.PreloadControl
        public boolean onTracksSelected(PreloadMediaSource preloadMediaSource) {
            return continueOrCompletePreloading(preloadMediaSource, new Predicate() { // from class: androidx.media3.exoplayer.source.preload.b
                @Override // com.google.common.base.Predicate
                public final boolean apply(Object obj) {
                    return DefaultPreloadManager.SourcePreloadControl.lambda$onTracksSelected$1((DefaultPreloadManager.Status) obj);
                }
            }, false);
        }

        @Override // androidx.media3.exoplayer.source.preload.PreloadMediaSource.PreloadControl
        public void onUsedByPlayer(PreloadMediaSource preloadMediaSource) {
            DefaultPreloadManager.this.d(preloadMediaSource);
        }
    }

    public DefaultPreloadManager(TargetPreloadStatusControl<Integer> targetPreloadStatusControl, MediaSource.Factory factory, TrackSelector trackSelector, BandwidthMeter bandwidthMeter, RendererCapabilitiesList.Factory factory2, Allocator allocator, Looper looper) {
        super(new RankingDataComparator(), targetPreloadStatusControl, factory);
        RendererCapabilitiesList rendererCapabilitiesListCreateRendererCapabilitiesList = factory2.createRendererCapabilitiesList();
        this.rendererCapabilitiesList = rendererCapabilitiesListCreateRendererCapabilitiesList;
        this.preloadMediaSourceFactory = new PreloadMediaSource.Factory(factory, new SourcePreloadControl(), trackSelector, bandwidthMeter, rendererCapabilitiesListCreateRendererCapabilitiesList.getRendererCapabilities(), allocator, looper);
    }

    @Override // androidx.media3.exoplayer.source.preload.BasePreloadManager
    protected void b(MediaSource mediaSource) {
        Assertions.checkArgument(mediaSource instanceof PreloadMediaSource);
        ((PreloadMediaSource) mediaSource).clear();
    }

    @Override // androidx.media3.exoplayer.source.preload.BasePreloadManager
    public MediaSource createMediaSourceForPreloading(MediaSource mediaSource) {
        return this.preloadMediaSourceFactory.createMediaSource(mediaSource);
    }

    @Override // androidx.media3.exoplayer.source.preload.BasePreloadManager
    protected void e(MediaSource mediaSource, long j2) {
        Assertions.checkArgument(mediaSource instanceof PreloadMediaSource);
        ((PreloadMediaSource) mediaSource).preload(j2);
    }

    @Override // androidx.media3.exoplayer.source.preload.BasePreloadManager
    protected void f() {
        this.rendererCapabilitiesList.release();
    }

    @Override // androidx.media3.exoplayer.source.preload.BasePreloadManager
    protected void g(MediaSource mediaSource) {
        Assertions.checkArgument(mediaSource instanceof PreloadMediaSource);
        ((PreloadMediaSource) mediaSource).releasePreloadMediaSource();
    }

    public void setCurrentPlayingIndex(int i2) {
        ((RankingDataComparator) this.f5456a).currentPlayingIndex = i2;
    }

    public static class Status implements TargetPreloadStatusControl.PreloadStatus {
        public static final int STAGE_LOADED_TO_POSITION_MS = 2;
        public static final int STAGE_SOURCE_PREPARED = 0;
        public static final int STAGE_TRACKS_SELECTED = 1;
        private final int stage;
        private final long value;

        @Target({ElementType.TYPE_USE})
        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Stage {
        }

        public Status(int i2, long j2) {
            this.stage = i2;
            this.value = j2;
        }

        @Override // androidx.media3.exoplayer.source.preload.TargetPreloadStatusControl.PreloadStatus
        public int getStage() {
            return this.stage;
        }

        @Override // androidx.media3.exoplayer.source.preload.TargetPreloadStatusControl.PreloadStatus
        public long getValue() {
            return this.value;
        }

        public Status(int i2) {
            this(i2, C.TIME_UNSET);
        }
    }
}
