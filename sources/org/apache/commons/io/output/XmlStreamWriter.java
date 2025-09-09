package org.apache.commons.io.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.input.XmlStreamReader;

/* loaded from: classes5.dex */
public class XmlStreamWriter extends Writer {
    private static final int BUFFER_SIZE = 4096;

    /* renamed from: a, reason: collision with root package name */
    static final Pattern f26602a = XmlStreamReader.ENCODING_PATTERN;
    private final String defaultEncoding;
    private String encoding;
    private final OutputStream out;
    private Writer writer;
    private StringWriter xmlPrologWriter;

    public XmlStreamWriter(OutputStream outputStream) {
        this(outputStream, (String) null);
    }

    private void detectEncoding(char[] cArr, int i2, int i3) throws IOException {
        StringBuffer buffer = this.xmlPrologWriter.getBuffer();
        int length = buffer.length() + i3 > 4096 ? 4096 - buffer.length() : i3;
        this.xmlPrologWriter.write(cArr, i2, length);
        if (buffer.length() >= 5) {
            if (buffer.substring(0, 5).equals("<?xml")) {
                int iIndexOf = buffer.indexOf("?>");
                if (iIndexOf > 0) {
                    Matcher matcher = f26602a.matcher(buffer.substring(0, iIndexOf));
                    if (matcher.find()) {
                        String upperCase = matcher.group(1).toUpperCase();
                        this.encoding = upperCase;
                        this.encoding = upperCase.substring(1, upperCase.length() - 1);
                    } else {
                        this.encoding = this.defaultEncoding;
                    }
                } else if (buffer.length() >= 4096) {
                    this.encoding = this.defaultEncoding;
                }
            } else {
                this.encoding = this.defaultEncoding;
            }
            if (this.encoding != null) {
                this.xmlPrologWriter = null;
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.out, this.encoding);
                this.writer = outputStreamWriter;
                outputStreamWriter.write(buffer.toString());
                if (i3 > length) {
                    this.writer.write(cArr, i2 + length, i3 - length);
                }
            }
        }
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.writer == null) {
            this.encoding = this.defaultEncoding;
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.out, this.encoding);
            this.writer = outputStreamWriter;
            outputStreamWriter.write(this.xmlPrologWriter.toString());
        }
        this.writer.close();
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        Writer writer = this.writer;
        if (writer != null) {
            writer.flush();
        }
    }

    public String getDefaultEncoding() {
        return this.defaultEncoding;
    }

    public String getEncoding() {
        return this.encoding;
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i2, int i3) throws IOException {
        if (this.xmlPrologWriter != null) {
            detectEncoding(cArr, i2, i3);
        } else {
            this.writer.write(cArr, i2, i3);
        }
    }

    public XmlStreamWriter(OutputStream outputStream, String str) {
        this.xmlPrologWriter = new StringWriter(4096);
        this.out = outputStream;
        this.defaultEncoding = str == null ? "UTF-8" : str;
    }

    public XmlStreamWriter(File file) throws FileNotFoundException {
        this(file, (String) null);
    }

    public XmlStreamWriter(File file, String str) throws FileNotFoundException {
        this(new FileOutputStream(file), str);
    }
}
