package androidx.media3.exoplayer.source;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.TransferListener;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.upstream.Allocator;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public final class ConcatenatingMediaSource2 extends CompositeMediaSource<Integer> {
    private static final int MSG_UPDATE_TIMELINE = 1;

    @GuardedBy("this")
    private MediaItem mediaItem;
    private final IdentityHashMap<MediaPeriod, MediaSourceHolder> mediaSourceByMediaPeriod;
    private final ImmutableList<MediaSourceHolder> mediaSourceHolders;

    @Nullable
    private Handler playbackThreadHandler;
    private boolean timelineUpdateScheduled;

    public static final class Builder {
        private int index;

        @Nullable
        private MediaItem mediaItem;

        @Nullable
        private MediaSource.Factory mediaSourceFactory;
        private final ImmutableList.Builder<MediaSourceHolder> mediaSourceHoldersBuilder = ImmutableList.builder();

        @CanIgnoreReturnValue
        public Builder add(MediaItem mediaItem) {
            return add(mediaItem, C.TIME_UNSET);
        }

        public ConcatenatingMediaSource2 build() {
            Assertions.checkArgument(this.index > 0, "Must add at least one source to the concatenation.");
            if (this.mediaItem == null) {
                this.mediaItem = MediaItem.fromUri(Uri.EMPTY);
            }
            return new ConcatenatingMediaSource2(this.mediaItem, this.mediaSourceHoldersBuilder.build());
        }

        @CanIgnoreReturnValue
        public Builder setMediaItem(MediaItem mediaItem) {
            this.mediaItem = mediaItem;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setMediaSourceFactory(MediaSource.Factory factory) {
            this.mediaSourceFactory = (MediaSource.Factory) Assertions.checkNotNull(factory);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder useDefaultMediaSourceFactory(Context context) {
            return setMediaSourceFactory(new DefaultMediaSourceFactory(context));
        }

        @CanIgnoreReturnValue
        public Builder add(MediaItem mediaItem, long j2) {
            Assertions.checkNotNull(mediaItem);
            if (j2 == C.TIME_UNSET) {
                MediaItem.ClippingConfiguration clippingConfiguration = mediaItem.clippingConfiguration;
                if (clippingConfiguration.endPositionMs != Long.MIN_VALUE) {
                    j2 = Util.usToMs(clippingConfiguration.endPositionUs - clippingConfiguration.startPositionUs);
                }
            }
            Assertions.checkStateNotNull(this.mediaSourceFactory, "Must use useDefaultMediaSourceFactory or setMediaSourceFactory first.");
            return add(this.mediaSourceFactory.createMediaSource(mediaItem), j2);
        }

        @CanIgnoreReturnValue
        public Builder add(MediaSource mediaSource) {
            return add(mediaSource, C.TIME_UNSET);
        }

        @CanIgnoreReturnValue
        public Builder add(MediaSource mediaSource, long j2) {
            Assertions.checkNotNull(mediaSource);
            Assertions.checkState(((mediaSource instanceof ProgressiveMediaSource) && j2 == C.TIME_UNSET) ? false : true, "Progressive media source must define an initial placeholder duration.");
            ImmutableList.Builder<MediaSourceHolder> builder = this.mediaSourceHoldersBuilder;
            int i2 = this.index;
            this.index = i2 + 1;
            builder.add((ImmutableList.Builder<MediaSourceHolder>) new MediaSourceHolder(mediaSource, i2, Util.msToUs(j2)));
            return this;
        }
    }

    private static final class ConcatenatedTimeline extends Timeline {
        private final long defaultPositionUs;
        private final long durationUs;
        private final ImmutableList<Integer> firstPeriodIndices;
        private final boolean isDynamic;
        private final boolean isSeekable;

        @Nullable
        private final Object manifest;
        private final MediaItem mediaItem;
        private final ImmutableList<Long> periodOffsetsInWindowUs;
        private final ImmutableList<Timeline> timelines;

        public ConcatenatedTimeline(MediaItem mediaItem, ImmutableList<Timeline> immutableList, ImmutableList<Integer> immutableList2, ImmutableList<Long> immutableList3, boolean z2, boolean z3, long j2, long j3, @Nullable Object obj) {
            this.mediaItem = mediaItem;
            this.timelines = immutableList;
            this.firstPeriodIndices = immutableList2;
            this.periodOffsetsInWindowUs = immutableList3;
            this.isSeekable = z2;
            this.isDynamic = z3;
            this.durationUs = j2;
            this.defaultPositionUs = j3;
            this.manifest = obj;
        }

        private int getChildIndexByPeriodIndex(int i2) {
            return Util.binarySearchFloor((List<? extends Comparable<? super Integer>>) this.firstPeriodIndices, Integer.valueOf(i2 + 1), false, false);
        }

        private long getPeriodDurationUs(Timeline.Period period, int i2) {
            if (period.durationUs == C.TIME_UNSET) {
                return C.TIME_UNSET;
            }
            return (i2 == this.periodOffsetsInWindowUs.size() + (-1) ? this.durationUs : this.periodOffsetsInWindowUs.get(i2 + 1).longValue()) - this.periodOffsetsInWindowUs.get(i2).longValue();
        }

        @Override // androidx.media3.common.Timeline
        public int getIndexOfPeriod(Object obj) {
            if (!(obj instanceof Pair) || !(((Pair) obj).first instanceof Integer)) {
                return -1;
            }
            int childIndex = ConcatenatingMediaSource2.getChildIndex(obj);
            int indexOfPeriod = this.timelines.get(childIndex).getIndexOfPeriod(ConcatenatingMediaSource2.getChildPeriodUid(obj));
            if (indexOfPeriod == -1) {
                return -1;
            }
            return this.firstPeriodIndices.get(childIndex).intValue() + indexOfPeriod;
        }

        @Override // androidx.media3.common.Timeline
        public Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z2) {
            int childIndexByPeriodIndex = getChildIndexByPeriodIndex(i2);
            this.timelines.get(childIndexByPeriodIndex).getPeriod(i2 - this.firstPeriodIndices.get(childIndexByPeriodIndex).intValue(), period, z2);
            period.windowIndex = 0;
            period.positionInWindowUs = this.periodOffsetsInWindowUs.get(i2).longValue();
            period.durationUs = getPeriodDurationUs(period, i2);
            if (z2) {
                period.uid = ConcatenatingMediaSource2.getPeriodUid(childIndexByPeriodIndex, Assertions.checkNotNull(period.uid));
            }
            return period;
        }

        @Override // androidx.media3.common.Timeline
        public Timeline.Period getPeriodByUid(Object obj, Timeline.Period period) {
            int childIndex = ConcatenatingMediaSource2.getChildIndex(obj);
            Object childPeriodUid = ConcatenatingMediaSource2.getChildPeriodUid(obj);
            Timeline timeline = this.timelines.get(childIndex);
            int iIntValue = this.firstPeriodIndices.get(childIndex).intValue() + timeline.getIndexOfPeriod(childPeriodUid);
            timeline.getPeriodByUid(childPeriodUid, period);
            period.windowIndex = 0;
            period.positionInWindowUs = this.periodOffsetsInWindowUs.get(iIntValue).longValue();
            period.durationUs = getPeriodDurationUs(period, iIntValue);
            period.uid = obj;
            return period;
        }

        @Override // androidx.media3.common.Timeline
        public int getPeriodCount() {
            return this.periodOffsetsInWindowUs.size();
        }

        @Override // androidx.media3.common.Timeline
        public Object getUidOfPeriod(int i2) {
            int childIndexByPeriodIndex = getChildIndexByPeriodIndex(i2);
            return ConcatenatingMediaSource2.getPeriodUid(childIndexByPeriodIndex, this.timelines.get(childIndexByPeriodIndex).getUidOfPeriod(i2 - this.firstPeriodIndices.get(childIndexByPeriodIndex).intValue()));
        }

        @Override // androidx.media3.common.Timeline
        public Timeline.Window getWindow(int i2, Timeline.Window window, long j2) {
            return window.set(Timeline.Window.SINGLE_WINDOW_UID, this.mediaItem, this.manifest, C.TIME_UNSET, C.TIME_UNSET, C.TIME_UNSET, this.isSeekable, this.isDynamic, null, this.defaultPositionUs, this.durationUs, 0, getPeriodCount() - 1, -this.periodOffsetsInWindowUs.get(0).longValue());
        }

        @Override // androidx.media3.common.Timeline
        public int getWindowCount() {
            return 1;
        }
    }

    static final class MediaSourceHolder {
        public int activeMediaPeriods;
        public final int index;
        public final long initialPlaceholderDurationUs;
        public final MaskingMediaSource mediaSource;
        public final HashMap<Object, Long> periodTimeOffsetsByUid = new HashMap<>();

        public MediaSourceHolder(MediaSource mediaSource, int i2, long j2) {
            this.mediaSource = new MaskingMediaSource(mediaSource, false);
            this.index = i2;
            this.initialPlaceholderDurationUs = j2;
        }
    }

    private void disableUnusedMediaSources() {
        for (int i2 = 0; i2 < this.mediaSourceHolders.size(); i2++) {
            MediaSourceHolder mediaSourceHolder = this.mediaSourceHolders.get(i2);
            if (mediaSourceHolder.activeMediaPeriods == 0) {
                n(Integer.valueOf(mediaSourceHolder.index));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getChildIndex(Object obj) {
        return ((Integer) ((Pair) obj).first).intValue();
    }

    private static int getChildIndexFromChildWindowSequenceNumber(long j2, int i2) {
        return (int) (j2 % i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object getChildPeriodUid(Object obj) {
        return ((Pair) obj).second;
    }

    private static long getChildWindowSequenceNumber(long j2, int i2, int i3) {
        return (j2 * i2) + i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object getPeriodUid(int i2, Object obj) {
        return Pair.create(Integer.valueOf(i2), obj);
    }

    private static long getWindowSequenceNumberFromChildWindowSequenceNumber(long j2, int i2) {
        return j2 / i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleMessage(Message message) {
        if (message.what == 1) {
            updateTimeline();
        }
        return true;
    }

    @Nullable
    private ConcatenatedTimeline maybeCreateConcatenatedTimeline() {
        int i2;
        boolean z2;
        boolean z3;
        Object obj;
        Object obj2;
        Timeline timeline;
        long j2;
        Timeline.Period period;
        boolean z4;
        ConcatenatingMediaSource2 concatenatingMediaSource2 = this;
        Timeline.Window window = new Timeline.Window();
        Timeline.Period period2 = new Timeline.Period();
        ImmutableList.Builder builder = ImmutableList.builder();
        ImmutableList.Builder builder2 = ImmutableList.builder();
        ImmutableList.Builder builder3 = ImmutableList.builder();
        int size = concatenatingMediaSource2.mediaSourceHolders.size();
        boolean z5 = true;
        boolean z6 = true;
        boolean z7 = true;
        int i3 = 0;
        Object obj3 = null;
        int periodCount = 0;
        boolean z8 = false;
        boolean z9 = false;
        long j3 = 0;
        long j4 = 0;
        long j5 = 0;
        while (i3 < size) {
            MediaSourceHolder mediaSourceHolder = concatenatingMediaSource2.mediaSourceHolders.get(i3);
            Timeline timeline2 = mediaSourceHolder.mediaSource.getTimeline();
            Assertions.checkArgument(timeline2.isEmpty() ^ z5, "Can't concatenate empty child Timeline.");
            builder.add((ImmutableList.Builder) timeline2);
            builder2.add((ImmutableList.Builder) Integer.valueOf(periodCount));
            periodCount += timeline2.getPeriodCount();
            int i4 = 0;
            while (i4 < timeline2.getWindowCount()) {
                timeline2.getWindow(i4, window);
                if (!z8) {
                    obj3 = window.manifest;
                    z8 = true;
                }
                if (z6 && Util.areEqual(obj3, window.manifest)) {
                    i2 = i3;
                    z2 = true;
                } else {
                    i2 = i3;
                    z2 = false;
                }
                long j6 = window.durationUs;
                if (j6 == C.TIME_UNSET) {
                    j6 = mediaSourceHolder.initialPlaceholderDurationUs;
                    if (j6 == C.TIME_UNSET) {
                        return null;
                    }
                }
                j3 += j6;
                if (mediaSourceHolder.index == 0 && i4 == 0) {
                    z3 = z2;
                    obj = obj3;
                    j4 = window.defaultPositionUs;
                    j5 = -window.positionInFirstPeriodUs;
                } else {
                    z3 = z2;
                    obj = obj3;
                }
                z7 &= window.isSeekable || window.isPlaceholder;
                z9 |= window.isDynamic;
                int i5 = window.firstPeriodIndex;
                while (i5 <= window.lastPeriodIndex) {
                    builder3.add((ImmutableList.Builder) Long.valueOf(j5));
                    timeline2.getPeriod(i5, period2, true);
                    int i6 = periodCount;
                    long j7 = period2.durationUs;
                    if (j7 == C.TIME_UNSET) {
                        Assertions.checkArgument(window.firstPeriodIndex == window.lastPeriodIndex, "Can't apply placeholder duration to multiple periods with unknown duration in a single window.");
                        j7 = window.positionInFirstPeriodUs + j6;
                    }
                    if (i5 != window.firstPeriodIndex || ((mediaSourceHolder.index == 0 && i4 == 0) || j7 == C.TIME_UNSET)) {
                        obj2 = obj;
                        timeline = timeline2;
                        j2 = 0;
                    } else {
                        Timeline timeline3 = timeline2;
                        obj2 = obj;
                        j2 = -window.positionInFirstPeriodUs;
                        j7 += j2;
                        timeline = timeline3;
                    }
                    Object objCheckNotNull = Assertions.checkNotNull(period2.uid);
                    Timeline.Window window2 = window;
                    if (mediaSourceHolder.activeMediaPeriods == 0 || !mediaSourceHolder.periodTimeOffsetsByUid.containsKey(objCheckNotNull)) {
                        period = period2;
                    } else {
                        period = period2;
                        if (!mediaSourceHolder.periodTimeOffsetsByUid.get(objCheckNotNull).equals(Long.valueOf(j2))) {
                            z4 = false;
                        }
                        Assertions.checkArgument(z4, "Can't handle windows with changing offset in first period.");
                        mediaSourceHolder.periodTimeOffsetsByUid.put(objCheckNotNull, Long.valueOf(j2));
                        j5 += j7;
                        i5++;
                        periodCount = i6;
                        obj = obj2;
                        timeline2 = timeline;
                        window = window2;
                        period2 = period;
                    }
                    z4 = true;
                    Assertions.checkArgument(z4, "Can't handle windows with changing offset in first period.");
                    mediaSourceHolder.periodTimeOffsetsByUid.put(objCheckNotNull, Long.valueOf(j2));
                    j5 += j7;
                    i5++;
                    periodCount = i6;
                    obj = obj2;
                    timeline2 = timeline;
                    window = window2;
                    period2 = period;
                }
                i4++;
                i3 = i2;
                z6 = z3;
                obj3 = obj;
            }
            i3++;
            z5 = true;
            concatenatingMediaSource2 = this;
        }
        return new ConcatenatedTimeline(getMediaItem(), builder.build(), builder2.build(), builder3.build(), z7, z9, j3, j4, z6 ? obj3 : null);
    }

    private void scheduleTimelineUpdate() {
        if (this.timelineUpdateScheduled) {
            return;
        }
        ((Handler) Assertions.checkNotNull(this.playbackThreadHandler)).obtainMessage(1).sendToTarget();
        this.timelineUpdateScheduled = true;
    }

    private void updateTimeline() {
        this.timelineUpdateScheduled = false;
        ConcatenatedTimeline concatenatedTimelineMaybeCreateConcatenatedTimeline = maybeCreateConcatenatedTimeline();
        if (concatenatedTimelineMaybeCreateConcatenatedTimeline != null) {
            k(concatenatedTimelineMaybeCreateConcatenatedTimeline);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.exoplayer.source.CompositeMediaSource
    /* renamed from: A, reason: merged with bridge method [inline-methods] */
    public long q(Integer num, long j2, MediaSource.MediaPeriodId mediaPeriodId) {
        Long l2;
        return (j2 == C.TIME_UNSET || mediaPeriodId == null || mediaPeriodId.isAd() || (l2 = this.mediaSourceHolders.get(num.intValue()).periodTimeOffsetsByUid.get(mediaPeriodId.periodUid)) == null) ? j2 : j2 + Util.usToMs(l2.longValue());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.exoplayer.source.CompositeMediaSource
    /* renamed from: B, reason: merged with bridge method [inline-methods] */
    public int r(Integer num, int i2) {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.exoplayer.source.CompositeMediaSource
    /* renamed from: C, reason: merged with bridge method [inline-methods] */
    public void lambda$prepareChildSource$0(Integer num, MediaSource mediaSource, Timeline timeline) {
        scheduleTimelineUpdate();
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    public boolean canUpdateMediaItem(MediaItem mediaItem) {
        return true;
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        MediaSourceHolder mediaSourceHolder = this.mediaSourceHolders.get(getChildIndex(mediaPeriodId.periodUid));
        MediaSource.MediaPeriodId mediaPeriodIdCopyWithWindowSequenceNumber = mediaPeriodId.copyWithPeriodUid(getChildPeriodUid(mediaPeriodId.periodUid)).copyWithWindowSequenceNumber(getChildWindowSequenceNumber(mediaPeriodId.windowSequenceNumber, this.mediaSourceHolders.size(), mediaSourceHolder.index));
        o(Integer.valueOf(mediaSourceHolder.index));
        mediaSourceHolder.activeMediaPeriods++;
        long jLongValue = mediaPeriodId.isAd() ? 0L : ((Long) Assertions.checkNotNull(mediaSourceHolder.periodTimeOffsetsByUid.get(mediaPeriodIdCopyWithWindowSequenceNumber.periodUid))).longValue();
        TimeOffsetMediaPeriod timeOffsetMediaPeriod = new TimeOffsetMediaPeriod(mediaSourceHolder.mediaSource.createPeriod(mediaPeriodIdCopyWithWindowSequenceNumber, allocator, j2 - jLongValue), jLongValue);
        this.mediaSourceByMediaPeriod.put(timeOffsetMediaPeriod, mediaSourceHolder);
        disableUnusedMediaSources();
        return timeOffsetMediaPeriod;
    }

    @Override // androidx.media3.exoplayer.source.CompositeMediaSource, androidx.media3.exoplayer.source.BaseMediaSource
    protected void f() {
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    @Nullable
    public Timeline getInitialTimeline() {
        return maybeCreateConcatenatedTimeline();
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public synchronized MediaItem getMediaItem() {
        return this.mediaItem;
    }

    @Override // androidx.media3.exoplayer.source.CompositeMediaSource, androidx.media3.exoplayer.source.BaseMediaSource
    protected void j(TransferListener transferListener) {
        super.j(transferListener);
        this.playbackThreadHandler = new Handler(new Handler.Callback() { // from class: androidx.media3.exoplayer.source.d
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.f5443a.handleMessage(message);
            }
        });
        for (int i2 = 0; i2 < this.mediaSourceHolders.size(); i2++) {
            t(Integer.valueOf(i2), this.mediaSourceHolders.get(i2).mediaSource);
        }
        scheduleTimelineUpdate();
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((MediaSourceHolder) Assertions.checkNotNull(this.mediaSourceByMediaPeriod.remove(mediaPeriod))).mediaSource.releasePeriod(((TimeOffsetMediaPeriod) mediaPeriod).getWrappedMediaPeriod());
        r0.activeMediaPeriods--;
        if (this.mediaSourceByMediaPeriod.isEmpty()) {
            return;
        }
        disableUnusedMediaSources();
    }

    @Override // androidx.media3.exoplayer.source.CompositeMediaSource, androidx.media3.exoplayer.source.BaseMediaSource
    protected void releaseSourceInternal() {
        super.releaseSourceInternal();
        Handler handler = this.playbackThreadHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.playbackThreadHandler = null;
        }
        this.timelineUpdateScheduled = false;
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    public synchronized void updateMediaItem(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.exoplayer.source.CompositeMediaSource
    /* renamed from: z, reason: merged with bridge method [inline-methods] */
    public MediaSource.MediaPeriodId p(Integer num, MediaSource.MediaPeriodId mediaPeriodId) {
        if (num.intValue() != getChildIndexFromChildWindowSequenceNumber(mediaPeriodId.windowSequenceNumber, this.mediaSourceHolders.size())) {
            return null;
        }
        return mediaPeriodId.copyWithPeriodUid(getPeriodUid(num.intValue(), mediaPeriodId.periodUid)).copyWithWindowSequenceNumber(getWindowSequenceNumberFromChildWindowSequenceNumber(mediaPeriodId.windowSequenceNumber, this.mediaSourceHolders.size()));
    }

    private ConcatenatingMediaSource2(MediaItem mediaItem, ImmutableList<MediaSourceHolder> immutableList) {
        this.mediaItem = mediaItem;
        this.mediaSourceHolders = immutableList;
        this.mediaSourceByMediaPeriod = new IdentityHashMap<>();
    }
}
