package org.spongycastle.jcajce.provider.asymmetric;

import c.a.c.a.a.a;
import c.a.c.a.c.b;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class EC {
    public static final String PREFIX = "org.spongycastle.jcajce.provider.asymmetric.ec.";
    public static final Map<String, String> generalEcAttributes;

    public static class Mappings extends b {
        @Override // c.a.c.a.c.a
        public void configure(a aVar) {
            aVar.addAlgorithm("AlgorithmParameters.EC", "org.spongycastle.jcajce.provider.asymmetric.ec.AlgorithmParametersSpi");
            aVar.addAttributes("KeyAgreement.ECDH", EC.generalEcAttributes);
            aVar.addAlgorithm("KeyAgreement.ECDH", "org.spongycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi$DH");
            aVar.addAlgorithm("KeyFactory.ECDH", "org.spongycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi$ECDH");
            aVar.addAlgorithm("KeyPairGenerator.ECDH", "org.spongycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi$ECDH");
        }
    }

    static {
        HashMap map = new HashMap();
        generalEcAttributes = map;
        map.put("SupportedKeyClasses", "java.security.interfaces.ECPublicKey|java.security.interfaces.ECPrivateKey");
        map.put("SupportedKeyFormats", "PKCS#8|X.509");
    }
}
