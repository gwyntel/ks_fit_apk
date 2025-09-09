package com.vivo.push.c;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.text.TextUtils;
import android.util.Base64;
import com.vivo.push.util.p;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Calendar;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes4.dex */
public final class e implements b {

    /* renamed from: a, reason: collision with root package name */
    private static PrivateKey f23082a;

    /* renamed from: b, reason: collision with root package name */
    private static PublicKey f23083b;

    /* renamed from: c, reason: collision with root package name */
    private static KeyStore f23084c;

    /* renamed from: d, reason: collision with root package name */
    private static X500Principal f23085d;

    /* renamed from: e, reason: collision with root package name */
    private Context f23086e;

    public e(Context context) {
        this.f23086e = context;
        try {
            b();
            a(context);
        } catch (Exception e2) {
            e2.printStackTrace();
            p.a("RsaSecurity", "init error" + e2.getMessage());
        }
    }

    private static boolean b(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            if (f23084c == null) {
                b();
            }
            return f23084c.containsAlias(str);
        } catch (Exception e2) {
            e2.printStackTrace();
            p.a("RsaSecurity", "getPrivateKeySigin error" + e2.getMessage());
            return false;
        }
    }

    @Override // com.vivo.push.c.b
    public final String a(String str) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, UnsupportedEncodingException {
        try {
            if (TextUtils.isEmpty(str) || b(this.f23086e) == null) {
                return null;
            }
            byte[] bytes = str.getBytes("UTF-8");
            PrivateKey privateKeyB = b(this.f23086e);
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKeyB);
            signature.update(bytes);
            String strEncodeToString = Base64.encodeToString(signature.sign(), 2);
            p.d("RsaSecurity", str.hashCode() + " = " + strEncodeToString);
            return strEncodeToString;
        } catch (Exception e2) {
            e2.printStackTrace();
            p.a("RsaSecurity", "signClientSDK error" + e2.getMessage());
            return null;
        }
    }

    private static void b() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            f23084c = keyStore;
            keyStore.load(null);
            f23085d = new X500Principal("CN=Push SDK, OU=VIVO, O=VIVO PUSH, C=CN");
        } catch (Exception e2) {
            e2.printStackTrace();
            p.a("RsaSecurity", "initKeyStore error" + e2.getMessage());
        }
    }

    @Override // com.vivo.push.c.b
    public final boolean a(byte[] bArr, PublicKey publicKey, byte[] bArr2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(bArr);
            return signature.verify(bArr2);
        } catch (Exception e2) {
            e2.printStackTrace();
            p.a("RsaSecurity", "verifyClientSDK error" + e2.getMessage());
            return false;
        }
    }

    private static PrivateKey b(Context context) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableEntryException {
        PrivateKey privateKey;
        try {
            privateKey = f23082a;
        } catch (Exception e2) {
            e2.printStackTrace();
            p.a("RsaSecurity", "getPrivateKeySigin error" + e2.getMessage());
        }
        if (privateKey != null) {
            return privateKey;
        }
        if (context == null) {
            p.d("RsaSecurity", " getPrivateKeySigin context == null ");
            return null;
        }
        if (!b("PushRsaKeyAlias")) {
            a(context);
        }
        KeyStore.Entry entry = f23084c.getEntry("PushRsaKeyAlias", null);
        if (entry instanceof KeyStore.PrivateKeyEntry) {
            PrivateKey privateKey2 = ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
            f23082a = privateKey2;
            return privateKey2;
        }
        return null;
    }

    private static void a(Context context) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        try {
            if (context == null) {
                p.d("RsaSecurity", " generateRSAKeyPairSign context == null ");
                return;
            }
            if (!b("PushRsaKeyAlias")) {
                Calendar calendar = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.add(1, 999);
                KeyPairGeneratorSpec keyPairGeneratorSpecBuild = new KeyPairGeneratorSpec.Builder(context.getApplicationContext()).setAlias("PushRsaKeyAlias").setSubject(f23085d).setSerialNumber(BigInteger.valueOf(1337L)).setStartDate(calendar.getTime()).setEndDate(calendar2.getTime()).build();
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(com.alipay.sdk.m.n.d.f9568a, "AndroidKeyStore");
                keyPairGenerator.initialize(keyPairGeneratorSpecBuild);
                keyPairGenerator.generateKeyPair();
                return;
            }
            p.d("RsaSecurity", " generateRSAKeyPairSign this keyAlias PushRsaKeyAlias is Created ");
        } catch (Exception e2) {
            e2.printStackTrace();
            p.a("RsaSecurity", "generateRSAKeyPairSign error" + e2.getMessage());
        }
    }

    @Override // com.vivo.push.c.b
    public final PublicKey a() throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableEntryException {
        PublicKey publicKey;
        try {
            publicKey = f23083b;
        } catch (Exception e2) {
            e2.printStackTrace();
            p.a("RsaSecurity", "getPublicKeySign error" + e2.getMessage());
        }
        if (publicKey != null) {
            return publicKey;
        }
        if (!b("PushRsaKeyAlias")) {
            a(this.f23086e);
        }
        KeyStore.Entry entry = f23084c.getEntry("PushRsaKeyAlias", null);
        if (entry instanceof KeyStore.PrivateKeyEntry) {
            PublicKey publicKey2 = ((KeyStore.PrivateKeyEntry) entry).getCertificate().getPublicKey();
            f23083b = publicKey2;
            return publicKey2;
        }
        return null;
    }
}
