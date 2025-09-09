package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class n extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = I.b("07B6882CAAEFA84F9554FF8428BD88E246D2782AE2");
        BigInteger bigIntegerB2 = I.b("0713612DCDDCB40AAB946BDA29CA91F73AF958AFD9");
        byte[] bArrDecode = Hex.decode("24B7B137C8A14D696E6768756151756FD0DA2E5C");
        BigInteger bigIntegerB3 = I.b("03FFFFFFFFFFFFFFFFFFFF48AAB689C29CA710279B");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(2L);
        ECCurve eCCurveB = I.b(new ECCurve.F2m(163, 3, 6, 7, bigIntegerB, bigIntegerB2, bigIntegerB3, bigIntegerValueOf));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("040369979697AB43897789566789567F787A7876A65400435EDB42EFAFB2989D51FEFCE3C80988F41FF883")), bigIntegerB3, bigIntegerValueOf, bArrDecode);
    }
}
