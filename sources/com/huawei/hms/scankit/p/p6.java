package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class p6 {

    /* renamed from: a, reason: collision with root package name */
    private final o3 f17667a;

    public p6(o3 o3Var) {
        this.f17667a = o3Var;
    }

    public void a(int[] iArr, int i2) throws a {
        p3 p3Var = new p3(this.f17667a, iArr);
        int[] iArr2 = new int[i2];
        boolean z2 = true;
        for (int i3 = 0; i3 < i2; i3++) {
            o3 o3Var = this.f17667a;
            int iA = p3Var.a(o3Var.a(o3Var.a() + i3));
            iArr2[(i2 - 1) - i3] = iA;
            if (iA != 0) {
                z2 = false;
            }
        }
        if (z2) {
            return;
        }
        p3[] p3VarArrA = a(this.f17667a.b(i2, 1), new p3(this.f17667a, iArr2), i2);
        p3 p3Var2 = p3VarArrA[0];
        p3 p3Var3 = p3VarArrA[1];
        int[] iArrA = a(p3Var2);
        int[] iArrA2 = a(p3Var3, iArrA);
        for (int i4 = 0; i4 < iArrA.length; i4++) {
            int length = (iArr.length - 1) - this.f17667a.c(iArrA[i4]);
            if (length < 0) {
                throw a.a("Bad error location");
            }
            iArr[length] = o3.a(iArr[length], iArrA2[i4]);
        }
    }

    private p3[] a(p3 p3Var, p3 p3Var2, int i2) throws a {
        if (p3Var.b() >= p3Var2.b()) {
            p3Var2 = p3Var;
            p3Var = p3Var2;
        }
        p3 p3VarD = this.f17667a.d();
        p3 p3VarB = this.f17667a.b();
        while (p3Var.b() >= i2 / 2) {
            if (!p3Var.c()) {
                p3 p3VarD2 = this.f17667a.d();
                int iB = this.f17667a.b(p3Var.b(p3Var.b()));
                while (p3Var2.b() >= p3Var.b() && !p3Var2.c()) {
                    int iB2 = p3Var2.b() - p3Var.b();
                    int iC = this.f17667a.c(p3Var2.b(p3Var2.b()), iB);
                    p3VarD2 = p3VarD2.a(this.f17667a.b(iB2, iC));
                    p3Var2 = p3Var2.a(p3Var.a(iB2, iC));
                }
                p3 p3VarA = p3VarD2.c(p3VarB).a(p3VarD);
                if (p3Var2.b() >= p3Var.b()) {
                    throw new IllegalStateException("Division algorithm failed to reduce polynomial?");
                }
                p3 p3Var3 = p3Var2;
                p3Var2 = p3Var;
                p3Var = p3Var3;
                p3VarD = p3VarB;
                p3VarB = p3VarA;
            } else {
                throw a.a("r_{i-1} was zero");
            }
        }
        int iB3 = p3VarB.b(0);
        if (iB3 != 0) {
            int iB4 = this.f17667a.b(iB3);
            return new p3[]{p3VarB.c(iB4), p3Var.c(iB4)};
        }
        throw a.a("sigmaTilde(0) was zero");
    }

    private int[] a(p3 p3Var) throws a {
        int iB = p3Var.b();
        if (iB == 1) {
            return new int[]{p3Var.b(1)};
        }
        int[] iArr = new int[iB];
        int i2 = 0;
        for (int i3 = 1; i3 < this.f17667a.c() && i2 < iB; i3++) {
            if (p3Var.a(i3) == 0) {
                iArr[i2] = this.f17667a.b(i3);
                i2++;
            }
        }
        if (i2 == iB) {
            return iArr;
        }
        throw a.a("Error locator degree does not match number of roots");
    }

    private int[] a(p3 p3Var, int[] iArr) {
        int length = iArr.length;
        int[] iArr2 = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            int iB = this.f17667a.b(iArr[i2]);
            int iC = 1;
            for (int i3 = 0; i3 < length; i3++) {
                if (i2 != i3) {
                    int iC2 = this.f17667a.c(iArr[i3], iB);
                    iC = this.f17667a.c(iC, (iC2 & 1) == 0 ? iC2 | 1 : iC2 & (-2));
                }
            }
            iArr2[i2] = this.f17667a.c(p3Var.a(iB), this.f17667a.b(iC));
            if (this.f17667a.a() != 0) {
                iArr2[i2] = this.f17667a.c(iArr2[i2], iB);
            }
        }
        return iArr2;
    }
}
