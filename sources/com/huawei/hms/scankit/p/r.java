package com.huawei.hms.scankit.p;

import java.util.Arrays;

/* loaded from: classes4.dex */
public final class r implements Cloneable {

    /* renamed from: a, reason: collision with root package name */
    private int[] f17708a;

    /* renamed from: b, reason: collision with root package name */
    private int[] f17709b;

    /* renamed from: c, reason: collision with root package name */
    private int f17710c;

    public r() {
        this.f17710c = 0;
        this.f17708a = new int[1];
    }

    private void a(int i2) {
        if (i2 > this.f17708a.length * 32) {
            int[] iArrF = f(i2);
            int[] iArr = this.f17708a;
            System.arraycopy(iArr, 0, iArrF, 0, iArr.length);
            this.f17708a = iArrF;
        }
    }

    private int e(int i2) {
        int i3 = 0;
        while (i2 > 0) {
            i2 &= i2 - 1;
            i3++;
        }
        return i3;
    }

    public boolean b(int i2) {
        return ((1 << (i2 & 31)) & this.f17708a[i2 / 32]) != 0;
    }

    public void c() {
        this.f17709b = this.f17708a;
    }

    public int d(int i2) {
        int i3 = this.f17710c;
        if (i2 >= i3) {
            return i3;
        }
        int i4 = i2 / 32;
        if (!w7.a(this.f17708a, i4)) {
            return -1;
        }
        int i5 = (-(1 << (i2 & 31))) & (~this.f17708a[i4]);
        while (i5 == 0) {
            i4++;
            int[] iArr = this.f17708a;
            if (i4 == iArr.length) {
                return this.f17710c;
            }
            if (w7.a(iArr, i4)) {
                i5 = ~this.f17708a[i4];
            }
        }
        int iNumberOfTrailingZeros = (i4 * 32) + Integer.numberOfTrailingZeros(i5);
        int i6 = this.f17710c;
        return iNumberOfTrailingZeros > i6 ? i6 : iNumberOfTrailingZeros;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof r)) {
            return false;
        }
        r rVar = (r) obj;
        return this.f17710c == rVar.f17710c && Arrays.equals(this.f17708a, rVar.f17708a);
    }

    public int f() {
        return (this.f17710c + 7) / 8;
    }

    public void g() {
        this.f17708a = this.f17709b;
    }

    public void h(int i2) {
        int[] iArr = this.f17708a;
        int i3 = i2 / 32;
        iArr[i3] = iArr[i3] - (1 << (i2 & 31));
    }

    public int hashCode() {
        return (this.f17710c * 31) + Arrays.hashCode(this.f17708a);
    }

    public void i() {
        for (int i2 = 0; i2 < this.f17710c - 1; i2++) {
            if (!b(i2) && b(i2 + 1)) {
                g(i2);
            }
        }
    }

    public void j() {
        for (int i2 = 0; i2 < this.f17710c - 1; i2++) {
            if (b(i2) && !b(i2 + 1)) {
                h(i2);
            }
        }
    }

    public String toString() {
        int i2 = this.f17710c;
        StringBuilder sb = new StringBuilder(i2 + (i2 / 8) + 1);
        for (int i3 = 0; i3 < this.f17710c; i3++) {
            if ((i3 & 7) == 0) {
                sb.append(' ');
            }
            sb.append(b(i3) ? 'X' : '.');
        }
        return sb.toString();
    }

    private static int[] f(int i2) {
        return new int[(i2 + 31) / 32];
    }

    public void b(int i2, int i3) {
        this.f17708a[i2 / 32] = i3;
    }

    public int c(int i2) {
        int i3 = this.f17710c;
        if (i2 >= i3) {
            return i3;
        }
        int i4 = i2 / 32;
        if (!w7.a(this.f17708a, i4)) {
            return -1;
        }
        int i5 = (-(1 << (i2 & 31))) & this.f17708a[i4];
        while (i5 == 0) {
            i4++;
            int[] iArr = this.f17708a;
            if (i4 == iArr.length) {
                return this.f17710c;
            }
            if (w7.a(iArr, i4)) {
                i5 = this.f17708a[i4];
            }
        }
        int iNumberOfTrailingZeros = (i4 * 32) + Integer.numberOfTrailingZeros(i5);
        int i6 = this.f17710c;
        return iNumberOfTrailingZeros > i6 ? i6 : iNumberOfTrailingZeros;
    }

    public int e() {
        return this.f17710c;
    }

    public void g(int i2) {
        int[] iArr = this.f17708a;
        int i3 = i2 / 32;
        iArr[i3] = (1 << (i2 & 31)) | iArr[i3];
    }

    public void h() {
        int[] iArr = new int[this.f17708a.length];
        int i2 = (this.f17710c - 1) / 32;
        int i3 = i2 + 1;
        for (int i4 = 0; i4 < i3; i4++) {
            long j2 = this.f17708a[i4];
            long j3 = ((j2 & 1431655765) << 1) | ((j2 >> 1) & 1431655765);
            long j4 = ((j3 & 858993459) << 2) | ((j3 >> 2) & 858993459);
            long j5 = ((j4 & 252645135) << 4) | ((j4 >> 4) & 252645135);
            long j6 = ((j5 & 16711935) << 8) | ((j5 >> 8) & 16711935);
            iArr[i2 - i4] = (int) (((j6 & 65535) << 16) | ((j6 >> 16) & 65535));
        }
        int i5 = this.f17710c;
        int i6 = i3 * 32;
        if (i5 != i6) {
            int i7 = i6 - i5;
            int i8 = iArr[0] >>> i7;
            for (int i9 = 1; i9 < i3; i9++) {
                int i10 = iArr[i9];
                iArr[i9 - 1] = i8 | (i10 << (32 - i7));
                i8 = i10 >>> i7;
            }
            iArr[i2] = i8;
        }
        this.f17708a = iArr;
    }

    public void b(r rVar) {
        if (this.f17710c != rVar.f17710c) {
            throw new IllegalArgumentException("Sizes don't match");
        }
        int i2 = 0;
        while (true) {
            int[] iArr = this.f17708a;
            if (i2 >= iArr.length) {
                return;
            }
            iArr[i2] = iArr[i2] ^ rVar.f17708a[i2];
            i2++;
        }
    }

    public r(int i2) {
        this.f17710c = i2;
        this.f17708a = f(i2);
    }

    public void a() {
        int length = this.f17708a.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.f17708a[i2] = 0;
        }
    }

    r(int[] iArr, int i2) {
        this.f17708a = iArr;
        this.f17710c = i2;
    }

    public boolean a(int i2, int i3, boolean z2, boolean z3) {
        if (i3 < i2 || i2 < 0 || i3 > this.f17710c) {
            throw new IllegalArgumentException();
        }
        if (i3 == i2) {
            return true;
        }
        int i4 = i3 - 1;
        int i5 = i2 / 32;
        int i6 = i4 / 32;
        int i7 = i5;
        int iE = 0;
        while (i7 <= i6) {
            int i8 = (2 << (i7 < i6 ? 31 : i4 & 31)) - (1 << (i7 > i5 ? 0 : i2 & 31));
            if (!z3 && (iE = iE + e(this.f17708a[i7] & i8)) > (i4 - i2) / 10) {
                return false;
            }
            if (z3) {
                int i9 = this.f17708a[i7] & i8;
                if (!z2) {
                    i8 = 0;
                }
                if (i9 != i8) {
                    return false;
                }
            }
            i7++;
        }
        return true;
    }

    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public r clone() {
        return new r((int[]) this.f17708a.clone(), this.f17710c);
    }

    public int[] d() {
        return this.f17708a;
    }

    public void c(int i2, int i3) {
        if (i3 < i2 || i2 < 0 || i3 > this.f17710c) {
            throw new IllegalArgumentException();
        }
        if (i3 == i2) {
            return;
        }
        int i4 = i3 - 1;
        int i5 = i2 / 32;
        int i6 = i4 / 32;
        int i7 = i5;
        while (i7 <= i6) {
            int i8 = 31;
            int i9 = i7 > i5 ? 0 : i2 & 31;
            if (i7 >= i6) {
                i8 = 31 & i4;
            }
            int i10 = (2 << i8) - (1 << i9);
            int[] iArr = this.f17708a;
            iArr[i7] = i10 | iArr[i7];
            i7++;
        }
    }

    public void a(boolean z2) {
        a(this.f17710c + 1);
        if (z2) {
            int[] iArr = this.f17708a;
            int i2 = this.f17710c;
            int i3 = i2 / 32;
            iArr[i3] = (1 << (i2 & 31)) | iArr[i3];
        }
        this.f17710c++;
    }

    public void a(int i2, int i3) {
        if (i3 >= 0 && i3 <= 32) {
            a(this.f17710c + i3);
            while (i3 > 0) {
                boolean z2 = true;
                if (((i2 >> (i3 - 1)) & 1) != 1) {
                    z2 = false;
                }
                a(z2);
                i3--;
            }
            return;
        }
        throw new IllegalArgumentException("Num bits must be between 0 and 32");
    }

    public void a(r rVar) {
        int i2 = rVar.f17710c;
        a(this.f17710c + i2);
        for (int i3 = 0; i3 < i2; i3++) {
            a(rVar.b(i3));
        }
    }

    public void a(int i2, byte[] bArr, int i3, int i4) {
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = 0;
            for (int i7 = 0; i7 < 8; i7++) {
                if (b(i2)) {
                    i6 |= 1 << (7 - i7);
                }
                i2++;
            }
            bArr[i3 + i5] = (byte) i6;
        }
    }
}
