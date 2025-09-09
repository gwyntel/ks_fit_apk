package com.xiaomi.accountsdk.diagnosis.encrypt;

import android.text.TextUtils;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes4.dex */
public class f {
    public static PublicKey a(String str) throws c {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalStateException("public key should not be empty");
        }
        try {
            return a(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e2) {
            throw new c("getPublicKey", e2.getCause());
        }
    }

    public static PublicKey a(byte[] bArr) throws c {
        if (bArr == null) {
            throw new IllegalStateException("public key bytes should not be empty");
        }
        try {
            return ((X509Certificate) CertificateFactory.getInstance(com.huawei.hms.feature.dynamic.f.e.f16169b).generateCertificate(new ByteArrayInputStream(bArr))).getPublicKey();
        } catch (CertificateException e2) {
            throw new c("getPublicKey", e2.getCause());
        }
    }

    public static byte[] a(byte[] bArr, Key key) throws c {
        try {
            return a(bArr, key, 1);
        } catch (c e2) {
            throw new c("encrypt", e2.getCause());
        }
    }

    public static byte[] a(byte[] bArr, Key key, int i2) throws c, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(i2, key);
            return cipher.doFinal(bArr);
        } catch (InvalidKeyException e2) {
            throw new c(e2.getCause());
        } catch (NoSuchAlgorithmException e3) {
            throw new c(e3.getCause());
        } catch (BadPaddingException e4) {
            throw new c(e4.getCause());
        } catch (IllegalBlockSizeException e5) {
            throw new c(e5.getCause());
        } catch (NoSuchPaddingException e6) {
            throw new c(e6.getCause());
        }
    }
}
