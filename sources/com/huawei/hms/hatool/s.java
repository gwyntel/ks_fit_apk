package com.huawei.hms.hatool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public final class s {

    /* renamed from: b, reason: collision with root package name */
    static Map<String, l1> f16464b = new HashMap();

    /* renamed from: c, reason: collision with root package name */
    private static s f16465c;

    /* renamed from: a, reason: collision with root package name */
    private g1 f16466a = new g1();

    private s() {
    }

    public static s c() {
        if (f16465c == null) {
            d();
        }
        return f16465c;
    }

    private static synchronized void d() {
        if (f16465c == null) {
            f16465c = new s();
        }
    }

    public l1 a(String str) {
        return f16464b.get(str);
    }

    public g1 b() {
        return this.f16466a;
    }

    public Set<String> a() {
        return f16464b.keySet();
    }

    public void a(String str, l1 l1Var) {
        f16464b.put(str, l1Var);
    }
}
