package c.a.a.h;

import com.yc.utesdk.ble.close.KeyType;
import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.endo.GLVTypeBParameters;
import org.spongycastle.util.encoders.Hex;

/* renamed from: c.a.a.h.d, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0352d extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = I.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F");
        BigInteger bigInteger = ECConstants.ZERO;
        BigInteger bigIntegerValueOf = BigInteger.valueOf(7L);
        BigInteger bigIntegerB2 = I.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141");
        BigInteger bigIntegerValueOf2 = BigInteger.valueOf(1L);
        ECCurve eCCurveB = I.b(new ECCurve.Fp(bigIntegerB, bigInteger, bigIntegerValueOf, bigIntegerB2, bigIntegerValueOf2), new GLVTypeBParameters(new BigInteger("7ae96a2b657c07106e64479eac3434e99cf0497512f58995c1396c28719501ee", 16), new BigInteger("5363ad4cc05c30e0a5261c028812645a122e22ea20816678df02967c1b23bd72", 16), new BigInteger[]{new BigInteger("3086d221a7d46bcde86c90e49284eb15", 16), new BigInteger("-e4437ed6010e88286f547fa90abfe4c3", 16)}, new BigInteger[]{new BigInteger("114ca50f7a8e2f3f657c1108d9d44cfd8", 16), new BigInteger("3086d221a7d46bcde86c90e49284eb15", 16)}, new BigInteger("3086d221a7d46bcde86c90e49284eb153dab", 16), new BigInteger("e4437ed6010e88286f547fa90abfe4c42212", 16), KeyType.SEND_MENSTRUAL_START_DAY));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("0479BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8")), bigIntegerB2, bigIntegerValueOf2, (byte[]) null);
    }
}
