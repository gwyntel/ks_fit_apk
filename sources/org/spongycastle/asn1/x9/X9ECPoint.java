package org.spongycastle.asn1.x9;

import c.a.a.AbstractC0363m;
import c.a.a.AbstractC0365o;
import c.a.a.C0342ba;
import c.a.a.r;
import c.a.d.a;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;

/* loaded from: classes5.dex */
public class X9ECPoint extends AbstractC0363m {

    /* renamed from: c, reason: collision with root package name */
    public ECCurve f26782c;
    public final AbstractC0365o encoding;

    /* renamed from: p, reason: collision with root package name */
    public ECPoint f26783p;

    public X9ECPoint(ECPoint eCPoint) {
        this(eCPoint, false);
    }

    public synchronized ECPoint getPoint() {
        try {
            if (this.f26783p == null) {
                this.f26783p = this.f26782c.decodePoint(this.encoding.g()).normalize();
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.f26783p;
    }

    public byte[] getPointEncoding() {
        return a.a(this.encoding.g());
    }

    public boolean isPointCompressed() {
        byte[] bArrG = this.encoding.g();
        if (bArrG == null || bArrG.length <= 0) {
            return false;
        }
        byte b2 = bArrG[0];
        return b2 == 2 || b2 == 3;
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        return this.encoding;
    }

    public X9ECPoint(ECPoint eCPoint, boolean z2) {
        this.f26783p = eCPoint.normalize();
        this.encoding = new C0342ba(eCPoint.getEncoded(z2));
    }

    public X9ECPoint(ECCurve eCCurve, byte[] bArr) {
        this.f26782c = eCCurve;
        this.encoding = new C0342ba(a.a(bArr));
    }

    public X9ECPoint(ECCurve eCCurve, AbstractC0365o abstractC0365o) {
        this(eCCurve, abstractC0365o.g());
    }
}
