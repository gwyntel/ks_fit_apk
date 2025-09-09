package com.huawei.hms.scankit.p;

import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class h5 implements l8 {
    public int a() {
        return 10;
    }

    public abstract boolean[] a(String str);

    @Override // com.huawei.hms.scankit.p.l8
    public s a(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<u2, ?> map) throws WriterException, NumberFormatException {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (i2 < 0 || i3 < 0) {
            throw new IllegalArgumentException("Negative size is not allowed. Input: " + i2 + 'x' + i3);
        }
        int iA = a();
        if (map != null) {
            u2 u2Var = u2.MARGIN;
            if (map.containsKey(u2Var)) {
                try {
                    iA = Integer.parseInt(map.get(u2Var).toString());
                } catch (NumberFormatException unused) {
                    throw new IllegalArgumentException("EncodeHintType MARGIN can not format integer");
                }
            }
        }
        return a(a(str), i2, i3, iA);
    }

    private static s a(boolean[] zArr, int i2, int i3, int i4) {
        int length = zArr.length;
        int i5 = i4 + length;
        int iMax = Math.max(i2, i5);
        int iMax2 = Math.max(1, i3);
        int i6 = iMax / i5;
        int i7 = (iMax - (length * i6)) / 2;
        s sVar = new s(iMax, iMax2);
        int i8 = 0;
        while (i8 < length) {
            if (zArr[i8]) {
                sVar.a(i7, 0, i6, iMax2);
            }
            i8++;
            i7 += i6;
        }
        return sVar;
    }

    protected static int a(boolean[] zArr, int i2, int[] iArr, boolean z2) {
        int i3 = 0;
        for (int i4 : iArr) {
            int i5 = 0;
            while (i5 < i4) {
                zArr[i2] = z2;
                i5++;
                i2++;
            }
            i3 += i4;
            z2 = !z2;
        }
        return i3;
    }
}
