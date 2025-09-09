package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public enum c3 {
    L(1),
    M(0),
    Q(3),
    H(2);


    /* renamed from: f, reason: collision with root package name */
    private static final c3[] f17063f;

    /* renamed from: a, reason: collision with root package name */
    private final int f17065a;

    static {
        c3 c3Var = L;
        c3 c3Var2 = M;
        c3 c3Var3 = Q;
        f17063f = new c3[]{c3Var2, c3Var, H, c3Var3};
    }

    c3(int i2) {
        this.f17065a = i2;
    }

    public static c3 a(int i2) {
        if (i2 >= 0) {
            c3[] c3VarArr = f17063f;
            if (i2 < c3VarArr.length) {
                return c3VarArr[i2];
            }
        }
        throw new IllegalArgumentException();
    }
}
