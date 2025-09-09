package com.huawei.hms.scankit.p;

import com.google.zxing.pdf417.PDF417Common;
import java.util.Formatter;

/* loaded from: classes4.dex */
final class z1 {

    /* renamed from: a, reason: collision with root package name */
    private final k f18046a;

    /* renamed from: b, reason: collision with root package name */
    private final a2[] f18047b;

    /* renamed from: c, reason: collision with root package name */
    private a0 f18048c;

    /* renamed from: d, reason: collision with root package name */
    private final int f18049d;

    z1(k kVar, a0 a0Var) {
        this.f18046a = kVar;
        int iA = kVar.a();
        this.f18049d = iA;
        this.f18048c = a0Var;
        this.f18047b = new a2[iA + 2];
    }

    private void a(a2 a2Var) throws a {
        if (a2Var != null) {
            try {
                ((b2) a2Var).a(this.f18046a);
            } catch (ClassCastException unused) {
                throw a.a();
            }
        }
    }

    private int b() {
        c();
        return d() + e();
    }

    private void c() {
        a2[] a2VarArr = this.f18047b;
        a2 a2Var = a2VarArr[0];
        if (a2Var == null || a2VarArr[this.f18049d + 1] == null) {
            return;
        }
        x0[] x0VarArrB = a2Var.b();
        x0[] x0VarArrB2 = this.f18047b[this.f18049d + 1].b();
        for (int i2 = 0; i2 < x0VarArrB.length; i2++) {
            x0 x0Var = x0VarArrB[i2];
            if (x0Var != null && x0VarArrB2[i2] != null && x0Var.c() == x0VarArrB2[i2].c()) {
                for (int i3 = 1; i3 <= this.f18049d; i3++) {
                    x0 x0Var2 = this.f18047b[i3].b()[i2];
                    if (x0Var2 != null) {
                        x0Var2.b(x0VarArrB[i2].c());
                        if (!x0Var2.g()) {
                            this.f18047b[i3].b()[i2] = null;
                        }
                    }
                }
            }
        }
    }

    private int d() {
        a2 a2Var = this.f18047b[0];
        if (a2Var == null) {
            return 0;
        }
        x0[] x0VarArrB = a2Var.b();
        int i2 = 0;
        for (int i3 = 0; i3 < x0VarArrB.length; i3++) {
            x0 x0Var = x0VarArrB[i3];
            if (x0Var != null) {
                int iC = x0Var.c();
                int iA = 0;
                for (int i4 = 1; i4 < this.f18049d + 1 && iA < 2; i4++) {
                    x0 x0Var2 = this.f18047b[i4].b()[i3];
                    if (x0Var2 != null) {
                        iA = a(iC, iA, x0Var2);
                        if (!x0Var2.g()) {
                            i2++;
                        }
                    }
                }
            }
        }
        return i2;
    }

    private int e() {
        a2 a2Var = this.f18047b[this.f18049d + 1];
        if (a2Var == null) {
            return 0;
        }
        x0[] x0VarArrB = a2Var.b();
        int i2 = 0;
        for (int i3 = 0; i3 < x0VarArrB.length; i3++) {
            x0 x0Var = x0VarArrB[i3];
            if (x0Var != null) {
                int iC = x0Var.c();
                int iA = 0;
                for (int i4 = this.f18049d + 1; i4 > 0 && iA < 2; i4--) {
                    x0 x0Var2 = this.f18047b[i4].b()[i3];
                    if (x0Var2 != null) {
                        iA = a(iC, iA, x0Var2);
                        if (!x0Var2.g()) {
                            i2++;
                        }
                    }
                }
            }
        }
        return i2;
    }

    int f() {
        return this.f18049d;
    }

    int g() {
        return this.f18046a.b();
    }

    int h() {
        return this.f18046a.c();
    }

    a0 i() {
        return this.f18048c;
    }

    a2[] j() throws a {
        a(this.f18047b[0]);
        a(this.f18047b[this.f18049d + 1]);
        int i2 = PDF417Common.MAX_CODEWORDS_IN_BARCODE;
        while (true) {
            int iA = a();
            if (iA <= 0 || iA >= i2) {
                break;
            }
            i2 = iA;
        }
        return this.f18047b;
    }

