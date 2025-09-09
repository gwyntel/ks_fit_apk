package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.p.a8;

/* loaded from: classes4.dex */
final class c1 {

    /* renamed from: a, reason: collision with root package name */
    private final int f17048a;

    /* renamed from: b, reason: collision with root package name */
    private final byte[] f17049b;

    private c1(int i2, byte[] bArr) {
        this.f17048a = i2;
        this.f17049b = bArr;
    }

    static c1[] a(byte[] bArr, a8 a8Var, c3 c3Var) {
        if (bArr.length != a8Var.l()) {
            throw new IllegalArgumentException();
        }
        a8.b bVarA = a8Var.a(c3Var);
        a8.a[] aVarArrA = bVarA.a();
        int iA = 0;
        for (a8.a aVar : aVarArrA) {
            iA += aVar.a();
        }
        c1[] c1VarArr = new c1[iA];
        int i2 = 0;
        for (a8.a aVar2 : aVarArrA) {
            int i3 = 0;
            while (i3 < aVar2.a()) {
                int iB = aVar2.b();
                c1VarArr[i2] = new c1(iB, new byte[bVarA.b() + iB]);
                i3++;
                i2++;
            }
        }
        int length = c1VarArr[0].f17049b.length;
        int i4 = iA - 1;
        while (i4 >= 0 && c1VarArr[i4].f17049b.length != length) {
            i4--;
        }
        int i5 = i4 + 1;
        int iB2 = length - bVarA.b();
        int i6 = 0;
        for (int i7 = 0; i7 < iB2; i7++) {
            int i8 = 0;
            while (i8 < i2) {
                c1VarArr[i8].f17049b[i7] = bArr[i6];
                i8++;
                i6++;
            }
        }
        int i9 = i5;
        while (i9 < i2) {
            c1VarArr[i9].f17049b[iB2] = bArr[i6];
            i9++;
            i6++;
        }
        int length2 = c1VarArr[0].f17049b.length;
        while (iB2 < length2) {
            int i10 = 0;
            while (i10 < i2) {
                c1VarArr[i10].f17049b[i10 < i5 ? iB2 : iB2 + 1] = bArr[i6];
                i10++;
                i6++;
            }
            iB2++;
        }
        return c1VarArr;
    }

    int b() {
        return this.f17048a;
    }

    byte[] a() {
        return this.f17049b;
    }
}
