package androidx.media3.extractor.mp3;

import androidx.annotation.Nullable;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import androidx.media3.extractor.MpegAudioUtil;
import androidx.media3.extractor.SeekMap;
import androidx.media3.extractor.SeekPoint;

/* loaded from: classes2.dex */
final class VbriSeeker implements Seeker {
    private static final String TAG = "VbriSeeker";
    private final int bitrate;
    private final long dataEndPosition;
    private final long durationUs;
    private final long[] positions;
    private final long[] timesUs;

    private VbriSeeker(long[] jArr, long[] jArr2, long j2, long j3, int i2) {
        this.timesUs = jArr;
        this.positions = jArr2;
        this.durationUs = j2;
        this.dataEndPosition = j3;
        this.bitrate = i2;
    }

    @Nullable
    public static VbriSeeker create(long j2, long j3, MpegAudioUtil.Header header, ParsableByteArray parsableByteArray) {
        int unsignedByte;
        parsableByteArray.skipBytes(10);
        int i2 = parsableByteArray.readInt();
        if (i2 <= 0) {
            return null;
        }
        int i3 = header.sampleRate;
        long jScaleLargeTimestamp = Util.scaleLargeTimestamp(i2, (i3 >= 32000 ? 1152 : 576) * 1000000, i3);
        int unsignedShort = parsableByteArray.readUnsignedShort();
        int unsignedShort2 = parsableByteArray.readUnsignedShort();
        int unsignedShort3 = parsableByteArray.readUnsignedShort();
        parsableByteArray.skipBytes(2);
        long j4 = j3 + header.frameSize;
        long[] jArr = new long[unsignedShort];
        long[] jArr2 = new long[unsignedShort];
        int i4 = 0;
        long j5 = j3;
        while (i4 < unsignedShort) {
            int i5 = unsignedShort2;
            long j6 = j4;
            jArr[i4] = (i4 * jScaleLargeTimestamp) / unsignedShort;
            jArr2[i4] = Math.max(j5, j6);
            if (unsignedShort3 == 1) {
                unsignedByte = parsableByteArray.readUnsignedByte();
            } else if (unsignedShort3 == 2) {
                unsignedByte = parsableByteArray.readUnsignedShort();
            } else if (unsignedShort3 == 3) {
                unsignedByte = parsableByteArray.readUnsignedInt24();
            } else {
                if (unsignedShort3 != 4) {
                    return null;
                }
                unsignedByte = parsableByteArray.readUnsignedIntToInt();
            }
            j5 += unsignedByte * i5;
            i4++;
            unsignedShort = unsignedShort;
            unsignedShort2 = i5;
            j4 = j6;
        }
        if (j2 != -1 && j2 != j5) {
            Log.w(TAG, "VBRI data size mismatch: " + j2 + ", " + j5);
        }
        return new VbriSeeker(jArr, jArr2, jScaleLargeTimestamp, j5, header.bitrate);
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
        int iBinarySearchFloor = Util.binarySearchFloor(this.timesUs, j2, true, true);
        SeekPoint seekPoint = new SeekPoint(this.timesUs[iBinarySearchFloor], this.positions[iBinarySearchFloor]);
        if (seekPoint.timeUs >= j2 || iBinarySearchFloor == this.timesUs.length - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i2 = iBinarySearchFloor + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(this.timesUs[i2], this.positions[i2]));
    }

    @Override // androidx.media3.extractor.mp3.Seeker
    public long getTimeUs(long j2) {
        return this.timesUs[Util.binarySearchFloor(this.positions, j2, true, true)];
    }

    @Override // androidx.media3.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }
}
