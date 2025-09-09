package com.huawei.secure.android.common.encrypt.aes;

import android.text.TextUtils;
import com.huawei.secure.android.common.encrypt.utils.EncryptUtil;
import com.huawei.secure.android.common.encrypt.utils.HexUtil;
import com.huawei.secure.android.common.encrypt.utils.b;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes4.dex */
public final class AesCbc {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18257a = "security:";

    /* renamed from: b, reason: collision with root package name */
    private static final String f18258b = "AES/CBC/PKCS5Padding";

    /* renamed from: c, reason: collision with root package name */
    private static final String f18259c = "AES";

    /* renamed from: d, reason: collision with root package name */
    private static final String f18260d = "CBC";

    /* renamed from: e, reason: collision with root package name */
    private static final String f18261e = "";

    /* renamed from: f, reason: collision with root package name */
    private static final int f18262f = 16;

    /* renamed from: g, reason: collision with root package name */
    private static final int f18263g = 16;

    private AesCbc() {
    }

    private static byte[] a(String str, byte[] bArr, byte[] bArr2) {
        if (TextUtils.isEmpty(str)) {
            b.b(f18260d, "encrypt 5 content is null");
            return new byte[0];
        }
        if (bArr == null) {
            b.b(f18260d, "encrypt 5 key is null");
            return new byte[0];
        }
        if (bArr.length < 16) {
            b.b(f18260d, "encrypt 5 key error: 5 key length less than 16 bytes.");
            return new byte[0];
        }
        if (bArr2 == null) {
            b.b(f18260d, "encrypt 5 iv is null");
            return new byte[0];
        }
        if (bArr2.length < 16) {
            b.b(f18260d, "encrypt 5 iv error: 5 iv length less than 16 bytes.");
            return new byte[0];
        }
        try {
            return encrypt(str.getBytes("UTF-8"), bArr, bArr2);
        } catch (UnsupportedEncodingException e2) {
            b.b(f18260d, " cbc encrypt data error" + e2.getMessage());
            return new byte[0];
        }
    }

