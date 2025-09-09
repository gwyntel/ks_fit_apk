package com.meizu.cloud.pushsdk.e.h;

import java.io.IOException;

/* loaded from: classes4.dex */
public abstract class f implements l {

    /* renamed from: a, reason: collision with root package name */
    private final l f19494a;

    public f(l lVar) {
        if (lVar == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.f19494a = lVar;
    }

    @Override // com.meizu.cloud.pushsdk.e.h.l
    public void a(b bVar, long j2) throws IOException {
        this.f19494a.a(bVar, j2);
    }

    @Override // com.meizu.cloud.pushsdk.e.h.l, java.lang.AutoCloseable
    public void close() throws IOException {
        this.f19494a.close();
    }

    @Override // com.meizu.cloud.pushsdk.e.h.l, java.io.Flushable
    public void flush() throws IOException {
        this.f19494a.flush();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.f19494a.toString() + ")";
    }
}
