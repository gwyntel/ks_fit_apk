package org.apache.commons.io.input;

import java.io.IOException;
import java.io.Reader;

/* loaded from: classes5.dex */
public class BoundedReader extends Reader {
    private static final int INVALID = -1;
    private int charsRead = 0;
    private int markedAt = -1;
    private final int maxCharsFromTargetReader;
    private int readAheadLimit;
    private final Reader target;

    public BoundedReader(Reader reader, int i2) throws IOException {
        this.target = reader;
        this.maxCharsFromTargetReader = i2;
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.target.close();
    }

    @Override // java.io.Reader
    public void mark(int i2) throws IOException {
        int i3 = this.charsRead;
        this.readAheadLimit = i2 - i3;
        this.markedAt = i3;
        this.target.mark(i2);
    }

    @Override // java.io.Reader
    public int read() throws IOException {
        int i2 = this.charsRead;
        if (i2 >= this.maxCharsFromTargetReader) {
            return -1;
        }
        int i3 = this.markedAt;
        if (i3 >= 0 && i2 - i3 >= this.readAheadLimit) {
            return -1;
        }
        this.charsRead = i2 + 1;
        return this.target.read();
    }

    @Override // java.io.Reader
    public void reset() throws IOException {
        this.charsRead = this.markedAt;
        this.target.reset();
    }

    @Override // java.io.Reader
    public int read(char[] cArr, int i2, int i3) throws IOException {
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = read();
            if (i5 == -1) {
                if (i4 == 0) {
                    return -1;
                }
                return i4;
            }
            cArr[i2 + i4] = (char) i5;
        }
        return i3;
    }
}
