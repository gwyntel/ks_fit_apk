package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.util.LoadOpencvJNIUtil;
import java.util.Map;

/* loaded from: classes4.dex */
public class g2 {

    /* renamed from: a, reason: collision with root package name */
    private final s f17292a;

    /* renamed from: b, reason: collision with root package name */
    private v6 f17293b;

    public g2(s sVar) {
        this.f17292a = sVar;
    }

    private static d6 a(u6 u6Var, u6 u6Var2, u6 u6Var3, u6 u6Var4, u6 u6Var5, int i2) {
        float fB;
        float fC;
        float fB2;
        float fC2;
        float f2 = i2 - 3.5f;
        if (u6Var4 != null) {
            float fB3 = u6Var4.b();
            fB = fB3;
            fC = u6Var4.c();
            fB2 = u6Var5.b();
            fC2 = u6Var5.c();
        } else {
            fB = (u6Var2.b() - u6Var.b()) + u6Var3.b();
            fC = (u6Var2.c() - u6Var.c()) + u6Var3.c();
            fB2 = f2;
            fC2 = fB2;
        }
        return d6.a(3.5f, 3.5f, f2, 3.5f, fB2, fC2, 3.5f, f2, u6Var.b(), u6Var.c(), u6Var2.b(), u6Var2.c(), fB, fC, u6Var3.b(), u6Var3.c());
    }

    private float b(int i2, int i3, int i4, int i5) {
        float fE;
        float fC;
        float fA = a(i2, i3, i4, i5);
        int iE = i2 - (i4 - i2);
        if (i2 == iE) {
            return Float.NaN;
        }
        int iC = 0;
        if (iE < 0) {
            fE = i2 / (i2 - iE);
            iE = 0;
        } else if (iE >= this.f17292a.e()) {
            fE = ((this.f17292a.e() - 1) - i2) / (iE - i2);
            iE = this.f17292a.e() - 1;
        } else {
            fE = 1.0f;
        }
        float f2 = i3;
        int i6 = (int) (f2 - ((i5 - i3) * fE));
        if (i3 == i6) {
            return Float.NaN;
        }
        if (i6 < 0) {
            fC = f2 / (i3 - i6);
        } else if (i6 >= this.f17292a.c()) {
            fC = ((this.f17292a.c() - 1) - i3) / (i6 - i3);
            iC = this.f17292a.c() - 1;
        } else {
            iC = i6;
            fC = 1.0f;
        }
        float fA2 = a(i2, i3, (int) (i2 + ((iE - i2) * fC)), iC);
        if (Math.max(fA, fA2) > Math.min(fA, fA2) * 1.5d) {
            return Float.NaN;
        }
        return (fA + fA2) - 1.0f;
    }

    private static s a(s sVar, d6 d6Var, int i2) throws a {
        return s3.a().a(sVar, i2, i2, d6Var, true);
    }

    private static int a(u6 u6Var, u6 u6Var2, u6 u6Var3, float f2) throws a {
        int iA;
        int i2;
        try {
            iA = ((s4.a(u6.a(u6Var, u6Var2) / f2) + s4.a(u6.a(u6Var, u6Var3) / f2)) / 2) + 7;
            i2 = iA & 3;
        } catch (a unused) {
            int iA2 = (((int) (u6.a(u6Var, u6Var2) / f2)) + ((int) (u6.a(u6Var, u6Var3) / f2))) / 2;
            iA = iA2 + 7;
            int i3 = iA & 3;
            if (i3 != 0) {
                if (i3 != 2) {
                    return i3 != 3 ? iA : iA2 + 9;
                }
            }
        }
        if (i2 != 0) {
            if (i2 != 2) {
                if (i2 != 3) {
                    return iA;
                }
                throw a.a();
            }
            return iA - 1;
        }
        return iA + 1;
    }

    public final j2 a(Map<l1, ?> map) throws a {
        this.f17293b = map == null ? null : (v6) map.get(l1.f17496j);
        return a(new i3(this.f17292a, this.f17293b).b());
    }

