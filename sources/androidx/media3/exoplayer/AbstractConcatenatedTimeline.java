package androidx.media3.exoplayer;

import android.util.Pair;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.source.ShuffleOrder;

@UnstableApi
/* loaded from: classes.dex */
public abstract class AbstractConcatenatedTimeline extends Timeline {
    private final int childCount;
    private final boolean isAtomic;
    private final ShuffleOrder shuffleOrder;

    public AbstractConcatenatedTimeline(boolean z2, ShuffleOrder shuffleOrder) {
        this.isAtomic = z2;
        this.shuffleOrder = shuffleOrder;
        this.childCount = shuffleOrder.getLength();
    }

    public static Object getChildPeriodUidFromConcatenatedUid(Object obj) {
        return ((Pair) obj).second;
    }

    public static Object getChildTimelineUidFromConcatenatedUid(Object obj) {
        return ((Pair) obj).first;
    }

    public static Object getConcatenatedUid(Object obj, Object obj2) {
        return Pair.create(obj, obj2);
    }

    private int getNextChildIndex(int i2, boolean z2) {
        if (z2) {
            return this.shuffleOrder.getNextIndex(i2);
        }
        if (i2 < this.childCount - 1) {
            return i2 + 1;
        }
        return -1;
    }

    private int getPreviousChildIndex(int i2, boolean z2) {
        if (z2) {
            return this.shuffleOrder.getPreviousIndex(i2);
        }
        if (i2 > 0) {
            return i2 - 1;
        }
        return -1;
    }

    protected abstract int a(Object obj);

    protected abstract int b(int i2);

    protected abstract Object c(int i2);

    protected abstract int d(int i2);

    protected abstract int e(int i2);

    protected abstract Timeline f(int i2);

    protected abstract int getChildIndexByPeriodIndex(int i2);

    @Override // androidx.media3.common.Timeline
    public int getFirstWindowIndex(boolean z2) {
        if (this.childCount == 0) {
            return -1;
        }
        if (this.isAtomic) {
            z2 = false;
        }
        int firstIndex = z2 ? this.shuffleOrder.getFirstIndex() : 0;
        while (f(firstIndex).isEmpty()) {
            firstIndex = getNextChildIndex(firstIndex, z2);
            if (firstIndex == -1) {
                return -1;
            }
        }
        return e(firstIndex) + f(firstIndex).getFirstWindowIndex(z2);
    }

    @Override // androidx.media3.common.Timeline
    public final int getIndexOfPeriod(Object obj) {
        int indexOfPeriod;
        if (!(obj instanceof Pair)) {
            return -1;
        }
        Object childTimelineUidFromConcatenatedUid = getChildTimelineUidFromConcatenatedUid(obj);
        Object childPeriodUidFromConcatenatedUid = getChildPeriodUidFromConcatenatedUid(obj);
        int iA = a(childTimelineUidFromConcatenatedUid);
        if (iA == -1 || (indexOfPeriod = f(iA).getIndexOfPeriod(childPeriodUidFromConcatenatedUid)) == -1) {
            return -1;
        }
        return d(iA) + indexOfPeriod;
    }

    @Override // androidx.media3.common.Timeline
    public int getLastWindowIndex(boolean z2) {
        int i2 = this.childCount;
        if (i2 == 0) {
            return -1;
        }
        if (this.isAtomic) {
            z2 = false;
        }
        int lastIndex = z2 ? this.shuffleOrder.getLastIndex() : i2 - 1;
        while (f(lastIndex).isEmpty()) {
            lastIndex = getPreviousChildIndex(lastIndex, z2);
            if (lastIndex == -1) {
                return -1;
            }
        }
        return e(lastIndex) + f(lastIndex).getLastWindowIndex(z2);
    }

