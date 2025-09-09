package com.xiaomi.accountsdk.diagnosis.encrypt;

import android.text.TextUtils;
import android.util.Base64;
import com.yc.utesdk.utils.close.AESUtils;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/* loaded from: classes4.dex */
public class PassportEnvEncryptUtils {

    public static class EncryptException extends Exception {
        public EncryptException(Throwable th) {
            super(th);
        }
    }

    public static class EncryptResult {
        public String content;
        public String encryptedKey;
    }

    private static String a(String str, SecretKey secretKey) throws EncryptException {
        try {
            return new a(secretKey.getEncoded()).a(str);
        } catch (b e2) {
            throw new EncryptException(e2);
        }
    }

    public static EncryptResult encrypt(String str) throws EncryptException, NoSuchAlgorithmException {
        EncryptResult encryptResult = new EncryptResult();
        SecretKey secretKeyA = a();
        try {
            String strEncodeToString = Base64.encodeToString(f.a(Base64.encode(secretKeyA.getEncoded(), 10), f.a("-----BEGIN CERTIFICATE-----\nMIICDzCCAXigAwIBAgIEWBw0IzANBgkqhkiG9w0BAQUFADBMMQswCQYDVQQGEwJD\nTjEPMA0GA1UEChMGeGlhb21pMQ8wDQYDVQQLEwZ4aWFvbWkxGzAZBgNVBAMTEmFj\nY291bnQueGlhb21pLmNvbTAeFw0xNjExMDQwNzA5MjNaFw0xNzExMDQwNzA5MjNa\nMEwxCzAJBgNVBAYTAkNOMQ8wDQYDVQQKEwZ4aWFvbWkxDzANBgNVBAsTBnhpYW9t\naTEbMBkGA1UEAxMSYWNjb3VudC54aWFvbWkuY29tMIGfMA0GCSqGSIb3DQEBAQUA\nA4GNADCBiQKBgQCHcPEm9Wo8/LWHL8mohOV5YalTgZLzng+nWCEkIRP//6GohYlI\nh3dvGpueJvQ3Sany/3dLx0x6MQKA34NxRyoO37R/LgPZUfe6eWzHQeColBBHxTED\nbCqDh46Gv5vogjqHRl4+q2WGCmZOIfmPjNHQWG8sMIZyTqFCLc6gk9vSewIDAQAB\nMA0GCSqGSIb3DQEBBQUAA4GBAHaPnscaxSPh0N0Z5OgQ6PcWr5uYPLMweatYGZRH\nSFxwSqYXpqIowuRxmrBj+oE5rG5rzFCtNjCBoeMVy/7JXZr9Juaw9NCWaTaqrmIV\nP4nK/0kizCvkx3088OOCGextGeZUC9/PCbVUEcRvGLwSrvgqiC1KG4ufeIdQWBaJ\n8ZlG\n-----END CERTIFICATE-----\n")), 10);
            encryptResult.content = a(str, secretKeyA);
            encryptResult.encryptedKey = strEncodeToString;
            return encryptResult;
        } catch (c e2) {
            throw new EncryptException(e2);
        }
    }

    private static SecretKey a() throws EncryptException, NoSuchAlgorithmException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AESUtils.AES);
            keyGenerator.init(new SecureRandom());
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e2) {
            throw new EncryptException(e2);
        }
    }

    public static EncryptResult encrypt(String[] strArr) {
        return encrypt(TextUtils.join(":", strArr));
    }
}
