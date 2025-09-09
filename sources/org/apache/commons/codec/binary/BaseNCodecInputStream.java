package org.apache.commons.codec.binary;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class BaseNCodecInputStream extends FilterInputStream {
    private final BaseNCodec baseNCodec;
    private final boolean doEncode;
    private final byte[] singleByte;

    protected BaseNCodecInputStream(InputStream inputStream, BaseNCodec baseNCodec, boolean z2) {
        super(inputStream);
        this.singleByte = new byte[1];
        this.doEncode = z2;
        this.baseNCodec = baseNCodec;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i2 = read(this.singleByte, 0, 1);
        while (i2 == 0) {
            i2 = read(this.singleByte, 0, 1);
        }
        if (i2 <= 0) {
            return -1;
        }
        byte b2 = this.singleByte[0];
        return b2 < 0 ? b2 + 256 : b2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        bArr.getClass();
        if (i2 >= 0 && i3 >= 0) {
            if (i2 > bArr.length || i2 + i3 > bArr.length) {
                throw new IndexOutOfBoundsException();
            }
            if (i3 == 0) {
                return 0;
            }
            int i4 = 0;
            while (i4 == 0) {
                if (!this.baseNCodec.g()) {
                    byte[] bArr2 = new byte[this.doEncode ? 4096 : 8192];
                    int i5 = ((FilterInputStream) this).in.read(bArr2);
                    if (this.doEncode) {
                        this.baseNCodec.d(bArr2, 0, i5);
                    } else {
                        this.baseNCodec.c(bArr2, 0, i5);
                    }
                }
                i4 = this.baseNCodec.i(bArr, i2, i3);
            }
            return i4;
        }
        throw new IndexOutOfBoundsException();
    }
}
