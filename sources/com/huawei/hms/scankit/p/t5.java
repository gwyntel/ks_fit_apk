package com.huawei.hms.scankit.p;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes4.dex */
public final class t5 {

    /* renamed from: a, reason: collision with root package name */
    private static final a3 f17819a = new a3();

    private static boolean a(int i2, int i3, int i4) {
        return i3 + (-2) <= i2 && i2 <= i4 + 2;
    }

    private static z1 b(b2 b2Var, b2 b2Var2) throws a {
        k kVarA;
        if ((b2Var == null && b2Var2 == null) || (kVarA = a(b2Var, b2Var2)) == null) {
            return null;
        }
        return new z1(kVarA, a0.a(a(b2Var), a(b2Var2)));
    }

    private static int c(int i2) {
        return 2 << i2;
    }

    public static w1 a(s sVar, u6 u6Var, u6 u6Var2, u6 u6Var3, u6 u6Var4, int i2, int i3, Map<l1, ?> map) throws a {
        a0 a0Var;
        z1 z1VarB;
        a0 a0Var2 = new a0(sVar, u6Var, u6Var2, u6Var3, u6Var4);
        b2 b2VarA = null;
        b2 b2VarA2 = null;
        boolean z2 = true;
        while (true) {
            a0Var = a0Var2;
            if (u6Var != null) {
                b2VarA = a(sVar, a0Var, u6Var, true, i2, i3);
            }
            if (u6Var3 != null) {
                b2VarA2 = a(sVar, a0Var, u6Var3, false, i2, i3);
            }
            z1VarB = b(b2VarA, b2VarA2);
            if (z1VarB == null) {
                throw a.a();
            }
            a0Var2 = z1VarB.i();
            if (!z2 || a0Var2 == null || (a0Var2.f() >= a0Var.f() && a0Var2.d() <= a0Var.d())) {
                break;
            }
            z2 = false;
        }
        z1VarB.a(a0Var);
        int iF = z1VarB.f() + 1;
        z1VarB.a(0, b2VarA);
        z1VarB.a(iF, b2VarA2);
        a(z1VarB, b2VarA, a0Var, iF, sVar, i2, i3);
        return a(z1VarB, map);
    }

