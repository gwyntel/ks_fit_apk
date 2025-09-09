package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;

/* loaded from: classes4.dex */
final class o7 {

    /* renamed from: c, reason: collision with root package name */
    private static final int[] f17647c = {24, 20, 18, 17, 12, 6, 3, 10, 9, 5};

    /* renamed from: a, reason: collision with root package name */
    private final int[] f17648a = new int[4];

    /* renamed from: b, reason: collision with root package name */
    private final StringBuilder f17649b = new StringBuilder();

    o7() {
    }

    s6 a(int i2, r rVar, int[] iArr) throws a {
        StringBuilder sb = this.f17649b;
        sb.setLength(0);
        float f2 = i2;
        return new s6(sb.toString(), null, new u6[]{new u6((iArr[0] + iArr[1]) / 2.0f, f2), new u6(a(rVar, iArr, sb), f2)}, BarcodeFormat.UPC_EAN_EXTENSION);
    }

    private int a(r rVar, int[] iArr, StringBuilder sb) throws a {
        int[] iArr2 = this.f17648a;
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int iE = rVar.e();
        int iD = iArr[1];
        int i2 = 0;
        for (int i3 = 0; i3 < 5 && iD < iE; i3++) {
            int iA = q7.a(rVar, iArr2, iD, q7.f17705g);
            sb.append((char) ((iA % 10) + 48));
            for (int i4 : iArr2) {
                iD += i4;
            }
            if (iA >= 10) {
                i2 |= 1 << (4 - i3);
            }
            if (i3 != 4) {
                iD = rVar.d(rVar.c(iD));
            }
        }
        if (sb.length() == 5) {
            if (a(sb.toString()) == a(i2)) {
                return iD;
            }
            throw a.a();
        }
        throw a.a();
    }

    private static int a(CharSequence charSequence) {
        int length = charSequence.length();
        int iCharAt = 0;
        for (int i2 = length - 2; i2 >= 0; i2 -= 2) {
            iCharAt += charSequence.charAt(i2) - '0';
        }
        int iCharAt2 = iCharAt * 3;
        for (int i3 = length - 1; i3 >= 0; i3 -= 2) {
            iCharAt2 += charSequence.charAt(i3) - '0';
        }
        return (iCharAt2 * 3) % 10;
    }

    private static int a(int i2) throws a {
        for (int i3 = 0; i3 < 10; i3++) {
            if (i2 == f17647c[i3]) {
                return i3;
            }
        }
        throw a.a();
    }
}
