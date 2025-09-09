package c.a.a.h;

import com.yc.utesdk.ble.close.KeyType;
import java.math.BigInteger;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
class v extends X9ECParametersHolder {
    @Override // org.spongycastle.asn1.x9.X9ECParametersHolder
    public X9ECParameters createParameters() {
        BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
        BigInteger bigIntegerB = I.b("027B680AC8B8596DA5A4AF8A19A0303FCA97FD7645309FA2A581485AF6263E313B79A2F5");
        byte[] bArrDecode = Hex.decode("77E2B07370EB0F832A6DD5B62DFC88CD06BB84BE");
        BigInteger bigIntegerB2 = I.b("03FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEF90399660FC938A90165B042A7CEFADB307");
        BigInteger bigIntegerValueOf2 = BigInteger.valueOf(2L);
        ECCurve eCCurveB = I.b(new ECCurve.F2m(KeyType.SYNC_AI_AGENT_MEMO_TO_DEVICE, 5, 7, 12, bigIntegerValueOf, bigIntegerB, bigIntegerB2, bigIntegerValueOf2));
        return new X9ECParameters(eCCurveB, new X9ECPoint(eCCurveB, Hex.decode("0405F939258DB7DD90E1934F8C70B0DFEC2EED25B8557EAC9C80E2E198F8CDBECD86B1205303676854FE24141CB98FE6D4B20D02B4516FF702350EDDB0826779C813F0DF45BE8112F4")), bigIntegerB2, bigIntegerValueOf2, bArrDecode);
    }
}
