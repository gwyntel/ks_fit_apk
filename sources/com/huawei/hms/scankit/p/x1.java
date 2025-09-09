package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class x1 extends s3 {
    @Override // com.huawei.hms.scankit.p.s3
    public s a(s sVar, int i2, int i3, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, float f17) throws a {
        return a(sVar, i2, i3, d6.a(f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17), false);
    }

    public s b(s sVar, int i2, int i3, d6 d6Var) throws a {
        if (i2 <= 0 || i3 <= 0) {
            throw a.a();
        }
        s sVar2 = new s(i2, i3);
        int i4 = i2 * 2;
        float[] fArr = new float[i4];
        for (int i5 = 0; i5 < i3; i5++) {
            float f2 = i5 + 0.5f;
            for (int i6 = 0; i6 < i4; i6 += 2) {
                fArr[i6] = (i6 / 2) + 0.5f;
                fArr[i6 + 1] = f2;
            }
            if (r3.f17729p && r3.f17726m) {
                d6Var.b(fArr);
            } else {
                d6Var.a(fArr);
            }
            int iE = sVar.e();
            int iC = sVar.c();
            for (int i7 = 0; i7 < i4; i7 += 2) {
                try {
                    int i8 = (int) fArr[i7];
                    int i9 = (int) fArr[i7 + 1];
                    if (i8 < -1 || i8 > iE || i9 < -1 || i9 > iC) {
                        sVar2.c(i7 / 2, i5);
                    } else if (sVar.b(i8, i9)) {
                        sVar2.c(i7 / 2, i5);
                    }
                } catch (ArrayIndexOutOfBoundsException unused) {
                    throw a.a();
                }
            }
        }
        return sVar2;
    }

    @Override // com.huawei.hms.scankit.p.s3
    public s a(s sVar, int i2, int i3, d6 d6Var, boolean z2) throws a {
        boolean z3 = r3.f17727n;
        if ((z3 && z2) || ((!z3 && !z2) || r3.f17734u)) {
            return b(sVar, i2, i3, d6Var);
        }
        return a(sVar, i2, i3, d6Var);
    }

    public s a(s sVar, int i2, int i3, d6 d6Var) throws a {
        int i4 = 5;
        char c2 = 2;
        int i5 = 0;
        if (i2 > 0 && i3 > 0) {
            s sVar2 = new s(i2, i3);
            int i6 = 0;
            while (i6 < i3) {
                int i7 = i5;
                while (i7 < i2) {
                    float f2 = i7;
                    float f3 = (f2 - 0.2f) + 0.5f;
                    float f4 = i6;
                    float f5 = f4 + 0.5f;
                    float f6 = f2 + 0.2f + 0.5f;
                    float f7 = f2 + 0.5f;
                    float[] fArr = new float[10];
                    fArr[i5] = f3;
                    fArr[1] = f5;
                    fArr[c2] = f6;
                    fArr[3] = f5;
                    fArr[4] = f7;
                    fArr[i4] = (f4 - 0.2f) + 0.5f;
                    fArr[6] = f7;
                    fArr[7] = f4 + 0.2f + 0.5f;
                    fArr[8] = f7;
                    fArr[9] = f5;
                    if (r3.f17729p && r3.f17726m) {
                        d6Var.b(fArr);
                    } else {
                        d6Var.a(fArr);
                    }
                    int iE = sVar.e();
                    int iC = sVar.c();
                    int i8 = i5;
                    int i9 = i8;
                    while (i8 < i4) {
                        int i10 = i8 * 2;
                        try {
                            int i11 = (int) fArr[i10];
                            int i12 = (int) fArr[i10 + 1];
                            if (i11 >= -1 && i11 <= iE && i12 >= -1 && i12 <= iC) {
                                if (sVar.b(i11, i12)) {
                                    i9++;
                                }
                            }
                            i8++;
                            i4 = 5;
                        } catch (ArrayIndexOutOfBoundsException unused) {
                            throw a.a();
                        }
                    }
                    if (i9 >= 3) {
                        sVar2.c(i7, i6);
                    }
                    i7++;
                    i4 = 5;
                    c2 = 2;
                    i5 = 0;
                }
                i6++;
                i4 = 5;
                c2 = 2;
                i5 = 0;
            }
            return sVar2;
        }
        throw a.a();
    }
}
