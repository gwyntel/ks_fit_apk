package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes4.dex */
public final class j4 extends g5 {

    /* renamed from: c, reason: collision with root package name */
    private static final int[] f17431c = {6, 8, 10, 12, 14};

    /* renamed from: d, reason: collision with root package name */
    private static final int[] f17432d = {1, 1, 1, 1};

    /* renamed from: e, reason: collision with root package name */
    private static final int[][] f17433e = {new int[]{1, 1, 2}, new int[]{1, 1, 3}};

    /* renamed from: f, reason: collision with root package name */
    public static final int[][] f17434f = {new int[]{1, 1, 2, 2, 1}, new int[]{2, 1, 1, 1, 2}, new int[]{1, 2, 1, 1, 2}, new int[]{2, 2, 1, 1, 1}, new int[]{1, 1, 2, 1, 2}, new int[]{2, 1, 2, 1, 1}, new int[]{1, 2, 2, 1, 1}, new int[]{1, 1, 1, 2, 2}, new int[]{2, 1, 1, 2, 1}, new int[]{1, 2, 1, 2, 1}, new int[]{1, 1, 3, 3, 1}, new int[]{3, 1, 1, 1, 3}, new int[]{1, 3, 1, 1, 3}, new int[]{3, 3, 1, 1, 1}, new int[]{1, 1, 3, 1, 3}, new int[]{3, 1, 3, 1, 1}, new int[]{1, 3, 3, 1, 1}, new int[]{1, 1, 1, 3, 3}, new int[]{3, 1, 1, 3, 1}, new int[]{1, 3, 1, 3, 1}};

    /* renamed from: a, reason: collision with root package name */
    private int f17435a = -1;

    /* renamed from: b, reason: collision with root package name */
    private int f17436b = -1;

    private int[] b(r rVar) throws a {
        int iC = c(rVar);
        while (true) {
            int[] iArrC = c(rVar, iC, f17432d);
            int i2 = iArrC[1];
            int i3 = iArrC[0];
            this.f17435a = (i2 - i3) / 4;
            if (a(rVar, i3)) {
                return iArrC;
            }
            iC = iArrC[2];
        }
    }

    private static int c(r rVar) throws a {
        int iE = rVar.e();
        int iC = rVar.c(0);
        if (iC != iE) {
            return iC;
        }
        throw a.a();
    }

