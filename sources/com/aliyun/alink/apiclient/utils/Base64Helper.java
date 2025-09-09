package com.aliyun.alink.apiclient.utils;

import com.alipay.sdk.m.n.a;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.UnsupportedEncodingException;

/* loaded from: classes2.dex */
public class Base64Helper {
    private static final String BASE64_CODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static final int[] BASE64_DECODE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    public static synchronized String decode(String str, String str2) throws UnsupportedEncodingException {
        if (str == null || str2 == null) {
            return null;
        }
        try {
            int length = str.endsWith("==") ? str.length() - 2 : str.endsWith(ContainerUtils.KEY_VALUE_DELIMITER) ? str.length() - 1 : str.length();
            byte[] bArr = new byte[(length * 3) / 4];
            int i2 = length - (length % 4);
            int i3 = 0;
            for (int i4 = 0; i4 < i2; i4 += 4) {
                int[] iArr = BASE64_DECODE;
                int i5 = iArr[str.charAt(i4)];
                int i6 = iArr[str.charAt(i4 + 1)];
                int i7 = iArr[str.charAt(i4 + 2)];
                int i8 = iArr[str.charAt(i4 + 3)];
                bArr[i3] = (byte) (((i5 << 2) | (i6 >> 4)) & 255);
                int i9 = i3 + 2;
                bArr[i3 + 1] = (byte) ((((i6 & 15) << 4) | (i7 >> 2)) & 255);
                i3 += 3;
                bArr[i9] = (byte) ((i8 | ((i7 & 3) << 6)) & 255);
            }
            if (2 <= length % 4) {
                int[] iArr2 = BASE64_DECODE;
                int i10 = iArr2[str.charAt(i2)];
                int i11 = iArr2[str.charAt(i2 + 1)];
                int i12 = i3 + 1;
                bArr[i3] = (byte) (((i10 << 2) | (i11 >> 4)) & 255);
                if (3 == length % 4) {
                    bArr[i12] = (byte) (((iArr2[str.charAt(i2 + 2)] >> 2) | ((i11 & 15) << 4)) & 255);
                }
            }
            return new String(bArr, str2);
        } catch (Throwable th) {
            throw th;
        }
    }

    public static synchronized String encode(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder("");
            int length = (3 - (bArr.length % 3)) % 3;
            byte[] bArrZeroPad = zeroPad(bArr.length + length, bArr);
            for (int i2 = 0; i2 < bArrZeroPad.length; i2 += 3) {
                int i3 = ((bArrZeroPad[i2] & 255) << 16) + ((bArrZeroPad[i2 + 1] & 255) << 8) + (bArrZeroPad[i2 + 2] & 255);
                sb.append(BASE64_CODE.charAt((i3 >> 18) & 63));
                sb.append(BASE64_CODE.charAt((i3 >> 12) & 63));
                sb.append(BASE64_CODE.charAt((i3 >> 6) & 63));
                sb.append(BASE64_CODE.charAt(i3 & 63));
            }
            int length2 = sb.length();
            while (length > 0) {
                sb.setCharAt(length2 - length, a.f9565h);
                length--;
            }
            return sb.toString();
        } catch (Throwable th) {
            throw th;
        }
    }

    private static byte[] zeroPad(int i2, byte[] bArr) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    public static synchronized String encode(String str, String str2) throws UnsupportedEncodingException {
        if (str == null || str2 == null) {
            return null;
        }
        return encode(str.getBytes(str2));
    }
}
