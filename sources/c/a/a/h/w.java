package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class w extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = I.b("DB7C2ABF62E35E668076BEAD208B");
        BigInteger bigIntegerB2 = I.b("6127C24C05F38A0AAAF65C0EF02C");
        BigInteger bigIntegerB3 = I.b("51DEF1815DB5ED74FCC34C85D709");
        byte[] bArrDecode = Hex.decode("002757A1114D696E6768756151755316C05E0BD4");
        BigInteger bigIntegerB4 = I.b("36DF0AAFD8B8D7597CA10520D04B");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(4L);
        ECCurve eCCurveB = I.b(new ECCurve.Fp(bigIntegerB, bigIntegerB2, bigIntegerB3, bigIntegerB4, bigIntegerValueOf));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("044BA30AB5E892B4E1649DD0928643ADCD46F5882E3747DEF36E956E97")), bigIntegerB4, bigIntegerValueOf, bArrDecode);
    }
}
