package com.vivo.push.c;

import android.security.keystore.KeyGenParameterSpec;
import com.google.android.gms.stats.CodePackage;
import com.vivo.push.util.p;
import com.yc.utesdk.utils.close.AESUtils;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/* loaded from: classes4.dex */
public final class a implements c {

    /* renamed from: a, reason: collision with root package name */
    private KeyStore f23076a;

    /* renamed from: b, reason: collision with root package name */
    private SecretKey f23077b;

    public a() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, NoSuchProviderException, InvalidAlgorithmParameterException {
        a();
        b();
    }

    private void a() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            this.f23076a = keyStore;
            keyStore.load(null);
        } catch (Exception e2) {
            e2.printStackTrace();
            p.a("AesSecurity", "initKeyStore error" + e2.getMessage());
        }
    }

    private SecretKey b() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        try {
            SecretKey secretKey = this.f23077b;
            if (secretKey != null) {
                return secretKey;
            }
            if (c()) {
                this.f23077b = d();
            } else {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(AESUtils.AES, "AndroidKeyStore");
                keyGenerator.init(new KeyGenParameterSpec.Builder("AesKeyAlias", 3).setBlockModes(CodePackage.GCM).setEncryptionPaddings("NoPadding").setKeySize(256).build());
                this.f23077b = keyGenerator.generateKey();
            }
            return this.f23077b;
        } catch (Exception e2) {
            e2.printStackTrace();
            p.a("AesSecurity", "getSecretKey error" + e2.getMessage());
            return null;
        }
    }

    private boolean c() {
        try {
            if (this.f23076a == null) {
                a();
            }
            return this.f23076a.containsAlias("AesKeyAlias");
        } catch (Exception e2) {
            e2.printStackTrace();
            p.a("AesSecurity", "hasAESKey error" + e2.getMessage());
            return false;
        }
    }

    private SecretKey d() {
        try {
            return ((KeyStore.SecretKeyEntry) this.f23076a.getEntry("AesKeyAlias", null)).getSecretKey();
        } catch (Exception e2) {
            e2.printStackTrace();
            p.a("AesSecurity", "getAESSecretKey error" + e2.getMessage());
            return null;
        }
    }
}
