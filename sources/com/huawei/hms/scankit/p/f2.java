package com.huawei.hms.scankit.p;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class f2 {

    /* renamed from: a, reason: collision with root package name */
    private static final int[] f17241a = {0, 4, 1, 5};

    /* renamed from: b, reason: collision with root package name */
    private static final int[] f17242b = {6, 2, 7, 3};

    /* renamed from: c, reason: collision with root package name */
    private static final int[] f17243c = {8, 1, 1, 1, 1, 1, 1, 3};

    /* renamed from: d, reason: collision with root package name */
    private static final int[] f17244d = {7, 1, 1, 3, 1, 1, 1, 2, 1};

    /* renamed from: e, reason: collision with root package name */
    private static boolean f17245e = false;

    public static o5 a(p pVar, Map<l1, ?> map, boolean z2) throws a {
        s sVarB = pVar.b();
        a(false);
        List<u6[]> listA = a(z2, sVarB);
        if (listA.isEmpty()) {
            sVarB = sVarB.clone();
            sVarB.f();
            listA = a(z2, sVarB);
            a(true);
        }
        return new o5(sVarB, listA);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001d, code lost:
    
        if (r4 != 0) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0020, code lost:
    
        r3 = r0.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0028, code lost:
    
        if (r3.hasNext() == false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002a, code lost:
    
        r4 = (com.huawei.hms.scankit.p.u6[]) r3.next();
        r7 = r4[1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0032, code lost:
    
        if (r7 == null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0034, code lost:
    
        r2 = (int) java.lang.Math.max(r2, r7.c());
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003e, code lost:
    
        r4 = r4[3];
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0040, code lost:
    
        if (r4 == null) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0042, code lost:
    
        r2 = java.lang.Math.max(r2, (int) r4.c());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.util.List<com.huawei.hms.scankit.p.u6[]> a(boolean r8, com.huawei.hms.scankit.p.s r9) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            r2 = r1
            r3 = r2
        L8:
            r4 = r3
        L9:
            int r5 = r9.c()
            if (r2 >= r5) goto L79
            com.huawei.hms.scankit.p.u6[] r3 = a(r9, r2, r3)
            r5 = r3[r1]
            r6 = 1
            if (r5 != 0) goto L50
            r5 = 3
            r7 = r3[r5]
            if (r7 != 0) goto L50
            if (r4 != 0) goto L20
            goto L79
        L20:
            java.util.Iterator r3 = r0.iterator()
        L24:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L4c
            java.lang.Object r4 = r3.next()
            com.huawei.hms.scankit.p.u6[] r4 = (com.huawei.hms.scankit.p.u6[]) r4
            r7 = r4[r6]
            if (r7 == 0) goto L3e
            float r2 = (float) r2
            float r7 = r7.c()
            float r2 = java.lang.Math.max(r2, r7)
            int r2 = (int) r2
        L3e:
            r4 = r4[r5]
            if (r4 == 0) goto L24
            float r4 = r4.c()
            int r4 = (int) r4
            int r2 = java.lang.Math.max(r2, r4)
            goto L24
        L4c:
            int r2 = r2 + 5
            r3 = r1
            goto L8
        L50:
            r0.add(r3)
            if (r8 != 0) goto L56
            goto L79
        L56:
            r2 = 2
            r4 = r3[r2]
            if (r4 == 0) goto L6a
            float r4 = r4.b()
            int r4 = (int) r4
            r2 = r3[r2]
            float r2 = r2.c()
        L66:
            int r2 = (int) r2
            r3 = r4
            r4 = r6
            goto L9
        L6a:
            r2 = 4
            r4 = r3[r2]
            float r4 = r4.b()
            int r4 = (int) r4
            r2 = r3[r2]
            float r2 = r2.c()
            goto L66
        L79:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.f2.a(boolean, com.huawei.hms.scankit.p.s):java.util.List");
    }

    private static u6[] a(s sVar, int i2, int i3) {
        int iC = sVar.c();
        int iE = sVar.e();
        u6[] u6VarArr = new u6[8];
        a(u6VarArr, a(sVar, iC, iE, i2, i3, f17243c), f17241a);
        u6 u6Var = u6VarArr[4];
        if (u6Var != null) {
            i3 = (int) u6Var.b();
            i2 = (int) u6VarArr[4].c();
        }
        a(u6VarArr, a(sVar, iC, iE, i2, i3, f17244d), f17242b);
        return u6VarArr;
    }

    private static void a(u6[] u6VarArr, u6[] u6VarArr2, int[] iArr) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            u6VarArr[iArr[i2]] = u6VarArr2[i2];
        }
    }

    private static u6[] a(s sVar, int i2, int i3, int i4, int i5, int[] iArr) {
        boolean z2;
        int i6;
        int i7;
        u6[] u6VarArr = new u6[4];
        int[] iArr2 = new int[iArr.length];
        int i8 = i4;
        while (true) {
            if (i8 >= i2) {
                z2 = false;
                break;
            }
            int[] iArrA = a(sVar, i5, i8, i3, false, iArr, iArr2);
            if (iArrA != null) {
                int i9 = i8;
                int[] iArr3 = iArrA;
                while (i9 > 0) {
                    int i10 = i9 - 1;
                    int[] iArrA2 = a(sVar, i5, i10, i3, false, iArr, iArr2);
                    if (iArrA2 == null) {
                        break;
                    }
                    iArr3 = iArrA2;
                    i9 = i10;
                }
                float f2 = i9;
                u6VarArr[0] = new u6(iArr3[0], f2);
                u6VarArr[1] = new u6(iArr3[1], f2);
                z2 = true;
                i8 = i9;
            } else {
                i8 += 5;
            }
        }
        int i11 = i8 + 1;
        if (z2) {
            int[] iArr4 = {(int) u6VarArr[0].b(), (int) u6VarArr[1].b()};
            int i12 = i11;
            int i13 = 0;
            while (true) {
                if (i12 >= i2) {
                    i6 = i13;
                    i7 = i12;
                    break;
                }
                i6 = i13;
                i7 = i12;
                int[] iArrA3 = a(sVar, iArr4[0], i12, i3, false, iArr, iArr2);
                if (iArrA3 != null && Math.abs(iArr4[0] - iArrA3[0]) < 5 && Math.abs(iArr4[1] - iArrA3[1]) < 5) {
                    iArr4 = iArrA3;
                    i13 = 0;
                } else {
                    if (i6 > 25) {
                        break;
                    }
                    i13 = i6 + 1;
                }
                i12 = i7 + 1;
            }
            i11 = i7 - (i6 + 1);
            float f3 = i11;
            u6VarArr[2] = new u6(iArr4[0], f3);
            u6VarArr[3] = new u6(iArr4[1], f3);
        }
        if (i11 - i8 < 10) {
            Arrays.fill(u6VarArr, (Object) null);
        }
        return u6VarArr;
    }

    private static int[] a(s sVar, int i2, int i3, int i4, boolean z2, int[] iArr, int[] iArr2) {
        Arrays.fill(iArr2, 0, iArr2.length, 0);
        int i5 = 0;
        while (sVar.b(i2, i3) && i2 > 0) {
            int i6 = i5 + 1;
            if (i5 >= 3) {
                break;
            }
            i2--;
            i5 = i6;
        }
        int length = iArr.length;
        boolean z3 = z2;
        int i7 = 0;
        int i8 = i2;
        while (i2 < i4) {
            if (sVar.b(i2, i3) != z3) {
                iArr2[i7] = iArr2[i7] + 1;
            } else {
                if (i7 != length - 1) {
                    i7++;
                } else {
                    if (a(iArr2, iArr, 0.8f) < 0.42f) {
                        return new int[]{i8, i2};
                    }
                    i8 += iArr2[0] + iArr2[1];
                    int i9 = i7 - 1;
                    System.arraycopy(iArr2, 2, iArr2, 0, i9);
                    iArr2[i9] = 0;
                    iArr2[i7] = 0;
                    i7--;
                }
                iArr2[i7] = 1;
                z3 = !z3;
            }
            i2++;
        }
        if (i7 != length - 1 || a(iArr2, iArr, 0.8f) >= 0.42f) {
            return null;
        }
        return new int[]{i8, i2 - 1};
    }

    private static float a(int[] iArr, int[] iArr2, float f2) {
        int length = iArr.length;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            i2 += iArr[i4];
            i3 += iArr2[i4];
        }
        if (i2 < i3) {
            return Float.POSITIVE_INFINITY;
        }
        float f3 = i2;
        float f4 = f3 / i3;
        float f5 = f2 * f4;
        float f6 = 0.0f;
        for (int i5 = 0; i5 < length; i5++) {
            float f7 = iArr2[i5] * f4;
            float f8 = iArr[i5];
            float f9 = f8 > f7 ? f8 - f7 : f7 - f8;
            if (f9 > f5) {
                return Float.POSITIVE_INFINITY;
            }
            f6 += f9;
        }
        return f6 / f3;
    }

    public static void a(boolean z2) {
        f17245e = z2;
    }

    public static boolean a() {
        return f17245e;
    }
}
