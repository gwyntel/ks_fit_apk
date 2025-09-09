package top.zibin.luban.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class BufferedInputStreamWrap extends FilterInputStream {
    public static final int DEFAULT_MARK_READ_LIMIT = 8388608;
    private volatile byte[] buf;
    private int count;
    private int markLimit;
    private int markPos;
    private int pos;

    static class InvalidMarkException extends IOException {
        private static final long serialVersionUID = -4338378848813561759L;

        InvalidMarkException(String str) {
            super(str);
        }
    }

    public BufferedInputStreamWrap(InputStream inputStream) {
        this(inputStream, 65536);
    }

    private int fillbuf(InputStream inputStream, byte[] bArr) throws IOException {
        int i2 = this.markPos;
        if (i2 != -1) {
            int i3 = this.pos - i2;
            int i4 = this.markLimit;
            if (i3 < i4) {
                if (i2 == 0 && i4 > bArr.length && this.count == bArr.length) {
                    int length = bArr.length * 2;
                    if (length <= i4) {
                        i4 = length;
                    }
                    byte[] bArr2 = ArrayPoolProvide.getInstance().get(i4);
                    System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                    this.buf = bArr2;
                    ArrayPoolProvide.getInstance().put(bArr);
                    bArr = bArr2;
                } else if (i2 > 0) {
                    System.arraycopy(bArr, i2, bArr, 0, bArr.length - i2);
                }
                int i5 = this.pos - this.markPos;
                this.pos = i5;
                this.markPos = 0;
                this.count = 0;
                int i6 = inputStream.read(bArr, i5, bArr.length - i5);
                int i7 = this.pos;
                if (i6 > 0) {
                    i7 += i6;
                }
                this.count = i7;
                return i6;
            }
        }
        int i8 = inputStream.read(bArr);
        if (i8 > 0) {
            this.markPos = -1;
            this.pos = 0;
            this.count = i8;
        }
        return i8;
    }

    private static IOException streamClosed() throws IOException {
        throw new IOException("BufferedInputStream is closed");
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized int available() throws IOException {
        InputStream inputStream = ((FilterInputStream) this).in;
        if (this.buf != null && inputStream != null) {
            return (this.count - this.pos) + inputStream.available();
        }
        return 0;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.buf != null) {
            ArrayPoolProvide.getInstance().put(this.buf);
            this.buf = null;
        }
        InputStream inputStream = ((FilterInputStream) this).in;
        ((FilterInputStream) this).in = null;
        if (inputStream != null) {
            inputStream.close();
        }
    }

    public synchronized void fixMarkLimit() {
        this.markLimit = this.buf.length;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void mark(int i2) {
        this.markLimit = Math.max(this.markLimit, i2);
        this.markPos = this.pos;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized int read() throws IOException {
        byte[] bArr = this.buf;
        InputStream inputStream = ((FilterInputStream) this).in;
        if (bArr == null || inputStream == null) {
            throw streamClosed();
        }
        if (this.pos >= this.count && fillbuf(inputStream, bArr) == -1) {
            return -1;
        }
        if (bArr != this.buf && (bArr = this.buf) == null) {
            throw streamClosed();
        }
        int i2 = this.count;
        int i3 = this.pos;
        if (i2 - i3 <= 0) {
            return -1;
        }
        this.pos = i3 + 1;
        return bArr[i3] & 255;
    }

    public synchronized void release() {
        if (this.buf != null) {
            ArrayPoolProvide.getInstance().put(this.buf);
            this.buf = null;
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        if (this.buf == null) {
            throw new IOException("Stream is closed");
        }
        int i2 = this.markPos;
        if (-1 == i2) {
            throw new InvalidMarkException("Mark has been invalidated, pos: " + this.pos + " markLimit: " + this.markLimit);
        }
        this.pos = i2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized long skip(long j2) throws IOException {
        if (j2 < 1) {
            return 0L;
        }
        byte[] bArr = this.buf;
        if (bArr == null) {
            throw streamClosed();
        }
        InputStream inputStream = ((FilterInputStream) this).in;
        if (inputStream == null) {
            throw streamClosed();
        }
        int i2 = this.count;
        int i3 = this.pos;
        if (i2 - i3 >= j2) {
            this.pos = (int) (i3 + j2);
            return j2;
        }
        long j3 = i2 - i3;
        this.pos = i2;
        if (this.markPos == -1 || j2 > this.markLimit) {
            return j3 + inputStream.skip(j2 - j3);
        }
        if (fillbuf(inputStream, bArr) == -1) {
            return j3;
        }
        int i4 = this.count;
        int i5 = this.pos;
        if (i4 - i5 >= j2 - j3) {
            this.pos = (int) ((i5 + j2) - j3);
            return j2;
        }
        long j4 = (j3 + i4) - i5;
        this.pos = i4;
        return j4;
    }

    BufferedInputStreamWrap(InputStream inputStream, int i2) {
        super(inputStream);
        this.markPos = -1;
        this.buf = ArrayPoolProvide.getInstance().get(i2);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4;
        int iMin;
        byte[] bArr2 = this.buf;
        if (bArr2 == null) {
            throw streamClosed();
        }
        if (i3 == 0) {
            return 0;
        }
        InputStream inputStream = ((FilterInputStream) this).in;
        if (inputStream != null) {
            int i5 = this.pos;
            int i6 = this.count;
            if (i5 < i6) {
                int iMin2 = Math.min(i6 - i5, i3);
                System.arraycopy(bArr2, this.pos, bArr, i2, iMin2);
                this.pos += iMin2;
                if (iMin2 == i3 || inputStream.available() == 0) {
                    return iMin2;
                }
                i2 += iMin2;
                i4 = i3 - iMin2;
            } else {
                i4 = i3;
            }
            while (true) {
                if (this.markPos == -1 && i4 >= bArr2.length) {
                    iMin = inputStream.read(bArr, i2, i4);
                    if (iMin == -1) {
                        return i4 != i3 ? i3 - i4 : -1;
                    }
                } else {
                    if (fillbuf(inputStream, bArr2) == -1) {
                        return i4 != i3 ? i3 - i4 : -1;
                    }
                    if (bArr2 != this.buf && (bArr2 = this.buf) == null) {
                        throw streamClosed();
                    }
                    iMin = Math.min(this.count - this.pos, i4);
                    System.arraycopy(bArr2, this.pos, bArr, i2, iMin);
                    this.pos += iMin;
                }
                i4 -= iMin;
                if (i4 == 0) {
                    return i3;
                }
                if (inputStream.available() == 0) {
                    return i3 - i4;
                }
                i2 += iMin;
            }
        } else {
            throw streamClosed();
        }
    }
}
