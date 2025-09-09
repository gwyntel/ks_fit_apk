package com.huawei.hms.scankit.p;

import java.util.Arrays;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class g5 implements o6 {
    private s6 b(p pVar, Map<l1, ?> map) throws a {
        int iE = pVar.e();
        int iC = pVar.c();
        r rVar = new r(iE);
        int iMax = Math.max(1, iC >> 5);
        int iIntValue = iC / 2;
        if (map != null) {
            l1 l1Var = l1.f17491e;
            if (map.containsKey(l1Var)) {
                iIntValue += (((Integer) map.get(l1Var)).intValue() * iMax) / 3;
            }
        }
        int i2 = iIntValue;
        int i3 = 0;
        while (i3 < 15) {
            int i4 = i3 + 1;
            int i5 = i4 / 2;
            if ((i3 & 1) != 0) {
                i5 = -i5;
            }
            int i6 = i2 + (i5 * iMax);
            if (i6 < 0 || i6 >= iC) {
                break;
            }
            s6 s6VarA = a(pVar, rVar, map, i6, iE);
            if (s6VarA != null && s6VarA.k() != null) {
                return s6VarA;
            }
            i3 = i4;
        }
        throw a.a();
    }

    public abstract s6 a(int i2, r rVar, Map<l1, ?> map) throws a;

    @Override // com.huawei.hms.scankit.p.o6
    public s6 a(p pVar, Map<l1, ?> map) throws a {
        return b(pVar, map);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0048 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.huawei.hms.scankit.p.s6 a(com.huawei.hms.scankit.p.p r10, com.huawei.hms.scankit.p.r r11, java.util.Map<com.huawei.hms.scankit.p.l1, ?> r12, int r13, int r14) throws com.huawei.hms.scankit.p.a {
        /*
            r9 = this;
            r0 = 0
        L1:
            r1 = 3
            if (r0 >= r1) goto L4b
            r2 = 1
            if (r0 != 0) goto Lc
            com.huawei.hms.scankit.p.r r11 = r10.a(r13, r11)     // Catch: com.huawei.hms.scankit.p.a -> L48
            goto L24
        Lc:
            if (r0 != r2) goto L18
            com.huawei.hms.scankit.p.s r1 = r10.b()
            com.huawei.hms.scankit.p.r r11 = r1.a(r13, r11)
            r1 = r2
            goto L24
        L18:
            r3 = 2
            if (r0 != r3) goto L24
            boolean r3 = com.huawei.hms.scankit.p.r3.f17733t
            if (r3 != 0) goto L20
            goto L48
        L20:
            com.huawei.hms.scankit.p.r r11 = r10.a(r13, r2)
        L24:
            int[] r3 = r11.d()
            boolean r3 = a(r3)
            if (r3 != 0) goto L2f
            goto L48
        L2f:
            boolean r3 = com.huawei.hms.scankit.p.r3.f17716c
            if (r3 != 0) goto L35
            r5 = r2
            goto L36
        L35:
            r5 = r1
        L36:
            r3 = r9
            r4 = r11
            r6 = r12
            r7 = r13
            r8 = r14
            com.huawei.hms.scankit.p.s6 r1 = r3.a(r4, r5, r6, r7, r8)
            if (r1 == 0) goto L48
            java.lang.String r2 = r1.k()
            if (r2 == 0) goto L48
            return r1
        L48:
            int r0 = r0 + 1
            goto L1
        L4b:
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.g5.a(com.huawei.hms.scankit.p.p, com.huawei.hms.scankit.p.r, java.util.Map, int, int):com.huawei.hms.scankit.p.s6");
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.huawei.hms.scankit.p.s6 a(com.huawei.hms.scankit.p.r r19, int r20, java.util.Map<com.huawei.hms.scankit.p.l1, ?> r21, int r22, int r23) {
        /*
            r18 = this;
            r0 = r23
            r1 = 0
            r3 = r20
            r2 = r21
            r4 = r1
        L8:
            if (r4 >= r3) goto La2
            if (r4 != 0) goto Lf
            r19.c()
        Lf:
            r5 = 1
            if (r4 != r5) goto L15
            r19.i()
        L15:
            r6 = 2
            if (r4 != r6) goto L1e
            r19.g()
            r19.j()
        L1e:
            r7 = r1
        L1f:
            if (r7 >= r6) goto L98
            if (r7 != r5) goto L45
            r19.h()
            if (r2 == 0) goto L45
            com.huawei.hms.scankit.p.l1 r8 = com.huawei.hms.scankit.p.l1.f17496j
            boolean r9 = r2.containsKey(r8)
            if (r9 == 0) goto L45
            java.util.EnumMap r9 = new java.util.EnumMap
            java.lang.Class<com.huawei.hms.scankit.p.l1> r10 = com.huawei.hms.scankit.p.l1.class
            r9.<init>(r10)
            r9.putAll(r2)
            r9.remove(r8)
            r8 = r18
            r10 = r22
            r2 = r9
            r9 = r19
            goto L4b
        L45:
            r8 = r18
            r9 = r19
            r10 = r22
        L4b:
            com.huawei.hms.scankit.p.s6 r11 = r8.a(r10, r9, r2)     // Catch: com.huawei.hms.scankit.p.a -> L8f
            if (r7 != r5) goto L87
            com.huawei.hms.scankit.p.u6[] r12 = r11.j()     // Catch: com.huawei.hms.scankit.p.a -> L8f
            if (r12 == 0) goto L87
            com.huawei.hms.scankit.p.u6 r13 = new com.huawei.hms.scankit.p.u6     // Catch: com.huawei.hms.scankit.p.a -> L8f
            float r14 = (float) r0     // Catch: com.huawei.hms.scankit.p.a -> L8f
            r15 = r12[r1]     // Catch: com.huawei.hms.scankit.p.a -> L8f
            float r15 = r15.b()     // Catch: com.huawei.hms.scankit.p.a -> L8f
            float r15 = r14 - r15
            r16 = 1065353216(0x3f800000, float:1.0)
            float r15 = r15 - r16
            r17 = r12[r1]     // Catch: com.huawei.hms.scankit.p.a -> L8f
            float r6 = r17.c()     // Catch: com.huawei.hms.scankit.p.a -> L8f
            r13.<init>(r15, r6)     // Catch: com.huawei.hms.scankit.p.a -> L8f
            r12[r1] = r13     // Catch: com.huawei.hms.scankit.p.a -> L8f
            com.huawei.hms.scankit.p.u6 r6 = new com.huawei.hms.scankit.p.u6     // Catch: com.huawei.hms.scankit.p.a -> L8f
            r13 = r12[r5]     // Catch: com.huawei.hms.scankit.p.a -> L8f
            float r13 = r13.b()     // Catch: com.huawei.hms.scankit.p.a -> L8f
            float r14 = r14 - r13
            float r14 = r14 - r16
            r13 = r12[r5]     // Catch: com.huawei.hms.scankit.p.a -> L8f
            float r13 = r13.c()     // Catch: com.huawei.hms.scankit.p.a -> L8f
            r6.<init>(r14, r13)     // Catch: com.huawei.hms.scankit.p.a -> L8f
            r12[r5] = r6     // Catch: com.huawei.hms.scankit.p.a -> L8f
        L87:
            boolean r6 = a(r11, r0)     // Catch: com.huawei.hms.scankit.p.a -> L8f
            if (r6 != 0) goto L8e
            goto L94
        L8e:
            return r11
        L8f:
            if (r7 != r5) goto L94
            r19.h()
        L94:
            int r7 = r7 + 1
            r6 = 2
            goto L1f
        L98:
            r8 = r18
            r9 = r19
            r10 = r22
            int r4 = r4 + 1
            goto L8
        La2:
            r8 = r18
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.g5.a(com.huawei.hms.scankit.p.r, int, java.util.Map, int, int):com.huawei.hms.scankit.p.s6");
    }

    private static boolean a(int[] iArr) {
        int iBitCount = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < iArr.length && iBitCount < 20; i3++) {
            int i4 = iArr[i3];
            iBitCount += Integer.bitCount((i2 | (i4 << 1)) ^ i4);
            i2 = (iArr[i3] >> 31) & 1;
        }
        return iBitCount >= 20;
    }

    private static boolean a(s6 s6Var, int i2) {
        u6[] u6VarArrJ = s6Var.j();
        return Math.abs(((double) u6VarArrJ[1].b()) - ((double) u6VarArrJ[0].b())) / ((double) i2) > 0.4d;
    }

    protected static void a(r rVar, int i2, int[] iArr) throws a {
        int length = iArr.length;
        int i3 = 0;
        Arrays.fill(iArr, 0, length, 0);
        int iE = rVar.e();
        if (i2 < iE) {
            boolean z2 = !rVar.b(i2);
            while (i2 < iE) {
                if (rVar.b(i2) == z2) {
                    i3++;
                    if (i3 == length) {
                        break;
                    }
                    if (i3 >= 0 && i3 < iArr.length) {
                        iArr[i3] = 1;
                        z2 = !z2;
                    } else {
                        throw a.a();
                    }
                } else if (i3 >= 0 && i3 < iArr.length) {
                    iArr[i3] = iArr[i3] + 1;
                } else {
                    throw a.a();
                }
                i2++;
            }
            if (i3 != length) {
                if (i3 != length - 1 || i2 != iE) {
                    throw a.a();
                }
                return;
            }
            return;
        }
        throw a.a();
    }

    protected static float a(int[] iArr, int[] iArr2, float f2) {
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
}
