package androidx.media3.common.util;

import androidx.core.view.MotionEventCompat;
import com.google.common.base.Charsets;
import com.google.errorprone.annotations.CheckReturnValue;
import java.nio.charset.Charset;

@CheckReturnValue
@UnstableApi
/* loaded from: classes.dex */
public final class ParsableBitArray {
    private int bitOffset;
    private int byteLimit;
    private int byteOffset;
    public byte[] data;

    public ParsableBitArray() {
        this.data = Util.EMPTY_BYTE_ARRAY;
    }

    private void assertValidOffset() {
        int i2;
        int i3 = this.byteOffset;
        Assertions.checkState(i3 >= 0 && (i3 < (i2 = this.byteLimit) || (i3 == i2 && this.bitOffset == 0)));
    }

    public int bitsLeft() {
        return ((this.byteLimit - this.byteOffset) * 8) - this.bitOffset;
    }

    public void byteAlign() {
        if (this.bitOffset == 0) {
            return;
        }
        this.bitOffset = 0;
        this.byteOffset++;
        assertValidOffset();
    }

    public int getBytePosition() {
        Assertions.checkState(this.bitOffset == 0);
        return this.byteOffset;
    }

    public int getPosition() {
        return (this.byteOffset * 8) + this.bitOffset;
    }

    public void putInt(int i2, int i3) {
        if (i3 < 32) {
            i2 &= (1 << i3) - 1;
        }
        int iMin = Math.min(8 - this.bitOffset, i3);
        int i4 = this.bitOffset;
        int i5 = (8 - i4) - iMin;
        int i6 = (MotionEventCompat.ACTION_POINTER_INDEX_MASK >> i4) | ((1 << i5) - 1);
        byte[] bArr = this.data;
        int i7 = this.byteOffset;
        byte b2 = (byte) (i6 & bArr[i7]);
        bArr[i7] = b2;
        int i8 = i3 - iMin;
        bArr[i7] = (byte) (b2 | ((i2 >>> i8) << i5));
        int i9 = i7 + 1;
        while (i8 > 8) {
            this.data[i9] = (byte) (i2 >>> (i8 - 8));
            i8 -= 8;
            i9++;
        }
        int i10 = 8 - i8;
        byte[] bArr2 = this.data;
        byte b3 = (byte) (bArr2[i9] & ((1 << i10) - 1));
        bArr2[i9] = b3;
        bArr2[i9] = (byte) (((i2 & ((1 << i8) - 1)) << i10) | b3);
        skipBits(i3);
        assertValidOffset();
    }

    public boolean readBit() {
        boolean z2 = (this.data[this.byteOffset] & (128 >> this.bitOffset)) != 0;
        skipBit();
        return z2;
    }

    public int readBits(int i2) {
        int i3;
        if (i2 == 0) {
            return 0;
        }
        this.bitOffset += i2;
        int i4 = 0;
        while (true) {
            i3 = this.bitOffset;
            if (i3 <= 8) {
                break;
            }
            int i5 = i3 - 8;
            this.bitOffset = i5;
            byte[] bArr = this.data;
            int i6 = this.byteOffset;
            this.byteOffset = i6 + 1;
            i4 |= (bArr[i6] & 255) << i5;
        }
        byte[] bArr2 = this.data;
        int i7 = this.byteOffset;
        int i8 = ((-1) >>> (32 - i2)) & (i4 | ((bArr2[i7] & 255) >> (8 - i3)));
        if (i3 == 8) {
            this.bitOffset = 0;
            this.byteOffset = i7 + 1;
        }
        assertValidOffset();
        return i8;
    }

    public long readBitsToLong(int i2) {
        return i2 <= 32 ? Util.toUnsignedLong(readBits(i2)) : Util.toLong(readBits(i2 - 32), readBits(32));
    }

    public void readBytes(byte[] bArr, int i2, int i3) {
        Assertions.checkState(this.bitOffset == 0);
        System.arraycopy(this.data, this.byteOffset, bArr, i2, i3);
        this.byteOffset += i3;
        assertValidOffset();
    }

    public String readBytesAsString(int i2) {
        return readBytesAsString(i2, Charsets.UTF_8);
    }

    public void reset(byte[] bArr) {
        reset(bArr, bArr.length);
    }

    public void setPosition(int i2) {
        int i3 = i2 / 8;
        this.byteOffset = i3;
        this.bitOffset = i2 - (i3 * 8);
        assertValidOffset();
    }

    public void skipBit() {
        int i2 = this.bitOffset + 1;
        this.bitOffset = i2;
        if (i2 == 8) {
            this.bitOffset = 0;
            this.byteOffset++;
        }
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

    public void skipBytes(int i2) {
        Assertions.checkState(this.bitOffset == 0);
        this.byteOffset += i2;
        assertValidOffset();
    }

    public String readBytesAsString(int i2, Charset charset) {
        byte[] bArr = new byte[i2];
        readBytes(bArr, 0, i2);
        return new String(bArr, charset);
    }

    public void reset(ParsableByteArray parsableByteArray) {
        reset(parsableByteArray.getData(), parsableByteArray.limit());
        setPosition(parsableByteArray.getPosition() * 8);
    }

    public ParsableBitArray(byte[] bArr) {
        this(bArr, bArr.length);
    }

    public ParsableBitArray(byte[] bArr, int i2) {
        this.data = bArr;
        this.byteLimit = i2;
    }

    public void reset(byte[] bArr, int i2) {
        this.data = bArr;
        this.byteOffset = 0;
        this.bitOffset = 0;
        this.byteLimit = i2;
    }

    public void readBits(byte[] bArr, int i2, int i3) {
        int i4 = (i3 >> 3) + i2;
        while (i2 < i4) {
            byte[] bArr2 = this.data;
            int i5 = this.byteOffset;
            int i6 = i5 + 1;
            this.byteOffset = i6;
            byte b2 = bArr2[i5];
            int i7 = this.bitOffset;
            byte b3 = (byte) (b2 << i7);
            bArr[i2] = b3;
            bArr[i2] = (byte) (((255 & bArr2[i6]) >> (8 - i7)) | b3);
            i2++;
        }
        int i8 = i3 & 7;
        if (i8 == 0) {
            return;
        }
        byte b4 = (byte) (bArr[i4] & (255 >> i8));
        bArr[i4] = b4;
        int i9 = this.bitOffset;
        if (i9 + i8 > 8) {
            byte[] bArr3 = this.data;
            int i10 = this.byteOffset;
            this.byteOffset = i10 + 1;
            bArr[i4] = (byte) (b4 | ((bArr3[i10] & 255) << i9));
            this.bitOffset = i9 - 8;
        }
        int i11 = this.bitOffset + i8;
        this.bitOffset = i11;
        byte[] bArr4 = this.data;
        int i12 = this.byteOffset;
        bArr[i4] = (byte) (((byte) (((255 & bArr4[i12]) >> (8 - i11)) << (8 - i8))) | bArr[i4]);
        if (i11 == 8) {
            this.bitOffset = 0;
            this.byteOffset = i12 + 1;
        }
        assertValidOffset();
    }
}
