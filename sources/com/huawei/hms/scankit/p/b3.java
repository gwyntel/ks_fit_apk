package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public enum b3 {
    L(1),
    M(0),
    Q(3),
    H(2);


    /* renamed from: f, reason: collision with root package name */
    private static final b3[] f17002f;

    /* renamed from: a, reason: collision with root package name */
    private final int f17004a;

    static {
        b3 b3Var = L;
        b3 b3Var2 = M;
        b3 b3Var3 = Q;
        f17002f = new b3[]{b3Var2, b3Var, H, b3Var3};
    }

    b3(int i2) {
        this.f17004a = i2;
    }

    public int a() {
        return this.f17004a;
    }

    public static b3 a(int i2) {
        if (i2 >= 0) {
            b3[] b3VarArr = f17002f;
            if (i2 < b3VarArr.length) {
                return b3VarArr[i2];
            }
        }
        throw new IllegalArgumentException();
    }
}
