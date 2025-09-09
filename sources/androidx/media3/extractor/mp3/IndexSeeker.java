package androidx.media3.extractor.mp3;

import androidx.media3.common.C;
import androidx.media3.common.util.LongArray;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.audio.SilenceSkippingAudioProcessor;
import androidx.media3.extractor.SeekMap;
import androidx.media3.extractor.SeekPoint;
import java.math.RoundingMode;

/* loaded from: classes2.dex */
final class IndexSeeker implements Seeker {
    private final int averageBitrate;
    private final long dataEndPosition;
    private long durationUs;
    private final LongArray positions;
    private final LongArray timesUs;

    public IndexSeeker(long j2, long j3, long j4) {
        this.durationUs = j2;
        this.dataEndPosition = j4;
        LongArray longArray = new LongArray();
        this.timesUs = longArray;
        LongArray longArray2 = new LongArray();
        this.positions = longArray2;
        longArray.add(0L);
        longArray2.add(j3);
        int i2 = C.RATE_UNSET_INT;
        if (j2 == C.TIME_UNSET) {
            this.averageBitrate = C.RATE_UNSET_INT;
            return;
        }
        long jScaleLargeValue = Util.scaleLargeValue(j3 - j4, 8L, j2, RoundingMode.HALF_UP);
        if (jScaleLargeValue > 0 && jScaleLargeValue <= 2147483647L) {
            i2 = (int) jScaleLargeValue;
        }
        this.averageBitrate = i2;
    }

    void a(long j2) {
        this.durationUs = j2;
    }

    @Override // androidx.media3.extractor.mp3.Seeker
    public int getAverageBitrate() {
        return this.averageBitrate;
    }

    @Override // androidx.media3.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return this.dataEndPosition;
    }

    @Override // androidx.media3.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // androidx.media3.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j2) {
        int iBinarySearchFloor = Util.binarySearchFloor(this.timesUs, j2, true, true);
        SeekPoint seekPoint = new SeekPoint(this.timesUs.get(iBinarySearchFloor), this.positions.get(iBinarySearchFloor));
        if (seekPoint.timeUs == j2 || iBinarySearchFloor == this.timesUs.size() - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i2 = iBinarySearchFloor + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(this.timesUs.get(i2), this.positions.get(i2)));
    }

    @Override // androidx.media3.extractor.mp3.Seeker
    public long getTimeUs(long j2) {
        return this.timesUs.get(Util.binarySearchFloor(this.positions, j2, true, true));
    }

    @Override // androidx.media3.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    public boolean isTimeUsInIndex(long j2) {
        LongArray longArray = this.timesUs;
        return j2 - longArray.get(longArray.size() - 1) < SilenceSkippingAudioProcessor.DEFAULT_MINIMUM_SILENCE_DURATION_US;
    }

    public void maybeAddSeekPoint(long j2, long j3) {
        if (isTimeUsInIndex(j2)) {
            return;
        }
        this.timesUs.add(j2);
        this.positions.add(j3);
    }
}
