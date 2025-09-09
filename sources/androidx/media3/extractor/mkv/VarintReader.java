package androidx.media3.extractor.mkv;

import androidx.media3.extractor.ExtractorInput;
import java.io.IOException;

/* loaded from: classes2.dex */
final class VarintReader {
    private static final int STATE_BEGIN_READING = 0;
    private static final int STATE_READ_CONTENTS = 1;
    private static final long[] VARINT_LENGTH_MASKS = {128, 64, 32, 16, 8, 4, 2, 1};
    private int length;
    private final byte[] scratch = new byte[8];
    private int state;

    public static long assembleVarint(byte[] bArr, int i2, boolean z2) {
        long j2 = bArr[0] & 255;
        if (z2) {
            j2 &= ~VARINT_LENGTH_MASKS[i2 - 1];
        }
        for (int i3 = 1; i3 < i2; i3++) {
            j2 = (j2 << 8) | (bArr[i3] & 255);
        }
        return j2;
    }

    public static int parseUnsignedVarintLength(int i2) {
        int i3 = 0;
        while (true) {
            long[] jArr = VARINT_LENGTH_MASKS;
            if (i3 >= jArr.length) {
                return -1;
            }
            if ((jArr[i3] & i2) != 0) {
                return i3 + 1;
            }
            i3++;
        }
    }

    public int getLastLength() {
        return this.length;
    }

    public long readUnsignedVarint(ExtractorInput extractorInput, boolean z2, boolean z3, int i2) throws IOException {
        if (this.state == 0) {
            if (!extractorInput.readFully(this.scratch, 0, 1, z2)) {
                return -1L;
            }
            int unsignedVarintLength = parseUnsignedVarintLength(this.scratch[0] & 255);
            this.length = unsignedVarintLength;
            if (unsignedVarintLength == -1) {
                throw new IllegalStateException("No valid varint length mask found");
            }
            this.state = 1;
        }
        int i3 = this.length;
        if (i3 > i2) {
            this.state = 0;
            return -2L;
        }
        if (i3 != 1) {
            extractorInput.readFully(this.scratch, 1, i3 - 1);
        }
        this.state = 0;
        return assembleVarint(this.scratch, this.length, z3);
    }

    public void reset() {
        this.state = 0;
        this.length = 0;
    }
}
