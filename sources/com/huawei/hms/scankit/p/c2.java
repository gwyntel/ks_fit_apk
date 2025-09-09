package com.huawei.hms.scankit.p;

import aisble.data.MutableData;
import kotlin.text.Typography;

/* loaded from: classes4.dex */
public final class c2 {

    /* renamed from: g, reason: collision with root package name */
    private static final int[] f17050g = {3808, 476, 2107, 1799};

    /* renamed from: a, reason: collision with root package name */
    private final s f17051a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f17052b;

    /* renamed from: c, reason: collision with root package name */
    private int f17053c;

    /* renamed from: d, reason: collision with root package name */
    private int f17054d;

    /* renamed from: e, reason: collision with root package name */
    private int f17055e;

    /* renamed from: f, reason: collision with root package name */
    private int f17056f;

    static final class a {

        /* renamed from: a, reason: collision with root package name */
        private final int f17057a;

        /* renamed from: b, reason: collision with root package name */
        private final int f17058b;

        a(int i2, int i3) {
            this.f17057a = i2;
            this.f17058b = i3;
        }

        u6 c() {
            return new u6(this.f17057a, this.f17058b);
        }

        public String toString() {
            return "<" + this.f17057a + ' ' + this.f17058b + Typography.greater;
        }

        int a() {
            return this.f17057a;
        }

        int b() {
            return this.f17058b;
        }
    }

    public c2(s sVar) {
        this.f17051a = sVar;
    }

    private a b() {
        u6 u6VarC;
        u6 u6Var;
        u6 u6Var2;
        u6 u6Var3;
        u6 u6VarC2;
        u6 u6VarC3;
        u6 u6VarC4;
        u6 u6VarC5;
        try {
            u6[] u6VarArrA = new j8(this.f17051a).a();
            u6Var2 = u6VarArrA[0];
            u6Var3 = u6VarArrA[1];
            u6Var = u6VarArrA[2];
            u6VarC = u6VarArrA[3];
        } catch (com.huawei.hms.scankit.p.a unused) {
            int iE = this.f17051a.e() / 2;
            int iC = this.f17051a.c() / 2;
            int i2 = iE + 7;
            int i3 = iC - 7;
            u6 u6VarC6 = a(new a(i2, i3), false, 1, -1).c();
            int i4 = iC + 7;
            u6 u6VarC7 = a(new a(i2, i4), false, 1, 1).c();
            int i5 = iE - 7;
            u6 u6VarC8 = a(new a(i5, i4), false, -1, 1).c();
            u6VarC = a(new a(i5, i3), false, -1, -1).c();
            u6Var = u6VarC8;
            u6Var2 = u6VarC6;
            u6Var3 = u6VarC7;
        }
        int iA = s4.a((((u6Var2.b() + u6VarC.b()) + u6Var3.b()) + u6Var.b()) / 4.0f);
        int iA2 = s4.a((((u6Var2.c() + u6VarC.c()) + u6Var3.c()) + u6Var.c()) / 4.0f);
        try {
            u6[] u6VarArrA2 = new j8(this.f17051a, 15, iA, iA2).a();
            u6VarC2 = u6VarArrA2[0];
            u6VarC3 = u6VarArrA2[1];
            u6VarC4 = u6VarArrA2[2];
            u6VarC5 = u6VarArrA2[3];
        } catch (com.huawei.hms.scankit.p.a unused2) {
            int i6 = iA + 7;
            int i7 = iA2 - 7;
            u6VarC2 = a(new a(i6, i7), false, 1, -1).c();
            int i8 = iA2 + 7;
            u6VarC3 = a(new a(i6, i8), false, 1, 1).c();
            int i9 = iA - 7;
            u6VarC4 = a(new a(i9, i8), false, -1, 1).c();
            u6VarC5 = a(new a(i9, i7), false, -1, -1).c();
        }
        return new a(s4.a((((u6VarC2.b() + u6VarC5.b()) + u6VarC3.b()) + u6VarC4.b()) / 4.0f), s4.a((((u6VarC2.c() + u6VarC5.c()) + u6VarC3.c()) + u6VarC4.c()) / 4.0f));
    }

