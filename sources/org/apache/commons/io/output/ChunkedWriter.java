package org.apache.commons.io.output;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/* loaded from: classes5.dex */
public class ChunkedWriter extends FilterWriter {
    private static final int DEFAULT_CHUNK_SIZE = 4096;
    private final int chunkSize;

    public ChunkedWriter(Writer writer, int i2) {
        super(writer);
        if (i2 <= 0) {
            throw new IllegalArgumentException();
        }
        this.chunkSize = i2;
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public void write(char[] cArr, int i2, int i3) throws IOException {
        while (i3 > 0) {
            int iMin = Math.min(i3, this.chunkSize);
            ((FilterWriter) this).out.write(cArr, i2, iMin);
            i3 -= iMin;
            i2 += iMin;
        }
    }

    public ChunkedWriter(Writer writer) {
        this(writer, 4096);
    }
}
