package com.xiaomi.push;

import org.apache.commons.io.IOUtils;

/* loaded from: classes4.dex */
public class bm {

    /* renamed from: a, reason: collision with other field name */
    private static byte[] f216a;

    /* renamed from: a, reason: collision with root package name */
    private static final String f23500a = System.getProperty("line.separator");

    /* renamed from: a, reason: collision with other field name */
    private static char[] f217a = new char[64];

    static {
        char c2 = 'A';
        int i2 = 0;
        while (c2 <= 'Z') {
            f217a[i2] = c2;
            c2 = (char) (c2 + 1);
            i2++;
        }
        char c3 = 'a';
        while (c3 <= 'z') {
            f217a[i2] = c3;
            c3 = (char) (c3 + 1);
            i2++;
        }
        char c4 = '0';
        while (c4 <= '9') {
            f217a[i2] = c4;
            c4 = (char) (c4 + 1);
            i2++;
        }
        char[] cArr = f217a;
        cArr[i2] = '+';
        cArr[i2 + 1] = IOUtils.DIR_SEPARATOR_UNIX;
        f216a = new byte[128];
        int i3 = 0;
        while (true) {
            byte[] bArr = f216a;
            if (i3 >= bArr.length) {
                break;
            }
            bArr[i3] = -1;
            i3++;
        }
        for (int i4 = 0; i4 < 64; i4++) {
            f216a[f217a[i4]] = (byte) i4;
        }
    }

    public static String a(String str) {
        return new String(a(str.getBytes()));
    }

    public static String b(String str) {
        return new String(m218a(str));
    }

    public static char[] a(byte[] bArr) {
        return a(bArr, 0, bArr.length);
    }

    public static char[] a(byte[] bArr, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8 = ((i3 * 4) + 2) / 3;
        char[] cArr = new char[((i3 + 2) / 3) * 4];
        int i9 = i3 + i2;
        int i10 = 0;
        while (i2 < i9) {
            int i11 = i2 + 1;
            byte b2 = bArr[i2];
            int i12 = b2 & 255;
            if (i11 < i9) {
                i4 = i2 + 2;
                i5 = bArr[i11] & 255;
            } else {
                i4 = i11;
                i5 = 0;
            }
            if (i4 < i9) {
                i6 = i4 + 1;
                i7 = bArr[i4] & 255;
            } else {
                i6 = i4;
                i7 = 0;
            }
            int i13 = ((b2 & 3) << 4) | (i5 >>> 4);
            int i14 = ((i5 & 15) << 2) | (i7 >>> 6);
            int i15 = i7 & 63;
            char[] cArr2 = f217a;
            cArr[i10] = cArr2[i12 >>> 2];
            int i16 = i10 + 2;
            cArr[i10 + 1] = cArr2[i13];
            char c2 = com.alipay.sdk.m.n.a.f9565h;
            cArr[i16] = i16 < i8 ? cArr2[i14] : '=';
            int i17 = i10 + 3;
            if (i17 < i8) {
                c2 = cArr2[i15];
            }
            cArr[i17] = c2;
            i10 += 4;
            i2 = i6;
        }
        return cArr;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static byte[] m218a(String str) {
        return a(str.toCharArray());
    }

    public static byte[] a(char[] cArr) {
        return a(cArr, 0, cArr.length);
    }

    public static byte[] a(char[] cArr, int i2, int i3) {
        char c2;
        if (i3 % 4 == 0) {
            while (i3 > 0 && cArr[(i2 + i3) - 1] == '=') {
                i3--;
            }
            int i4 = (i3 * 3) / 4;
            byte[] bArr = new byte[i4];
            int i5 = i3 + i2;
            int i6 = 0;
            while (i2 < i5) {
                char c3 = cArr[i2];
                int i7 = i2 + 2;
                char c4 = cArr[i2 + 1];
                char c5 = 'A';
                if (i7 < i5) {
                    i2 += 3;
                    c2 = cArr[i7];
                } else {
                    i2 = i7;
                    c2 = 'A';
                }
                if (i2 < i5) {
                    c5 = cArr[i2];
                    i2++;
                }
                if (c3 <= 127 && c4 <= 127 && c2 <= 127 && c5 <= 127) {
                    byte[] bArr2 = f216a;
                    byte b2 = bArr2[c3];
                    byte b3 = bArr2[c4];
                    byte b4 = bArr2[c2];
                    byte b5 = bArr2[c5];
                    if (b2 >= 0 && b3 >= 0 && b4 >= 0 && b5 >= 0) {
                        int i8 = (b2 << 2) | (b3 >>> 4);
                        int i9 = ((b3 & 15) << 4) | (b4 >>> 2);
                        int i10 = ((b4 & 3) << 6) | b5;
                        int i11 = i6 + 1;
                        bArr[i6] = (byte) i8;
                        if (i11 < i4) {
                            bArr[i11] = (byte) i9;
                            i11 = i6 + 2;
                        }
                        if (i11 < i4) {
                            i6 = i11 + 1;
                            bArr[i11] = (byte) i10;
                        } else {
                            i6 = i11;
                        }
                    } else {
                        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
                    }
                } else {
                    throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
                }
            }
            return bArr;
        }
        throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
    }
}
