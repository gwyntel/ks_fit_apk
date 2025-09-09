package org.spongycastle.jcajce.provider.asymmetric.util;

import c.a.a.f.a;
import c.a.a.j.b;
import c.a.c.a.c.c;
import com.huawei.hms.feature.dynamic.f.e;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/* loaded from: classes5.dex */
public abstract class BaseKeyFactorySpi extends KeyFactorySpi implements c {
    @Override // java.security.KeyFactorySpi
    public PrivateKey engineGeneratePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        if (!(keySpec instanceof PKCS8EncodedKeySpec)) {
            throw new InvalidKeySpecException("key spec not recognized");
        }
        try {
            return generatePrivate(a.getInstance(((PKCS8EncodedKeySpec) keySpec).getEncoded()));
        } catch (Exception e2) {
            throw new InvalidKeySpecException("encoded key spec not recognized: " + e2.getMessage());
        }
    }

    @Override // java.security.KeyFactorySpi
    public PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        if (!(keySpec instanceof X509EncodedKeySpec)) {
            throw new InvalidKeySpecException("key spec not recognized");
        }
        try {
            return generatePublic(b.getInstance(((X509EncodedKeySpec) keySpec).getEncoded()));
        } catch (Exception e2) {
            throw new InvalidKeySpecException("encoded key spec not recognized: " + e2.getMessage());
        }
    }

    @Override // java.security.KeyFactorySpi
    public KeySpec engineGetKeySpec(Key key, Class cls) throws InvalidKeySpecException {
        if (cls.isAssignableFrom(PKCS8EncodedKeySpec.class) && key.getFormat().equals("PKCS#8")) {
            return new PKCS8EncodedKeySpec(key.getEncoded());
        }
        if (cls.isAssignableFrom(X509EncodedKeySpec.class) && key.getFormat().equals(e.f16169b)) {
            return new X509EncodedKeySpec(key.getEncoded());
        }
        throw new InvalidKeySpecException("not implemented yet " + key + " " + cls);
    }
}
