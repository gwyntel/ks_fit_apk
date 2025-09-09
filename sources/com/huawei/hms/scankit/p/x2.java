package com.huawei.hms.scankit.p;

import java.util.Locale;

/* loaded from: classes4.dex */
public final class x2 {

    /* renamed from: a, reason: collision with root package name */
    private static final int[] f17985a = {4, 6, 6, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

    private static int a(int i2, boolean z2) {
        return ((z2 ? 88 : 112) + (i2 * 16)) * i2;
    }

    private static r b(r rVar, int i2, int i3) {
        int iE = rVar.e() / i3;
        q6 q6Var = new q6(a(i3));
        int i4 = i2 / i3;
        int[] iArrA = a(rVar, i3, i4);
        q6Var.a(iArrA, i4 - iE);
        r rVar2 = new r();
        rVar2.a(0, i2 % i3);
        for (int i5 : iArrA) {
            rVar2.a(i5, i3);
        }
        return rVar2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static f a(byte[] bArr, int i2, int i3) {
        r rVarA;
        int i4;
        boolean z2;
        int iAbs;
        int iA;
        int i5;
        int i6;
        int i7 = 2;
        r rVarA2 = new c4(bArr).a();
        int iE = ((rVarA2.e() * i2) / 100) + 11;
        int iE2 = rVarA2.e() + iE;
        int i8 = 4;
        int i9 = 0;
        int i10 = 1;
        if (i3 == 0) {
            r rVarA3 = null;
            int i11 = 0;
            int i12 = 0;
            while (i11 <= i) {
                boolean z3 = i11 <= 3 ? i10 : i9;
                int i13 = z3 != 0 ? i11 + 1 : i11;
                int iA2 = a(i13, z3);
                if (iE2 > iA2) {
                    i5 = i10;
                } else {
                    if (rVarA3 == null || i12 != f17985a[i13]) {
                        int i14 = f17985a[i13];
                        i12 = i14;
                        rVarA3 = a(rVarA2, i14);
                    }
                    int i15 = iA2 - (iA2 % i12);
                    if ((z3 == 0 || rVarA3.e() <= i12 * 64) && rVarA3.e() + iE <= i15) {
                        rVarA = rVarA3;
                        i4 = i12;
                        z2 = z3;
                        iAbs = i13;
                        iA = iA2;
                    } else {
                        i5 = 1;
                    }
                }
                i11 += i5;
                i10 = i5;
                i = 32;
                i8 = 4;
                i9 = 0;
            }
            throw new IllegalArgumentException("Data too large for an Aztec code");
        }
        z2 = i3 < 0;
        iAbs = Math.abs(i3);
        if (iAbs > (z2 ? 4 : 32)) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Illegal value %s for layers", Integer.valueOf(i3)));
        }
        iA = a(iAbs, z2);
        i4 = f17985a[iAbs];
        int i16 = iA - (iA % i4);
        rVarA = a(rVarA2, i4);
        if (rVarA.e() + iE > i16) {
            throw new IllegalArgumentException("Data to large for user specified layer");
        }
        if (z2 && rVarA.e() > i4 * 64) {
            throw new IllegalArgumentException("Data to large for user specified layer");
        }
        r rVarB = b(rVarA, iA, i4);
        int iE3 = rVarA.e() / i4;
        r rVarA4 = a(z2, iAbs, iE3);
        int i17 = (z2 ? 11 : 14) + (iAbs * 4);
        int[] iArr = new int[i17];
        if (z2) {
            for (int i18 = i9; i18 < i17; i18 += i10) {
                iArr[i18] = i18;
            }
            i6 = i17;
        } else {
            int i19 = i17 / 2;
            i6 = i17 + 1 + (((i19 - 1) / 15) * 2);
            int i20 = i6 / 2;
            for (int i21 = i9; i21 < i19; i21 += i10) {
                iArr[(i19 - i21) - 1] = (i20 - r15) - 1;
                iArr[i19 + i21] = (i21 / 15) + i21 + i20 + i10;
            }
        }
        s sVar = new s(i6);
        int i22 = i9;
        int i23 = i22;
        while (i22 < iAbs) {
            int i24 = ((iAbs - i22) * i8) + (z2 ? 9 : 12);
            while (i9 < i24) {
                int i25 = i9 * 2;
                int i26 = 0;
                while (i26 < i7) {
                    if (rVarB.b(i23 + i25 + i26)) {
                        int i27 = i22 * 2;
                        sVar.c(iArr[i27 + i26], iArr[i27 + i9]);
                    }
                    if (rVarB.b((i24 * 2) + i23 + i25 + i26)) {
                        int i28 = i22 * 2;
                        sVar.c(iArr[i28 + i9], iArr[((i17 - 1) - i28) - i26]);
                    }
                    if (rVarB.b((i24 * 4) + i23 + i25 + i26)) {
                        int i29 = (i17 - 1) - (i22 * 2);
                        sVar.c(iArr[i29 - i26], iArr[i29 - i9]);
                    }
                    if (rVarB.b((i24 * 6) + i23 + i25 + i26)) {
                        int i30 = i22 * 2;
                        sVar.c(iArr[((i17 - 1) - i30) - i9], iArr[i30 + i26]);
                    }
                    i26++;
                    i10 = 1;
                    i7 = 2;
                }
                i9 += i10;
                i7 = 2;
            }
            i23 += i24 * 8;
            i22 += i10;
            i7 = 2;
            i8 = 4;
            i9 = 0;
        }
        a(sVar, z2, i6, rVarA4);
        if (z2) {
            a(sVar, i6 / 2, 5);
        } else {
            int i31 = i6 / 2;
            a(sVar, i31, 7);
            int i32 = 0;
            int i33 = 0;
            while (i32 < (i17 / 2) - 1) {
                for (int i34 = i31 & 1; i34 < i6; i34 += 2) {
                    int i35 = i31 - i33;
                    sVar.c(i35, i34);
                    int i36 = i31 + i33;
                    sVar.c(i36, i34);
                    sVar.c(i34, i35);
                    sVar.c(i34, i36);
                }
                i32 += 15;
                i33 += 16;
            }
        }
        f fVar = new f();
        fVar.a(z2);
        fVar.c(i6);
        fVar.b(iAbs);
        fVar.a(iE3);
        fVar.a(sVar);
        return fVar;
    }

    private static void a(s sVar, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4 += 2) {
            int i5 = i2 - i4;
            int i6 = i5;
            while (true) {
                int i7 = i2 + i4;
                if (i6 <= i7) {
                    sVar.c(i6, i5);
                    sVar.c(i6, i7);
                    sVar.c(i5, i6);
                    sVar.c(i7, i6);
                    i6++;
                }
            }
        }
        int i8 = i2 - i3;
        sVar.c(i8, i8);
        int i9 = i8 + 1;
        sVar.c(i9, i8);
        sVar.c(i8, i9);
        int i10 = i2 + i3;
        sVar.c(i10, i8);
        sVar.c(i10, i9);
        sVar.c(i10, i10 - 1);
    }

