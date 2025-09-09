package com.huawei.hms.scankit.p;

import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.nio.charset.Charset;
import java.util.Map;

/* loaded from: classes4.dex */
public final class u5 implements l8 {
    @Override // com.huawei.hms.scankit.p.l8
    public s a(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<u2, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.PDF_417) {
            throw new IllegalArgumentException("Can only encode PDF_417, but got " + barcodeFormat);
        }
        l5 l5Var = new l5();
        if (map != null) {
            u2 u2Var = u2.PDF417_COMPACT;
            if (map.containsKey(u2Var)) {
                l5Var.a(Boolean.valueOf(map.get(u2Var).toString()).booleanValue());
            }
            u2 u2Var2 = u2.PDF417_COMPACTION;
            if (map.containsKey(u2Var2)) {
                l5Var.a(y0.valueOf(map.get(u2Var2).toString()));
            }
            u2 u2Var3 = u2.PDF417_DIMENSIONS;
            if (map.containsKey(u2Var3)) {
                m2 m2Var = (m2) map.get(u2Var3);
                l5Var.b(m2Var.a(), m2Var.c(), m2Var.b(), m2Var.d());
            }
            u2 u2Var4 = u2.MARGIN;
            i = map.containsKey(u2Var4) ? Integer.parseInt(map.get(u2Var4).toString()) : 30;
            u2 u2Var5 = u2.ERROR_CORRECTION;
            i = map.containsKey(u2Var5) ? Integer.parseInt(map.get(u2Var5).toString()) : 2;
            u2 u2Var6 = u2.CHARACTER_SET;
            if (map.containsKey(u2Var6)) {
                l5Var.a(Charset.forName(map.get(u2Var6).toString()));
            }
        }
        return a(l5Var, str, i, i2, i3, i);
    }

    private static s a(l5 l5Var, String str, int i2, int i3, int i4, int i5) throws WriterException {
        l5Var.a(str, i2);
        byte[][] bArrA = l5Var.a().a(1, 4);
        int length = i3 / bArrA[0].length;
        int length2 = i4 / bArrA.length;
        if (length >= length2) {
            length = length2;
        }
        if (length > 1) {
            return a(l5Var.a().a(length, length * 4), i5);
        }
        return a(bArrA, i5);
    }

    private static s a(byte[][] bArr, int i2) {
        int i3 = i2 * 2;
        s sVar = new s(bArr[0].length + i3, bArr.length + i3);
        sVar.a();
        int iC = (sVar.c() - i2) - 1;
        int i4 = 0;
        while (i4 < bArr.length) {
            byte[] bArr2 = bArr[i4];
            for (int i5 = 0; i5 < bArr[0].length; i5++) {
                if (bArr2[i5] == 1) {
                    sVar.c(i5 + i2, iC);
                }
            }
            i4++;
            iC--;
        }
        return sVar;
    }
}
