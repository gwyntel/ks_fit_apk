package org.apache.commons.io.input;

import java.io.Reader;
import java.io.Serializable;

/* loaded from: classes5.dex */
public class CharSequenceReader extends Reader implements Serializable {
    private static final long serialVersionUID = 3724187752191401220L;
    private final CharSequence charSequence;
    private int idx;
    private int mark;

    public CharSequenceReader(String str) {
        this.charSequence = str == null ? "" : str;
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.idx = 0;
        this.mark = 0;
    }

    @Override // java.io.Reader
    public void mark(int i2) {
        this.mark = this.idx;
    }

    @Override // java.io.Reader
    public boolean markSupported() {
        return true;
    }

    @Override // java.io.Reader
    public int read() {
        if (this.idx >= this.charSequence.length()) {
            return -1;
        }
        CharSequence charSequence = this.charSequence;
        int i2 = this.idx;
        this.idx = i2 + 1;
        return charSequence.charAt(i2);
    }

    @Override // java.io.Reader
    public void reset() {
        this.idx = this.mark;
    }

    @Override // java.io.Reader
    public long skip(long j2) {
        if (j2 < 0) {
            throw new IllegalArgumentException("Number of characters to skip is less than zero: " + j2);
        }
        if (this.idx >= this.charSequence.length()) {
            return -1L;
        }
        int iMin = (int) Math.min(this.charSequence.length(), this.idx + j2);
        int i2 = iMin - this.idx;
        this.idx = iMin;
        return i2;
    }

    public String toString() {
        return this.charSequence.toString();
    }

    @Override // java.io.Reader
    public int read(char[] cArr, int i2, int i3) {
        if (this.idx >= this.charSequence.length()) {
            return -1;
        }
        if (cArr != null) {
            if (i3 < 0 || i2 < 0 || i2 + i3 > cArr.length) {
                throw new IndexOutOfBoundsException("Array Size=" + cArr.length + ", offset=" + i2 + ", length=" + i3);
            }
            int i4 = 0;
            for (int i5 = 0; i5 < i3; i5++) {
                int i6 = read();
                if (i6 == -1) {
                    return i4;
                }
                cArr[i2 + i5] = (char) i6;
                i4++;
            }
            return i4;
        }
        throw new NullPointerException("Character array is missing");
    }
}
