package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class MarkEnforcingInputStream extends FilterInputStream {
    private static final int END_OF_STREAM = -1;
    private static final int UNSET = Integer.MIN_VALUE;
    private int availableBytes;

    public MarkEnforcingInputStream(@NonNull InputStream inputStream) {
        super(inputStream);
        this.availableBytes = Integer.MIN_VALUE;
    }

    private long getBytesToRead(long j2) {
        int i2 = this.availableBytes;
        if (i2 == 0) {
            return -1L;
        }
        return (i2 == Integer.MIN_VALUE || j2 <= ((long) i2)) ? j2 : i2;
    }

    private void updateAvailableBytesAfterRead(long j2) {
        int i2 = this.availableBytes;
        if (i2 == Integer.MIN_VALUE || j2 == -1) {
            return;
        }
        this.availableBytes = (int) (i2 - j2);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        int i2 = this.availableBytes;
        return i2 == Integer.MIN_VALUE ? super.available() : Math.min(i2, super.available());
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void mark(int i2) {
        super.mark(i2);
        this.availableBytes = i2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        if (getBytesToRead(1L) == -1) {
            return -1;
        }
        int i2 = super.read();
        updateAvailableBytesAfterRead(1L);
        return i2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        super.reset();
        this.availableBytes = Integer.MIN_VALUE;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j2) throws IOException {
        long bytesToRead = getBytesToRead(j2);
        if (bytesToRead == -1) {
            return 0L;
        }
        long jSkip = super.skip(bytesToRead);
        updateAvailableBytesAfterRead(jSkip);
        return jSkip;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(@NonNull byte[] bArr, int i2, int i3) throws IOException {
        int bytesToRead = (int) getBytesToRead(i3);
        if (bytesToRead == -1) {
            return -1;
        }
        int i4 = super.read(bArr, i2, bytesToRead);
        updateAvailableBytesAfterRead(i4);
        return i4;
    }
}
