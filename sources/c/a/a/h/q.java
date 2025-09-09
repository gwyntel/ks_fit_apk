package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class q extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = I.b("0163F35A5137C2CE3EA6ED8667190B0BC43ECD69977702709B");
        BigInteger bigIntegerB2 = I.b("00C9BB9E8927D4D64C377E2AB2856A5B16E3EFB7F61D4316AE");
        byte[] bArrDecode = Hex.decode("10B7B4D696E676875615175137C8A16FD0DA2211");
        BigInteger bigIntegerB3 = I.b("010000000000000000000000015AAB561B005413CCD4EE99D5");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(2L);
        ECCurve eCCurveB = I.b(new ECCurve.F2m(193, 15, bigIntegerB, bigIntegerB2, bigIntegerB3, bigIntegerValueOf));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("0400D9B67D192E0367C803F39E1A7E82CA14A651350AAE617E8F01CE94335607C304AC29E7DEFBD9CA01F596F927224CDECF6C")), bigIntegerB3, bigIntegerValueOf, bArrDecode);
    }
}
