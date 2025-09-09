package c.a.a.h;

import com.xiaomi.infra.galaxy.fds.Common;
import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class x extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigInteger = ECConstants.ZERO;
        BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
        BigInteger bigIntegerB = I.b("7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE5F83B2D4EA20400EC4557D5ED3E3E7CA5B4B5C83B8E01E5FCF");
        BigInteger bigIntegerValueOf2 = BigInteger.valueOf(4L);
        ECCurve eCCurveB = I.b(new ECCurve.F2m(Common.HTTP_STATUS_CONFLICT, 87, bigInteger, bigIntegerValueOf, bigIntegerB, bigIntegerValueOf2));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("040060F05F658F49C1AD3AB1890F7184210EFD0987E307C84C27ACCFB8F9F67CC2C460189EB5AAAA62EE222EB1B35540CFE902374601E369050B7C4E42ACBA1DACBF04299C3460782F918EA427E6325165E9EA10E3DA5F6C42E9C55215AA9CA27A5863EC48D8E0286B")), bigIntegerB, bigIntegerValueOf2, (byte[]) null);
    }
}
