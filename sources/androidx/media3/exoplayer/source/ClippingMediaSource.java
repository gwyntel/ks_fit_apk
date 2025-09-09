package androidx.media3.exoplayer.source;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.upstream.Allocator;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

@UnstableApi
/* loaded from: classes2.dex */
public final class ClippingMediaSource extends WrappingMediaSource {
    private final boolean allowDynamicClippingUpdates;

    @Nullable
    private IllegalClippingException clippingError;

    @Nullable
    private ClippingTimeline clippingTimeline;
    private final boolean enableInitialDiscontinuity;
    private final long endUs;
    private final ArrayList<ClippingMediaPeriod> mediaPeriods;
    private long periodEndUs;
    private long periodStartUs;
    private final boolean relativeToDefaultPosition;
    private final long startUs;
    private final Timeline.Window window;

    private static final class ClippingTimeline extends ForwardingTimeline {
        private final long durationUs;
        private final long endUs;
        private final boolean isDynamic;
        private final long startUs;

        public ClippingTimeline(Timeline timeline, long j2, long j3) throws IllegalClippingException {
            super(timeline);
            boolean z2 = false;
            if (timeline.getPeriodCount() != 1) {
                throw new IllegalClippingException(0);
            }
            Timeline.Window window = timeline.getWindow(0, new Timeline.Window());
            long jMax = Math.max(0L, j2);
            if (!window.isPlaceholder && jMax != 0 && !window.isSeekable) {
                throw new IllegalClippingException(1);
            }
            long jMax2 = j3 == Long.MIN_VALUE ? window.durationUs : Math.max(0L, j3);
            long j4 = window.durationUs;
            if (j4 != C.TIME_UNSET) {
                jMax2 = jMax2 > j4 ? j4 : jMax2;
                if (jMax > jMax2) {
                    throw new IllegalClippingException(2);
                }
            }
            this.startUs = jMax;
            this.endUs = jMax2;
            this.durationUs = jMax2 == C.TIME_UNSET ? -9223372036854775807L : jMax2 - jMax;
            if (window.isDynamic && (jMax2 == C.TIME_UNSET || (j4 != C.TIME_UNSET && jMax2 == j4))) {
                z2 = true;
            }
            this.isDynamic = z2;
        }

        @Override // androidx.media3.exoplayer.source.ForwardingTimeline, androidx.media3.common.Timeline
        public Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z2) {
            this.f5401a.getPeriod(0, period, z2);
            long positionInWindowUs = period.getPositionInWindowUs() - this.startUs;
            long j2 = this.durationUs;
            return period.set(period.id, period.uid, 0, j2 == C.TIME_UNSET ? -9223372036854775807L : j2 - positionInWindowUs, positionInWindowUs);
        }

