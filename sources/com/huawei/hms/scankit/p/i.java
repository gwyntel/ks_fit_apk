package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/* loaded from: classes4.dex */
public final class i implements l8 {
    @Override // com.huawei.hms.scankit.p.l8
    public s a(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<u2, ?> map) throws NumberFormatException {
        Charset charsetForName = StandardCharsets.ISO_8859_1;
        int i4 = 4;
        if (map != null) {
            u2 u2Var = u2.CHARACTER_SET;
            if (map.containsKey(u2Var)) {
                charsetForName = Charset.forName(map.get(u2Var).toString());
            }
            u2 u2Var2 = u2.ERROR_CORRECTION;
            i = map.containsKey(u2Var2) ? Integer.parseInt(map.get(u2Var2).toString()) : 33;
            u2 u2Var3 = u2.AZTEC_LAYERS;
            i = map.containsKey(u2Var3) ? Integer.parseInt(map.get(u2Var3).toString()) : 0;
            u2 u2Var4 = u2.MARGIN;
            if (map.containsKey(u2Var4)) {
                i4 = Integer.parseInt(map.get(u2Var4).toString());
            }
        }
        return a(str, barcodeFormat, i2, i3, charsetForName, i, i, i4);
    }

    private static s a(String str, BarcodeFormat barcodeFormat, int i2, int i3, Charset charset, int i4, int i5, int i6) {
        if (barcodeFormat == BarcodeFormat.AZTEC) {
            return a(x2.a(str.getBytes(charset), i4, i5), i2, i3, i6);
        }
        throw new IllegalArgumentException("Can only encode AZTEC, but got " + barcodeFormat);
    }

    private static s a(f fVar, int i2, int i3, int i4) {
        s sVarA = fVar.a();
        if (sVarA != null) {
            int iE = sVarA.e();
            int iC = sVarA.c();
            int i5 = i4 * 2;
            int i6 = iE + i5;
            int i7 = i5 + iC;
            int iMax = Math.max(i2, i6);
            int iMax2 = Math.max(i3, i7);
            int iMin = Math.min(iMax / i6, iMax2 / i7);
            int i8 = (iMax - (iE * iMin)) / 2;
            int i9 = (iMax2 - (iC * iMin)) / 2;
            s sVar = new s(iMax, iMax2);
            int i10 = 0;
            while (i10 < iC) {
                int i11 = 0;
                int i12 = i8;
                while (i11 < iE) {
                    if (sVarA.b(i11, i10)) {
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
        throw new IllegalStateException();
    }
}
