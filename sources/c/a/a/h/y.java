package c.a.a.h;

import com.xiaomi.infra.galaxy.fds.Common;
import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class y extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
        BigInteger bigIntegerB = I.b("0021A5C2C8EE9FEB5C4B9A753B7B476B7FD6422EF1F3DD674761FA99D6AC27C8A9A197B272822F6CD57A55AA4F50AE317B13545F");
        byte[] bArrDecode = Hex.decode("4099B5A457F9D69F79213D094C4BCD4D4262210B");
        BigInteger bigIntegerB2 = I.b("010000000000000000000000000000000000000000000000000001E2AAD6A612F33307BE5FA47C3C9E052F838164CD37D9A21173");
        BigInteger bigIntegerValueOf2 = BigInteger.valueOf(2L);
        ECCurve eCCurveB = I.b(new ECCurve.F2m(Common.HTTP_STATUS_CONFLICT, 87, bigIntegerValueOf, bigIntegerB, bigIntegerB2, bigIntegerValueOf2));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("04015D4860D088DDB3496B0C6064756260441CDE4AF1771D4DB01FFE5B34E59703DC255A868A1180515603AEAB60794E54BB7996A70061B1CFAB6BE5F32BBFA78324ED106A7636B9C5A7BD198D0158AA4F5488D08F38514F1FDF4B4F40D2181B3681C364BA0273C706")), bigIntegerB2, bigIntegerValueOf2, bArrDecode);
    }
}
