package org.apache.commons.io.output;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.input.ClosedInputStream;

/* loaded from: classes5.dex */
public class ByteArrayOutputStream extends OutputStream {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private final List<byte[]> buffers;
    private int count;
    private byte[] currentBuffer;
    private int currentBufferIndex;
    private int filledBufferSum;
    private boolean reuseBuffers;

    public ByteArrayOutputStream() {
        this(1024);
    }

    private void needNewBuffer(int i2) {
        if (this.currentBufferIndex < this.buffers.size() - 1) {
            this.filledBufferSum += this.currentBuffer.length;
            int i3 = this.currentBufferIndex + 1;
            this.currentBufferIndex = i3;
            this.currentBuffer = this.buffers.get(i3);
            return;
        }
        byte[] bArr = this.currentBuffer;
        if (bArr == null) {
            this.filledBufferSum = 0;
        } else {
            i2 = Math.max(bArr.length << 1, i2 - this.filledBufferSum);
            this.filledBufferSum += this.currentBuffer.length;
        }
        this.currentBufferIndex++;
        byte[] bArr2 = new byte[i2];
        this.currentBuffer = bArr2;
        this.buffers.add(bArr2);
    }

    public static InputStream toBufferedInputStream(InputStream inputStream) throws IOException {
        return toBufferedInputStream(inputStream, 1024);
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    public synchronized void reset() {
        try {
            this.count = 0;
            this.filledBufferSum = 0;
            this.currentBufferIndex = 0;
            if (this.reuseBuffers) {
                this.currentBuffer = this.buffers.get(0);
            } else {
                this.currentBuffer = null;
                int length = this.buffers.get(0).length;
                this.buffers.clear();
                needNewBuffer(length);
                this.reuseBuffers = true;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized int size() {
        return this.count;
    }

    public synchronized byte[] toByteArray() {
        int i2 = this.count;
        if (i2 == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] bArr = new byte[i2];
        int i3 = 0;
        for (byte[] bArr2 : this.buffers) {
            int iMin = Math.min(bArr2.length, i2);
            System.arraycopy(bArr2, 0, bArr, i3, iMin);
            i3 += iMin;
            i2 -= iMin;
            if (i2 == 0) {
                break;
            }
        }
        return bArr;
    }

    public synchronized InputStream toInputStream() {
        int i2 = this.count;
        if (i2 == 0) {
            return new ClosedInputStream();
        }
        ArrayList arrayList = new ArrayList(this.buffers.size());
        for (byte[] bArr : this.buffers) {
            int iMin = Math.min(bArr.length, i2);
            arrayList.add(new ByteArrayInputStream(bArr, 0, iMin));
            i2 -= iMin;
            if (i2 == 0) {
                break;
            }
        }
        this.reuseBuffers = false;
        return new SequenceInputStream(Collections.enumeration(arrayList));
    }

    @Deprecated
    public String toString() {
        return new String(toByteArray(), Charset.defaultCharset());
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        int i4;
        if (i2 < 0 || i2 > bArr.length || i3 < 0 || (i4 = i2 + i3) > bArr.length || i4 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i3 == 0) {
            return;
        }
        synchronized (this) {
            try {
                int i5 = this.count;
                int i6 = i5 + i3;
                int i7 = i5 - this.filledBufferSum;
                while (i3 > 0) {
                    int iMin = Math.min(i3, this.currentBuffer.length - i7);
                    System.arraycopy(bArr, i4 - i3, this.currentBuffer, i7, iMin);
                    i3 -= iMin;
                    if (i3 > 0) {
                        needNewBuffer(i6);
                        i7 = 0;
                    }
                }
                this.count = i6;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public synchronized void writeTo(OutputStream outputStream) throws IOException {
        int i2 = this.count;
        for (byte[] bArr : this.buffers) {
            int iMin = Math.min(bArr.length, i2);
            outputStream.write(bArr, 0, iMin);
            i2 -= iMin;
            if (i2 == 0) {
                break;
            }
        }
    }

    public ByteArrayOutputStream(int i2) {
        this.buffers = new ArrayList();
        this.reuseBuffers = true;
        if (i2 >= 0) {
            synchronized (this) {
                needNewBuffer(i2);
            }
        } else {
            throw new IllegalArgumentException("Negative initial size: " + i2);
        }
    }

    public static InputStream toBufferedInputStream(InputStream inputStream, int i2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i2);
        byteArrayOutputStream.write(inputStream);
        return byteArrayOutputStream.toInputStream();
    }

    public String toString(String str) throws UnsupportedEncodingException {
        return new String(toByteArray(), str);
    }

    public String toString(Charset charset) {
        return new String(toByteArray(), charset);
    }

    @Override // java.io.OutputStream
    public synchronized void write(int i2) {
        try {
            int i3 = this.count;
            int i4 = i3 - this.filledBufferSum;
            if (i4 == this.currentBuffer.length) {
                needNewBuffer(i3 + 1);
                i4 = 0;
            }
            this.currentBuffer[i4] = (byte) i2;
            this.count++;
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized int write(InputStream inputStream) throws IOException {
        int i2;
        try {
            int i3 = this.count - this.filledBufferSum;
            byte[] bArr = this.currentBuffer;
            int i4 = inputStream.read(bArr, i3, bArr.length - i3);
            i2 = 0;
            while (i4 != -1) {
                i2 += i4;
                i3 += i4;
                this.count += i4;
                byte[] bArr2 = this.currentBuffer;
                if (i3 == bArr2.length) {
                    needNewBuffer(bArr2.length);
                    i3 = 0;
                }
                byte[] bArr3 = this.currentBuffer;
                i4 = inputStream.read(bArr3, i3, bArr3.length - i3);
            }
        } catch (Throwable th) {
            throw th;
        }
        return i2;
    }
}
