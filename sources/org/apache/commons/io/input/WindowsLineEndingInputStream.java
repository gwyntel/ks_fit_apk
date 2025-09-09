package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class WindowsLineEndingInputStream extends InputStream {
    private final boolean ensureLineFeedAtEndOfFile;
    private final InputStream target;
    private boolean slashRSeen = false;
    private boolean slashNSeen = false;
    private boolean injectSlashN = false;
    private boolean eofSeen = false;

    public WindowsLineEndingInputStream(InputStream inputStream, boolean z2) {
        this.target = inputStream;
        this.ensureLineFeedAtEndOfFile = z2;
    }

    private int eofGame() {
        if (!this.ensureLineFeedAtEndOfFile) {
            return -1;
        }
        boolean z2 = this.slashNSeen;
        if (!z2 && !this.slashRSeen) {
            this.slashRSeen = true;
            return 13;
        }
        if (z2) {
            return -1;
        }
        this.slashRSeen = false;
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
        this.slashRSeen = i2 == 13;
        this.slashNSeen = i2 == 10;
        return i2;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        this.target.close();
    }

    @Override // java.io.InputStream
    public synchronized void mark(int i2) {
        throw new UnsupportedOperationException("Mark not supported");
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.eofSeen) {
            return eofGame();
        }
        if (this.injectSlashN) {
            this.injectSlashN = false;
            return 10;
        }
        boolean z2 = this.slashRSeen;
        int withUpdate = readWithUpdate();
        if (this.eofSeen) {
            return eofGame();
        }
        if (withUpdate != 10 || z2) {
            return withUpdate;
        }
        this.injectSlashN = true;
        return 13;
    }
}
