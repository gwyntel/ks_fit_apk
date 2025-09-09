package org.apache.commons.io.output;

import java.io.Writer;

/* loaded from: classes5.dex */
public class NullWriter extends Writer {
    public static final NullWriter NULL_WRITER = new NullWriter();

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(char c2) {
        return this;
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
    }

    @Override // java.io.Writer
    public void write(int i2) {
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence) {
        return this;
    }

    @Override // java.io.Writer
    public void write(String str) {
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence, int i2, int i3) {
        return this;
    }

    @Override // java.io.Writer
    public void write(String str, int i2, int i3) {
    }

    @Override // java.io.Writer
    public void write(char[] cArr) {
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i2, int i3) {
    }
}
