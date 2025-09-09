package com.alipay.apmobilesecuritysdk.f;

import java.util.LinkedList;

/* loaded from: classes2.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    public static b f9082a = new b();

    /* renamed from: b, reason: collision with root package name */
    public Thread f9083b = null;

    /* renamed from: c, reason: collision with root package name */
    public LinkedList<Runnable> f9084c = new LinkedList<>();

    public static b a() {
        return f9082a;
    }

    public static /* synthetic */ Thread b(b bVar) {
        bVar.f9083b = null;
        return null;
    }

    public final synchronized void a(Runnable runnable) {
        this.f9084c.add(runnable);
        if (this.f9083b == null) {
            Thread thread = new Thread(new c(this));
            this.f9083b = thread;
            thread.start();
        }
    }
}