    protected final j2 a(k3 k3Var) throws a {
        e3 e3VarB = k3Var.b();
        e3 e3VarC = k3Var.c();
        e3 e3VarA = k3Var.a();
        try {
            float fA = a(e3VarB, e3VarC, e3VarA);
            if (fA >= 1.0f) {
                return a(e3VarB, e3VarC, e3VarA, fA);
            }
            throw a.a();
        } catch (a unused) {
            float fE = ((e3VarB.e() + e3VarC.e()) + e3VarA.e()) / 3.0f;
            if (fE >= 1.0f) {
                return a(e3VarB, e3VarC, e3VarA, fE);
            }
            throw a.a();
        }
    }

    private j2 a(e3 e3Var, e3 e3Var2, e3 e3Var3, float f2) throws a {
        d[] dVarArr;
        d[] dVarArr2;
        d[] dVarArr3;
        int i2;
        int i3;
        char c2;
        int i4;
        int i5;
        int iA;
        u6[] u6VarArr;
        int iA2 = a((u6) e3Var, (u6) e3Var2, (u6) e3Var3, f2);
        r3.f17736w.push(Integer.valueOf(iA2));
        b8 b8VarB = b8.b(iA2);
        if (r3.f17732s && r3.f17716c) {
            return a(e3Var, e3Var2, e3Var3, f2, iA2);
        }
        int iD = b8VarB.d() - 7;
        int length = b8VarB.c().length;
        int i6 = length * length;
        d[] dVarArr4 = new d[i6];
        d[] dVarArr5 = new d[i6];
        d[] dVarArr6 = new d[2];
        if (b8VarB.c().length > 0) {
            dVarArr = dVarArr6;
            dVarArr2 = dVarArr5;
            dVarArr3 = dVarArr4;
            i2 = length;
            i3 = iA2;
            c2 = 2;
            i4 = 4;
            i5 = 3;
            iA = a(e3Var, e3Var2, e3Var3, f2, iA2, b8VarB, dVarArr3, dVarArr2, i2, iD, dVarArr);
        } else {
            dVarArr = dVarArr6;
            dVarArr2 = dVarArr5;
            dVarArr3 = dVarArr4;
            i2 = length;
            i3 = iA2;
            c2 = 2;
            i4 = 4;
            i5 = 3;
            iA = 0;
        }
        d dVar = dVarArr[0];
        d6 d6VarA = a(e3Var, e3Var2, e3Var3, dVar, dVarArr[1], i3);
        if (r3.f17729p && r3.f17726m) {
            a(d6VarA, i2, i3, e3Var, e3Var2, e3Var3, dVarArr3, iA, dVarArr2);
        }
        int i7 = i3;
        s sVarA = a(this.f17292a, d6VarA, i7);
        if (dVar == null) {
            u6VarArr = new u6[i5];
            u6VarArr[0] = e3Var3;
            u6VarArr[1] = e3Var;
            u6VarArr[c2] = e3Var2;
        } else {
            u6VarArr = new u6[i4];
            u6VarArr[0] = e3Var3;
            u6VarArr[1] = e3Var;
            u6VarArr[c2] = e3Var2;
            u6VarArr[i5] = dVar;
        }
        float f3 = i7;
        float[] fArr = new float[8];
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        fArr[c2] = f3;
        fArr[i5] = 0.0f;
        fArr[i4] = f3;
        fArr[5] = f3;
        fArr[6] = 0.0f;
        fArr[7] = f3;
        d6VarA.a(fArr);
        u6 u6Var = new u6(fArr[0], fArr[1], e3Var.d());
        u6 u6Var2 = new u6(fArr[c2], fArr[i5], e3Var2.d());
        u6 u6Var3 = new u6(fArr[i4], fArr[5], e3Var3.d());
        u6 u6VarA = a(new u6(fArr[6], fArr[7]));
        u6 u6VarA2 = a(u6Var);
        u6 u6VarA3 = a(u6Var2);
        u6 u6VarA4 = a(u6Var3);
        u6[] u6VarArr2 = new u6[i4];
        u6VarArr2[0] = u6VarA;
        u6VarArr2[1] = u6VarA2;
        u6VarArr2[c2] = u6VarA3;
        u6VarArr2[i5] = u6VarA4;
        return new j2(sVarA, u6VarArr, u6VarArr2, f2);
    }

