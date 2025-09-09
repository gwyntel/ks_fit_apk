package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class m extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
        BigInteger bigIntegerValueOf2 = BigInteger.valueOf(1L);
        BigInteger bigIntegerB = I.b("04000000000000000000020108A2E0CC0D99F8A5EF");
        BigInteger bigIntegerValueOf3 = BigInteger.valueOf(2L);
        ECCurve eCCurveB = I.b(new ECCurve.F2m(163, 3, 6, 7, bigIntegerValueOf, bigIntegerValueOf2, bigIntegerB, bigIntegerValueOf3));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("0402FE13C0537BBC11ACAA07D793DE4E6D5E5C94EEE80289070FB05D38FF58321F2E800536D538CCDAA3D9")), bigIntegerB, bigIntegerValueOf3, (byte[]) null);
    }
}
