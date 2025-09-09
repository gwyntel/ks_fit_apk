package com.xiaomi.infra.galaxy.fds.auth.signature;

import org.apache.commons.io.IOUtils;

/* loaded from: classes4.dex */
class Base64Util {
    private static final int BASELENGTH = 128;
    private static final int EIGHTBIT = 8;
    private static final int FOURBYTE = 4;
    private static final int LOOKUPLENGTH = 64;
    private static final char PAD = '=';
    private static final int SIGN = -128;
    private static final int SIXBIT = 6;
    private static final int SIXTEENBIT = 16;
    private static final int TWENTYFOURBITGROUP = 24;
    private static final boolean fDebug = false;
    private static final byte[] base64Alphabet = new byte[128];
    private static final char[] lookUpBase64Alphabet = new char[64];

    static {
        int i2 = 0;
        for (int i3 = 0; i3 < 128; i3++) {
            base64Alphabet[i3] = -1;
        }
        for (int i4 = 90; i4 >= 65; i4--) {
            base64Alphabet[i4] = (byte) (i4 - 65);
        }
        for (int i5 = 122; i5 >= 97; i5--) {
            base64Alphabet[i5] = (byte) (i5 - 71);
        }
        for (int i6 = 57; i6 >= 48; i6--) {
            base64Alphabet[i6] = (byte) (i6 + 4);
        }
        byte[] bArr = base64Alphabet;
        bArr[43] = 62;
        bArr[47] = 63;
        for (int i7 = 0; i7 <= 25; i7++) {
            lookUpBase64Alphabet[i7] = (char) (i7 + 65);
        }
        int i8 = 26;
        int i9 = 0;
        while (i8 <= 51) {
            lookUpBase64Alphabet[i8] = (char) (i9 + 97);
            i8++;
            i9++;
        }
        int i10 = 52;
        while (i10 <= 61) {
            lookUpBase64Alphabet[i10] = (char) (i2 + 48);
            i10++;
            i2++;
        }
        char[] cArr = lookUpBase64Alphabet;
        cArr[62] = '+';
        cArr[63] = IOUtils.DIR_SEPARATOR_UNIX;
    }

    Base64Util() {
    }

    public static byte[] decode(String str) {
        if (str == null) {
            return null;
        }
        char[] charArray = str.toCharArray();
        int iRemoveWhiteSpace = removeWhiteSpace(charArray);
        if (iRemoveWhiteSpace % 4 != 0) {
            return null;
        }
        int i2 = iRemoveWhiteSpace / 4;
        if (i2 == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[i2 * 3];
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 < i2 - 1) {
            int i6 = i4 + 1;
            char c2 = charArray[i4];
            if (isData(c2)) {
                int i7 = i4 + 2;
                char c3 = charArray[i6];
                if (isData(c3)) {
                    int i8 = i4 + 3;
                    char c4 = charArray[i7];
                    if (isData(c4)) {
                        i4 += 4;
                        char c5 = charArray[i8];
                        if (isData(c5)) {
                            byte[] bArr2 = base64Alphabet;
                            byte b2 = bArr2[c2];
                            byte b3 = bArr2[c3];
                            byte b4 = bArr2[c4];
                            byte b5 = bArr2[c5];
                            bArr[i5] = (byte) ((b2 << 2) | (b3 >> 4));
                            int i9 = i5 + 2;
                            bArr[i5 + 1] = (byte) (((b3 & 15) << 4) | ((b4 >> 2) & 15));
                            i5 += 3;
                            bArr[i9] = (byte) ((b4 << 6) | b5);
                            i3++;
                        }
                    }
                }
            }
            return null;
        }
        int i10 = i4 + 1;
        char c6 = charArray[i4];
        if (!isData(c6)) {
            return null;
        }
        int i11 = i4 + 2;
        char c7 = charArray[i10];
        if (!isData(c7)) {
            return null;
        }
        byte[] bArr3 = base64Alphabet;
        byte b6 = bArr3[c6];
        byte b7 = bArr3[c7];
        char c8 = charArray[i11];
        char c9 = charArray[i4 + 3];
        if (isData(c8) && isData(c9)) {
            byte b8 = bArr3[c8];
            byte b9 = bArr3[c9];
            bArr[i5] = (byte) ((b6 << 2) | (b7 >> 4));
            bArr[i5 + 1] = (byte) (((b7 & 15) << 4) | ((b8 >> 2) & 15));
            bArr[i5 + 2] = (byte) (b9 | (b8 << 6));
            return bArr;
        }
        if (isPad(c8) && isPad(c9)) {
            if ((b7 & 15) != 0) {
                return null;
            }
            int i12 = i3 * 3;
            byte[] bArr4 = new byte[i12 + 1];
            System.arraycopy(bArr, 0, bArr4, 0, i12);
            bArr4[i5] = (byte) ((b6 << 2) | (b7 >> 4));
            return bArr4;
        }
        if (isPad(c8) || !isPad(c9)) {
            return null;
        }
        byte b10 = bArr3[c8];
        if ((b10 & 3) != 0) {
            return null;
        }
        int i13 = i3 * 3;
        byte[] bArr5 = new byte[i13 + 2];
        System.arraycopy(bArr, 0, bArr5, 0, i13);
        bArr5[i5] = (byte) ((b6 << 2) | (b7 >> 4));
        bArr5[i5 + 1] = (byte) (((b10 >> 2) & 15) | ((b7 & 15) << 4));
        return bArr5;
    }

