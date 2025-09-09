package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class u {

    /* renamed from: a, reason: collision with root package name */
    private final s f17821a;

    /* renamed from: b, reason: collision with root package name */
    private b8 f17822b;

    /* renamed from: c, reason: collision with root package name */
    private l3 f17823c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f17824d;

    u(s sVar) throws a {
        int iC = sVar.c();
        if (iC < 21 || (iC & 3) != 1) {
            throw a.a();
        }
        this.f17821a = sVar;
    }

    private int a(int i2, int i3, int i4) {
        return this.f17824d ? this.f17821a.b(i3, i2) : this.f17821a.b(i2, i3) ? (i4 << 1) | 1 : i4 << 1;
    }

    byte[] b() throws a {
        l3 l3VarC = c();
        b8 b8VarD = d();
        g1 g1Var = g1.values()[l3VarC.a()];
        int iC = this.f17821a.c();
        g1Var.a(this.f17821a, iC);
        s sVarA = b8VarD.a();
        byte[] bArr = new byte[b8VarD.e()];
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
                        if (this.f17821a.b(i10, i8)) {
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
        if (i4 == b8VarD.e()) {
            return bArr;
        }
        throw a.a();
    }

    l3 c() throws a {
        l3 l3Var = this.f17823c;
        if (l3Var != null) {
            return l3Var;
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
        int iC = this.f17821a.c();
        int i4 = iC - 7;
        for (int i5 = iC - 1; i5 >= i4; i5--) {
            iA = a(8, i5, iA);
        }
        for (int i6 = iC - 8; i6 < iC; i6++) {
            iA = a(i6, 8, iA);
        }
        l3 l3VarA = l3.a(iA3, iA);
        this.f17823c = l3VarA;
        if (l3VarA != null) {
            return l3VarA;
        }
        throw a.a();
    }

    b8 d() throws a {
        b8 b8Var = this.f17822b;
        if (b8Var != null) {
            return b8Var;
        }
        int iC = this.f17821a.c();
        int i2 = (iC - 17) / 4;
        if (i2 <= 6) {
            return b8.c(i2);
        }
        int i3 = iC - 11;
        int iA = 0;
        int iA2 = 0;
        for (int i4 = 5; i4 >= 0; i4--) {
            for (int i5 = iC - 9; i5 >= i3; i5--) {
                iA2 = a(i5, i4, iA2);
            }
        }
        b8 b8VarA = b8.a(iA2);
        if (b8VarA != null && b8VarA.d() == iC) {
            this.f17822b = b8VarA;
            return b8VarA;
        }
        for (int i6 = 5; i6 >= 0; i6--) {
            for (int i7 = iC - 9; i7 >= i3; i7--) {
                iA = a(i6, i7, iA);
            }
        }
        b8 b8VarA2 = b8.a(iA);
        if (b8VarA2 == null || b8VarA2.d() != iC) {
            throw a.a();
        }
        this.f17822b = b8VarA2;
        return b8VarA2;
    }

    void e() {
        if (this.f17823c == null) {
            return;
        }
        g1.values()[this.f17823c.a()].a(this.f17821a, this.f17821a.c());
    }

    void a(boolean z2) {
        this.f17822b = null;
        this.f17823c = null;
        this.f17824d = z2;
    }

    void a() {
        int i2 = 0;
        while (i2 < this.f17821a.e()) {
            int i3 = i2 + 1;
            for (int i4 = i3; i4 < this.f17821a.c(); i4++) {
                if (this.f17821a.b(i2, i4) != this.f17821a.b(i4, i2)) {
                    this.f17821a.a(i4, i2);
                    this.f17821a.a(i2, i4);
                }
            }
            i2 = i3;
        }
    }
}
