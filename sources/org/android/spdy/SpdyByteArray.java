package org.android.spdy;

import java.util.Arrays;

/* loaded from: classes5.dex */
public class SpdyByteArray implements Comparable<SpdyByteArray> {
    private byte[] byteArray;
    int dataLength;
    int length;

    SpdyByteArray() {
        this.byteArray = null;
        this.length = 0;
        this.dataLength = 0;
    }

    public byte[] getByteArray() {
        return this.byteArray;
    }

    public int getDataLength() {
        return this.dataLength;
    }

    public void recycle() {
        Arrays.fill(this.byteArray, (byte) 0);
        this.dataLength = 0;
        SpdyBytePool.getInstance().recycle(this);
    }

    void setByteArrayDataLength(int i2) {
        this.dataLength = i2;
    }

    @Override // java.lang.Comparable
    public int compareTo(SpdyByteArray spdyByteArray) {
        int i2 = this.length;
        int i3 = spdyByteArray.length;
        if (i2 != i3) {
            return i2 - i3;
        }
        if (this.byteArray == null) {
            return -1;
        }
        if (spdyByteArray.byteArray == null) {
            return 1;
        }
        return hashCode() - spdyByteArray.hashCode();
    }

    SpdyByteArray(int i2) {
        this.byteArray = new byte[i2];
        this.length = i2;
        this.dataLength = 0;
    }
}
