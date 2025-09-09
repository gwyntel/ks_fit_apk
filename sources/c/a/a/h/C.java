package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class C extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = I.b("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFF");
        BigInteger bigIntegerB2 = I.b("D6031998D1B3BBFEBF59CC9BBFF9AEE1");
        BigInteger bigIntegerB3 = I.b("5EEEFCA380D02919DC2C6558BB6D8A5D");
        byte[] bArrDecode = Hex.decode("004D696E67687561517512D8F03431FCE63B88F4");
        BigInteger bigIntegerB4 = I.b("3FFFFFFF7FFFFFFFBE0024720613B5A3");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(4L);
        ECCurve eCCurveB = I.b(new ECCurve.Fp(bigIntegerB, bigIntegerB2, bigIntegerB3, bigIntegerB4, bigIntegerValueOf));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("047B6AA5D85E572983E6FB32A7CDEBC14027B6916A894D3AEE7106FE805FC34B44")), bigIntegerB4, bigIntegerValueOf, bArrDecode);
    }
}
