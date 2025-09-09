package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.util.LoadOpencvJNIUtil;

/* loaded from: classes4.dex */
public class l4 {
    public static int a(int i2, int i3, int i4) {
        return i2 >= i3 ? i3 : i2 <= i4 ? i4 : i2;
    }

    public static p a(p pVar) {
        int iE = pVar.e();
        int iC = pVar.c();
        byte[] bArrD = pVar.d();
        byte[] bArr = new byte[iE * iC];
        for (int i2 = 0; i2 < iC; i2++) {
            for (int i3 = 0; i3 < iE; i3++) {
                bArr[(((i3 * iC) + iC) - i2) - 1] = bArrD[(i2 * iE) + i3];
            }
        }
        return new p(new e4(new e6(bArr, iC, iE, 0, 0, iC, iE, false)));
    }

    public static p a(p pVar, float f2) {
        if (f2 == 1.0f) {
            return pVar;
        }
        int iC = pVar.c();
        int iE = pVar.e();
        int i2 = (int) (iE / f2);
        int i3 = (int) (iC / f2);
        byte[] bArrD = pVar.d();
        int i4 = i2 * i3;
        byte[] bArr = new byte[i4];
        int i5 = 0;
        int i6 = 0;
        while (i6 < i4) {
            double dA = a(i6 % i2, i2 - 1, i5) * f2;
            double dA2 = a(i6 / i2, i3 - 1, i5) * f2;
            double dFloor = Math.floor(dA);
            int i7 = i6;
            double dFloor2 = Math.floor(dA2);
            double d2 = dA - dFloor;
            double d3 = dA2 - dFloor2;
            int i8 = i4;
            int iA = a((int) dFloor, iE - 1, 0);
            int iA2 = a((int) dFloor2, iC - 1, 0);
            int i9 = iA2 * iE;
            int i10 = iC;
            int i11 = i2;
            double d4 = 1.0d - d2;
            double d5 = 1.0d - d3;
            int i12 = iA + 1;
            byte[] bArr2 = bArr;
            int i13 = iA2 + 1;
            bArr2[i7] = (byte) (((int) (((bArrD[i9 + iA] & 255) * d4 * d5) + ((bArrD[i9 + a(i12, r14, 0)] & 255) * d2 * d5) + ((bArrD[(a(i13, r6, 0) * iE) + iA] & 255) * d4 * d3) + ((bArrD[(a(i13, r6, 0) * iE) + a(i12, r14, 0)] & 255) * d2 * d3))) & 255);
            i6 = i7 + 1;
            i5 = 0;
            i4 = i8;
            i2 = i11;
            iC = i10;
            i3 = i3;
            bArr = bArr2;
        }
        return new p(new e4(new e6(bArr, i2, i3, 0, 0, i2, i3, false)));
    }

    public static p a(boolean z2, p pVar, float f2) {
        if (f2 == 1.0f) {
            return pVar;
        }
        int iC = pVar.c();
        int iE = pVar.e();
        int i2 = (int) (iE / f2);
        int i3 = (int) (iC / f2);
        return new p(new e4(new e6(LoadOpencvJNIUtil.imageResize(pVar.d(), iC, iE, i3, i2), i2, i3, 0, 0, i2, i3, false)));
    }
}
