package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class BoundedInputStream extends InputStream {
    private final InputStream in;
    private long mark;
    private final long max;
    private long pos;
    private boolean propagateClose;

    public BoundedInputStream(InputStream inputStream, long j2) {
        this.pos = 0L;
        this.mark = -1L;
        this.propagateClose = true;
        this.max = j2;
        this.in = inputStream;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        long j2 = this.max;
        if (j2 < 0 || this.pos < j2) {
            return this.in.available();
        }
        return 0;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.propagateClose) {
            this.in.close();
        }
    }

    public boolean isPropagateClose() {
        return this.propagateClose;
    }

    @Override // java.io.InputStream
    public synchronized void mark(int i2) {
        this.in.mark(i2);
        this.mark = this.pos;
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return this.in.markSupported();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        long j2 = this.max;
        if (j2 >= 0 && this.pos >= j2) {
            return -1;
        }
        int i2 = this.in.read();
        this.pos++;
        return i2;
    }

    @Override // java.io.InputStream
    public synchronized void reset() throws IOException {
        this.in.reset();
        this.pos = this.mark;
    }

    public void setPropagateClose(boolean z2) {
        this.propagateClose = z2;
    }

    @Override // java.io.InputStream
    public long skip(long j2) throws IOException {
        long j3 = this.max;
        if (j3 >= 0) {
            j2 = Math.min(j2, j3 - this.pos);
        }
        long jSkip = this.in.skip(j2);
        this.pos += jSkip;
        return jSkip;
    }

    public String toString() {
        return this.in.toString();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        long j2 = this.max;
        if (j2 >= 0 && this.pos >= j2) {
            return -1;
        }
        int i4 = this.in.read(bArr, i2, (int) (j2 >= 0 ? Math.min(i3, j2 - this.pos) : i3));
        if (i4 == -1) {
            return -1;
        }
        this.pos += i4;
        return i4;
    }

    public BoundedInputStream(InputStream inputStream) {
        this(inputStream, -1L);
    }
}
