package com.meizu.cloud.pushsdk.e.b;

import com.meizu.cloud.pushsdk.e.d.k;

/* loaded from: classes4.dex */
public class c<T> {

    /* renamed from: a, reason: collision with root package name */
    private final T f19359a;

    /* renamed from: b, reason: collision with root package name */
    private final com.meizu.cloud.pushsdk.e.c.a f19360b;

    /* renamed from: c, reason: collision with root package name */
    private k f19361c;

    public c(com.meizu.cloud.pushsdk.e.c.a aVar) {
        this.f19359a = null;
        this.f19360b = aVar;
    }

    public static <T> c<T> a(com.meizu.cloud.pushsdk.e.c.a aVar) {
        return new c<>(aVar);
    }

    public T b() {
        return this.f19359a;
    }

    public boolean c() {
        return this.f19360b == null;
    }

    public c(T t2) {
        this.f19359a = t2;
        this.f19360b = null;
    }

    public static <T> c<T> a(T t2) {
        return new c<>(t2);
    }

    public com.meizu.cloud.pushsdk.e.c.a a() {
        return this.f19360b;
    }

    public void a(k kVar) {
        this.f19361c = kVar;
    }
}
