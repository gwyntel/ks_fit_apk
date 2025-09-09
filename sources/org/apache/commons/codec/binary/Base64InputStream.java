package org.apache.commons.codec.binary;

import java.io.InputStream;

/* loaded from: classes5.dex */
public class Base64InputStream extends BaseNCodecInputStream {
    public Base64InputStream(InputStream inputStream) {
        this(inputStream, false);
    }

    public Base64InputStream(InputStream inputStream, boolean z2) {
        super(inputStream, new Base64(false), z2);
    }

    public Base64InputStream(InputStream inputStream, boolean z2, int i2, byte[] bArr) {
        super(inputStream, new Base64(i2, bArr), z2);
    }
}
