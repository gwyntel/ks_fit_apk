package org.apache.commons.io.input;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public abstract class ProxyInputStream extends FilterInputStream {
    public ProxyInputStream(InputStream inputStream) {
        super(inputStream);
    }

    protected void a(int i2) {
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        try {
            return super.available();
        } catch (IOException e2) {
            c(e2);
            return 0;
        }
    }

    protected void b(int i2) {
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            ((FilterInputStream) this).in.close();
        } catch (IOException e2) {
            c(e2);
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void mark(int i2) {
        ((FilterInputStream) this).in.mark(i2);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return ((FilterInputStream) this).in.markSupported();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i2 = 1;
        try {
            b(1);
            int i3 = ((FilterInputStream) this).in.read();
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

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        try {
            ((FilterInputStream) this).in.reset();
        } catch (IOException e2) {
            c(e2);
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j2) throws IOException {
        try {
            return ((FilterInputStream) this).in.skip(j2);
        } catch (IOException e2) {
            c(e2);
            return 0L;
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        int length;
        if (bArr != null) {
            try {
                length = bArr.length;
            } catch (IOException e2) {
                c(e2);
                return -1;
            }
        } else {
            length = 0;
        }
        b(length);
        int i2 = ((FilterInputStream) this).in.read(bArr);
        a(i2);
        return i2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        try {
            b(i3);
            int i4 = ((FilterInputStream) this).in.read(bArr, i2, i3);
            a(i4);
            return i4;
        } catch (IOException e2) {
            c(e2);
            return -1;
        }
    }

    protected void c(IOException iOException) throws IOException {
        throw iOException;
    }
}
