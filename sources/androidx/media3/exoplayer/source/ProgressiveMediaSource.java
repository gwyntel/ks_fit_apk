package androidx.media3.exoplayer.source;

import android.os.Looper;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.TransferListener;
import androidx.media3.exoplayer.analytics.PlayerId;
import androidx.media3.exoplayer.drm.DefaultDrmSessionManagerProvider;
import androidx.media3.exoplayer.drm.DrmSessionManager;
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaExtractor;
import androidx.media3.exoplayer.source.ProgressiveMediaPeriod;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.exoplayer.upstream.Allocator;
import androidx.media3.exoplayer.upstream.CmcdConfiguration;
import androidx.media3.exoplayer.upstream.DefaultLoadErrorHandlingPolicy;
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy;
import androidx.media3.extractor.DefaultExtractorsFactory;
import androidx.media3.extractor.ExtractorsFactory;
import androidx.media3.extractor.text.SubtitleParser;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@UnstableApi
/* loaded from: classes2.dex */
public final class ProgressiveMediaSource extends BaseMediaSource implements ProgressiveMediaPeriod.Listener {
    public static final int DEFAULT_LOADING_CHECK_INTERVAL_BYTES = 1048576;
    private final int continueLoadingCheckIntervalBytes;
    private final DataSource.Factory dataSourceFactory;
    private final DrmSessionManager drmSessionManager;
    private final LoadErrorHandlingPolicy loadableLoadErrorHandlingPolicy;

    @GuardedBy("this")
    private MediaItem mediaItem;
    private final ProgressiveMediaExtractor.Factory progressiveMediaExtractorFactory;
    private long timelineDurationUs;
    private boolean timelineIsLive;
    private boolean timelineIsPlaceholder;
    private boolean timelineIsSeekable;

    @Nullable
    private TransferListener transferListener;

    public static final class Factory implements MediaSourceFactory {
        private int continueLoadingCheckIntervalBytes;
        private final DataSource.Factory dataSourceFactory;
        private DrmSessionManagerProvider drmSessionManagerProvider;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private ProgressiveMediaExtractor.Factory progressiveMediaExtractorFactory;

        public Factory(DataSource.Factory factory) {
            this(factory, new DefaultExtractorsFactory());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ ProgressiveMediaExtractor lambda$new$0(ExtractorsFactory extractorsFactory, PlayerId playerId) {
            return new BundledExtractorsAdapter(extractorsFactory);
        }

        @Override // androidx.media3.exoplayer.source.MediaSource.Factory
        public /* synthetic */ MediaSource.Factory experimentalParseSubtitlesDuringExtraction(boolean z2) {
            return q.a(this, z2);
        }

        @Override // androidx.media3.exoplayer.source.MediaSource.Factory
        public int[] getSupportedTypes() {
            return new int[]{4};
        }

        @Override // androidx.media3.exoplayer.source.MediaSource.Factory
        public /* synthetic */ MediaSource.Factory setCmcdConfigurationFactory(CmcdConfiguration.Factory factory) {
            return q.b(this, factory);
        }

        @CanIgnoreReturnValue
        public Factory setContinueLoadingCheckIntervalBytes(int i2) {
            this.continueLoadingCheckIntervalBytes = i2;
            return this;
        }

        @Override // androidx.media3.exoplayer.source.MediaSource.Factory
        public /* synthetic */ MediaSource.Factory setSubtitleParserFactory(SubtitleParser.Factory factory) {
            return q.c(this, factory);
        }

        public Factory(DataSource.Factory factory, final ExtractorsFactory extractorsFactory) {
            this(factory, new ProgressiveMediaExtractor.Factory() { // from class: androidx.media3.exoplayer.source.d0
                @Override // androidx.media3.exoplayer.source.ProgressiveMediaExtractor.Factory
                public final ProgressiveMediaExtractor createProgressiveMediaExtractor(PlayerId playerId) {
                    return ProgressiveMediaSource.Factory.lambda$new$0(extractorsFactory, playerId);
                }
            });
        }

        @Override // androidx.media3.exoplayer.source.MediaSource.Factory
        public ProgressiveMediaSource createMediaSource(MediaItem mediaItem) {
            Assertions.checkNotNull(mediaItem.localConfiguration);
            return new ProgressiveMediaSource(mediaItem, this.dataSourceFactory, this.progressiveMediaExtractorFactory, this.drmSessionManagerProvider.get(mediaItem), this.loadErrorHandlingPolicy, this.continueLoadingCheckIntervalBytes);
        }

        @Override // androidx.media3.exoplayer.source.MediaSource.Factory
        @CanIgnoreReturnValue
        public Factory setDrmSessionManagerProvider(DrmSessionManagerProvider drmSessionManagerProvider) {
            this.drmSessionManagerProvider = (DrmSessionManagerProvider) Assertions.checkNotNull(drmSessionManagerProvider, "MediaSource.Factory#setDrmSessionManagerProvider no longer handles null by instantiating a new DefaultDrmSessionManagerProvider. Explicitly construct and pass an instance in order to retain the old behavior.");
            return this;
        }

        @Override // androidx.media3.exoplayer.source.MediaSource.Factory
        @CanIgnoreReturnValue
        public Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            this.loadErrorHandlingPolicy = (LoadErrorHandlingPolicy) Assertions.checkNotNull(loadErrorHandlingPolicy, "MediaSource.Factory#setLoadErrorHandlingPolicy no longer handles null by instantiating a new DefaultLoadErrorHandlingPolicy. Explicitly construct and pass an instance in order to retain the old behavior.");
            return this;
        }

