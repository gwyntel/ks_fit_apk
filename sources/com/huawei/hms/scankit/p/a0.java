package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class a0 {

    /* renamed from: a, reason: collision with root package name */
    private final s f16945a;

    /* renamed from: b, reason: collision with root package name */
    private final u6 f16946b;

    /* renamed from: c, reason: collision with root package name */
    private final u6 f16947c;

    /* renamed from: d, reason: collision with root package name */
    private final u6 f16948d;

    /* renamed from: e, reason: collision with root package name */
    private final u6 f16949e;

    /* renamed from: f, reason: collision with root package name */
    private final int f16950f;

    /* renamed from: g, reason: collision with root package name */
    private final int f16951g;

    /* renamed from: h, reason: collision with root package name */
    private final int f16952h;

    /* renamed from: i, reason: collision with root package name */
    private final int f16953i;

    a0(s sVar, u6 u6Var, u6 u6Var2, u6 u6Var3, u6 u6Var4) throws a {
        boolean z2 = u6Var == null || u6Var2 == null;
        boolean z3 = u6Var3 == null || u6Var4 == null;
        if (z2 && z3) {
            throw a.a();
        }
        if (z2) {
            u6Var = new u6(0.0f, u6Var3.c());
            u6Var2 = new u6(0.0f, u6Var4.c());
        } else if (z3) {
            u6Var3 = new u6(sVar.e() - 1, u6Var.c());
            u6Var4 = new u6(sVar.e() - 1, u6Var2.c());
        }
        this.f16945a = sVar;
        this.f16946b = u6Var;
        this.f16947c = u6Var2;
        this.f16948d = u6Var3;
        this.f16949e = u6Var4;
        this.f16950f = (int) Math.min(u6Var.b(), u6Var2.b());
        this.f16951g = (int) Math.max(u6Var3.b(), u6Var4.b());
        this.f16952h = (int) Math.min(u6Var.c(), u6Var3.c());
        this.f16953i = (int) Math.max(u6Var2.c(), u6Var4.c());
    }

    static a0 a(a0 a0Var, a0 a0Var2) throws a {
        return a0Var == null ? a0Var2 : a0Var2 == null ? a0Var : new a0(a0Var.f16945a, a0Var.f16946b, a0Var.f16947c, a0Var2.f16948d, a0Var2.f16949e);
    }

    u6 b() {
        return this.f16949e;
    }

    int c() {
        return this.f16951g;
    }

    int d() {
        return this.f16953i;
    }

    int e() {
        return this.f16950f;
    }

    int f() {
        return this.f16952h;
    }

    u6 g() {
        return this.f16946b;
    }

    u6 h() {
        return this.f16948d;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    com.huawei.hms.scankit.p.a0 a(int r13, int r14, boolean r15) throws com.huawei.hms.scankit.p.a {
        /*
            r12 = this;
            com.huawei.hms.scankit.p.u6 r0 = r12.f16946b
            com.huawei.hms.scankit.p.u6 r1 = r12.f16947c
            com.huawei.hms.scankit.p.u6 r2 = r12.f16948d
            com.huawei.hms.scankit.p.u6 r3 = r12.f16949e
            if (r13 <= 0) goto L2a
            if (r15 == 0) goto Le
            r4 = r0
            goto Lf
        Le:
            r4 = r2
        Lf:
            float r5 = r4.c()
            int r5 = (int) r5
            int r5 = r5 - r13
            if (r5 >= 0) goto L18
            r5 = 0
        L18:
            com.huawei.hms.scankit.p.u6 r13 = new com.huawei.hms.scankit.p.u6
            float r4 = r4.b()
            float r5 = (float) r5
            r13.<init>(r4, r5)
            if (r15 == 0) goto L27
            r8 = r13
        L25:
            r10 = r2
            goto L2c
        L27:
            r10 = r13
            r8 = r0
            goto L2c
        L2a:
            r8 = r0
            goto L25
        L2c:
            if (r14 <= 0) goto L5d
            if (r15 == 0) goto L33
            com.huawei.hms.scankit.p.u6 r13 = r12.f16947c
            goto L35
        L33:
            com.huawei.hms.scankit.p.u6 r13 = r12.f16949e
        L35:
            float r0 = r13.c()
            int r0 = (int) r0
            int r0 = r0 + r14
            com.huawei.hms.scankit.p.s r14 = r12.f16945a
            int r14 = r14.c()
            if (r0 < r14) goto L4b
            com.huawei.hms.scankit.p.s r14 = r12.f16945a
            int r14 = r14.c()
            int r0 = r14 + (-1)
        L4b:
            com.huawei.hms.scankit.p.u6 r14 = new com.huawei.hms.scankit.p.u6
            float r13 = r13.b()
            float r0 = (float) r0
            r14.<init>(r13, r0)
            if (r15 == 0) goto L5a
            r9 = r14
        L58:
            r11 = r3
            goto L5f
        L5a:
            r11 = r14
            r9 = r1
            goto L5f
        L5d:
            r9 = r1
            goto L58
        L5f:
            com.huawei.hms.scankit.p.a0 r13 = new com.huawei.hms.scankit.p.a0
            com.huawei.hms.scankit.p.s r7 = r12.f16945a
            r6 = r13
            r6.<init>(r7, r8, r9, r10, r11)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.a0.a(int, int, boolean):com.huawei.hms.scankit.p.a0");
    }

    u6 a() {
        return this.f16947c;
    }

    a0(a0 a0Var) {
        this.f16945a = a0Var.f16945a;
        this.f16946b = a0Var.g();
        this.f16947c = a0Var.a();
        this.f16948d = a0Var.h();
        this.f16949e = a0Var.b();
        this.f16950f = a0Var.e();
        this.f16951g = a0Var.c();
        this.f16952h = a0Var.f();
        this.f16953i = a0Var.d();
    }
}
