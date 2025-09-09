package com.huawei.hms.scankit.p;

import java.lang.reflect.Array;

/* loaded from: classes4.dex */
final class m5 {

    /* renamed from: a, reason: collision with root package name */
    private static final float[][] f17554a = (float[][]) Array.newInstance((Class<?>) Float.TYPE, n5.f17579b.length, 8);

    static {
        int i2;
        int i3 = 0;
        while (true) {
            int[] iArr = n5.f17579b;
            if (i3 >= iArr.length) {
                return;
            }
            int i4 = iArr[i3];
            int i5 = i4 & 1;
            int i6 = 0;
            while (i6 < 8) {
                float f2 = 0.0f;
                while (true) {
                    i2 = i4 & 1;
                    if (i2 == i5) {
                        f2 += 1.0f;
                        i4 >>= 1;
                    }
                }
                f17554a[i3][7 - i6] = f2 / 17.0f;
                i6++;
                i5 = i2;
            }
            i3++;
        }
    }

    private static int a(int[] iArr) {
        long j2 = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            for (int i3 = 0; i3 < iArr[i2]; i3++) {
                int i4 = 1;
                long j3 = j2 << 1;
                if (i2 % 2 != 0) {
                    i4 = 0;
                }
                j2 = j3 | i4;
            }
        }
        return (int) j2;
    }

    private static int b(int[] iArr) {
        int iA = s4.a(iArr);
        float[] fArr = new float[8];
        if (iA > 1) {
            for (int i2 = 0; i2 < 8; i2++) {
                fArr[i2] = iArr[i2] / iA;
            }
        }
        float f2 = Float.MAX_VALUE;
        int i3 = -1;
        int i4 = 0;
        while (true) {
            float[][] fArr2 = f17554a;
            if (i4 >= fArr2.length) {
                return i3;
            }
            float[] fArr3 = fArr2[i4];
            float f3 = 0.0f;
            for (int i5 = 0; i5 < 8; i5++) {
                float f4 = fArr3[i5] - fArr[i5];
                f3 += f4 * f4;
                if (f3 >= f2) {
                    break;
                }
            }
            if (f3 < f2) {
                i3 = n5.f17579b[i4];
                f2 = f3;
            }
            i4++;
        }
    }

    private static int c(int[] iArr) {
        int iA = a(iArr);
        if (n5.a(iA) == -1) {
            return -1;
        }
        return iA;
    }

    static int d(int[] iArr) {
        int iC = c(e(iArr));
        return iC != -1 ? iC : b(iArr);
    }

    private static int[] e(int[] iArr) {
        float fA = s4.a(iArr);
        int[] iArr2 = new int[8];
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < 17; i4++) {
            float f2 = (fA / 34.0f) + ((i4 * fA) / 17.0f);
            int i5 = iArr[i3];
            if (i2 + i5 <= f2) {
                i2 += i5;
                i3++;
            }
            iArr2[i3] = iArr2[i3] + 1;
        }
        return iArr2;
    }
}