        public Factory(DataSource.Factory factory, ProgressiveMediaExtractor.Factory factory2) {
            this(factory, factory2, new DefaultDrmSessionManagerProvider(), new DefaultLoadErrorHandlingPolicy(), 1048576);
        }

        public Factory(DataSource.Factory factory, ProgressiveMediaExtractor.Factory factory2, DrmSessionManagerProvider drmSessionManagerProvider, LoadErrorHandlingPolicy loadErrorHandlingPolicy, int i2) {
            this.dataSourceFactory = factory;
            this.progressiveMediaExtractorFactory = factory2;
            this.drmSessionManagerProvider = drmSessionManagerProvider;
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
            this.continueLoadingCheckIntervalBytes = i2;
        }
    }

    private MediaItem.LocalConfiguration getLocalConfiguration() {
        return (MediaItem.LocalConfiguration) Assertions.checkNotNull(getMediaItem().localConfiguration);
    }

    private void notifySourceInfoRefreshed() {
        Timeline singlePeriodTimeline = new SinglePeriodTimeline(this.timelineDurationUs, this.timelineIsSeekable, false, this.timelineIsLive, (Object) null, getMediaItem());
        if (this.timelineIsPlaceholder) {
            singlePeriodTimeline = new ForwardingTimeline(singlePeriodTimeline) { // from class: androidx.media3.exoplayer.source.ProgressiveMediaSource.1
                @Override // androidx.media3.exoplayer.source.ForwardingTimeline, androidx.media3.common.Timeline
                public Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z2) {
                    super.getPeriod(i2, period, z2);
                    period.isPlaceholder = true;
                    return period;
                }

                @Override // androidx.media3.exoplayer.source.ForwardingTimeline, androidx.media3.common.Timeline
                public Timeline.Window getWindow(int i2, Timeline.Window window, long j2) {
                    super.getWindow(i2, window, j2);
                    window.isPlaceholder = true;
                    return window;
                }
            };
        }
        k(singlePeriodTimeline);
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    public boolean canUpdateMediaItem(MediaItem mediaItem) {
        MediaItem.LocalConfiguration localConfiguration = getLocalConfiguration();
        MediaItem.LocalConfiguration localConfiguration2 = mediaItem.localConfiguration;
        return localConfiguration2 != null && localConfiguration2.uri.equals(localConfiguration.uri) && localConfiguration2.imageDurationMs == localConfiguration.imageDurationMs && Util.areEqual(localConfiguration2.customCacheKey, localConfiguration.customCacheKey);
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        DataSource dataSourceCreateDataSource = this.dataSourceFactory.createDataSource();
        TransferListener transferListener = this.transferListener;
        if (transferListener != null) {
            dataSourceCreateDataSource.addTransferListener(transferListener);
        }
        MediaItem.LocalConfiguration localConfiguration = getLocalConfiguration();
        return new ProgressiveMediaPeriod(localConfiguration.uri, dataSourceCreateDataSource, this.progressiveMediaExtractorFactory.createProgressiveMediaExtractor(g()), this.drmSessionManager, b(mediaPeriodId), this.loadableLoadErrorHandlingPolicy, d(mediaPeriodId), this, allocator, localConfiguration.customCacheKey, this.continueLoadingCheckIntervalBytes, Util.msToUs(localConfiguration.imageDurationMs));
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public synchronized MediaItem getMediaItem() {
        return this.mediaItem;
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource
    protected void j(TransferListener transferListener) {
        this.transferListener = transferListener;
        this.drmSessionManager.setPlayer((Looper) Assertions.checkNotNull(Looper.myLooper()), g());
        this.drmSessionManager.prepare();
        notifySourceInfoRefreshed();
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() {
    }

    @Override // androidx.media3.exoplayer.source.ProgressiveMediaPeriod.Listener
    public void onSourceInfoRefreshed(long j2, boolean z2, boolean z3) {
        if (j2 == C.TIME_UNSET) {
            j2 = this.timelineDurationUs;
        }
        if (!this.timelineIsPlaceholder && this.timelineDurationUs == j2 && this.timelineIsSeekable == z2 && this.timelineIsLive == z3) {
            return;
        }
        this.timelineDurationUs = j2;
        this.timelineIsSeekable = z2;
        this.timelineIsLive = z3;
        this.timelineIsPlaceholder = false;
        notifySourceInfoRefreshed();
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((ProgressiveMediaPeriod) mediaPeriod).release();
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource
    protected void releaseSourceInternal() {
        this.drmSessionManager.release();
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    public synchronized void updateMediaItem(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    private ProgressiveMediaSource(MediaItem mediaItem, DataSource.Factory factory, ProgressiveMediaExtractor.Factory factory2, DrmSessionManager drmSessionManager, LoadErrorHandlingPolicy loadErrorHandlingPolicy, int i2) {
        this.mediaItem = mediaItem;
        this.dataSourceFactory = factory;
        this.progressiveMediaExtractorFactory = factory2;
        this.drmSessionManager = drmSessionManager;
        this.loadableLoadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.continueLoadingCheckIntervalBytes = i2;
        this.timelineIsPlaceholder = true;
        this.timelineDurationUs = C.TIME_UNSET;
    }
}
