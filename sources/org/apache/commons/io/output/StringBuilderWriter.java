package org.apache.commons.io.output;

import java.io.Serializable;
import java.io.Writer;

/* loaded from: classes5.dex */
public class StringBuilderWriter extends Writer implements Serializable {
    private static final long serialVersionUID = -146927496096066153L;
    private final StringBuilder builder;

    public StringBuilderWriter() {
        this.builder = new StringBuilder();
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
    }

    public StringBuilder getBuilder() {
        return this.builder;
    }

    public String toString() {
        return this.builder.toString();
    }

    @Override // java.io.Writer
    public void write(String str) {
        if (str != null) {
            this.builder.append(str);
        }
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i2, int i3) {
        if (cArr != null) {
            this.builder.append(cArr, i2, i3);
        }
    }

    public StringBuilderWriter(int i2) {
        this.builder = new StringBuilder(i2);
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(char c2) {
        this.builder.append(c2);
        return this;
    }

    public StringBuilderWriter(StringBuilder sb) {
        this.builder = sb == null ? new StringBuilder() : sb;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence) {
        this.builder.append(charSequence);
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence, int i2, int i3) {
        this.builder.append(charSequence, i2, i3);
        return this;
    }
}
