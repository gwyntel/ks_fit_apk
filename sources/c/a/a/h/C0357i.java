package c.a.a.h;

import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* renamed from: c.a.a.h.i, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0357i extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerB = I.b("00689918DBEC7E5A0DD6DFC0AA55C7");
        BigInteger bigIntegerB2 = I.b("0095E9A9EC9B297BD4BF36E059184F");
        byte[] bArrDecode = Hex.decode("10C0FB15760860DEF1EEF4D696E676875615175D");
        BigInteger bigIntegerB3 = I.b("010000000000000108789B2496AF93");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(2L);
        ECCurve eCCurveB = I.b(new ECCurve.F2m(113, 9, bigIntegerB, bigIntegerB2, bigIntegerB3, bigIntegerValueOf));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("0401A57A6A7B26CA5EF52FCDB816479700B3ADC94ED1FE674C06E695BABA1D")), bigIntegerB3, bigIntegerValueOf, bArrDecode);
    }
}
