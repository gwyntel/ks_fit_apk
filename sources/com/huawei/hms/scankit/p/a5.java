package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/* loaded from: classes4.dex */
public final class a5 implements o6 {
    public static boolean a(s6 s6Var, float f2, float f3) {
        double dAbs = Math.abs(s6Var.j()[0].b() - s6Var.j()[1].b()) / f2;
        return (dAbs < 0.55d && ((double) f3) > 1.5d) || dAbs < 0.3d;
    }

    public s6 b(p pVar, p pVar2, p pVar3, Map<l1, ?> map, m4 m4Var, i2 i2Var) throws a {
        return pVar3 != null ? b(pVar3, m4Var, map, i2Var) : a(pVar, pVar2, m4Var, map);
    }

    public s6 c(p pVar, m4 m4Var, Map<l1, ?> map, i2 i2Var) throws a {
        s6 s6VarA;
        int iE = pVar.e();
        int iC = pVar.c();
        if (iE >= iC) {
            iE = iC;
        }
        float f2 = (iE * 1.0f) / 500.0f;
        p pVarG = m4Var.g(pVar, f2);
        try {
            s6VarA = a(pVarG, a(map), map);
            if (s6VarA != null) {
                try {
                    if (s6VarA.k() != null) {
                        k2.a(s6VarA.j(), f2, i2Var);
                        return s6VarA;
                    }
                } catch (a unused) {
                    try {
                        pVarG.a(n1.a(pVarG.d(), pVarG.e(), pVarG.c(), true));
                        s6 s6VarA2 = a(pVarG, a(map), map);
                        if (s6VarA2 == null || s6VarA2.k() == null) {
                            throw a.a();
                        }
                        k2.a(s6VarA2.j(), f2, i2Var);
                        return s6VarA2;
                    } catch (a unused2) {
                        if (s6VarA != null) {
                            return s6VarA;
                        }
                        throw a.a();
                    }
                }
            }
            throw a.a();
        } catch (a unused3) {
            s6VarA = null;
        }
    }

    @Override // com.huawei.hms.scankit.p.o6
    public s6 a(p pVar, Map<l1, ?> map) throws a {
        return a(pVar, a(map), map);
    }

    public s6 b(p pVar, m4 m4Var, Map<l1, ?> map, i2 i2Var) throws a {
        int iE = pVar.e();
        int iC = pVar.c();
        int i2 = iE < iC ? iE : iC;
        float f2 = i2 * 1.0f;
        float f3 = f2 / 128.0f;
        if (f3 < 1.0f && r3.f17716c) {
            pVar = m4Var.e(pVar, f3);
        }
        p pVar2 = pVar;
        float f4 = f2 / 500.0f;
        float f5 = f4 >= 1.0f ? f4 : 1.0f;
        try {
            s6 s6VarA = a(m4Var.g(pVar2, f5), a(map), map);
            if (s6VarA != null && s6VarA.k() != null) {
                k2.a(s6VarA.j(), f5, i2Var);
                return s6VarA;
            }
            if (!r3.f17716c && s6VarA != null && s6VarA.k() == null && s6VarA.j().length >= 3) {
            }
            throw a.a();
        } catch (a unused) {
            s6 s6VarA2 = a(i2, pVar2, m4Var, map, i2Var);
            if (s6VarA2 == null) {
                throw a.a();
            }
            if (0 != 0) {
                s6VarA2.a();
                s6VarA2.b((u6[]) null);
                k2.a(s6VarA2.j(), f5, i2Var);
            }
            return s6VarA2;
        }
    }

