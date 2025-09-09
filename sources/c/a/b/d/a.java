package c.a.b.d;

import c.a.b.b;
import c.a.b.h.d;
import c.a.b.h.e;
import c.a.b.h.h;
import c.a.b.h.i;
import c.a.b.j;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.FixedPointCombMultiplier;
import org.spongycastle.math.ec.WNafUtil;

/* loaded from: classes2.dex */
public class a implements b, ECConstants {

    /* renamed from: a, reason: collision with root package name */
    public d f8048a;

    /* renamed from: b, reason: collision with root package name */
    public SecureRandom f8049b;

    public void a(j jVar) {
        e eVar = (e) jVar;
        this.f8049b = eVar.a();
        this.f8048a = eVar.b();
        if (this.f8049b == null) {
            this.f8049b = new SecureRandom();
        }
    }

    public c.a.b.a b() {
        BigInteger bigIntegerD = this.f8048a.d();
        int iBitLength = bigIntegerD.bitLength();
        int i2 = iBitLength >>> 2;
        while (true) {
            BigInteger bigInteger = new BigInteger(iBitLength, this.f8049b);
            if (bigInteger.compareTo(ECConstants.TWO) >= 0 && bigInteger.compareTo(bigIntegerD) < 0 && WNafUtil.getNafWeight(bigInteger) >= i2) {
                return new c.a.b.a(new i(a().multiply(this.f8048a.b(), bigInteger), this.f8048a), new h(bigInteger, this.f8048a));
            }
        }
    }

    public ECMultiplier a() {
        return new FixedPointCombMultiplier();
    }
}
