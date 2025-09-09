package org.apache.commons.io.output;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/* loaded from: classes5.dex */
public class ProxyWriter extends FilterWriter {
    public ProxyWriter(Writer writer) {
        super(writer);
    }

    protected void a(int i2) {
    }

    protected void b(int i2) {
    }

    @Override // java.io.FilterWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            ((FilterWriter) this).out.close();
        } catch (IOException e2) {
            c(e2);
        }
    }

    @Override // java.io.FilterWriter, java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        try {
            ((FilterWriter) this).out.flush();
        } catch (IOException e2) {
            c(e2);
        }
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public void write(int i2) throws IOException {
        try {
            b(1);
            ((FilterWriter) this).out.write(i2);
            a(1);
        } catch (IOException e2) {
            c(e2);
        }
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(char c2) throws IOException {
        try {
            b(1);
            ((FilterWriter) this).out.append(c2);
            a(1);
        } catch (IOException e2) {
            c(e2);
        }
        return this;
    }

    @Override // java.io.Writer
    public void write(char[] cArr) throws IOException {
        int length;
        if (cArr != null) {
            try {
                length = cArr.length;
            } catch (IOException e2) {
                c(e2);
                return;
            }
        } else {
            length = 0;
        }
        b(length);
        ((FilterWriter) this).out.write(cArr);
        a(length);
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence, int i2, int i3) throws IOException {
        int i4 = i3 - i2;
        try {
            b(i4);
            ((FilterWriter) this).out.append(charSequence, i2, i3);
            a(i4);
        } catch (IOException e2) {
            c(e2);
        }
        return this;
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public void write(char[] cArr, int i2, int i3) throws IOException {
        try {
            b(i3);
            ((FilterWriter) this).out.write(cArr, i2, i3);
            a(i3);
        } catch (IOException e2) {
            c(e2);
        }
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence) throws IOException {
        int length;
        if (charSequence != null) {
            try {
                length = charSequence.length();
            } catch (IOException e2) {
                c(e2);
            }
        } else {
            length = 0;
        }
        b(length);
        ((FilterWriter) this).out.append(charSequence);
        a(length);
        return this;
    }

    @Override // java.io.Writer
    public void write(String str) throws IOException {
        int length;
        if (str != null) {
            try {
                length = str.length();
            } catch (IOException e2) {
                c(e2);
                return;
            }
        } else {
            length = 0;
        }
        b(length);
        ((FilterWriter) this).out.write(str);
        a(length);
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public void write(String str, int i2, int i3) throws IOException {
        try {
            b(i3);
            ((FilterWriter) this).out.write(str, i2, i3);
            a(i3);
        } catch (IOException e2) {
            c(e2);
        }
    }

    protected void c(IOException iOException) throws IOException {
        throw iOException;
    }
}
