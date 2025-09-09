package org.apache.commons.io.input;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class NullInputStream extends InputStream {
    private boolean eof;
    private long mark;
    private final boolean markSupported;
    private long position;
    private long readlimit;
    private final long size;
    private final boolean throwEofException;

    public NullInputStream(long j2) {
        this(j2, true, false);
    }

    private int doEndOfFile() throws EOFException {
        this.eof = true;
        if (this.throwEofException) {
            throw new EOFException();
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int available() {
        long j2 = this.size - this.position;
        if (j2 <= 0) {
            return 0;
        }
        if (j2 > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j2;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.eof = false;
        this.position = 0L;
        this.mark = -1L;
    }

    protected int e() {
        return 0;
    }

    protected void f(byte[] bArr, int i2, int i3) {
    }

    public long getPosition() {
        return this.position;
    }

    public long getSize() {
        return this.size;
    }

    @Override // java.io.InputStream
    public synchronized void mark(int i2) {
        if (!this.markSupported) {
            throw new UnsupportedOperationException("Mark not supported");
        }
        this.mark = this.position;
        this.readlimit = i2;
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return this.markSupported;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.eof) {
            throw new IOException("Read after end of file");
        }
        long j2 = this.position;
        if (j2 == this.size) {
            return doEndOfFile();
        }
        this.position = j2 + 1;
        return e();
    }

    @Override // java.io.InputStream
    public synchronized void reset() throws IOException {
        if (!this.markSupported) {
            throw new UnsupportedOperationException("Mark not supported");
        }
        long j2 = this.mark;
        if (j2 < 0) {
            throw new IOException("No position has been marked");
        }
        if (this.position > this.readlimit + j2) {
            throw new IOException("Marked position [" + this.mark + "] is no longer valid - passed the read limit [" + this.readlimit + "]");
        }
        this.position = j2;
        this.eof = false;
    }

    @Override // java.io.InputStream
    public long skip(long j2) throws IOException {
        if (this.eof) {
            throw new IOException("Skip after end of file");
        }
        long j3 = this.position;
        long j4 = this.size;
        if (j3 == j4) {
            return doEndOfFile();
        }
        long j5 = j3 + j2;
        this.position = j5;
        if (j5 <= j4) {
            return j2;
        }
        long j6 = j2 - (j5 - j4);
        this.position = j4;
        return j6;
    }

    public NullInputStream(long j2, boolean z2, boolean z3) {
        this.mark = -1L;
        this.size = j2;
        this.markSupported = z2;
        this.throwEofException = z3;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (!this.eof) {
            long j2 = this.position;
            long j3 = this.size;
            if (j2 == j3) {
                return doEndOfFile();
            }
            long j4 = j2 + i3;
            this.position = j4;
            if (j4 > j3) {
                i3 -= (int) (j4 - j3);
                this.position = j3;
            }
            f(bArr, i2, i3);
            return i3;
        }
        throw new IOException("Read after end of file");
    }
}
