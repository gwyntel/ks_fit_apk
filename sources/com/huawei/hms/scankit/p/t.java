package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class t {

    /* renamed from: a, reason: collision with root package name */
    private final s f17798a;

    /* renamed from: b, reason: collision with root package name */
    private a8 f17799b;

    /* renamed from: c, reason: collision with root package name */
    private m3 f17800c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f17801d;

    t(s sVar) throws a {
        int iC = sVar.c();
        if (iC < 21 || (iC & 3) != 1) {
            throw a.a();
        }
        this.f17798a = sVar;
    }

    private int a(int i2, int i3, int i4) {
        return this.f17801d ? this.f17798a.b(i3, i2) : this.f17798a.b(i2, i3) ? (i4 << 1) | 1 : i4 << 1;
    }

    byte[] b() throws a {
        m3 m3VarC = c();
        a8 a8VarD = d();
        f1 f1Var = f1.values()[m3VarC.b()];
        int iC = this.f17798a.c();
        f1Var.a(this.f17798a, iC);
        s sVarA = a8VarD.a();
        byte[] bArr = new byte[a8VarD.l()];
        int i2 = iC - 1;
        boolean z2 = true;
        int i3 = i2;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i3 > 0) {
            if (i3 == 6) {
                i3--;
            }
            for (int i7 = 0; i7 < iC; i7++) {
                int i8 = z2 ? i2 - i7 : i7;
                for (int i9 = 0; i9 < 2; i9++) {
                    int i10 = i3 - i9;
                    if (!sVarA.b(i10, i8)) {
                        i6++;
                        i5 <<= 1;
                        if (this.f17798a.b(i10, i8)) {
                            i5 |= 1;
                        }
                        if (i6 == 8) {
                            bArr[i4] = (byte) i5;
                            i4++;
                            i5 = 0;
                            i6 = 0;
                        }
                    }
                }
            }
            z2 = !z2;
            i3 -= 2;
        }
        if (i4 == a8VarD.l()) {
            return bArr;
        }
        throw a.a();
    }

    m3 c() throws a {
        m3 m3Var = this.f17800c;
        if (m3Var != null) {
            return m3Var;
        }
        int iA = 0;
        int iA2 = 0;
        for (int i2 = 0; i2 < 6; i2++) {
            iA2 = a(i2, 8, iA2);
        }
        int iA3 = a(8, 7, a(8, 8, a(7, 8, iA2)));
        for (int i3 = 5; i3 >= 0; i3--) {
            iA3 = a(8, i3, iA3);
        }
        int iC = this.f17798a.c();
        int i4 = iC - 7;
        for (int i5 = iC - 1; i5 >= i4; i5--) {
            iA = a(8, i5, iA);
        }
        for (int i6 = iC - 8; i6 < iC; i6++) {
            iA = a(i6, 8, iA);
        }
        m3 m3VarA = m3.a(iA3, iA);
        this.f17800c = m3VarA;
        if (m3VarA != null) {
            return m3VarA;
        }
        throw a.a();
    }

    a8 d() throws a {
        a8 a8Var = this.f17799b;
        if (a8Var != null) {
            return a8Var;
        }
        int iC = this.f17798a.c();
        int i2 = (iC - 17) / 4;
        if (i2 <= 6) {
            return a8.b(i2);
        }
        int i3 = iC - 11;
        int iA = 0;
        int iA2 = 0;
        for (int i4 = 5; i4 >= 0; i4--) {
            for (int i5 = iC - 9; i5 >= i3; i5--) {
                iA2 = a(i5, i4, iA2);
            }
        }
        a8 a8VarA = a8.a(iA2);
        if (a8VarA != null && a8VarA.k() == iC) {
            this.f17799b = a8VarA;
            return a8VarA;
        }
        for (int i6 = 5; i6 >= 0; i6--) {
            for (int i7 = iC - 9; i7 >= i3; i7--) {
                iA = a(i6, i7, iA);
            }
        }
        a8 a8VarA2 = a8.a(iA);
        if (a8VarA2 == null || a8VarA2.k() != iC) {
            throw a.a();
        }
        this.f17799b = a8VarA2;
        return a8VarA2;
    }

    void e() {
        if (this.f17800c == null) {
            return;
        }
        f1.values()[this.f17800c.b()].a(this.f17798a, this.f17798a.c());
    }

    void a(boolean z2) {
        this.f17799b = null;
        this.f17800c = null;
        this.f17801d = z2;
    }

    void a() throws a {
        if (this.f17798a != null) {
            int i2 = 0;
            while (i2 < this.f17798a.e()) {
                int i3 = i2 + 1;
                for (int i4 = i3; i4 < this.f17798a.c(); i4++) {
                    if (this.f17798a.b(i2, i4) != this.f17798a.b(i4, i2)) {
                        this.f17798a.a(i4, i2);
                        this.f17798a.a(i2, i4);
                    }
                }
                i2 = i3;
            }
            return;
        }
        throw a.a();
    }
}
