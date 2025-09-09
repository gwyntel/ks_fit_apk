package org.spongycastle.jce.spec;

import org.spongycastle.math.ec.ECPoint;

/* loaded from: classes5.dex */
public class ECPublicKeySpec extends ECKeySpec {

    /* renamed from: q, reason: collision with root package name */
    public ECPoint f26792q;

    public ECPublicKeySpec(ECPoint eCPoint, ECParameterSpec eCParameterSpec) {
        super(eCParameterSpec);
        if (eCPoint.getCurve() != null) {
            this.f26792q = eCPoint.normalize();
        } else {
            this.f26792q = eCPoint;
        }
    }

    public ECPoint getQ() {
        return this.f26792q;
    }
}
