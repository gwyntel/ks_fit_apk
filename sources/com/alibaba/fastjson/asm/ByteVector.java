package com.alibaba.fastjson.asm;

/* loaded from: classes2.dex */
public class ByteVector {
    public byte[] data;
    public int length;

    public ByteVector() {
        this.data = new byte[64];
    }

    private void enlarge(int i2) {
        byte[] bArr = this.data;
        int length = bArr.length * 2;
        int i3 = this.length;
        int i4 = i2 + i3;
        if (length <= i4) {
            length = i4;
        }
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, i3);
        this.data = bArr2;
    }

    ByteVector put11(int i2, int i3) {
        int i4 = this.length;
        if (i4 + 2 > this.data.length) {
            enlarge(2);
        }
        byte[] bArr = this.data;
        bArr[i4] = (byte) i2;
        bArr[i4 + 1] = (byte) i3;
        this.length = i4 + 2;
        return this;
    }

    public ByteVector put12(int i2, int i3) {
        int i4 = this.length;
        if (i4 + 3 > this.data.length) {
            enlarge(3);
        }
        byte[] bArr = this.data;
        bArr[i4] = (byte) i2;
        bArr[i4 + 1] = (byte) (i3 >>> 8);
        bArr[i4 + 2] = (byte) i3;
        this.length = i4 + 3;
        return this;
    }

    public ByteVector putByte(int i2) {
        int i3 = this.length;
        int i4 = i3 + 1;
        if (i4 > this.data.length) {
            enlarge(1);
        }
        this.data[i3] = (byte) i2;
        this.length = i4;
        return this;
    }

    public ByteVector putByteArray(byte[] bArr, int i2, int i3) {
        if (this.length + i3 > this.data.length) {
            enlarge(i3);
        }
        if (bArr != null) {
            System.arraycopy(bArr, i2, this.data, this.length, i3);
        }
        this.length += i3;
        return this;
    }

    public ByteVector putInt(int i2) {
        int i3 = this.length;
        if (i3 + 4 > this.data.length) {
            enlarge(4);
        }
        byte[] bArr = this.data;
        bArr[i3] = (byte) (i2 >>> 24);
        bArr[i3 + 1] = (byte) (i2 >>> 16);
        bArr[i3 + 2] = (byte) (i2 >>> 8);
        bArr[i3 + 3] = (byte) i2;
        this.length = i3 + 4;
        return this;
    }

    public ByteVector putShort(int i2) {
        int i3 = this.length;
        if (i3 + 2 > this.data.length) {
            enlarge(2);
        }
        byte[] bArr = this.data;
        bArr[i3] = (byte) (i2 >>> 8);
        bArr[i3 + 1] = (byte) i2;
        this.length = i3 + 2;
        return this;
    }

    public ByteVector putUTF8(String str) {
        int length = str.length();
        int i2 = this.length;
        if (i2 + 2 + length > this.data.length) {
            enlarge(length + 2);
        }
        byte[] bArr = this.data;
        int i3 = i2 + 1;
        bArr[i2] = (byte) (length >>> 8);
        int i4 = i2 + 2;
        bArr[i3] = (byte) length;
        int i5 = 0;
        while (i5 < length) {
            char cCharAt = str.charAt(i5);
            if ((cCharAt < 1 || cCharAt > 127) && (cCharAt < 19968 || cCharAt > 40959)) {
                throw new UnsupportedOperationException();
            }
            bArr[i4] = (byte) cCharAt;
            i5++;
            i4++;
        }
        this.length = i4;
        return this;
    }

    public ByteVector(int i2) {
        this.data = new byte[i2];
    }
}
