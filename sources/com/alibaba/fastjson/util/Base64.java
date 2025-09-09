package com.alibaba.fastjson.util;

import java.util.Arrays;

/* loaded from: classes2.dex */
public class Base64 {
    public static final char[] CA;
    public static final int[] IA;

    static {
        char[] charArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        CA = charArray;
        int[] iArr = new int[256];
        IA = iArr;
        Arrays.fill(iArr, -1);
        int length = charArray.length;
        for (int i2 = 0; i2 < length; i2++) {
            IA[CA[i2]] = i2;
        }
        IA[61] = 0;
    }

    public static byte[] decodeFast(char[] cArr, int i2, int i3) {
        int i4;
        int i5 = 0;
        if (i3 == 0) {
            return new byte[0];
        }
        int i6 = (i2 + i3) - 1;
        int i7 = i2;
        while (i7 < i6 && IA[cArr[i7]] < 0) {
            i7++;
        }
        while (i6 > 0 && IA[cArr[i6]] < 0) {
            i6--;
        }
        int i8 = cArr[i6] == '=' ? cArr[i6 + (-1)] == '=' ? 2 : 1 : 0;
        int i9 = (i6 - i7) + 1;
        if (i3 > 76) {
            i4 = (cArr[76] == '\r' ? i9 / 78 : 0) << 1;
        } else {
            i4 = 0;
        }
        int i10 = (((i9 - i4) * 6) >> 3) - i8;
        byte[] bArr = new byte[i10];
        int i11 = (i10 / 3) * 3;
        int i12 = 0;
        int i13 = 0;
        while (i12 < i11) {
            int[] iArr = IA;
            int i14 = i7 + 4;
            int i15 = iArr[cArr[i7 + 3]] | (iArr[cArr[i7 + 1]] << 12) | (iArr[cArr[i7]] << 18) | (iArr[cArr[i7 + 2]] << 6);
            bArr[i12] = (byte) (i15 >> 16);
            int i16 = i12 + 2;
            bArr[i12 + 1] = (byte) (i15 >> 8);
            i12 += 3;
            bArr[i16] = (byte) i15;
            if (i4 <= 0 || (i13 = i13 + 1) != 19) {
                i7 = i14;
            } else {
                i7 += 6;
                i13 = 0;
            }
        }
        if (i12 < i10) {
            int i17 = 0;
            while (i7 <= i6 - i8) {
                i5 |= IA[cArr[i7]] << (18 - (i17 * 6));
                i17++;
                i7++;
            }
            int i18 = 16;
            while (i12 < i10) {
                bArr[i12] = (byte) (i5 >> i18);
                i18 -= 8;
                i12++;
            }
        }
        return bArr;
    }

    public static byte[] decodeFast(String str, int i2, int i3) {
        int i4;
        if (i3 == 0) {
            return new byte[0];
        }
        int i5 = (i2 + i3) - 1;
        int i6 = i2;
        while (i6 < i5 && IA[str.charAt(i6)] < 0) {
            i6++;
        }
        while (i5 > 0 && IA[str.charAt(i5)] < 0) {
            i5--;
        }
        int i7 = str.charAt(i5) == '=' ? str.charAt(i5 + (-1)) == '=' ? 2 : 1 : 0;
        int i8 = (i5 - i6) + 1;
        if (i3 > 76) {
            i4 = (str.charAt(76) == '\r' ? i8 / 78 : 0) << 1;
        } else {
            i4 = 0;
        }
        int i9 = (((i8 - i4) * 6) >> 3) - i7;
        byte[] bArr = new byte[i9];
        int i10 = (i9 / 3) * 3;
        int i11 = 0;
        int i12 = 0;
        while (i11 < i10) {
            int[] iArr = IA;
            int i13 = i6 + 4;
            int i14 = iArr[str.charAt(i6 + 3)] | (iArr[str.charAt(i6 + 1)] << 12) | (iArr[str.charAt(i6)] << 18) | (iArr[str.charAt(i6 + 2)] << 6);
            bArr[i11] = (byte) (i14 >> 16);
            int i15 = i11 + 2;
            bArr[i11 + 1] = (byte) (i14 >> 8);
            i11 += 3;
            bArr[i15] = (byte) i14;
            if (i4 <= 0 || (i12 = i12 + 1) != 19) {
                i6 = i13;
            } else {
                i6 += 6;
                i12 = 0;
            }
        }
        if (i11 < i9) {
            int i16 = 0;
            int i17 = 0;
            while (i6 <= i5 - i7) {
                i16 |= IA[str.charAt(i6)] << (18 - (i17 * 6));
                i17++;
                i6++;
            }
            int i18 = 16;
            while (i11 < i9) {
                bArr[i11] = (byte) (i16 >> i18);
                i18 -= 8;
                i11++;
            }
        }
        return bArr;
    }

    public static byte[] decodeFast(String str) {
        int i2;
        int length = str.length();
        if (length == 0) {
            return new byte[0];
        }
        int i3 = length - 1;
        int i4 = 0;
        while (i4 < i3 && IA[str.charAt(i4) & 255] < 0) {
            i4++;
        }
        while (i3 > 0 && IA[str.charAt(i3) & 255] < 0) {
            i3--;
        }
        int i5 = str.charAt(i3) == '=' ? str.charAt(i3 + (-1)) == '=' ? 2 : 1 : 0;
        int i6 = (i3 - i4) + 1;
        if (length > 76) {
            i2 = (str.charAt(76) == '\r' ? i6 / 78 : 0) << 1;
        } else {
            i2 = 0;
        }
        int i7 = (((i6 - i2) * 6) >> 3) - i5;
        byte[] bArr = new byte[i7];
        int i8 = (i7 / 3) * 3;
        int i9 = 0;
        int i10 = 0;
        while (i9 < i8) {
            int[] iArr = IA;
            int i11 = i4 + 4;
            int i12 = iArr[str.charAt(i4 + 3)] | (iArr[str.charAt(i4 + 1)] << 12) | (iArr[str.charAt(i4)] << 18) | (iArr[str.charAt(i4 + 2)] << 6);
            bArr[i9] = (byte) (i12 >> 16);
            int i13 = i9 + 2;
            bArr[i9 + 1] = (byte) (i12 >> 8);
            i9 += 3;
            bArr[i13] = (byte) i12;
            if (i2 <= 0 || (i10 = i10 + 1) != 19) {
                i4 = i11;
            } else {
                i4 += 6;
                i10 = 0;
            }
        }
        if (i9 < i7) {
            int i14 = 0;
            int i15 = 0;
            while (i4 <= i3 - i5) {
                i14 |= IA[str.charAt(i4)] << (18 - (i15 * 6));
                i15++;
                i4++;
            }
            int i16 = 16;
            while (i9 < i7) {
                bArr[i9] = (byte) (i14 >> i16);
                i16 -= 8;
                i9++;
            }
        }
        return bArr;
    }
}
