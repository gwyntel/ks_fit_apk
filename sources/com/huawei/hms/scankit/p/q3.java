package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public class q3 extends o {

    /* renamed from: d, reason: collision with root package name */
    private static final byte[] f17689d = new byte[0];

    /* renamed from: b, reason: collision with root package name */
    private byte[] f17690b;

    /* renamed from: c, reason: collision with root package name */
    private final int[] f17691c;

    public q3(p4 p4Var) {
        super(p4Var);
        this.f17690b = f17689d;
        this.f17691c = new int[32];
    }

    @Override // com.huawei.hms.scankit.p.o
    public r a(int i2, r rVar) throws a {
        p4 p4VarC = c();
        int iC = p4VarC.c();
        if (rVar == null || rVar.e() < iC) {
            rVar = new r(iC);
        } else {
            rVar.a();
        }
        a(iC);
        byte[] bArrA = p4VarC.a(i2, this.f17690b);
        int[] iArr = this.f17691c;
        for (int i3 = 0; i3 < iC; i3++) {
            int i4 = (bArrA[i3] & 255) >> 3;
            iArr[i4] = iArr[i4] + 1;
        }
        int iA = a(iArr, false);
        if (iC < 3) {
            for (int i5 = 0; i5 < iC; i5++) {
                if ((bArrA[i5] & 255) < iA) {
                    rVar.g(i5);
                }
            }
        } else {
            int i6 = 1;
            int i7 = bArrA[0] & 255;
            int i8 = bArrA[1] & 255;
            while (i6 < iC - 1) {
                int i9 = i6 + 1;
                int i10 = bArrA[i9] & 255;
                if ((((i8 * 4) - i7) - i10) / 2 < iA) {
                    rVar.g(i6);
                }
                i7 = i8;
                i6 = i9;
                i8 = i10;
            }
        }
        return rVar;
    }

    @Override // com.huawei.hms.scankit.p.o
    public s a() throws a {
        p4 p4VarC = c();
        int iC = p4VarC.c();
        int iA = p4VarC.a();
        a(iC);
        int[] iArr = this.f17691c;
        for (int i2 = 1; i2 < 5; i2++) {
            byte[] bArrA = p4VarC.a((iA * i2) / 5, this.f17690b);
            int i3 = (iC * 4) / 5;
            for (int i4 = iC / 5; i4 < i3; i4++) {
                int i5 = (bArrA[i4] & 255) >> 3;
                iArr[i5] = iArr[i5] + 1;
            }
        }
        int iA2 = a(iArr, true);
        byte[] bArrB = p4VarC.b();
        int i6 = (iC + 31) / 32;
        int[] iArr2 = new int[i6 * iA];
        for (int i7 = 0; i7 < iA; i7++) {
            int i8 = i7 * iC;
            for (int i9 = 0; i9 < iC; i9++) {
                if ((bArrB[i8 + i9] & 255) < iA2) {
                    int i10 = (i7 * i6) + (i9 >> 5);
                    iArr2[i10] = iArr2[i10] | (1 << (i9 & 31));
                }
            }
        }
        return new s(iC, iA, i6, iArr2);
    }

    @Override // com.huawei.hms.scankit.p.o
    public o a(p4 p4Var) {
        return new q3(p4Var);
    }

    private void a(int i2) {
        if (this.f17690b.length < i2) {
            this.f17690b = new byte[i2];
        }
        for (int i3 = 0; i3 < 32; i3++) {
            this.f17691c[i3] = 0;
        }
    }

    private static int a(int[] iArr, boolean z2) throws a {
        int length = iArr.length;
        boolean z3 = false;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            int i6 = iArr[i5];
            if (i6 > i2) {
                i4 = i5;
                i2 = i6;
            }
            if (i6 > i3) {
                i3 = i6;
            }
        }
        int i7 = 0;
        int i8 = 0;
        for (int i9 = 0; i9 < length; i9++) {
            int i10 = i9 - i4;
            int i11 = iArr[i9] * i10 * i10;
            if (i11 > i8) {
                i7 = i9;
                i8 = i11;
            }
        }
        if (i4 <= i7) {
            int i12 = i4;
            i4 = i7;
            i7 = i12;
        }
        int i13 = i4 - i7;
        if (i13 <= length / 16) {
            throw a.a();
        }
        int i14 = i4 - 1;
        int i15 = -1;
        int i16 = i14;
        while (i14 > i7) {
            int i17 = i14 - i7;
            int i18 = i17 * i17 * (i4 - i14) * (i3 - iArr[i14]);
            if (i18 > i15) {
                i16 = i14;
                i15 = i18;
            }
            i14--;
        }
        if (z2) {
            if (i16 < 10 && i15 < 100000 && i13 < 10) {
                z3 = true;
            }
            r3.f17733t = z3;
        }
        return i16 << 3;
    }
}
