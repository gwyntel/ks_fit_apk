package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class a3 {

    /* renamed from: a, reason: collision with root package name */
    private final w4 f16959a = w4.f17951f;

    public int a(int[] iArr, int i2, int[] iArr2) throws a {
        x4 x4Var = new x4(this.f16959a, iArr);
        int[] iArr3 = new int[i2];
        boolean z2 = false;
        for (int i3 = i2; i3 > 0; i3--) {
            int iA = x4Var.a(this.f16959a.a(i3));
            iArr3[i2 - i3] = iA;
            if (iA != 0) {
                z2 = true;
            }
        }
        if (!z2) {
            return 0;
        }
        x4 x4VarA = this.f16959a.a();
        if (iArr2 != null) {
            for (int i4 : iArr2) {
                int iA2 = this.f16959a.a((iArr.length - 1) - i4);
                w4 w4Var = this.f16959a;
                x4VarA = x4VarA.b(new x4(w4Var, new int[]{w4Var.d(0, iA2), 1}));
            }
        }
        x4[] x4VarArrA = a(this.f16959a.b(i2, 1), new x4(this.f16959a, iArr3), i2);
        x4 x4Var2 = x4VarArrA[0];
        x4 x4Var3 = x4VarArrA[1];
        int[] iArrA = a(x4Var2);
        int[] iArrA2 = a(x4Var3, x4Var2, iArrA);
        for (int i5 = 0; i5 < iArrA.length; i5++) {
            int length = (iArr.length - 1) - this.f16959a.c(iArrA[i5]);
            if (length < 0) {
                throw a.a();
            }
            iArr[length] = this.f16959a.d(iArr[length], iArrA2[i5]);
        }
        return iArrA.length;
    }

    private x4[] a(x4 x4Var, x4 x4Var2, int i2) throws a {
        if (x4Var.a() >= x4Var2.a()) {
            x4Var2 = x4Var;
            x4Var = x4Var2;
        }
        x4 x4VarC = this.f16959a.c();
        x4 x4VarA = this.f16959a.a();
        while (x4Var.a() >= i2 / 2) {
            if (!x4Var.b()) {
                x4 x4VarC2 = this.f16959a.c();
                int iB = this.f16959a.b(x4Var.b(x4Var.a()));
                while (x4Var2.a() >= x4Var.a() && !x4Var2.b()) {
                    int iA = x4Var2.a() - x4Var.a();
                    int iC = this.f16959a.c(x4Var2.b(x4Var2.a()), iB);
                    x4VarC2 = x4VarC2.a(this.f16959a.b(iA, iC));
                    x4Var2 = x4Var2.c(x4Var.a(iA, iC));
                }
                x4 x4VarC3 = x4VarC2.b(x4VarA).c(x4VarC).c();
                x4 x4Var3 = x4Var2;
                x4Var2 = x4Var;
                x4Var = x4Var3;
                x4VarC = x4VarA;
                x4VarA = x4VarC3;
            } else {
                throw a.a();
            }
        }
        int iB2 = x4VarA.b(0);
        if (iB2 != 0) {
            int iB3 = this.f16959a.b(iB2);
            return new x4[]{x4VarA.c(iB3), x4Var.c(iB3)};
        }
        throw a.a();
    }

    private int[] a(x4 x4Var) throws a {
        int iA = x4Var.a();
        int[] iArr = new int[iA];
        int i2 = 0;
        for (int i3 = 1; i3 < this.f16959a.b() && i2 < iA; i3++) {
            if (x4Var.a(i3) == 0) {
                iArr[i2] = this.f16959a.b(i3);
                i2++;
            }
        }
        if (i2 == iA) {
            return iArr;
        }
        throw a.a();
    }

    private int[] a(x4 x4Var, x4 x4Var2, int[] iArr) {
        int iA = x4Var2.a();
        int[] iArr2 = new int[iA];
        for (int i2 = 1; i2 <= iA; i2++) {
            iArr2[iA - i2] = this.f16959a.c(i2, x4Var2.b(i2));
        }
        x4 x4Var3 = new x4(this.f16959a, iArr2);
        int length = iArr.length;
        int[] iArr3 = new int[length];
        for (int i3 = 0; i3 < length; i3++) {
            int iB = this.f16959a.b(iArr[i3]);
            iArr3[i3] = this.f16959a.c(this.f16959a.d(0, x4Var.a(iB)), this.f16959a.b(x4Var3.a(iB)));
        }
        return iArr3;
    }
}
