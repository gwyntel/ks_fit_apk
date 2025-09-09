package com.meizu.cloud.pushsdk.d;

/* loaded from: classes4.dex */
public class h<T> {

    /* renamed from: a, reason: collision with root package name */
    private T f19224a;

    /* renamed from: b, reason: collision with root package name */
    private T f19225b;

    protected h(T t2) {
        if (t2 == null) {
            throw new RuntimeException("proxy must be has a default implementation");
        }
        this.f19225b = t2;
    }

    protected T b() {
        T t2 = this.f19224a;
        return t2 != null ? t2 : this.f19225b;
    }
}