    private static int b(int[] iArr) {
        int iMax = -1;
        for (int i2 : iArr) {
            iMax = Math.max(iMax, i2);
        }
        return iMax;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int[] b(com.huawei.hms.scankit.p.s r7, int r8, int r9, boolean r10, int r11, int r12) {
        /*
            r0 = 8
            int[] r1 = new int[r0]
            r2 = 1
            if (r10 == 0) goto L9
            r3 = r2
            goto La
        L9:
            r3 = -1
        La:
            r4 = 0
            r5 = r10
        Lc:
            if (r10 == 0) goto L11
            if (r11 >= r9) goto L27
            goto L13
        L11:
            if (r11 < r8) goto L27
        L13:
            if (r4 >= r0) goto L27
            boolean r6 = r7.b(r11, r12)
            if (r6 != r5) goto L22
            r6 = r1[r4]
            int r6 = r6 + r2
            r1[r4] = r6
            int r11 = r11 + r3
            goto Lc
        L22:
            int r4 = r4 + 1
            r5 = r5 ^ 1
            goto Lc
        L27:
            if (r4 == r0) goto L34
            if (r10 == 0) goto L2c
            r8 = r9
        L2c:
            if (r11 != r8) goto L32
            r7 = 7
            if (r4 != r7) goto L32
            goto L34
        L32:
            r7 = 0
            return r7
        L34:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.t5.b(com.huawei.hms.scankit.p.s, int, int, boolean, int, int):int[]");
    }

    private static int b(int i2) {
        return a(a(i2));
    }

    private static void a(z1 z1Var, b2 b2Var, a0 a0Var, int i2, s sVar, int i3, int i4) {
        a2 b2Var2;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        a2 a2Var;
        boolean z2 = b2Var != null;
        int i10 = i3;
        int i11 = i4;
        for (int i12 = 1; i12 <= i2; i12++) {
            int i13 = z2 ? i12 : i2 - i12;
            if (z1Var.a(i13) == null) {
                if (i13 != 0 && i13 != i2) {
                    b2Var2 = new a2(a0Var);
                } else {
                    b2Var2 = new b2(a0Var, i13 == 0);
                }
                a2 a2Var2 = b2Var2;
                z1Var.a(i13, a2Var2);
                int i14 = -1;
                int i15 = i10;
                int iMax = i11;
                int iF = a0Var.f();
                int i16 = -1;
                while (iF <= a0Var.d()) {
                    int iA = a(z1Var, i13, iF, z2);
                    if (iA >= 0 && iA <= a0Var.c()) {
                        i5 = iA;
                    } else if (i16 == i14) {
                        i6 = i16;
                        i7 = iF;
                        i8 = i15;
                        i9 = i14;
                        a2Var = a2Var2;
                        i15 = i8;
                        i5 = i6;
                        iF = i7 + 1;
                        a2Var2 = a2Var;
                        i16 = i5;
                        i14 = i9;
                    } else {
                        i5 = i16;
                    }
                    i6 = i16;
                    int i17 = iF;
                    int i18 = iMax;
                    int i19 = i15;
                    i9 = i14;
                    a2Var = a2Var2;
                    x0 x0VarA = a(sVar, a0Var.e(), a0Var.c(), z2, i5, i17, i19, i18);
                    i7 = i17;
                    if (x0VarA != null) {
                        a2Var.a(i7, x0VarA);
                        int iMin = Math.min(i19, x0VarA.f());
                        iMax = Math.max(i18, x0VarA.f());
                        i15 = iMin;
                        iF = i7 + 1;
                        a2Var2 = a2Var;
                        i16 = i5;
                        i14 = i9;
                    } else {
                        iMax = i18;
                        i8 = i19;
                        i15 = i8;
                        i5 = i6;
                        iF = i7 + 1;
                        a2Var2 = a2Var;
                        i16 = i5;
                        i14 = i9;
                    }
                }
                i10 = i15;
                i11 = iMax;
            }
        }
    }

    private static a0 a(b2 b2Var) throws a {
        int[] iArrD;
        if (b2Var == null || (iArrD = b2Var.d()) == null) {
            return null;
        }
        int iB = b(iArrD);
        int i2 = 0;
        int i3 = 0;
        for (int i4 : iArrD) {
            i3 += iB - i4;
            if (i4 > 0) {
                break;
            }
        }
        x0[] x0VarArrB = b2Var.b();
        for (int i5 = 0; i3 > 0 && x0VarArrB[i5] == null; i5++) {
            i3--;
        }
        for (int length = iArrD.length - 1; length >= 0; length--) {
            int i6 = iArrD[length];
            i2 += iB - i6;
            if (i6 > 0) {
                break;
            }
        }
        for (int length2 = x0VarArrB.length - 1; i2 > 0 && x0VarArrB[length2] == null; length2--) {
            i2--;
        }
        return b2Var.a().a(i3, i2, b2Var.e());
    }

    private static k a(b2 b2Var, b2 b2Var2) throws a {
        k kVarC;
        k kVarC2;
        if (b2Var == null || (kVarC = b2Var.c()) == null) {
            if (b2Var2 == null) {
                return null;
            }
            return b2Var2.c();
        }
        if (b2Var2 == null || (kVarC2 = b2Var2.c()) == null || kVarC.a() == kVarC2.a() || kVarC.b() == kVarC2.b() || kVarC.c() == kVarC2.c()) {
            return kVarC;
        }
        return null;
    }

    private static b2 a(s sVar, a0 a0Var, u6 u6Var, boolean z2, int i2, int i3) {
        int iB;
        b2 b2Var = new b2(a0Var, z2);
        int i4 = 0;
        while (i4 < 2) {
            int i5 = i4 == 0 ? 1 : -1;
            int iB2 = (int) u6Var.b();
            for (int iC = (int) u6Var.c(); iC <= a0Var.d() && iC >= a0Var.f(); iC += i5) {
                x0 x0VarA = a(sVar, 0, sVar.e(), z2, iB2, iC, i2, i3);
                if (x0VarA != null) {
                    b2Var.a(iC, x0VarA);
                    if (z2) {
                        iB = x0VarA.d();
                    } else {
                        iB = x0VarA.b();
                    }
                    iB2 = iB;
                }
            }
            i4++;
        }
        return b2Var;
    }

    private static void a(z1 z1Var, m[][] mVarArr) throws a {
        m mVar = mVarArr[0][1];
        int[] iArrA = mVar.a();
        int iF = (z1Var.f() * z1Var.h()) - c(z1Var.g());
        if (iArrA.length != 0) {
            if (iArrA[0] != iF) {
                mVar.a(iF);
            }
        } else {
            if (iF >= 1 && iF <= 928) {
                mVar.a(iF);
                return;
            }
            throw a.a();
        }
    }

    private static w1 a(z1 z1Var, Map<l1, ?> map) throws a {
        m[][] mVarArrA = a(z1Var);
        a(z1Var, mVarArrA);
        ArrayList arrayList = new ArrayList();
        int[] iArr = new int[z1Var.h() * z1Var.f()];
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (int i2 = 0; i2 < z1Var.h(); i2++) {
            int i3 = 0;
            while (i3 < z1Var.f()) {
                int i4 = i3 + 1;
                int[] iArrA = mVarArrA[i2][i4].a();
                int iF = (z1Var.f() * i2) + i3;
                if (iArrA.length == 0) {
                    arrayList.add(Integer.valueOf(iF));
                } else if (iArrA.length == 1) {
                    iArr[iF] = iArrA[0];
                } else {
                    arrayList3.add(Integer.valueOf(iF));
                    arrayList2.add(iArrA);
                }
                i3 = i4;
            }
        }
        int size = arrayList2.size();
        int[][] iArr2 = new int[size][];
        for (int i5 = 0; i5 < size; i5++) {
            iArr2[i5] = (int[]) arrayList2.get(i5);
        }
        return a(z1Var.g(), iArr, n5.a(arrayList), n5.a(arrayList3), iArr2, map);
    }

    private static w1 a(int i2, int[] iArr, int[] iArr2, int[] iArr3, int[][] iArr4, Map<l1, ?> map) throws a {
        int length = iArr3.length;
        int[] iArr5 = new int[length];
        int i3 = 100;
        while (true) {
            int i4 = i3 - 1;
            if (i3 > 0) {
                for (int i5 = 0; i5 < length; i5++) {
                    iArr[iArr3[i5]] = iArr4[i5][iArr5[i5]];
                }
                try {
                    return a(iArr, i2, iArr2, map);
                } catch (a unused) {
                    if (length == 0) {
                        throw a.a();
                    }
                    int i6 = 0;
                    while (true) {
                        if (i6 >= length) {
                            break;
                        }
                        int i7 = iArr5[i6];
                        if (i7 < iArr4[i6].length - 1) {
                            iArr5[i6] = i7 + 1;
                            break;
                        }
                        iArr5[i6] = 0;
                        if (i6 == length - 1) {
                            throw a.a();
                        }
                        i6++;
                    }
                    i3 = i4;
                }
            } else {
                throw a.a();
            }
        }
    }

    private static m[][] a(z1 z1Var) throws a {
        int iC;
        m[][] mVarArr = (m[][]) Array.newInstance((Class<?>) m.class, z1Var.h(), z1Var.f() + 2);
        for (m[] mVarArr2 : mVarArr) {
            int i2 = 0;
            while (true) {
                if (i2 < mVarArr2.length) {
                    mVarArr2[i2] = new m();
                    i2++;
                }
            }
        }
        int i3 = 0;
        for (a2 a2Var : z1Var.j()) {
            if (a2Var != null) {
                for (x0 x0Var : a2Var.b()) {
                    if (x0Var != null && (iC = x0Var.c()) >= 0 && iC < mVarArr.length) {
                        mVarArr[iC][i3].a(x0Var.e());
                    }
                }
            }
            i3++;
        }
        return mVarArr;
    }

    private static boolean a(z1 z1Var, int i2) {
        return i2 >= 0 && i2 <= z1Var.f() + 1;
    }

    private static int a(z1 z1Var, int i2, int i3, boolean z2) {
        int i4 = z2 ? 1 : -1;
        int i5 = i2 - i4;
        x0 x0VarA = a(z1Var, i5) ? z1Var.a(i5).a(i3) : null;
        if (x0VarA != null) {
            return z2 ? x0VarA.b() : x0VarA.d();
        }
        x0 x0VarB = z1Var.a(i2).b(i3);
        if (x0VarB != null) {
            return z2 ? x0VarB.d() : x0VarB.b();
        }
        if (a(z1Var, i5)) {
            x0VarB = z1Var.a(i5).b(i3);
        }
        if (x0VarB != null) {
            return z2 ? x0VarB.b() : x0VarB.d();
        }
        int i6 = 0;
        while (true) {
            i2 -= i4;
            if (!a(z1Var, i2)) {
                return z2 ? z1Var.i().e() : z1Var.i().c();
            }
            for (x0 x0Var : z1Var.a(i2).b()) {
                if (x0Var != null) {
                    return (z2 ? x0Var.b() : x0Var.d()) + (i4 * i6 * (x0Var.b() - x0Var.d()));
                }
            }
            i6++;
        }
    }

    private static x0 a(s sVar, int i2, int i3, boolean z2, int i4, int i5, int i6, int i7) {
        int i8;
        int iD;
        int iA;
        int iA2 = a(sVar, i2, i3, z2, i4, i5);
        int[] iArrB = b(sVar, i2, i3, z2, iA2, i5);
        if (iArrB == null) {
            return null;
        }
        int iA3 = s4.a(iArrB);
        if (z2) {
            i8 = iA2 + iA3;
        } else {
            for (int i9 = 0; i9 < iArrB.length / 2; i9++) {
                int i10 = iArrB[i9];
                iArrB[i9] = iArrB[(iArrB.length - 1) - i9];
                iArrB[(iArrB.length - 1) - i9] = i10;
            }
            iA2 -= iA3;
            i8 = iA2;
        }
        if (a(iA3, i6, i7) && (iA = n5.a((iD = m5.d(iArrB)))) != -1) {
            return new x0(iA2, i8, b(iD), iA);
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0022, code lost:
    
        r0 = -r0;
        r8 = !r8;
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0022, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0022, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0022, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0011  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int a(com.huawei.hms.scankit.p.s r5, int r6, int r7, boolean r8, int r9, int r10) {
        /*
            if (r8 == 0) goto L4
            r0 = -1
            goto L5
        L4:
            r0 = 1
        L5:
            r1 = 0
            r2 = r9
        L7:
            r3 = 2
            if (r1 >= r3) goto L28
        La:
            if (r8 == 0) goto Lf
            if (r2 < r6) goto L22
            goto L11
        Lf:
            if (r2 >= r7) goto L22
        L11:
            boolean r4 = r5.b(r2, r10)
            if (r8 != r4) goto L22
            int r4 = r9 - r2
            int r4 = java.lang.Math.abs(r4)
            if (r4 <= r3) goto L20
            return r9
        L20:
            int r2 = r2 + r0
            goto La
        L22:
            int r0 = -r0
            r8 = r8 ^ 1
            int r1 = r1 + 1
            goto L7
        L28:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.t5.a(com.huawei.hms.scankit.p.s, int, int, boolean, int, int):int");
    }

    private static w1 a(int[] iArr, int i2, int[] iArr2, Map<l1, ?> map) throws a {
        if (iArr.length != 0) {
            int i3 = 1 << (i2 + 1);
            int iA = a(iArr, iArr2, i3);
            a(iArr, i3);
            w1 w1VarA = q1.a(iArr, String.valueOf(i2), map);
            w1VarA.b(Integer.valueOf(iA));
            w1VarA.a(Integer.valueOf(iArr2.length));
            return w1VarA;
        }
        throw a.a();
    }

    private static int a(int[] iArr, int[] iArr2, int i2) throws a {
        if ((iArr2 == null || iArr2.length <= (i2 / 2) + 3) && i2 >= 0 && i2 <= 512) {
            return f17819a.a(iArr, i2, iArr2);
        }
        throw a.a();
    }

    private static void a(int[] iArr, int i2) throws a {
        if (iArr.length >= 4) {
            int i3 = iArr[0];
            if (i3 > iArr.length) {
                throw a.a();
            }
            if (i3 == 0) {
                if (i2 < iArr.length) {
                    iArr[0] = iArr.length - i2;
                    return;
                }
                throw a.a();
            }
            return;
        }
        throw a.a();
    }

    private static int[] a(int i2) {
        int[] iArr = new int[8];
        int i3 = 0;
        int i4 = 7;
        while (true) {
            int i5 = i2 & 1;
            if (i5 != i3) {
                i4--;
                if (i4 < 0) {
                    return iArr;
                }
                i3 = i5;
            }
            iArr[i4] = iArr[i4] + 1;
            i2 >>= 1;
        }
    }

    private static int a(int[] iArr) {
        return ((((iArr[0] - iArr[2]) + iArr[4]) - iArr[6]) + 9) % 9;
    }
}
