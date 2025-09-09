package org.apache.commons.codec.binary;

import java.io.InputStream;

/* loaded from: classes5.dex */
public class Base32InputStream extends BaseNCodecInputStream {
    public Base32InputStream(InputStream inputStream) {
        this(inputStream, false);
    }

    public Base32InputStream(InputStream inputStream, boolean z2) {
        super(inputStream, new Base32(false), z2);
    }

    public Base32InputStream(InputStream inputStream, boolean z2, int i2, byte[] bArr) {
        super(inputStream, new Base32(i2, bArr), z2);
    }
}
