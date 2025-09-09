package androidx.media3.exoplayer.source;

import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.TransferListener;
import androidx.media3.exoplayer.analytics.PlayerId;
import androidx.media3.exoplayer.drm.DrmSessionEventListener;
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider;
import androidx.media3.exoplayer.upstream.Allocator;
import androidx.media3.exoplayer.upstream.CmcdConfiguration;
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy;
import androidx.media3.extractor.text.SubtitleParser;
import java.io.IOException;

/* loaded from: classes2.dex */
public interface MediaSource {

    public interface Factory {

        @UnstableApi
        public static final Factory UNSUPPORTED = MediaSourceFactory.UNSUPPORTED;

        @UnstableApi
        MediaSource createMediaSource(MediaItem mediaItem);

        @UnstableApi
        @Deprecated
        Factory experimentalParseSubtitlesDuringExtraction(boolean z2);

        @UnstableApi
        int[] getSupportedTypes();

        @UnstableApi
        Factory setCmcdConfigurationFactory(CmcdConfiguration.Factory factory);

        @UnstableApi
        Factory setDrmSessionManagerProvider(DrmSessionManagerProvider drmSessionManagerProvider);

        @UnstableApi
        Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy);

        @UnstableApi
        Factory setSubtitleParserFactory(SubtitleParser.Factory factory);
    }

    @UnstableApi
    public static final class MediaPeriodId {
        public final int adGroupIndex;
        public final int adIndexInAdGroup;
        public final int nextAdGroupIndex;
        public final Object periodUid;
        public final long windowSequenceNumber;

        public MediaPeriodId(Object obj) {
            this(obj, -1L);
        }

        public MediaPeriodId copyWithPeriodUid(Object obj) {
            return this.periodUid.equals(obj) ? this : new MediaPeriodId(obj, this.adGroupIndex, this.adIndexInAdGroup, this.windowSequenceNumber, this.nextAdGroupIndex);
        }

        public MediaPeriodId copyWithWindowSequenceNumber(long j2) {
            return this.windowSequenceNumber == j2 ? this : new MediaPeriodId(this.periodUid, this.adGroupIndex, this.adIndexInAdGroup, j2, this.nextAdGroupIndex);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MediaPeriodId)) {
                return false;
            }
            MediaPeriodId mediaPeriodId = (MediaPeriodId) obj;
            return this.periodUid.equals(mediaPeriodId.periodUid) && this.adGroupIndex == mediaPeriodId.adGroupIndex && this.adIndexInAdGroup == mediaPeriodId.adIndexInAdGroup && this.windowSequenceNumber == mediaPeriodId.windowSequenceNumber && this.nextAdGroupIndex == mediaPeriodId.nextAdGroupIndex;
        }

        public int hashCode() {
            return ((((((((527 + this.periodUid.hashCode()) * 31) + this.adGroupIndex) * 31) + this.adIndexInAdGroup) * 31) + ((int) this.windowSequenceNumber)) * 31) + this.nextAdGroupIndex;
        }

        public boolean isAd() {
            return this.adGroupIndex != -1;
        }

        public MediaPeriodId(Object obj, long j2) {
            this(obj, -1, -1, j2, -1);
        }

        public MediaPeriodId(Object obj, long j2, int i2) {
            this(obj, -1, -1, j2, i2);
        }

        public MediaPeriodId(Object obj, int i2, int i3, long j2) {
            this(obj, i2, i3, j2, -1);
        }

        private MediaPeriodId(Object obj, int i2, int i3, long j2, int i4) {
            this.periodUid = obj;
            this.adGroupIndex = i2;
            this.adIndexInAdGroup = i3;
            this.windowSequenceNumber = j2;
            this.nextAdGroupIndex = i4;
        }
    }

    @UnstableApi
    public interface MediaSourceCaller {
        void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline);
    }

    @UnstableApi
    void addDrmEventListener(Handler handler, DrmSessionEventListener drmSessionEventListener);

    @UnstableApi
    void addEventListener(Handler handler, MediaSourceEventListener mediaSourceEventListener);

    @UnstableApi
    boolean canUpdateMediaItem(MediaItem mediaItem);

    @UnstableApi
    MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator, long j2);

    @UnstableApi
    void disable(MediaSourceCaller mediaSourceCaller);

    @UnstableApi
    void enable(MediaSourceCaller mediaSourceCaller);

    @Nullable
    @UnstableApi
    Timeline getInitialTimeline();

    @UnstableApi
    MediaItem getMediaItem();

    @UnstableApi
    boolean isSingleWindow();

    @UnstableApi
    void maybeThrowSourceInfoRefreshError() throws IOException;

    @UnstableApi
    @Deprecated
    void prepareSource(MediaSourceCaller mediaSourceCaller, @Nullable TransferListener transferListener);

    @UnstableApi
    void prepareSource(MediaSourceCaller mediaSourceCaller, @Nullable TransferListener transferListener, PlayerId playerId);

    @UnstableApi
    void releasePeriod(MediaPeriod mediaPeriod);

    @UnstableApi
    void releaseSource(MediaSourceCaller mediaSourceCaller);

    @UnstableApi
    void removeDrmEventListener(DrmSessionEventListener drmSessionEventListener);

    @UnstableApi
    void removeEventListener(MediaSourceEventListener mediaSourceEventListener);

    @UnstableApi
    void updateMediaItem(MediaItem mediaItem);
}
