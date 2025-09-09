package org.apache.commons.io.input;

import java.io.InputStream;

/* loaded from: classes5.dex */
public class InfiniteCircularInputStream extends InputStream {
    private int position = -1;
    private final byte[] repeatedContent;

    public InfiniteCircularInputStream(byte[] bArr) {
        this.repeatedContent = bArr;
    }

    @Override // java.io.InputStream
    public int read() {
        int i2 = this.position + 1;
        byte[] bArr = this.repeatedContent;
        int length = i2 % bArr.length;
        this.position = length;
        return bArr[length] & 255;
    }
}
