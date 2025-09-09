package com.xiaomi.push;

import java.io.ByteArrayOutputStream;

/* loaded from: classes4.dex */
public class kb extends ByteArrayOutputStream {
    public kb(int i2) {
        super(i2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] m666a() {
        return ((ByteArrayOutputStream) this).buf;
    }

    public kb() {
    }

    public int a() {
        return ((ByteArrayOutputStream) this).count;
    }
}
