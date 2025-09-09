package com.meizu.cloud.pushsdk.e.h;

import java.io.IOException;
import java.io.InterruptedIOException;

/* loaded from: classes4.dex */
public class n {

    /* renamed from: a, reason: collision with root package name */
    public static final n f19516a = new a();

    /* renamed from: b, reason: collision with root package name */
    private boolean f19517b;

    /* renamed from: c, reason: collision with root package name */
    private long f19518c;

    class a extends n {
        a() {
        }

        @Override // com.meizu.cloud.pushsdk.e.h.n
        public void a() {
        }
    }

    public void a() throws IOException {
        if (Thread.interrupted()) {
            throw new InterruptedIOException("thread interrupted");
        }
        if (this.f19517b && this.f19518c - System.nanoTime() <= 0) {
            throw new InterruptedIOException("deadline reached");
        }
    }
}
