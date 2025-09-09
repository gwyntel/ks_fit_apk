package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class v {

    /* renamed from: a, reason: collision with root package name */
    private final s f17866a;

    /* renamed from: b, reason: collision with root package name */
    private final s f17867b;

    /* renamed from: c, reason: collision with root package name */
    private final z7 f17868c;

    v(s sVar) throws a {
        int iC = sVar.c();
        if (iC < 8 || iC > 144 || (iC & 1) != 0) {
            throw a.a();
        }
        this.f17868c = b(sVar);
        s sVarA = a(sVar);
        this.f17866a = sVarA;
        this.f17867b = new s(sVarA.e(), sVarA.c());
    }

    private static z7 b(s sVar) throws a {
        return z7.a(sVar.c(), sVar.e());
    }

    private int c(int i2, int i3) {
        int i4 = i2 - 1;
        int i5 = (a(i4, 0, i2, i3) ? 1 : 0) << 1;
        int i6 = i3 - 1;
        if (a(i4, i6, i2, i3)) {
            i5 |= 1;
        }
        int i7 = i5 << 1;
        int i8 = i3 - 3;
        if (a(0, i8, i2, i3)) {
            i7 |= 1;
        }
        int i9 = i7 << 1;
        int i10 = i3 - 2;
        if (a(0, i10, i2, i3)) {
            i9 |= 1;
        }
        int i11 = i9 << 1;
        if (a(0, i6, i2, i3)) {
            i11 |= 1;
        }
        int i12 = i11 << 1;
        if (a(1, i8, i2, i3)) {
            i12 |= 1;
        }
        int i13 = i12 << 1;
        if (a(1, i10, i2, i3)) {
            i13 |= 1;
        }
        int i14 = i13 << 1;
        return a(1, i6, i2, i3) ? i14 | 1 : i14;
    }

    private int d(int i2, int i3) {
        int i4 = (a(i2 + (-3), 0, i2, i3) ? 1 : 0) << 1;
        if (a(i2 - 2, 0, i2, i3)) {
            i4 |= 1;
        }
        int i5 = i4 << 1;
        if (a(i2 - 1, 0, i2, i3)) {
            i5 |= 1;
        }
        int i6 = i5 << 1;
        if (a(0, i3 - 2, i2, i3)) {
            i6 |= 1;
        }
        int i7 = i6 << 1;
        int i8 = i3 - 1;
        if (a(0, i8, i2, i3)) {
            i7 |= 1;
        }
        int i9 = i7 << 1;
        if (a(1, i8, i2, i3)) {
            i9 |= 1;
        }
        int i10 = i9 << 1;
        if (a(2, i8, i2, i3)) {
            i10 |= 1;
        }
        int i11 = i10 << 1;
        return a(3, i8, i2, i3) ? i11 | 1 : i11;
    }

    z7 a() {
        return this.f17868c;
    }

    private int[] a(int i2, int i3, int i4, int i5, byte[] bArr, int i6) {
        while (true) {
            if (i2 < i3 && i4 >= 0 && !this.f17867b.b(i4, i2)) {
                bArr[i6] = (byte) b(i2, i4, i3, i5);
                i6++;
            }
            int i7 = i2 - 2;
            int i8 = i4 + 2;
            if (i7 < 0 || i8 >= i5) {
                break;
            }
            i2 = i7;
            i4 = i8;
        }
        int i9 = i2 - 1;
        int i10 = i4 + 5;
        while (true) {
            if (i9 >= 0 && i10 < i5 && !this.f17867b.b(i10, i9)) {
                bArr[i6] = (byte) b(i9, i10, i3, i5);
                i6++;
            }
            int i11 = i9 + 2;
            int i12 = i10 - 2;
            if (i11 >= i3 || i12 < 0) {
                break;
            }
            i9 = i11;
            i10 = i12;
        }
        return new int[]{i9 + 5, i10 - 1, i6};
    }

    byte[] b() throws a {
        byte[] bArr = new byte[this.f17868c.g()];
        int iC = this.f17866a.c();
        int iE = this.f17866a.e();
        int i2 = 0;
        int i3 = 0;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        int i4 = 4;
        while (true) {
            if (i4 == iC && i2 == 0 && !z2) {
                bArr[i3] = (byte) a(iC, iE);
                i4 -= 2;
                i2 += 2;
                i3++;
                z2 = true;
            } else {
                int i5 = iC - 2;
                if (i4 == i5 && i2 == 0 && (iE & 3) != 0 && !z3) {
                    bArr[i3] = (byte) b(iC, iE);
                    i4 -= 2;
                    i2 += 2;
                    i3++;
                    z3 = true;
                } else if (i4 == iC + 4 && i2 == 2 && (iE & 7) == 0 && !z4) {
                    bArr[i3] = (byte) c(iC, iE);
                    i4 -= 2;
                    i2 += 2;
                    i3++;
                    z4 = true;
                } else if (i4 == i5 && i2 == 0 && (iE & 7) == 4 && !z5) {
                    bArr[i3] = (byte) d(iC, iE);
                    i4 -= 2;
                    i2 += 2;
                    i3++;
                    z5 = true;
                } else {
                    int[] iArrA = a(i4, iC, i2, iE, bArr, i3);
                    i4 = iArrA[0];
                    int i6 = iArrA[1];
                    i3 = iArrA[2];
                    i2 = i6;
                }
            }
            if (i4 >= iC && i2 >= iE) {
                break;
            }
        }
        if (i3 == this.f17868c.g()) {
            return bArr;
        }
        throw a.a();
    }

    private boolean a(int i2, int i3, int i4, int i5) {
        if (i2 < 0) {
            i2 += i4;
            i3 += 4 - ((i4 + 4) & 7);
        }
        if (i3 < 0) {
            i3 += i5;
            i2 += 4 - ((i5 + 4) & 7);
        }
        this.f17867b.c(i3, i2);
        return this.f17866a.b(i3, i2);
    }

    private int a(int i2, int i3) {
        int i4 = i2 - 1;
        int i5 = (a(i4, 0, i2, i3) ? 1 : 0) << 1;
        if (a(i4, 1, i2, i3)) {
            i5 |= 1;
        }
        int i6 = i5 << 1;
        if (a(i4, 2, i2, i3)) {
            i6 |= 1;
        }
        int i7 = i6 << 1;
        if (a(0, i3 - 2, i2, i3)) {
            i7 |= 1;
        }
        int i8 = i7 << 1;
        int i9 = i3 - 1;
        if (a(0, i9, i2, i3)) {
            i8 |= 1;
        }
        int i10 = i8 << 1;
        if (a(1, i9, i2, i3)) {
            i10 |= 1;
        }
        int i11 = i10 << 1;
        if (a(2, i9, i2, i3)) {
            i11 |= 1;
        }
        int i12 = i11 << 1;
        return a(3, i9, i2, i3) ? i12 | 1 : i12;
    }

    private s a(s sVar) {
        int iF = this.f17868c.f();
        int iE = this.f17868c.e();
        if (sVar.c() == iF) {
            int iC = this.f17868c.c();
            int iB = this.f17868c.b();
            int i2 = iF / iC;
            int i3 = iE / iB;
            s sVar2 = new s(i3 * iB, i2 * iC);
            for (int i4 = 0; i4 < i2; i4++) {
                int i5 = i4 * iC;
                for (int i6 = 0; i6 < i3; i6++) {
                    int i7 = i6 * iB;
                    for (int i8 = 0; i8 < iC; i8++) {
                        int i9 = ((iC + 2) * i4) + 1 + i8;
                        int i10 = i5 + i8;
                        for (int i11 = 0; i11 < iB; i11++) {
                            if (sVar.b(((iB + 2) * i6) + 1 + i11, i9)) {
                                sVar2.c(i7 + i11, i10);
                            }
                        }
                    }
                }
            }
            return sVar2;
        }
        throw new IllegalArgumentException("Dimension of bitMatrix must match the version size");
    }

    private int b(int i2, int i3, int i4, int i5) {
        int i6 = i2 - 2;
        int i7 = i3 - 2;
        int i8 = (a(i6, i7, i4, i5) ? 1 : 0) << 1;
        int i9 = i3 - 1;
        if (a(i6, i9, i4, i5)) {
            i8 |= 1;
        }
        int i10 = i8 << 1;
        int i11 = i2 - 1;
        if (a(i11, i7, i4, i5)) {
            i10 |= 1;
        }
        int i12 = i10 << 1;
        if (a(i11, i9, i4, i5)) {
            i12 |= 1;
        }
        int i13 = i12 << 1;
        if (a(i11, i3, i4, i5)) {
            i13 |= 1;
        }
        int i14 = i13 << 1;
        if (a(i2, i7, i4, i5)) {
            i14 |= 1;
        }
        int i15 = i14 << 1;
        if (a(i2, i9, i4, i5)) {
            i15 |= 1;
        }
        int i16 = i15 << 1;
        return a(i2, i3, i4, i5) ? i16 | 1 : i16;
    }

    private int b(int i2, int i3) {
        int i4 = (a(i2 + (-3), 0, i2, i3) ? 1 : 0) << 1;
        if (a(i2 - 2, 0, i2, i3)) {
            i4 |= 1;
        }
        int i5 = i4 << 1;
        if (a(i2 - 1, 0, i2, i3)) {
            i5 |= 1;
        }
        int i6 = i5 << 1;
        if (a(0, i3 - 4, i2, i3)) {
            i6 |= 1;
        }
        int i7 = i6 << 1;
        if (a(0, i3 - 3, i2, i3)) {
            i7 |= 1;
        }
        int i8 = i7 << 1;
        if (a(0, i3 - 2, i2, i3)) {
            i8 |= 1;
        }
        int i9 = i8 << 1;
        int i10 = i3 - 1;
        if (a(0, i10, i2, i3)) {
            i9 |= 1;
        }
        int i11 = i9 << 1;
        return a(1, i10, i2, i3) ? i11 | 1 : i11;
    }
}
