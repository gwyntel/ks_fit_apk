package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class r extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigInteger = ECConstants.ZERO;
        BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
        BigInteger bigIntegerB = I.b("8000000000000000000000000000069D5BB915BCD46EFB1AD5F173ABDF");
        BigInteger bigIntegerValueOf2 = BigInteger.valueOf(4L);
        ECCurve eCCurveB = I.b(new ECCurve.F2m(233, 74, bigInteger, bigIntegerValueOf, bigIntegerB, bigIntegerValueOf2));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("04017232BA853A7E731AF129F22FF4149563A419C26BF50A4C9D6EEFAD612601DB537DECE819B7F70F555A67C427A8CD9BF18AEB9B56E0C11056FAE6A3")), bigIntegerB, bigIntegerValueOf2, (byte[]) null);
    }
}