    public static String encode(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length * 8;
        if (length == 0) {
            return "";
        }
        int i2 = length % 24;
        int i3 = length / 24;
        char[] cArr = new char[(i2 != 0 ? i3 + 1 : i3) * 4];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            byte b2 = bArr[i4];
            int i7 = i4 + 2;
            byte b3 = bArr[i4 + 1];
            i4 += 3;
            byte b4 = bArr[i7];
            byte b5 = (byte) (b3 & 15);
            byte b6 = (byte) (b2 & 3);
            int i8 = b2 & Byte.MIN_VALUE;
            int i9 = b2 >> 2;
            if (i8 != 0) {
                i9 ^= 192;
            }
            byte b7 = (byte) i9;
            int i10 = b3 & Byte.MIN_VALUE;
            int i11 = b3 >> 4;
            if (i10 != 0) {
                i11 ^= 240;
            }
            byte b8 = (byte) i11;
            byte b9 = (byte) ((b4 & Byte.MIN_VALUE) == 0 ? b4 >> 6 : (b4 >> 6) ^ 252);
            char[] cArr2 = lookUpBase64Alphabet;
            cArr[i5] = cArr2[b7];
            cArr[i5 + 1] = cArr2[b8 | (b6 << 4)];
            int i12 = i5 + 3;
            cArr[i5 + 2] = cArr2[(b5 << 2) | b9];
            i5 += 4;
            cArr[i12] = cArr2[b4 & 63];
        }
        if (i2 == 8) {
            byte b10 = bArr[i4];
            byte b11 = (byte) (b10 & 3);
            int i13 = b10 & Byte.MIN_VALUE;
            int i14 = b10 >> 2;
            if (i13 != 0) {
                i14 ^= 192;
            }
            byte b12 = (byte) i14;
            char[] cArr3 = lookUpBase64Alphabet;
            cArr[i5] = cArr3[b12];
            cArr[i5 + 1] = cArr3[b11 << 4];
            cArr[i5 + 2] = '=';
            cArr[i5 + 3] = '=';
        } else if (i2 == 16) {
            byte b13 = bArr[i4];
            byte b14 = bArr[i4 + 1];
            byte b15 = (byte) (b14 & 15);
            byte b16 = (byte) (b13 & 3);
            int i15 = b13 & Byte.MIN_VALUE;
            int i16 = b13 >> 2;
            if (i15 != 0) {
                i16 ^= 192;
            }
            byte b17 = (byte) i16;
            int i17 = b14 & Byte.MIN_VALUE;
            int i18 = b14 >> 4;
            if (i17 != 0) {
                i18 ^= 240;
            }
            byte b18 = (byte) i18;
            char[] cArr4 = lookUpBase64Alphabet;
            cArr[i5] = cArr4[b17];
            cArr[i5 + 1] = cArr4[b18 | (b16 << 4)];
            cArr[i5 + 2] = cArr4[b15 << 2];
            cArr[i5 + 3] = '=';
        }
        return new String(cArr);
    }

    protected static boolean isBase64(char c2) {
        return isWhiteSpace(c2) || isPad(c2) || isData(c2);
    }

    protected static boolean isData(char c2) {
        return c2 < 128 && base64Alphabet[c2] != -1;
    }

    protected static boolean isPad(char c2) {
        return c2 == '=';
    }

    protected static boolean isWhiteSpace(char c2) {
        return c2 == ' ' || c2 == '\r' || c2 == '\n' || c2 == '\t';
    }

    protected static int removeWhiteSpace(char[] cArr) {
        if (cArr == null) {
            return 0;
        }
        int length = cArr.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (!isWhiteSpace(cArr[i3])) {
                cArr[i2] = cArr[i3];
                i2++;
            }
        }
        return i2;
    }
}
