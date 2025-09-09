package com.taobao.accs.utl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public class r extends ByteArrayOutputStream {
    public r(int i2) {
        super(i2);
    }

    public r a(byte b2) throws IOException {
        write(b2);
        return this;
    }

    public r() {
    }

    public r a(short s2) throws IOException {
        write(s2 >> 8);
        write(s2);
        return this;
    }
}