    public g a(boolean z2) throws com.huawei.hms.scankit.p.a {
        u6[] u6VarArrA = a(b());
        if (z2) {
            u6 u6Var = u6VarArrA[0];
            u6VarArrA[0] = u6VarArrA[2];
            u6VarArrA[2] = u6Var;
        }
        a(u6VarArrA);
        s sVar = this.f17051a;
        int i2 = this.f17056f;
        return new g(a(sVar, u6VarArrA[i2 % 4], u6VarArrA[(i2 + 1) % 4], u6VarArrA[(i2 + 2) % 4], u6VarArrA[(i2 + 3) % 4]), b(u6VarArrA), this.f17052b, this.f17054d, this.f17053c);
    }

    private void a(u6[] u6VarArr) throws com.huawei.hms.scankit.p.a {
        long j2;
        long j3;
        if (a(u6VarArr[0]) && a(u6VarArr[1]) && a(u6VarArr[2]) && a(u6VarArr[3])) {
            int i2 = this.f17055e * 2;
            int[] iArr = {a(u6VarArr[0], u6VarArr[1], i2), a(u6VarArr[1], u6VarArr[2], i2), a(u6VarArr[2], u6VarArr[3], i2), a(u6VarArr[3], u6VarArr[0], i2)};
            this.f17056f = a(iArr, i2);
            long j4 = 0;
            for (int i3 = 0; i3 < 4; i3++) {
                int i4 = iArr[(this.f17056f + i3) % 4];
                if (this.f17052b) {
                    j2 = j4 << 7;
                    j3 = (i4 >> 1) & 127;
                } else {
                    j2 = j4 << 10;
                    j3 = ((i4 >> 2) & 992) + ((i4 >> 1) & 31);
                }
                j4 = j2 + j3;
            }
            int iA = a(j4, this.f17052b);
            if (this.f17052b) {
                this.f17053c = (iA >> 6) + 1;
                this.f17054d = (iA & 63) + 1;
                return;
            } else {
                this.f17053c = (iA >> 11) + 1;
                this.f17054d = (iA & MutableData.SFLOAT_NAN) + 1;
                return;
            }
        }
        throw com.huawei.hms.scankit.p.a.a();
    }

    private u6[] b(u6[] u6VarArr) {
        return a(u6VarArr, this.f17055e * 2, a());
    }

    private boolean b(a aVar, a aVar2, a aVar3, a aVar4) {
        a aVar5 = new a(aVar.a() - 3, aVar.b() + 3);
        a aVar6 = new a(aVar2.a() - 3, aVar2.b() - 3);
        a aVar7 = new a(aVar3.a() + 3, aVar3.b() - 3);
        a aVar8 = new a(aVar4.a() + 3, aVar4.b() + 3);
        int iB = b(aVar8, aVar5);
        return iB != 0 && b(aVar5, aVar6) == iB && b(aVar6, aVar7) == iB && b(aVar7, aVar8) == iB;
    }

    private static int a(int[] iArr, int i2) throws com.huawei.hms.scankit.p.a {
        int i3 = 0;
        for (int i4 : iArr) {
            i3 = (i3 << 3) + ((i4 >> (i2 - 2)) << 1) + (i4 & 1);
        }
        int i5 = ((i3 & 1) << 11) + (i3 >> 1);
        for (int i6 = 0; i6 < 4; i6++) {
            if (Integer.bitCount(f17050g[i6] ^ i5) <= 2) {
                return i6;
            }
        }
        throw com.huawei.hms.scankit.p.a.a();
    }

    private static int a(long j2, boolean z2) throws com.huawei.hms.scankit.p.a {
        int i2;
        int i3;
        if (z2) {
            i2 = 7;
            i3 = 2;
        } else {
            i2 = 10;
            i3 = 4;
        }
        int i4 = i2 - i3;
        int[] iArr = new int[i2];
        for (int i5 = i2 - 1; i5 >= 0; i5--) {
            iArr[i5] = ((int) j2) & 15;
            j2 >>= 4;
        }
        try {
            new p6(o3.f17632k).a(iArr, i4);
            int i6 = 0;
            for (int i7 = 0; i7 < i3; i7++) {
                i6 = (i6 << 4) + iArr[i7];
            }
            return i6;
        } catch (com.huawei.hms.scankit.p.a unused) {
            throw com.huawei.hms.scankit.p.a.a();
        }
    }

