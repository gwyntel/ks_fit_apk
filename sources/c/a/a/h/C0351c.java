package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* renamed from: c.a.a.h.c, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0351c extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = I.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000000000000000000000001");
        BigInteger bigIntegerB2 = I.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFE");
        BigInteger bigIntegerB3 = I.b("B4050A850C04B3ABF54132565044B0B7D7BFD8BA270B39432355FFB4");
        byte[] bArrDecode = Hex.decode("BD71344799D5C7FCDC45B59FA3B9AB8F6A948BC5");
        BigInteger bigIntegerB4 = I.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFF16A2E0B8F03E13DD29455C5C2A3D");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
        ECCurve eCCurveB = I.b(new ECCurve.Fp(bigIntegerB, bigIntegerB2, bigIntegerB3, bigIntegerB4, bigIntegerValueOf));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("04B70E0CBD6BB4BF7F321390B94A03C1D356C21122343280D6115C1D21BD376388B5F723FB4C22DFE6CD4375A05A07476444D5819985007E34")), bigIntegerB4, bigIntegerValueOf, bArrDecode);
    }
}
