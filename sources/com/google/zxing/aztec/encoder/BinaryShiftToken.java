package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
final class BinaryShiftToken extends Token {
    private final short binaryShiftByteCount;
    private final short binaryShiftStart;

    BinaryShiftToken(Token token, int i2, int i3) {
        super(token);
        this.binaryShiftStart = (short) i2;
        this.binaryShiftByteCount = (short) i3;
    }

    @Override // com.google.zxing.aztec.encoder.Token
    public void appendTo(BitArray bitArray, byte[] bArr) {
        int i2 = 0;
        while (true) {
            short s2 = this.binaryShiftByteCount;
            if (i2 >= s2) {
                return;
            }
            if (i2 == 0 || (i2 == 31 && s2 <= 62)) {
                bitArray.appendBits(31, 5);
                short s3 = this.binaryShiftByteCount;
                if (s3 > 62) {
                    bitArray.appendBits(s3 - 31, 16);
                } else if (i2 == 0) {
                    bitArray.appendBits(Math.min((int) s3, 31), 5);
                } else {
                    bitArray.appendBits(s3 - 31, 5);
                }
            }
            bitArray.appendBits(bArr[this.binaryShiftStart + i2], 8);
            i2++;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("<");
        sb.append((int) this.binaryShiftStart);
        sb.append("::");
        sb.append((this.binaryShiftStart + this.binaryShiftByteCount) - 1);
        sb.append(Typography.greater);
        return sb.toString();
    }
}
