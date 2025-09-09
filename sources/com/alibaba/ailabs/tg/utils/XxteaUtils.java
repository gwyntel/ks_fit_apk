package com.alibaba.ailabs.tg.utils;

import java.io.UnsupportedEncodingException;

/* loaded from: classes2.dex */
public final class XxteaUtils {
    private static final int DELTA = -1640531527;

    private XxteaUtils() {
    }

    private static int MX(int i2, int i3, int i4, int i5, int i6, int[] iArr) {
        return ((i2 ^ i3) + (iArr[(i5 & 3) ^ i6] ^ i4)) ^ (((i4 >>> 5) ^ (i3 << 2)) + ((i3 >>> 3) ^ (i4 << 4)));
    }

    public static final byte[] decrypt(byte[] bArr, byte[] bArr2) {
        return bArr.length == 0 ? bArr : toByteArray(decrypt(toIntArray(bArr, false), toIntArray(fixKey(bArr2), false)), true);
    }

    public static final byte[] decryptBase64String(String str, byte[] bArr) {
        return decrypt(EncodeUtils.base64Decode(str), bArr);
    }

    public static final String decryptBase64StringToString(String str, byte[] bArr) {
        try {
            byte[] bArrDecrypt = decrypt(EncodeUtils.base64Decode(str), bArr);
            if (bArrDecrypt == null) {
                return null;
            }
            return new String(bArrDecrypt, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static final String decryptToString(byte[] bArr, byte[] bArr2) {
        try {
            byte[] bArrDecrypt = decrypt(bArr, bArr2);
            if (bArrDecrypt == null) {
                return null;
            }
            return new String(bArrDecrypt, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static final byte[] encrypt(byte[] bArr, byte[] bArr2) {
        return bArr.length == 0 ? bArr : toByteArray(encrypt(toIntArray(bArr, true), toIntArray(fixKey(bArr2), false)), false);
    }

    public static final String encryptToBase64String(byte[] bArr, byte[] bArr2) {
        byte[] bArrEncrypt = encrypt(bArr, bArr2);
        if (bArrEncrypt == null) {
            return null;
        }
        return EncodeUtils.base64Encode2String(bArrEncrypt);
    }

    private static byte[] fixKey(byte[] bArr) {
        if (bArr.length == 16) {
            return bArr;
        }
        byte[] bArr2 = new byte[16];
        if (bArr.length < 16) {
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        } else {
            System.arraycopy(bArr, 0, bArr2, 0, 16);
        }
        return bArr2;
    }

    private static byte[] toByteArray(int[] iArr, boolean z2) {
        int length = iArr.length << 2;
        if (z2) {
            int i2 = iArr[iArr.length - 1];
            int i3 = length - 4;
            if (i2 < length - 7 || i2 > i3) {
                return null;
            }
            length = i2;
        }
        byte[] bArr = new byte[length];
        for (int i4 = 0; i4 < length; i4++) {
            bArr[i4] = (byte) (iArr[i4 >>> 2] >>> ((i4 & 3) << 3));
        }
        return bArr;
    }

    private static int[] toIntArray(byte[] bArr, boolean z2) {
        int[] iArr;
        int length = (bArr.length & 3) == 0 ? bArr.length >>> 2 : (bArr.length >>> 2) + 1;
        if (z2) {
            iArr = new int[length + 1];
            iArr[length] = bArr.length;
        } else {
            iArr = new int[length];
        }
        int length2 = bArr.length;
        for (int i2 = 0; i2 < length2; i2++) {
            int i3 = i2 >>> 2;
            iArr[i3] = iArr[i3] | ((bArr[i2] & 255) << ((i2 & 3) << 3));
        }
        return iArr;
    }

    public static final byte[] decryptBase64String(String str, String str2) {
        return decrypt(EncodeUtils.base64Decode(str), str2);
    }

    public static final String decryptBase64StringToString(String str, String str2) {
        try {
            byte[] bArrDecrypt = decrypt(EncodeUtils.base64Decode(str), str2);
            if (bArrDecrypt == null) {
                return null;
            }
            return new String(bArrDecrypt, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static final String decryptToString(byte[] bArr, String str) {
        try {
            byte[] bArrDecrypt = decrypt(bArr, str);
            if (bArrDecrypt == null) {
                return null;
            }
            return new String(bArrDecrypt, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static final String encryptToBase64String(String str, byte[] bArr) {
        byte[] bArrEncrypt = encrypt(str, bArr);
        if (bArrEncrypt == null) {
            return null;
        }
        return EncodeUtils.base64Encode2String(bArrEncrypt);
    }

    public static final byte[] decrypt(byte[] bArr, String str) {
        try {
            return decrypt(bArr, str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static final byte[] encrypt(String str, byte[] bArr) {
        try {
            return encrypt(str.getBytes("UTF-8"), bArr);
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    private static int[] decrypt(int[] iArr, int[] iArr2) {
        int length = iArr.length;
        int i2 = length - 1;
        if (i2 < 1) {
            return iArr;
        }
        int iMX = iArr[0];
        for (int i3 = ((52 / length) + 6) * DELTA; i3 != 0; i3 -= DELTA) {
            int i4 = (i3 >>> 2) & 3;
            int iMX2 = iMX;
            int i5 = i2;
            while (i5 > 0) {
                iMX2 = iArr[i5] - MX(i3, iMX2, iArr[i5 - 1], i5, i4, iArr2);
                iArr[i5] = iMX2;
                i5--;
            }
            iMX = iArr[0] - MX(i3, iMX2, iArr[i2], i5, i4, iArr2);
            iArr[0] = iMX;
        }
        return iArr;
    }

    public static final byte[] encrypt(byte[] bArr, String str) {
        try {
            return encrypt(bArr, str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static final String encryptToBase64String(byte[] bArr, String str) {
        byte[] bArrEncrypt = encrypt(bArr, str);
        if (bArrEncrypt == null) {
            return null;
        }
        return EncodeUtils.base64Encode2String(bArrEncrypt);
    }

    public static final byte[] encrypt(String str, String str2) {
        try {
            return encrypt(str.getBytes("UTF-8"), str2.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    private static int[] encrypt(int[] iArr, int[] iArr2) {
        int length = iArr.length;
        int i2 = length - 1;
        if (i2 < 1) {
            return iArr;
        }
        int i3 = (52 / length) + 6;
        int iMX = iArr[i2];
        int i4 = 0;
        while (true) {
            int i5 = i3 - 1;
            if (i3 <= 0) {
                return iArr;
            }
            i4 += DELTA;
            int i6 = (i4 >>> 2) & 3;
            int iMX2 = iMX;
            int i7 = 0;
            while (i7 < i2) {
                int i8 = i7 + 1;
                iMX2 = iArr[i7] + MX(i4, iArr[i8], iMX2, i7, i6, iArr2);
                iArr[i7] = iMX2;
                i7 = i8;
            }
            iMX = MX(i4, iArr[0], iMX2, i7, i6, iArr2) + iArr[i2];
            iArr[i2] = iMX;
            i3 = i5;
        }
    }

    public static final String encryptToBase64String(String str, String str2) {
        byte[] bArrEncrypt = encrypt(str, str2);
        if (bArrEncrypt == null) {
            return null;
        }
        return EncodeUtils.base64Encode2String(bArrEncrypt);
    }
}
