package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class l extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = I.b("DB7C2ABF62E35E668076BEAD208B");
        BigInteger bigIntegerB2 = I.b("DB7C2ABF62E35E668076BEAD2088");
        BigInteger bigIntegerB3 = I.b("659EF8BA043916EEDE8911702B22");
        byte[] bArrDecode = Hex.decode("00F50B028E4D696E676875615175290472783FB1");
        BigInteger bigIntegerB4 = I.b("DB7C2ABF62E35E7628DFAC6561C5");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
        ECCurve eCCurveB = I.b(new ECCurve.Fp(bigIntegerB, bigIntegerB2, bigIntegerB3, bigIntegerB4, bigIntegerValueOf));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("0409487239995A5EE76B55F9C2F098A89CE5AF8724C0A23E0E0FF77500")), bigIntegerB4, bigIntegerValueOf, bArrDecode);
    }
}
