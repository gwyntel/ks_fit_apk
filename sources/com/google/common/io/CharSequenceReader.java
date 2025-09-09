package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.Objects;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
/* loaded from: classes3.dex */
final class CharSequenceReader extends Reader {
    private int mark;
    private int pos;

    @CheckForNull
    private CharSequence seq;

    public CharSequenceReader(CharSequence charSequence) {
        this.seq = (CharSequence) Preconditions.checkNotNull(charSequence);
    }

    private void checkOpen() throws IOException {
        if (this.seq == null) {
            throw new IOException("reader closed");
        }
    }

    private boolean hasRemaining() {
        return remaining() > 0;
    }

    private int remaining() {
        Objects.requireNonNull(this.seq);
        return this.seq.length() - this.pos;
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        this.seq = null;
    }

    @Override // java.io.Reader
    public synchronized void mark(int i2) throws IOException {
        Preconditions.checkArgument(i2 >= 0, "readAheadLimit (%s) may not be negative", i2);
        checkOpen();
        this.mark = this.pos;
    }

    @Override // java.io.Reader
    public boolean markSupported() {
        return true;
    }

    @Override // java.io.Reader, java.lang.Readable
    public synchronized int read(CharBuffer charBuffer) throws IOException {
        Preconditions.checkNotNull(charBuffer);
        checkOpen();
        Objects.requireNonNull(this.seq);
        if (!hasRemaining()) {
            return -1;
        }
        int iMin = Math.min(charBuffer.remaining(), remaining());
        for (int i2 = 0; i2 < iMin; i2++) {
            CharSequence charSequence = this.seq;
            int i3 = this.pos;
            this.pos = i3 + 1;
            charBuffer.put(charSequence.charAt(i3));
        }
        return iMin;
    }

    @Override // java.io.Reader
    public synchronized boolean ready() throws IOException {
        checkOpen();
        return true;
    }

    @Override // java.io.Reader
    public synchronized void reset() throws IOException {
        checkOpen();
        this.pos = this.mark;
    }

    @Override // java.io.Reader
    public synchronized long skip(long j2) throws IOException {
        int iMin;
        Preconditions.checkArgument(j2 >= 0, "n (%s) may not be negative", j2);
        checkOpen();
        iMin = (int) Math.min(remaining(), j2);
        this.pos += iMin;
        return iMin;
    }

    @Override // java.io.Reader
    public synchronized int read() throws IOException {
        char cCharAt;
        checkOpen();
        Objects.requireNonNull(this.seq);
        if (hasRemaining()) {
            CharSequence charSequence = this.seq;
            int i2 = this.pos;
            this.pos = i2 + 1;
            cCharAt = charSequence.charAt(i2);
        } else {
            cCharAt = 65535;
        }
        return cCharAt;
    }

    @Override // java.io.Reader
    public synchronized int read(char[] cArr, int i2, int i3) throws IOException {
        Preconditions.checkPositionIndexes(i2, i2 + i3, cArr.length);
        checkOpen();
        Objects.requireNonNull(this.seq);
        if (!hasRemaining()) {
            return -1;
        }
        int iMin = Math.min(i3, remaining());
        for (int i4 = 0; i4 < iMin; i4++) {
            CharSequence charSequence = this.seq;
            int i5 = this.pos;
            this.pos = i5 + 1;
            cArr[i2 + i4] = charSequence.charAt(i5);
        }
        return iMin;
    }
}
