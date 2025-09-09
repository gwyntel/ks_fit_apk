package com.huawei.hms.scankit.p;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;

/* loaded from: classes4.dex */
final class q1 {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f17677a = ";<>@[\\]_`~!\r\t,:\n-.$/\"|*()?{}'".toCharArray();

    /* renamed from: b, reason: collision with root package name */
    private static final char[] f17678b = "0123456789&\r\t,:#-.$/+%*=^".toCharArray();

    /* renamed from: c, reason: collision with root package name */
    private static final BigInteger[] f17679c;

    static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f17680a;

        static {
            int[] iArr = new int[b.values().length];
            f17680a = iArr;
            try {
                iArr[b.ALPHA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f17680a[b.LOWER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f17680a[b.MIXED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f17680a[b.PUNCT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f17680a[b.ALPHA_SHIFT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f17680a[b.PUNCT_SHIFT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private enum b {
        ALPHA,
        LOWER,
        MIXED,
        PUNCT,
        ALPHA_SHIFT,
        PUNCT_SHIFT
    }

    static {
        BigInteger[] bigIntegerArr = new BigInteger[16];
        f17679c = bigIntegerArr;
        bigIntegerArr[0] = BigInteger.ONE;
        BigInteger bigIntegerValueOf = BigInteger.valueOf(900L);
        bigIntegerArr[1] = bigIntegerValueOf;
        int i2 = 2;
        while (true) {
            BigInteger[] bigIntegerArr2 = f17679c;
            if (i2 >= bigIntegerArr2.length) {
                return;
            }
            bigIntegerArr2[i2] = bigIntegerArr2[i2 - 1].multiply(bigIntegerValueOf);
            i2++;
        }
    }

    private static boolean a(int i2) {
        return i2 == 901 || i2 == 924 || i2 == 902 || i2 == 928 || i2 == 923 || i2 == 922;
    }

    private static int b(int[] iArr, int i2, StringBuilder sb) throws com.huawei.hms.scankit.p.a {
        int i3 = iArr[0];
        int[] iArr2 = new int[(i3 - i2) * 2];
        int[] iArr3 = new int[(i3 - i2) * 2];
        boolean z2 = false;
        int i4 = 0;
        while (i2 < iArr[0] && !z2) {
            int i5 = i2 + 1;
            int i6 = iArr[i2];
            if (i6 < 900) {
                iArr2[i4] = i6 / 30;
                iArr2[i4 + 1] = i6 % 30;
                i4 += 2;
            } else if (i6 == 900) {
                iArr2[i4] = 900;
                i4++;
            } else if (i6 == 913) {
                iArr2[i4] = 913;
                i2 += 2;
                iArr3[i4] = iArr[i5];
                i4++;
            } else {
                if (!a(i6)) {
                    throw com.huawei.hms.scankit.p.a.a();
                }
                z2 = true;
            }
            i2 = i5;
        }
        a(iArr2, iArr3, i4, sb);
        return i2;
    }

    private static b[] c(StringBuilder sb, int[] iArr, b bVar, b bVar2, int[] iArr2) throws com.huawei.hms.scankit.p.a {
        b bVar3;
        int i2 = iArr2[1];
        if (i2 < 26) {
            iArr2[2] = (char) (i2 + 97);
        } else if (i2 == 900) {
            bVar = b.ALPHA;
        } else if (i2 != 913) {
            switch (i2) {
                case 26:
                    iArr2[2] = 32;
                    break;
                case 27:
                    bVar3 = b.ALPHA_SHIFT;
                    bVar2 = bVar;
                    bVar = bVar3;
                    break;
                case 28:
                    bVar = b.MIXED;
                    break;
                case 29:
                    bVar3 = b.PUNCT_SHIFT;
                    bVar2 = bVar;
                    bVar = bVar3;
                    break;
                default:
                    throw com.huawei.hms.scankit.p.a.a();
            }
        } else {
            sb.append((char) iArr[iArr2[0]]);
        }
        return new b[]{bVar, bVar2};
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.huawei.hms.scankit.p.q1.b[] d(java.lang.StringBuilder r5, int[] r6, com.huawei.hms.scankit.p.q1.b r7, com.huawei.hms.scankit.p.q1.b r8, int[] r9) throws com.huawei.hms.scankit.p.a {
        /*
            r0 = 0
            r1 = 1
            r2 = r9[r1]
            r3 = 25
            r4 = 2
            if (r2 >= r3) goto L10
            char[] r5 = com.huawei.hms.scankit.p.q1.f17678b
            char r5 = r5[r2]
            r9[r4] = r5
            goto L3b
        L10:
            r3 = 900(0x384, float:1.261E-42)
            if (r2 == r3) goto L39
            r3 = 913(0x391, float:1.28E-42)
            if (r2 == r3) goto L30
            switch(r2) {
                case 25: goto L2d;
                case 26: goto L28;
                case 27: goto L25;
                case 28: goto L39;
                case 29: goto L20;
                default: goto L1b;
            }
        L1b:
            com.huawei.hms.scankit.p.a r5 = com.huawei.hms.scankit.p.a.a()
            throw r5
        L20:
            com.huawei.hms.scankit.p.q1$b r5 = com.huawei.hms.scankit.p.q1.b.PUNCT_SHIFT
            r8 = r7
            r7 = r5
            goto L3b
        L25:
            com.huawei.hms.scankit.p.q1$b r7 = com.huawei.hms.scankit.p.q1.b.LOWER
            goto L3b
        L28:
            r5 = 32
            r9[r4] = r5
            goto L3b
        L2d:
            com.huawei.hms.scankit.p.q1$b r7 = com.huawei.hms.scankit.p.q1.b.PUNCT
            goto L3b
        L30:
            r9 = r9[r0]
            r6 = r6[r9]
            char r6 = (char) r6
            r5.append(r6)
            goto L3b
        L39:
            com.huawei.hms.scankit.p.q1$b r7 = com.huawei.hms.scankit.p.q1.b.ALPHA
        L3b:
            com.huawei.hms.scankit.p.q1$b[] r5 = new com.huawei.hms.scankit.p.q1.b[r4]
            r5[r0] = r7
            r5[r1] = r8
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.q1.d(java.lang.StringBuilder, int[], com.huawei.hms.scankit.p.q1$b, com.huawei.hms.scankit.p.q1$b, int[]):com.huawei.hms.scankit.p.q1$b[]");
    }

    private static b[] e(StringBuilder sb, int[] iArr, b bVar, b bVar2, int[] iArr2) throws com.huawei.hms.scankit.p.a {
        int i2 = iArr2[1];
        if (i2 < 29) {
            iArr2[2] = f17677a[i2];
        } else if (i2 == 29 || i2 == 900) {
            bVar = b.ALPHA;
        } else {
            if (i2 != 913) {
                throw com.huawei.hms.scankit.p.a.a();
            }
            sb.append((char) iArr[iArr2[0]]);
        }
        return new b[]{bVar, bVar2};
    }

    private static b[] f(StringBuilder sb, int[] iArr, b bVar, b bVar2, int[] iArr2) throws com.huawei.hms.scankit.p.a {
        b bVar3;
        int i2 = iArr2[1];
        if (i2 < 29) {
            iArr2[2] = f17677a[i2];
        } else {
            if (i2 == 29 || i2 == 900) {
                bVar3 = b.ALPHA;
                return new b[]{bVar3, bVar2};
            }
            if (i2 != 913) {
                throw com.huawei.hms.scankit.p.a.a();
            }
            sb.append((char) iArr[iArr2[0]]);
        }
        bVar3 = bVar2;
        return new b[]{bVar3, bVar2};
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0052, code lost:
    
        if (r6.length() == 0) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0057, code lost:
    
        if (r8 != java.nio.charset.StandardCharsets.ISO_8859_1) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0059, code lost:
    
        r9 = r6.length();
        r2 = new byte[r9];
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x005f, code lost:
    
        if (r0 >= r9) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0061, code lost:
    
        r2[r0] = (byte) r6.charAt(r0);
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0074, code lost:
    
        r9 = new com.huawei.hms.scankit.p.w1(null, new java.lang.String(r2, com.huawei.hms.scankit.p.c7.a(r2, r11)), null, r10);
        r9.a(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x007c, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0081, code lost:
    
        throw com.huawei.hms.scankit.p.a.a();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0082, code lost:
    
        r9 = new com.huawei.hms.scankit.p.w1(null, r6.toString(), null, r10);
        r9.a(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008e, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0093, code lost:
    
        throw com.huawei.hms.scankit.p.a.a();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static com.huawei.hms.scankit.p.w1 a(int[] r9, java.lang.String r10, java.util.Map<com.huawei.hms.scankit.p.l1, ?> r11) throws com.huawei.hms.scankit.p.a {
        /*
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            int r0 = r9.length
            r1 = 2
            int r0 = r0 * r1
            r6.<init>(r0)
            java.nio.charset.Charset r0 = java.nio.charset.StandardCharsets.ISO_8859_1
            r2 = 1
            r2 = r9[r2]
            com.huawei.hms.scankit.p.s5 r7 = new com.huawei.hms.scankit.p.s5
            r7.<init>()
            r8 = r0
            r3 = r1
            r1 = r2
        L15:
            r0 = 0
            r2 = r9[r0]
            if (r3 > r2) goto L4e
            if (r3 != r2) goto L23
            int r2 = r6.length()
            if (r2 <= 0) goto L23
            goto L4e
        L23:
            r0 = 927(0x39f, float:1.299E-42)
            if (r1 != r0) goto L39
            int r0 = r3 + 1
            r1 = r9[r3]
            com.huawei.hms.scankit.p.o0 r1 = com.huawei.hms.scankit.p.o0.a(r1)
            java.lang.String r1 = r1.name()
            java.nio.charset.Charset r1 = java.nio.charset.Charset.forName(r1)
            r8 = r1
            goto L41
        L39:
            r0 = r6
            r2 = r9
            r4 = r8
            r5 = r7
            int r0 = a(r0, r1, r2, r3, r4, r5)
        L41:
            int r1 = r9.length
            if (r0 >= r1) goto L49
            int r3 = r0 + 1
            r1 = r9[r0]
            goto L15
        L49:
            com.huawei.hms.scankit.p.a r9 = com.huawei.hms.scankit.p.a.a()
            throw r9
        L4e:
            int r9 = r6.length()
            if (r9 == 0) goto L8f
            java.nio.charset.Charset r9 = java.nio.charset.StandardCharsets.ISO_8859_1
            r1 = 0
            if (r8 != r9) goto L82
            int r9 = r6.length()
            byte[] r2 = new byte[r9]
        L5f:
            if (r0 >= r9) goto L6b
            char r3 = r6.charAt(r0)
            byte r3 = (byte) r3
            r2[r0] = r3
            int r0 = r0 + 1
            goto L5f
        L6b:
            java.lang.String r9 = com.huawei.hms.scankit.p.c7.a(r2, r11)
            java.lang.String r11 = new java.lang.String     // Catch: java.io.UnsupportedEncodingException -> L7d
            r11.<init>(r2, r9)     // Catch: java.io.UnsupportedEncodingException -> L7d
            com.huawei.hms.scankit.p.w1 r9 = new com.huawei.hms.scankit.p.w1
            r9.<init>(r1, r11, r1, r10)
            r9.a(r7)
            return r9
        L7d:
            com.huawei.hms.scankit.p.a r9 = com.huawei.hms.scankit.p.a.a()
            throw r9
        L82:
            com.huawei.hms.scankit.p.w1 r9 = new com.huawei.hms.scankit.p.w1
            java.lang.String r11 = r6.toString()
            r9.<init>(r1, r11, r1, r10)
            r9.a(r7)
            return r9
        L8f:
            com.huawei.hms.scankit.p.a r9 = com.huawei.hms.scankit.p.a.a()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.q1.a(int[], java.lang.String, java.util.Map):com.huawei.hms.scankit.p.w1");
    }

    private static b[] b(StringBuilder sb, int[] iArr, b bVar, b bVar2, int[] iArr2) throws com.huawei.hms.scankit.p.a {
        b bVar3;
        int i2 = iArr2[1];
        if (i2 < 26) {
            iArr2[2] = (char) (i2 + 65);
        } else {
            if (i2 != 26) {
                if (i2 == 900) {
                    bVar3 = b.ALPHA;
                    return new b[]{bVar3, bVar2};
                }
                throw com.huawei.hms.scankit.p.a.a();
            }
            iArr2[2] = 32;
        }
        bVar3 = bVar2;
        return new b[]{bVar3, bVar2};
    }

    private static int a(StringBuilder sb, int i2, int[] iArr, int i3, Charset charset, s5 s5Var) throws com.huawei.hms.scankit.p.a {
        if (i2 == 913) {
            int i4 = i3 + 1;
            sb.append((char) iArr[i3]);
            return i4;
        }
        if (i2 != 928) {
            switch (i2) {
                case TypedValues.Custom.TYPE_INT /* 900 */:
                    return b(iArr, i3, sb);
                case TypedValues.Custom.TYPE_FLOAT /* 901 */:
                    break;
                case TypedValues.Custom.TYPE_COLOR /* 902 */:
                    return a(iArr, i3, sb);
                default:
                    switch (i2) {
                        case 922:
                        case 923:
                            throw com.huawei.hms.scankit.p.a.a();
                        case 924:
                            break;
                        case 925:
                            return i3 + 1;
                        case 926:
                            return i3 + 2;
                        default:
                            return b(iArr, i3 - 1, sb);
                    }
            }
            return a(i2, iArr, charset, i3, sb);
        }
        return a(iArr, i3, s5Var);
    }

    static int a(int[] iArr, int i2, s5 s5Var) throws com.huawei.hms.scankit.p.a {
        int i3 = 0;
        if (i2 + 2 <= iArr[0]) {
            int[] iArr2 = new int[2];
            while (i3 < 2) {
                iArr2[i3] = iArr[i2];
                i3++;
                i2++;
            }
            try {
                s5Var.c(Integer.parseInt(a(iArr2, 2)));
                StringBuilder sb = new StringBuilder();
                int iB = b(iArr, i2, sb);
                s5Var.b(sb.toString());
                int i4 = iArr[iB] == 923 ? iB + 1 : -1;
                a(iB, iArr, s5Var);
                if (i4 != -1) {
                    int i5 = iB - i4;
                    if (s5Var.a()) {
                        i5--;
                    }
                    s5Var.a(Arrays.copyOfRange(iArr, i4, i5 + i4));
                }
                return iB;
            } catch (Exception unused) {
                throw com.huawei.hms.scankit.p.a.a();
            }
        }
        throw com.huawei.hms.scankit.p.a.a();
    }

    private static void a(int i2, int[] iArr, s5 s5Var) throws com.huawei.hms.scankit.p.a {
        while (i2 < iArr[0]) {
            int i3 = iArr[i2];
            if (i3 == 923) {
                int i4 = iArr[i2 + 1];
                if (i4 == 0) {
                    StringBuilder sb = new StringBuilder();
                    i2 = b(iArr, i2 + 2, sb);
                    s5Var.c(sb.toString());
                } else if (i4 == 3) {
                    StringBuilder sb2 = new StringBuilder();
                    i2 = b(iArr, i2 + 2, sb2);
                    s5Var.d(sb2.toString());
                } else if (i4 == 4) {
                    StringBuilder sb3 = new StringBuilder();
                    i2 = b(iArr, i2 + 2, sb3);
                    s5Var.a(sb3.toString());
                } else if (i4 == 1) {
                    StringBuilder sb4 = new StringBuilder();
                    i2 = a(iArr, i2 + 2, sb4);
                    s5Var.b(Integer.parseInt(sb4.toString()));
                } else if (i4 == 2) {
                    StringBuilder sb5 = new StringBuilder();
                    i2 = a(iArr, i2 + 2, sb5);
                    s5Var.b(Long.parseLong(sb5.toString()));
                } else if (i4 == 6) {
                    StringBuilder sb6 = new StringBuilder();
                    i2 = a(iArr, i2 + 2, sb6);
                    s5Var.a(Integer.parseInt(sb6.toString()));
                } else if (i4 == 5) {
                    StringBuilder sb7 = new StringBuilder();
                    i2 = a(iArr, i2 + 2, sb7);
                    s5Var.a(Long.parseLong(sb7.toString()));
                } else {
                    throw com.huawei.hms.scankit.p.a.a();
                }
            } else if (i3 == 922) {
                i2++;
                s5Var.a(true);
            } else {
                throw com.huawei.hms.scankit.p.a.a();
            }
        }
    }

    private static void a(int[] iArr, int[] iArr2, int i2, StringBuilder sb) throws com.huawei.hms.scankit.p.a {
        b bVar = b.ALPHA;
        b bVar2 = bVar;
        int i3 = 0;
        while (i3 < i2) {
            int[] iArr3 = {i3, iArr[i3], 0};
            b[] bVarArrA = {bVar, bVar2};
            switch (a.f17680a[bVar.ordinal()]) {
                case 1:
                    bVarArrA = a(sb, iArr2, bVar, bVar2, iArr3);
                    break;
                case 2:
                    bVarArrA = c(sb, iArr2, bVar, bVar2, iArr3);
                    break;
                case 3:
                    bVarArrA = d(sb, iArr2, bVar, bVar2, iArr3);
                    break;
                case 4:
                    bVarArrA = e(sb, iArr2, bVar, bVar2, iArr3);
                    break;
                case 5:
                    bVarArrA = b(sb, iArr2, bVar, bVar2, iArr3);
                    break;
                case 6:
                    bVarArrA = f(sb, iArr2, bVar, bVar2, iArr3);
                    break;
            }
            bVar = bVarArrA[0];
            bVar2 = bVarArrA[1];
            int i4 = iArr3[0];
            char c2 = (char) iArr3[2];
            if (c2 != 0) {
                sb.append(c2);
            }
            i3 = i4 + 1;
        }
    }

    private static b[] a(StringBuilder sb, int[] iArr, b bVar, b bVar2, int[] iArr2) throws com.huawei.hms.scankit.p.a {
        int i2 = iArr2[1];
        if (i2 < 26) {
            iArr2[2] = (char) (i2 + 65);
        } else if (i2 == 900) {
            bVar = b.ALPHA;
        } else if (i2 != 913) {
            switch (i2) {
                case 26:
                    iArr2[2] = 32;
                    break;
                case 27:
                    bVar = b.LOWER;
                    break;
                case 28:
                    bVar = b.MIXED;
                    break;
                case 29:
                    bVar2 = bVar;
                    bVar = b.PUNCT_SHIFT;
                    break;
                default:
                    throw com.huawei.hms.scankit.p.a.a();
            }
        } else {
            sb.append((char) iArr[iArr2[0]]);
        }
        return new b[]{bVar, bVar2};
    }

    private static int a(int i2, int[] iArr, Charset charset, int i3, StringBuilder sb) throws com.huawei.hms.scankit.p.a {
        int iA;
        int i4;
        int i5;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i6 = 0;
        long j2 = 0;
        boolean z2 = false;
        if (i2 == 901) {
            int[] iArr2 = new int[6];
            int i7 = i3 + 1;
            int i8 = iArr[i3];
            while (true) {
                i5 = iArr[0];
                if (i7 < i5 && !z2) {
                    int i9 = i6 + 1;
                    iArr2[i6] = i8;
                    j2 = (j2 * 900) + i8;
                    int i10 = i7 + 1;
                    i8 = iArr[i7];
                    if (i8 != 928) {
                        switch (i8) {
                            case TypedValues.Custom.TYPE_INT /* 900 */:
                            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
                            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                                break;
                            default:
                                switch (i8) {
                                    case 922:
                                    case 923:
                                    case 924:
                                        break;
                                    default:
                                        if (i9 % 5 != 0 || i9 <= 0) {
                                            i7 = i10;
                                            i6 = i9;
                                            break;
                                        } else {
                                            for (int i11 = 0; i11 < 6; i11++) {
                                                byteArrayOutputStream.write((byte) (j2 >> ((5 - i11) * 8)));
                                            }
                                            j2 = 0;
                                            i7 = i10;
                                            i6 = 0;
                                            break;
                                        }
                                        break;
                                }
                        }
                    }
                    z2 = true;
                    i6 = i9;
                }
            }
            if (i7 == i5 && i8 < 900) {
                iArr2[i6] = i8;
                i6++;
            }
            for (i4 = 0; i4 < i6; i4++) {
                byteArrayOutputStream.write((byte) iArr2[i4]);
            }
            iA = i7;
        } else {
            iA = i2 == 924 ? a(i3, iArr, false, 0, 0L, byteArrayOutputStream) : i3;
        }
        sb.append(new String(byteArrayOutputStream.toByteArray(), charset));
        return iA;
    }

    private static int a(int i2, int[] iArr, boolean z2, int i3, long j2, ByteArrayOutputStream byteArrayOutputStream) throws com.huawei.hms.scankit.p.a {
        while (i2 < iArr[0] && !z2) {
            int i4 = i2 + 1;
            int i5 = iArr[i2];
            if (i5 < 900) {
                i3++;
                j2 = (j2 * 900) + i5;
                i2 = i4;
            } else {
                if (i5 != 928) {
                    switch (i5) {
                        default:
                            switch (i5) {
                                case 922:
                                case 923:
                                case 924:
                                    break;
                                default:
                                    throw com.huawei.hms.scankit.p.a.a();
                            }
                        case TypedValues.Custom.TYPE_INT /* 900 */:
                        case TypedValues.Custom.TYPE_FLOAT /* 901 */:
                        case TypedValues.Custom.TYPE_COLOR /* 902 */:
                            z2 = true;
                            break;
                    }
                }
                z2 = true;
            }
            if (i3 % 5 == 0 && i3 > 0) {
                for (int i6 = 0; i6 < 6; i6++) {
                    byteArrayOutputStream.write((byte) (j2 >> ((5 - i6) * 8)));
                }
                j2 = 0;
                i3 = 0;
            }
        }
        return i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0047, code lost:
    
        return r9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int a(int[] r8, int r9, java.lang.StringBuilder r10) throws com.huawei.hms.scankit.p.a {
        /*
            r0 = 15
            int[] r0 = new int[r0]
            r1 = 0
            r2 = r1
            r3 = r2
        L7:
            r4 = r8[r1]
            if (r9 >= r4) goto L47
            if (r2 != 0) goto L47
            int r5 = r9 + 1
            r6 = r8[r9]
            r7 = 1
            if (r5 != r4) goto L15
            r2 = r7
        L15:
            r4 = 900(0x384, float:1.261E-42)
            if (r6 >= r4) goto L1f
            r0[r3] = r6
            int r3 = r3 + 1
            r9 = r5
            goto L32
        L1f:
            if (r6 == r4) goto L31
            r2 = 901(0x385, float:1.263E-42)
            if (r6 == r2) goto L31
            r2 = 928(0x3a0, float:1.3E-42)
            if (r6 == r2) goto L31
            switch(r6) {
                case 922: goto L31;
                case 923: goto L31;
                case 924: goto L31;
                default: goto L2c;
            }
        L2c:
            com.huawei.hms.scankit.p.a r8 = com.huawei.hms.scankit.p.a.a()
            throw r8
        L31:
            r2 = r7
        L32:
            int r4 = r3 % 15
            if (r4 == 0) goto L3c
            r4 = 902(0x386, float:1.264E-42)
            if (r6 == r4) goto L3c
            if (r2 == 0) goto L7
        L3c:
            if (r3 <= 0) goto L7
            java.lang.String r3 = a(r0, r3)
            r10.append(r3)
            r3 = r1
            goto L7
        L47:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.q1.a(int[], int, java.lang.StringBuilder):int");
    }

    private static String a(int[] iArr, int i2) throws com.huawei.hms.scankit.p.a {
        BigInteger bigIntegerAdd = BigInteger.ZERO;
        for (int i3 = 0; i3 < i2; i3++) {
            bigIntegerAdd = bigIntegerAdd.add(f17679c[(i2 - i3) - 1].multiply(BigInteger.valueOf(iArr[i3])));
        }
        String string = bigIntegerAdd.toString();
        if (string.charAt(0) == '1') {
            return string.substring(1);
        }
        throw com.huawei.hms.scankit.p.a.a();
    }
}
