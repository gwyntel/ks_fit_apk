package com.huawei.hms.scankit.p;

import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Map;

/* loaded from: classes4.dex */
public final class r2 extends r7 {
    @Override // com.huawei.hms.scankit.p.h5, com.huawei.hms.scankit.p.l8
    public s a(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<u2, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.EAN_8) {
            return super.a(str, barcodeFormat, i2, i3, map);
        }
        throw new IllegalArgumentException("Can only encode EAN_8, but got " + barcodeFormat);
    }

    @Override // com.huawei.hms.scankit.p.h5
    public boolean[] a(String str) {
        int length = str.length();
        if (length == 7) {
            try {
                str = str + q7.b(str);
            } catch (a e2) {
                throw new IllegalArgumentException(e2);
            }
        } else if (length == 8) {
            try {
                if (!q7.a((CharSequence) str)) {
                    throw new IllegalArgumentException("Contents do not pass checksum");
                }
            } catch (a unused) {
                throw new IllegalArgumentException("Illegal contents");
            }
        } else {
            throw new IllegalArgumentException("Requested contents should be 8 digits long, but got " + length);
        }
        boolean[] zArr = new boolean[67];
        int iA = h5.a(zArr, 0, q7.f17701c, true);
        for (int i2 = 0; i2 <= 3; i2++) {
            iA += h5.a(zArr, iA, q7.f17704f[Character.digit(str.charAt(i2), 10)], false);
        }
        int iA2 = iA + h5.a(zArr, iA, q7.f17702d, false);
        for (int i3 = 4; i3 <= 7; i3++) {
            iA2 += h5.a(zArr, iA2, q7.f17704f[Character.digit(str.charAt(i3), 10)], true);
        }
        h5.a(zArr, iA2, q7.f17701c, true);
        return zArr;
    }
}
