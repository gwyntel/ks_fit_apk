package org.spongycastle.jce.provider;

import c.a.a.C0364n;
import c.a.c.a.a.a;
import c.a.c.a.a.b;
import c.a.c.a.c.c;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.yc.utesdk.utils.close.AESUtils;
import java.security.AccessController;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public final class BouncyCastleProvider extends Provider implements a {
    public static final String ASYMMETRIC_PACKAGE = "org.spongycastle.jcajce.provider.asymmetric.";
    public static final String DIGEST_PACKAGE = "org.spongycastle.jcajce.provider.digest.";
    public static final String KEYSTORE_PACKAGE = "org.spongycastle.jcajce.provider.keystore.";
    public static final String PROVIDER_NAME = "SC";
    public static final String SECURE_RANDOM_PACKAGE = "org.spongycastle.jcajce.provider.drbg.";
    public static final String SYMMETRIC_PACKAGE = "org.spongycastle.jcajce.provider.symmetric.";

    /* renamed from: info, reason: collision with root package name */
    public static String f26788info = "BouncyCastle Security Provider v1.58";
    public static final b CONFIGURATION = new BouncyCastleProviderConfiguration();
    public static final Map keyInfoConverters = new HashMap();
    public static final String[] SYMMETRIC_GENERIC = {"PBEPBKDF1", "PBEPBKDF2", "PBEPKCS12", "TLSKDF"};
    public static final String[] SYMMETRIC_MACS = {"SipHash", "Poly1305"};
    public static final String[] SYMMETRIC_CIPHERS = {AESUtils.AES, "ARC4", "ARIA", "Blowfish", "Camellia", "CAST5", "CAST6", "ChaCha", "DES", "DESede", "GOST28147", "Grainv1", "Grain128", "HC128", "HC256", "IDEA", "Noekeon", "RC2", "RC5", "RC6", "Rijndael", "Salsa20", "SEED", "Serpent", "Shacal2", "Skipjack", "SM4", "TEA", "Twofish", "Threefish", "VMPC", "VMPCKSA3", "XTEA", "XSalsa20", "OpenSSLPBKDF", "DSTU7624"};
    public static final String[] ASYMMETRIC_GENERIC = {"X509", "IES"};
    public static final String[] ASYMMETRIC_CIPHERS = {"EC"};
    public static final String[] DIGESTS = {"GOST3411", "Keccak", "MD2", "MD4", Utils.MD5, "SHA1", "RIPEMD128", "RIPEMD160", "RIPEMD256", "RIPEMD320", "SHA224", "SHA256", "SHA384", "SHA512", "SHA3", "Skein", "SM3", "Tiger", "Whirlpool", "Blake2b", "DSTU7564"};
    public static final String[] KEYSTORES = {"BC", "BCFKS", "PKCS12"};
    public static final String[] SECURE_RANDOMS = {"DRBG"};

    public BouncyCastleProvider() {
        super(PROVIDER_NAME, 1.58d, f26788info);
        AccessController.doPrivileged(new PrivilegedAction() { // from class: org.spongycastle.jce.provider.BouncyCastleProvider.1
            @Override // java.security.PrivilegedAction
            public Object run() {
                BouncyCastleProvider.this.setup();
                return null;
            }
        });
    }

    public static c getAsymmetricKeyInfoConverter(C0364n c0364n) {
        c cVar;
        Map map = keyInfoConverters;
        synchronized (map) {
            cVar = (c) map.get(c0364n);
        }
        return cVar;
    }

    public static PrivateKey getPrivateKey(c.a.a.f.a aVar) {
        c asymmetricKeyInfoConverter = getAsymmetricKeyInfoConverter(aVar.c().getAlgorithm());
        if (asymmetricKeyInfoConverter == null) {
            return null;
        }
        return asymmetricKeyInfoConverter.generatePrivate(aVar);
    }

    public static PublicKey getPublicKey(c.a.a.j.b bVar) {
        c asymmetricKeyInfoConverter = getAsymmetricKeyInfoConverter(bVar.getAlgorithm().getAlgorithm());
        if (asymmetricKeyInfoConverter == null) {
            return null;
        }
        return asymmetricKeyInfoConverter.generatePublic(bVar);
    }

    private void loadAlgorithms(String str, String[] strArr) {
        for (int i2 = 0; i2 != strArr.length; i2++) {
            Class clsA = c.a.c.a.b.a.b.a(BouncyCastleProvider.class, str + strArr[i2] + "$Mappings");
            if (clsA != null) {
                try {
                    ((c.a.c.a.c.a) clsA.newInstance()).configure(this);
                } catch (Exception e2) {
                    throw new InternalError("cannot create instance of " + str + strArr[i2] + "$Mappings : " + e2);
                }
            }
        }
    }

    private void loadPQCKeys() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setup() {
        loadAlgorithms(ASYMMETRIC_PACKAGE, ASYMMETRIC_CIPHERS);
    }

    @Override // c.a.c.a.a.a
    public void addAlgorithm(String str, String str2) {
        if (!containsKey(str)) {
            put(str, str2);
            return;
        }
        throw new IllegalStateException("duplicate provider key (" + str + ") found");
    }

    @Override // c.a.c.a.a.a
    public void addAttributes(String str, Map<String, String> map) {
        for (String str2 : map.keySet()) {
            String str3 = str + " " + str2;
            if (containsKey(str3)) {
                throw new IllegalStateException("duplicate provider attribute key (" + str3 + ") found");
            }
            put(str3, map.get(str2));
        }
    }

    @Override // c.a.c.a.a.a
    public void addKeyInfoConverter(C0364n c0364n, c cVar) {
        Map map = keyInfoConverters;
        synchronized (map) {
            map.put(c0364n, cVar);
        }
    }

    public boolean hasAlgorithm(String str, String str2) {
        if (!containsKey(str + "." + str2)) {
            if (!containsKey("Alg.Alias." + str + "." + str2)) {
                return false;
            }
        }
        return true;
    }

    public void setParameter(String str, Object obj) {
        b bVar = CONFIGURATION;
        synchronized (bVar) {
            ((BouncyCastleProviderConfiguration) bVar).setParameter(str, obj);
        }
    }

    public void addAlgorithm(String str, C0364n c0364n, String str2) {
        addAlgorithm(str + "." + c0364n, str2);
        addAlgorithm(str + ".OID." + c0364n, str2);
    }
}
