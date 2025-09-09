package com.xiaomi.infra.galaxy.fds.android.util;

import com.xiaomi.infra.galaxy.fds.android.model.ObjectMetadata;
import com.xiaomi.infra.galaxy.fds.android.model.ProgressListener;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class ObjectInputStream extends FilterInputStream {
    private long lastNotifyTime;
    private final ProgressListener listener;
    private final ObjectMetadata metadata;
    private long totalBytesRead;

    public ObjectInputStream(InputStream inputStream, ObjectMetadata objectMetadata, ProgressListener progressListener) {
        super(inputStream);
        this.metadata = objectMetadata;
        this.listener = progressListener;
    }

    private void notifyListener(boolean z2) {
        if (this.listener != null) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (!z2 || jCurrentTimeMillis - this.lastNotifyTime >= this.listener.progressInterval()) {
                this.lastNotifyTime = jCurrentTimeMillis;
                this.listener.onProgress(this.totalBytesRead, this.metadata.getContentLength());
            }
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        notifyListener(false);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = super.read(bArr, i2, i3);
        if (i4 != -1) {
            this.totalBytesRead += i4;
            notifyListener(true);
        }
        return i4;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        super.reset();
        this.totalBytesRead = 0L;
        notifyListener(true);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i2 = super.read();
        if (i2 != -1) {
            this.totalBytesRead++;
            notifyListener(true);
        }
        return i2;
    }
}
