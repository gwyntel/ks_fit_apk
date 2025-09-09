package com.huawei.hms.scankit.p;

import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Map;

/* loaded from: classes4.dex */
public final class k6 implements l8 {
    @Override // com.huawei.hms.scankit.p.l8
    public s a(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<u2, ?> map) throws WriterException {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (barcodeFormat != BarcodeFormat.QR_CODE) {
            throw new IllegalArgumentException("Can only encode QR_CODE, but got " + barcodeFormat);
        }
        if (i2 < 0 || i3 < 0) {
            throw new IllegalArgumentException("Requested dimensions are too small: " + i2 + 'x' + i3);
        }
        b3 b3VarValueOf = b3.L;
        Boolean bool = Boolean.FALSE;
        if (map != null) {
            u2 u2Var = u2.ERROR_CORRECTION;
            if (map.containsKey(u2Var)) {
                b3VarValueOf = b3.valueOf(map.get(u2Var).toString());
            }
            u2 u2Var2 = u2.MARGIN;
            i = map.containsKey(u2Var2) ? Integer.parseInt(map.get(u2Var2).toString()) : 4;
            u2 u2Var3 = u2.LOGO;
            if (map.containsKey(u2Var3)) {
                bool = (Boolean) map.get(u2Var3);
            }
        }
        return a(w2.a(str, b3VarValueOf, map), i2, i3, i, bool.booleanValue());
    }

    private static s a(h6 h6Var, int i2, int i3, int i4, boolean z2) {
        int iMax;
        int iMax2;
        int iMin;
        c0 c0VarA = h6Var.a();
        if (c0VarA != null) {
            int iC = c0VarA.c();
            int iB = c0VarA.b();
            if (z2) {
                iMax = Math.max(i2, iC);
                iMax2 = Math.max(i3, iB);
                int i5 = i4 * 2;
                iMin = Math.min((iMax - i5) / iC, (iMax2 - i5) / iB);
            } else {
                int i6 = i4 * 2;
                int i7 = iC + i6;
                int i8 = i6 + iB;
                iMax = Math.max(i2, i7);
                iMax2 = Math.max(i3, i8);
                iMin = Math.min(iMax / i7, iMax2 / i8);
            }
            int i9 = (iMax - (iC * iMin)) / 2;
            int i10 = (iMax2 - (iB * iMin)) / 2;
            s sVar = new s(iMax, iMax2);
            int i11 = 0;
            while (i11 < iB) {
                int i12 = 0;
                int i13 = i9;
                while (i12 < iC) {
                    if (c0VarA.a(i12, i11) == 1) {
                        sVar.a(i13, i10, iMin, iMin);
                    }
                    i12++;
                    i13 += iMin;
                }
                i11++;
                i10 += iMin;
            }
            return sVar;
        }
        throw new IllegalStateException();
    }
}
