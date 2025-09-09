package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class d2 {

    /* renamed from: a, reason: collision with root package name */
    private final s f17102a;

    /* renamed from: b, reason: collision with root package name */
    private final j8 f17103b;

    public d2(s sVar) throws a {
        this.f17102a = sVar;
        this.f17103b = new j8(sVar);
    }

    private u6[] b(u6[] u6VarArr) {
        u6 u6Var = u6VarArr[0];
        u6 u6Var2 = u6VarArr[1];
        u6 u6Var3 = u6VarArr[3];
        u6 u6Var4 = u6VarArr[2];
        int iA = a(u6Var, u6Var2);
        int iA2 = a(u6Var2, u6Var3);
        int iA3 = a(u6Var3, u6Var4);
        int iA4 = a(u6Var4, u6Var);
        u6[] u6VarArr2 = {u6Var4, u6Var, u6Var2, u6Var3};
        if (iA > iA2) {
            u6VarArr2[0] = u6Var;
            u6VarArr2[1] = u6Var2;
            u6VarArr2[2] = u6Var3;
            u6VarArr2[3] = u6Var4;
            iA = iA2;
        }
        if (iA > iA3) {
            u6VarArr2[0] = u6Var2;
            u6VarArr2[1] = u6Var3;
            u6VarArr2[2] = u6Var4;
            u6VarArr2[3] = u6Var;
        } else {
            iA3 = iA;
        }
        if (iA3 > iA4) {
            u6VarArr2[0] = u6Var3;
            u6VarArr2[1] = u6Var4;
            u6VarArr2[2] = u6Var;
            u6VarArr2[3] = u6Var2;
        }
        return u6VarArr2;
    }

    private u6[] c(u6[] u6VarArr) {
        u6 u6Var = u6VarArr[0];
        u6 u6Var2 = u6VarArr[1];
        u6 u6Var3 = u6VarArr[2];
        u6 u6Var4 = u6VarArr[3];
        int iA = (a(u6Var, u6Var4) + 1) * 4;
        if (a(a(u6Var2, u6Var3, iA), u6Var) < a(a(u6Var3, u6Var2, iA), u6Var4)) {
            u6VarArr[0] = u6Var;
            u6VarArr[1] = u6Var2;
            u6VarArr[2] = u6Var3;
            u6VarArr[3] = u6Var4;
        } else {
            u6VarArr[0] = u6Var2;
            u6VarArr[1] = u6Var3;
            u6VarArr[2] = u6Var4;
            u6VarArr[3] = u6Var;
        }
        return u6VarArr;
    }

    private u6[] d(u6[] u6VarArr) {
        u6 u6Var = u6VarArr[0];
        u6 u6Var2 = u6VarArr[1];
        u6 u6Var3 = u6VarArr[2];
        u6 u6Var4 = u6VarArr[3];
        int iA = a(u6Var, u6Var4) + 1;
        u6 u6VarA = a(u6Var, u6Var2, (a(u6Var3, u6Var4) + 1) * 4);
        u6 u6VarA2 = a(u6Var3, u6Var2, iA * 4);
        int iA2 = a(u6VarA, u6Var4);
        int i2 = iA2 + 1;
        int iA3 = a(u6VarA2, u6Var4);
        int i3 = iA3 + 1;
        if ((i2 & 1) == 1) {
            i2 = iA2 + 2;
        }
        if ((i3 & 1) == 1) {
            i3 = iA3 + 2;
        }
        float fB = (((u6Var.b() + u6Var2.b()) + u6Var3.b()) + u6Var4.b()) / 4.0f;
        float fC = (((u6Var.c() + u6Var2.c()) + u6Var3.c()) + u6Var4.c()) / 4.0f;
        u6 u6VarA3 = a(u6Var, fB, fC);
        u6 u6VarA4 = a(u6Var2, fB, fC);
        u6 u6VarA5 = a(u6Var3, fB, fC);
        u6 u6VarA6 = a(u6Var4, fB, fC);
        int i4 = i3 * 4;
        int i5 = i2 * 4;
        return new u6[]{a(a(u6VarA3, u6VarA4, i4), u6VarA6, i5), a(a(u6VarA4, u6VarA3, i4), u6VarA5, i5), a(a(u6VarA5, u6VarA6, i4), u6VarA4, i5), a(a(u6VarA6, u6VarA5, i4), u6VarA3, i5)};
    }

    public j2 a() throws a {
        int iMax;
        int i2;
        u6[] u6VarArrC = c(b(this.f17103b.a()));
        u6 u6VarA = a(u6VarArrC);
        u6VarArrC[3] = u6VarA;
        if (u6VarA == null) {
            throw a.a();
        }
        u6[] u6VarArrD = d(u6VarArrC);
        u6 u6Var = u6VarArrD[0];
        u6 u6Var2 = u6VarArrD[1];
        u6 u6Var3 = u6VarArrD[2];
        u6 u6Var4 = u6VarArrD[3];
        int iA = a(u6Var, u6Var4);
        int i3 = iA + 1;
        int iA2 = a(u6Var3, u6Var4);
        int i4 = iA2 + 1;
        if ((i3 & 1) == 1) {
            i3 = iA + 2;
        }
        if ((i4 & 1) == 1) {
            i4 = iA2 + 2;
        }
        if (i3 * 4 >= i4 * 7 || i4 * 4 >= i3 * 7) {
            iMax = i3;
            i2 = i4;
        } else {
            iMax = Math.max(i3, i4);
            i2 = iMax;
        }
        return new j2(a(this.f17102a, u6Var, u6Var2, u6Var3, u6Var4, iMax, i2), new u6[]{u6Var, u6Var2, u6Var3, u6Var4});
    }

    private static u6 a(u6 u6Var, u6 u6Var2, int i2) {
        float f2 = i2 + 1;
        return new u6(u6Var.b() + ((u6Var2.b() - u6Var.b()) / f2), u6Var.c() + ((u6Var2.c() - u6Var.c()) / f2));
    }

    private static u6 a(u6 u6Var, float f2, float f3) {
        float fB = u6Var.b();
        float fC = u6Var.c();
        return new u6(fB < f2 ? fB - 1.0f : fB + 1.0f, fC < f3 ? fC - 1.0f : fC + 1.0f);
    }

    private u6 a(u6[] u6VarArr) {
        u6 u6Var = u6VarArr[0];
        u6 u6Var2 = u6VarArr[1];
        u6 u6Var3 = u6VarArr[2];
        u6 u6Var4 = u6VarArr[3];
        int iA = a(u6Var, u6Var4);
        u6 u6VarA = a(u6Var, u6Var2, (a(u6Var2, u6Var4) + 1) * 4);
        u6 u6VarA2 = a(u6Var3, u6Var2, (iA + 1) * 4);
        int iA2 = a(u6VarA, u6Var4);
        int iA3 = a(u6VarA2, u6Var4);
        float f2 = iA2 + 1;
        u6 u6Var5 = new u6(u6Var4.b() + ((u6Var3.b() - u6Var2.b()) / f2), u6Var4.c() + ((u6Var3.c() - u6Var2.c()) / f2));
        float f3 = iA3 + 1;
        u6 u6Var6 = new u6(u6Var4.b() + ((u6Var.b() - u6Var2.b()) / f3), u6Var4.c() + ((u6Var.c() - u6Var2.c()) / f3));
        if (a(u6Var5)) {
            return (a(u6Var6) && a(u6VarA, u6Var5) + a(u6VarA2, u6Var5) <= a(u6VarA, u6Var6) + a(u6VarA2, u6Var6)) ? u6Var6 : u6Var5;
        }
        if (a(u6Var6)) {
            return u6Var6;
        }
        return null;
    }

    private boolean a(u6 u6Var) {
        return u6Var.b() >= 0.0f && u6Var.b() < ((float) this.f17102a.e()) && u6Var.c() > 0.0f && u6Var.c() < ((float) this.f17102a.c());
    }

    private static s a(s sVar, u6 u6Var, u6 u6Var2, u6 u6Var3, u6 u6Var4, int i2, int i3) throws a {
        float f2 = i2 - 0.5f;
        float f3 = i3 - 0.5f;
        return s3.a().a(sVar, i2, i3, 0.5f, 0.5f, f2, 0.5f, f2, f3, 0.5f, f3, u6Var.b(), u6Var.c(), u6Var4.b(), u6Var4.c(), u6Var3.b(), u6Var3.c(), u6Var2.b(), u6Var2.c());
    }

    private int a(u6 u6Var, u6 u6Var2) {
        int i2;
        boolean z2;
        d2 d2Var = this;
        int iB = (int) u6Var.b();
        int iC = (int) u6Var.c();
        int iB2 = (int) u6Var2.b();
        int iC2 = (int) u6Var2.c();
        boolean z3 = Math.abs(iC2 - iC) > Math.abs(iB2 - iB);
        if (!z3) {
            iC = iB;
            iB = iC;
            iC2 = iB2;
            iB2 = iC2;
        }
        int iAbs = Math.abs(iC2 - iC);
        int iAbs2 = Math.abs(iB2 - iB);
        int i3 = (-iAbs) / 2;
        int i4 = iB < iB2 ? 1 : -1;
        int i5 = iC < iC2 ? 1 : -1;
        boolean zB = d2Var.f17102a.b(z3 ? iB : iC, z3 ? iC : iB);
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (iC != iC2) {
            boolean zB2 = d2Var.f17102a.b(z3 ? iB : iC, z3 ? iC : iB);
            i6++;
            if (zB2 != zB) {
                i2 = iC2;
                z2 = z3;
                if (i6 > Math.ceil(i7 / 1.5d)) {
                    i8++;
                    i7 -= (i7 - i6) / i8;
                    zB = zB2;
                    i6 = 0;
                }
            } else {
                i2 = iC2;
                z2 = z3;
            }
            i3 += iAbs2;
            if (i3 > 0) {
                if (iB == iB2) {
                    break;
                }
                iB += i4;
                i3 -= iAbs;
            }
            iC += i5;
            d2Var = this;
            z3 = z2;
            iC2 = i2;
        }
        return i8;
    }
}
