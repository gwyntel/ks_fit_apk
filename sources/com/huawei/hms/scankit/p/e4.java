package com.huawei.hms.scankit.p;

import java.lang.reflect.Array;

/* loaded from: classes4.dex */
public final class e4 extends q3 {

    /* renamed from: f, reason: collision with root package name */
    private static int f17200f = 3;

    /* renamed from: g, reason: collision with root package name */
    private static int f17201g = 8;

    /* renamed from: h, reason: collision with root package name */
    private static int f17202h = 7;

    /* renamed from: i, reason: collision with root package name */
    private static int f17203i = 40;

    /* renamed from: j, reason: collision with root package name */
    private static int f17204j = 24;

    /* renamed from: e, reason: collision with root package name */
    private s f17205e;

    public e4(p4 p4Var) {
        super(p4Var);
        a(r3.f17727n);
    }

    private static int a(int i2, int i3, int i4) {
        return i2 < i3 ? i3 : i2 > i4 ? i4 : i2;
    }

    private void a(boolean z2) {
        if (z2) {
            f17200f = 2;
            f17201g = 4;
            f17202h = 3;
            f17203i = 20;
            return;
        }
        f17200f = 3;
        f17201g = 8;
        f17202h = 7;
        f17203i = 40;
    }

    @Override // com.huawei.hms.scankit.p.q3, com.huawei.hms.scankit.p.o
    public s a() throws a {
        s sVar = this.f17205e;
        if (sVar != null) {
            return sVar;
        }
        p4 p4VarC = c();
        int iC = p4VarC.c();
        int iA = p4VarC.a();
        int i2 = f17203i;
        if (iC >= i2 && iA >= i2) {
            byte[] bArrB = p4VarC.b();
            int i3 = f17200f;
            int i4 = iC >> i3;
            int i5 = f17202h;
            if ((iC & i5) != 0) {
                i4++;
            }
            int i6 = iA >> i3;
            if ((i5 & iA) != 0) {
                i6++;
            }
            int i7 = i6;
            this.f17205e = a(bArrB, i4, i7, iC, iA, a(bArrB, i4, i7, iC, iA));
        } else {
            this.f17205e = super.a();
        }
        return this.f17205e;
    }

    @Override // com.huawei.hms.scankit.p.q3, com.huawei.hms.scankit.p.o
    public o a(p4 p4Var) {
        return new e4(p4Var);
    }

    private static s a(byte[] bArr, int i2, int i3, int i4, int i5, int[][] iArr) {
        int i6;
        int i7;
        int i8;
        int[] iArr2 = new int[i2 * i3];
        for (int i9 = 0; i9 < i3; i9++) {
            int iA = a(i9, 2, i3 - 3);
            for (int i10 = 0; i10 < i2; i10++) {
                int iA2 = a(i10, 2, i2 - 3);
                int[] iArr3 = iArr[iA + 2];
                int i11 = iA2 + 2;
                int i12 = iArr3[i11];
                if (iA == 2 && iA2 == 2) {
                    i8 = 0;
                    i7 = 0;
                } else {
                    if (iA == 2) {
                        i6 = 0;
                        i7 = iArr3[iA2 - 3];
                        i8 = 0;
                    } else if (iA2 == 2) {
                        i8 = iArr[iA - 3][i11];
                        i7 = 0;
                    } else {
                        int[] iArr4 = iArr[iA - 3];
                        int i13 = iA2 - 3;
                        i6 = iArr4[i13];
                        int i14 = iArr4[i11];
                        i7 = iArr3[i13];
                        i8 = i14;
                    }
                    iArr2[(i9 * i2) + i10] = (((i12 + i6) - i8) - i7) / 25;
                }
                i6 = i7;
                iArr2[(i9 * i2) + i10] = (((i12 + i6) - i8) - i7) / 25;
            }
        }
        return new s(i4, i5, (i4 + 31) / 32, a(bArr, iArr2, i2, i3, i4, i5));
    }

    private static int[] a(byte[] bArr, int[] iArr, int i2, int i3, int i4, int i5) {
        int i6;
        int i7 = (i4 + 31) / 32;
        int i8 = i7 * i5;
        int[] iArr2 = new int[i8];
        for (int i9 = 0; i9 < i8; i9++) {
            iArr2[i9] = 0;
        }
        int i10 = f17201g;
        for (int i11 = 0; i11 < i5; i11++) {
            int i12 = i11 / i10;
            for (int i13 = 0; i13 < i4; i13++) {
                if ((bArr[(i11 * i4) + i13] & 255) <= iArr[(i12 * i2) + (i13 / i10)] && (i6 = (i11 * i7) + (i13 / 32)) < i8) {
                    iArr2[i6] = iArr2[i6] | (1 << (i13 & 31));
                }
            }
        }
        return iArr2;
    }

    private static int[][] a(byte[] bArr, int i2, int i3, int i4, int i5) {
        int i6 = f17201g;
        int i7 = i5 - i6;
        int i8 = i4 - i6;
        char c2 = 1;
        int i9 = 0;
        Class cls = Integer.TYPE;
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) cls, i3, i2);
        int[][] iArr2 = (int[][]) Array.newInstance((Class<?>) cls, i3, i2);
        int i10 = 0;
        while (i10 < i3) {
            int i11 = i10 << f17200f;
            if (i11 > i7) {
                i11 = i7;
            }
            int i12 = i9;
            int i13 = i12;
            while (i12 < i2) {
                int i14 = i12 << f17200f;
                if (i14 > i8) {
                    i14 = i8;
                }
                int[] iArrA = a(i14, i11, i4, bArr);
                int i15 = iArrA[i9];
                int i16 = iArrA[c2];
                int i17 = iArrA[2];
                int i18 = i15 >> (f17200f * 2);
                if (i17 - i16 <= f17204j) {
                    i18 = i16 / 2;
                    if (i10 > 0 && i12 > 0) {
                        int[] iArr3 = iArr2[i10 - 1];
                        int i19 = i12 - 1;
                        int i20 = ((iArr3[i12] + (iArr2[i10][i19] * 2)) + iArr3[i19]) / 4;
                        if (i16 < i20) {
                            i18 = i20;
                        }
                    }
                }
                i13 += i18;
                iArr2[i10][i12] = i18;
                if (i10 == 0 && i12 == 0) {
                    iArr[i10][i12] = i18;
                } else if (i10 == 0) {
                    iArr[i10][i12] = i13;
                } else {
                    iArr[i10][i12] = iArr[i10 - 1][i12] + i13;
                }
                i12++;
                c2 = 1;
                i9 = 0;
            }
            i10++;
            c2 = 1;
            i9 = 0;
        }
        return iArr;
    }

    private static int[] a(int i2, int i3, int i4, byte[] bArr) {
        int i5 = (i3 * i4) + i2;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 255;
        while (i6 < f17201g) {
            for (int i10 = 0; i10 < f17201g; i10++) {
                int i11 = bArr[i5 + i10] & 255;
                i7 += i11;
                if (i11 < i9) {
                    i9 = i11;
                }
                if (i11 > i8) {
                    i8 = i11;
                }
            }
            if (i8 - i9 > f17204j) {
                while (true) {
                    i6++;
                    i5 += i4;
                    if (i6 < f17201g) {
                        for (int i12 = 0; i12 < f17201g; i12++) {
                            i7 += bArr[i5 + i12] & 255;
                        }
                    }
                }
            }
            i6++;
            i5 += i4;
        }
        return new int[]{i7, i9, i8};
    }
}