    private int b(a aVar, a aVar2) {
        float fA = a(aVar, aVar2);
        float fA2 = (aVar2.a() - aVar.a()) / fA;
        float fB = (aVar2.b() - aVar.b()) / fA;
        float fA3 = aVar.a();
        float fB2 = aVar.b();
        boolean zB = this.f17051a.b(aVar.a(), aVar.b());
        int iCeil = (int) Math.ceil(fA);
        int i2 = 0;
        for (int i3 = 0; i3 < iCeil; i3++) {
            fA3 += fA2;
            fB2 += fB;
            if (this.f17051a.b(s4.a(fA3), s4.a(fB2)) != zB) {
                i2++;
            }
        }
        float f2 = i2 / fA;
        if (f2 <= 0.1f || f2 >= 0.9f) {
            return (f2 <= 0.1f) == zB ? 1 : -1;
        }
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private u6[] a(a aVar) throws com.huawei.hms.scankit.p.a {
        int i2;
        int i3;
        int i4 = 1;
        this.f17055e = 1;
        a aVar2 = aVar;
        a aVar3 = aVar2;
        a aVar4 = aVar3;
        a aVar5 = aVar4;
        boolean z2 = 1;
        while (this.f17055e < 9) {
            a aVarA = a(aVar5, z2, i4, -1);
            a aVarA2 = a(aVar4, z2, i4, i4);
            a aVarA3 = a(aVar3, z2, -1, i4);
            a aVarA4 = a(aVar2, z2, -1, -1);
            if (this.f17055e > 2) {
                double dA = (a(aVarA4, aVarA) * this.f17055e) / (a(aVar2, aVar5) * (this.f17055e + 2));
                if (dA < 0.75d || dA > 1.25d || !a(aVarA, aVarA2, aVarA3, aVarA4) || (!b(aVarA, aVarA2, aVarA3, aVarA4) && ((i3 = this.f17055e) == 5 || i3 == 7))) {
                    break;
                }
                i2 = 1;
            } else {
                i2 = i4;
            }
            this.f17055e += i2;
            aVar2 = aVarA4;
            aVar5 = aVarA;
            aVar4 = aVarA2;
            aVar3 = aVarA3;
            i4 = 1;
            z2 ^= i2;
        }
        int i5 = this.f17055e;
        if (i5 != 5 && i5 != 7) {
            throw com.huawei.hms.scankit.p.a.a();
        }
        this.f17052b = i5 == 5;
        u6[] u6VarArr = {new u6(aVar5.a() + 0.5f, aVar5.b() - 0.5f), new u6(aVar4.a() + 0.5f, aVar4.b() + 0.5f), new u6(aVar3.a() - 0.5f, aVar3.b() + 0.5f), new u6(aVar2.a() - 0.5f, aVar2.b() - 0.5f)};
        int i6 = this.f17055e * 2;
        return a(u6VarArr, i6 - 3, i6);
    }

    private s a(s sVar, u6 u6Var, u6 u6Var2, u6 u6Var3, u6 u6Var4) throws com.huawei.hms.scankit.p.a {
        s3 s3VarA = s3.a();
        int iA = a();
        float f2 = iA / 2.0f;
        float f3 = this.f17055e;
        float f4 = f2 - f3;
        float f5 = f2 + f3;
        return s3VarA.a(sVar, iA, iA, f4, f4, f5, f4, f5, f5, f4, f5, u6Var.b(), u6Var.c(), u6Var2.b(), u6Var2.c(), u6Var3.b(), u6Var3.c(), u6Var4.b(), u6Var4.c());
    }

    private int a(u6 u6Var, u6 u6Var2, int i2) {
        float fA = a(u6Var, u6Var2);
        float f2 = fA / i2;
        float fB = u6Var.b();
        float fC = u6Var.c();
        float fB2 = ((u6Var2.b() - u6Var.b()) * f2) / fA;
        float fC2 = (f2 * (u6Var2.c() - u6Var.c())) / fA;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            float f3 = i4;
            if (this.f17051a.b(s4.a((f3 * fB2) + fB), s4.a((f3 * fC2) + fC))) {
                i3 |= 1 << ((i2 - i4) - 1);
            }
        }
        return i3;
    }

