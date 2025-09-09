package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Map;

/* loaded from: classes4.dex */
public final class j1 implements l8 {
    @Override // com.huawei.hms.scankit.p.l8
    public s a(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<u2, ?> map) {
        l2 l2Var;
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (barcodeFormat != BarcodeFormat.DATA_MATRIX) {
            throw new IllegalArgumentException("Can only encode DATA_MATRIX, but got " + barcodeFormat);
        }
        if (i2 < 0 || i3 < 0) {
            throw new IllegalArgumentException("Requested dimensions can't be negative: " + i2 + 'x' + i3);
        }
        e7 e7Var = e7.FORCE_SQUARE;
        if (map != null) {
            e7 e7Var2 = (e7) map.get(u2.DATA_MATRIX_SHAPE);
            if (e7Var2 != null) {
                e7Var = e7Var2;
            }
            l2 l2Var2 = (l2) map.get(u2.MIN_SIZE);
            if (l2Var2 == null) {
                l2Var2 = null;
            }
            l2 l2Var3 = (l2) map.get(u2.MAX_SIZE);
            l2Var = l2Var3 != null ? l2Var3 : null;
            u2 u2Var = u2.MARGIN;
            i = map.containsKey(u2Var) ? Integer.parseInt(map.get(u2Var).toString()) : 4;
            l2Var = l2Var;
            l2Var = l2Var2;
        } else {
            l2Var = null;
        }
        String strA = d4.a(str, e7Var, l2Var, l2Var);
        d7 d7VarA = d7.a(strA.length(), e7Var, l2Var, l2Var, true);
        y1 y1Var = new y1(z2.a(strA, d7VarA), d7VarA.f(), d7VarA.e());
        y1Var.a();
        return a(y1Var, d7VarA, i2, i3, i);
    }

    private static s a(y1 y1Var, d7 d7Var, int i2, int i3, int i4) {
        int iF = d7Var.f();
        int iE = d7Var.e();
        c0 c0Var = new c0(d7Var.h(), d7Var.g());
        int i5 = 0;
        for (int i6 = 0; i6 < iE; i6++) {
            if (i6 % d7Var.f17139e == 0) {
                int i7 = 0;
                for (int i8 = 0; i8 < d7Var.h(); i8++) {
                    c0Var.a(i7, i5, i8 % 2 == 0);
                    i7++;
                }
                i5++;
            }
            int i9 = 0;
            for (int i10 = 0; i10 < iF; i10++) {
                if (i10 % d7Var.f17138d == 0) {
                    c0Var.a(i9, i5, true);
                    i9++;
                }
                c0Var.a(i9, i5, y1Var.a(i10, i6));
                int i11 = i9 + 1;
                int i12 = d7Var.f17138d;
                if (i10 % i12 == i12 - 1) {
                    c0Var.a(i11, i5, i6 % 2 == 0);
                    i9 += 2;
                } else {
                    i9 = i11;
                }
            }
            int i13 = i5 + 1;
            int i14 = d7Var.f17139e;
            if (i6 % i14 == i14 - 1) {
                int i15 = 0;
                for (int i16 = 0; i16 < d7Var.h(); i16++) {
                    c0Var.a(i15, i13, true);
                    i15++;
                }
                i5 += 2;
            } else {
                i5 = i13;
            }
        }
        return a(c0Var, i2, i3, i4);
    }

    private static s a(c0 c0Var, int i2, int i3, int i4) {
        s sVar;
        int iC = c0Var.c();
        int iB = c0Var.b();
        int i5 = i4 * 2;
        int i6 = iC + i5;
        int i7 = i5 + iB;
        int iMax = Math.max(i2, i6);
        int iMax2 = Math.max(i3, i7);
        int iMin = Math.min(iMax / i6, iMax2 / i7);
        int i8 = (iMax - (iC * iMin)) / 2;
        int i9 = (iMax2 - (iB * iMin)) / 2;
        if (i3 >= iB && i2 >= iC) {
            sVar = new s(i2, i3);
        } else {
            sVar = new s(iC, iB);
            i8 = 0;
            i9 = 0;
        }
        sVar.a();
        int i10 = 0;
        while (i10 < iB) {
            int i11 = 0;
            int i12 = i8;
            while (i11 < iC) {
                if (c0Var.a(i11, i10) == 1) {
                    sVar.a(i12, i9, iMin, iMin);
                }
                i11++;
                i12 += iMin;
            }
            i10++;
            i9 += iMin;
        }
        return sVar;
    }
}
