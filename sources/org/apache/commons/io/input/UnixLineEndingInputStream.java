package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class UnixLineEndingInputStream extends InputStream {
    private final boolean ensureLineFeedAtEndOfFile;
    private final InputStream target;
    private boolean slashNSeen = false;
    private boolean slashRSeen = false;
    private boolean eofSeen = false;

    public UnixLineEndingInputStream(InputStream inputStream, boolean z2) {
        this.target = inputStream;
        this.ensureLineFeedAtEndOfFile = z2;
    }

    private int eofGame(boolean z2) {
        if (z2 || !this.ensureLineFeedAtEndOfFile || this.slashNSeen) {
            return -1;
        }
        this.slashNSeen = true;
        return 10;
    }

    private int readWithUpdate() throws IOException {
        int i2 = this.target.read();
        boolean z2 = i2 == -1;
        this.eofSeen = z2;
        if (z2) {
            return i2;
        }
        this.slashNSeen = i2 == 10;
        this.slashRSeen = i2 == 13;
        return i2;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        this.target.close();
    }

    @Override // java.io.InputStream
    public synchronized void mark(int i2) {
        throw new UnsupportedOperationException("Mark notsupported");
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        boolean z2 = this.slashRSeen;
        if (this.eofSeen) {
            return eofGame(z2);
        }
        int withUpdate = readWithUpdate();
        if (this.eofSeen) {
            return eofGame(z2);
        }
        if (this.slashRSeen) {
            return 10;
        }
        return (z2 && this.slashNSeen) ? read() : withUpdate;
    }
}
