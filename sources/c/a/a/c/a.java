package c.a.a.c;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class a extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = c.b("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF");
        BigInteger bigIntegerB2 = c.b("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC");
        BigInteger bigIntegerB3 = c.b("28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93");
        BigInteger bigIntegerB4 = c.b("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
        ECCurve eCCurveB = c.b(new ECCurve.Fp(bigIntegerB, bigIntegerB2, bigIntegerB3, bigIntegerB4, bigIntegerValueOf));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("0432C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0")), bigIntegerB4, bigIntegerValueOf, (byte[]) null);
    }
}
