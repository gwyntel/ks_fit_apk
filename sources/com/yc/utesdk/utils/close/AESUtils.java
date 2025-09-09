package com.yc.utesdk.utils.close;

import android.util.Base64;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes4.dex */
public class AESUtils {
    public static final String AES = "AES";
    public static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String AES_KEY = "U6woFMMEYEjgb+Ep";
    public static final int KEY_SIZE = 128;
    public static final String RNG_ALGORITHM = "SHA1PRNG";

    public static byte[] base64StrToByte(String str) {
        return Base64.decode(str, 0);
    }

    public static String byteToBase64Str(byte[] bArr) {
        return new String(Base64.encode(bArr, 0));
    }

    public static byte[] decryptByAES(byte[] bArr, byte[] bArr2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, AES);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr2);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(2, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static byte[] encryptByAES(byte[] bArr, byte[] bArr2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, AES);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr2);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(1, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static SecretKey generateKey(byte[] bArr) throws NoSuchAlgorithmException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
            SecureRandom secureRandom = SecureRandom.getInstance(RNG_ALGORITHM);
            secureRandom.setSeed(bArr);
            keyGenerator.init(128, secureRandom);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