        @Override // androidx.media3.exoplayer.source.ForwardingTimeline, androidx.media3.common.Timeline
        public Timeline.Window getWindow(int i2, Timeline.Window window, long j2) {
            this.f5401a.getWindow(0, window, 0L);
            long j3 = window.positionInFirstPeriodUs;
            long j4 = this.startUs;
            window.positionInFirstPeriodUs = j3 + j4;
            window.durationUs = this.durationUs;
            window.isDynamic = this.isDynamic;
            long j5 = window.defaultPositionUs;
            if (j5 != C.TIME_UNSET) {
                long jMax = Math.max(j5, j4);
                window.defaultPositionUs = jMax;
                long j6 = this.endUs;
                if (j6 != C.TIME_UNSET) {
                    jMax = Math.min(jMax, j6);
                }
                window.defaultPositionUs = jMax - this.startUs;
            }
            long jUsToMs = Util.usToMs(this.startUs);
            long j7 = window.presentationStartTimeMs;
            if (j7 != C.TIME_UNSET) {
                window.presentationStartTimeMs = j7 + jUsToMs;
            }
            long j8 = window.windowStartTimeMs;
            if (j8 != C.TIME_UNSET) {
                window.windowStartTimeMs = j8 + jUsToMs;
            }
            return window;
        }
    }

    public static final class IllegalClippingException extends IOException {
        public static final int REASON_INVALID_PERIOD_COUNT = 0;
        public static final int REASON_NOT_SEEKABLE_TO_START = 1;
        public static final int REASON_START_EXCEEDS_END = 2;
        public final int reason;

        @Target({ElementType.TYPE_USE})
        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Reason {
        }

        public IllegalClippingException(int i2) {
            super("Illegal clipping: " + getReasonDescription(i2));
            this.reason = i2;
        }

        private static String getReasonDescription(int i2) {
            return i2 != 0 ? i2 != 1 ? i2 != 2 ? "unknown" : "start exceeds end" : "not seekable to start" : "invalid period count";
        }
    }

    public ClippingMediaSource(MediaSource mediaSource, long j2, long j3) {
        this(mediaSource, j2, j3, true, false, false);
    }

    private void refreshClippedTimeline(Timeline timeline) {
        long j2;
        long j3;
        timeline.getWindow(0, this.window);
        long positionInFirstPeriodUs = this.window.getPositionInFirstPeriodUs();
        if (this.clippingTimeline == null || this.mediaPeriods.isEmpty() || this.allowDynamicClippingUpdates) {
            long j4 = this.startUs;
            long j5 = this.endUs;
            if (this.relativeToDefaultPosition) {
                long defaultPositionUs = this.window.getDefaultPositionUs();
                j4 += defaultPositionUs;
                j5 += defaultPositionUs;
            }
            this.periodStartUs = positionInFirstPeriodUs + j4;
            this.periodEndUs = this.endUs != Long.MIN_VALUE ? positionInFirstPeriodUs + j5 : Long.MIN_VALUE;
            int size = this.mediaPeriods.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.mediaPeriods.get(i2).updateClipping(this.periodStartUs, this.periodEndUs);
            }
            j2 = j4;
            j3 = j5;
        } else {
            long j6 = this.periodStartUs - positionInFirstPeriodUs;
            j3 = this.endUs != Long.MIN_VALUE ? this.periodEndUs - positionInFirstPeriodUs : Long.MIN_VALUE;
            j2 = j6;
        }
        try {
            ClippingTimeline clippingTimeline = new ClippingTimeline(timeline, j2, j3);
            this.clippingTimeline = clippingTimeline;
            k(clippingTimeline);
        } catch (IllegalClippingException e2) {
            this.clippingError = e2;
            for (int i3 = 0; i3 < this.mediaPeriods.size(); i3++) {
                this.mediaPeriods.get(i3).setClippingError(this.clippingError);
            }
        }
    }

    @Override // androidx.media3.exoplayer.source.WrappingMediaSource
    protected void B(Timeline timeline) {
        if (this.clippingError != null) {
            return;
        }
        refreshClippedTimeline(timeline);
    }

    @Override // androidx.media3.exoplayer.source.WrappingMediaSource, androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    public boolean canUpdateMediaItem(MediaItem mediaItem) {
        return getMediaItem().clippingConfiguration.equals(mediaItem.clippingConfiguration) && this.f5413a.canUpdateMediaItem(mediaItem);
    }

    @Override // androidx.media3.exoplayer.source.WrappingMediaSource, androidx.media3.exoplayer.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        ClippingMediaPeriod clippingMediaPeriod = new ClippingMediaPeriod(this.f5413a.createPeriod(mediaPeriodId, allocator, j2), this.enableInitialDiscontinuity, this.periodStartUs, this.periodEndUs);
        this.mediaPeriods.add(clippingMediaPeriod);
        return clippingMediaPeriod;
    }

    @Override // androidx.media3.exoplayer.source.CompositeMediaSource, androidx.media3.exoplayer.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        IllegalClippingException illegalClippingException = this.clippingError;
        if (illegalClippingException != null) {
            throw illegalClippingException;
        }
        super.maybeThrowSourceInfoRefreshError();
    }

    @Override // androidx.media3.exoplayer.source.WrappingMediaSource, androidx.media3.exoplayer.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        Assertions.checkState(this.mediaPeriods.remove(mediaPeriod));
        this.f5413a.releasePeriod(((ClippingMediaPeriod) mediaPeriod).mediaPeriod);
        if (!this.mediaPeriods.isEmpty() || this.allowDynamicClippingUpdates) {
            return;
        }
        refreshClippedTimeline(((ClippingTimeline) Assertions.checkNotNull(this.clippingTimeline)).f5401a);
    }

    @Override // androidx.media3.exoplayer.source.CompositeMediaSource, androidx.media3.exoplayer.source.BaseMediaSource
    protected void releaseSourceInternal() {
        super.releaseSourceInternal();
        this.clippingError = null;
        this.clippingTimeline = null;
    }

    public ClippingMediaSource(MediaSource mediaSource, long j2) {
        this(mediaSource, 0L, j2, true, false, true);
    }

    public ClippingMediaSource(MediaSource mediaSource, long j2, long j3, boolean z2, boolean z3, boolean z4) {
        super((MediaSource) Assertions.checkNotNull(mediaSource));
        Assertions.checkArgument(j2 >= 0);
        this.startUs = j2;
        this.endUs = j3;
        this.enableInitialDiscontinuity = z2;
        this.allowDynamicClippingUpdates = z3;
        this.relativeToDefaultPosition = z4;
        this.mediaPeriods = new ArrayList<>();
        this.window = new Timeline.Window();
    }
}
