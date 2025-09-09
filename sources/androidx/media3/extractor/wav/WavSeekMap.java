package androidx.media3.extractor.wav;

import androidx.media3.common.util.Util;
import androidx.media3.extractor.SeekMap;
import androidx.media3.extractor.SeekPoint;

/* loaded from: classes2.dex */
final class WavSeekMap implements SeekMap {
    private final long blockCount;
    private final long durationUs;
    private final long firstBlockPosition;
    private final int framesPerBlock;
    private final WavFormat wavFormat;

    public WavSeekMap(WavFormat wavFormat, int i2, long j2, long j3) {
        this.wavFormat = wavFormat;
        this.framesPerBlock = i2;
        this.firstBlockPosition = j2;
        long j4 = (j3 - j2) / wavFormat.blockSize;
        this.blockCount = j4;
        this.durationUs = blockIndexToTimeUs(j4);
    }

    private long blockIndexToTimeUs(long j2) {
        return Util.scaleLargeTimestamp(j2 * this.framesPerBlock, 1000000L, this.wavFormat.frameRateHz);
    }

    @Override // androidx.media3.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // androidx.media3.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j2) {
        long jConstrainValue = Util.constrainValue((this.wavFormat.frameRateHz * j2) / (this.framesPerBlock * 1000000), 0L, this.blockCount - 1);
        long j3 = this.firstBlockPosition + (this.wavFormat.blockSize * jConstrainValue);
        long jBlockIndexToTimeUs = blockIndexToTimeUs(jConstrainValue);
        SeekPoint seekPoint = new SeekPoint(jBlockIndexToTimeUs, j3);
        if (jBlockIndexToTimeUs >= j2 || jConstrainValue == this.blockCount - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        long j4 = jConstrainValue + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(blockIndexToTimeUs(j4), this.firstBlockPosition + (this.wavFormat.blockSize * j4)));
    }

    @Override // androidx.media3.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }
}
