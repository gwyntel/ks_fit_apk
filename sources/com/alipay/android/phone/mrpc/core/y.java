package com.alipay.android.phone.mrpc.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public final class y implements InvocationHandler {

    /* renamed from: a, reason: collision with root package name */
    public g f9047a;

    /* renamed from: b, reason: collision with root package name */
    public Class<?> f9048b;

    /* renamed from: c, reason: collision with root package name */
    public z f9049c;

    public y(g gVar, Class<?> cls, z zVar) {
        this.f9047a = gVar;
        this.f9048b = cls;
        this.f9049c = zVar;
    }

    @Override // java.lang.reflect.InvocationHandler
    public final Object invoke(Object obj, Method method, Object[] objArr) {
        return this.f9049c.a(method, objArr);
    }
}
