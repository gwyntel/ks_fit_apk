package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
/* loaded from: classes3.dex */
public final class CharStreams {
    private static final int DEFAULT_BUF_SIZE = 2048;

    private static final class NullWriter extends Writer {
        private static final NullWriter INSTANCE = new NullWriter();

        private NullWriter() {
        }

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

        public String toString() {
            return "CharStreams.nullWriter()";
        }

        @Override // java.io.Writer
        public void write(int i2) {
        }

        @Override // java.io.Writer, java.lang.Appendable
        public Writer append(@CheckForNull CharSequence charSequence) {
            return this;
        }

        @Override // java.io.Writer
        public void write(char[] cArr) {
            Preconditions.checkNotNull(cArr);
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i2, int i3) {
            Preconditions.checkPositionIndexes(i2, i3 + i2, cArr.length);
        }

        @Override // java.io.Writer
        public void write(String str) {
            Preconditions.checkNotNull(str);
        }

        @Override // java.io.Writer
        public void write(String str, int i2, int i3) {
            Preconditions.checkPositionIndexes(i2, i3 + i2, str.length());
        }

        @Override // java.io.Writer, java.lang.Appendable
        public Writer append(@CheckForNull CharSequence charSequence, int i2, int i3) {
            Preconditions.checkPositionIndexes(i2, i3, charSequence == null ? 4 : charSequence.length());
            return this;
        }
    }

    private CharStreams() {
    }

    static long a(Reader reader, StringBuilder sb) throws IOException {
        Preconditions.checkNotNull(reader);
        Preconditions.checkNotNull(sb);
        char[] cArr = new char[2048];
        long j2 = 0;
        while (true) {
            int i2 = reader.read(cArr);
            if (i2 == -1) {
                return j2;
            }
            sb.append(cArr, 0, i2);
            j2 += i2;
        }
    }

    public static Writer asWriter(Appendable appendable) {
        return appendable instanceof Writer ? (Writer) appendable : new AppendableWriter(appendable);
    }

    static long b(Reader reader, Writer writer) throws IOException {
        Preconditions.checkNotNull(reader);
        Preconditions.checkNotNull(writer);
        char[] cArr = new char[2048];
        long j2 = 0;
        while (true) {
            int i2 = reader.read(cArr);
            if (i2 == -1) {
                return j2;
            }
            writer.write(cArr, 0, i2);
            j2 += i2;
        }
    }

    static CharBuffer c() {
        return CharBuffer.allocate(2048);
    }

    @CanIgnoreReturnValue
    public static long copy(Readable readable, Appendable appendable) throws IOException {
        if (readable instanceof Reader) {
            return appendable instanceof StringBuilder ? a((Reader) readable, (StringBuilder) appendable) : b((Reader) readable, asWriter(appendable));
        }
        Preconditions.checkNotNull(readable);
        Preconditions.checkNotNull(appendable);
        CharBuffer charBufferC = c();
        long jRemaining = 0;
        while (readable.read(charBufferC) != -1) {
            Java8Compatibility.b(charBufferC);
            appendable.append(charBufferC);
            jRemaining += charBufferC.remaining();
            Java8Compatibility.a(charBufferC);
        }
        return jRemaining;
    }

    @CanIgnoreReturnValue
    public static long exhaust(Readable readable) throws IOException {
        CharBuffer charBufferC = c();
        long j2 = 0;
        while (true) {
            long j3 = readable.read(charBufferC);
            if (j3 == -1) {
                return j2;
            }
            j2 += j3;
            Java8Compatibility.a(charBufferC);
        }
    }

    public static Writer nullWriter() {
        return NullWriter.INSTANCE;
    }

    public static List<String> readLines(Readable readable) throws IOException {
        ArrayList arrayList = new ArrayList();
        LineReader lineReader = new LineReader(readable);
        while (true) {
            String line = lineReader.readLine();
            if (line == null) {
                return arrayList;
            }
            arrayList.add(line);
        }
    }

    public static void skipFully(Reader reader, long j2) throws IOException {
        Preconditions.checkNotNull(reader);
        while (j2 > 0) {
            long jSkip = reader.skip(j2);
            if (jSkip == 0) {
                throw new EOFException();
            }
            j2 -= jSkip;
        }
    }

    public static String toString(Readable readable) throws IOException {
        return toStringBuilder(readable).toString();
    }

    private static StringBuilder toStringBuilder(Readable readable) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (readable instanceof Reader) {
            a((Reader) readable, sb);
        } else {
            copy(readable, sb);
        }
        return sb;
    }

    @CanIgnoreReturnValue
    @ParametricNullness
    public static <T> T readLines(Readable readable, LineProcessor<T> lineProcessor) throws IOException {
        String line;
        Preconditions.checkNotNull(readable);
        Preconditions.checkNotNull(lineProcessor);
        LineReader lineReader = new LineReader(readable);
        do {
            line = lineReader.readLine();
            if (line == null) {
                break;
            }
        } while (lineProcessor.processLine(line));
        return lineProcessor.getResult();
    }
}
