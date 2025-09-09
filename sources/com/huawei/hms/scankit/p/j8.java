package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class j8 {

    /* renamed from: a, reason: collision with root package name */
    private final s f17450a;

    /* renamed from: b, reason: collision with root package name */
    private final int f17451b;

    /* renamed from: c, reason: collision with root package name */
    private final int f17452c;

    /* renamed from: d, reason: collision with root package name */
    private final int f17453d;

    /* renamed from: e, reason: collision with root package name */
    private final int f17454e;

    /* renamed from: f, reason: collision with root package name */
    private final int f17455f;

    /* renamed from: g, reason: collision with root package name */
    private final int f17456g;

    public j8(s sVar) throws a {
        this(sVar, 10, sVar.e() / 2, sVar.c() / 2);
    }

    private u6[] b(int[] iArr) throws a {
        int i2 = iArr[1] - iArr[0];
        u6 u6VarA = null;
        u6 u6VarA2 = null;
        for (int i3 = 1; u6VarA2 == null && i3 < i2; i3++) {
            u6VarA2 = a(iArr[0], r6 - i3, r4 + i3, iArr[3]);
        }
        if (u6VarA2 == null) {
            throw a.a();
        }
        u6 u6VarA3 = null;
        for (int i4 = 1; u6VarA3 == null && i4 < i2; i4++) {
            u6VarA3 = a(iArr[0], r8 + i4, r5 + i4, iArr[2]);
        }
        if (u6VarA3 == null) {
            throw a.a();
        }
        u6 u6VarA4 = null;
        for (int i5 = 1; u6VarA4 == null && i5 < i2; i5++) {
            u6VarA4 = a(iArr[1], r10 + i5, r2 - i5, iArr[2]);
        }
        if (u6VarA4 == null) {
            throw a.a();
        }
        for (int i6 = 1; u6VarA == null && i6 < i2; i6++) {
            u6VarA = a(iArr[1], r9 - i6, r3 - i6, iArr[3]);
        }
        if (u6VarA != null) {
            return a(u6VarA, u6VarA2, u6VarA4, u6VarA3);
        }
        throw a.a();
    }

    private void c(int[] iArr) {
        int i2;
        boolean z2 = true;
        while (true) {
            if ((!z2 && iArr[9] == 1) || (i2 = iArr[0]) < 0) {
                return;
            }
            boolean zA = a(iArr[2], iArr[3], i2, false);
            if (zA) {
                iArr[0] = iArr[0] - 1;
                iArr[5] = 1;
                iArr[9] = 1;
            } else if (iArr[9] != 1) {
                iArr[0] = iArr[0] - 1;
            }
            z2 = zA;
        }
    }

    private void d(int[] iArr) {
        int i2;
        boolean zA = true;
        while (true) {
            if ((!zA && iArr[7] == 1) || (i2 = iArr[1]) >= this.f17452c) {
                return;
            }
            zA = a(iArr[2], iArr[3], i2, false);
            if (zA) {
                iArr[1] = iArr[1] + 1;
                iArr[5] = 1;
                iArr[7] = 1;
            } else if (iArr[7] != 1) {
                iArr[1] = iArr[1] + 1;
            }
        }
    }

    private void e(int[] iArr) {
        int i2;
        boolean z2 = true;
        while (true) {
            if ((!z2 && iArr[10] == 1) || (i2 = iArr[2]) < 0) {
                return;
            }
            boolean zA = a(iArr[0], iArr[1], i2, true);
            if (zA) {
                iArr[2] = iArr[2] - 1;
                iArr[5] = 1;
                iArr[10] = 1;
            } else if (iArr[10] != 1) {
                iArr[2] = iArr[2] - 1;
            }
            z2 = zA;
        }
    }

    public u6[] a() throws a {
        int[] iArr = {this.f17453d, this.f17454e, this.f17456g, this.f17455f, 0, 1, 0, 0, 0, 0, 0};
        while (true) {
            if (iArr[5] != 1) {
                break;
            }
            iArr[5] = 0;
            d(iArr);
            if (iArr[1] >= this.f17452c) {
                iArr[4] = 1;
                break;
            }
            a(iArr);
            if (iArr[3] >= this.f17451b) {
                iArr[4] = 1;
                break;
            }
            c(iArr);
            if (iArr[0] < 0) {
                iArr[4] = 1;
                break;
            }
            e(iArr);
            if (iArr[2] < 0) {
                iArr[4] = 1;
                break;
            }
            if (iArr[5] == 1) {
                iArr[6] = 1;
            }
        }
        if (iArr[4] == 1 || iArr[6] != 1) {
            throw a.a();
        }
        return b(iArr);
    }

    public j8(s sVar, int i2, int i3, int i4) throws a {
        this.f17450a = sVar;
        int iC = sVar.c();
        this.f17451b = iC;
        int iE = sVar.e();
        this.f17452c = iE;
        int i5 = i2 / 2;
        int i6 = i3 - i5;
        this.f17453d = i6;
        int i7 = i3 + i5;
        this.f17454e = i7;
        int i8 = i4 - i5;
        this.f17456g = i8;
        int i9 = i4 + i5;
        this.f17455f = i9;
        if (i8 < 0 || i6 < 0 || i9 >= iC || i7 >= iE) {
            throw a.a();
        }
    }

    private void a(int[] iArr) {
        int i2;
        boolean z2 = true;
        while (true) {
            if ((!z2 && iArr[8] == 1) || (i2 = iArr[3]) >= this.f17451b) {
                return;
            }
            boolean zA = a(iArr[0], iArr[1], i2, true);
            if (zA) {
                iArr[3] = iArr[3] + 1;
                iArr[5] = 1;
                iArr[8] = 1;
            } else if (iArr[8] != 1) {
                iArr[3] = iArr[3] + 1;
            }
            z2 = zA;
        }
    }

    private u6 a(float f2, float f3, float f4, float f5) {
        int iA = s4.a(s4.a(f2, f3, f4, f5));
        float f6 = iA;
        float f7 = (f4 - f2) / f6;
        float f8 = (f5 - f3) / f6;
        for (int i2 = 0; i2 < iA; i2++) {
            float f9 = i2;
            int iA2 = s4.a((f9 * f7) + f2);
            int iA3 = s4.a((f9 * f8) + f3);
            if (this.f17450a.b(iA2, iA3)) {
                return new u6(iA2, iA3);
            }
        }
        return null;
    }

    private u6[] a(u6 u6Var, u6 u6Var2, u6 u6Var3, u6 u6Var4) {
        float fB = u6Var.b();
        float fC = u6Var.c();
        float fB2 = u6Var2.b();
        float fC2 = u6Var2.c();
        float fB3 = u6Var3.b();
        float fC3 = u6Var3.c();
        float fB4 = u6Var4.b();
        float fC4 = u6Var4.c();
        if (fB < this.f17452c / 2.0f) {
            return new u6[]{new u6(fB4 - 1.0f, fC4 + 1.0f), new u6(fB2 + 1.0f, fC2 + 1.0f), new u6(fB3 - 1.0f, fC3 - 1.0f), new u6(fB + 1.0f, fC - 1.0f)};
        }
        return new u6[]{new u6(fB4 + 1.0f, fC4 + 1.0f), new u6(fB2 + 1.0f, fC2 - 1.0f), new u6(fB3 - 1.0f, fC3 + 1.0f), new u6(fB - 1.0f, fC - 1.0f)};
    }

    private boolean a(int i2, int i3, int i4, boolean z2) {
        if (z2) {
            while (i2 <= i3) {
                if (this.f17450a.b(i2, i4)) {
                    return true;
                }
                i2++;
            }
            return false;
        }
        while (i2 <= i3) {
            if (this.f17450a.b(i4, i2)) {
                return true;
            }
            i2++;
        }
        return false;
    }
}
