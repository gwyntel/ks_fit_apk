package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.endo.GLVTypeBParameters;
import org.spongycastle.util.encoders.Hex;

/* renamed from: c.a.a.h.b, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0350b extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = I.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFE56D");
        BigInteger bigInteger = ECConstants.ZERO;
        BigInteger bigIntegerValueOf = BigInteger.valueOf(5L);
        BigInteger bigIntegerB2 = I.b("010000000000000000000000000001DCE8D2EC6184CAF0A971769FB1F7");
        BigInteger bigIntegerValueOf2 = BigInteger.valueOf(1L);
        ECCurve eCCurveB = I.b(new ECCurve.Fp(bigIntegerB, bigInteger, bigIntegerValueOf, bigIntegerB2, bigIntegerValueOf2), new GLVTypeBParameters(new BigInteger("fe0e87005b4e83761908c5131d552a850b3f58b749c37cf5b84d6768", 16), new BigInteger("60dcd2104c4cbc0be6eeefc2bdd610739ec34e317f9b33046c9e4788", 16), new BigInteger[]{new BigInteger("6b8cf07d4ca75c88957d9d670591", 16), new BigInteger("-b8adf1378a6eb73409fa6c9c637d", 16)}, new BigInteger[]{new BigInteger("1243ae1b4d71613bc9f780a03690e", 16), new BigInteger("6b8cf07d4ca75c88957d9d670591", 16)}, new BigInteger("6b8cf07d4ca75c88957d9d67059037a4", 16), new BigInteger("b8adf1378a6eb73409fa6c9c637ba7f5", 16), 240));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("04A1455B334DF099DF30FC28A169A467E9E47075A90F7E650EB6B7A45C7E089FED7FBA344282CAFBD6F7E319F7C0B0BD59E2CA4BDB556D61A5")), bigIntegerB2, bigIntegerValueOf2, (byte[]) null);
    }
}
