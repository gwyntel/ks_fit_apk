package org.apache.commons.io.output;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public class ChunkedOutputStream extends FilterOutputStream {
    private static final int DEFAULT_CHUNK_SIZE = 4096;
    private final int chunkSize;

    public ChunkedOutputStream(OutputStream outputStream, int i2) {
        super(outputStream);
        if (i2 <= 0) {
            throw new IllegalArgumentException();
        }
        this.chunkSize = i2;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        while (i3 > 0) {
            int iMin = Math.min(i3, this.chunkSize);
            ((FilterOutputStream) this).out.write(bArr, i2, iMin);
            i3 -= iMin;
            i2 += iMin;
        }
    }

    public ChunkedOutputStream(OutputStream outputStream) {
        this(outputStream, 4096);
    }
}
