package com.huawei.hms.scankit.p;

import java.util.Map;

/* loaded from: classes4.dex */
public class h2 {

    /* renamed from: a, reason: collision with root package name */
    private final s f17317a;

    /* renamed from: b, reason: collision with root package name */
    private v6 f17318b;

    public h2(s sVar) {
        this.f17317a = sVar;
    }

    private static float b(float f2, float f3, float f4) {
        return Math.min(Math.min(f2, f3), f4);
    }

    public final f3[] a(Map<l1, ?> map) throws a {
        v6 v6Var = map == null ? null : (v6) map.get(l1.f17496j);
        this.f17318b = v6Var;
        return new j3(this.f17317a, v6Var).a(map);
    }

    public static boolean a(f3 f3Var, f3 f3Var2, f3 f3Var3) {
        float[] fArr = new float[3];
        a(f3Var, f3Var2, f3Var3, fArr);
        float f2 = fArr[0];
        float f3 = fArr[1];
        float f4 = fArr[2];
        float fSqrt = (float) Math.sqrt(f3);
        float fSqrt2 = (float) Math.sqrt(f4);
        float fSqrt3 = (float) Math.sqrt(f2);
        if (fSqrt / fSqrt2 >= 1.8f || fSqrt2 / fSqrt >= 1.8f || b(fSqrt, fSqrt2, fSqrt3) <= a(f3Var.e(), f3Var2.e(), f3Var3.e()) * 6.0f) {
            return false;
        }
        float f5 = ((f3 + f4) - f2) / ((fSqrt * 2.0f) * fSqrt2);
        float f6 = fSqrt3 * 2.0f;
        float f7 = ((f2 + f3) - f4) / (fSqrt * f6);
        float f8 = ((f2 + f4) - f3) / (f6 * fSqrt2);
        return Math.abs(f5) <= 0.342f && f7 >= 0.5736f && f7 <= 0.8191f && f8 >= 0.5736f && f8 <= 0.8191f;
    }

    private static void a(f3 f3Var, f3 f3Var2, f3 f3Var3, float[] fArr) {
        float fB = f3Var.b() - f3Var2.b();
        float fC = f3Var.c() - f3Var2.c();
        float f2 = (fB * fB) + (fC * fC);
        float fB2 = f3Var.b() - f3Var3.b();
        float fC2 = f3Var.c() - f3Var3.c();
        float f3 = (fB2 * fB2) + (fC2 * fC2);
        float fB3 = f3Var2.b() - f3Var3.b();
        float fC3 = f3Var2.c() - f3Var3.c();
        float f4 = (fB3 * fB3) + (fC3 * fC3);
        if (f2 > f4 && f2 > f3) {
            fArr[0] = f2;
            fArr[1] = f3;
            fArr[2] = f4;
        } else if (f4 > f2 && f4 > f3) {
            fArr[0] = f4;
            fArr[1] = f2;
            fArr[2] = f3;
        } else {
            fArr[0] = f3;
            fArr[1] = f2;
            fArr[2] = f4;
        }
    }

    private static float a(float f2, float f3, float f4) {
        return Math.max(Math.max(f2, f3), f4);
    }

    public static boolean a(f3[] f3VarArr, f3[] f3VarArr2, int[] iArr) {
        f3 f3Var = f3VarArr[0];
        f3 f3Var2 = f3VarArr[1];
        f3 f3Var3 = f3VarArr[2];
        int i2 = iArr[0];
        int i3 = iArr[1];
        int i4 = iArr[2];
        float fB = (f3Var3.b() - f3Var2.b()) + f3Var.b();
        float fC = (f3Var3.c() - f3Var2.c()) + f3Var.c();
        float fE = ((f3Var.e() + f3Var2.e()) + f3Var3.e()) / 3.0f;
        for (int i5 = 0; i5 < f3VarArr2.length; i5++) {
            if (i5 != i2 && i5 != i3 && i5 != i4) {
                f3 f3Var4 = f3VarArr2[i5];
                float fB2 = fB - f3Var4.b();
                float fC2 = fC - f3Var4.c();
                if (((float) Math.sqrt((fB2 * fB2) + (fC2 * fC2))) < 10.0f * fE) {
                    return true;
                }
            }
        }
        return false;
    }
}
