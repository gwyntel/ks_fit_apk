package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* renamed from: c.a.a.h.f, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0354f extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = I.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFF");
        BigInteger bigIntegerB2 = I.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFC");
        BigInteger bigIntegerB3 = I.b("B3312FA7E23EE7E4988E056BE3F82D19181D9C6EFE8141120314088F5013875AC656398D8A2ED19D2A85C8EDD3EC2AEF");
        byte[] bArrDecode = Hex.decode("A335926AA319A27A1D00896A6773A4827ACDAC73");
        BigInteger bigIntegerB4 = I.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC7634D81F4372DDF581A0DB248B0A77AECEC196ACCC52973");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
        ECCurve eCCurveB = I.b(new ECCurve.Fp(bigIntegerB, bigIntegerB2, bigIntegerB3, bigIntegerB4, bigIntegerValueOf));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("04AA87CA22BE8B05378EB1C71EF320AD746E1D3B628BA79B9859F741E082542A385502F25DBF55296C3A545E3872760AB73617DE4A96262C6F5D9E98BF9292DC29F8F41DBD289A147CE9DA3113B5F0B8C00A60B1CE1D7E819D7A431D7C90EA0E5F")), bigIntegerB4, bigIntegerValueOf, bArrDecode);
    }
}
