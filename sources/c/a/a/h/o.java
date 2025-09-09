package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class o extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
        BigInteger bigIntegerB = I.b("020A601907B8C953CA1481EB10512F78744A3205FD");
        byte[] bArrDecode = Hex.decode("85E25BFE5C86226CDB12016F7553F9D0E693A268");
        BigInteger bigIntegerB2 = I.b("040000000000000000000292FE77E70C12A4234C33");
        BigInteger bigIntegerValueOf2 = BigInteger.valueOf(2L);
        ECCurve eCCurveB = I.b(new ECCurve.F2m(163, 3, 6, 7, bigIntegerValueOf, bigIntegerB, bigIntegerB2, bigIntegerValueOf2));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("0403F0EBA16286A2D57EA0991168D4994637E8343E3600D51FBC6C71A0094FA2CDD545B11C5C0C797324F1")), bigIntegerB2, bigIntegerValueOf2, bArrDecode);
    }
}
