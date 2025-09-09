package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class CountingInputStream extends ProxyInputStream {
    private long count;

    public CountingInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override // org.apache.commons.io.input.ProxyInputStream
    protected synchronized void a(int i2) {
        if (i2 != -1) {
            this.count += i2;
        }
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

    @Override // org.apache.commons.io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized long skip(long j2) throws IOException {
        long jSkip;
        jSkip = super.skip(j2);
        this.count += jSkip;
        return jSkip;
    }
}
