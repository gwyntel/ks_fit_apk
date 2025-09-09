package com.huawei.hms.scankit.p;

import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Map;

/* loaded from: classes4.dex */
public final class u0 extends h5 {
    @Override // com.huawei.hms.scankit.p.h5, com.huawei.hms.scankit.p.l8
    public s a(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<u2, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_39) {
            return super.a(str, barcodeFormat, i2, i3, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_39, but got " + barcodeFormat);
    }

    @Override // com.huawei.hms.scankit.p.h5
    public boolean[] a(String str) {
        int length = str.length();
        if (length <= 80) {
            int[] iArr = new int[9];
            int i2 = length + 25;
            for (int i3 = 0; i3 < length; i3++) {
                int iIndexOf = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".indexOf(str.charAt(i3));
                if (iIndexOf >= 0) {
                    a(t0.f17802e[iIndexOf], iArr);
                    for (int i4 = 0; i4 < 9; i4++) {
                        i2 += iArr[i4];
                    }
                } else {
                    throw new IllegalArgumentException("Bad contents: error contents");
                }
            }
            boolean[] zArr = new boolean[i2];
            a(148, iArr);
            int iA = h5.a(zArr, 0, iArr, true);
            int[] iArr2 = {1};
            int iA2 = iA + h5.a(zArr, iA, iArr2, false);
            for (int i5 = 0; i5 < length; i5++) {
                a(t0.f17802e["0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".indexOf(str.charAt(i5))], iArr);
                int iA3 = iA2 + h5.a(zArr, iA2, iArr, true);
                iA2 = iA3 + h5.a(zArr, iA3, iArr2, false);
            }
            a(148, iArr);
            h5.a(zArr, iA2, iArr, true);
            return zArr;
        }
        throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + length);
    }

    private static void a(int i2, int[] iArr) {
        for (int i3 = 0; i3 < 9; i3++) {
            int i4 = 1;
            if (((1 << (8 - i3)) & i2) != 0) {
                i4 = 2;
            }
            iArr[i3] = i4;
        }
    }
}
