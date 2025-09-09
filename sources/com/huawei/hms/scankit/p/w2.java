package com.huawei.hms.scankit.p;

import com.huawei.hms.hmsscankit.WriterException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public final class w2 {

    /* renamed from: a, reason: collision with root package name */
    private static final int[] f17915a = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1};

    static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f17916a;

        static {
            int[] iArr = new int[u4.values().length];
            f17916a = iArr;
            try {
                iArr[u4.NUMERIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f17916a[u4.ALPHANUMERIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f17916a[u4.BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f17916a[u4.KANJI.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private static int a(c0 c0Var) {
        return r4.a(c0Var) + r4.b(c0Var) + r4.c(c0Var) + r4.d(c0Var);
    }

    static void b(CharSequence charSequence, r rVar) {
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length) {
            int iCharAt = charSequence.charAt(i2) - '0';
            int i3 = i2 + 2;
            if (i3 < length) {
                rVar.a((iCharAt * 100) + ((charSequence.charAt(i2 + 1) - '0') * 10) + (charSequence.charAt(i3) - '0'), 10);
                i2 += 3;
            } else {
                i2++;
                if (i2 < length) {
                    rVar.a((iCharAt * 10) + (charSequence.charAt(i2) - '0'), 7);
                    i2 = i3;
                } else {
                    rVar.a(iCharAt, 4);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.huawei.hms.scankit.p.h6 a(java.lang.String r6, com.huawei.hms.scankit.p.b3 r7, java.util.Map<com.huawei.hms.scankit.p.u2, ?> r8) throws com.huawei.hms.hmsscankit.WriterException, java.io.UnsupportedEncodingException {
        /*
            if (r8 == 0) goto Lc
            com.huawei.hms.scankit.p.u2 r0 = com.huawei.hms.scankit.p.u2.CHARACTER_SET
            boolean r0 = r8.containsKey(r0)
            if (r0 == 0) goto Lc
            r0 = 1
            goto Ld
        Lc:
            r0 = 0
        Ld:
            if (r0 == 0) goto L1a
            com.huawei.hms.scankit.p.u2 r1 = com.huawei.hms.scankit.p.u2.CHARACTER_SET
            java.lang.Object r1 = r8.get(r1)
            java.lang.String r1 = r1.toString()
            goto L1c
        L1a:
            java.lang.String r1 = "ISO-8859-1"
        L1c:
            com.huawei.hms.scankit.p.u4 r2 = a(r6, r1)
            com.huawei.hms.scankit.p.r r3 = new com.huawei.hms.scankit.p.r
            r3.<init>()
            com.huawei.hms.scankit.p.u4 r4 = com.huawei.hms.scankit.p.u4.BYTE
            if (r2 != r4) goto L34
            if (r0 == 0) goto L34
            com.huawei.hms.scankit.p.o0 r0 = com.huawei.hms.scankit.p.o0.a(r1)
            if (r0 == 0) goto L34
            a(r0, r3)
        L34:
            if (r8 == 0) goto L55
            com.huawei.hms.scankit.p.u2 r0 = com.huawei.hms.scankit.p.u2.GS1_FORMAT
            boolean r5 = r8.containsKey(r0)
            if (r5 == 0) goto L55
            java.lang.Object r0 = r8.get(r0)
            java.lang.String r0 = r0.toString()
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L55
            com.huawei.hms.scankit.p.u4 r0 = com.huawei.hms.scankit.p.u4.FNC1_FIRST_POSITION
            a(r0, r3)
        L55:
            a(r2, r3)
            com.huawei.hms.scankit.p.r r0 = new com.huawei.hms.scankit.p.r
            r0.<init>()
            a(r6, r2, r0, r1)
            if (r8 == 0) goto L8d
            com.huawei.hms.scankit.p.u2 r1 = com.huawei.hms.scankit.p.u2.QR_VERSION
            boolean r5 = r8.containsKey(r1)
            if (r5 == 0) goto L8d
            java.lang.Object r8 = r8.get(r1)
            java.lang.String r8 = r8.toString()
            int r8 = java.lang.Integer.parseInt(r8)
            com.huawei.hms.scankit.p.b8 r8 = com.huawei.hms.scankit.p.b8.c(r8)
            int r1 = a(r2, r3, r0, r8)
            boolean r1 = a(r1, r8, r7)
            if (r1 == 0) goto L85
            goto L91
        L85:
            com.huawei.hms.hmsscankit.WriterException r6 = new com.huawei.hms.hmsscankit.WriterException
            java.lang.String r7 = "Data too big for requested version"
            r6.<init>(r7)
            throw r6
        L8d:
            com.huawei.hms.scankit.p.b8 r8 = a(r7, r2, r3, r0)
        L91:
            com.huawei.hms.scankit.p.r r1 = new com.huawei.hms.scankit.p.r
            r1.<init>()
            r1.a(r3)
            if (r2 != r4) goto La0
            int r6 = r0.f()
            goto La4
        La0:
            int r6 = r6.length()
        La4:
            a(r6, r8, r2, r1)
            r1.a(r0)
            com.huawei.hms.scankit.p.b8$b r6 = r8.a(r7)
            int r0 = r8.e()
            int r3 = r6.d()
            int r0 = r0 - r3
            a(r0, r1)
            int r3 = r8.e()
            int r6 = r6.c()
            com.huawei.hms.scankit.p.r r6 = a(r1, r3, r0, r6)
            com.huawei.hms.scankit.p.h6 r0 = new com.huawei.hms.scankit.p.h6
            r0.<init>()
            r0.a(r7)
            r0.a(r2)
            r0.a(r8)
            int r1 = r8.d()
            com.huawei.hms.scankit.p.c0 r2 = new com.huawei.hms.scankit.p.c0
            r2.<init>(r1, r1)
            int r1 = a(r6, r7, r8, r2)
            r0.b(r1)
            com.huawei.hms.scankit.p.t4.a(r6, r7, r8, r1, r2)
            r0.a(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.w2.a(java.lang.String, com.huawei.hms.scankit.p.b3, java.util.Map):com.huawei.hms.scankit.p.h6");
    }

    private static b8 a(b3 b3Var, u4 u4Var, r rVar, r rVar2) throws WriterException {
        return a(a(u4Var, rVar, rVar2, a(a(u4Var, rVar, rVar2, b8.c(1)), b3Var)), b3Var);
    }

    private static int a(u4 u4Var, r rVar, r rVar2, b8 b8Var) {
        return rVar.e() + u4Var.a(b8Var) + rVar2.e();
    }

    static int a(int i2) {
        int[] iArr = f17915a;
        if (i2 < iArr.length) {
            return iArr[i2];
        }
        return -1;
    }

    private static u4 a(String str, String str2) {
        if ("Shift_JIS".equals(str2) && a(str)) {
            return u4.KANJI;
        }
        boolean z2 = false;
        boolean z3 = false;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt >= '0' && cCharAt <= '9') {
                z3 = true;
            } else {
                if (a(cCharAt) == -1) {
                    return u4.BYTE;
                }
                z2 = true;
            }
        }
        if (z2) {
            return u4.ALPHANUMERIC;
        }
        if (z3) {
            return u4.NUMERIC;
        }
        return u4.BYTE;
    }

    private static boolean a(String str) throws UnsupportedEncodingException {
        try {
            byte[] bytes = str.getBytes("Shift_JIS");
            int length = bytes.length;
            if (length % 2 != 0) {
                return false;
            }
            for (int i2 = 0; i2 < length; i2 += 2) {
                int i3 = bytes[i2] & 255;
                if ((i3 < 129 || i3 > 159) && (i3 < 224 || i3 > 235)) {
                    return false;
                }
            }
            return true;
        } catch (UnsupportedEncodingException unused) {
            return false;
        }
    }

    private static int a(r rVar, b3 b3Var, b8 b8Var, c0 c0Var) throws WriterException {
        int i2 = Integer.MAX_VALUE;
        int i3 = -1;
        for (int i4 = 0; i4 < 8; i4++) {
            t4.a(rVar, b3Var, b8Var, i4, c0Var);
            int iA = a(c0Var);
            if (iA < i2) {
                i3 = i4;
                i2 = iA;
            }
        }
        return i3;
    }

    private static b8 a(int i2, b3 b3Var) throws WriterException {
        for (int i3 = 1; i3 <= 40; i3++) {
            b8 b8VarC = b8.c(i3);
            if (a(i2, b8VarC, b3Var)) {
                return b8VarC;
            }
        }
        throw new WriterException("Data too big");
    }

    private static boolean a(int i2, b8 b8Var, b3 b3Var) {
        return b8Var.e() - b8Var.a(b3Var).d() >= (i2 + 7) / 8;
    }

    static void a(int i2, r rVar) throws WriterException {
        int i3 = i2 * 8;
        if (rVar.e() <= i3) {
            for (int i4 = 0; i4 < 4 && rVar.e() < i3; i4++) {
                rVar.a(false);
            }
            int iE = rVar.e() & 7;
            if (iE > 0) {
                while (iE < 8) {
                    rVar.a(false);
                    iE++;
                }
            }
            int iF = i2 - rVar.f();
            for (int i5 = 0; i5 < iF; i5++) {
                rVar.a((i5 & 1) == 0 ? 236 : 17, 8);
            }
            if (rVar.e() != i3) {
                throw new WriterException("Bits size does not equal capacity");
            }
            return;
        }
        throw new WriterException("data bits cannot fit in the QR Code" + rVar.e() + " > " + i3);
    }

    static void a(int i2, int i3, int i4, int i5, int[] iArr, int[] iArr2) throws WriterException {
        if (i5 < i4) {
            int i6 = i2 % i4;
            int i7 = i4 - i6;
            int i8 = i2 / i4;
            int i9 = i8 + 1;
            int i10 = i3 / i4;
            int i11 = i10 + 1;
            int i12 = i8 - i10;
            int i13 = i9 - i11;
            if (i12 != i13) {
                throw new WriterException("EC bytes mismatch");
            }
            if (i4 != i7 + i6) {
                throw new WriterException("RS blocks mismatch");
            }
            if (i2 != ((i10 + i12) * i7) + ((i11 + i13) * i6)) {
                throw new WriterException("Total bytes mismatch");
            }
            if (i5 < i7) {
                iArr[0] = i10;
                iArr2[0] = i12;
                return;
            } else {
                iArr[0] = i11;
                iArr2[0] = i13;
                return;
            }
        }
        throw new WriterException("Block ID too large");
    }

    static r a(r rVar, int i2, int i3, int i4) throws WriterException {
        if (rVar.f() == i3) {
            ArrayList arrayList = new ArrayList(i4);
            int i5 = 0;
            int iMax = 0;
            int iMax2 = 0;
            for (int i6 = 0; i6 < i4; i6++) {
                int[] iArr = new int[1];
                int[] iArr2 = new int[1];
                a(i2, i3, i4, i6, iArr, iArr2);
                int i7 = iArr[0];
                byte[] bArr = new byte[i7];
                rVar.a(i5 * 8, bArr, 0, i7);
                byte[] bArrA = a(bArr, iArr2[0]);
                arrayList.add(new y(bArr, bArrA));
                iMax2 = Math.max(iMax2, i7);
                iMax = Math.max(iMax, bArrA.length);
                i5 += iArr[0];
            }
            if (i3 == i5) {
                r rVar2 = new r();
                for (int i8 = 0; i8 < iMax2; i8++) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        byte[] bArrA2 = ((y) it.next()).a();
                        if (i8 < bArrA2.length) {
                            rVar2.a(bArrA2[i8], 8);
                        }
                    }
                }
                for (int i9 = 0; i9 < iMax; i9++) {
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        byte[] bArrB = ((y) it2.next()).b();
                        if (i9 < bArrB.length) {
                            rVar2.a(bArrB[i9], 8);
                        }
                    }
                }
                if (i2 == rVar2.f()) {
                    return rVar2;
                }
                throw new WriterException("Interleaving error: " + i2 + " and " + rVar2.f() + " differ.");
            }
            throw new WriterException("Data bytes does not match offset");
        }
        throw new WriterException("Number of bits and data bytes does not match");
    }

    static byte[] a(byte[] bArr, int i2) {
        int length = bArr.length;
        int[] iArr = new int[length + i2];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = bArr[i3] & 255;
        }
        new q6(o3.f17633l).a(iArr, i2);
        byte[] bArr2 = new byte[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            bArr2[i4] = (byte) iArr[length + i4];
        }
        return bArr2;
    }

    static void a(u4 u4Var, r rVar) {
        rVar.a(u4Var.a(), 4);
    }

    static void a(int i2, b8 b8Var, u4 u4Var, r rVar) throws WriterException {
        int iA = u4Var.a(b8Var);
        int i3 = 1 << iA;
        if (i2 < i3) {
            rVar.a(i2, iA);
            return;
        }
        throw new WriterException(i2 + " is bigger than " + (i3 - 1));
    }

    static void a(String str, u4 u4Var, r rVar, String str2) throws WriterException, UnsupportedEncodingException {
        int i2 = a.f17916a[u4Var.ordinal()];
        if (i2 == 1) {
            b(str, rVar);
            return;
        }
        if (i2 == 2) {
            a((CharSequence) str, rVar);
            return;
        }
        if (i2 == 3) {
            a(str, rVar, str2);
        } else {
            if (i2 == 4) {
                a(str, rVar);
                return;
            }
            throw new WriterException("Invalid mode: " + u4Var);
        }
    }

    static void a(CharSequence charSequence, r rVar) throws WriterException {
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length) {
            int iA = a(charSequence.charAt(i2));
            if (iA == -1) {
                throw new WriterException();
            }
            int i3 = i2 + 1;
            if (i3 < length) {
                int iA2 = a(charSequence.charAt(i3));
                if (iA2 != -1) {
                    rVar.a((iA * 45) + iA2, 11);
                    i2 += 2;
                } else {
                    throw new WriterException();
                }
            } else {
                rVar.a(iA, 6);
                i2 = i3;
            }
        }
    }

    static void a(String str, r rVar, String str2) throws WriterException, UnsupportedEncodingException {
        try {
            for (byte b2 : str.getBytes(str2)) {
                rVar.a(b2, 8);
            }
        } catch (UnsupportedEncodingException e2) {
            throw new WriterException(e2);
        }
    }

    static void a(String str, r rVar) throws WriterException, UnsupportedEncodingException {
        int i2;
        try {
            byte[] bytes = str.getBytes("Shift_JIS");
            int length = bytes.length;
            for (int i3 = 0; i3 < length; i3 += 2) {
                int i4 = ((bytes[i3] & 255) << 8) | (bytes[i3 + 1] & 255);
                int i5 = 33088;
                if (i4 >= 33088 && i4 <= 40956) {
                    i2 = i4 - i5;
                } else if (i4 < 57408 || i4 > 60351) {
                    i2 = -1;
                } else {
                    i5 = 49472;
                    i2 = i4 - i5;
                }
                if (i2 != -1) {
                    rVar.a(((i2 >> 8) * 192) + (i2 & 255), 13);
                } else {
                    throw new WriterException("Invalid byte sequence");
                }
            }
        } catch (UnsupportedEncodingException e2) {
            throw new WriterException(e2);
        }
    }

    private static void a(o0 o0Var, r rVar) {
        rVar.a(u4.ECI.a(), 4);
        rVar.a(o0Var.a(), 8);
    }
}
