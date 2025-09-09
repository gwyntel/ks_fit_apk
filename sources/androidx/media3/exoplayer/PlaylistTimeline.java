package androidx.media3.exoplayer;

import androidx.media3.common.AdPlaybackState;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.source.ForwardingTimeline;
import androidx.media3.exoplayer.source.ShuffleOrder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
final class PlaylistTimeline extends AbstractConcatenatedTimeline {
    private final HashMap<Object, Integer> childIndexByUid;
    private final int[] firstPeriodInChildIndices;
    private final int[] firstWindowInChildIndices;
    private final int periodCount;
    private final Timeline[] timelines;
    private final Object[] uids;
    private final int windowCount;

    public PlaylistTimeline(Collection<? extends MediaSourceInfoHolder> collection, ShuffleOrder shuffleOrder) {
        this(getTimelines(collection), getUids(collection), shuffleOrder);
    }

    private static Timeline[] getTimelines(Collection<? extends MediaSourceInfoHolder> collection) {
        Timeline[] timelineArr = new Timeline[collection.size()];
        Iterator<? extends MediaSourceInfoHolder> it = collection.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            timelineArr[i2] = it.next().getTimeline();
            i2++;
        }
        return timelineArr;
    }

    private static Object[] getUids(Collection<? extends MediaSourceInfoHolder> collection) {
        Object[] objArr = new Object[collection.size()];
        Iterator<? extends MediaSourceInfoHolder> it = collection.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            objArr[i2] = it.next().getUid();
            i2++;
        }
        return objArr;
    }

    @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
    protected int a(Object obj) {
        Integer num = this.childIndexByUid.get(obj);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
    protected int b(int i2) {
        return Util.binarySearchFloor(this.firstWindowInChildIndices, i2 + 1, false, false);
    }

    @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
    protected Object c(int i2) {
        return this.uids[i2];
    }

    public PlaylistTimeline copyWithPlaceholderTimeline(ShuffleOrder shuffleOrder) {
        Timeline[] timelineArr = new Timeline[this.timelines.length];
        int i2 = 0;
        while (true) {
            Timeline[] timelineArr2 = this.timelines;
            if (i2 >= timelineArr2.length) {
                return new PlaylistTimeline(timelineArr, this.uids, shuffleOrder);
            }
            timelineArr[i2] = new ForwardingTimeline(timelineArr2[i2]) { // from class: androidx.media3.exoplayer.PlaylistTimeline.1
                private final Timeline.Window window = new Timeline.Window();

                @Override // androidx.media3.exoplayer.source.ForwardingTimeline, androidx.media3.common.Timeline
                public Timeline.Period getPeriod(int i3, Timeline.Period period, boolean z2) {
                    Timeline.Period period2 = super.getPeriod(i3, period, z2);
                    if (super.getWindow(period2.windowIndex, this.window).isLive()) {
                        period2.set(period.id, period.uid, period.windowIndex, period.durationUs, period.positionInWindowUs, AdPlaybackState.NONE, true);
                    } else {
                        period2.isPlaceholder = true;
                    }
                    return period2;
                }
            };
            i2++;
        }
    }

    @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
    protected int d(int i2) {
        return this.firstPeriodInChildIndices[i2];
    }

    @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
    protected int e(int i2) {
        return this.firstWindowInChildIndices[i2];
    }

    @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
    protected Timeline f(int i2) {
        return this.timelines[i2];
    }

    List g() {
        return Arrays.asList(this.timelines);
    }

    @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
    protected int getChildIndexByPeriodIndex(int i2) {
        return Util.binarySearchFloor(this.firstPeriodInChildIndices, i2 + 1, false, false);
    }

    @Override // androidx.media3.common.Timeline
    public int getPeriodCount() {
        return this.periodCount;
    }

    @Override // androidx.media3.common.Timeline
    public int getWindowCount() {
        return this.windowCount;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private PlaylistTimeline(Timeline[] timelineArr, Object[] objArr, ShuffleOrder shuffleOrder) {
        super(false, shuffleOrder);
        int i2 = 0;
        int length = timelineArr.length;
        this.timelines = timelineArr;
        this.firstPeriodInChildIndices = new int[length];
        this.firstWindowInChildIndices = new int[length];
        this.uids = objArr;
        this.childIndexByUid = new HashMap<>();
        int length2 = timelineArr.length;
        int windowCount = 0;
        int periodCount = 0;
        int i3 = 0;
        while (i2 < length2) {
            Timeline timeline = timelineArr[i2];
            this.timelines[i3] = timeline;
            this.firstWindowInChildIndices[i3] = windowCount;
            this.firstPeriodInChildIndices[i3] = periodCount;
            windowCount += timeline.getWindowCount();
            periodCount += this.timelines[i3].getPeriodCount();
            this.childIndexByUid.put(objArr[i3], Integer.valueOf(i3));
            i2++;
            i3++;
        }
        this.windowCount = windowCount;
        this.periodCount = periodCount;
    }
}
