package c.a.a.i;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class k extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigInteger = new BigInteger("D7C134AA264366862A18302575D0FB98D116BC4B6DDEBCA3A5A7939F", 16);
        BigInteger bigInteger2 = new BigInteger(HiAnalyticsConstant.KeyAndValue.NUMBER_01, 16);
        ECCurve eCCurveB = o.b(new ECCurve.Fp(new BigInteger("D7C134AA264366862A18302575D1D787B09F075797DA89F57EC8C0FF", 16), new BigInteger("D7C134AA264366862A18302575D1D787B09F075797DA89F57EC8C0FC", 16), new BigInteger("4B337D934104CD7BEF271BF60CED1ED20DA14C08B3BB64F18A60888D", 16), bigInteger, bigInteger2));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("046AB1E344CE25FF3896424E7FFE14762ECB49F8928AC0C76029B4D5800374E9F5143E568CD23F3F4D7C0D4B1E41C8CC0D1C6ABD5F1A46DB4C")), bigInteger, bigInteger2);
    }
}
