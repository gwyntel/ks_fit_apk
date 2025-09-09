package com.google.zxing.pdf417.decoder.ec;

/* loaded from: classes3.dex */
final class ModulusPoly {
    private final int[] coefficients;
    private final ModulusGF field;

    ModulusPoly(ModulusGF modulusGF, int[] iArr) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException();
        }
        this.field = modulusGF;
        int length = iArr.length;
        int i2 = 1;
        if (length <= 1 || iArr[0] != 0) {
            this.coefficients = iArr;
            return;
        }
        while (i2 < length && iArr[i2] == 0) {
            i2++;
        }
        if (i2 == length) {
            this.coefficients = new int[]{0};
            return;
        }
        int[] iArr2 = new int[length - i2];
        this.coefficients = iArr2;
        System.arraycopy(iArr, i2, iArr2, 0, iArr2.length);
    }

    ModulusPoly a(ModulusPoly modulusPoly) {
        if (!this.field.equals(modulusPoly.field)) {
            throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        }
        if (e()) {
            return modulusPoly;
        }
        if (modulusPoly.e()) {
            return this;
        }
        int[] iArr = this.coefficients;
        int[] iArr2 = modulusPoly.coefficients;
        if (iArr.length <= iArr2.length) {
            iArr = iArr2;
            iArr2 = iArr;
        }
        int[] iArr3 = new int[iArr.length];
        int length = iArr.length - iArr2.length;
        System.arraycopy(iArr, 0, iArr3, 0, length);
        for (int i2 = length; i2 < iArr.length; i2++) {
            iArr3[i2] = this.field.a(iArr2[i2 - length], iArr[i2]);
        }
        return new ModulusPoly(this.field, iArr3);
    }

    int b(int i2) {
        if (i2 == 0) {
            return c(0);
        }
        if (i2 == 1) {
            int iA = 0;
            for (int i3 : this.coefficients) {
                iA = this.field.a(iA, i3);
            }
            return iA;
        }
        int[] iArr = this.coefficients;
        int iA2 = iArr[0];
        int length = iArr.length;
        for (int i4 = 1; i4 < length; i4++) {
            ModulusGF modulusGF = this.field;
            iA2 = modulusGF.a(modulusGF.i(i2, iA2), this.coefficients[i4]);
        }
        return iA2;
    }

    int c(int i2) {
        return this.coefficients[(r0.length - 1) - i2];
    }

    int d() {
        return this.coefficients.length - 1;
    }

    boolean e() {
        return this.coefficients[0] == 0;
    }

    ModulusPoly f(int i2) {
        if (i2 == 0) {
            return this.field.f();
        }
        if (i2 == 1) {
            return this;
        }
        int length = this.coefficients.length;
        int[] iArr = new int[length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = this.field.i(this.coefficients[i3], i2);
        }
        return new ModulusPoly(this.field, iArr);
    }

    ModulusPoly g(ModulusPoly modulusPoly) {
        if (!this.field.equals(modulusPoly.field)) {
            throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        }
        if (e() || modulusPoly.e()) {
            return this.field.f();
        }
        int[] iArr = this.coefficients;
        int length = iArr.length;
        int[] iArr2 = modulusPoly.coefficients;
        int length2 = iArr2.length;
        int[] iArr3 = new int[(length + length2) - 1];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = iArr[i2];
            for (int i4 = 0; i4 < length2; i4++) {
                int i5 = i2 + i4;
                ModulusGF modulusGF = this.field;
                iArr3[i5] = modulusGF.a(iArr3[i5], modulusGF.i(i3, iArr2[i4]));
            }
        }
        return new ModulusPoly(this.field, iArr3);
    }

    ModulusPoly h(int i2, int i3) {
        if (i2 < 0) {
            throw new IllegalArgumentException();
        }
        if (i3 == 0) {
            return this.field.f();
        }
        int length = this.coefficients.length;
        int[] iArr = new int[i2 + length];
        for (int i4 = 0; i4 < length; i4++) {
            iArr[i4] = this.field.i(this.coefficients[i4], i3);
        }
        return new ModulusPoly(this.field, iArr);
    }

    ModulusPoly i() {
        int length = this.coefficients.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = this.field.j(0, this.coefficients[i2]);
        }
        return new ModulusPoly(this.field, iArr);
    }

    ModulusPoly j(ModulusPoly modulusPoly) {
        if (this.field.equals(modulusPoly.field)) {
            return modulusPoly.e() ? this : a(modulusPoly.i());
        }
        throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(d() * 8);
        for (int iD = d(); iD >= 0; iD--) {
            int iC = c(iD);
            if (iC != 0) {
                if (iC < 0) {
                    sb.append(" - ");
                    iC = -iC;
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (iD == 0 || iC != 1) {
                    sb.append(iC);
                }
                if (iD != 0) {
                    if (iD == 1) {
                        sb.append('x');
                    } else {
                        sb.append("x^");
                        sb.append(iD);
                    }
                }
            }
        }
        return sb.toString();
    }
}
