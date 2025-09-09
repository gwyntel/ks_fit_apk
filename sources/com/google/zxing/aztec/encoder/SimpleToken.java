package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
final class SimpleToken extends Token {
    private final short bitCount;
    private final short value;

    SimpleToken(Token token, int i2, int i3) {
        super(token);
        this.value = (short) i2;
        this.bitCount = (short) i3;
    }

    @Override // com.google.zxing.aztec.encoder.Token
    void appendTo(BitArray bitArray, byte[] bArr) {
        bitArray.appendBits(this.value, this.bitCount);
    }

    public String toString() {
        short s2 = this.value;
        short s3 = this.bitCount;
        return "<" + Integer.toBinaryString((s2 & ((1 << s3) - 1)) | (1 << s3) | (1 << this.bitCount)).substring(1) + Typography.greater;
    }
}