    static r a(boolean z2, int i2, int i3) {
        r rVar = new r();
        if (z2) {
            rVar.a(i2 - 1, 2);
            rVar.a(i3 - 1, 6);
            return b(rVar, 28, 4);
        }
        rVar.a(i2 - 1, 5);
        rVar.a(i3 - 1, 11);
        return b(rVar, 40, 4);
    }

    private static void a(s sVar, boolean z2, int i2, r rVar) {
        int i3 = i2 / 2;
        int i4 = 0;
        if (z2) {
            while (i4 < 7) {
                int i5 = (i3 - 3) + i4;
                if (rVar.b(i4)) {
                    sVar.c(i5, i3 - 5);
                }
                if (rVar.b(i4 + 7)) {
                    sVar.c(i3 + 5, i5);
                }
                if (rVar.b(20 - i4)) {
                    sVar.c(i5, i3 + 5);
                }
                if (rVar.b(27 - i4)) {
                    sVar.c(i3 - 5, i5);
                }
                i4++;
            }
            return;
        }
        while (i4 < 10) {
            int i6 = (i3 - 5) + i4 + (i4 / 5);
            if (rVar.b(i4)) {
                sVar.c(i6, i3 - 7);
            }
            if (rVar.b(i4 + 10)) {
                sVar.c(i3 + 7, i6);
            }
            if (rVar.b(29 - i4)) {
                sVar.c(i6, i3 + 7);
            }
            if (rVar.b(39 - i4)) {
                sVar.c(i3 - 7, i6);
            }
            i4++;
        }
    }

    private static int[] a(r rVar, int i2, int i3) {
        int[] iArr = new int[i3];
        int iE = rVar.e() / i2;
        for (int i4 = 0; i4 < iE; i4++) {
            int i5 = 0;
            for (int i6 = 0; i6 < i2; i6++) {
                i5 |= rVar.b((i4 * i2) + i6) ? 1 << ((i2 - i6) - 1) : 0;
            }
            iArr[i4] = i5;
        }
        return iArr;
    }

    private static o3 a(int i2) {
        if (i2 == 4) {
            return o3.f17632k;
        }
        if (i2 == 6) {
            return o3.f17631j;
        }
        if (i2 == 8) {
            return o3.f17635n;
        }
        if (i2 == 10) {
            return o3.f17630i;
        }
        if (i2 == 12) {
            return o3.f17629h;
        }
        throw new IllegalArgumentException("Unsupported word size " + i2);
    }

    static r a(r rVar, int i2) {
        r rVar2 = new r();
        int iE = rVar.e();
        int i3 = (1 << i2) - 2;
        int i4 = 0;
        while (i4 < iE) {
            int i5 = 0;
            for (int i6 = 0; i6 < i2; i6++) {
                int i7 = i4 + i6;
                if (i7 >= iE || rVar.b(i7)) {
                    i5 |= 1 << ((i2 - 1) - i6);
                }
            }
            int i8 = i5 & i3;
            if (i8 == i3) {
                rVar2.a(i8, i2);
            } else if (i8 == 0) {
                rVar2.a(i5 | 1, i2);
            } else {
                rVar2.a(i5, i2);
                i4 += i2;
            }
            i4--;
            i4 += i2;
        }
        return rVar2;
    }
}
