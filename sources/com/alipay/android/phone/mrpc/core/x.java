package com.alipay.android.phone.mrpc.core;

import java.lang.reflect.Proxy;

/* loaded from: classes2.dex */
public final class x {

    /* renamed from: a, reason: collision with root package name */
    public g f9045a;

    /* renamed from: b, reason: collision with root package name */
    public z f9046b = new z(this);

    public x(g gVar) {
        this.f9045a = gVar;
    }

    public final g a() {
        return this.f9045a;
    }

    public final <T> T a(Class<T> cls) {
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new y(this.f9045a, cls, this.f9046b));
    }
}