    public o6[] a(Map<l1, ?> map) {
        Collection collection = map == null ? null : (Collection) map.get(l1.f17489c);
        ArrayList arrayList = new ArrayList();
        if (collection != null) {
            if (collection.contains(BarcodeFormat.UPC_A) || collection.contains(BarcodeFormat.UPC_E) || collection.contains(BarcodeFormat.EAN_13) || collection.contains(BarcodeFormat.EAN_8) || collection.contains(BarcodeFormat.CODABAR) || collection.contains(BarcodeFormat.CODE_39) || collection.contains(BarcodeFormat.CODE_93) || collection.contains(BarcodeFormat.CODE_128) || collection.contains(BarcodeFormat.ITF)) {
                arrayList.add(new z4(map));
            }
            if (collection.contains(BarcodeFormat.QR_CODE)) {
                arrayList.add(new j6());
            }
            if (collection.contains(BarcodeFormat.DATA_MATRIX)) {
                arrayList.add(new h1());
            }
            if (collection.contains(BarcodeFormat.AZTEC)) {
                arrayList.add(new h());
            }
            if (collection.contains(BarcodeFormat.PDF_417)) {
                arrayList.add(new r5());
            }
            if (collection.contains(BarcodeFormat.HARMONY_CODE)) {
                arrayList.add(new z3());
            }
            if (collection.contains(BarcodeFormat.WXCODE)) {
                arrayList.add(new m8());
            }
        }
        return (o6[]) arrayList.toArray(new o6[arrayList.size()]);
    }

