package com.aliyun.alink.business.devicecenter.utils;

import com.aliyun.alink.linksdk.securesigner.util.Utils;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class EncryptUtils {
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String SIGN_METHOD_HMAC = "hmac";
    public static final String SIGN_METHOD_MD5 = "md5";

    public static byte[] a(String str) throws IOException {
        return a(str.getBytes("UTF-8"), Utils.MD5);
    }

    public static boolean areNotEmpty(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            return false;
        }
        boolean z2 = true;
        for (String str : strArr) {
            z2 &= !isEmpty(str);
        }
        return z2;
    }

    public static byte[] encryptHMAC(String str, String str2) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance(secretKeySpec.getAlgorithm());
            mac.init(secretKeySpec);
            return mac.doFinal(str.getBytes("UTF-8"));
        } catch (GeneralSecurityException e2) {
            throw new IOException(e2.toString());
        }
    }

    public static boolean isEmpty(String str) {
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

    public static String signTopRequest(Map<String, String> map, String str, String str2) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        byte[] bArrA;
        String[] strArr = (String[]) map.keySet().toArray(new String[0]);
        Arrays.sort(strArr);
        StringBuilder sb = new StringBuilder();
        if (SIGN_METHOD_MD5.equals(str2)) {
            sb.append(str);
        }
        for (String str3 : strArr) {
            String str4 = map.get(str3);
            if (areNotEmpty(str3, str4)) {
                sb.append(str3);
                sb.append(str4);
            }
        }
        if (SIGN_METHOD_HMAC.equals(str2)) {
            bArrA = encryptHMAC(sb.toString(), str);
        } else {
            sb.append(str);
            bArrA = a(sb.toString());
        }
        return a(bArrA);
    }

    public static byte[] a(byte[] bArr, String str) throws NoSuchAlgorithmException {
        if (bArr != null && bArr.length > 0) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(str);
                messageDigest.update(bArr);
                return messageDigest.digest();
            } catch (NoSuchAlgorithmException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                sb.append("0");
            }
            sb.append(hexString.toUpperCase());
        }
        return sb.toString();
    }
}
