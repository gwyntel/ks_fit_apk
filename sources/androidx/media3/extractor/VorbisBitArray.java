package androidx.media3.extractor;

import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;

@UnstableApi
/* loaded from: classes2.dex */
public final class VorbisBitArray {
    private int bitOffset;
    private final int byteLimit;
    private int byteOffset;
    private final byte[] data;

    public VorbisBitArray(byte[] bArr) {
        this.data = bArr;
        this.byteLimit = bArr.length;
    }

    private void assertValidOffset() {
        int i2;
        int i3 = this.byteOffset;
        Assertions.checkState(i3 >= 0 && (i3 < (i2 = this.byteLimit) || (i3 == i2 && this.bitOffset == 0)));
    }

    public int bitsLeft() {
        return ((this.byteLimit - this.byteOffset) * 8) - this.bitOffset;
    }

    public int getPosition() {
        return (this.byteOffset * 8) + this.bitOffset;
    }

    public boolean readBit() {
        boolean z2 = (((this.data[this.byteOffset] & 255) >> this.bitOffset) & 1) == 1;
        skipBits(1);
        return z2;
    }

    public int readBits(int i2) {
        int i3 = this.byteOffset;
        int iMin = Math.min(i2, 8 - this.bitOffset);
        int i4 = i3 + 1;
        int i5 = ((this.data[i3] & 255) >> this.bitOffset) & (255 >> (8 - iMin));
        while (iMin < i2) {
            i5 |= (this.data[i4] & 255) << iMin;
            iMin += 8;
            i4++;
        }
        int i6 = i5 & ((-1) >>> (32 - i2));
        skipBits(i2);
        return i6;
    }

    public void reset() {
        this.byteOffset = 0;
        this.bitOffset = 0;
    }

    public void setPosition(int i2) {
        int i3 = i2 / 8;
        this.byteOffset = i3;
        this.bitOffset = i2 - (i3 * 8);
        assertValidOffset();
    }

    public void skipBits(int i2) {
        int i3 = i2 / 8;
        int i4 = this.byteOffset + i3;
        this.byteOffset = i4;
        int i5 = this.bitOffset + (i2 - (i3 * 8));
        this.bitOffset = i5;
        if (i5 > 7) {
            this.byteOffset = i4 + 1;
            this.bitOffset = i5 - 8;
        }
        assertValidOffset();
    }
}