    private static byte[] b(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length - 16];
        System.arraycopy(bArr, 16, bArr2, 0, bArr.length - 16);
        return bArr2;
    }

    private static byte[] c(byte[] bArr) {
        byte[] bArr2 = new byte[16];
        System.arraycopy(bArr, 0, bArr2, 0, 16);
        return bArr2;
    }

    static byte[] d(byte[] bArr) {
        String str;
        try {
            str = new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            b.b(f18260d, "stripCryptHead: exception : " + e2.getMessage());
            str = "";
        }
        if (!str.startsWith(f18257a)) {
            return new byte[0];
        }
        if (bArr.length <= 9) {
            return new byte[0];
        }
        int length = bArr.length - 9;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 9, bArr2, 0, length);
        return bArr2;
    }

    public static String decrypt(String str, String str2) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            b.b(f18260d, "decrypt 1 content is null");
            return "";
        }
        if (TextUtils.isEmpty(str2)) {
            b.b(f18260d, "decrypt 1 key is null");
            return "";
        }
        byte[] bArrHexStr2ByteArray = HexUtil.hexStr2ByteArray(str2);
        if (bArrHexStr2ByteArray.length >= 16) {
            return decrypt(str, bArrHexStr2ByteArray);
        }
        b.b(f18260d, "decrypt 1 key error: 1 key length less than 16 bytes.");
        return "";
    }

    public static String decryptWithCryptHead(String str, byte[] bArr) throws UnsupportedEncodingException {
        if (!TextUtils.isEmpty(str) && bArr != null && bArr.length >= 16) {
            String strC = c(str);
            if ("".equals(strC)) {
                return "";
            }
            int iIndexOf = strC.indexOf(58);
            if (iIndexOf >= 0) {
                return decrypt(HexUtil.byteArray2HexStr(HexUtil.hexStr2ByteArray(strC.substring(iIndexOf + 1))), bArr, HexUtil.hexStr2ByteArray(strC.substring(0, iIndexOf)));
            }
            b.b(f18260d, " cbc cipherText data missing colon");
        }
        return "";
    }

    public static byte[] decryptWithCryptHeadReturnByte(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null || bArr2.length < 16) {
            return new byte[0];
        }
        byte[] bArrD = d(bArr);
        if (bArrD.length == 0) {
            return new byte[0];
        }
        int iA = a(bArrD);
        if (iA < 0) {
            b.b(f18260d, " cbc cipherText data missing colon");
            return new byte[0];
        }
        byte[] bArrCopyOf = Arrays.copyOf(bArrD, iA);
        int length = (bArrD.length - bArrCopyOf.length) - 1;
        byte[] bArr3 = new byte[length];
        System.arraycopy(bArrD, iA + 1, bArr3, 0, length);
        return decrypt(bArr3, bArr2, bArrCopyOf);
    }

    public static String encrypt(String str, String str2) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            b.b(f18260d, "encrypt 1 content is null");
            return "";
        }
        if (TextUtils.isEmpty(str2)) {
            b.b(f18260d, "encrypt 1 key is null");
            return "";
        }
        byte[] bArrHexStr2ByteArray = HexUtil.hexStr2ByteArray(str2);
        if (bArrHexStr2ByteArray.length >= 16) {
            return encrypt(str, bArrHexStr2ByteArray);
        }
        b.b(f18260d, "encrypt 1 key error: 1 key length less than 16 bytes.");
        return "";
    }

    private static String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return str.substring(6, 12) + str.substring(16, 26) + str.substring(32, 48);
        } catch (Exception e2) {
            b.b(f18260d, "getIv exception : " + e2.getMessage());
            return "";
        }
    }

    static String c(String str) {
        return (TextUtils.isEmpty(str) || str.indexOf(f18257a) == -1) ? "" : str.substring(9);
    }

    public static String decrypt(String str, byte[] bArr) {
        if (TextUtils.isEmpty(str)) {
            b.b(f18260d, "decrypt 2 content is null");
            return "";
        }
        if (bArr == null) {
            b.b(f18260d, "decrypt 2 key is null");
            return "";
        }
        if (bArr.length < 16) {
            b.b(f18260d, "decrypt 2 key error: 2 key length less than 16 bytes.");
            return "";
        }
        String strB = b(str);
        String strA = a(str);
        if (TextUtils.isEmpty(strB)) {
            b.b(f18260d, "decrypt 2 iv is null");
            return "";
        }
        if (TextUtils.isEmpty(strA)) {
            b.b(f18260d, "decrypt 2 encrypt content is null");
            return "";
        }
        return decrypt(strA, bArr, HexUtil.hexStr2ByteArray(strB));
    }

    public static String decryptWithCryptHead(byte[] bArr, byte[] bArr2) {
        try {
            return new String(decryptWithCryptHeadReturnByte(bArr, bArr2), "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            b.b(f18260d, "decryptWithCryptHead UnsupportedEncodingException ");
            return "";
        }
    }

    public static String encrypt(String str, byte[] bArr) throws NoSuchAlgorithmException {
        if (TextUtils.isEmpty(str)) {
            b.b(f18260d, "encrypt 2 content is null");
            return "";
        }
        if (bArr == null) {
            b.b(f18260d, "encrypt 2 key is null");
            return "";
        }
        if (bArr.length < 16) {
            b.b(f18260d, "encrypt 2 key error: 2 key length less than 16 bytes.");
            return "";
        }
        byte[] bArrGenerateSecureRandom = EncryptUtil.generateSecureRandom(16);
        byte[] bArrA = a(str, bArr, bArrGenerateSecureRandom);
        return (bArrA == null || bArrA.length == 0) ? "" : a(HexUtil.byteArray2HexStr(bArrGenerateSecureRandom), HexUtil.byteArray2HexStr(bArrA));
    }

    private static String a(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                return str2.substring(0, 6) + str.substring(0, 6) + str2.substring(6, 10) + str.substring(6, 16) + str2.substring(10, 16) + str.substring(16) + str2.substring(16);
            } catch (Exception e2) {
                b.b(f18260d, "mix exception: " + e2.getMessage());
            }
        }
        return "";
    }

    public static String encrypt(String str, String str2, String str3) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            b.b(f18260d, "encrypt 3 content is null");
            return "";
        }
        if (TextUtils.isEmpty(str2)) {
            b.b(f18260d, "encrypt 3 key is null");
            return "";
        }
        if (TextUtils.isEmpty(str3)) {
            b.b(f18260d, "encrypt 3 iv is null");
            return "";
        }
        byte[] bArrHexStr2ByteArray = HexUtil.hexStr2ByteArray(str2);
        byte[] bArrHexStr2ByteArray2 = HexUtil.hexStr2ByteArray(str3);
        if (bArrHexStr2ByteArray.length < 16) {
            b.b(f18260d, "encrypt 3 key error: 3 key length less than 16 bytes.");
            return "";
        }
        if (bArrHexStr2ByteArray2.length < 16) {
            b.b(f18260d, "encrypt 3 iv error: 3 iv length less than 16 bytes.");
            return "";
        }
        return encrypt(str, bArrHexStr2ByteArray, bArrHexStr2ByteArray2);
    }

    public static String decrypt(String str, String str2, String str3) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            b.b(f18260d, "decrypt 3 content is null");
            return "";
        }
        if (TextUtils.isEmpty(str2)) {
            b.b(f18260d, "decrypt 3 key is null");
            return "";
        }
        if (TextUtils.isEmpty(str3)) {
            b.b(f18260d, "decrypt 3 iv is null");
            return "";
        }
        byte[] bArrHexStr2ByteArray = HexUtil.hexStr2ByteArray(str2);
        byte[] bArrHexStr2ByteArray2 = HexUtil.hexStr2ByteArray(str3);
        if (bArrHexStr2ByteArray.length < 16) {
            b.b(f18260d, "decrypt 3 key error: 3 key length less than 16 bytes.");
            return "";
        }
        if (bArrHexStr2ByteArray2.length < 16) {
            b.b(f18260d, "decrypt 3 iv error: 3 iv length less than 16 bytes.");
            return "";
        }
        return decrypt(str, bArrHexStr2ByteArray, bArrHexStr2ByteArray2);
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return str.substring(0, 6) + str.substring(12, 16) + str.substring(26, 32) + str.substring(48);
        } catch (Exception e2) {
            b.b(f18260d, "get encryptword exception : " + e2.getMessage());
            return "";
        }
    }

    public static String encrypt(String str, byte[] bArr, byte[] bArr2) {
        if (TextUtils.isEmpty(str)) {
            b.b(f18260d, "encrypt 4 content is null");
            return "";
        }
        if (bArr == null) {
            b.b(f18260d, "encrypt 4 key is null");
            return "";
        }
        if (bArr.length < 16) {
            b.b(f18260d, "encrypt 4 key error: 4 key length less than 16 bytes.");
            return "";
        }
        if (bArr2 == null) {
            b.b(f18260d, "encrypt 4 iv is null");
            return "";
        }
        if (bArr2.length < 16) {
            b.b(f18260d, "encrypt 4 iv error: 4 iv length less than 16 bytes.");
            return "";
        }
        return HexUtil.byteArray2HexStr(a(str, bArr, bArr2));
    }

    public static String decrypt(String str, byte[] bArr, byte[] bArr2) {
        if (TextUtils.isEmpty(str)) {
            b.b(f18260d, "decrypt 4 content is null");
            return "";
        }
        if (bArr == null) {
            b.b(f18260d, "decrypt 4 key is null");
            return "";
        }
        if (bArr.length < 16) {
            b.b(f18260d, "decrypt 4 key error: 4 key length less than 16 bytes.");
            return "";
        }
        if (bArr2 == null) {
            b.b(f18260d, "decrypt 4 iv is null");
            return "";
        }
        if (bArr2.length < 16) {
            b.b(f18260d, "decrypt 4 iv error: 4 iv length less than 16 bytes.");
            return "";
        }
        try {
            return new String(decrypt(HexUtil.hexStr2ByteArray(str), bArr, bArr2), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            b.b(f18260d, " cbc decrypt data error" + e2.getMessage());
            return "";
        }
    }

    private static int a(byte[] bArr) {
        return bArr[16] == 58 ? 16 : -1;
    }

    public static byte[] encrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (bArr == null) {
            b.b(f18260d, "encrypt 6 content is null");
            return new byte[0];
        }
        if (bArr.length == 0) {
            b.b(f18260d, "encrypt 6 content length is 0");
            return new byte[0];
        }
        if (bArr2 == null) {
            b.b(f18260d, "encrypt 6 key is null");
            return new byte[0];
        }
        if (bArr2.length < 16) {
            b.b(f18260d, "encrypt 6 key error: 6 key length less than 16 bytes.");
            return new byte[0];
        }
        if (bArr3 == null) {
            b.b(f18260d, "encrypt 6 iv is null");
            return new byte[0];
        }
        if (bArr3.length < 16) {
            b.b(f18260d, "encrypt 6 iv error: 6 iv length less than 16 bytes.");
            return new byte[0];
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, secretKeySpec, new IvParameterSpec(bArr3));
            return cipher.doFinal(bArr);
        } catch (NullPointerException e2) {
            b.b(f18260d, "NullPointerException: " + e2.getMessage());
            return new byte[0];
        } catch (InvalidAlgorithmParameterException e3) {
            b.b(f18260d, "InvalidAlgorithmParameterException: " + e3.getMessage());
            return new byte[0];
        } catch (InvalidKeyException e4) {
            b.b(f18260d, "InvalidKeyException: " + e4.getMessage());
            return new byte[0];
        } catch (NoSuchAlgorithmException e5) {
            b.b(f18260d, "NoSuchAlgorithmException: " + e5.getMessage());
            return new byte[0];
        } catch (BadPaddingException e6) {
            b.b(f18260d, "BadPaddingException: " + e6.getMessage());
            return new byte[0];
        } catch (IllegalBlockSizeException e7) {
            b.b(f18260d, "IllegalBlockSizeException: " + e7.getMessage());
            return new byte[0];
        } catch (NoSuchPaddingException e8) {
            b.b(f18260d, "NoSuchPaddingException: " + e8.getMessage());
            return new byte[0];
        }
    }

    public static byte[] decrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (bArr == null) {
            b.b(f18260d, "decrypt 6 content is null");
            return new byte[0];
        }
        if (bArr.length == 0) {
            b.b(f18260d, "decrypt 6 content length is 0");
            return new byte[0];
        }
        if (bArr2 == null) {
            b.b(f18260d, "decrypt 6 key is null");
            return new byte[0];
        }
        if (bArr2.length < 16) {
            b.b(f18260d, "decrypt 6 key error: 6 key length less than 16 bytes.");
            return new byte[0];
        }
        if (bArr3 == null) {
            b.b(f18260d, "decrypt 6 iv is null");
            return new byte[0];
        }
        if (bArr3.length < 16) {
            b.b(f18260d, "decrypt 6 iv error: 6 iv length less than 16 bytes.");
            return new byte[0];
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, secretKeySpec, new IvParameterSpec(bArr3));
            return cipher.doFinal(bArr);
        } catch (NullPointerException e2) {
            b.b(f18260d, "NullPointerException: " + e2.getMessage());
            return new byte[0];
        } catch (InvalidAlgorithmParameterException e3) {
            b.b(f18260d, "InvalidAlgorithmParameterException: " + e3.getMessage());
            return new byte[0];
        } catch (InvalidKeyException e4) {
            b.b(f18260d, "InvalidKeyException: " + e4.getMessage());
            return new byte[0];
        } catch (NoSuchAlgorithmException e5) {
            b.b(f18260d, "NoSuchAlgorithmException: " + e5.getMessage());
            return new byte[0];
        } catch (BadPaddingException e6) {
            b.b(f18260d, "BadPaddingException: " + e6.getMessage());
            b.b(f18260d, "key is not right");
            return new byte[0];
        } catch (IllegalBlockSizeException e7) {
            b.b(f18260d, "IllegalBlockSizeException: " + e7.getMessage());
            return new byte[0];
        } catch (NoSuchPaddingException e8) {
            b.b(f18260d, "NoSuchPaddingException: " + e8.getMessage());
            return new byte[0];
        }
    }

    public static byte[] encrypt(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException {
        byte[] bArrGenerateSecureRandom = EncryptUtil.generateSecureRandom(16);
        return a(bArrGenerateSecureRandom, encrypt(bArr, bArr2, bArrGenerateSecureRandom));
    }

    public static byte[] decrypt(byte[] bArr, byte[] bArr2) {
        return decrypt(b(bArr), bArr2, c(bArr));
    }
}