    private int a(e3 e3Var, e3 e3Var2, e3 e3Var3, float f2, int i2, b8 b8Var, d[] dVarArr, d[] dVarArr2, int i3, int i4, d[] dVarArr3) {
        int i5;
        int i6;
        float fB = (e3Var2.b() - e3Var.b()) + e3Var3.b();
        float fC = (e3Var2.c() - e3Var.c()) + e3Var3.c();
        float f3 = i4;
        float f4 = 3.0f / f3;
        float f5 = 1.0f - f4;
        int iB = (int) (e3Var.b() + ((fB - e3Var.b()) * f5));
        int iC = (int) (e3Var.c() + (f5 * (fC - e3Var.c())));
        if (r3.f17735v[0] && i4 == 22) {
            i6 = 8;
            i5 = 2;
        } else {
            i5 = 4;
            i6 = 16;
        }
        for (int i7 = i5; i7 <= i6; i7 <<= 1) {
            try {
                dVarArr3[0] = a(f2, iB, iC, i7);
                break;
            } catch (a unused) {
            }
        }
        float f6 = i2 - 6.5f;
        dVarArr3[1] = new d(f6, f6, e3Var3.e());
        d dVar = dVarArr3[0];
        if (dVar != null && s4.a(iB, iC, dVar.b(), dVarArr3[0].c()) > f2 * 4.0f) {
            dVarArr3[0] = null;
        }
        if (dVarArr3[0] == null && i3 > 2) {
            int i8 = i3 - 2;
            dVarArr3[1] = new d(b8Var.c()[i8] + 0.5f, f6, e3Var3.e());
            int iC2 = (int) (e3Var3.c() - (f4 * (e3Var3.c() - e3Var.c())));
            int iB2 = (int) ((((b8Var.c()[i8] - 3.0f) / f3) * (e3Var2.b() - e3Var.b())) + e3Var3.b());
            while (i5 <= i6) {
                try {
                    dVarArr3[0] = a(f2, iB2, iC2, i5);
                    break;
                } catch (a unused2) {
                    i5 <<= 1;
                }
            }
            d dVar2 = dVarArr3[0];
            if (dVar2 != null && s4.a(iB2, iC2, dVar2.b(), dVarArr3[0].c()) > f2 * 4.0f) {
                dVarArr3[0] = null;
            }
        }
        if (r3.f17729p && r3.f17726m) {
            return a(b8Var, i4, e3Var2, e3Var, e3Var3, f2, i3, 0, dVarArr, dVarArr2);
        }
        return 0;
    }

    private int a(b8 b8Var, int i2, e3 e3Var, e3 e3Var2, e3 e3Var3, float f2, int i3, int i4, d[] dVarArr, d[] dVarArr2) {
        int i5;
        int i6;
        int i7;
        float f3;
        int i8 = i3;
        int i9 = i4;
        int i10 = 0;
        while (i10 < i8) {
            if (i10 == 0) {
                i5 = i8 - 1;
                i6 = 1;
            } else {
                i5 = i8;
                i6 = 0;
            }
            int i11 = i10 != i8 + (-1) ? i6 : 1;
            float f4 = 3.0f;
            float f5 = i2;
            float fB = (((b8Var.c()[i10] - 3.0f) * (e3Var.b() - e3Var2.b())) / f5) + e3Var2.b();
            float fC = (((b8Var.c()[i10] - 3.0f) * (e3Var.c() - e3Var2.c())) / f5) + e3Var2.c();
            while (i11 < i5) {
                int iC = (int) (fC - (((b8Var.c()[i11] - f4) * (e3Var2.c() - e3Var3.c())) / f5));
                int iB = (int) (fB - (((b8Var.c()[i11] - f4) * (e3Var2.b() - e3Var3.b())) / f5));
                int i12 = 4;
                int i13 = 4;
                while (true) {
                    if (i13 > i12) {
                        i7 = i5;
                        f3 = fB;
                        break;
                    }
                    int i14 = (i10 * i8) + i11;
                    try {
                        dVarArr[i14] = a(f2, iB, iC, i13);
                        i7 = i5;
                        try {
                            f3 = fB;
                            try {
                                dVarArr2[i14] = new d(b8Var.c()[i10] + 0.5f, b8Var.c()[i11] + 0.5f, e3Var3.e());
                                i9++;
                                break;
                            } catch (a unused) {
                                continue;
                            }
                        } catch (a unused2) {
                            f3 = fB;
                            i13 <<= 1;
                            i8 = i3;
                            fB = f3;
                            i5 = i7;
                            i12 = 4;
                        }
                    } catch (a unused3) {
                        i7 = i5;
                    }
                    i13 <<= 1;
                    i8 = i3;
                    fB = f3;
                    i5 = i7;
                    i12 = 4;
                }
                i11++;
                i8 = i3;
                fB = f3;
                i5 = i7;
                f4 = 3.0f;
            }
            i10++;
            i8 = i3;
        }
        return i9;
    }

