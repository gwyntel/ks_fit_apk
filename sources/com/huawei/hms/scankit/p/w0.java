package com.huawei.hms.scankit.p;

import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Map;

/* loaded from: classes4.dex */
public class w0 extends h5 {
    @Override // com.huawei.hms.scankit.p.h5, com.huawei.hms.scankit.p.l8
    public s a(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<u2, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_93) {
            return super.a(str, barcodeFormat, i2, i3, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_93, but got " + barcodeFormat);
    }

    @Override // com.huawei.hms.scankit.p.h5
    public boolean[] a(String str) {
        int length = str.length();
        if (length <= 80) {
            int[] iArr = new int[9];
            int length2 = ((str.length() + 4) * 9) + 1;
            a(v0.f17870d[47], iArr);
            boolean[] zArr = new boolean[length2];
            int iA = a(zArr, 0, iArr);
            for (int i2 = 0; i2 < length; i2++) {
                int iIndexOf = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(str.charAt(i2));
                if (iIndexOf >= 0) {
                    a(v0.f17870d[iIndexOf], iArr);
                    iA += a(zArr, iA, iArr);
                } else {
                    throw new IllegalArgumentException("Bad contents: please check contents");
                }
            }
            int iA2 = a(str, 20);
            int[] iArr2 = v0.f17870d;
            a(iArr2[iA2], iArr);
            int iA3 = iA + a(zArr, iA, iArr);
            a(iArr2[a(str + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".charAt(iA2), 15)], iArr);
            int iA4 = iA3 + a(zArr, iA3, iArr);
            a(iArr2[47], iArr);
            zArr[iA4 + a(zArr, iA4, iArr)] = true;
            return zArr;
        }
        throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + length);
    }

    private static void a(int i2, int[] iArr) {
        for (int i3 = 0; i3 < 9; i3++) {
            int i4 = 1;
            if (((1 << (8 - i3)) & i2) == 0) {
                i4 = 0;
            }
            iArr[i3] = i4;
        }
    }

    private static int a(boolean[] zArr, int i2, int[] iArr) {
        int length = iArr.length;
        int i3 = 0;
        while (i3 < length) {
            int i4 = i2 + 1;
            zArr[i2] = iArr[i3] != 0;
            i3++;
            i2 = i4;
        }
        return 9;
    }

    private static int a(String str, int i2) {
        int iIndexOf = 0;
        int i3 = 1;
        for (int length = str.length() - 1; length >= 0; length--) {
            iIndexOf += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(str.charAt(length)) * i3;
            i3++;
            if (i3 > i2) {
                i3 = 1;
            }
        }
        return iIndexOf % 47;
    }
}
