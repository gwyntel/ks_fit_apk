package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* renamed from: c.a.a.h.j, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0358j extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = I.b("07A11B09A76B562144418FF3FF8C2570B8");
        BigInteger bigIntegerB2 = I.b("0217C05610884B63B9C6C7291678F9D341");
        byte[] bArrDecode = Hex.decode("4D696E676875615175985BD3ADBADA21B43A97E2");
        BigInteger bigIntegerB3 = I.b("0400000000000000023123953A9464B54D");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(2L);
        ECCurve eCCurveB = I.b(new ECCurve.F2m(131, 2, 3, 8, bigIntegerB, bigIntegerB2, bigIntegerB3, bigIntegerValueOf));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("040081BAF91FDF9833C40F9C181343638399078C6E7EA38C001F73C8134B1B4EF9E150")), bigIntegerB3, bigIntegerValueOf, bArrDecode);
    }
}
