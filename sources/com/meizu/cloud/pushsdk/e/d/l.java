package com.meizu.cloud.pushsdk.e.d;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public abstract class l implements Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        m.a(f());
    }

    public final InputStream e() {
        return f().d();
    }

    public abstract com.meizu.cloud.pushsdk.e.h.d f();
}
