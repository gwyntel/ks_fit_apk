package androidx.media3.extractor.mp3;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;
import androidx.media3.extractor.MpegAudioUtil;
import androidx.media3.extractor.SeekMap;
import androidx.media3.extractor.SeekPoint;

/* loaded from: classes2.dex */
final class XingSeeker implements Seeker {
    private static final String TAG = "XingSeeker";
    private final int bitrate;
    private final long dataEndPosition;
    private final long dataSize;
    private final long dataStartPosition;
    private final long durationUs;

    @Nullable
    private final long[] tableOfContents;
    private final int xingFrameSize;

    private XingSeeker(long j2, int i2, long j3, int i3) {
        this(j2, i2, j3, i3, -1L, null);
    }

    @Nullable
    public static XingSeeker create(XingFrame xingFrame, long j2) {
        long[] jArr;
        long jComputeDurationUs = xingFrame.computeDurationUs();
        if (jComputeDurationUs == C.TIME_UNSET) {
            return null;
        }
        long j3 = xingFrame.dataSize;
        if (j3 == -1 || (jArr = xingFrame.tableOfContents) == null) {
            MpegAudioUtil.Header header = xingFrame.header;
            return new XingSeeker(j2, header.frameSize, jComputeDurationUs, header.bitrate);
        }
        MpegAudioUtil.Header header2 = xingFrame.header;
        return new XingSeeker(j2, header2.frameSize, jComputeDurationUs, header2.bitrate, j3, jArr);
    }

    private long getTimeUsForTableIndex(int i2) {
        return (this.durationUs * i2) / 100;
    }

    @Override // androidx.media3.extractor.mp3.Seeker
    public int getAverageBitrate() {
        return this.bitrate;
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
        if (!isSeekable()) {
            return new SeekMap.SeekPoints(new SeekPoint(0L, this.dataStartPosition + this.xingFrameSize));
        }
        long jConstrainValue = Util.constrainValue(j2, 0L, this.durationUs);
        double d2 = (jConstrainValue * 100.0d) / this.durationUs;
        double d3 = 0.0d;
        if (d2 > 0.0d) {
            if (d2 >= 100.0d) {
                d3 = 256.0d;
            } else {
                int i2 = (int) d2;
                double d4 = ((long[]) Assertions.checkStateNotNull(this.tableOfContents))[i2];
                d3 = d4 + ((d2 - i2) * ((i2 == 99 ? 256.0d : r3[i2 + 1]) - d4));
            }
        }
        return new SeekMap.SeekPoints(new SeekPoint(jConstrainValue, this.dataStartPosition + Util.constrainValue(Math.round((d3 / 256.0d) * this.dataSize), this.xingFrameSize, this.dataSize - 1)));
    }

    @Override // androidx.media3.extractor.mp3.Seeker
    public long getTimeUs(long j2) {
        long j3 = j2 - this.dataStartPosition;
        if (!isSeekable() || j3 <= this.xingFrameSize) {
            return 0L;
        }
        long[] jArr = (long[]) Assertions.checkStateNotNull(this.tableOfContents);
        double d2 = (j3 * 256.0d) / this.dataSize;
        int iBinarySearchFloor = Util.binarySearchFloor(jArr, (long) d2, true, true);
        long timeUsForTableIndex = getTimeUsForTableIndex(iBinarySearchFloor);
        long j4 = jArr[iBinarySearchFloor];
        int i2 = iBinarySearchFloor + 1;
        long timeUsForTableIndex2 = getTimeUsForTableIndex(i2);
        return timeUsForTableIndex + Math.round((j4 == (iBinarySearchFloor == 99 ? 256L : jArr[i2]) ? 0.0d : (d2 - j4) / (r0 - j4)) * (timeUsForTableIndex2 - timeUsForTableIndex));
    }

    @Override // androidx.media3.extractor.SeekMap
    public boolean isSeekable() {
        return this.tableOfContents != null;
    }

    private XingSeeker(long j2, int i2, long j3, int i3, long j4, @Nullable long[] jArr) {
        this.dataStartPosition = j2;
        this.xingFrameSize = i2;
        this.durationUs = j3;
        this.bitrate = i3;
        this.dataSize = j4;
        this.tableOfContents = jArr;
        this.dataEndPosition = j4 != -1 ? j2 + j4 : -1L;
    }
}
