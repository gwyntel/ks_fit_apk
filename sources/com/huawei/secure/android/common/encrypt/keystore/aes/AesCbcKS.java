package com.huawei.secure.android.common.encrypt.keystore.aes;

import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;
import com.huawei.secure.android.common.encrypt.utils.HexUtil;
import com.huawei.secure.android.common.encrypt.utils.b;
import com.yc.utesdk.utils.close.AESUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes4.dex */
public class AesCbcKS {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18302a = "CBCKS";

    /* renamed from: b, reason: collision with root package name */
    private static final String f18303b = "AndroidKeyStore";

    /* renamed from: c, reason: collision with root package name */
    private static final String f18304c = "AES/CBC/PKCS7Padding";

    /* renamed from: d, reason: collision with root package name */
    private static final String f18305d = "";

    /* renamed from: e, reason: collision with root package name */
    private static final int f18306e = 16;

    /* renamed from: f, reason: collision with root package name */
    private static final int f18307f = 256;

    /* renamed from: g, reason: collision with root package name */
    private static Map<String, SecretKey> f18308g = new HashMap();

    private static boolean a() {
        return true;
    }

    private static SecretKey b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (f18308g.get(str) == null) {
            a(str);
        }
        return f18308g.get(str);
    }

    public static String decrypt(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            b.b(f18302a, "alias or encrypt content is null");
            return "";
        }
        try {
            return new String(decrypt(str, HexUtil.hexStr2ByteArray(str2)), "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            b.b(f18302a, "encrypt: UnsupportedEncodingException");
            return "";
        }
    }

    public static String encrypt(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            b.b(f18302a, "encrypt 1 content is null");
            return "";
        }
        try {
            return HexUtil.byteArray2HexStr(encrypt(str, str2.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException unused) {
            b.b(f18302a, "encrypt: UnsupportedEncodingException");
            return "";
        }
    }

    private static synchronized SecretKey a(String str) {
        SecretKey secretKeyGenerateKey;
        try {
            b.c(f18302a, "load key");
            secretKeyGenerateKey = null;
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        KeyStore keyStore = KeyStore.getInstance(f18303b);
                                        keyStore.load(null);
                                        Key key = keyStore.getKey(str, null);
                                        if (key == null || !(key instanceof SecretKey)) {
                                            b.c(f18302a, "generate key");
                                            KeyGenerator keyGenerator = KeyGenerator.getInstance(AESUtils.AES, f18303b);
                                            keyGenerator.init(new KeyGenParameterSpec.Builder(str, 3).setBlockModes("CBC").setEncryptionPaddings("PKCS7Padding").setKeySize(256).build());
                                            secretKeyGenerateKey = keyGenerator.generateKey();
                                        } else {
                                            secretKeyGenerateKey = (SecretKey) key;
                                        }
                                    } catch (NoSuchAlgorithmException e2) {
                                        b.b(f18302a, "NoSuchAlgorithmException: " + e2.getMessage());
                                    } catch (NoSuchProviderException e3) {
                                        b.b(f18302a, "NoSuchProviderException: " + e3.getMessage());
                                    }
                                } catch (CertificateException e4) {
                                    b.b(f18302a, "CertificateException: " + e4.getMessage());
                                }
                            } catch (Exception e5) {
                                b.b(f18302a, "Exception: " + e5.getMessage());
                            }
                        } catch (InvalidAlgorithmParameterException e6) {
                            b.b(f18302a, "InvalidAlgorithmParameterException: " + e6.getMessage());
                        }
                    } catch (KeyStoreException e7) {
                        b.b(f18302a, "KeyStoreException: " + e7.getMessage());
                    }
                } catch (UnrecoverableKeyException e8) {
                    b.b(f18302a, "UnrecoverableKeyException: " + e8.getMessage());
                }
            } catch (IOException e9) {
                b.b(f18302a, "IOException: " + e9.getMessage());
            }
            f18308g.put(str, secretKeyGenerateKey);
        } catch (Throwable th) {
            throw th;
        }
        return secretKeyGenerateKey;
    }

    public static byte[] encrypt(String str, byte[] bArr) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] bArr2 = new byte[0];
        if (!TextUtils.isEmpty(str) && bArr != null) {
            if (!a()) {
                b.b(f18302a, "sdk version is too low");
                return bArr2;
            }
            try {
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                SecretKey secretKeyB = b(str);
                if (secretKeyB == null) {
                    b.b(f18302a, "encrypt secret key is null");
                    return bArr2;
                }
                cipher.init(1, secretKeyB);
                byte[] bArrDoFinal = cipher.doFinal(bArr);
                byte[] iv = cipher.getIV();
                if (iv != null && iv.length == 16) {
                    byte[] bArrCopyOf = Arrays.copyOf(iv, iv.length + bArrDoFinal.length);
                    System.arraycopy(bArrDoFinal, 0, bArrCopyOf, iv.length, bArrDoFinal.length);
                    return bArrCopyOf;
                }
                b.b(f18302a, "IV is invalid.");
                return bArr2;
            } catch (InvalidKeyException e2) {
                b.b(f18302a, "InvalidKeyException: " + e2.getMessage());
                return bArr2;
            } catch (NoSuchAlgorithmException e3) {
                b.b(f18302a, "NoSuchAlgorithmException: " + e3.getMessage());
                return bArr2;
            } catch (BadPaddingException e4) {
                b.b(f18302a, "BadPaddingException: " + e4.getMessage());
                return bArr2;
            } catch (IllegalBlockSizeException e5) {
                b.b(f18302a, "IllegalBlockSizeException: " + e5.getMessage());
                return bArr2;
            } catch (NoSuchPaddingException e6) {
                b.b(f18302a, "NoSuchPaddingException: " + e6.getMessage());
                return bArr2;
            } catch (Exception e7) {
                b.b(f18302a, "Exception: " + e7.getMessage());
                return bArr2;
            }
        }
        b.b(f18302a, "alias or encrypt content is null");
        return bArr2;
    }

    public static byte[] decrypt(String str, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] bArr2 = new byte[0];
        if (!TextUtils.isEmpty(str) && bArr != null) {
            if (!a()) {
                b.b(f18302a, "sdk version is too low");
                return bArr2;
            }
            if (bArr.length <= 16) {
                b.b(f18302a, "Decrypt source data is invalid.");
                return bArr2;
            }
            SecretKey secretKeyB = b(str);
            if (secretKeyB == null) {
                b.b(f18302a, "decrypt secret key is null");
                return bArr2;
            }
            byte[] bArrCopyOf = Arrays.copyOf(bArr, 16);
            try {
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(2, secretKeyB, new IvParameterSpec(bArrCopyOf));
                return cipher.doFinal(bArr, 16, bArr.length - 16);
            } catch (InvalidAlgorithmParameterException e2) {
                b.b(f18302a, "InvalidAlgorithmParameterException: " + e2.getMessage());
                return bArr2;
            } catch (InvalidKeyException e3) {
                b.b(f18302a, "InvalidKeyException: " + e3.getMessage());
                return bArr2;
            } catch (NoSuchAlgorithmException e4) {
                b.b(f18302a, "NoSuchAlgorithmException: " + e4.getMessage());
                return bArr2;
            } catch (BadPaddingException e5) {
                b.b(f18302a, "BadPaddingException: " + e5.getMessage());
                return bArr2;
            } catch (IllegalBlockSizeException e6) {
                b.b(f18302a, "IllegalBlockSizeException: " + e6.getMessage());
                return bArr2;
            } catch (NoSuchPaddingException e7) {
                b.b(f18302a, "NoSuchPaddingException: " + e7.getMessage());
                return bArr2;
            } catch (Exception e8) {
                b.b(f18302a, "Exception: " + e8.getMessage());
                return bArr2;
            }
        }
        b.b(f18302a, "alias or encrypt content is null");
        return bArr2;
    }
}
