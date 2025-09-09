package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;

/* loaded from: classes4.dex */
final class n7 {

    /* renamed from: a, reason: collision with root package name */
    private final int[] f17582a = new int[4];

    /* renamed from: b, reason: collision with root package name */
    private final StringBuilder f17583b = new StringBuilder();

    n7() {
    }

    s6 a(int i2, r rVar, int[] iArr) throws a {
        StringBuilder sb = this.f17583b;
        sb.setLength(0);
        float f2 = i2;
        return new s6(sb.toString(), null, new u6[]{new u6((iArr[0] + iArr[1]) / 2.0f, f2), new u6(a(rVar, iArr, sb), f2)}, BarcodeFormat.UPC_EAN_EXTENSION);
    }

    private int a(r rVar, int[] iArr, StringBuilder sb) throws a {
        int[] iArr2 = this.f17582a;
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int iE = rVar.e();
        int iD = iArr[1];
        int i2 = 0;
        for (int i3 = 0; i3 < 2 && iD < iE; i3++) {
            int iA = q7.a(rVar, iArr2, iD, q7.f17705g);
            sb.append((char) ((iA % 10) + 48));
            for (int i4 : iArr2) {
                iD += i4;
            }
            if (iA >= 10) {
                i2 |= 1 << (1 - i3);
            }
            if (i3 != 1) {
                iD = rVar.d(rVar.c(iD));
            }
        }
        if (sb.length() == 2) {
            try {
                if (Integer.parseInt(sb.toString()) % 4 == i2) {
                    return iD;
                }
                throw a.a();
            } catch (NumberFormatException unused) {
                throw a.a();
            }
        }
        throw a.a();
    }
}
