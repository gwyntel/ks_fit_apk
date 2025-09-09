package com.huawei.hms.scankit.p;

import java.util.Formatter;

/* loaded from: classes4.dex */
class a2 {

    /* renamed from: a, reason: collision with root package name */
    private final a0 f16957a;

    /* renamed from: b, reason: collision with root package name */
    private final x0[] f16958b;

    a2(a0 a0Var) {
        this.f16957a = new a0(a0Var);
        this.f16958b = new x0[(a0Var.d() - a0Var.f()) + 1];
    }

    final void a(int i2, x0 x0Var) {
        this.f16958b[c(i2)] = x0Var;
    }

    final x0 b(int i2) {
        x0 x0Var;
        x0 x0Var2;
        x0 x0VarA = a(i2);
        if (x0VarA != null) {
            return x0VarA;
        }
        for (int i3 = 1; i3 < 5; i3++) {
            int iC = c(i2) - i3;
            if (iC >= 0 && (x0Var2 = this.f16958b[iC]) != null) {
                return x0Var2;
            }
            int iC2 = c(i2) + i3;
            x0[] x0VarArr = this.f16958b;
            if (iC2 < x0VarArr.length && (x0Var = x0VarArr[iC2]) != null) {
                return x0Var;
            }
        }
        return null;
    }

    final int c(int i2) {
        return i2 - this.f16957a.f();
    }

    public String toString() {
        Formatter formatter = new Formatter();
        try {
            int i2 = 0;
            for (x0 x0Var : this.f16958b) {
                if (x0Var == null) {
                    formatter.format("%3d:    |   %n", Integer.valueOf(i2));
                    i2++;
                } else {
                    formatter.format("%3d: %3d|%3d%n", Integer.valueOf(i2), Integer.valueOf(x0Var.c()), Integer.valueOf(x0Var.e()));
                    i2++;
                }
            }
            String string = formatter.toString();
            formatter.close();
            return string;
        } catch (Throwable th) {
            try {
                formatter.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    final x0 a(int i2) {
        return this.f16958b[c(i2)];
    }

    final a0 a() {
        return this.f16957a;
    }

    final x0[] b() {
        return this.f16958b;
    }
}
