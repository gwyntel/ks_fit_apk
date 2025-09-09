package c.a.a.c;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class b extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = c.b("BDB6F4FE3E8B1D9E0DA8C0D46F4C318CEFE4AFE3B6B8551F");
        BigInteger bigIntegerB2 = c.b("BB8E5E8FBC115E139FE6A814FE48AAA6F0ADA1AA5DF91985");
        BigInteger bigIntegerB3 = c.b("1854BEBDC31B21B7AEFC80AB0ECD10D5B1B3308E6DBF11C1");
        BigInteger bigIntegerB4 = c.b("BDB6F4FE3E8B1D9E0DA8C0D40FC962195DFAE76F56564677");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
        ECCurve eCCurveB = c.b(new ECCurve.Fp(bigIntegerB, bigIntegerB2, bigIntegerB3, bigIntegerB4, bigIntegerValueOf));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("044AD5F7048DE709AD51236DE65E4D4B482C836DC6E410664002BB3A02D4AAADACAE24817A4CA3A1B014B5270432DB27D2")), bigIntegerB4, bigIntegerValueOf, (byte[]) null);
    }
}
