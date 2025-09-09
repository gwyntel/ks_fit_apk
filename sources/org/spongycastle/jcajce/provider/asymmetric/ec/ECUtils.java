package org.spongycastle.jcajce.provider.asymmetric.ec;

import c.a.a.AbstractC0362l;
import c.a.a.C0364n;
import c.a.a.Z;
import c.a.b.h.b;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;

/* loaded from: classes5.dex */
public class ECUtils {
    public static b generatePublicKeyParameter(PublicKey publicKey) {
        return publicKey instanceof BCECPublicKey ? ((BCECPublicKey) publicKey).engineGetKeyParameters() : ECUtil.generatePublicKeyParameter(publicKey);
    }

    public static X9ECParameters getDomainParametersFromGenSpec(ECGenParameterSpec eCGenParameterSpec) {
        return getDomainParametersFromName(eCGenParameterSpec.getName());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v6, types: [org.spongycastle.asn1.x9.X9ECParameters] */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    public static X9ECParameters getDomainParametersFromName(String str) {
        try {
            str = (str.charAt(0) < '0' || str.charAt(0) > '2') ? str.indexOf(32) > 0 ? ECUtil.getNamedCurveByName(str.substring(str.indexOf(32) + 1)) : ECUtil.getNamedCurveByName(str) : ECUtil.getNamedCurveByOid(new C0364n(str));
            return str;
        } catch (IllegalArgumentException unused) {
            return ECUtil.getNamedCurveByName(str);
        }
    }

    public static X962Parameters getDomainParametersFromName(ECParameterSpec eCParameterSpec, boolean z2) {
        if (!(eCParameterSpec instanceof ECNamedCurveSpec)) {
            if (eCParameterSpec == null) {
                return new X962Parameters((AbstractC0362l) Z.f7690a);
            }
            ECCurve eCCurveConvertCurve = EC5Util.convertCurve(eCParameterSpec.getCurve());
            return new X962Parameters(new X9ECParameters(eCCurveConvertCurve, EC5Util.convertPoint(eCCurveConvertCurve, eCParameterSpec.getGenerator(), z2), eCParameterSpec.getOrder(), BigInteger.valueOf(eCParameterSpec.getCofactor()), eCParameterSpec.getCurve().getSeed()));
        }
        ECNamedCurveSpec eCNamedCurveSpec = (ECNamedCurveSpec) eCParameterSpec;
        C0364n namedCurveOid = ECUtil.getNamedCurveOid(eCNamedCurveSpec.getName());
        if (namedCurveOid == null) {
            namedCurveOid = new C0364n(eCNamedCurveSpec.getName());
        }
        return new X962Parameters(namedCurveOid);
    }
}
