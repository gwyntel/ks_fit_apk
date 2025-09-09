package org.apache.commons.codec.binary;

import java.io.OutputStream;

/* loaded from: classes5.dex */
public class Base64OutputStream extends BaseNCodecOutputStream {
    public Base64OutputStream(OutputStream outputStream) {
        this(outputStream, true);
    }

    public Base64OutputStream(OutputStream outputStream, boolean z2) {
        super(outputStream, new Base64(false), z2);
    }

    public Base64OutputStream(OutputStream outputStream, boolean z2, int i2, byte[] bArr) {
        super(outputStream, new Base64(i2, bArr), z2);
    }
}
