package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
/* loaded from: classes3.dex */
final class MultiInputStream extends InputStream {

    @CheckForNull
    private InputStream in;
    private Iterator<? extends ByteSource> it;

    public MultiInputStream(Iterator<? extends ByteSource> it) throws IOException {
        this.it = (Iterator) Preconditions.checkNotNull(it);
        advance();
    }

    private void advance() throws IOException {
        close();
        if (this.it.hasNext()) {
            this.in = this.it.next().openStream();
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        InputStream inputStream = this.in;
        if (inputStream == null) {
            return 0;
        }
        return inputStream.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        InputStream inputStream = this.in;
        if (inputStream != null) {
            try {
                inputStream.close();
            } finally {
                this.in = null;
            }
        }
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        while (true) {
            InputStream inputStream = this.in;
            if (inputStream == null) {
                return -1;
            }
            int i2 = inputStream.read();
            if (i2 != -1) {
                return i2;
            }
            advance();
        }
    }

    @Override // java.io.InputStream
    public long skip(long j2) throws IOException {
        InputStream inputStream = this.in;
        if (inputStream == null || j2 <= 0) {
            return 0L;
        }
        long jSkip = inputStream.skip(j2);
        if (jSkip != 0) {
            return jSkip;
        }
        if (read() == -1) {
            return 0L;
        }
        return this.in.skip(j2 - 1) + 1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        Preconditions.checkNotNull(bArr);
        while (true) {
            InputStream inputStream = this.in;
            if (inputStream == null) {
                return -1;
            }
            int i4 = inputStream.read(bArr, i2, i3);
            if (i4 != -1) {
                return i4;
            }
            advance();
        }
    }
}
