package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.pdf417.PDF417Common;

/* loaded from: classes3.dex */
public final class ModulusGF {
    public static final ModulusGF PDF417_GF = new ModulusGF(PDF417Common.NUMBER_OF_CODEWORDS, 3);
    private final int[] expTable;
    private final int[] logTable;
    private final int modulus;
    private final ModulusPoly one;
    private final ModulusPoly zero;

    private ModulusGF(int i2, int i3) {
        this.modulus = i2;
        this.expTable = new int[i2];
        this.logTable = new int[i2];
        int i4 = 1;
        for (int i5 = 0; i5 < i2; i5++) {
            this.expTable[i5] = i4;
            i4 = (i4 * i3) % i2;
        }
        for (int i6 = 0; i6 < i2 - 1; i6++) {
            this.logTable[this.expTable[i6]] = i6;
        }
        this.zero = new ModulusPoly(this, new int[]{0});
        this.one = new ModulusPoly(this, new int[]{1});
    }

    int a(int i2, int i3) {
        return (i2 + i3) % this.modulus;
    }

    ModulusPoly b(int i2, int i3) {
        if (i2 < 0) {
            throw new IllegalArgumentException();
        }
        if (i3 == 0) {
            return this.zero;
        }
        int[] iArr = new int[i2 + 1];
        iArr[0] = i3;
        return new ModulusPoly(this, iArr);
    }

    int c(int i2) {
        return this.expTable[i2];
    }

    ModulusPoly d() {
        return this.one;
    }

    int e() {
        return this.modulus;
    }

    ModulusPoly f() {
        return this.zero;
    }

    int g(int i2) {
        if (i2 != 0) {
            return this.expTable[(this.modulus - this.logTable[i2]) - 1];
        }
        throw new ArithmeticException();
    }

    int h(int i2) {
        if (i2 != 0) {
            return this.logTable[i2];
        }
        throw new IllegalArgumentException();
    }

    int i(int i2, int i3) {
        if (i2 == 0 || i3 == 0) {
            return 0;
        }
        int[] iArr = this.expTable;
        int[] iArr2 = this.logTable;
        return iArr[(iArr2[i2] + iArr2[i3]) % (this.modulus - 1)];
    }

    int j(int i2, int i3) {
        int i4 = this.modulus;
        return ((i2 + i4) - i3) % i4;
    }
}
