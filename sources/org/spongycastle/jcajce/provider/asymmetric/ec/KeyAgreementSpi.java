package org.spongycastle.jcajce.provider.asymmetric.ec;

import c.a.b.c;
import c.a.b.f;
import c.a.b.h.d;
import c.a.b.h.h;
import c.a.c.b.a;
import c.a.c.b.b;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.x9.X9IntegerConverter;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jce.interfaces.ECPrivateKey;
import org.spongycastle.jce.interfaces.ECPublicKey;

/* loaded from: classes5.dex */
public class KeyAgreementSpi extends BaseAgreementSpi {
    public static final X9IntegerConverter converter = new X9IntegerConverter();
    public c agreement;
    public String kaAlgorithm;
    public a mqvParameters;
    public d parameters;
    public BigInteger result;

    public static class DH extends KeyAgreementSpi {
        public DH() {
            super("ECDH", new c.a.b.a.a(), null);
        }
    }

    public KeyAgreementSpi(String str, c cVar, f fVar) {
        super(str, fVar);
        this.kaAlgorithm = str;
        this.agreement = cVar;
    }

    public static String getSimpleName(Class cls) {
        String name = cls.getName();
        return name.substring(name.lastIndexOf(46) + 1);
    }

    private void initFromKey(Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException {
        if (key instanceof PrivateKey) {
            h hVar = (h) ECUtil.generatePrivateKeyParameter((PrivateKey) key);
            this.parameters = hVar.a();
            this.ukmParameters = algorithmParameterSpec instanceof b ? ((b) algorithmParameterSpec).a() : null;
            this.agreement.init(hVar);
            return;
        }
        throw new InvalidKeyException(this.kaAlgorithm + " key agreement requires " + getSimpleName(ECPrivateKey.class) + " for initialisation");
    }

    public byte[] bigIntToBytes(BigInteger bigInteger) {
        X9IntegerConverter x9IntegerConverter = converter;
        return x9IntegerConverter.integerToBytes(bigInteger, x9IntegerConverter.getByteLength(this.parameters.a()));
    }

    @Override // org.spongycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi
    public byte[] calcSecret() {
        return bigIntToBytes(this.result);
    }

    @Override // javax.crypto.KeyAgreementSpi
    public Key engineDoPhase(Key key, boolean z2) throws InvalidKeyException {
        if (this.parameters == null) {
            throw new IllegalStateException(this.kaAlgorithm + " not initialised.");
        }
        if (!z2) {
            throw new IllegalStateException(this.kaAlgorithm + " can only be between two parties.");
        }
        if (!(key instanceof PublicKey)) {
            throw new InvalidKeyException(this.kaAlgorithm + " key agreement requires " + getSimpleName(ECPublicKey.class) + " for doPhase");
        }
        try {
            this.result = this.agreement.a(ECUtils.generatePublicKeyParameter((PublicKey) key));
            return null;
        } catch (Exception e2) {
            throw new InvalidKeyException("calculation failed: " + e2.getMessage()) { // from class: org.spongycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi.1
                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return e2;
                }
            };
        }
    }

    @Override // javax.crypto.KeyAgreementSpi
    public void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameterSpec != null && !(algorithmParameterSpec instanceof b)) {
            throw new InvalidAlgorithmParameterException("No algorithm parameters supported");
        }
        initFromKey(key, algorithmParameterSpec);
    }

    @Override // javax.crypto.KeyAgreementSpi
    public void engineInit(Key key, SecureRandom secureRandom) throws InvalidKeyException {
        initFromKey(key, null);
    }
}
