package androidx.media3.common.util;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.media3.common.C;

@UnstableApi
/* loaded from: classes.dex */
public final class ConstantRateTimestampIterator implements TimestampIterator {
    private final long endPositionUs;
    private final float frameRate;
    private int framesAdded;
    private final double framesDurationUs;
    private final long startPositionUs;
    private final int totalNumberOfFramesToAdd;

    public ConstantRateTimestampIterator(@IntRange(from = 1) long j2, @FloatRange(from = 0.0d, fromInclusive = false) float f2) {
        this(0L, j2, f2);
    }

    private long getTimestampUsAfter(int i2) {
        long jRound = this.startPositionUs + Math.round(this.framesDurationUs * i2);
        Assertions.checkState(jRound >= 0);
        return jRound;
    }

    @Override // androidx.media3.common.util.TimestampIterator
    public long getLastTimestampUs() {
        int i2 = this.totalNumberOfFramesToAdd;
        return i2 == 0 ? C.TIME_UNSET : getTimestampUsAfter(i2 - 1);
    }

    @Override // androidx.media3.common.util.TimestampIterator
    public boolean hasNext() {
        return this.framesAdded < this.totalNumberOfFramesToAdd;
    }

    @Override // androidx.media3.common.util.TimestampIterator
    public long next() {
        Assertions.checkState(hasNext());
        int i2 = this.framesAdded;
        this.framesAdded = i2 + 1;
        return getTimestampUsAfter(i2);
    }

    public ConstantRateTimestampIterator(@IntRange(from = 0) long j2, @IntRange(from = 1) long j3, @FloatRange(from = 0.0d, fromInclusive = false) float f2) {
        boolean z2 = false;
        Assertions.checkArgument(j3 > 0);
        Assertions.checkArgument(f2 > 0.0f);
        if (0 <= j2 && j2 < j3) {
            z2 = true;
        }
        Assertions.checkArgument(z2);
        this.startPositionUs = j2;
        this.endPositionUs = j3;
        this.frameRate = f2;
        this.totalNumberOfFramesToAdd = Math.round(((j3 - j2) / 1000000.0f) * f2);
        this.framesDurationUs = 1000000.0f / f2;
    }

    @Override // androidx.media3.common.util.TimestampIterator
    public ConstantRateTimestampIterator copyOf() {
        return new ConstantRateTimestampIterator(this.startPositionUs, this.endPositionUs, this.frameRate);
    }
}
