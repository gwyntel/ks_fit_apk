package com.huawei.secure.android.common.encrypt.keystore.rsa;

import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.sdk.m.n.d;
import com.huawei.secure.android.common.encrypt.utils.b;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

/* loaded from: classes4.dex */
public abstract class RSAEncryptKS {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18316a = "RSAEncryptKS";

    /* renamed from: b, reason: collision with root package name */
    private static final String f18317b = "AndroidKeyStore";

    /* renamed from: c, reason: collision with root package name */
    private static final String f18318c = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    /* renamed from: d, reason: collision with root package name */
    private static final String f18319d = "";

    /* renamed from: e, reason: collision with root package name */
    private static final int f18320e = 2048;

    /* renamed from: f, reason: collision with root package name */
    private static final int f18321f = 3072;

    private static boolean a() {
        return true;
    }

    private static PublicKey b(String str, boolean z2) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        if (!b(str)) {
            a(str, z2);
        }
        Certificate certificateC = c(str);
        if (certificateC != null) {
            return certificateC.getPublicKey();
        }
        return null;
    }

    private static Certificate c(String str) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        try {
            KeyStore keyStore = KeyStore.getInstance(f18317b);
            keyStore.load(null);
            return keyStore.getCertificate(str);
        } catch (IOException e2) {
            b.b(f18316a, "IOException: " + e2.getMessage());
            return null;
        } catch (KeyStoreException e3) {
            b.b(f18316a, "KeyStoreException: " + e3.getMessage());
            return null;
        } catch (NoSuchAlgorithmException e4) {
            b.b(f18316a, "NoSuchAlgorithmException: " + e4.getMessage());
            return null;
        } catch (CertificateException e5) {
            b.b(f18316a, "CertificateException: " + e5.getMessage());
            return null;
        } catch (Exception e6) {
            b.b(f18316a, "Exception: " + e6.getMessage());
            return null;
        }
    }

    @Deprecated
    public static String decrpyt(String str, String str2) {
        try {
            return new String(decrpyt(str, Base64.decode(str2, 0)), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            b.b(f18316a, "UnsupportedEncodingException: " + e2.getMessage());
            return "";
        } catch (Exception e3) {
            b.b(f18316a, "Exception: " + e3.getMessage());
            return "";
        }
    }

    public static String decrpytNew(String str, String str2) {
        try {
            return new String(decrpytNew(str, Base64.decode(str2, 0)), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            b.b(f18316a, "UnsupportedEncodingException: " + e2.getMessage());
            return "";
        } catch (Exception e3) {
            b.b(f18316a, "Exception: " + e3.getMessage());
            return "";
        }
    }

    @Deprecated
    public static String encrypt(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return "";
        }
        try {
            return Base64.encodeToString(encrypt(str, str2.getBytes("UTF-8")), 0);
        } catch (UnsupportedEncodingException e2) {
            b.b(f18316a, "UnsupportedEncodingException: " + e2.getMessage());
            return "";
        }
    }

    public static String encryptNew(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return "";
        }
        try {
            return Base64.encodeToString(encryptNew(str, str2.getBytes("UTF-8")), 0);
        } catch (UnsupportedEncodingException e2) {
            b.b(f18316a, "UnsupportedEncodingException: " + e2.getMessage());
            return "";
        }
    }

    private static byte[] a(String str, byte[] bArr, boolean z2) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, KeyStoreException, CertificateException, InvalidAlgorithmParameterException {
        byte[] bArr2 = new byte[0];
        if (TextUtils.isEmpty(str) || bArr == null) {
            b.b(f18316a, "alias or content is null");
            return bArr2;
        }
        if (!a()) {
            b.b(f18316a, "sdk version is too low");
            return bArr2;
        }
        PublicKey publicKeyB = b(str, z2);
        if (publicKeyB == null) {
            b.b(f18316a, "Public key is null");
            return bArr2;
        }
        try {
            Cipher cipher = Cipher.getInstance(f18318c);
            cipher.init(1, publicKeyB, new OAEPParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-1"), PSource.PSpecified.DEFAULT));
            return cipher.doFinal(bArr);
        } catch (InvalidAlgorithmParameterException e2) {
            b.b(f18316a, "InvalidAlgorithmParameterException: " + e2.getMessage());
            return bArr2;
        } catch (InvalidKeyException e3) {
            b.b(f18316a, "InvalidKeyException: " + e3.getMessage());
            return bArr2;
        } catch (NoSuchAlgorithmException e4) {
            b.b(f18316a, "NoSuchAlgorithmException: " + e4.getMessage());
            return bArr2;
        } catch (BadPaddingException e5) {
            b.b(f18316a, "BadPaddingException: " + e5.getMessage());
            return bArr2;
        } catch (IllegalBlockSizeException e6) {
            b.b(f18316a, "IllegalBlockSizeException: " + e6.getMessage());
            return bArr2;
        } catch (NoSuchPaddingException e7) {
            b.b(f18316a, "NoSuchPaddingException: " + e7.getMessage());
            return bArr2;
        } catch (Exception e8) {
            b.b(f18316a, "Exception: " + e8.getMessage());
            return bArr2;
        }
    }

    @Deprecated
    public static byte[] decrpyt(String str, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, KeyStoreException, CertificateException, InvalidAlgorithmParameterException {
        byte[] bArr2 = new byte[0];
        if (!TextUtils.isEmpty(str) && bArr != null) {
            if (!a()) {
                b.b(f18316a, "sdk version is too low");
                return bArr2;
            }
            PrivateKey privateKeyA = a(str);
            if (privateKeyA == null) {
                b.b(f18316a, "Private key is null");
                return bArr2;
            }
            try {
                Cipher cipher = Cipher.getInstance(f18318c);
                cipher.init(2, privateKeyA, new OAEPParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-1"), PSource.PSpecified.DEFAULT));
                return cipher.doFinal(bArr);
            } catch (InvalidAlgorithmParameterException e2) {
                b.b(f18316a, "InvalidAlgorithmParameterException: " + e2.getMessage());
                return bArr2;
            } catch (InvalidKeyException e3) {
                b.b(f18316a, "InvalidKeyException: " + e3.getMessage());
                return bArr2;
            } catch (NoSuchAlgorithmException e4) {
                b.b(f18316a, "NoSuchAlgorithmException: " + e4.getMessage());
                return bArr2;
            } catch (BadPaddingException e5) {
                b.b(f18316a, "BadPaddingException: " + e5.getMessage());
                return bArr2;
            } catch (IllegalBlockSizeException e6) {
                b.b(f18316a, "IllegalBlockSizeException: " + e6.getMessage());
                return bArr2;
            } catch (NoSuchPaddingException e7) {
                b.b(f18316a, "NoSuchPaddingException: " + e7.getMessage());
                return bArr2;
            } catch (Exception e8) {
                b.b(f18316a, "Exception: " + e8.getMessage());
                return bArr2;
            }
        }
        b.b(f18316a, "alias or encrypted content is null");
        return bArr2;
    }

    public static byte[] decrpytNew(String str, byte[] bArr) {
        return decrpyt(str, bArr);
    }

    @Deprecated
    public static byte[] encrypt(String str, byte[] bArr) {
        return a(str, bArr, false);
    }

    public static byte[] encryptNew(String str, byte[] bArr) {
        return a(str, bArr, true);
    }

    private static boolean b(String str) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        try {
            KeyStore keyStore = KeyStore.getInstance(f18317b);
            keyStore.load(null);
            return keyStore.getKey(str, null) != null;
        } catch (IOException e2) {
            b.b(f18316a, "IOException: " + e2.getMessage());
            return false;
        } catch (KeyStoreException e3) {
            b.b(f18316a, "KeyStoreException: " + e3.getMessage());
            return false;
        } catch (NoSuchAlgorithmException e4) {
            b.b(f18316a, "NoSuchAlgorithmException: " + e4.getMessage());
            return false;
        } catch (UnrecoverableKeyException e5) {
            b.b(f18316a, "UnrecoverableKeyException: " + e5.getMessage());
            return false;
        } catch (CertificateException e6) {
            b.b(f18316a, "CertificateException: " + e6.getMessage());
            return false;
        } catch (Exception e7) {
            b.b(f18316a, "Exception: " + e7.getMessage());
            return false;
        }
    }

    private static synchronized KeyPair a(String str, boolean z2) {
        KeyPair keyPairGenerateKeyPair = null;
        if (b(str)) {
            b.b(f18316a, "Key pair exits");
            return null;
        }
        b.c(f18316a, "generate key pair.");
        try {
            try {
                try {
                    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(d.f9568a, f18317b);
                    if (!z2) {
                        keyPairGenerator.initialize(new KeyGenParameterSpec.Builder(str, 2).setDigests("SHA-256", "SHA-512").setEncryptionPaddings("OAEPPadding").setKeySize(2048).build());
                    } else {
                        keyPairGenerator.initialize(new KeyGenParameterSpec.Builder(str, 2).setDigests("SHA-256", "SHA-512").setEncryptionPaddings("OAEPPadding").setKeySize(3072).build());
                    }
                    keyPairGenerateKeyPair = keyPairGenerator.generateKeyPair();
                } catch (InvalidAlgorithmParameterException e2) {
                    b.b(f18316a, "InvalidAlgorithmParameterException: " + e2.getMessage());
                } catch (NoSuchAlgorithmException e3) {
                    b.b(f18316a, "NoSuchAlgorithmException: " + e3.getMessage());
                }
            } catch (Exception e4) {
                b.b(f18316a, "Exception: " + e4.getMessage());
            }
        } catch (NoSuchProviderException e5) {
            b.b(f18316a, "NoSuchProviderException: " + e5.getMessage());
        }
        return keyPairGenerateKeyPair;
    }

    private static PrivateKey a(String str) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        if (!b(str)) {
            return null;
        }
        try {
            KeyStore keyStore = KeyStore.getInstance(f18317b);
            keyStore.load(null);
            return (PrivateKey) keyStore.getKey(str, null);
        } catch (IOException e2) {
            b.b(f18316a, "IOException: " + e2.getMessage());
            return null;
        } catch (KeyStoreException e3) {
            b.b(f18316a, "KeyStoreException: " + e3.getMessage());
            return null;
        } catch (NoSuchAlgorithmException e4) {
            b.b(f18316a, "NoSuchAlgorithmException: " + e4.getMessage());
            return null;
        } catch (UnrecoverableKeyException e5) {
            b.b(f18316a, "UnrecoverableKeyException: " + e5.getMessage());
            return null;
        } catch (CertificateException e6) {
            b.b(f18316a, "CertificateException: " + e6.getMessage());
            return null;
        } catch (Exception e7) {
            b.b(f18316a, "Exception: " + e7.getMessage());
            return null;
        }
    }
}