    @Override // com.huawei.hms.scankit.p.g5
    public s6 a(int i2, r rVar, Map<l1, ?> map) throws a {
        boolean z2;
        int[] iArrB = b(rVar);
        int[] iArrA = a(rVar);
        StringBuilder sb = new StringBuilder(20);
        a(rVar, iArrB[1], iArrA[0], sb);
        String string = sb.toString();
        int[] iArr = f17431c;
        int length = string.length();
        int length2 = iArr.length;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= length2) {
                z2 = false;
                break;
            }
            int i5 = iArr[i3];
            if (length == i5) {
                z2 = true;
                break;
            }
            if (i5 > i4) {
                i4 = i5;
            }
            i3++;
        }
        if (!z2 && length > i4) {
            z2 = true;
        }
        if (!z2) {
            throw a.a();
        }
        float f2 = i2;
        return new s6(string, null, new u6[]{new u6(iArrB[0], f2), new u6(iArrA[1], f2)}, BarcodeFormat.ITF);
    }

    private int[] c(r rVar, int i2, int[] iArr) throws a {
        char c2;
        int i3;
        int length = iArr.length;
        int[] iArr2 = new int[length];
        int iE = rVar.e();
        char c3 = 0;
        int i4 = i2;
        int i5 = i4;
        boolean z2 = false;
        int i6 = 0;
        while (i4 < iE) {
            if (rVar.b(i4) == z2) {
                if (i6 == length - 1) {
                    int[] iArr3 = (int[]) iArr2.clone();
                    Arrays.sort(iArr3);
                    int i7 = iArr3[c3];
                    double d2 = (iArr3[1] + i7) * 0.5d;
                    int i8 = iArr3[2];
                    int i9 = iArr3[3];
                    int i10 = i4;
                    if (((i8 + i9) * 0.5d) / d2 < 4.0d && (i9 * 1.0d) / i7 <= 3.0d) {
                        int[] iArr4 = new int[10];
                        g5.a(rVar, i10, iArr4);
                        this.f17436b = -1;
                        for (int i11 = 0; i11 < 10; i11++) {
                            int i12 = iArr4[i11];
                            if (i12 > this.f17436b) {
                                this.f17436b = i12;
                            }
                        }
                        return new int[]{i5, i10, iArr2[0] + i5 + iArr2[1]};
                    }
                    i3 = i10;
                    c2 = 0;
                    i5 += iArr2[0] + iArr2[1];
                    int i13 = i6 - 1;
                    System.arraycopy(iArr2, 2, iArr2, 0, i13);
                    iArr2[i13] = 0;
                    iArr2[i6] = 0;
                    i6--;
                } else {
                    int i14 = i4;
                    c2 = c3;
                    i3 = i14;
                    i6++;
                }
                iArr2[i6] = 1;
                z2 = !z2;
            } else if (i6 >= 0 && i6 < length) {
                iArr2[i6] = iArr2[i6] + 1;
                int i15 = i4;
                c2 = c3;
                i3 = i15;
            } else {
                throw a.a();
            }
            char c4 = c2;
            i4 = i3 + 1;
            c3 = c4;
        }
        throw a.a();
    }

    private int[] b(r rVar, int i2, int[] iArr) throws a {
        int length = iArr.length;
        int[] iArr2 = new int[length];
        int iE = rVar.e();
        int i3 = i2;
        int i4 = i3;
        boolean z2 = false;
        int i5 = 0;
        while (i3 < iE) {
            if (rVar.b(i3) == z2) {
                if (i5 != length - 1) {
                    i5++;
                } else if (Math.min(iArr2[0], iArr2[1]) != 0 && Math.max(iArr2[0], iArr2[1]) != 0) {
                    float fMax = Math.max(iArr2[0], iArr2[1]) / Math.min(iArr2[0], iArr2[1]);
                    int i6 = iArr2[0];
                    int i7 = iArr2[1];
                    float f2 = (iArr2[2] * 2.0f) / (i6 + i7);
                    if (fMax <= 3.0f && f2 > 1.5d && f2 < 4.0f) {
                        return new int[]{i4, i3, i6 + i4 + i7};
                    }
                    i4 += i6 + i7;
                    int i8 = i5 - 1;
                    System.arraycopy(iArr2, 2, iArr2, 0, i8);
                    iArr2[i8] = 0;
                    iArr2[i5] = 0;
                    i5--;
                } else {
                    throw a.a();
                }
                iArr2[i5] = 1;
                z2 = !z2;
            } else if (i5 >= 0 && i5 < length) {
                iArr2[i5] = iArr2[i5] + 1;
            } else {
                throw a.a();
            }
            i3++;
        }
        throw a.a();
    }

    private static void a(r rVar, int i2, int i3, StringBuilder sb) throws a {
        int[] iArr = new int[10];
        int[] iArr2 = new int[5];
        int[] iArr3 = new int[5];
        while (i2 < i3) {
            g5.a(rVar, i2, iArr);
            int i4 = -1;
            int i5 = 10000;
            for (int i6 = 0; i6 < 10; i6++) {
                int i7 = iArr[i6];
                if (i4 <= i7) {
                    i4 = i7;
                }
                if (i5 >= i7) {
                    i5 = i7;
                }
            }
            if (i4 / i5 <= 8) {
                for (int i8 = 0; i8 < 5; i8++) {
                    int i9 = i8 * 2;
                    iArr2[i8] = iArr[i9];
                    iArr3[i8] = iArr[i9 + 1];
                }
                sb.append((char) (b(iArr2) + 48));
                sb.append((char) (b(iArr3) + 48));
                for (int i10 = 0; i10 < 10; i10++) {
                    i2 += iArr[i10];
                }
            } else {
                throw a.a();
            }
        }
        if (i2 != i3) {
            throw a.a();
        }
    }

    private static int b(int[] iArr) throws a {
        int length = f17434f.length;
        float f2 = 0.3f;
        int i2 = -1;
        for (int i3 = 0; i3 < length; i3++) {
            float fA = g5.a(iArr, f17434f[i3], 0.75f);
            if (fA < f2) {
                i2 = i3;
                f2 = fA;
            } else if (Math.abs(fA - f2) < 1.0E-7d) {
                i2 = -1;
            }
        }
        if (i2 >= 0) {
            return i2 % 10;
        }
        throw a.a();
    }

    private boolean a(r rVar, int i2) {
        int i3 = this.f17435a * 10;
        int i4 = (int) (this.f17436b * 1.5d);
        if (i3 < i4) {
            i3 = i4;
        }
        for (int i5 = i2 - 1; i3 > 0 && i5 >= 0 && !rVar.b(i5); i5--) {
            i3--;
        }
        return i3 == 0;
    }

    private int[] a(r rVar) throws a {
        try {
            rVar.h();
            int iC = c(rVar);
            while (true) {
                int[] iArrB = b(rVar, iC, f17433e[0]);
                if (a(rVar, iArrB[0])) {
                    int i2 = iArrB[0];
                    iArrB[0] = rVar.e() - iArrB[1];
                    iArrB[1] = rVar.e() - i2;
                    return iArrB;
                }
                iC = iArrB[2];
            }
        } finally {
            rVar.h();
        }
    }
}
