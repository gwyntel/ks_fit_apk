package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.p.b8;

/* loaded from: classes4.dex */
final class d1 {

    /* renamed from: a, reason: collision with root package name */
    private final int f17100a;

    /* renamed from: b, reason: collision with root package name */
    private final byte[] f17101b;

    private d1(int i2, byte[] bArr) {
        this.f17100a = i2;
        this.f17101b = bArr;
    }

    static d1[] a(byte[] bArr, b8 b8Var, b3 b3Var) {
        if (bArr.length != b8Var.e()) {
            throw new IllegalArgumentException();
        }
        b8.b bVarA = b8Var.a(b3Var);
        b8.a[] aVarArrA = bVarA.a();
        int iA = 0;
        for (b8.a aVar : aVarArrA) {
            iA += aVar.a();
        }
        d1[] d1VarArr = new d1[iA];
        int i2 = 0;
        for (b8.a aVar2 : aVarArrA) {
            int i3 = 0;
            while (i3 < aVar2.a()) {
                int iB = aVar2.b();
                d1VarArr[i2] = new d1(iB, new byte[bVarA.b() + iB]);
                i3++;
                i2++;
            }
        }
        int length = d1VarArr[0].f17101b.length;
        int i4 = iA - 1;
        while (i4 >= 0 && d1VarArr[i4].f17101b.length != length) {
            i4--;
        }
        return a(d1VarArr, bArr, length, bVarA, i2, i4 + 1);
    }

    int b() {
        return this.f17100a;
    }

    private static d1[] a(d1[] d1VarArr, byte[] bArr, int i2, b8.b bVar, int i3, int i4) {
        int iB = i2 - bVar.b();
        int i5 = 0;
        for (int i6 = 0; i6 < iB; i6++) {
            int i7 = 0;
            while (i7 < i3) {
                d1VarArr[i7].f17101b[i6] = bArr[i5];
                i7++;
                i5++;
            }
        }
        int i8 = i4;
        while (i8 < i3) {
            d1VarArr[i8].f17101b[iB] = bArr[i5];
            i8++;
            i5++;
        }
        int length = d1VarArr[0].f17101b.length;
        while (iB < length) {
            int i9 = 0;
            while (i9 < i3) {
                int i10 = i9 < i4 ? iB : iB + 1;
                if (i9 >= 0 && i9 < d1VarArr.length && w7.a(d1VarArr[i9].f17101b, i10) && w7.a(bArr, i5)) {
                    d1VarArr[i9].f17101b[i10] = bArr[i5];
                    i9++;
                    i5++;
                } else {
                    throw new ArrayIndexOutOfBoundsException();
                }
            }
            iB++;
        }
        return d1VarArr;
    }

    byte[] a() {
        return this.f17101b;
    }
}
