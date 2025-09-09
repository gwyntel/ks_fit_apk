package c.a.d;

import com.google.common.base.Ascii;
import java.security.AccessController;

/* loaded from: classes2.dex */
public final class l {

    /* renamed from: a, reason: collision with root package name */
    public static String f8124a;

    static {
        try {
            f8124a = (String) AccessController.doPrivileged(new k());
        } catch (Exception unused) {
            try {
                f8124a = String.format("%n", new Object[0]);
            } catch (Exception unused2) {
                f8124a = "\n";
            }
        }
    }

    public static String a(String str) {
        char[] charArray = str.toCharArray();
        boolean z2 = false;
        for (int i2 = 0; i2 != charArray.length; i2++) {
            char c2 = charArray[i2];
            if ('A' <= c2 && 'Z' >= c2) {
                charArray[i2] = (char) (c2 + ' ');
                z2 = true;
            }
        }
        return z2 ? new String(charArray) : str;
    }

    public static String b(String str) {
        char[] charArray = str.toCharArray();
        boolean z2 = false;
        for (int i2 = 0; i2 != charArray.length; i2++) {
            char c2 = charArray[i2];
            if ('a' <= c2 && 'z' >= c2) {
                charArray[i2] = (char) (c2 - ' ');
                z2 = true;
            }
        }
        return z2 ? new String(charArray) : str;
    }

    public static String c(byte[] bArr) {
        char c2;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < bArr.length) {
            int i5 = i4 + 1;
            byte b2 = bArr[i3];
            if ((b2 & 240) == 240) {
                i4 += 2;
                i3 += 4;
            } else {
                i3 = (b2 & 224) == 224 ? i3 + 3 : (b2 & 192) == 192 ? i3 + 2 : i3 + 1;
                i4 = i5;
            }
        }
        char[] cArr = new char[i4];
        int i6 = 0;
        while (i2 < bArr.length) {
            byte b3 = bArr[i2];
            if ((b3 & 240) == 240) {
                int i7 = (((((b3 & 3) << 18) | ((bArr[i2 + 1] & 63) << 12)) | ((bArr[i2 + 2] & 63) << 6)) | (bArr[i2 + 3] & 63)) - 65536;
                char c3 = (char) ((i7 >> 10) | 55296);
                c2 = (char) ((i7 & 1023) | 56320);
                cArr[i6] = c3;
                i2 += 4;
                i6++;
            } else if ((b3 & 224) == 224) {
                c2 = (char) (((b3 & 15) << 12) | ((bArr[i2 + 1] & 63) << 6) | (bArr[i2 + 2] & 63));
                i2 += 3;
            } else if ((b3 & 208) == 208 || (b3 & 192) == 192) {
                int i8 = (b3 & Ascii.US) << 6;
                byte b4 = bArr[i2 + 1];
                c2 = (char) (i8 | (b4 & 63));
                i2 += 2;
            } else {
                c2 = (char) (b3 & 255);
                i2++;
            }
            cArr[i6] = c2;
            i6++;
        }
        return new String(cArr);
    }

    public static char[] a(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length];
        for (int i2 = 0; i2 != length; i2++) {
            cArr[i2] = (char) (bArr[i2] & 255);
        }
        return cArr;
    }

    public static String b(byte[] bArr) {
        return new String(a(bArr));
    }

    public static String a() {
        return f8124a;
    }
}
