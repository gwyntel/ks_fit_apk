package com.umeng.commonsdk.statistics.common;

import android.annotation.SuppressLint;
import com.alibaba.ailabs.iot.aisbase.AESUtil;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.umeng.commonsdk.utils.UMUtils;
import com.yc.utesdk.utils.close.AESUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes4.dex */
public class DataHelper {
    public static long ENVELOPE_ENTITY_RAW_LENGTH_MAX = 2097152;
    public static long ENVELOPE_EXTRA_LENGTH = 614400;
    public static long ENVELOPE_LENGTH_MAX = 204800;
    private static String UMENG_PLUS = "umeng+0123456789";

    public static String assembleStatelessURL(String str) {
        return "https://" + str;
    }

    public static String assembleURL(String str) {
        return "https://" + str;
    }

    static String bytes2Hex(byte[] bArr) {
        String str = "";
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                str = str + "0";
            }
            str = str + hexString;
        }
        return str;
    }

    public static String convertExceptionToString(Throwable th) throws IOException {
        String string = null;
        if (th == null) {
            return null;
        }
        try {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th.printStackTrace(printWriter);
            for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
                cause.printStackTrace(printWriter);
            }
            string = stringWriter.toString();
            printWriter.close();
            stringWriter.close();
            return string;
        } catch (Exception unused) {
            return string;
        }
    }

    public static byte[] decrypt(byte[] bArr, byte[] bArr2) throws Exception {
        Cipher cipher = Cipher.getInstance(AESUtil.PKCS7PADDING_CIPHER_ALGORITHM);
        cipher.init(2, new SecretKeySpec(bArr2, AESUtils.AES), new IvParameterSpec(UMUtils.genIv()));
        return cipher.doFinal(bArr);
    }

    @SuppressLint({"TrulyRandom"})
    public static byte[] encrypt(byte[] bArr, byte[] bArr2) throws Exception {
        Cipher cipher = Cipher.getInstance(AESUtil.PKCS7PADDING_CIPHER_ALGORITHM);
        cipher.init(1, new SecretKeySpec(bArr2, AESUtils.AES), new IvParameterSpec(UMUtils.genIv()));
        return cipher.doFinal(bArr);
    }

    public static String encryptBySHA1(String str) throws NoSuchAlgorithmException {
        byte[] bytes = str.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(bytes);
            return bytes2Hex(messageDigest.digest());
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] hash(byte[] bArr) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(Utils.MD5);
            messageDigest.reset();
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean largeThanMaxSize(long j2, long j3) {
        return j2 > j3;
    }

    public static int random(int i2, String str) throws NumberFormatException {
        int i3 = 0;
        if (new Random().nextFloat() < 0.001d) {
            if (str == null) {
                MLog.e("--->", "null signature..");
            }
            try {
                i3 = Integer.parseInt(str.substring(9, 11), 16);
            } catch (Exception unused) {
            }
            return (i3 | 128) * 1000;
        }
        int iNextInt = new Random().nextInt(i2);
        if (iNextInt > 255000 || iNextInt < 128000) {
            return iNextInt;
        }
        return 127000;
    }

    public static byte[] reverseHexString(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length % 2 != 0) {
            return null;
        }
        byte[] bArr = new byte[length / 2];
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 + 2;
            bArr[i2 / 2] = (byte) Integer.valueOf(str.substring(i2, i3), 16).intValue();
            i2 = i3;
        }
        return bArr;
    }

    public static String toHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b2 : bArr) {
            stringBuffer.append(String.format("%02X", Byte.valueOf(b2)));
        }
        return stringBuffer.toString().toLowerCase(Locale.US);
    }
}
