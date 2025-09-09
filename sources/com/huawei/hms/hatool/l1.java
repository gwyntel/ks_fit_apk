package com.huawei.hms.hatool;

/* loaded from: classes4.dex */
public class l1 {

    /* renamed from: a, reason: collision with root package name */
    private s0 f16426a;

    /* renamed from: b, reason: collision with root package name */
    private s0 f16427b;

    /* renamed from: c, reason: collision with root package name */
    private s0 f16428c;

    /* renamed from: d, reason: collision with root package name */
    private s0 f16429d;

    public l1(String str) {
    }

    public s0 a() {
        return this.f16428c;
    }

    public s0 b() {
        return this.f16426a;
    }

    public s0 c() {
        return this.f16427b;
    }

    public s0 d() {
        return this.f16429d;
    }

    public s0 a(String str) {
        if (str.equals("oper")) {
            return c();
        }
        if (str.equals("maint")) {
            return b();
        }
        if (str.equals("diffprivacy")) {
            return a();
        }
        if (str.equals("preins")) {
            return d();
        }
        v.f("hmsSdk", "HiAnalyticsInstData.getConfig(type): wrong type: " + str);
        return null;
    }

    public void b(s0 s0Var) {
        this.f16427b = s0Var;
    }

    public void a(s0 s0Var) {
        this.f16426a = s0Var;
    }
}
