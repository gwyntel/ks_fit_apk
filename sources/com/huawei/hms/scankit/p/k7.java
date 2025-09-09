package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import com.huawei.hms.scankit.util.LoadOpencvJNIUtil;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;

/* loaded from: classes4.dex */
public class k7 {
    public static byte[] a(p pVar, Map<l1, Object> map, s6 s6Var, int[] iArr, double[] dArr) throws a {
        if (pVar == null) {
            return null;
        }
        Collection collection = map != null ? (Collection) map.get(l1.f17489c) : null;
        if ((collection != null && !collection.contains(BarcodeFormat.QR_CODE) && !collection.contains(BarcodeFormat.PDF_417)) || s6Var == null || s6Var.k() != null) {
            iArr[0] = pVar.e();
            iArr[1] = pVar.c();
            return pVar.d();
        }
        if (collection != null) {
            collection.clear();
            l1 l1Var = l1.f17489c;
            map.remove(l1Var);
            collection.add(s6Var.c());
            map.put(l1Var, collection);
        } else if (map != null) {
            l1 l1Var2 = l1.f17489c;
            map.remove(l1Var2);
            Vector vector = new Vector();
            vector.add(s6Var.c());
            map.put(l1Var2, vector);
        }
        float f2 = 3.0f / r3.f17722i;
        if (f2 < 1.0f) {
            f2 = 1.0f;
        }
        dArr[5] = f2;
        return a(pVar, s6Var.j(), dArr, iArr);
    }

    private static byte[] a(p pVar, u6[] u6VarArr, double[] dArr, int[] iArr) throws a {
        if (u6VarArr == null) {
            iArr[0] = pVar.e();
            iArr[1] = pVar.c();
            return pVar.d();
        }
        int iE = pVar.e();
        int iB = iE;
        int iC = pVar.c();
        int iB2 = 0;
        int iC2 = 0;
        for (u6 u6Var : u6VarArr) {
            if (u6Var != null) {
                if (((int) u6Var.b()) < iB) {
                    iB = (int) u6Var.b();
                }
                if (((int) u6Var.c()) < iC) {
                    iC = (int) u6Var.c();
                }
                if (((int) u6Var.b()) > iB2) {
                    iB2 = (int) u6Var.b();
                }
                if (((int) u6Var.c()) > iC2) {
                    iC2 = (int) u6Var.c();
                }
            }
        }
        return a(u6VarArr, pVar, iArr, Math.max(iB2 - iB, iC2 - iC), dArr);
    }

    private static byte[] a(u6[] u6VarArr, p pVar, int[] iArr, float f2, double[] dArr) throws a {
        float fB = u6VarArr[0].b();
        float fB2 = u6VarArr[1].b();
        float fB3 = u6VarArr[2].b();
        float fC = u6VarArr[0].c();
        float fC2 = u6VarArr[1].c();
        float fC3 = u6VarArr[2].c();
        if (fB >= 0.0f && fB2 >= 0.0f && fB3 >= 0.0f && fC >= 0.0f && fC2 >= 0.0f && fC3 >= 0.0f && fB <= pVar.e() && fB2 <= pVar.e() && fB3 <= pVar.e() && fC <= pVar.c() && fC2 <= pVar.c() && fC3 <= pVar.c()) {
            int i2 = ((int) (fC + fC3)) / 2;
            int i3 = (int) ((((int) (fB + fB3)) / 2) - f2);
            if (i3 < 0) {
                i3 = 0;
            }
            int i4 = (int) (i2 - f2);
            if (i4 < 0) {
                i4 = 0;
            }
            int iC = ((int) f2) * 2;
            int iE = i3 + iC <= pVar.e() ? iC : pVar.e() - i3;
            if (i4 + iC > pVar.c()) {
                iC = pVar.c() - i4;
            }
            p pVarA = pVar.a(i3, i4, iE, iC);
            double degrees = dArr[0] + Math.toDegrees(a(u6VarArr[0], u6VarArr[1])) + 90.0d;
            dArr[0] = degrees;
            dArr[1] = i3;
            dArr[2] = i4;
            double d2 = iE;
            dArr[3] = d2;
            double d3 = iC;
            dArr[4] = d3;
            double radians = Math.toRadians(degrees);
            int iAbs = (int) (((Math.abs(Math.sin(radians)) * d2) + (Math.abs(Math.cos(radians)) * d3)) * dArr[5]);
            int iAbs2 = (int) (((d3 * Math.abs(Math.sin(radians))) + (d2 * Math.abs(Math.cos(radians)))) * dArr[5]);
            iArr[0] = iAbs2;
            iArr[1] = iAbs;
            byte[] bArrImageRotate = LoadOpencvJNIUtil.imageRotate(pVarA.d(), pVarA.c(), pVarA.e(), iAbs, iAbs2, (float) dArr[0], dArr[5]);
            if (bArrImageRotate != null) {
                return bArrImageRotate;
            }
            iArr[0] = pVar.e();
            iArr[1] = pVar.c();
            return pVar.d();
        }
        iArr[0] = pVar.e();
        iArr[1] = pVar.c();
        throw a.a();
    }

    public static double a(u6 u6Var, u6 u6Var2) {
        return Math.atan2(u6Var2.c() - u6Var.c(), u6Var2.b() - u6Var.b());
    }

    public static u6[] a(u6[] u6VarArr, int i2, int i3, double[] dArr) {
        double d2;
        u6[] u6VarArr2;
        int i4;
        u6 u6Var = null;
        if (u6VarArr == null) {
            return null;
        }
        double d3 = dArr[3];
        int i5 = d3 != 0.0d ? (int) d3 : i2;
        double d4 = dArr[4];
        int i6 = d4 != 0.0d ? (int) d4 : i3;
        double d5 = dArr[5];
        u6[] u6VarArr3 = new u6[u6VarArr.length];
        int i7 = 0;
        double radians = Math.toRadians(dArr[0]);
        double dCos = Math.cos(radians) * d5;
        double dSin = Math.sin(radians) * d5;
        double d6 = i6;
        double d7 = i5;
        double dAbs = (((Math.abs(dSin) - dSin) * d6) + ((Math.abs(dCos) - dCos) * d7)) / 2.0d;
        double d8 = -dSin;
        double dAbs2 = ((d6 * (Math.abs(dCos) - dCos)) + (d7 * (Math.abs(dSin) + dSin))) / 2.0d;
        while (i7 < u6VarArr.length) {
            if (u6VarArr[i7] != null) {
                u6VarArr2 = u6VarArr3;
                i4 = i7;
                double dB = (((r2.b() - dAbs) * dCos) + ((dAbs2 - r2.c()) * dSin)) / ((dCos * dCos) - (dSin * d8));
                d2 = d8;
                u6VarArr2[i4] = new u6(Math.round(dB) + ((int) dArr[1]), Math.round(dSin == 0.0d ? (r2.c() - dAbs2) / dCos : ((r2.b() - dAbs) - (dCos * dB)) / dSin) + ((int) dArr[2]));
                u6Var = null;
            } else {
                d2 = d8;
                u6VarArr2 = u6VarArr3;
                i4 = i7;
                u6VarArr2[i4] = u6Var;
            }
            i7 = i4 + 1;
            d8 = d2;
            u6VarArr3 = u6VarArr2;
        }
        return u6VarArr3;
    }
}