    private void a(d6 d6Var, int i2, int i3, e3 e3Var, e3 e3Var2, e3 e3Var3, d[] dVarArr, int i4, d[] dVarArr2) {
        int i5 = i4 + 3;
        int i6 = i5 * 2;
        float[] fArr = new float[i6];
        float[] fArr2 = new float[i6];
        fArr[0] = e3Var.b();
        fArr[1] = e3Var.c();
        fArr[2] = e3Var2.b();
        fArr[3] = e3Var2.c();
        fArr[4] = e3Var3.b();
        fArr[5] = e3Var3.c();
        fArr2[0] = 3.5f;
        fArr2[1] = 3.5f;
        float f2 = i3 - 3.5f;
        fArr2[2] = f2;
        fArr2[3] = 3.5f;
        fArr2[4] = 3.5f;
        fArr2[5] = f2;
        int i7 = 6;
        int i8 = 6;
        for (int i9 = 0; i9 < i2 * i2; i9++) {
            d dVar = dVarArr[i9];
            if (dVar != null) {
                int i10 = i8 + 1;
                fArr[i8] = dVar.b();
                i8 += 2;
                fArr[i10] = dVarArr[i9].c();
                int i11 = i7 + 1;
                fArr2[i7] = dVarArr2[i9].b();
                i7 += 2;
                fArr2[i11] = dVarArr2[i9].c();
            }
        }
        float[] fArrQuadFitting = LoadOpencvJNIUtil.QuadFitting(fArr2, i5, fArr);
        if (fArrQuadFitting.length != 0) {
            d6Var.a(fArrQuadFitting[0], fArrQuadFitting[1], fArrQuadFitting[2], fArrQuadFitting[3], fArrQuadFitting[4], fArrQuadFitting[5], fArrQuadFitting[6], fArrQuadFitting[7], fArrQuadFitting[8], fArrQuadFitting[9], fArrQuadFitting[10], fArrQuadFitting[11], fArrQuadFitting[12], fArrQuadFitting[13]);
        }
    }

    private j2 a(e3 e3Var, e3 e3Var2, e3 e3Var3, float f2, int i2) {
        s sVar = new s(i2, i2);
        float f3 = i2;
        float fC = this.f17292a.c() / f3;
        for (int i3 = 0; i3 < i2; i3++) {
            for (int i4 = 0; i4 < i2; i4++) {
                double d2 = fC * 0.5d;
                int i5 = (int) ((i4 * fC) + d2);
                int i6 = (int) ((i3 * fC) + d2);
                if (i5 >= -1 && i5 <= this.f17292a.e() && i6 >= -1 && i6 <= this.f17292a.c()) {
                    if (this.f17292a.b(i5, i6)) {
                        sVar.c(i4, i3);
                    }
                } else {
                    sVar.c(i4, i3);
                }
            }
        }
        u6[] u6VarArr = {e3Var3, e3Var, e3Var2};
        float[] fArr = {0.0f, 0.0f, f3, 0.0f, f3, f3, 0.0f, f3};
        a(e3Var, e3Var2, e3Var3, null, null, i2).a(fArr);
        return new j2(sVar, u6VarArr, new u6[]{a(new u6(fArr[6], fArr[7])), a(new u6(fArr[0], fArr[1])), a(new u6(fArr[2], fArr[3])), a(new u6(fArr[4], fArr[5]))}, f2);
    }

    protected final float a(u6 u6Var, u6 u6Var2, u6 u6Var3) {
        return (a(u6Var, u6Var2) + a(u6Var, u6Var3)) / 2.0f;
    }

