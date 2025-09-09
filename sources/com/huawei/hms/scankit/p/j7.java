package com.huawei.hms.scankit.p;

import java.util.HashMap;

/* loaded from: classes4.dex */
public class j7 {

    /* renamed from: a, reason: collision with root package name */
    private static float f17444a = 2.51f;

    /* renamed from: b, reason: collision with root package name */
    private static float f17445b = 0.03f;

    /* renamed from: c, reason: collision with root package name */
    private static float f17446c = 2.43f;

    /* renamed from: d, reason: collision with root package name */
    private static float f17447d = 0.59f;

    /* renamed from: e, reason: collision with root package name */
    private static float f17448e = 0.14f;

    /* renamed from: f, reason: collision with root package name */
    private static HashMap<Integer, Integer> f17449f = new HashMap<>(255);

    private static int a(int i2, float f2) {
        if (f17449f.containsKey(Integer.valueOf(i2))) {
            return f17449f.get(Integer.valueOf(i2)).intValue();
        }
        float f3 = i2 / f2;
        int i3 = (int) ((f2 * (((f17444a * f3) + f17445b) * f3)) / ((f3 * ((f17446c * f3) + f17447d)) + f17448e));
        f17449f.put(Integer.valueOf(i2), Integer.valueOf(i3));
        return i3;
    }

    public static p4 b(p4 p4Var) {
        int iA = a(p4Var);
        int iC = p4Var.c();
        int iA2 = p4Var.a();
        byte[] bArrB = p4Var.b();
        byte[] bArr = new byte[iA2 * iC];
        for (int i2 = 0; i2 < iA2; i2++) {
            for (int i3 = 0; i3 < iC; i3++) {
                int i4 = (i2 * iC) + i3;
                bArr[i4] = (byte) (a(bArrB[i4] & 255, iA) & 255);
            }
        }
        f17449f = new HashMap<>(255);
        return new e6(bArr, iC, iA2, 0, 0, iC, iA2, false);
    }

    private static int a(p4 p4Var) {
        if (p4Var.b() == null) {
            return 1;
        }
        int iC = p4Var.c();
        int iA = p4Var.a();
        long j2 = 0;
        for (int i2 = iA / 4; i2 < (iA * 3) / 4; i2++) {
            for (int i3 = iC / 4; i3 < (iC * 3) / 4; i3++) {
                j2 += r0[(i2 * iC) + i3] & 255;
            }
        }
        return (int) ((j2 / r0.length) * 4);
    }
}
