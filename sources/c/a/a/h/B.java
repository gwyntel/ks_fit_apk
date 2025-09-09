package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class B extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = I.b("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFF");
        BigInteger bigIntegerB2 = I.b("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFC");
        BigInteger bigIntegerB3 = I.b("E87579C11079F43DD824993C2CEE5ED3");
        byte[] bArrDecode = Hex.decode("000E0D4D696E6768756151750CC03A4473D03679");
        BigInteger bigIntegerB4 = I.b("FFFFFFFE0000000075A30D1B9038A115");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
        ECCurve eCCurveB = I.b(new ECCurve.Fp(bigIntegerB, bigIntegerB2, bigIntegerB3, bigIntegerB4, bigIntegerValueOf));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("04161FF7528B899B2D0C28607CA52C5B86CF5AC8395BAFEB13C02DA292DDED7A83")), bigIntegerB4, bigIntegerValueOf, bArrDecode);
    }
}
