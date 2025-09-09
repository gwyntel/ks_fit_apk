package org.apache.commons.io.output;

import java.io.OutputStream;

/* loaded from: classes5.dex */
public class CountingOutputStream extends ProxyOutputStream {
    private long count;

    public CountingOutputStream(OutputStream outputStream) {
        super(outputStream);
        this.count = 0L;
    }

    @Override // org.apache.commons.io.output.ProxyOutputStream
    protected synchronized void b(int i2) {
        this.count += i2;
    }

    public synchronized long getByteCount() {
        return this.count;
    }

    public int getCount() {
        long byteCount = getByteCount();
        if (byteCount <= 2147483647L) {
            return (int) byteCount;
        }
        throw new ArithmeticException("The byte count " + byteCount + " is too large to be converted to an int");
    }

    public synchronized long resetByteCount() {
        long j2;
        j2 = this.count;
        this.count = 0L;
        return j2;
    }

    public int resetCount() {
        long jResetByteCount = resetByteCount();
        if (jResetByteCount <= 2147483647L) {
            return (int) jResetByteCount;
        }
        throw new ArithmeticException("The byte count " + jResetByteCount + " is too large to be converted to an int");
    }
}