    private s6 a(p pVar, o6[] o6VarArr, Map<l1, ?> map) throws a {
        if (o6VarArr != null) {
            for (o6 o6Var : o6VarArr) {
                try {
                    s6 s6VarA = o6Var.a(pVar, map);
                    if (s6VarA != null && s6VarA.j() != null) {
                        int i2 = 0;
                        for (u6 u6Var : s6VarA.j()) {
                            if (u6Var != null) {
                                i2++;
                            }
                        }
                        if (i2 == 0 && s6VarA.c() == BarcodeFormat.PDF_417) {
                            throw a.a();
                        }
                    }
                    return s6VarA;
                } catch (a unused) {
                }
            }
        }
        throw a.a();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.huawei.hms.scankit.p.s6 a(com.huawei.hms.scankit.p.p r11, com.huawei.hms.scankit.p.p r12, java.util.Map<com.huawei.hms.scankit.p.l1, java.lang.Object> r13, com.huawei.hms.scankit.p.m4 r14, com.huawei.hms.scankit.p.i2 r15) throws com.huawei.hms.scankit.p.a {
        /*
            r10 = this;
            com.huawei.hms.scankit.p.l1 r0 = com.huawei.hms.scankit.p.l1.f17490d
            boolean r0 = r13.containsKey(r0)
            r1 = 1065353216(0x3f800000, float:1.0)
            r2 = 2
            float[] r7 = new float[r2]
            r7 = {x0064: FILL_ARRAY_DATA , data: [1065353216, 0} // fill-array
            r2 = 1
            r9 = 0
            if (r12 == 0) goto L1d
            r3 = r10
            r4 = r12
            r5 = r14
            r6 = r13
            r8 = r15
            com.huawei.hms.scankit.p.s6 r12 = r3.a(r4, r5, r6, r7, r8)
        L1b:
            r13 = r9
            goto L31
        L1d:
            if (r0 != 0) goto L26
            boolean r12 = com.huawei.hms.scankit.p.r3.f17714a
            if (r12 != 0) goto L24
            goto L26
        L24:
            r12 = 0
            goto L1b
        L26:
            com.huawei.hms.scankit.p.s6 r12 = r10.a(r11, r14, r13, r7)
            r13 = r7[r2]
            int r13 = (r13 > r1 ? 1 : (r13 == r1 ? 0 : -1))
            if (r13 <= 0) goto L1b
            r13 = r2
        L31:
            if (r12 == 0) goto L5f
            if (r13 == 0) goto L5e
            com.huawei.hms.scankit.p.u6[] r13 = r12.j()
            if (r13 == 0) goto L5e
        L3b:
            int r14 = r13.length
            if (r9 >= r14) goto L5e
            r14 = r13[r9]
            if (r14 == 0) goto L5c
            com.huawei.hms.scankit.p.u6 r14 = new com.huawei.hms.scankit.p.u6
            r15 = r13[r9]
            float r15 = r15.c()
            int r0 = r11.c()
            int r0 = r0 - r2
            float r0 = (float) r0
            r1 = r13[r9]
            float r1 = r1.b()
            float r0 = r0 - r1
            r14.<init>(r15, r0)
            r13[r9] = r14
        L5c:
            int r9 = r9 + r2
            goto L3b
        L5e:
            return r12
        L5f:
            com.huawei.hms.scankit.p.a r11 = com.huawei.hms.scankit.p.a.a()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.a5.a(com.huawei.hms.scankit.p.p, com.huawei.hms.scankit.p.p, java.util.Map, com.huawei.hms.scankit.p.m4, com.huawei.hms.scankit.p.i2):com.huawei.hms.scankit.p.s6");
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.huawei.hms.scankit.p.s6 a(com.huawei.hms.scankit.p.p r11, com.huawei.hms.scankit.p.m4 r12, java.util.Map<com.huawei.hms.scankit.p.l1, java.lang.Object> r13, float[] r14, com.huawei.hms.scankit.p.i2 r15) throws com.huawei.hms.scankit.p.a {
        /*
            r10 = this;
            float r0 = r15.n()
            int r1 = r11.e()
            int r2 = r11.c()
            if (r1 >= r2) goto L13
            int r1 = r11.e()
            goto L17
        L13:
            int r1 = r11.c()
        L17:
            float r1 = (float) r1
            r2 = 1140457472(0x43fa0000, float:500.0)
            float r2 = r1 / r2
            r3 = 1065353216(0x3f800000, float:1.0)
            int r4 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r4 >= 0) goto L23
            r2 = r3
        L23:
            com.huawei.hms.scankit.p.p r4 = r12.g(r11, r2)
            com.huawei.hms.scankit.p.o6[] r5 = r10.a(r13)
            float r6 = r15.a()
            r7 = 0
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 <= 0) goto L3e
            float r6 = r15.b()
            float r7 = r15.a()
            float r6 = r6 / r7
            goto L3f
        L3e:
            r6 = r3
        L3f:
            r7 = 0
            com.huawei.hms.scankit.p.s6 r8 = r10.a(r4, r5, r13)     // Catch: com.huawei.hms.scankit.p.a -> L53
            float r9 = r0 / r2
            boolean r9 = a(r8, r9, r6)     // Catch: com.huawei.hms.scankit.p.a -> L52
            if (r9 != 0) goto L4d
            goto La5
        L4d:
            com.huawei.hms.scankit.p.a r8 = com.huawei.hms.scankit.p.a.a()     // Catch: com.huawei.hms.scankit.p.a -> L53
            throw r8     // Catch: com.huawei.hms.scankit.p.a -> L53
        L52:
            r7 = r8
        L53:
            boolean r8 = com.huawei.hms.scankit.p.r3.f17729p
            if (r8 == 0) goto La4
            r2 = 1132068864(0x437a0000, float:250.0)
            float r1 = r1 / r2
            int r2 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r2 >= 0) goto L5f
            goto L60
        L5f:
            r3 = r1
        L60:
            com.huawei.hms.scankit.p.p r4 = r12.f(r11, r3)
            com.huawei.hms.scankit.p.l1 r11 = com.huawei.hms.scankit.p.l1.f17491e     // Catch: com.huawei.hms.scankit.p.a -> L85
            r1 = 2
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch: com.huawei.hms.scankit.p.a -> L85
            r13.put(r11, r1)     // Catch: com.huawei.hms.scankit.p.a -> L85
            com.huawei.hms.scankit.p.p r11 = r12.e(r4)     // Catch: com.huawei.hms.scankit.p.a -> L85
            com.huawei.hms.scankit.p.s6 r8 = r10.a(r11, r5, r13)     // Catch: com.huawei.hms.scankit.p.a -> L85
            float r11 = r0 / r3
            boolean r11 = a(r8, r11, r6)     // Catch: com.huawei.hms.scankit.p.a -> L85
            if (r11 != 0) goto L80
        L7e:
            r2 = r3
            goto La5
        L80:
            com.huawei.hms.scankit.p.a r11 = com.huawei.hms.scankit.p.a.a()     // Catch: com.huawei.hms.scankit.p.a -> L85
            throw r11     // Catch: com.huawei.hms.scankit.p.a -> L85
        L85:
            com.huawei.hms.scankit.p.l1 r11 = com.huawei.hms.scankit.p.l1.f17491e
            r1 = 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r13.put(r11, r1)
            com.huawei.hms.scankit.p.p r11 = r12.f(r4)
            com.huawei.hms.scankit.p.s6 r8 = r10.a(r11, r5, r13)
            float r0 = r0 / r3
            boolean r11 = a(r8, r0, r6)
            if (r11 != 0) goto L9f
            goto L7e
        L9f:
            com.huawei.hms.scankit.p.a r11 = com.huawei.hms.scankit.p.a.a()
            throw r11
        La4:
            r8 = r7
        La5:
            r11 = 0
            r14[r11] = r2
            if (r8 == 0) goto Lb1
            com.huawei.hms.scankit.p.s r11 = r4.b()
            com.huawei.hms.scankit.p.k2.a(r11, r8, r2, r15)
        Lb1:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.a5.a(com.huawei.hms.scankit.p.p, com.huawei.hms.scankit.p.m4, java.util.Map, float[], com.huawei.hms.scankit.p.i2):com.huawei.hms.scankit.p.s6");
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x005c, code lost:
    
        r10 = r5;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v1, types: [com.huawei.hms.scankit.p.m4] */
    /* JADX WARN: Type inference failed for: r10v10, types: [com.huawei.hms.scankit.p.s6] */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r10v7, types: [com.huawei.hms.scankit.p.s6] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.huawei.hms.scankit.p.s6 a(com.huawei.hms.scankit.p.p r9, com.huawei.hms.scankit.p.m4 r10, java.util.Map<com.huawei.hms.scankit.p.l1, java.lang.Object> r11, float[] r12) throws com.huawei.hms.scankit.p.a {
        /*
            r8 = this;
            int r0 = r9.c()
            int r1 = r9.e()
            int r0 = java.lang.Math.min(r0, r1)
            float r0 = (float) r0
            r1 = 1065353216(0x3f800000, float:1.0)
            float r0 = r0 * r1
            r2 = 1149698048(0x44870000, float:1080.0)
            float r0 = r0 / r2
            boolean r2 = com.huawei.hms.scankit.p.r3.f17714a
            if (r2 == 0) goto L1c
            com.huawei.hms.scankit.p.p r9 = r10.a(r9, r0)
            goto L28
        L1c:
            r2 = 1069547520(0x3fc00000, float:1.5)
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto L23
            r1 = r0
        L23:
            com.huawei.hms.scankit.p.p r9 = r10.a(r9, r1)
            r0 = r1
        L28:
            com.huawei.hms.scankit.p.o6[] r1 = r8.a(r11)
            boolean r2 = com.huawei.hms.scankit.p.r3.f17715b
            r3 = 0
            r4 = 0
            if (r2 != 0) goto L44
            boolean r2 = com.huawei.hms.scankit.p.r3.f17714a
            if (r2 == 0) goto L44
            com.huawei.hms.scankit.p.l1 r10 = com.huawei.hms.scankit.p.l1.f17491e
            java.lang.Integer r2 = java.lang.Integer.valueOf(r3)
            r11.put(r10, r2)
            com.huawei.hms.scankit.p.s6 r10 = r8.a(r9, r1, r11)
            goto L80
        L44:
            r2 = r3
            r5 = r4
        L46:
            r6 = 2
            if (r2 >= r6) goto L5c
            r6 = 1
            if (r2 != r6) goto L5e
            com.huawei.hms.scankit.p.p r7 = com.huawei.hms.scankit.p.l4.a(r9)
            com.huawei.hms.scankit.p.p r7 = r10.c(r7)     // Catch: com.huawei.hms.scankit.p.a -> L7d
            com.huawei.hms.scankit.p.s6 r5 = r8.a(r7, r1, r11)     // Catch: com.huawei.hms.scankit.p.a -> L7d
            r7 = 1073741824(0x40000000, float:2.0)
            r12[r6] = r7     // Catch: com.huawei.hms.scankit.p.a -> L7d
        L5c:
            r10 = r5
            goto L80
        L5e:
            com.huawei.hms.scankit.p.p r7 = r10.b(r9)     // Catch: com.huawei.hms.scankit.p.a -> L67
            com.huawei.hms.scankit.p.s6 r10 = r8.a(r7, r1, r11)     // Catch: com.huawei.hms.scankit.p.a -> L67
            goto L80
        L67:
            boolean r7 = com.huawei.hms.scankit.p.r3.f17729p     // Catch: com.huawei.hms.scankit.p.a -> L7d
            if (r7 == 0) goto L7d
            com.huawei.hms.scankit.p.l1 r7 = com.huawei.hms.scankit.p.l1.f17491e     // Catch: com.huawei.hms.scankit.p.a -> L7d
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: com.huawei.hms.scankit.p.a -> L7d
            r11.put(r7, r6)     // Catch: com.huawei.hms.scankit.p.a -> L7d
            com.huawei.hms.scankit.p.p r6 = r10.d(r9)     // Catch: com.huawei.hms.scankit.p.a -> L7d
            com.huawei.hms.scankit.p.s6 r10 = r8.a(r6, r1, r11)     // Catch: com.huawei.hms.scankit.p.a -> L7d
            goto L80
        L7d:
            int r2 = r2 + 1
            goto L46
        L80:
            if (r10 == 0) goto L89
            com.huawei.hms.scankit.p.s r9 = r9.b()
            com.huawei.hms.scankit.p.k2.a(r9, r10, r0, r4)
        L89:
            r12[r3] = r0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.a5.a(com.huawei.hms.scankit.p.p, com.huawei.hms.scankit.p.m4, java.util.Map, float[]):com.huawei.hms.scankit.p.s6");
    }

    public s6 a(p pVar, p pVar2, p pVar3, Map<l1, ?> map, m4 m4Var, i2 i2Var) throws a {
        s6 s6VarA = pVar3 != null ? a(pVar3, m4Var, map, i2Var) : null;
        if (s6VarA != null) {
            return s6VarA;
        }
        throw a.a();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007d A[Catch: a -> 0x0090, TryCatch #2 {a -> 0x0090, blocks: (B:29:0x0060, B:31:0x007d, B:33:0x0083, B:35:0x008b, B:36:0x008f), top: B:48:0x0060 }] */
    /* JADX WARN: Type inference failed for: r4v5, types: [com.huawei.hms.scankit.p.u6[]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.huawei.hms.scankit.p.s6 a(com.huawei.hms.scankit.p.p r9, com.huawei.hms.scankit.p.m4 r10, java.util.Map<com.huawei.hms.scankit.p.l1, ?> r11, com.huawei.hms.scankit.p.i2 r12) throws com.huawei.hms.scankit.p.a {
        /*
            r8 = this;
            int r0 = r9.e()
            int r1 = r9.c()
            if (r0 >= r1) goto Lb
            goto Lc
        Lb:
            r0 = r1
        Lc:
            float r0 = (float) r0
            r1 = 1065353216(0x3f800000, float:1.0)
            float r0 = r0 * r1
            r1 = 1147207680(0x44610000, float:900.0)
            float r0 = r0 / r1
            com.huawei.hms.scankit.p.p r9 = r10.i(r9, r0)
            r10 = 0
            r1 = 0
            com.huawei.hms.scankit.p.o6[] r2 = r8.a(r11)     // Catch: com.huawei.hms.scankit.p.a -> L5e
            com.huawei.hms.scankit.p.s6 r2 = r8.a(r9, r2, r11)     // Catch: com.huawei.hms.scankit.p.a -> L5e
            if (r2 == 0) goto L31
            java.lang.String r3 = r2.k()     // Catch: com.huawei.hms.scankit.p.a -> L5c
            if (r3 == 0) goto L31
            com.huawei.hms.scankit.p.u6[] r3 = r2.j()     // Catch: com.huawei.hms.scankit.p.a -> L5c
            com.huawei.hms.scankit.p.k2.a(r3, r0, r12)     // Catch: com.huawei.hms.scankit.p.a -> L5c
            return r2
        L31:
            boolean r3 = com.huawei.hms.scankit.p.r3.f17716c     // Catch: com.huawei.hms.scankit.p.a -> L5c
            if (r3 != 0) goto L56
            if (r2 == 0) goto L56
            java.lang.String r3 = r2.k()     // Catch: com.huawei.hms.scankit.p.a -> L5c
            if (r3 != 0) goto L56
            com.huawei.hms.scankit.p.u6[] r3 = r2.j()     // Catch: com.huawei.hms.scankit.p.a -> L5c
            int r3 = r3.length     // Catch: com.huawei.hms.scankit.p.a -> L5c
            r4 = 3
            if (r3 < r4) goto L56
            r3 = 1
            com.huawei.hms.scankit.p.u6[] r4 = r2.j()     // Catch: com.huawei.hms.scankit.p.a -> L52
            java.lang.Object r4 = r4.clone()     // Catch: com.huawei.hms.scankit.p.a -> L52
            com.huawei.hms.scankit.p.u6[] r4 = (com.huawei.hms.scankit.p.u6[]) r4     // Catch: com.huawei.hms.scankit.p.a -> L52
            r10 = r4
            goto L57
        L52:
            r7 = r2
            r2 = r10
            r10 = r7
            goto L60
        L56:
            r3 = r1
        L57:
            com.huawei.hms.scankit.p.a r4 = com.huawei.hms.scankit.p.a.a()     // Catch: com.huawei.hms.scankit.p.a -> L52
            throw r4     // Catch: com.huawei.hms.scankit.p.a -> L52
        L5c:
            r3 = r1
            goto L52
        L5e:
            r2 = r10
            r3 = r1
        L60:
            byte[] r4 = r9.d()     // Catch: com.huawei.hms.scankit.p.a -> L90
            int r5 = r9.e()     // Catch: com.huawei.hms.scankit.p.a -> L90
            int r6 = r9.c()     // Catch: com.huawei.hms.scankit.p.a -> L90
            com.huawei.hms.scankit.p.s r1 = com.huawei.hms.scankit.p.n1.a(r4, r5, r6, r1)     // Catch: com.huawei.hms.scankit.p.a -> L90
            r9.a(r1)     // Catch: com.huawei.hms.scankit.p.a -> L90
            com.huawei.hms.scankit.p.o6[] r1 = r8.a(r11)     // Catch: com.huawei.hms.scankit.p.a -> L90
            com.huawei.hms.scankit.p.s6 r10 = r8.a(r9, r1, r11)     // Catch: com.huawei.hms.scankit.p.a -> L90
            if (r10 == 0) goto L8b
            java.lang.String r9 = r10.k()     // Catch: com.huawei.hms.scankit.p.a -> L90
            if (r9 == 0) goto L8b
            com.huawei.hms.scankit.p.u6[] r9 = r10.j()     // Catch: com.huawei.hms.scankit.p.a -> L90
            com.huawei.hms.scankit.p.k2.a(r9, r0, r12)     // Catch: com.huawei.hms.scankit.p.a -> L90
            return r10
        L8b:
            com.huawei.hms.scankit.p.a r9 = com.huawei.hms.scankit.p.a.a()     // Catch: com.huawei.hms.scankit.p.a -> L90
            throw r9     // Catch: com.huawei.hms.scankit.p.a -> L90
        L90:
            if (r10 == 0) goto La2
            if (r3 == 0) goto La1
            r10.a()
            r10.b(r2)
            com.huawei.hms.scankit.p.u6[] r9 = r10.j()
            com.huawei.hms.scankit.p.k2.a(r9, r0, r12)
        La1:
            return r10
        La2:
            com.huawei.hms.scankit.p.a r9 = com.huawei.hms.scankit.p.a.a()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.a5.a(com.huawei.hms.scankit.p.p, com.huawei.hms.scankit.p.m4, java.util.Map, com.huawei.hms.scankit.p.i2):com.huawei.hms.scankit.p.s6");
    }

    private s6 a(int i2, p pVar, m4 m4Var, Map<l1, ?> map, i2 i2Var) throws a {
        float f2 = (i2 * 1.0f) / 250.0f;
        if (f2 < 1.0f) {
            f2 = 1.0f;
        }
        p pVarF = m4Var.f(pVar, f2);
        o6[] o6VarArrA = a(map);
        try {
            try {
                s6 s6VarA = a(m4Var.e(pVarF), o6VarArrA, map);
                if (s6VarA != null && s6VarA.k() != null) {
                    k2.a(s6VarA.j(), f2, i2Var);
                    return s6VarA;
                }
                throw a.a();
            } catch (a unused) {
                s6 s6VarA2 = a(m4Var.f(pVarF), o6VarArrA, map);
                if (s6VarA2 != null && s6VarA2.k() != null) {
                    k2.a(s6VarA2.j(), f2, i2Var);
                    return s6VarA2;
                }
                throw a.a();
            }
        } catch (a unused2) {
            s6 s6VarA3 = a(new p(new e4(pVar.a().c())), o6VarArrA, map);
            if (s6VarA3 != null && s6VarA3.k() != null) {
                k2.a(s6VarA3.j(), 1.0f, i2Var);
            }
            return s6VarA3;
        }
    }

    public s6 a(p pVar, p pVar2, m4 m4Var, Map<l1, ?> map) throws a {
        p pVarA;
        int iE = pVar.e();
        int iC = pVar.c();
        int i2 = iE < iC ? iE : iC;
        float f2 = (i2 * 1.0f) / 1080.0f;
        if (f2 <= 1.0f) {
            f2 = 1.0f;
        }
        if (r3.f17714a) {
            pVarA = m4Var.a(pVar, f2);
        } else {
            float f3 = f2 > 1.5f ? f2 : 1.0f;
            float f4 = f3;
            pVarA = m4Var.a(pVar, f3);
            f2 = f4;
        }
        try {
            s6 s6VarA = a(pVarA, a(map), map);
            if (s6VarA != null && s6VarA.k() != null) {
                k2.a(s6VarA.j(), f2, (i2) null);
                return s6VarA;
            }
            if (!r3.f17716c && s6VarA != null && s6VarA.k() == null && s6VarA.j().length >= 3) {
            }
            throw a.a();
        } catch (a unused) {
            s6 s6VarA2 = a(i2, m4Var, pVar, pVar2, map);
            if (s6VarA2 == null) {
                throw a.a();
            }
            if (0 != 0) {
                s6VarA2.a();
                s6VarA2.b((u6[]) null);
            }
            return s6VarA2;
        }
    }

    private s6 a(int i2, m4 m4Var, p pVar, p pVar2, Map<l1, ?> map) throws a {
        o6[] o6VarArrA = a(map);
        try {
            if (r3.f17714a) {
                float f2 = (i2 * 1.0f) / 500.0f;
                if (f2 <= 1.0f) {
                    f2 = 1.0f;
                }
                s6 s6VarA = a(m4Var.g(m4Var.g(pVar, f2)), o6VarArrA, map);
                if (s6VarA != null && s6VarA.k() != null) {
                    k2.a(s6VarA.j(), f2, (i2) null);
                    return s6VarA;
                }
            }
            throw a.a();
        } catch (a unused) {
            float f3 = (i2 * 1.0f) / 1080.0f;
            float f4 = f3 > 1.0f ? f3 : 1.0f;
            s6 s6VarA2 = a(m4Var.b(pVar2, f4), o6VarArrA, map);
            if (s6VarA2 != null && s6VarA2.k() != null) {
                k2.a(s6VarA2.j(), f4, (i2) null);
            }
            return s6VarA2;
        }
    }
}
