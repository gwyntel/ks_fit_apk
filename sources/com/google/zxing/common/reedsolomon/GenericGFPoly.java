package com.google.zxing.common.reedsolomon;

/* loaded from: classes3.dex */
final class GenericGFPoly {
    private final int[] coefficients;
    private final GenericGF field;

    GenericGFPoly(GenericGF genericGF, int[] iArr) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException();
        }
        this.field = genericGF;
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

    GenericGFPoly a(GenericGFPoly genericGFPoly) {
        if (!this.field.equals(genericGFPoly.field)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (g()) {
            return genericGFPoly;
        }
        if (genericGFPoly.g()) {
            return this;
        }
        int[] iArr = this.coefficients;
        int[] iArr2 = genericGFPoly.coefficients;
        if (iArr.length <= iArr2.length) {
            iArr = iArr2;
            iArr2 = iArr;
        }
        int[] iArr3 = new int[iArr.length];
        int length = iArr.length - iArr2.length;
        System.arraycopy(iArr, 0, iArr3, 0, length);
        for (int i2 = length; i2 < iArr.length; i2++) {
            iArr3[i2] = GenericGF.a(iArr2[i2 - length], iArr[i2]);
        }
        return new GenericGFPoly(this.field, iArr3);
    }

    GenericGFPoly[] b(GenericGFPoly genericGFPoly) {
        if (!this.field.equals(genericGFPoly.field)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (genericGFPoly.g()) {
            throw new IllegalArgumentException("Divide by 0");
        }
        GenericGFPoly genericGFPolyE = this.field.e();
        int iF = this.field.f(genericGFPoly.d(genericGFPoly.f()));
        GenericGFPoly genericGFPolyA = this;
        while (genericGFPolyA.f() >= genericGFPoly.f() && !genericGFPolyA.g()) {
            int iF2 = genericGFPolyA.f() - genericGFPoly.f();
            int iH = this.field.h(genericGFPolyA.d(genericGFPolyA.f()), iF);
            GenericGFPoly genericGFPolyJ = genericGFPoly.j(iF2, iH);
            genericGFPolyE = genericGFPolyE.a(this.field.b(iF2, iH));
            genericGFPolyA = genericGFPolyA.a(genericGFPolyJ);
        }
        return new GenericGFPoly[]{genericGFPolyE, genericGFPolyA};
    }

    int c(int i2) {
        if (i2 == 0) {
            return d(0);
        }
        if (i2 == 1) {
            int iA = 0;
            for (int i3 : this.coefficients) {
                iA = GenericGF.a(iA, i3);
            }
            return iA;
        }
        int[] iArr = this.coefficients;
        int iA2 = iArr[0];
        int length = iArr.length;
        for (int i4 = 1; i4 < length; i4++) {
            iA2 = GenericGF.a(this.field.h(i2, iA2), this.coefficients[i4]);
        }
        return iA2;
    }

    int d(int i2) {
        return this.coefficients[(r0.length - 1) - i2];
    }

    int[] e() {
        return this.coefficients;
    }

    int f() {
        return this.coefficients.length - 1;
    }

    boolean g() {
        return this.coefficients[0] == 0;
    }

    GenericGFPoly h(int i2) {
        if (i2 == 0) {
            return this.field.e();
        }
        if (i2 == 1) {
            return this;
        }
        int length = this.coefficients.length;
        int[] iArr = new int[length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = this.field.h(this.coefficients[i3], i2);
        }
        return new GenericGFPoly(this.field, iArr);
    }

    GenericGFPoly i(GenericGFPoly genericGFPoly) {
        if (!this.field.equals(genericGFPoly.field)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (g() || genericGFPoly.g()) {
            return this.field.e();
        }
        int[] iArr = this.coefficients;
        int length = iArr.length;
        int[] iArr2 = genericGFPoly.coefficients;
        int length2 = iArr2.length;
        int[] iArr3 = new int[(length + length2) - 1];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = iArr[i2];
            for (int i4 = 0; i4 < length2; i4++) {
                int i5 = i2 + i4;
                iArr3[i5] = GenericGF.a(iArr3[i5], this.field.h(i3, iArr2[i4]));
            }
        }
        return new GenericGFPoly(this.field, iArr3);
    }

    GenericGFPoly j(int i2, int i3) {
        if (i2 < 0) {
            throw new IllegalArgumentException();
        }
        if (i3 == 0) {
            return this.field.e();
        }
        int length = this.coefficients.length;
        int[] iArr = new int[i2 + length];
        for (int i4 = 0; i4 < length; i4++) {
            iArr[i4] = this.field.h(this.coefficients[i4], i3);
        }
        return new GenericGFPoly(this.field, iArr);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(f() * 8);
        for (int iF = f(); iF >= 0; iF--) {
            int iD = d(iF);
            if (iD != 0) {
                if (iD < 0) {
                    sb.append(" - ");
                    iD = -iD;
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (iF == 0 || iD != 1) {
                    int iG = this.field.g(iD);
                    if (iG == 0) {
                        sb.append('1');
                    } else if (iG == 1) {
                        sb.append('a');
                    } else {
                        sb.append("a^");
                        sb.append(iG);
                    }
                }
                if (iF != 0) {
                    if (iF == 1) {
                        sb.append('x');
                    } else {
                        sb.append("x^");
                        sb.append(iF);
                    }
                }
            }
        }
        return sb.toString();
    }
}
