package com.huawei.hms.scankit.p;

import com.google.zxing.common.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
final class r1 {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f17712a = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ $%*+-./:".toCharArray();

    static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f17713a;

        static {
            int[] iArr = new int[u4.values().length];
            f17713a = iArr;
            try {
                iArr[u4.NUMERIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f17713a[u4.ALPHANUMERIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f17713a[u4.BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f17713a[u4.KANJI.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f17713a[u4.TERMINATOR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f17713a[u4.FNC1_FIRST_POSITION.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f17713a[u4.FNC1_SECOND_POSITION.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f17713a[u4.STRUCTURED_APPEND.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f17713a[u4.ECI.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f17713a[u4.HANZI.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    static w1 a(byte[] bArr, b8 b8Var, b3 b3Var, Map<l1, ?> map) throws com.huawei.hms.scankit.p.a {
        int i2;
        int i3;
        w wVar = new w(bArr);
        StringBuilder sb = new StringBuilder(50);
        ArrayList arrayList = new ArrayList(1);
        int i4 = -1;
        int i5 = -1;
        int i6 = 0;
        while (true) {
            try {
                u4 u4VarA = wVar.a() < 4 ? u4.TERMINATOR : u4.a(wVar.a(4));
                int[] iArr = {i6, i4, i5};
                a(u4VarA, wVar, sb, b8Var, iArr, null, arrayList, map);
                i6 = iArr[0] == 1 ? 1 : 0;
                i2 = iArr[1];
                i3 = iArr[2];
                if (u4VarA == u4.TERMINATOR) {
                    break;
                }
                i4 = i2;
                i5 = i3;
            } catch (IllegalArgumentException unused) {
                throw com.huawei.hms.scankit.p.a.a();
            }
        }
        return new w1(bArr, sb.toString(), arrayList.isEmpty() ? null : arrayList, b3Var == null ? null : b3Var.toString(), i2, i3);
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
            if (w7.a(bArr, i3)) {
                int i6 = i3 + 1;
                if (w7.a(bArr, i6)) {
                    bArr[i3] = (byte) (i5 >> 8);
                    bArr[i6] = (byte) i5;
                    i3 += 2;
                    i2--;
                }
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        try {
            sb.append(new String(bArr, StringUtils.SHIFT_JIS));
        } catch (UnsupportedEncodingException unused) {
            throw com.huawei.hms.scankit.p.a.a();
        }
    }

    private static void c(w wVar, StringBuilder sb, int i2) throws com.huawei.hms.scankit.p.a {
        while (i2 >= 3) {
            if (wVar.a() < 10) {
                throw com.huawei.hms.scankit.p.a.a();
            }
            int iA = wVar.a(10);
            if (iA >= 1000) {
                throw com.huawei.hms.scankit.p.a.a();
            }
            sb.append(a(iA / 100));
            sb.append(a((iA / 10) % 10));
            sb.append(a(iA % 10));
            i2 -= 3;
        }
        if (i2 == 2) {
            if (wVar.a() < 7) {
                throw com.huawei.hms.scankit.p.a.a();
            }
            int iA2 = wVar.a(7);
            if (iA2 >= 100) {
                throw com.huawei.hms.scankit.p.a.a();
            }
            sb.append(a(iA2 / 10));
            sb.append(a(iA2 % 10));
            return;
        }
        if (i2 == 1) {
            if (wVar.a() < 4) {
                throw com.huawei.hms.scankit.p.a.a();
            }
            int iA3 = wVar.a(4);
            if (iA3 >= 10) {
                throw com.huawei.hms.scankit.p.a.a();
            }
            sb.append(a(iA3));
        }
    }

    private static void a(u4 u4Var, w wVar, StringBuilder sb, b8 b8Var, int[] iArr, o0 o0Var, List<byte[]> list, Map<l1, ?> map) throws com.huawei.hms.scankit.p.a {
        int[] iArr2 = a.f17713a;
        switch (iArr2[u4Var.ordinal()]) {
            case 5:
                return;
            case 6:
            case 7:
                iArr[0] = 1;
                return;
            case 8:
                if (wVar.a() >= 16) {
                    iArr[1] = wVar.a(8);
                    iArr[2] = wVar.a(8);
                    return;
                }
                throw com.huawei.hms.scankit.p.a.a();
            case 9:
                if (o0.a(a(wVar)) == null) {
                    throw com.huawei.hms.scankit.p.a.a();
                }
                return;
            case 10:
                int iA = wVar.a(4);
                int iA2 = wVar.a(u4Var.a(b8Var));
                if (iA == 1) {
                    a(wVar, sb, iA2);
                    return;
                }
                return;
            default:
                int iA3 = wVar.a(u4Var.a(b8Var));
                int i2 = iArr2[u4Var.ordinal()];
                if (i2 == 1) {
                    c(wVar, sb, iA3);
                    return;
                }
                if (i2 == 2) {
                    a(wVar, sb, iA3, iArr[0] == 1);
                    return;
                } else if (i2 == 3) {
                    a(wVar, sb, iA3, o0Var, list, map);
                    return;
                } else {
                    if (i2 == 4) {
                        b(wVar, sb, iA3);
                        return;
                    }
                    throw com.huawei.hms.scankit.p.a.a();
                }
        }
    }

    private static void a(w wVar, StringBuilder sb, int i2) throws com.huawei.hms.scankit.p.a {
        if (i2 * 13 <= wVar.a()) {
            byte[] bArr = new byte[i2 * 2];
            int i3 = 0;
            while (i2 > 0) {
                int iA = wVar.a(13);
                int i4 = (iA % 96) | ((iA / 96) << 8);
                int i5 = i4 + (i4 < 959 ? 41377 : 42657);
                if (w7.a(bArr, i3)) {
                    int i6 = i3 + 1;
                    if (w7.a(bArr, i6)) {
                        bArr[i3] = (byte) ((i5 >> 8) & 255);
                        bArr[i6] = (byte) (i5 & 255);
                        i3 += 2;
                        i2--;
                    }
                }
                throw new ArrayIndexOutOfBoundsException();
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

    private static void a(w wVar, StringBuilder sb, int i2, o0 o0Var, Collection<byte[]> collection, Map<l1, ?> map) throws com.huawei.hms.scankit.p.a {
        String strName;
        if (i2 * 8 <= wVar.a()) {
            byte[] bArr = new byte[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                bArr[i3] = (byte) wVar.a(8);
            }
            if (o0Var == null) {
                strName = c7.a(bArr, map);
            } else {
                strName = o0Var.name();
            }
            try {
                sb.append(new String(bArr, strName));
                collection.add(bArr);
                return;
            } catch (UnsupportedEncodingException unused) {
                throw com.huawei.hms.scankit.p.a.a();
            }
        }
        throw com.huawei.hms.scankit.p.a.a();
    }

    private static char a(int i2) throws com.huawei.hms.scankit.p.a {
        char[] cArr = f17712a;
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
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.r1.a(com.huawei.hms.scankit.p.w, java.lang.StringBuilder, int, boolean):void");
    }

    private static int a(w wVar) throws com.huawei.hms.scankit.p.a {
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
}