    private float a(u6 u6Var, u6 u6Var2) {
        float fB = b((int) u6Var.b(), (int) u6Var.c(), (int) u6Var2.b(), (int) u6Var2.c());
        float fB2 = b((int) u6Var2.b(), (int) u6Var2.c(), (int) u6Var.b(), (int) u6Var.c());
        return Float.isNaN(fB) ? fB2 / 7.0f : Float.isNaN(fB2) ? fB / 7.0f : (fB + fB2) / 14.0f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0086, code lost:
    
        if (r15 != r0) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x008e, code lost:
    
        return com.huawei.hms.scankit.p.s4.a(r19, r6, r1, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x008f, code lost:
    
        return Float.NaN;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float a(int r18, int r19, int r20, int r21) {
        /*
            r17 = this;
            int r0 = r21 - r19
            int r0 = java.lang.Math.abs(r0)
            int r1 = r20 - r18
            int r1 = java.lang.Math.abs(r1)
            r3 = 1
            if (r0 <= r1) goto L11
            r0 = r3
            goto L12
        L11:
            r0 = 0
        L12:
            if (r0 == 0) goto L1d
            r4 = r18
            r1 = r19
            r6 = r20
            r5 = r21
            goto L25
        L1d:
            r1 = r18
            r4 = r19
            r5 = r20
            r6 = r21
        L25:
            int r7 = r5 - r1
            int r7 = java.lang.Math.abs(r7)
            int r8 = r6 - r4
            int r8 = java.lang.Math.abs(r8)
            int r9 = -r7
            r10 = 2
            int r9 = r9 / r10
            r11 = -1
            if (r1 >= r5) goto L39
            r12 = r3
            goto L3a
        L39:
            r12 = r11
        L3a:
            if (r4 >= r6) goto L3d
            r11 = r3
        L3d:
            int r5 = r5 + r12
            r13 = r1
            r14 = r4
            r15 = 0
        L41:
            if (r13 == r5) goto L81
            if (r0 == 0) goto L47
            r2 = r14
            goto L48
        L47:
            r2 = r13
        L48:
            if (r0 == 0) goto L4c
            r10 = r13
            goto L4d
        L4c:
            r10 = r14
        L4d:
            if (r15 != r3) goto L57
            r16 = r0
            r0 = r3
            r19 = r5
            r3 = r17
            goto L5e
        L57:
            r3 = r17
            r16 = r0
            r19 = r5
            r0 = 0
        L5e:
            com.huawei.hms.scankit.p.s r5 = r3.f17292a
            boolean r2 = r5.b(r2, r10)
            if (r0 != r2) goto L70
            r0 = 2
            if (r15 != r0) goto L6e
            float r0 = com.huawei.hms.scankit.p.s4.a(r13, r14, r1, r4)
            return r0
        L6e:
            int r15 = r15 + 1
        L70:
            int r9 = r9 + r8
            if (r9 <= 0) goto L79
            if (r14 != r6) goto L77
            r0 = 2
            goto L86
        L77:
            int r14 = r14 + r11
            int r9 = r9 - r7
        L79:
            int r13 = r13 + r12
            r5 = r19
            r0 = r16
            r3 = 1
            r10 = 2
            goto L41
        L81:
            r3 = r17
            r19 = r5
            r0 = r10
        L86:
            if (r15 != r0) goto L8f
            r5 = r19
            float r0 = com.huawei.hms.scankit.p.s4.a(r5, r6, r1, r4)
            return r0
        L8f:
            r0 = 2143289344(0x7fc00000, float:NaN)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.g2.a(int, int, int, int):float");
    }

    protected final d a(float f2, int i2, int i3, float f3) throws a {
        int i4 = (int) (f3 * f2);
        int iMax = Math.max(0, i2 - i4);
        int iMin = Math.min(this.f17292a.e() - 1, i2 + i4) - iMax;
        float f4 = 3.0f * f2;
        if (iMin >= f4) {
            int iMax2 = Math.max(0, i3 - i4);
            int iMin2 = Math.min(this.f17292a.c() - 1, i3 + i4) - iMax2;
            if (iMin2 >= f4) {
                return new e(this.f17292a, iMax, iMax2, iMin, iMin2, f2, this.f17293b).a();
            }
            throw a.a();
        }
        throw a.a();
    }

    private u6 a(u6 u6Var) {
        float fB = u6Var.b();
        float fC = u6Var.c();
        int iE = this.f17292a.e() - 1;
        int iC = this.f17292a.c() - 1;
        if (fB < 0.0f) {
            fB = 0.0f;
        }
        float f2 = iE;
        if (fB > f2) {
            fB = f2;
        }
        if (fC < 0.0f) {
            fC = 0.0f;
        }
        float f3 = iC;
        if (fC > f3) {
            fC = f3;
        }
        return new u6(fB, fC, u6Var.d());
    }
}
