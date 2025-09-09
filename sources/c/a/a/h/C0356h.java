package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* renamed from: c.a.a.h.h, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0356h extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = I.b("003088250CA6E7C7FE649CE85820F7");
        BigInteger bigIntegerB2 = I.b("00E8BEE4D3E2260744188BE0E9C723");
        byte[] bArrDecode = Hex.decode("10E723AB14D696E6768756151756FEBF8FCB49A9");
        BigInteger bigIntegerB3 = I.b("0100000000000000D9CCEC8A39E56F");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(2L);
        ECCurve eCCurveB = I.b(new ECCurve.F2m(113, 9, bigIntegerB, bigIntegerB2, bigIntegerB3, bigIntegerValueOf));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("04009D73616F35F4AB1407D73562C10F00A52830277958EE84D1315ED31886")), bigIntegerB3, bigIntegerValueOf, bArrDecode);
    }
}
