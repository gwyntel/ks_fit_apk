package com.aliyun.alink.business.devicecenter.utils;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.log.ALog;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

/* loaded from: classes2.dex */
public class StringUtils {
    public static final char[] HEX_STRING_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String byteArray2String(byte[] bArr) {
        if (bArr == null || bArr.length < 1) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (byte b2 : bArr) {
            stringBuffer.append((int) b2);
            stringBuffer.append(" ");
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public static String bytesToHexString(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b2 = bArr[i2];
            int i3 = i2 * 2;
            char[] cArr2 = HEX_STRING_ARRAY;
            cArr[i3] = cArr2[(b2 & 255) >>> 4];
            cArr[i3 + 1] = cArr2[b2 & 15];
        }
        return new String(cArr);
    }

    public static byte[] getBssidHexByteArray(String str) {
        ALog.d("StringUtils", "getBssidHexByteArray() called with: bssid = [" + str + "]");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            String[] strArrSplit = str.split(":");
            if (strArrSplit == null || strArrSplit.length != 6) {
                ALog.w("StringUtils", "getBssidHexByteArray length error.");
            }
            byte[] bArr = new byte[6];
            for (int i2 = 0; i2 < 6; i2++) {
                bArr[i2] = Integer.valueOf(Integer.parseInt(strArrSplit[i2], 16)).byteValue();
            }
            return bArr;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            ALog.w("StringUtils", "getBssidHexByteArray exception=" + e2);
            return null;
        }
    }

    public static String getEncodedString(String str, int i2) {
        if (TextUtils.isEmpty(str)) {
            return "[0]";
        }
        try {
            int length = str.length();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("[");
            stringBuffer.append(length);
            stringBuffer.append("]");
            for (int i3 = 0; i3 < length; i3++) {
                stringBuffer.append(((str.charAt(i3) + i2) - 32) % 128);
            }
            return stringBuffer.toString();
        } catch (Exception unused) {
            return "[" + str.length() + "]";
        }
    }

    public static String getHexString(int i2) {
        if (i2 < 1) {
            return null;
        }
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = 0; i3 < i2; i3++) {
            stringBuffer.append("0123456789ABCDEF".charAt(random.nextInt(16)));
        }
        return stringBuffer.toString();
    }

    public static String getHexStringFromByteArray(byte[] bArr) {
        if (bArr == null || bArr.length < 1) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        int length = bArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            stringBuffer.append(String.format("%02x", Byte.valueOf(bArr[i2])));
            if (i2 != length - 1) {
                stringBuffer.append(" ");
            }
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public static int getIntFromString(String str) {
        if (!TextUtils.isEmpty(str) && TextUtils.isDigitsOnly(str)) {
            try {
                return Integer.parseInt(str);
            } catch (Exception unused) {
            }
        }
        return 0;
    }

    public static String getLastBssid(String str, int i2) {
        ALog.d("StringUtils", "getLastBssid bssid=" + str + ", byteNum=" + i2);
        if (i2 < 0 || i2 > 6) {
            ALog.d("StringUtils", "getLastBssid failed. invalid byte number.");
            return null;
        }
        if (!isValidBssid(str)) {
            ALog.d("StringUtils", "getLastBssid failed. invalid bssid.");
            return null;
        }
        String[] strArrSplit = str.split(":");
        StringBuffer stringBuffer = new StringBuffer();
        int length = strArrSplit.length;
        for (int i3 = length - i2; i3 < length; i3++) {
            stringBuffer.append(strArrSplit[i3]);
        }
        ALog.d("StringUtils", "getLastBssid result=" + stringBuffer.toString());
        return stringBuffer.toString();
    }

    public static String getMDData(String str, String str2) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance(str2);
        messageDigest.update(str.getBytes("UTF-8"));
        return bytesToHexString(messageDigest.digest());
    }

    public static int getStringLength(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        return str.length();
    }

    public static byte[] hexStringTobytes(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i2 = 0; i2 < length; i2 += 2) {
            bArr[i2 / 2] = (byte) ((Character.digit(str.charAt(i2), 16) << 4) + Character.digit(str.charAt(i2 + 1), 16));
        }
        return bArr;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEqualString(String str, String str2) {
        return TextUtils.isEmpty(str) ? TextUtils.isEmpty(str2) : str.equals(str2);
    }

    public static boolean isValidBssid(String str) {
        String[] strArrSplit;
        return (TextUtils.isEmpty(str) || (strArrSplit = str.split(":")) == null || strArrSplit.length != 6 || "02:00:00:00:00:00".equals(str)) ? false : true;
    }

    public static boolean isValidIntString(String str) throws NumberFormatException {
        if (!TextUtils.isEmpty(str) && TextUtils.isDigitsOnly(str)) {
            try {
                Integer.parseInt(str);
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static String join(String str, Iterable<? extends String> iterable) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(iterable);
        StringBuilder sb = new StringBuilder();
        Iterator<? extends String> it = iterable.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append(str);
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String getMDData(byte[] bArr, String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(str);
        messageDigest.update(bArr);
        return bytesToHexString(messageDigest.digest());
    }
}
