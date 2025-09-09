package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class b2 extends a2 {

    /* renamed from: c, reason: collision with root package name */
    private final boolean f16997c;

    b2(a0 a0Var, boolean z2) {
        super(a0Var);
        this.f16997c = z2;
    }

    private void b(k kVar) {
        a0 a0VarA = a();
        u6 u6VarG = this.f16997c ? a0VarA.g() : a0VarA.h();
        u6 u6VarA = this.f16997c ? a0VarA.a() : a0VarA.b();
        int iC = c((int) u6VarA.c());
        x0[] x0VarArrB = b();
        int iC2 = -1;
        int i2 = 0;
        int iMax = 1;
        for (int iC3 = c((int) u6VarG.c()); iC3 < iC; iC3++) {
            x0 x0Var = x0VarArrB[iC3];
            if (x0Var != null) {
                x0Var.h();
                int iC4 = x0Var.c() - iC2;
                if (iC4 == 0) {
                    i2++;
                } else {
                    if (iC4 == 1) {
                        iMax = Math.max(iMax, i2);
                        iC2 = x0Var.c();
                    } else if (x0Var.c() >= kVar.c()) {
                        x0VarArrB[iC3] = null;
                    } else {
                        iC2 = x0Var.c();
                    }
                    i2 = 1;
                }
            }
        }
    }

    private void f() {
        for (x0 x0Var : b()) {
            if (x0Var != null) {
                x0Var.h();
            }
        }
    }

    void a(k kVar) throws a {
        x0[] x0VarArrB = b();
        f();
        a(x0VarArrB, kVar);
        a0 a0VarA = a();
        u6 u6VarG = this.f16997c ? a0VarA.g() : a0VarA.h();
        u6 u6VarA = this.f16997c ? a0VarA.a() : a0VarA.b();
        int iC = c((int) u6VarG.c());
        int iC2 = c((int) u6VarA.c());
        int iC3 = -1;
        int i2 = 0;
        int iMax = 1;
        while (iC < iC2) {
            x0 x0Var = x0VarArrB[iC];
            if (x0Var != null) {
                int iC4 = x0Var.c() - iC3;
                if (iC4 == 0) {
                    i2++;
                } else {
                    if (iC4 == 1) {
                        iMax = Math.max(iMax, i2);
                        iC3 = x0Var.c();
                    } else if (iC4 < 0 || x0Var.c() >= kVar.c() || iC4 > iC) {
                        x0VarArrB[iC] = null;
                    } else {
                        if (iMax > 2) {
                            iC4 *= iMax - 2;
                        }
                        boolean z2 = iC4 >= iC;
                        for (int i3 = 1; i3 <= iC4 && !z2; i3++) {
                            z2 = x0VarArrB[iC - i3] != null;
                        }
                        if (z2) {
                            x0VarArrB[iC] = null;
                        } else {
                            iC3 = x0Var.c();
                        }
                    }
                    i2 = 1;
                }
            }
            iC++;
        }
    }

    k c() throws a {
        x0[] x0VarArrB = b();
        m mVar = new m();
        m mVar2 = new m();
        m mVar3 = new m();
        m mVar4 = new m();
        for (x0 x0Var : x0VarArrB) {
            if (x0Var != null) {
                x0Var.h();
                int iE = x0Var.e() % 30;
                int iC = x0Var.c();
                if (!this.f16997c) {
                    iC += 2;
                }
                int i2 = iC % 3;
                if (i2 == 0) {
                    mVar2.a((iE * 3) + 1);
                } else if (i2 == 1) {
                    mVar4.a(iE / 3);
                    mVar3.a(iE % 3);
                } else {
                    if (i2 != 2) {
                        throw a.a();
                    }
                    mVar.a(iE + 1);
                }
            }
        }
        if (mVar.a().length == 0 || mVar2.a().length == 0 || mVar3.a().length == 0 || mVar4.a().length == 0 || mVar.a()[0] < 1 || mVar2.a()[0] + mVar3.a()[0] < 3 || mVar2.a()[0] + mVar3.a()[0] > 90) {
            return null;
        }
        k kVar = new k(mVar.a()[0], mVar2.a()[0], mVar3.a()[0], mVar4.a()[0]);
        a(x0VarArrB, kVar);
        return kVar;
    }

    int[] d() throws a {
        int iC;
        k kVarC = c();
        if (kVarC == null) {
            return null;
        }
        b(kVarC);
        int iC2 = kVarC.c();
        int[] iArr = new int[iC2];
        for (x0 x0Var : b()) {
            if (x0Var != null && (iC = x0Var.c()) < iC2) {
                iArr[iC] = iArr[iC] + 1;
            }
        }
        return iArr;
    }

    boolean e() {
        return this.f16997c;
    }

    @Override // com.huawei.hms.scankit.p.a2
    public String toString() {
        return "IsLeft: " + this.f16997c + '\n' + super.toString();
    }

    private void a(x0[] x0VarArr, k kVar) throws a {
        for (int i2 = 0; i2 < x0VarArr.length; i2++) {
            x0 x0Var = x0VarArr[i2];
            if (x0Var != null) {
                int iE = x0Var.e() % 30;
                int iC = x0Var.c();
                if (iC > kVar.c()) {
                    x0VarArr[i2] = null;
                } else {
                    if (!this.f16997c) {
                        iC += 2;
                    }
                    int i3 = iC % 3;
                    if (i3 != 0) {
                        if (i3 != 1) {
                            if (i3 == 2) {
                                if (iE + 1 != kVar.a()) {
                                    x0VarArr[i2] = null;
                                }
                            } else {
                                throw a.a();
                            }
                        } else if (iE / 3 != kVar.b() || iE % 3 != kVar.d()) {
                            x0VarArr[i2] = null;
                        }
                    } else if ((iE * 3) + 1 != kVar.e()) {
                        x0VarArr[i2] = null;
                    }
                }
            }
        }
    }
}
