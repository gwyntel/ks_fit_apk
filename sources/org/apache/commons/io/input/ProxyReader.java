package org.apache.commons.io.input;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

/* loaded from: classes5.dex */
public abstract class ProxyReader extends FilterReader {
    public ProxyReader(Reader reader) {
        super(reader);
    }

    protected void a(int i2) {
    }

    protected void b(int i2) {
    }

    @Override // java.io.FilterReader, java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            ((FilterReader) this).in.close();
        } catch (IOException e2) {
            c(e2);
        }
    }

    @Override // java.io.FilterReader, java.io.Reader
    public synchronized void mark(int i2) throws IOException {
        try {
            ((FilterReader) this).in.mark(i2);
        } catch (IOException e2) {
            c(e2);
        }
    }

    @Override // java.io.FilterReader, java.io.Reader
    public boolean markSupported() {
        return ((FilterReader) this).in.markSupported();
    }

    @Override // java.io.FilterReader, java.io.Reader
    public int read() throws IOException {
        int i2 = 1;
        try {
            b(1);
            int i3 = ((FilterReader) this).in.read();
            if (i3 == -1) {
                i2 = -1;
            }
            a(i2);
            return i3;
        } catch (IOException e2) {
            c(e2);
            return -1;
        }
    }

    @Override // java.io.FilterReader, java.io.Reader
    public boolean ready() throws IOException {
        try {
            return ((FilterReader) this).in.ready();
        } catch (IOException e2) {
            c(e2);
            return false;
        }
    }

    @Override // java.io.FilterReader, java.io.Reader
    public synchronized void reset() throws IOException {
        try {
            ((FilterReader) this).in.reset();
        } catch (IOException e2) {
            c(e2);
        }
    }

    @Override // java.io.FilterReader, java.io.Reader
    public long skip(long j2) throws IOException {
        try {
            return ((FilterReader) this).in.skip(j2);
        } catch (IOException e2) {
            c(e2);
            return 0L;
        }
    }

    @Override // java.io.Reader
    public int read(char[] cArr) throws IOException {
        int length;
        if (cArr != null) {
            try {
                length = cArr.length;
            } catch (IOException e2) {
                c(e2);
                return -1;
            }
        } else {
            length = 0;
        }
        b(length);
        int i2 = ((FilterReader) this).in.read(cArr);
        a(i2);
        return i2;
    }

    @Override // java.io.FilterReader, java.io.Reader
    public int read(char[] cArr, int i2, int i3) throws IOException {
        try {
            b(i3);
            int i4 = ((FilterReader) this).in.read(cArr, i2, i3);
            a(i4);
            return i4;
        } catch (IOException e2) {
            c(e2);
            return -1;
        }
    }

    @Override // java.io.Reader, java.lang.Readable
    public int read(CharBuffer charBuffer) throws IOException {
        int length;
        if (charBuffer != null) {
            try {
                length = charBuffer.length();
            } catch (IOException e2) {
                c(e2);
                return -1;
            }
        } else {
            length = 0;
        }
        b(length);
        int i2 = ((FilterReader) this).in.read(charBuffer);
        a(i2);
        return i2;
    }

    protected void c(IOException iOException) throws IOException {
        throw iOException;
    }
}