    private boolean a(a aVar, a aVar2, a aVar3, a aVar4) {
        a aVar5 = new a((int) Math.ceil((((aVar.f17057a + aVar2.f17057a) + aVar3.f17057a) + aVar4.f17057a) / 4.0f), (int) Math.ceil((((aVar.f17058b + aVar2.f17058b) + aVar3.f17058b) + aVar4.f17058b) / 4.0f));
        float fA = a(aVar5, aVar);
        float fA2 = a(aVar5, aVar2);
        float fA3 = a(aVar5, aVar3);
        float fA4 = a(aVar5, aVar4);
        double d2 = fA / fA2;
        if (d2 <= 0.75d || d2 >= 1.25d) {
            return false;
        }
        double d3 = fA / fA3;
        if (d3 <= 0.75d || d3 >= 1.25d) {
            return false;
        }
        double d4 = fA / fA4;
        return d4 > 0.75d && d4 < 1.25d;
    }

    private a a(a aVar, boolean z2, int i2, int i3) {
        int iA = aVar.a() + i2;
        int iB = aVar.b();
        while (true) {
            iB += i3;
            if (!a(iA, iB) || this.f17051a.b(iA, iB) != z2) {
                break;
            }
            iA += i2;
        }
        int i4 = iA - i2;
        int i5 = iB - i3;
        while (a(i4, i5) && this.f17051a.b(i4, i5) == z2) {
            i4 += i2;
        }
        int i6 = i4 - i2;
        while (a(i6, i5) && this.f17051a.b(i6, i5) == z2) {
            i5 += i3;
        }
        return new a(i6, i5 - i3);
    }

    private static u6[] a(u6[] u6VarArr, int i2, int i3) {
        float f2 = i3 / (i2 * 2.0f);
        float fB = u6VarArr[0].b() - u6VarArr[2].b();
        float fC = u6VarArr[0].c() - u6VarArr[2].c();
        float fB2 = (u6VarArr[0].b() + u6VarArr[2].b()) / 2.0f;
        float fC2 = (u6VarArr[0].c() + u6VarArr[2].c()) / 2.0f;
        float f3 = fB * f2;
        float f4 = fC * f2;
        u6 u6Var = new u6(fB2 + f3, fC2 + f4);
        u6 u6Var2 = new u6(fB2 - f3, fC2 - f4);
        float fB3 = u6VarArr[1].b() - u6VarArr[3].b();
        float fC3 = u6VarArr[1].c() - u6VarArr[3].c();
        float fB4 = (u6VarArr[1].b() + u6VarArr[3].b()) / 2.0f;
        float fC4 = (u6VarArr[1].c() + u6VarArr[3].c()) / 2.0f;
        float f5 = fB3 * f2;
        float f6 = f2 * fC3;
        return new u6[]{u6Var, new u6(fB4 + f5, fC4 + f6), u6Var2, new u6(fB4 - f5, fC4 - f6)};
    }

    private boolean a(int i2, int i3) {
        return i2 >= 0 && i2 < this.f17051a.e() && i3 > 0 && i3 < this.f17051a.c();
    }

    private boolean a(u6 u6Var) {
        return a(s4.a(u6Var.b()), s4.a(u6Var.c()));
    }

    private static float a(a aVar, a aVar2) {
        return s4.a(aVar.a(), aVar.b(), aVar2.a(), aVar2.b());
    }

    private static float a(u6 u6Var, u6 u6Var2) {
        return s4.a(u6Var.b(), u6Var.c(), u6Var2.b(), u6Var2.c());
    }

    private int a() {
        if (this.f17052b) {
            return (this.f17053c * 4) + 11;
        }
        int i2 = this.f17053c;
        return i2 <= 4 ? (i2 * 4) + 15 : (i2 * 4) + ((((i2 - 4) / 8) + 1) * 2) + 15;
    }
}