    public String toString() {
        a2[] a2VarArr = this.f18047b;
        a2 a2Var = a2VarArr[0];
        if (a2Var == null) {
            a2Var = a2VarArr[this.f18049d + 1];
        }
        Formatter formatter = new Formatter();
        for (int i2 = 0; i2 < a2Var.b().length; i2++) {
            try {
                formatter.format("CW %3d:", Integer.valueOf(i2));
                for (int i3 = 0; i3 < this.f18049d + 2; i3++) {
                    a2 a2Var2 = this.f18047b[i3];
                    if (a2Var2 == null) {
                        formatter.format("    |   ", new Object[0]);
                    } else {
                        x0 x0Var = a2Var2.b()[i2];
                        if (x0Var == null) {
                            formatter.format("    |   ", new Object[0]);
                        } else {
                            formatter.format(" %3d|%3d", Integer.valueOf(x0Var.c()), Integer.valueOf(x0Var.e()));
                        }
                    }
                }
                formatter.format("%n", new Object[0]);
            } catch (Throwable th) {
                try {
                    formatter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        String string = formatter.toString();
        formatter.close();
        return string;
    }

    private int a() {
        int iB = b();
        if (iB == 0) {
            return 0;
        }
        for (int i2 = 1; i2 < this.f18049d + 1; i2++) {
            x0[] x0VarArrB = this.f18047b[i2].b();
            for (int i3 = 0; i3 < x0VarArrB.length; i3++) {
                x0 x0Var = x0VarArrB[i3];
                if (x0Var != null && !x0Var.g()) {
                    a(i2, i3, x0VarArrB);
                }
            }
        }
        return iB;
    }

    private static int a(int i2, int i3, x0 x0Var) {
        if (x0Var == null || x0Var.g()) {
            return i3;
        }
        if (!x0Var.a(i2)) {
            return i3 + 1;
        }
        x0Var.b(i2);
        return 0;
    }

    private void a(int i2, int i3, x0[] x0VarArr) {
        x0 x0Var = x0VarArr[i3];
        x0[] x0VarArrB = this.f18047b[i2 - 1].b();
        a2 a2Var = this.f18047b[i2 + 1];
        x0[] x0VarArrB2 = a2Var != null ? a2Var.b() : x0VarArrB;
        x0[] x0VarArr2 = new x0[14];
        x0VarArr2[2] = x0VarArrB[i3];
        x0VarArr2[3] = x0VarArrB2[i3];
        if (i3 > 0) {
            int i4 = i3 - 1;
            x0VarArr2[0] = x0VarArr[i4];
            x0VarArr2[4] = x0VarArrB[i4];
            x0VarArr2[5] = x0VarArrB2[i4];
        }
        if (i3 > 1) {
            int i5 = i3 - 2;
            x0VarArr2[8] = x0VarArr[i5];
            x0VarArr2[10] = x0VarArrB[i5];
            x0VarArr2[11] = x0VarArrB2[i5];
        }
        if (i3 < x0VarArr.length - 1) {
            int i6 = i3 + 1;
            x0VarArr2[1] = x0VarArr[i6];
            x0VarArr2[6] = x0VarArrB[i6];
            x0VarArr2[7] = x0VarArrB2[i6];
        }
        if (i3 < x0VarArr.length - 2) {
            int i7 = i3 + 2;
            x0VarArr2[9] = x0VarArr[i7];
            x0VarArr2[12] = x0VarArrB[i7];
            x0VarArr2[13] = x0VarArrB2[i7];
        }
        for (int i8 = 0; i8 < 14 && !a(x0Var, x0VarArr2[i8]); i8++) {
        }
    }

    private static boolean a(x0 x0Var, x0 x0Var2) {
        if (x0Var2 == null || !x0Var2.g() || x0Var2.a() != x0Var.a()) {
            return false;
        }
        x0Var.b(x0Var2.c());
        return true;
    }

    void a(a0 a0Var) {
        this.f18048c = a0Var;
    }

    void a(int i2, a2 a2Var) {
        this.f18047b[i2] = a2Var;
    }

    a2 a(int i2) {
        return this.f18047b[i2];
    }
}
