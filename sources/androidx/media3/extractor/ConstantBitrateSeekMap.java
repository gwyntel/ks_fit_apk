package androidx.media3.extractor;

import androidx.media3.common.C;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.extractor.SeekMap;

@UnstableApi
/* loaded from: classes2.dex */
public class ConstantBitrateSeekMap implements SeekMap {
    private final boolean allowSeeksIfLengthUnknown;
    private final int bitrate;
    private final long dataSize;
    private final long durationUs;
    private final long firstFrameBytePosition;
    private final int frameSize;
    private final long inputLength;

    public ConstantBitrateSeekMap(long j2, long j3, int i2, int i3) {
        this(j2, j3, i2, i3, false);
    }

    private long getFramePositionForTimeUs(long j2) {
        int i2 = this.frameSize;
        long jMin = (((j2 * this.bitrate) / 8000000) / i2) * i2;
        long j3 = this.dataSize;
        if (j3 != -1) {
            jMin = Math.min(jMin, j3 - i2);
        }
        return this.firstFrameBytePosition + Math.max(jMin, 0L);
    }

    @Override // androidx.media3.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // androidx.media3.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j2) {
        if (this.dataSize == -1 && !this.allowSeeksIfLengthUnknown) {
            return new SeekMap.SeekPoints(new SeekPoint(0L, this.firstFrameBytePosition));
        }
        long framePositionForTimeUs = getFramePositionForTimeUs(j2);
        long timeUsAtPosition = getTimeUsAtPosition(framePositionForTimeUs);
        SeekPoint seekPoint = new SeekPoint(timeUsAtPosition, framePositionForTimeUs);
        if (this.dataSize != -1 && timeUsAtPosition < j2) {
            int i2 = this.frameSize;
            if (i2 + framePositionForTimeUs < this.inputLength) {
                long j3 = framePositionForTimeUs + i2;
                return new SeekMap.SeekPoints(seekPoint, new SeekPoint(getTimeUsAtPosition(j3), j3));
            }
        }
        return new SeekMap.SeekPoints(seekPoint);
    }

    public long getTimeUsAtPosition(long j2) {
        return getTimeUsAtPosition(j2, this.firstFrameBytePosition, this.bitrate);
    }

    @Override // androidx.media3.extractor.SeekMap
    public boolean isSeekable() {
        return this.dataSize != -1 || this.allowSeeksIfLengthUnknown;
    }

    public ConstantBitrateSeekMap(long j2, long j3, int i2, int i3, boolean z2) {
        this.inputLength = j2;
        this.firstFrameBytePosition = j3;
        this.frameSize = i3 == -1 ? 1 : i3;
        this.bitrate = i2;
        this.allowSeeksIfLengthUnknown = z2;
        if (j2 == -1) {
            this.dataSize = -1L;
            this.durationUs = C.TIME_UNSET;
        } else {
            this.dataSize = j2 - j3;
            this.durationUs = getTimeUsAtPosition(j2, j3, i2);
        }
    }

    private static long getTimeUsAtPosition(long j2, long j3, int i2) {
        return (Math.max(0L, j2 - j3) * 8000000) / i2;
    }
}
