package com.huawei.hms.hatool;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class y {

    /* renamed from: b, reason: collision with root package name */
    private static y f16500b;

    /* renamed from: a, reason: collision with root package name */
    private volatile Map<String, p0> f16501a = new HashMap();

    private y() {
    }

    private p0 a(String str) {
        if (!this.f16501a.containsKey(str)) {
            this.f16501a.put(str, new p0());
        }
        return this.f16501a.get(str);
    }

    private static synchronized void b() {
        if (f16500b == null) {
            f16500b = new y();
        }
    }

    public p0 a(String str, long j2) {
        p0 p0VarA = a(str);
        p0VarA.a(j2);
        return p0VarA;
    }

    public static y a() {
        if (f16500b == null) {
            b();
        }
        return f16500b;
    }
}
