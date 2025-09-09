package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public abstract class ThresholdingOutputStream extends OutputStream {
    private final int threshold;
    private boolean thresholdExceeded;
    private long written;

    public ThresholdingOutputStream(int i2) {
        this.threshold = i2;
    }

    protected void a(int i2) {
        if (this.thresholdExceeded || this.written + i2 <= this.threshold) {
            return;
        }
        this.thresholdExceeded = true;
        f();
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            flush();
        } catch (IOException unused) {
        }
        e().close();
    }

    protected abstract OutputStream e();

    protected abstract void f();

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        e().flush();
    }

    public long getByteCount() {
        return this.written;
    }

    public int getThreshold() {
        return this.threshold;
    }

    public boolean isThresholdExceeded() {
        return this.written > ((long) this.threshold);
    }

    @Override // java.io.OutputStream
    public void write(int i2) throws IOException {
        a(1);
        e().write(i2);
        this.written++;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        a(bArr.length);
        e().write(bArr);
        this.written += bArr.length;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        a(i3);
        e().write(bArr, i2, i3);
        this.written += i3;
    }
}
