package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.util.LoadOpencvJNIUtil;
import java.util.List;

/* loaded from: classes4.dex */
public class k2 {

    /* renamed from: a, reason: collision with root package name */
    private static d5 f17474a;

    /* renamed from: b, reason: collision with root package name */
    private static p f17475b;

    public static List<i2> a(boolean z2, p pVar, int i2, boolean z3) {
        int iE = pVar.e();
        int iC = pVar.c();
        byte[] bArrB = pVar.a().c().b();
        d5 d5Var = new d5();
        f17474a = d5Var;
        d5Var.a(z2, bArrB, iC, iE, i2, z3);
        return f17474a.f17108a;
    }

    public static boolean a(boolean z2, p pVar, i2 i2Var) throws a {
        float fI;
        int iE = pVar.e();
        int iC = pVar.c();
        float[] fArr = {i2Var.j(), i2Var.k(), i2Var.f(), i2Var.c()};
        if (z2) {
            i2Var.f17371n = Math.max(i2Var.m(), i2Var.l());
            i2Var.f17372o = Math.min(i2Var.m(), i2Var.l());
            fI = i2Var.i();
            if (i2Var.g() == 11.0f || i2Var.g() == 0.0f) {
                fI = 0.0f;
            }
            i2Var.f17379v = Math.max(fArr[2], fArr[3]);
            i2Var.f17375r = (int) Math.max(fArr[0] - (fArr[2] * 0.5d), 0.0d);
            i2Var.f17376s = (int) Math.max(fArr[1] - (fArr[3] * 0.5d), 0.0d);
        } else {
            fI = i2Var.i();
            i2Var.f17379v = Math.max(fArr[2], fArr[3]);
            i2Var.f17375r = (int) i2Var.d();
            i2Var.f17376s = (int) i2Var.e();
        }
        i2Var.f17373p = Math.min(iE - i2Var.f17375r, (int) fArr[2]);
        int iMin = Math.min(iC - i2Var.f17376s, (int) fArr[3]);
        i2Var.f17374q = iMin;
        int i2 = i2Var.f17373p;
        if (i2 > 0 && iMin > 0) {
            p pVarA = pVar.a(i2Var.f17375r, i2Var.f17376s, i2, iMin);
            f17475b = pVarA;
            a(pVarA, fI, i2Var, fArr);
            return true;
        }
        throw a.a("crop_w <= 0 || crop_h <= 0");
    }

    private static void a(p pVar, float f2, i2 i2Var, float[] fArr) {
        byte[] bArrB;
        float fMin;
        float fMax;
        float radians = (float) Math.toRadians(f2);
        double d2 = radians;
        int iAbs = (int) ((i2Var.f17373p * Math.abs(Math.sin(d2))) + (i2Var.f17374q * Math.abs(Math.cos(d2))));
        int iAbs2 = (int) ((i2Var.f17374q * Math.abs(Math.sin(d2))) + (i2Var.f17373p * Math.abs(Math.cos(d2))));
        float[] fArr2 = i2Var.f17370m;
        fArr2[0] = iAbs2 * 0.5f;
        fArr2[1] = iAbs * 0.5f;
        fArr2[2] = (iAbs2 - i2Var.f17373p) * 0.5f;
        fArr2[3] = (iAbs - i2Var.f17374q) * 0.5f;
        fArr2[4] = radians;
        if (!r3.f17715b) {
            bArrB = LoadOpencvJNIUtil.removeMoirePattern(pVar.a().c().b(), i2Var.f17374q, i2Var.f17373p);
        } else {
            bArrB = pVar.a().c().b();
        }
        byte[] bArr = bArrB;
        if (f2 == 0.0f) {
            i2Var.f17377t = 0;
            i2Var.f17378u = 0;
            int i2 = i2Var.f17373p;
            int i3 = i2Var.f17374q;
            i2Var.f17369l = new p(new q3(new e6(bArr, i2, i3, 0, 0, i2, i3, false)));
            return;
        }
        p pVar2 = new p(new q3(new e6(LoadOpencvJNIUtil.imageRotate(bArr, i2Var.f17374q, i2Var.f17373p, iAbs, iAbs2, f2, 1.0d), iAbs2, iAbs, 0, 0, iAbs2, iAbs, false)));
        if ((i2Var.g() == 3.0f || i2Var.g() == 4.0f) && pVar2.c() > pVar2.e()) {
            fMin = Math.min(fArr[2], fArr[3]);
            fMax = Math.max(fArr[2], fArr[3]);
        } else {
            fMin = Math.max(fArr[2], fArr[3]);
            fMax = Math.min(fArr[2], fArr[3]);
        }
        i2Var.f17377t = (int) Math.max((iAbs2 * 0.5d) - (fMin * 0.5d), 0.0d);
        i2Var.f17378u = (int) Math.max((iAbs * 0.5d) - (fMax * 0.5d), 0.0d);
        i2Var.f17369l = pVar2.a(i2Var.f17377t, i2Var.f17378u, Math.min(iAbs2 - i2Var.f17377t, (int) fMin), Math.min(iAbs - i2Var.f17378u, (int) fMax));
    }

