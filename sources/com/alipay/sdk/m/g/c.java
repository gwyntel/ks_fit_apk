package com.alipay.sdk.m.g;

import android.util.Base64;
import java.security.SecureRandom;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public static volatile SecureRandom f9271a;

    /* renamed from: b, reason: collision with root package name */
    public static final char[] f9272b = "0123456789ABCDEF".toCharArray();

    public static byte[] a(long j2) {
        return new byte[]{(byte) j2, (byte) (j2 >> 8), (byte) (j2 >> 16), (byte) (j2 >> 24), (byte) (j2 >> 32), (byte) (j2 >> 40), (byte) (j2 >> 48), (byte) (j2 >> 56)};
    }

    public static byte[] b() {
        byte[] bArr = new byte[2];
        a().nextBytes(bArr);
        return bArr;
    }

    public static byte[] c() {
        byte[] bArr = new byte[4];
        a().nextBytes(bArr);
        return bArr;
    }

    public static byte[] a(int i2) {
        return new byte[]{(byte) i2, (byte) (i2 >> 8), (byte) (i2 >> 16), (byte) (i2 >> 24)};
    }

    public static byte[] a(short s2) {
        return new byte[]{(byte) s2, (byte) (s2 >> 8)};
    }

    public static String b(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b2 = bArr[i2];
            int i3 = i2 * 2;
            char[] cArr2 = f9272b;
            cArr[i3] = cArr2[(b2 & 255) >>> 4];
            cArr[i3 + 1] = cArr2[b2 & 15];
        }
        return new String(cArr);
    }

    public static byte[] a(char c2, char c3) {
        return new byte[]{(byte) (c2 & 255), (byte) (c3 & 255)};
    }

    public static byte[] a(char c2) {
        return new byte[]{(byte) (c2 & 255)};
    }

    public static byte[] a(byte b2) {
        return new byte[]{b2};
    }

    public static SecureRandom a() {
        if (f9271a != null) {
            return f9271a;
        }
        synchronized (c.class) {
            try {
                if (f9271a == null) {
                    f9271a = new SecureRandom();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return f9271a;
    }

    public static byte[] a(byte[]... bArr) {
        int length = 0;
        for (byte[] bArr2 : bArr) {
            length += bArr2.length;
        }
        byte[] bArrCopyOf = null;
        int length2 = 0;
        for (byte[] bArr3 : bArr) {
            if (bArrCopyOf == null) {
                bArrCopyOf = Arrays.copyOf(bArr3, length);
                length2 = bArr3.length;
            } else {
                System.arraycopy(bArr3, 0, bArrCopyOf, length2, bArr3.length);
                length2 += bArr3.length;
            }
        }
        return bArrCopyOf;
    }

    public static String a(byte[] bArr) {
        return Base64.encodeToString(bArr, 3);
    }
}