    @Override // androidx.media3.common.Timeline
    public int getNextWindowIndex(int i2, int i3, boolean z2) {
        if (this.isAtomic) {
            if (i3 == 1) {
                i3 = 2;
            }
            z2 = false;
        }
        int iB = b(i2);
        int iE = e(iB);
        int nextWindowIndex = f(iB).getNextWindowIndex(i2 - iE, i3 != 2 ? i3 : 0, z2);
        if (nextWindowIndex != -1) {
            return iE + nextWindowIndex;
        }
        int nextChildIndex = getNextChildIndex(iB, z2);
        while (nextChildIndex != -1 && f(nextChildIndex).isEmpty()) {
            nextChildIndex = getNextChildIndex(nextChildIndex, z2);
        }
        if (nextChildIndex != -1) {
            return e(nextChildIndex) + f(nextChildIndex).getFirstWindowIndex(z2);
        }
        if (i3 == 2) {
            return getFirstWindowIndex(z2);
        }
        return -1;
    }

    @Override // androidx.media3.common.Timeline
    public final Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z2) {
        int childIndexByPeriodIndex = getChildIndexByPeriodIndex(i2);
        int iE = e(childIndexByPeriodIndex);
        f(childIndexByPeriodIndex).getPeriod(i2 - d(childIndexByPeriodIndex), period, z2);
        period.windowIndex += iE;
        if (z2) {
            period.uid = getConcatenatedUid(c(childIndexByPeriodIndex), Assertions.checkNotNull(period.uid));
        }
        return period;
    }

    @Override // androidx.media3.common.Timeline
    public final Timeline.Period getPeriodByUid(Object obj, Timeline.Period period) {
        Object childTimelineUidFromConcatenatedUid = getChildTimelineUidFromConcatenatedUid(obj);
        Object childPeriodUidFromConcatenatedUid = getChildPeriodUidFromConcatenatedUid(obj);
        int iA = a(childTimelineUidFromConcatenatedUid);
        int iE = e(iA);
        f(iA).getPeriodByUid(childPeriodUidFromConcatenatedUid, period);
        period.windowIndex += iE;
        period.uid = obj;
        return period;
    }

    @Override // androidx.media3.common.Timeline
    public int getPreviousWindowIndex(int i2, int i3, boolean z2) {
        if (this.isAtomic) {
            if (i3 == 1) {
                i3 = 2;
            }
            z2 = false;
        }
        int iB = b(i2);
        int iE = e(iB);
        int previousWindowIndex = f(iB).getPreviousWindowIndex(i2 - iE, i3 != 2 ? i3 : 0, z2);
        if (previousWindowIndex != -1) {
            return iE + previousWindowIndex;
        }
        int previousChildIndex = getPreviousChildIndex(iB, z2);
        while (previousChildIndex != -1 && f(previousChildIndex).isEmpty()) {
            previousChildIndex = getPreviousChildIndex(previousChildIndex, z2);
        }
        if (previousChildIndex != -1) {
            return e(previousChildIndex) + f(previousChildIndex).getLastWindowIndex(z2);
        }
        if (i3 == 2) {
            return getLastWindowIndex(z2);
        }
        return -1;
    }

    @Override // androidx.media3.common.Timeline
    public final Object getUidOfPeriod(int i2) {
        int childIndexByPeriodIndex = getChildIndexByPeriodIndex(i2);
        return getConcatenatedUid(c(childIndexByPeriodIndex), f(childIndexByPeriodIndex).getUidOfPeriod(i2 - d(childIndexByPeriodIndex)));
    }

    @Override // androidx.media3.common.Timeline
    public final Timeline.Window getWindow(int i2, Timeline.Window window, long j2) {
        int iB = b(i2);
        int iE = e(iB);
        int iD = d(iB);
        f(iB).getWindow(i2 - iE, window, j2);
        Object objC = c(iB);
        if (!Timeline.Window.SINGLE_WINDOW_UID.equals(window.uid)) {
            objC = getConcatenatedUid(objC, window.uid);
        }
        window.uid = objC;
        window.firstPeriodIndex += iD;
        window.lastPeriodIndex += iD;
        return window;
    }
}
