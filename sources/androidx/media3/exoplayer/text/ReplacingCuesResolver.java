package androidx.media3.exoplayer.text;

import androidx.media3.common.C;
import androidx.media3.common.text.Cue;
import androidx.media3.extractor.text.CuesWithTiming;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.util.ArrayList;

/* loaded from: classes2.dex */
final class ReplacingCuesResolver implements CuesResolver {
    private final ArrayList<CuesWithTiming> cuesWithTimingList = new ArrayList<>();

    private int getIndexOfCuesStartingAfter(long j2) {
        for (int i2 = 0; i2 < this.cuesWithTimingList.size(); i2++) {
            if (j2 < this.cuesWithTimingList.get(i2).startTimeUs) {
                return i2;
            }
        }
        return this.cuesWithTimingList.size();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0025  */
    @Override // androidx.media3.exoplayer.text.CuesResolver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean addCues(androidx.media3.extractor.text.CuesWithTiming r10, long r11) {
        /*
            r9 = this;
            long r0 = r10.startTimeUs
            r2 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r1 = 0
            r4 = 1
            if (r0 == 0) goto Lf
            r0 = r4
            goto L10
        Lf:
            r0 = r1
        L10:
            androidx.media3.common.util.Assertions.checkArgument(r0)
            long r5 = r10.startTimeUs
            int r0 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r0 > 0) goto L25
            long r5 = r10.endTimeUs
            int r0 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r0 == 0) goto L23
            int r0 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r0 >= 0) goto L25
        L23:
            r0 = r4
            goto L26
        L25:
            r0 = r1
        L26:
            java.util.ArrayList<androidx.media3.extractor.text.CuesWithTiming> r2 = r9.cuesWithTimingList
            int r2 = r2.size()
            int r2 = r2 - r4
        L2d:
            if (r2 < 0) goto L58
            long r5 = r10.startTimeUs
            java.util.ArrayList<androidx.media3.extractor.text.CuesWithTiming> r3 = r9.cuesWithTimingList
            java.lang.Object r3 = r3.get(r2)
            androidx.media3.extractor.text.CuesWithTiming r3 = (androidx.media3.extractor.text.CuesWithTiming) r3
            long r7 = r3.startTimeUs
            int r3 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r3 < 0) goto L46
            java.util.ArrayList<androidx.media3.extractor.text.CuesWithTiming> r11 = r9.cuesWithTimingList
            int r2 = r2 + r4
            r11.add(r2, r10)
            return r0
        L46:
            java.util.ArrayList<androidx.media3.extractor.text.CuesWithTiming> r3 = r9.cuesWithTimingList
            java.lang.Object r3 = r3.get(r2)
            androidx.media3.extractor.text.CuesWithTiming r3 = (androidx.media3.extractor.text.CuesWithTiming) r3
            long r5 = r3.startTimeUs
            int r3 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r3 > 0) goto L55
            r0 = r1
        L55:
            int r2 = r2 + (-1)
            goto L2d
        L58:
            java.util.ArrayList<androidx.media3.extractor.text.CuesWithTiming> r11 = r9.cuesWithTimingList
            r11.add(r1, r10)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.text.ReplacingCuesResolver.addCues(androidx.media3.extractor.text.CuesWithTiming, long):boolean");
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public void clear() {
        this.cuesWithTimingList.clear();
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public void discardCuesBeforeTimeUs(long j2) {
        int indexOfCuesStartingAfter = getIndexOfCuesStartingAfter(j2);
        if (indexOfCuesStartingAfter > 0) {
            this.cuesWithTimingList.subList(0, indexOfCuesStartingAfter).clear();
        }
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public ImmutableList<Cue> getCuesAtTimeUs(long j2) {
        int indexOfCuesStartingAfter = getIndexOfCuesStartingAfter(j2);
        if (indexOfCuesStartingAfter == 0) {
            return ImmutableList.of();
        }
        CuesWithTiming cuesWithTiming = this.cuesWithTimingList.get(indexOfCuesStartingAfter - 1);
        long j3 = cuesWithTiming.endTimeUs;
        return (j3 == C.TIME_UNSET || j2 < j3) ? cuesWithTiming.cues : ImmutableList.of();
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public long getNextCueChangeTimeUs(long j2) {
        if (this.cuesWithTimingList.isEmpty()) {
            return Long.MIN_VALUE;
        }
        if (j2 < this.cuesWithTimingList.get(0).startTimeUs) {
            return this.cuesWithTimingList.get(0).startTimeUs;
        }
        for (int i2 = 1; i2 < this.cuesWithTimingList.size(); i2++) {
            CuesWithTiming cuesWithTiming = this.cuesWithTimingList.get(i2);
            if (j2 < cuesWithTiming.startTimeUs) {
                long j3 = this.cuesWithTimingList.get(i2 - 1).endTimeUs;
                return (j3 == C.TIME_UNSET || j3 <= j2 || j3 >= cuesWithTiming.startTimeUs) ? cuesWithTiming.startTimeUs : j3;
            }
        }
        long j4 = ((CuesWithTiming) Iterables.getLast(this.cuesWithTimingList)).endTimeUs;
        if (j4 == C.TIME_UNSET || j2 >= j4) {
            return Long.MIN_VALUE;
        }
        return j4;
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public long getPreviousCueChangeTimeUs(long j2) {
        if (this.cuesWithTimingList.isEmpty() || j2 < this.cuesWithTimingList.get(0).startTimeUs) {
            return C.TIME_UNSET;
        }
        for (int i2 = 1; i2 < this.cuesWithTimingList.size(); i2++) {
            long j3 = this.cuesWithTimingList.get(i2).startTimeUs;
            if (j2 == j3) {
                return j3;
            }
            if (j2 < j3) {
                CuesWithTiming cuesWithTiming = this.cuesWithTimingList.get(i2 - 1);
                long j4 = cuesWithTiming.endTimeUs;
                return (j4 == C.TIME_UNSET || j4 > j2) ? cuesWithTiming.startTimeUs : j4;
            }
        }
        CuesWithTiming cuesWithTiming2 = (CuesWithTiming) Iterables.getLast(this.cuesWithTimingList);
        long j5 = cuesWithTiming2.endTimeUs;
        return (j5 == C.TIME_UNSET || j2 < j5) ? cuesWithTiming2.startTimeUs : j5;
    }
}
