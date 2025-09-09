package com.alipay.sdk.m.c0;

/* loaded from: classes2.dex */
public final class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ b f9220a;

    public c(b bVar) {
        this.f9220a = bVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.f9220a.b();
        } catch (Exception e2) {
            d.a(e2);
        }
    }
}
