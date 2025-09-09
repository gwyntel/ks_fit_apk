package org.apache.commons.io.output;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public class ProxyOutputStream extends FilterOutputStream {
    public ProxyOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    protected void a(int i2) {
    }

    protected void b(int i2) {
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            ((FilterOutputStream) this).out.close();
        } catch (IOException e2) {
            c(e2);
        }
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        try {
            ((FilterOutputStream) this).out.flush();
        } catch (IOException e2) {
            c(e2);
        }
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i2) throws IOException {
        try {
            b(1);
            ((FilterOutputStream) this).out.write(i2);
            a(1);
        } catch (IOException e2) {
            c(e2);
        }
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        int length;
        if (bArr != null) {
            try {
                length = bArr.length;
            } catch (IOException e2) {
                c(e2);
                return;
            }
        } else {
            length = 0;
        }
        b(length);
        ((FilterOutputStream) this).out.write(bArr);
        a(length);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        try {
            b(i3);
            ((FilterOutputStream) this).out.write(bArr, i2, i3);
            a(i3);
        } catch (IOException e2) {
            c(e2);
        }
    }

    protected void c(IOException iOException) throws IOException {
        throw iOException;
    }
}
