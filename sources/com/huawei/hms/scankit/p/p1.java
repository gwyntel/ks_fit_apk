package com.huawei.hms.scankit.p;

import com.google.zxing.common.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
final class p1 {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f17659a = "0123456789abcdefghijklmnopqrstuvwxyz !-./:=?T".toCharArray();

    /* renamed from: b, reason: collision with root package name */
    private static final char[] f17660b = "0123456789ABCDEF".toCharArray();

    static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f17661a;

        static {
            int[] iArr = new int[v4.values().length];
            f17661a = iArr;
            try {
                iArr[v4.TERMINATOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f17661a[v4.FNC1_FIRST_POSITION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f17661a[v4.FNC1_SECOND_POSITION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f17661a[v4.STRUCTURED_APPEND.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f17661a[v4.ECI.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f17661a[v4.HANZI.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f17661a[v4.NUMERIC.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f17661a[v4.ALPHANUMERIC.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f17661a[v4.HEXADECIMAL.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f17661a[v4.HEXABYTE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f17661a[v4.BYTE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f17661a[v4.KANJI.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x009e A[LOOP:0: B:34:0x001b->B:31:0x009e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x007d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static com.huawei.hms.scankit.p.w1 a(byte[] r15, com.huawei.hms.scankit.p.a8 r16, com.huawei.hms.scankit.p.c3 r17, java.util.Map<com.huawei.hms.scankit.p.l1, ?> r18) throws com.huawei.hms.scankit.p.a {
        /*
            r0 = r16
            com.huawei.hms.scankit.p.w r1 = new com.huawei.hms.scankit.p.w
            r3 = r15
            r1.<init>(r15)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r4 = 50
            r2.<init>(r4)
            java.util.ArrayList r4 = new java.util.ArrayList
            r5 = 1
            r4.<init>(r5)
            r6 = -1
            r7 = 0
            r8 = 0
            r10 = r7
            r9 = r8
            r8 = r6
        L1b:
            int r11 = r1.a()     // Catch: java.lang.IllegalArgumentException -> La4
            r12 = 4
            if (r11 >= r12) goto L25
            com.huawei.hms.scankit.p.v4 r11 = com.huawei.hms.scankit.p.v4.TERMINATOR     // Catch: java.lang.IllegalArgumentException -> La4
            goto L2d
        L25:
            int r11 = r1.a(r12)     // Catch: java.lang.IllegalArgumentException -> La4
            com.huawei.hms.scankit.p.v4 r11 = com.huawei.hms.scankit.p.v4.a(r11)     // Catch: java.lang.IllegalArgumentException -> La4
        L2d:
            int[] r12 = com.huawei.hms.scankit.p.p1.a.f17661a     // Catch: java.lang.IllegalArgumentException -> La4
            int r13 = r11.ordinal()     // Catch: java.lang.IllegalArgumentException -> La4
            r12 = r12[r13]     // Catch: java.lang.IllegalArgumentException -> La4
            switch(r12) {
                case 1: goto L76;
                case 2: goto L70;
                case 3: goto L70;
                case 4: goto L53;
                case 5: goto L48;
                case 6: goto L42;
                default: goto L38;
            }     // Catch: java.lang.IllegalArgumentException -> La4
        L38:
            r12 = r18
            com.huawei.hms.scankit.p.z0 r13 = a(r0, r12, r1, r2)     // Catch: java.lang.IllegalArgumentException -> La4
            a(r13, r4, r10, r9, r11)     // Catch: java.lang.IllegalArgumentException -> La4
            goto L4e
        L42:
            r12 = r18
            a(r0, r1, r2, r11)     // Catch: java.lang.IllegalArgumentException -> La4
            goto L4e
        L48:
            r12 = r18
            com.huawei.hms.scankit.p.o0 r10 = a(r1)     // Catch: java.lang.IllegalArgumentException -> La4
        L4e:
            r14 = r8
            r8 = r6
            r6 = r9
            r9 = r14
            goto L79
        L53:
            r12 = r18
            int r6 = r1.a()     // Catch: java.lang.IllegalArgumentException -> La4
            r8 = 16
            if (r6 < r8) goto L6b
            r6 = 8
            int r8 = r1.a(r6)     // Catch: java.lang.IllegalArgumentException -> La4
            int r6 = r1.a(r6)     // Catch: java.lang.IllegalArgumentException -> La4
            r14 = r9
            r9 = r6
            r6 = r14
            goto L79
        L6b:
            com.huawei.hms.scankit.p.a r0 = com.huawei.hms.scankit.p.a.a()     // Catch: java.lang.IllegalArgumentException -> La4
            throw r0     // Catch: java.lang.IllegalArgumentException -> La4
        L70:
            r12 = r18
            r9 = r8
            r8 = r6
            r6 = r5
            goto L79
        L76:
            r12 = r18
            goto L4e
        L79:
            com.huawei.hms.scankit.p.v4 r13 = com.huawei.hms.scankit.p.v4.TERMINATOR     // Catch: java.lang.IllegalArgumentException -> La4
            if (r11 != r13) goto L9e
            com.huawei.hms.scankit.p.w1 r0 = new com.huawei.hms.scankit.p.w1
            java.lang.String r1 = r2.toString()
            boolean r2 = r4.isEmpty()
            if (r2 == 0) goto L8b
            r5 = r7
            goto L8c
        L8b:
            r5 = r4
        L8c:
            if (r17 != 0) goto L90
            r6 = r7
            goto L95
        L90:
            java.lang.String r2 = r17.toString()
            r6 = r2
        L95:
            r2 = r0
            r3 = r15
            r4 = r1
            r7 = r8
            r8 = r9
            r2.<init>(r3, r4, r5, r6, r7, r8)
            return r0
        L9e:
            r14 = r9
            r9 = r6
            r6 = r8
            r8 = r14
            goto L1b
        La4:
            com.huawei.hms.scankit.p.a r0 = com.huawei.hms.scankit.p.a.a()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.p1.a(byte[], com.huawei.hms.scankit.p.a8, com.huawei.hms.scankit.p.c3, java.util.Map):com.huawei.hms.scankit.p.w1");
    }

    private static void b(w wVar, StringBuilder sb, int i2) throws com.huawei.hms.scankit.p.a {
        if (i2 * 13 > wVar.a()) {
            throw com.huawei.hms.scankit.p.a.a();
        }
        byte[] bArr = new byte[i2 * 2];
        int i3 = 0;
        while (i2 > 0) {
            int iA = wVar.a(13);
            int i4 = (iA % 192) | ((iA / 192) << 8);
            int i5 = i4 + (i4 < 7936 ? 33088 : 49472);
            bArr[i3] = (byte) (i5 >> 8);
            bArr[i3 + 1] = (byte) i5;
            i3 += 2;
            i2--;
        }
        try {
            sb.append(new String(bArr, StringUtils.SHIFT_JIS));
        } catch (UnsupportedEncodingException unused) {
            throw com.huawei.hms.scankit.p.a.a();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void c(com.huawei.hms.scankit.p.w r3, java.lang.StringBuilder r4, int r5, boolean r6) throws com.huawei.hms.scankit.p.a {
        /*
            int r0 = r4.length()
        L4:
            r1 = 1
            if (r5 <= r1) goto L2d
            int r1 = r3.a()
            r2 = 8
            if (r1 < r2) goto L28
            int r1 = r3.a(r2)
            int r2 = r1 / 16
            char r2 = b(r2)
            r4.append(r2)
            int r1 = r1 % 16
            char r1 = b(r1)
            r4.append(r1)
            int r5 = r5 + (-2)
            goto L4
        L28:
            com.huawei.hms.scankit.p.a r3 = com.huawei.hms.scankit.p.a.a()
            throw r3
        L2d:
            if (r5 != r1) goto L47
            int r5 = r3.a()
            r2 = 4
            if (r5 < r2) goto L42
            int r3 = r3.a(r2)
            char r3 = b(r3)
            r4.append(r3)
            goto L47
        L42:
            com.huawei.hms.scankit.p.a r3 = com.huawei.hms.scankit.p.a.a()
            throw r3
        L47:
            if (r6 == 0) goto L72
        L49:
            int r3 = r4.length()
            if (r0 >= r3) goto L72
            char r3 = r4.charAt(r0)
            r5 = 37
            if (r3 != r5) goto L6f
            int r3 = r4.length()
            int r3 = r3 - r1
            if (r0 >= r3) goto L6a
            int r3 = r0 + 1
            char r6 = r4.charAt(r3)
            if (r6 != r5) goto L6a
            r4.deleteCharAt(r3)
            goto L6f
        L6a:
            r3 = 29
            r4.setCharAt(r0, r3)
        L6f:
            int r0 = r0 + 1
            goto L49
        L72:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.p1.c(com.huawei.hms.scankit.p.w, java.lang.StringBuilder, int, boolean):void");
    }

    private static char b(int i2) throws com.huawei.hms.scankit.p.a {
        char[] cArr = f17660b;
        if (i2 < cArr.length) {
            return cArr[i2];
        }
        throw com.huawei.hms.scankit.p.a.a();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void b(com.huawei.hms.scankit.p.w r3, java.lang.StringBuilder r4, int r5, boolean r6) throws com.huawei.hms.scankit.p.a {
        /*
            int r0 = r4.length()
        L4:
            r1 = 1
            if (r5 <= r1) goto L2d
            int r1 = r3.a()
            r2 = 8
            if (r1 < r2) goto L28
            int r1 = r3.a(r2)
            int r2 = r1 / 16
            char r2 = b(r2)
            r4.append(r2)
            int r1 = r1 % 16
            char r1 = b(r1)
            r4.append(r1)
            int r5 = r5 + (-2)
            goto L4
        L28:
            com.huawei.hms.scankit.p.a r3 = com.huawei.hms.scankit.p.a.a()
            throw r3
        L2d:
            if (r5 != r1) goto L47
            int r5 = r3.a()
            r2 = 4
            if (r5 < r2) goto L42
            int r3 = r3.a(r2)
            char r3 = b(r3)
            r4.append(r3)
            goto L47
        L42:
            com.huawei.hms.scankit.p.a r3 = com.huawei.hms.scankit.p.a.a()
            throw r3
        L47:
            if (r6 == 0) goto L72
        L49:
            int r3 = r4.length()
            if (r0 >= r3) goto L72
            char r3 = r4.charAt(r0)
            r5 = 37
            if (r3 != r5) goto L6f
            int r3 = r4.length()
            int r3 = r3 - r1
            if (r0 >= r3) goto L6a
            int r3 = r0 + 1
            char r6 = r4.charAt(r3)
            if (r6 != r5) goto L6a
            r4.deleteCharAt(r3)
            goto L6f
        L6a:
            r3 = 29
            r4.setCharAt(r0, r3)
        L6f:
            int r0 = r0 + 1
            goto L49
        L72:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.p1.b(com.huawei.hms.scankit.p.w, java.lang.StringBuilder, int, boolean):void");
    }

    private static void c(w wVar, StringBuilder sb, int i2) throws com.huawei.hms.scankit.p.a {
        while (i2 >= 3) {
            if (wVar.a() >= 10) {
                int iA = wVar.a(10);
                if (iA < 1000) {
                    sb.append(a(iA / 100));
                    sb.append(a((iA / 10) % 10));
                    sb.append(a(iA % 10));
                    i2 -= 3;
                } else {
                    throw com.huawei.hms.scankit.p.a.a();
                }
            } else {
                throw com.huawei.hms.scankit.p.a.a();
            }
        }
        if (i2 == 2) {
            if (wVar.a() >= 7) {
                int iA2 = wVar.a(7);
                if (iA2 < 100) {
                    sb.append(a(iA2 / 10));
                    sb.append(a(iA2 % 10));
                    return;
                }
                throw com.huawei.hms.scankit.p.a.a();
            }
            throw com.huawei.hms.scankit.p.a.a();
        }
        if (i2 == 1) {
            if (wVar.a() >= 4) {
                int iA3 = wVar.a(4);
                if (iA3 < 10) {
                    sb.append(a(iA3));
                    return;
                }
                throw com.huawei.hms.scankit.p.a.a();
            }
            throw com.huawei.hms.scankit.p.a.a();
        }
    }

    private static z0 a(a8 a8Var, Map<l1, ?> map, w wVar, StringBuilder sb) {
        return new z0(a8Var, map, wVar, sb);
    }

    private static void a(z0 z0Var, List<byte[]> list, o0 o0Var, boolean z2, v4 v4Var) throws com.huawei.hms.scankit.p.a {
        a(new h7(z0Var.f18043b, z0Var.f18044c, z0Var.f18045d, list), o0Var, z2, v4Var, z0Var.f18044c.a(v4Var.a(z0Var.f18042a)));
    }

    private static o0 a(w wVar) throws com.huawei.hms.scankit.p.a {
        o0 o0VarA = o0.a(b(wVar));
        a(o0VarA);
        return o0VarA;
    }

    private static int b(w wVar) throws com.huawei.hms.scankit.p.a {
        int iA = wVar.a(8);
        if ((iA & 128) == 0) {
            return iA & 127;
        }
        if ((iA & 192) == 128) {
            return wVar.a(8) | ((iA & 63) << 8);
        }
        if ((iA & 224) == 192) {
            return wVar.a(16) | ((iA & 31) << 16);
        }
        throw com.huawei.hms.scankit.p.a.a();
    }

    private static void a(o0 o0Var) throws com.huawei.hms.scankit.p.a {
        if (o0Var == null) {
            throw com.huawei.hms.scankit.p.a.a();
        }
    }

    private static void a(a8 a8Var, w wVar, StringBuilder sb, v4 v4Var) throws com.huawei.hms.scankit.p.a {
        int iA = wVar.a(4);
        int iA2 = wVar.a(v4Var.a(a8Var));
        if (iA == 1) {
            a(wVar, sb, iA2);
        }
    }

    private static void a(h7 h7Var, o0 o0Var, boolean z2, v4 v4Var, int i2) throws com.huawei.hms.scankit.p.a {
        switch (a.f17661a[v4Var.ordinal()]) {
            case 7:
                c(h7Var.f17334b, h7Var.f17335c, i2);
                return;
            case 8:
                a(h7Var.f17334b, h7Var.f17335c, i2, z2);
                return;
            case 9:
                c(h7Var.f17334b, h7Var.f17335c, i2, z2);
                return;
            case 10:
                b(h7Var.f17334b, h7Var.f17335c, i2, z2);
                return;
            case 11:
                a(new b0(h7Var.f17334b, h7Var.f17335c), i2, o0Var, h7Var.f17336d, h7Var.f17333a);
                return;
            case 12:
                b(h7Var.f17334b, h7Var.f17335c, i2);
                return;
            default:
                throw com.huawei.hms.scankit.p.a.a();
        }
    }

    private static void a(w wVar, StringBuilder sb, int i2) throws com.huawei.hms.scankit.p.a {
        if (i2 * 13 <= wVar.a()) {
            byte[] bArr = new byte[i2 * 2];
            int i3 = 0;
            while (i2 > 0) {
                int iA = wVar.a(13);
                int i4 = (iA % 96) | ((iA / 96) << 8);
                int i5 = i4 + (i4 < 2560 ? 41377 : 42657);
                bArr[i3] = (byte) ((i5 >> 8) & 255);
                bArr[i3 + 1] = (byte) (i5 & 255);
                i3 += 2;
                i2--;
            }
            try {
                sb.append(new String(bArr, StringUtils.GB2312));
                return;
            } catch (UnsupportedEncodingException unused) {
                throw com.huawei.hms.scankit.p.a.a();
            }
        }
        throw com.huawei.hms.scankit.p.a.a();
    }

    private static void a(b0 b0Var, int i2, o0 o0Var, Collection<byte[]> collection, Map<l1, ?> map) throws com.huawei.hms.scankit.p.a {
        String strName;
        if (i2 * 8 <= b0Var.f16988a.a()) {
            byte[] bArr = new byte[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                bArr[i3] = (byte) b0Var.f16988a.a(8);
            }
            if (o0Var == null) {
                strName = c7.a(bArr, map);
            } else {
                strName = o0Var.name();
            }
            try {
                b0Var.f16989b.append(new String(bArr, strName));
                collection.add(bArr);
                return;
            } catch (UnsupportedEncodingException unused) {
                throw com.huawei.hms.scankit.p.a.a();
            }
        }
        throw com.huawei.hms.scankit.p.a.a();
    }

    private static char a(int i2) throws com.huawei.hms.scankit.p.a {
        char[] cArr = f17659a;
        if (i2 < cArr.length) {
            return cArr[i2];
        }
        throw com.huawei.hms.scankit.p.a.a();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(com.huawei.hms.scankit.p.w r3, java.lang.StringBuilder r4, int r5, boolean r6) throws com.huawei.hms.scankit.p.a {
        /*
            int r0 = r4.length()
        L4:
            r1 = 1
            if (r5 <= r1) goto L2d
            int r1 = r3.a()
            r2 = 11
            if (r1 < r2) goto L28
            int r1 = r3.a(r2)
            int r2 = r1 / 45
            char r2 = a(r2)
            r4.append(r2)
            int r1 = r1 % 45
            char r1 = a(r1)
            r4.append(r1)
            int r5 = r5 + (-2)
            goto L4
        L28:
            com.huawei.hms.scankit.p.a r3 = com.huawei.hms.scankit.p.a.a()
            throw r3
        L2d:
            if (r5 != r1) goto L47
            int r5 = r3.a()
            r2 = 6
            if (r5 < r2) goto L42
            int r3 = r3.a(r2)
            char r3 = a(r3)
            r4.append(r3)
            goto L47
        L42:
            com.huawei.hms.scankit.p.a r3 = com.huawei.hms.scankit.p.a.a()
            throw r3
        L47:
            if (r6 == 0) goto L72
        L49:
            int r3 = r4.length()
            if (r0 >= r3) goto L72
            char r3 = r4.charAt(r0)
            r5 = 37
            if (r3 != r5) goto L6f
            int r3 = r4.length()
            int r3 = r3 - r1
            if (r0 >= r3) goto L6a
            int r3 = r0 + 1
            char r6 = r4.charAt(r3)
            if (r6 != r5) goto L6a
            r4.deleteCharAt(r3)
            goto L6f
        L6a:
            r3 = 29
            r4.setCharAt(r0, r3)
        L6f:
            int r0 = r0 + 1
            goto L49
        L72:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.p1.a(com.huawei.hms.scankit.p.w, java.lang.StringBuilder, int, boolean):void");
    }
}
