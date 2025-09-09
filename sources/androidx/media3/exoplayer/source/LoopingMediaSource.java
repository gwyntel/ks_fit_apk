package androidx.media3.exoplayer.source;

import androidx.annotation.Nullable;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.AbstractConcatenatedTimeline;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ShuffleOrder;
import androidx.media3.exoplayer.upstream.Allocator;
import java.util.HashMap;
import java.util.Map;

@UnstableApi
@Deprecated
/* loaded from: classes2.dex */
public final class LoopingMediaSource extends WrappingMediaSource {
    private final Map<MediaSource.MediaPeriodId, MediaSource.MediaPeriodId> childMediaPeriodIdToMediaPeriodId;
    private final int loopCount;
    private final Map<MediaPeriod, MediaSource.MediaPeriodId> mediaPeriodToChildMediaPeriodId;

    private static final class InfinitelyLoopingTimeline extends ForwardingTimeline {
        public InfinitelyLoopingTimeline(Timeline timeline) {
            super(timeline);
        }

        @Override // androidx.media3.exoplayer.source.ForwardingTimeline, androidx.media3.common.Timeline
        public int getNextWindowIndex(int i2, int i3, boolean z2) {
            int nextWindowIndex = this.f5401a.getNextWindowIndex(i2, i3, z2);
            return nextWindowIndex == -1 ? getFirstWindowIndex(z2) : nextWindowIndex;
        }

        @Override // androidx.media3.exoplayer.source.ForwardingTimeline, androidx.media3.common.Timeline
        public int getPreviousWindowIndex(int i2, int i3, boolean z2) {
            int previousWindowIndex = this.f5401a.getPreviousWindowIndex(i2, i3, z2);
            return previousWindowIndex == -1 ? getLastWindowIndex(z2) : previousWindowIndex;
        }
    }

    private static final class LoopingTimeline extends AbstractConcatenatedTimeline {
        private final int childPeriodCount;
        private final Timeline childTimeline;
        private final int childWindowCount;
        private final int loopCount;

        public LoopingTimeline(Timeline timeline, int i2) {
            super(false, new ShuffleOrder.UnshuffledShuffleOrder(i2));
            this.childTimeline = timeline;
            int periodCount = timeline.getPeriodCount();
            this.childPeriodCount = periodCount;
            this.childWindowCount = timeline.getWindowCount();
            this.loopCount = i2;
            if (periodCount > 0) {
                Assertions.checkState(i2 <= Integer.MAX_VALUE / periodCount, "LoopingMediaSource contains too many periods");
            }
        }

        @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
        protected int a(Object obj) {
            if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            }
            return -1;
        }

        @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
        protected int b(int i2) {
            return i2 / this.childWindowCount;
        }

        @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
        protected Object c(int i2) {
            return Integer.valueOf(i2);
        }

        @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
        protected int d(int i2) {
            return i2 * this.childPeriodCount;
        }

        @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
        protected int e(int i2) {
            return i2 * this.childWindowCount;
        }

        @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
        protected Timeline f(int i2) {
            return this.childTimeline;
        }

        @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
        protected int getChildIndexByPeriodIndex(int i2) {
            return i2 / this.childPeriodCount;
        }

        @Override // androidx.media3.common.Timeline
        public int getPeriodCount() {
            return this.childPeriodCount * this.loopCount;
        }

        @Override // androidx.media3.common.Timeline
        public int getWindowCount() {
            return this.childWindowCount * this.loopCount;
        }
    }

    public LoopingMediaSource(MediaSource mediaSource) {
        this(mediaSource, Integer.MAX_VALUE);
    }

    @Override // androidx.media3.exoplayer.source.WrappingMediaSource
    protected void B(Timeline timeline) {
        k(this.loopCount != Integer.MAX_VALUE ? new LoopingTimeline(timeline, this.loopCount) : new InfinitelyLoopingTimeline(timeline));
    }

    @Override // androidx.media3.exoplayer.source.WrappingMediaSource, androidx.media3.exoplayer.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        if (this.loopCount == Integer.MAX_VALUE) {
            return this.f5413a.createPeriod(mediaPeriodId, allocator, j2);
        }
        MediaSource.MediaPeriodId mediaPeriodIdCopyWithPeriodUid = mediaPeriodId.copyWithPeriodUid(AbstractConcatenatedTimeline.getChildPeriodUidFromConcatenatedUid(mediaPeriodId.periodUid));
        this.childMediaPeriodIdToMediaPeriodId.put(mediaPeriodIdCopyWithPeriodUid, mediaPeriodId);
        MediaPeriod mediaPeriodCreatePeriod = this.f5413a.createPeriod(mediaPeriodIdCopyWithPeriodUid, allocator, j2);
        this.mediaPeriodToChildMediaPeriodId.put(mediaPeriodCreatePeriod, mediaPeriodIdCopyWithPeriodUid);
        return mediaPeriodCreatePeriod;
    }

    @Override // androidx.media3.exoplayer.source.WrappingMediaSource, androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    @Nullable
    public Timeline getInitialTimeline() {
        MaskingMediaSource maskingMediaSource = (MaskingMediaSource) this.f5413a;
        return this.loopCount != Integer.MAX_VALUE ? new LoopingTimeline(maskingMediaSource.getTimeline(), this.loopCount) : new InfinitelyLoopingTimeline(maskingMediaSource.getTimeline());
    }

    @Override // androidx.media3.exoplayer.source.WrappingMediaSource, androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    public boolean isSingleWindow() {
        return false;
    }

    @Override // androidx.media3.exoplayer.source.WrappingMediaSource, androidx.media3.exoplayer.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        this.f5413a.releasePeriod(mediaPeriod);
        MediaSource.MediaPeriodId mediaPeriodIdRemove = this.mediaPeriodToChildMediaPeriodId.remove(mediaPeriod);
        if (mediaPeriodIdRemove != null) {
            this.childMediaPeriodIdToMediaPeriodId.remove(mediaPeriodIdRemove);
        }
    }

    @Override // androidx.media3.exoplayer.source.WrappingMediaSource
    protected MediaSource.MediaPeriodId v(MediaSource.MediaPeriodId mediaPeriodId) {
        return this.loopCount != Integer.MAX_VALUE ? this.childMediaPeriodIdToMediaPeriodId.get(mediaPeriodId) : mediaPeriodId;
    }

    public LoopingMediaSource(MediaSource mediaSource, int i2) {
        super(new MaskingMediaSource(mediaSource, false));
        Assertions.checkArgument(i2 > 0);
        this.loopCount = i2;
        this.childMediaPeriodIdToMediaPeriodId = new HashMap();
        this.mediaPeriodToChildMediaPeriodId = new HashMap();
    }
}
