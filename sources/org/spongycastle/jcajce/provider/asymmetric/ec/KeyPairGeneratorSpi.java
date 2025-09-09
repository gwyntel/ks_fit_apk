package org.spongycastle.jcajce.provider.asymmetric.ec;

import androidx.media3.exoplayer.RendererCapabilities;
import c.a.a.C0364n;
import c.a.b.d.a;
import c.a.b.h.d;
import c.a.b.h.e;
import c.a.b.h.h;
import c.a.b.h.i;
import c.a.c.a.a.b;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.util.Hashtable;
import org.spongycastle.asn1.x9.ECNamedCurveTable;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.jce.spec.ECParameterSpec;
import org.spongycastle.math.ec.ECCurve;

/* loaded from: classes5.dex */
public abstract class KeyPairGeneratorSpi extends KeyPairGenerator {

    public static class ECDH extends EC {
        public ECDH() {
            super("ECDH", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public KeyPairGeneratorSpi(String str) {
        super(str);
    }

    public static class EC extends KeyPairGeneratorSpi {
        public static Hashtable ecParameters;
        public String algorithm;
        public int certainty;
        public b configuration;
        public Object ecParams;
        public a engine;
        public boolean initialised;
        public e param;
        public SecureRandom random;
        public int strength;

        static {
            Hashtable hashtable = new Hashtable();
            ecParameters = hashtable;
            hashtable.put(c.a.d.e.a(192), new ECGenParameterSpec("prime192v1"));
            ecParameters.put(c.a.d.e.a(239), new ECGenParameterSpec("prime239v1"));
            ecParameters.put(c.a.d.e.a(256), new ECGenParameterSpec("prime256v1"));
            ecParameters.put(c.a.d.e.a(224), new ECGenParameterSpec("P-224"));
            ecParameters.put(c.a.d.e.a(RendererCapabilities.DECODER_SUPPORT_MASK), new ECGenParameterSpec("P-384"));
            ecParameters.put(c.a.d.e.a(DeviceCommonConstants.WHAT_GW_CONN_TIMEOUT), new ECGenParameterSpec("P-521"));
        }

        public EC() {
            super("EC");
            this.engine = new a();
            this.ecParams = null;
            this.strength = 239;
            this.certainty = 50;
            this.random = new SecureRandom();
            this.initialised = false;
            this.algorithm = "EC";
            this.configuration = BouncyCastleProvider.CONFIGURATION;
        }

        public e createKeyGenParamsBC(ECParameterSpec eCParameterSpec, SecureRandom secureRandom) {
            return new e(new d(eCParameterSpec.getCurve(), eCParameterSpec.getG(), eCParameterSpec.getN(), eCParameterSpec.getH()), secureRandom);
        }

        public e createKeyGenParamsJCE(java.security.spec.ECParameterSpec eCParameterSpec, SecureRandom secureRandom) {
            ECCurve eCCurveConvertCurve = EC5Util.convertCurve(eCParameterSpec.getCurve());
            return new e(new d(eCCurveConvertCurve, EC5Util.convertPoint(eCCurveConvertCurve, eCParameterSpec.getGenerator(), false), eCParameterSpec.getOrder(), BigInteger.valueOf(eCParameterSpec.getCofactor())), secureRandom);
        }

        public ECNamedCurveSpec createNamedCurveSpec(String str) throws InvalidAlgorithmParameterException {
            X9ECParameters domainParametersFromName = ECUtils.getDomainParametersFromName(str);
            if (domainParametersFromName == null) {
                try {
                    domainParametersFromName = ECNamedCurveTable.getByOID(new C0364n(str));
                    if (domainParametersFromName == null && (domainParametersFromName = (X9ECParameters) this.configuration.getAdditionalECParameters().get(new C0364n(str))) == null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("unknown curve OID: ");
                        sb.append(str);
                        throw new InvalidAlgorithmParameterException(sb.toString());
                    }
                } catch (IllegalArgumentException unused) {
                    throw new InvalidAlgorithmParameterException("unknown curve name: " + str);
                }
            }
            return new ECNamedCurveSpec(str, domainParametersFromName.getCurve(), domainParametersFromName.getG(), domainParametersFromName.getN(), domainParametersFromName.getH(), null);
        }

        @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
        public KeyPair generateKeyPair() {
            if (!this.initialised) {
                initialize(this.strength, new SecureRandom());
            }
            c.a.b.a aVarB = this.engine.b();
            i iVar = (i) aVarB.b();
            h hVar = (h) aVarB.a();
            Object obj = this.ecParams;
            if (obj instanceof ECParameterSpec) {
                ECParameterSpec eCParameterSpec = (ECParameterSpec) obj;
                BCECPublicKey bCECPublicKey = new BCECPublicKey(this.algorithm, iVar, eCParameterSpec, this.configuration);
                return new KeyPair(bCECPublicKey, new BCECPrivateKey(this.algorithm, hVar, bCECPublicKey, eCParameterSpec, this.configuration));
            }
            if (obj == null) {
                return new KeyPair(new BCECPublicKey(this.algorithm, iVar, this.configuration), new BCECPrivateKey(this.algorithm, hVar, this.configuration));
            }
            java.security.spec.ECParameterSpec eCParameterSpec2 = (java.security.spec.ECParameterSpec) obj;
            BCECPublicKey bCECPublicKey2 = new BCECPublicKey(this.algorithm, iVar, eCParameterSpec2, this.configuration);
            return new KeyPair(bCECPublicKey2, new BCECPrivateKey(this.algorithm, hVar, bCECPublicKey2, eCParameterSpec2, this.configuration));
        }

        @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
        public void initialize(int i2, SecureRandom secureRandom) {
            this.strength = i2;
            this.random = secureRandom;
            ECGenParameterSpec eCGenParameterSpec = (ECGenParameterSpec) ecParameters.get(c.a.d.e.a(i2));
            if (eCGenParameterSpec == null) {
                throw new InvalidParameterException("unknown key size.");
            }
            try {
                initialize(eCGenParameterSpec, secureRandom);
            } catch (InvalidAlgorithmParameterException unused) {
                throw new InvalidParameterException("key size not configurable.");
            }
        }

        public void initializeNamedCurve(String str, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            ECNamedCurveSpec eCNamedCurveSpecCreateNamedCurveSpec = createNamedCurveSpec(str);
            this.ecParams = eCNamedCurveSpecCreateNamedCurveSpec;
            this.param = createKeyGenParamsJCE(eCNamedCurveSpecCreateNamedCurveSpec, secureRandom);
        }

        @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
        public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            if (algorithmParameterSpec == null) {
                ECParameterSpec ecImplicitlyCa = this.configuration.getEcImplicitlyCa();
                if (ecImplicitlyCa != null) {
                    this.ecParams = null;
                    this.param = createKeyGenParamsBC(ecImplicitlyCa, secureRandom);
                } else {
                    throw new InvalidAlgorithmParameterException("null parameter passed but no implicitCA set");
                }
            } else if (algorithmParameterSpec instanceof ECParameterSpec) {
                this.ecParams = algorithmParameterSpec;
                this.param = createKeyGenParamsBC((ECParameterSpec) algorithmParameterSpec, secureRandom);
            } else if (algorithmParameterSpec instanceof java.security.spec.ECParameterSpec) {
                this.ecParams = algorithmParameterSpec;
                this.param = createKeyGenParamsJCE((java.security.spec.ECParameterSpec) algorithmParameterSpec, secureRandom);
            } else if (algorithmParameterSpec instanceof ECGenParameterSpec) {
                initializeNamedCurve(((ECGenParameterSpec) algorithmParameterSpec).getName(), secureRandom);
            } else if (algorithmParameterSpec instanceof ECNamedCurveGenParameterSpec) {
                initializeNamedCurve(((ECNamedCurveGenParameterSpec) algorithmParameterSpec).getName(), secureRandom);
            } else {
                throw new InvalidAlgorithmParameterException("parameter object not a ECParameterSpec");
            }
            this.engine.a(this.param);
            this.initialised = true;
        }

        public EC(String str, b bVar) {
            super(str);
            this.engine = new a();
            this.ecParams = null;
            this.strength = 239;
            this.certainty = 50;
            this.random = new SecureRandom();
            this.initialised = false;
            this.algorithm = str;
            this.configuration = bVar;
        }
    }
}
