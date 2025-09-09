package androidx.media3.extractor.mp3;

import android.util.Pair;
import androidx.media3.common.C;
import androidx.media3.common.util.Util;
import androidx.media3.extractor.SeekMap;
import androidx.media3.extractor.SeekPoint;
import androidx.media3.extractor.metadata.id3.MlltFrame;

/* loaded from: classes2.dex */
final class MlltSeeker implements Seeker {
    private final long durationUs;
    private final long[] referencePositions;
    private final long[] referenceTimesMs;

    private MlltSeeker(long[] jArr, long[] jArr2, long j2) {
        this.referencePositions = jArr;
        this.referenceTimesMs = jArr2;
        this.durationUs = j2 == C.TIME_UNSET ? Util.msToUs(jArr2[jArr2.length - 1]) : j2;
    }

    public static MlltSeeker create(long j2, MlltFrame mlltFrame, long j3) {
        int length = mlltFrame.bytesDeviations.length;
        int i2 = length + 1;
        long[] jArr = new long[i2];
        long[] jArr2 = new long[i2];
        jArr[0] = j2;
        long j4 = 0;
        jArr2[0] = 0;
        for (int i3 = 1; i3 <= length; i3++) {
            int i4 = i3 - 1;
            j2 += mlltFrame.bytesBetweenReference + mlltFrame.bytesDeviations[i4];
            j4 += mlltFrame.millisecondsBetweenReference + mlltFrame.millisecondsDeviations[i4];
            jArr[i3] = j2;
            jArr2[i3] = j4;
        }
        return new MlltSeeker(jArr, jArr2, j3);
    }

    private static Pair<Long, Long> linearlyInterpolate(long j2, long[] jArr, long[] jArr2) {
        int iBinarySearchFloor = Util.binarySearchFloor(jArr, j2, true, true);
        long j3 = jArr[iBinarySearchFloor];
        long j4 = jArr2[iBinarySearchFloor];
        int i2 = iBinarySearchFloor + 1;
        if (i2 == jArr.length) {
            return Pair.create(Long.valueOf(j3), Long.valueOf(j4));
        }
        return Pair.create(Long.valueOf(j2), Long.valueOf(((long) ((jArr[i2] == j3 ? 0.0d : (j2 - j3) / (r6 - j3)) * (jArr2[i2] - j4))) + j4));
    }

    @Override // androidx.media3.extractor.mp3.Seeker
    public int getAverageBitrate() {
        return C.RATE_UNSET_INT;
    }

    @Override // androidx.media3.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return -1L;
    }

    @Override // androidx.media3.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // androidx.media3.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j2) {
        Pair<Long, Long> pairLinearlyInterpolate = linearlyInterpolate(Util.usToMs(Util.constrainValue(j2, 0L, this.durationUs)), this.referenceTimesMs, this.referencePositions);
        return new SeekMap.SeekPoints(new SeekPoint(Util.msToUs(((Long) pairLinearlyInterpolate.first).longValue()), ((Long) pairLinearlyInterpolate.second).longValue()));
    }

    @Override // androidx.media3.extractor.mp3.Seeker
    public long getTimeUs(long j2) {
        return Util.msToUs(((Long) linearlyInterpolate(j2, this.referencePositions, this.referenceTimesMs).second).longValue());
    }

    @Override // androidx.media3.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }
}
