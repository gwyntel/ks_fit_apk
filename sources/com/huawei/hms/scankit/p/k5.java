package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class k5 extends q3 {

    /* renamed from: e, reason: collision with root package name */
    private s f17481e;

    /* renamed from: f, reason: collision with root package name */
    private p4 f17482f;

    public k5(p4 p4Var) {
        super(p4Var);
        this.f17482f = p4Var;
    }

    @Override // com.huawei.hms.scankit.p.q3, com.huawei.hms.scankit.p.o
    public s a() {
        s sVar = this.f17481e;
        if (sVar != null) {
            return sVar;
        }
        p4 p4VarC = c();
        int iC = p4VarC.c();
        int iA = p4VarC.a();
        byte[] bArrB = p4VarC.b();
        s sVar2 = new s(iC, iA);
        byte[] bArrB2 = this.f17482f.b();
        int iC2 = this.f17482f.c();
        int iA2 = this.f17482f.a();
        int i2 = 256;
        int[] iArr = new int[256];
        for (int i3 = 0; i3 < iA2; i3++) {
            int i4 = i3 * iC2;
            for (int i5 = 0; i5 < iC2; i5++) {
                int i6 = bArrB2[i4 + i5] & 255;
                iArr[i6] = iArr[i6] + 1;
            }
        }
        int i7 = 0;
        int i8 = 0;
        double d2 = 0.0d;
        while (i7 < i2) {
            int i9 = 0;
            int i10 = 0;
            double d3 = 0.0d;
            while (i9 < i7) {
                i10 += iArr[i9];
                d3 += r12 * i9;
                i9++;
                iArr = iArr;
                i2 = 256;
            }
            int[] iArr2 = iArr;
            int i11 = i7;
            int i12 = 0;
            double d4 = 0.0d;
            while (i11 < i2) {
                i12 += iArr2[i11];
                d4 += r12 * i11;
                i11++;
                i2 = 256;
            }
            double d5 = i10;
            int i13 = iC2;
            int i14 = iA2;
            double d6 = iC2 * iA2;
            double d7 = d5 / d6;
            int i15 = i8;
            double d8 = i12;
            double d9 = d8 / d6;
            double d10 = i10 > 0 ? d3 / d5 : 0.0d;
            double d11 = i12 > 0 ? d4 / d8 : 0.0d;
            double d12 = (d7 * d10) + (d9 * d11);
            double d13 = d10 - d12;
            double d14 = d11 - d12;
            double d15 = (d7 * d13 * d13) + (d9 * d14 * d14);
            if (d15 > d2) {
                i8 = i7;
                d2 = d15;
            } else {
                i8 = i15;
            }
            i7++;
            iArr = iArr2;
            iC2 = i13;
            iA2 = i14;
            i2 = 256;
        }
        int i16 = i8;
        for (int i17 = 0; i17 < iA; i17++) {
            int i18 = i17 * iC;
            int i19 = 0;
            while (i19 < iC) {
                int i20 = i16;
                if ((bArrB[i18 + i19] & 255) <= i20) {
                    sVar2.c(i19, i17);
                }
                i19++;
                i16 = i20;
            }
        }
        this.f17481e = sVar2;
        return sVar2;
    }
}
