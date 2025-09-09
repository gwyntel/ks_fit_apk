package org.spongycastle.asn1.x9;

import c.a.a.AbstractC0362l;
import c.a.a.AbstractC0363m;
import c.a.a.AbstractC0374y;
import c.a.a.C0364n;
import c.a.a.InterfaceC0345e;
import c.a.a.r;

/* loaded from: classes5.dex */
public class X962Parameters extends AbstractC0363m implements InterfaceC0345e {
    public r params;

    public X962Parameters(X9ECParameters x9ECParameters) {
        this.params = null;
        this.params = x9ECParameters.toASN1Primitive();
    }

    public static X962Parameters getInstance(Object obj) {
        if (obj == null || (obj instanceof X962Parameters)) {
            return (X962Parameters) obj;
        }
        if (obj instanceof r) {
            return new X962Parameters((r) obj);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException("unknown object in getInstance()");
        }
        try {
            return new X962Parameters(r.a((byte[]) obj));
        } catch (Exception e2) {
            throw new IllegalArgumentException("unable to parse encoded data: " + e2.getMessage());
        }
    }

    public r getParameters() {
        return this.params;
    }

    public boolean isImplicitlyCA() {
        return this.params instanceof AbstractC0362l;
    }

    public boolean isNamedCurve() {
        return this.params instanceof C0364n;
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        return this.params;
    }

    public X962Parameters(C0364n c0364n) {
        this.params = c0364n;
    }

    public X962Parameters(AbstractC0362l abstractC0362l) {
        this.params = abstractC0362l;
    }

    public X962Parameters(r rVar) {
        this.params = rVar;
    }

    public static X962Parameters getInstance(AbstractC0374y abstractC0374y, boolean z2) {
        return getInstance(abstractC0374y.g());
    }
}
