package com.huawei.hms.scankit.p;

import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Map;

/* loaded from: classes4.dex */
public final class p2 extends r7 {
    @Override // com.huawei.hms.scankit.p.h5, com.huawei.hms.scankit.p.l8
    public s a(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<u2, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.EAN_13) {
            return super.a(str, barcodeFormat, i2, i3, map);
        }
        throw new IllegalArgumentException("Can only encode EAN_13, but got " + barcodeFormat);
    }

    @Override // com.huawei.hms.scankit.p.h5
    public boolean[] a(String str) {
        int length = str.length();
        if (length == 12) {
            try {
                str = str + q7.b(str);
            } catch (a e2) {
                throw new IllegalArgumentException(e2);
            }
        } else if (length == 13) {
            try {
                if (!q7.a((CharSequence) str)) {
                    throw new IllegalArgumentException("Contents do not pass checksum");
                }
            } catch (a unused) {
                throw new IllegalArgumentException("Illegal contents");
            }
        } else {
            throw new IllegalArgumentException("Requested contents should be 12 or 13 digits long, but got " + length);
        }
        int i2 = o2.f17626j[Character.digit(str.charAt(0), 10)];
        boolean[] zArr = new boolean[95];
        int iA = h5.a(zArr, 0, q7.f17701c, true);
        for (int i3 = 1; i3 <= 6; i3++) {
            int iDigit = Character.digit(str.charAt(i3), 10);
            if (((i2 >> (6 - i3)) & 1) == 1) {
                iDigit += 10;
            }
            iA += h5.a(zArr, iA, q7.f17705g[iDigit], false);
        }
        int iA2 = iA + h5.a(zArr, iA, q7.f17702d, false);
        for (int i4 = 7; i4 <= 12; i4++) {
            iA2 += h5.a(zArr, iA2, q7.f17704f[Character.digit(str.charAt(i4), 10)], true);
        }
        h5.a(zArr, iA2, q7.f17701c, true);
        return zArr;
    }
}
