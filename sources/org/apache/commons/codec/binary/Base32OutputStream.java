package org.apache.commons.codec.binary;

import java.io.OutputStream;

/* loaded from: classes5.dex */
public class Base32OutputStream extends BaseNCodecOutputStream {
    public Base32OutputStream(OutputStream outputStream) {
        this(outputStream, true);
    }

    public Base32OutputStream(OutputStream outputStream, boolean z2) {
        super(outputStream, new Base32(false), z2);
    }

    public Base32OutputStream(OutputStream outputStream, boolean z2, int i2, byte[] bArr) {
        super(outputStream, new Base32(i2, bArr), z2);
    }
}
