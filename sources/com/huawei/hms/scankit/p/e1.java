package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.p.z7;

/* loaded from: classes4.dex */
final class e1 {

    /* renamed from: a, reason: collision with root package name */
    private final int f17187a;

    /* renamed from: b, reason: collision with root package name */
    private final byte[] f17188b;

    private e1(int i2, byte[] bArr) {
        this.f17187a = i2;
        this.f17188b = bArr;
    }

    static e1[] a(byte[] bArr, z7 z7Var) {
        z7.c cVarD = z7Var.d();
        z7.b[] bVarArrA = cVarD.a();
        int iA = 0;
        for (z7.b bVar : bVarArrA) {
            iA += bVar.a();
        }
        e1[] e1VarArr = new e1[iA];
        int i2 = 0;
        for (z7.b bVar2 : bVarArrA) {
            int i3 = 0;
            while (i3 < bVar2.a()) {
                int iB = bVar2.b();
                e1VarArr[i2] = new e1(iB, new byte[cVarD.b() + iB]);
                i3++;
                i2++;
            }
        }
        int length = e1VarArr[0].f17188b.length - cVarD.b();
        int i4 = length - 1;
        int i5 = 0;
        for (int i6 = 0; i6 < i4; i6++) {
            int i7 = 0;
            while (i7 < i2) {
                e1VarArr[i7].f17188b[i6] = bArr[i5];
                i7++;
                i5++;
            }
        }
        boolean z2 = z7Var.h() == 24;
        int i8 = z2 ? 8 : i2;
        int i9 = 0;
        while (i9 < i8) {
            e1VarArr[i9].f17188b[i4] = bArr[i5];
            i9++;
            i5++;
        }
        int length2 = e1VarArr[0].f17188b.length;
        while (length < length2) {
            int i10 = 0;
            while (i10 < i2) {
                int i11 = z2 ? (i10 + 8) % i2 : i10;
                e1VarArr[i11].f17188b[(!z2 || i11 <= 7) ? length : length - 1] = bArr[i5];
                i10++;
                i5++;
            }
            length++;
        }
        if (i5 == bArr.length) {
            return e1VarArr;
        }
        throw new IllegalArgumentException();
    }

    int b() {
        return this.f17187a;
    }

    byte[] a() {
        return this.f17188b;
    }
}
