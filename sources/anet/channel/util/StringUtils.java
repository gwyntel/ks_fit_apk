package anet.channel.util;

import java.security.MessageDigest;

/* loaded from: classes2.dex */
public class StringUtils {
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String buildKey(String str, String str2) {
        return concatString(str, HttpConstant.SCHEME_SPLIT, str2);
    }

    private static String bytesToHexString(byte[] bArr, char[] cArr) {
        char[] cArr2 = new char[bArr.length << 1];
        int i2 = 0;
        for (byte b2 : bArr) {
            int i3 = i2 + 1;
            cArr2[i2] = cArr[(b2 & 240) >>> 4];
            i2 += 2;
            cArr2[i3] = cArr[b2 & 15];
        }
        return new String(cArr2);
    }

    public static String concatString(String str, String str2) {
        StringBuilder sb = new StringBuilder(str.length() + str2.length());
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    public static boolean isBlank(String str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for (int i2 = 0; i2 < length; i2++) {
                if (!Character.isWhitespace(str.charAt(i2))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isStringEqual(String str, String str2) {
        return (str == null && str2 == null) || (str != null && str.equals(str2));
    }

    public static String md5ToHex(String str) {
        if (str == null) {
            return null;
        }
        try {
            return bytesToHexString(MessageDigest.getInstance(com.aliyun.alink.linksdk.securesigner.util.Utils.MD5).digest(str.getBytes("utf-8")));
        } catch (Exception unused) {
            return null;
        }
    }

    public static String simplifyString(String str, int i2) {
        return str.length() <= i2 ? str : concatString(str.substring(0, i2), "......");
    }

    public static String stringNull2Empty(String str) {
        return str == null ? "" : str;
    }

    public static String concatString(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder(str.length() + str2.length() + str3.length());
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        return sb.toString();
    }

    public static String bytesToHexString(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        return bytesToHexString(bArr, DIGITS_LOWER);
    }
}