    public static void a(s sVar, s6 s6Var, float f2, i2 i2Var) {
        int iC;
        int iC2;
        u6[] u6VarArrJ = s6Var.j();
        float fMin = Math.min(u6VarArrJ[0].b(), u6VarArrJ[1].b());
        float fMax = Math.max(u6VarArrJ[0].b(), u6VarArrJ[1].b());
        float fC = u6VarArrJ[0].c();
        if (fMax > sVar.e() - 1) {
            fMax = sVar.e() - 1;
        }
        float f3 = fMax;
        float fC2 = fC > ((float) (sVar.c() - 1)) ? sVar.c() - 1 : fC;
        int iC3 = sVar.c();
        try {
            int[] iArrA = a(sVar, u6VarArrJ, fMin, f3, fC2, iC3, new int[iC3]);
            iC = iArrA[0];
            iC2 = iArrA[1];
        } catch (IndexOutOfBoundsException unused) {
            iC = (int) u6VarArrJ[0].c();
            iC2 = (int) u6VarArrJ[0].c();
        }
        float f4 = iC;
        float f5 = iC2;
        u6[] u6VarArr = {new u6(fMin, f4), new u6(f3, f4), new u6(f3, f5), new u6(fMin, f5)};
        if (i2Var != null) {
            a(u6VarArr, f2, i2Var);
        }
        s6Var.a();
        s6Var.a(u6VarArr);
    }

    private static int[] a(s sVar, u6[] u6VarArr, float f2, float f3, float f4, int i2, int[] iArr) {
        int i3;
        int iC;
        int iC2;
        int i4 = (int) f2;
        int i5 = i4;
        int i6 = 0;
        while (true) {
            i3 = ((int) f3) - 1;
            if (i5 >= i3) {
                break;
            }
            int i7 = (int) f4;
            boolean zB = sVar.b(i5, i7);
            i5++;
            if (sVar.b(i5, i7) ^ zB) {
                i6++;
            }
        }
        int i8 = 0;
        for (int i9 = 0; i9 < i2; i9++) {
            int i10 = i4;
            int i11 = 0;
            while (i10 < i3) {
                boolean zB2 = sVar.b(i10, i9);
                i10++;
                if (zB2 ^ sVar.b(i10, i9)) {
                    i11++;
                }
            }
            float f5 = i6;
            if (i11 > 1.5f * f5) {
                i11 = 0;
            }
            if (i11 < f5 * 0.5f) {
                i11 = 0;
            }
            iArr[i9] = i11;
            if (i11 > i8) {
                i8 = i11;
            }
        }
        if (i8 > 0) {
            float[] fArr = new float[i2];
            for (int i12 = 0; i12 < i2; i12++) {
                fArr[i12] = iArr[i12] / i8;
            }
            float f6 = 0.0f;
            for (int i13 = 0; i13 < i2; i13++) {
                f6 += fArr[i13];
            }
            float f7 = f6 / i2;
            if (f7 > 1.0d) {
                f7 = 0.99f;
            }
            iC = (int) f4;
            iC2 = iC;
            while (true) {
                if (iC2 < 0) {
                    iC2 = 0;
                    break;
                }
                if (fArr[iC2] < f7) {
                    break;
                }
                iC2--;
            }
            while (true) {
                if (iC >= i2) {
                    iC = 0;
                    break;
                }
                if (fArr[iC] < f7) {
                    break;
                }
                iC++;
            }
        } else {
            iC = 0;
            iC2 = 0;
        }
        if (iC2 == 0 && iC == 0) {
            iC2 = ((int) u6VarArr[0].c()) + (-10) < 0 ? 0 : ((int) u6VarArr[0].c()) - 10;
            iC = i2 - 1;
            if (((int) u6VarArr[0].c()) + 10 <= iC) {
                iC = ((int) u6VarArr[0].c()) + 10;
            }
        }
        return new int[]{iC2, iC};
    }

    private static u6 a(float f2, float f3, i2 i2Var) {
        float[] fArr = i2Var.f17370m;
        if (fArr != null && fArr.length == 5) {
            float f4 = -fArr[4];
            double d2 = f2 - fArr[0];
            double d3 = f4;
            int iCos = (int) ((d2 * Math.cos(d3)) + ((f3 - i2Var.f17370m[1]) * Math.sin(d3)) + i2Var.f17370m[0]);
            int iSin = (int) (((-(f2 - r2)) * Math.sin(d3)) + ((f3 - i2Var.f17370m[1]) * Math.cos(d3)) + i2Var.f17370m[1]);
            float[] fArr2 = i2Var.f17370m;
            return new u6((iCos - fArr2[2]) + i2Var.f17375r, (iSin - fArr2[3]) + i2Var.f17376s);
        }
        return new u6(f2, f3);
    }

    public static void a(u6[] u6VarArr, float f2, i2 i2Var) {
        if (i2Var == null || u6VarArr == null) {
            return;
        }
        for (int i2 = 0; i2 < u6VarArr.length; i2++) {
            u6VarArr[i2] = a((u6VarArr[i2].b() * f2) + i2Var.f17377t, (u6VarArr[i2].c() * f2) + i2Var.f17378u, i2Var);
        }
    }
}
