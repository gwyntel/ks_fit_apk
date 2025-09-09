package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class p3 {

    /* renamed from: a, reason: collision with root package name */
    private final o3 f17662a;

    /* renamed from: b, reason: collision with root package name */
    private final int[] f17663b;

    p3(o3 o3Var, int[] iArr) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException();
        }
        this.f17662a = o3Var;
        int length = iArr.length;
        int i2 = 1;
        if (length <= 1 || iArr[0] != 0) {
            this.f17663b = iArr;
            return;
        }
        while (i2 < length && iArr[i2] == 0) {
            i2++;
        }
        if (i2 == length) {
            this.f17663b = new int[]{0};
            return;
        }
        int i3 = length - i2;
        int[] iArr2 = new int[i3];
        this.f17663b = iArr2;
        System.arraycopy(iArr, i2, iArr2, 0, i3);
    }

    int[] a() {
        return this.f17663b;
    }

    int b() {
        return this.f17663b.length - 1;
    }

    boolean c() {
        return this.f17663b[0] == 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(b() * 8);
        for (int iB = b(); iB >= 0; iB--) {
            int iB2 = b(iB);
            if (iB2 != 0) {
                if (iB2 < 0) {
                    sb.append(" - ");
                    iB2 = -iB2;
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (iB == 0 || iB2 != 1) {
                    int iC = this.f17662a.c(iB2);
                    if (iC == 0) {
                        sb.append('1');
                    } else if (iC == 1) {
                        sb.append('a');
                    } else {
                        sb.append("a^");
                        sb.append(iC);
                    }
                }
                if (iB != 0) {
                    if (iB == 1) {
                        sb.append('x');
                    } else {
                        sb.append("x^");
                        sb.append(iB);
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
            for (int i3 : this.f17663b) {
                iA = o3.a(iA, i3);
            }
            return iA;
        }
        int[] iArr = this.f17663b;
        int iA2 = iArr[0];
        int length = iArr.length;
        for (int i4 = 1; i4 < length; i4++) {
            iA2 = o3.a(this.f17662a.c(i2, iA2), this.f17663b[i4]);
        }
        return iA2;
    }

    int b(int i2) {
        return this.f17663b[(r0.length - 1) - i2];
    }

    p3 c(p3 p3Var) {
        if (!this.f17662a.equals(p3Var.f17662a)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (c() || p3Var.c()) {
            return this.f17662a.d();
        }
        int[] iArr = this.f17663b;
        int length = iArr.length;
        int[] iArr2 = p3Var.f17663b;
        int length2 = iArr2.length;
        int[] iArr3 = new int[(length + length2) - 1];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = iArr[i2];
            for (int i4 = 0; i4 < length2; i4++) {
                int i5 = i2 + i4;
                iArr3[i5] = o3.a(iArr3[i5], this.f17662a.c(i3, iArr2[i4]));
            }
        }
        return new p3(this.f17662a, iArr3);
    }

    p3[] b(p3 p3Var) {
        if (this.f17662a.equals(p3Var.f17662a)) {
            if (!p3Var.c()) {
                p3 p3VarD = this.f17662a.d();
                int iB = this.f17662a.b(p3Var.b(p3Var.b()));
                p3 p3VarA = this;
                while (p3VarA.b() >= p3Var.b() && !p3VarA.c()) {
                    int iB2 = p3VarA.b() - p3Var.b();
                    int iC = this.f17662a.c(p3VarA.b(p3VarA.b()), iB);
                    p3 p3VarA2 = p3Var.a(iB2, iC);
                    p3VarD = p3VarD.a(this.f17662a.b(iB2, iC));
                    p3VarA = p3VarA.a(p3VarA2);
                }
                return new p3[]{p3VarD, p3VarA};
            }
            throw new IllegalArgumentException("Divide by 0");
        }
        throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
    }

    p3 a(p3 p3Var) {
        if (this.f17662a.equals(p3Var.f17662a)) {
            if (c()) {
                return p3Var;
            }
            if (p3Var.c()) {
                return this;
            }
            int[] iArr = this.f17663b;
            int[] iArr2 = p3Var.f17663b;
            if (iArr.length <= iArr2.length) {
                iArr = iArr2;
                iArr2 = iArr;
            }
            int[] iArr3 = new int[iArr.length];
            int length = iArr.length - iArr2.length;
            System.arraycopy(iArr, 0, iArr3, 0, length);
            for (int i2 = length; i2 < iArr.length; i2++) {
                iArr3[i2] = o3.a(iArr2[i2 - length], iArr[i2]);
            }
            return new p3(this.f17662a, iArr3);
        }
        throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
    }

    p3 c(int i2) {
        if (i2 == 0) {
            return this.f17662a.d();
        }
        if (i2 == 1) {
            return this;
        }
        int length = this.f17663b.length;
        int[] iArr = new int[length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = this.f17662a.c(this.f17663b[i3], i2);
        }
        return new p3(this.f17662a, iArr);
    }

    p3 a(int i2, int i3) {
        if (i2 < 0) {
            throw new IllegalArgumentException();
        }
        if (i3 == 0) {
            return this.f17662a.d();
        }
        int length = this.f17663b.length;
        int[] iArr = new int[i2 + length];
        for (int i4 = 0; i4 < length; i4++) {
            iArr[i4] = this.f17662a.c(this.f17663b[i4], i3);
        }
        return new p3(this.f17662a, iArr);
    }
}
