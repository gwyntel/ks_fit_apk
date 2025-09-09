package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;

/* loaded from: classes4.dex */
public final class q2 extends q7 {

    /* renamed from: h, reason: collision with root package name */
    private final int[] f17688h = new int[4];

    private int b(r rVar, int[] iArr, int i2, int[][] iArr2) throws a {
        g5.a(rVar, i2, iArr);
        int length = iArr2.length;
        float f2 = 0.43f;
        int i3 = -1;
        for (int i4 = 0; i4 < length; i4++) {
            float fA = g5.a(iArr, iArr2[i4], 0.7f);
            if (fA < f2) {
                i3 = i4;
                f2 = fA;
            }
        }
        if (i3 >= 0) {
            return i3;
        }
        throw a.a();
    }

    @Override // com.huawei.hms.scankit.p.q7
    public boolean a(int i2, int i3, r rVar) {
        return rVar.a(i3, ((int) ((i3 - i2) * 1.5d)) + i3, false, true);
    }

    @Override // com.huawei.hms.scankit.p.q7
    protected int a(r rVar, int[] iArr, StringBuilder sb) throws a {
        int[] iArr2 = this.f17688h;
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int iE = rVar.e();
        int i2 = iArr[1];
        for (int i3 = 0; i3 < 4 && i2 < iE; i3++) {
            sb.append((char) (b(rVar, iArr2, i2, q7.f17704f) + 48));
            for (int i4 : iArr2) {
                i2 += i4;
            }
        }
        int i5 = q7.a(rVar, i2, true, q7.f17702d)[1];
        for (int i6 = 0; i6 < 4 && i5 < iE; i6++) {
            sb.append((char) (b(rVar, iArr2, i5, q7.f17704f) + 48));
            for (int i7 : iArr2) {
                i5 += i7;
            }
        }
        return i5;
    }

    @Override // com.huawei.hms.scankit.p.q7
    BarcodeFormat a() {
        return BarcodeFormat.EAN_8;
    }

    @Override // com.huawei.hms.scankit.p.q7
    boolean a(int[] iArr, int[] iArr2) throws a {
        int i2 = iArr[1];
        int i3 = iArr[0];
        double d2 = (i2 - i3) / 3.0d;
        int i4 = iArr2[1];
        int i5 = iArr2[0];
        return ((double) Math.abs(((int) Math.round(((double) (i4 - i3)) / (((double) ((i4 - i5) + (i2 - i3))) / 6.0d))) + (-67))) <= 6.7d && Math.abs(1.0d - (d2 / (((double) (i4 - i5)) / 3.0d))) < 0.2d;
    }
}
