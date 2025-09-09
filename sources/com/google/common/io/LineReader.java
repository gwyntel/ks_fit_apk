package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
/* loaded from: classes3.dex */
public final class LineReader {
    private final char[] buf;
    private final CharBuffer cbuf;
    private final LineBuffer lineBuf;
    private final Queue<String> lines;
    private final Readable readable;

    @CheckForNull
    private final Reader reader;

    public LineReader(Readable readable) {
        CharBuffer charBufferC = CharStreams.c();
        this.cbuf = charBufferC;
        this.buf = charBufferC.array();
        this.lines = new ArrayDeque();
        this.lineBuf = new LineBuffer() { // from class: com.google.common.io.LineReader.1
            @Override // com.google.common.io.LineBuffer
            protected void c(String str, String str2) {
                LineReader.this.lines.add(str);
            }
        };
        this.readable = (Readable) Preconditions.checkNotNull(readable);
        this.reader = readable instanceof Reader ? (Reader) readable : null;
    }

    @CanIgnoreReturnValue
    @CheckForNull
    public String readLine() throws IOException {
        int i2;
        while (true) {
            if (this.lines.peek() != null) {
                break;
            }
            Java8Compatibility.a(this.cbuf);
            Reader reader = this.reader;
            if (reader != null) {
                char[] cArr = this.buf;
                i2 = reader.read(cArr, 0, cArr.length);
            } else {
                i2 = this.readable.read(this.cbuf);
            }
            if (i2 == -1) {
                this.lineBuf.b();
                break;
            }
            this.lineBuf.a(this.buf, 0, i2);
        }
        return this.lines.poll();
    }
}
