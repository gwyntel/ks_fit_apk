package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class k extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = I.b("03E5A88919D7CAFCBF415F07C2176573B2");
        BigInteger bigIntegerB2 = I.b("04B8266A46C55657AC734CE38F018F2192");
        byte[] bArrDecode = Hex.decode("985BD3ADBAD4D696E676875615175A21B43A97E3");
        BigInteger bigIntegerB3 = I.b("0400000000000000016954A233049BA98F");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(2L);
        ECCurve eCCurveB = I.b(new ECCurve.F2m(131, 2, 3, 8, bigIntegerB, bigIntegerB2, bigIntegerB3, bigIntegerValueOf));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("040356DCD8F2F95031AD652D23951BB366A80648F06D867940A5366D9E265DE9EB240F")), bigIntegerB3, bigIntegerValueOf, bArrDecode);
    }
}
