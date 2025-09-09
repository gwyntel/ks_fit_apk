package androidx.media3.extractor.mp3;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import androidx.media3.extractor.MpegAudioUtil;
import anet.channel.entity.EventType;

/* loaded from: classes2.dex */
final class XingFrame {
    public final long dataSize;
    public final int encoderDelay;
    public final int encoderPadding;
    public final long frameCount;
    public final MpegAudioUtil.Header header;

    @Nullable
    public final long[] tableOfContents;

    private XingFrame(MpegAudioUtil.Header header, long j2, long j3, @Nullable long[] jArr, int i2, int i3) {
        this.header = new MpegAudioUtil.Header(header);
        this.frameCount = j2;
        this.dataSize = j3;
        this.tableOfContents = jArr;
        this.encoderDelay = i2;
        this.encoderPadding = i3;
    }

    public static XingFrame parse(MpegAudioUtil.Header header, ParsableByteArray parsableByteArray) {
        long[] jArr;
        int i2;
        int i3;
        int i4 = parsableByteArray.readInt();
        int unsignedIntToInt = (i4 & 1) != 0 ? parsableByteArray.readUnsignedIntToInt() : -1;
        long unsignedInt = (i4 & 2) != 0 ? parsableByteArray.readUnsignedInt() : -1L;
        if ((i4 & 4) == 4) {
            long[] jArr2 = new long[100];
            for (int i5 = 0; i5 < 100; i5++) {
                jArr2[i5] = parsableByteArray.readUnsignedByte();
            }
            jArr = jArr2;
        } else {
            jArr = null;
        }
        if ((i4 & 8) != 0) {
            parsableByteArray.skipBytes(4);
        }
        if (parsableByteArray.bytesLeft() >= 24) {
            parsableByteArray.skipBytes(21);
            int unsignedInt24 = parsableByteArray.readUnsignedInt24();
            i3 = unsignedInt24 & EventType.ALL;
            i2 = (16773120 & unsignedInt24) >> 12;
        } else {
            i2 = -1;
            i3 = -1;
        }
        return new XingFrame(header, unsignedIntToInt, unsignedInt, jArr, i2, i3);
    }

    public long computeDurationUs() {
        long j2 = this.frameCount;
        if (j2 == -1 || j2 == 0) {
            return C.TIME_UNSET;
        }
        return Util.sampleCountToDurationUs((j2 * r2.samplesPerFrame) - 1, this.header.sampleRate);
    }
}
