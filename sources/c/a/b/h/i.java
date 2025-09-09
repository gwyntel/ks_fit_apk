package c.a.b.h;

import org.spongycastle.math.ec.ECPoint;

/* loaded from: classes2.dex */
public class i extends f {

    /* renamed from: c, reason: collision with root package name */
    public final ECPoint f8103c;

    public i(ECPoint eCPoint, d dVar) {
        super(false, dVar);
        this.f8103c = a(eCPoint);
    }

    public final ECPoint a(ECPoint eCPoint) {
        if (eCPoint == null) {
            throw new IllegalArgumentException("point has null value");
        }
        if (eCPoint.isInfinity()) {
            throw new IllegalArgumentException("point at infinity");
        }
        ECPoint eCPointNormalize = eCPoint.normalize();
        if (eCPointNormalize.isValid()) {
            return eCPointNormalize;
        }
        throw new IllegalArgumentException("point not on curve");
    }

    public ECPoint b() {
        return this.f8103c;
    }
}
