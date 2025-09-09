package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class x4 {

    /* renamed from: a, reason: collision with root package name */
    private final w4 f17987a;

    /* renamed from: b, reason: collision with root package name */
    private final int[] f17988b;

    x4(w4 w4Var, int[] iArr) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException();
        }
        this.f17987a = w4Var;
        int length = iArr.length;
        int i2 = 1;
        if (length <= 1 || iArr[0] != 0) {
            this.f17988b = iArr;
            return;
        }
        while (i2 < length && iArr[i2] == 0) {
            i2++;
        }
        if (i2 == length) {
            this.f17988b = new int[]{0};
            return;
        }
        int i3 = length - i2;
        int[] iArr2 = new int[i3];
        this.f17988b = iArr2;
        System.arraycopy(iArr, i2, iArr2, 0, i3);
    }

    int a() {
        return this.f17988b.length - 1;
    }

    boolean b() {
        return this.f17988b[0] == 0;
    }

    x4 c(x4 x4Var) {
        if (this.f17987a.equals(x4Var.f17987a)) {
            return x4Var.b() ? this : a(x4Var.c());
        }
        throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(a() * 8);
        for (int iA = a(); iA >= 0; iA--) {
            int iB = b(iA);
            if (iB != 0) {
                if (iB < 0) {
                    sb.append(" - ");
                    iB = -iB;
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (iA == 0 || iB != 1) {
                    sb.append(iB);
                }
                if (iA != 0) {
                    if (iA == 1) {
                        sb.append('x');
                    } else {
                        sb.append("x^");
                        sb.append(iA);
                    }
                }
            }
        }
        return sb.toString();
    }

    int a(int i2) {
        if (i2 == 0) {
            return b(0);
        }
        if (i2 == 1) {
            int iA = 0;
            for (int i3 : this.f17988b) {
                iA = this.f17987a.a(iA, i3);
            }
            return iA;
        }
        int[] iArr = this.f17988b;
        int iA2 = iArr[0];
        int length = iArr.length;
        for (int i4 = 1; i4 < length; i4++) {
            w4 w4Var = this.f17987a;
            iA2 = w4Var.a(w4Var.c(i2, iA2), this.f17988b[i4]);
        }
        return iA2;
    }

    int b(int i2) {
        return this.f17988b[(r0.length - 1) - i2];
    }

    x4 b(x4 x4Var) {
        if (this.f17987a.equals(x4Var.f17987a)) {
            if (!b() && !x4Var.b()) {
                int[] iArr = this.f17988b;
                int length = iArr.length;
                int[] iArr2 = x4Var.f17988b;
                int length2 = iArr2.length;
                int[] iArr3 = new int[(length + length2) - 1];
                for (int i2 = 0; i2 < length; i2++) {
                    int i3 = iArr[i2];
                    for (int i4 = 0; i4 < length2; i4++) {
                        int i5 = i2 + i4;
                        w4 w4Var = this.f17987a;
                        iArr3[i5] = w4Var.a(iArr3[i5], w4Var.c(i3, iArr2[i4]));
                    }
                }
                return new x4(this.f17987a, iArr3);
            }
            return this.f17987a.c();
        }
        throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
    }

    x4 c() {
        int length = this.f17988b.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = this.f17987a.d(0, this.f17988b[i2]);
        }
        return new x4(this.f17987a, iArr);
    }

    x4 a(x4 x4Var) {
        if (this.f17987a.equals(x4Var.f17987a)) {
            if (b()) {
                return x4Var;
            }
            if (x4Var.b()) {
                return this;
            }
            int[] iArr = this.f17988b;
            int[] iArr2 = x4Var.f17988b;
            if (iArr.length <= iArr2.length) {
                iArr = iArr2;
                iArr2 = iArr;
            }
            int[] iArr3 = new int[iArr.length];
            int length = iArr.length - iArr2.length;
            System.arraycopy(iArr, 0, iArr3, 0, length);
            for (int i2 = length; i2 < iArr.length; i2++) {
                iArr3[i2] = this.f17987a.a(iArr2[i2 - length], iArr[i2]);
            }
            return new x4(this.f17987a, iArr3);
        }
        throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
    }

    x4 c(int i2) {
        if (i2 == 0) {
            return this.f17987a.c();
        }
        if (i2 == 1) {
            return this;
        }
        int length = this.f17988b.length;
        int[] iArr = new int[length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = this.f17987a.c(this.f17988b[i3], i2);
        }
        return new x4(this.f17987a, iArr);
    }

    x4 a(int i2, int i3) {
        if (i2 < 0) {
            throw new IllegalArgumentException();
        }
        if (i3 == 0) {
            return this.f17987a.c();
        }
        int length = this.f17988b.length;
        int[] iArr = new int[i2 + length];
        for (int i4 = 0; i4 < length; i4++) {
            iArr[i4] = this.f17987a.c(this.f17988b[i4], i3);
        }
        return new x4(this.f17987a, iArr);
    }
}
