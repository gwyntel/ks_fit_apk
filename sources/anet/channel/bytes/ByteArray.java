package anet.channel.bytes;

import anet.channel.bytes.a;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class ByteArray implements Comparable<ByteArray> {
    final byte[] buffer;
    int bufferLength;
    int dataLength;

    private ByteArray(byte[] bArr, int i2) {
        bArr = bArr == null ? new byte[i2] : bArr;
        this.buffer = bArr;
        this.bufferLength = bArr.length;
        this.dataLength = i2;
    }

    public static ByteArray create(int i2) {
        return new ByteArray(null, i2);
    }

    public static ByteArray wrap(byte[] bArr, int i2) {
        if (bArr == null || i2 < 0 || i2 > bArr.length) {
            return null;
        }
        return new ByteArray(bArr, i2);
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public int getBufferLength() {
        return this.bufferLength;
    }

    public int getDataLength() {
        return this.dataLength;
    }

    public int readFrom(InputStream inputStream) throws IOException {
        int i2 = inputStream.read(this.buffer, 0, this.bufferLength);
        this.dataLength = i2 != -1 ? i2 : 0;
        return i2;
    }

    public void recycle() {
        if (this.bufferLength == 0) {
            return;
        }
        a.C0009a.f6699a.a(this);
    }

    public void setDataLength(int i2) {
        this.dataLength = i2;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(this.buffer, 0, this.dataLength);
    }

    @Override // java.lang.Comparable
    public int compareTo(ByteArray byteArray) {
        int i2 = this.bufferLength;
        int i3 = byteArray.bufferLength;
        if (i2 != i3) {
            return i2 - i3;
        }
        if (this.buffer == null) {
            return -1;
        }
        if (byteArray.buffer == null) {
            return 1;
        }
        return hashCode() - byteArray.hashCode();
    }

    public static ByteArray wrap(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return wrap(bArr, bArr.length);
    }
}
