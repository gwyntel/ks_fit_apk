package com.aliyun.alink.business.devicecenter.utils;

import java.io.ByteArrayOutputStream;

/* loaded from: classes2.dex */
public class HexUtils {

    /* renamed from: a, reason: collision with root package name */
    public static String f10640a = "0123456789ABCDEF";

    public static byte[] HexString2Bytes(String str) {
        byte[] bArr = new byte[6];
        byte[] bytes = str.getBytes();
        for (int i2 = 0; i2 < 6; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = a(bytes[i3], bytes[i3 + 1]);
        }
        return bArr;
    }

    public static byte a(byte b2, byte b3) {
        return (byte) (((byte) (Byte.decode("0x" + new String(new byte[]{b2})).byteValue() << 4)) | Byte.decode("0x" + new String(new byte[]{b3})).byteValue());
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(128);
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static String decode(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(str.length() / 2);
        for (int i2 = 0; i2 < str.length(); i2 += 2) {
            byteArrayOutputStream.write((f10640a.indexOf(str.charAt(i2)) << 4) | f10640a.indexOf(str.charAt(i2 + 1)));
        }
        return new String(byteArrayOutputStream.toByteArray());
    }

    public static String encode(String str) {
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b2 : bytes) {
            sb.append(f10640a.charAt((b2 & 240) >> 4));
            sb.append(f10640a.charAt(b2 & 15));
        }
        return sb.toString();
    }

    public static byte[] hexStringToBytes(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) (a(charArray[i3 + 1]) | (a(charArray[i3]) << 4));
        }
        return bArr;
    }

    public static String stringToHexString(String str) {
        String str2 = "";
        for (int i2 = 0; i2 < str.length(); i2++) {
            str2 = str2 + Integer.toHexString(str.charAt(i2));
        }
        return str2;
    }

    public static byte a(char c2) {
        return (byte) "0123456789ABCDEF".indexOf(c2);
    }
}
