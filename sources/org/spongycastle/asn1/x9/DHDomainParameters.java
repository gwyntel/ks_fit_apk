package org.spongycastle.asn1.x9;

import c.a.a.AbstractC0363m;
import c.a.a.AbstractC0368s;
import c.a.a.AbstractC0374y;
import c.a.a.C0347g;
import c.a.a.C0361k;
import c.a.a.InterfaceC0346f;
import c.a.a.fa;
import c.a.a.r;
import java.math.BigInteger;
import java.util.Enumeration;

/* loaded from: classes5.dex */
public class DHDomainParameters extends AbstractC0363m {

    /* renamed from: g, reason: collision with root package name */
    public C0361k f26771g;

    /* renamed from: j, reason: collision with root package name */
    public C0361k f26772j;

    /* renamed from: p, reason: collision with root package name */
    public C0361k f26773p;

    /* renamed from: q, reason: collision with root package name */
    public C0361k f26774q;
    public DHValidationParms validationParms;

    public DHDomainParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, DHValidationParms dHValidationParms) {
        if (bigInteger == null) {
            throw new IllegalArgumentException("'p' cannot be null");
        }
        if (bigInteger2 == null) {
            throw new IllegalArgumentException("'g' cannot be null");
        }
        if (bigInteger3 == null) {
            throw new IllegalArgumentException("'q' cannot be null");
        }
        this.f26773p = new C0361k(bigInteger);
        this.f26771g = new C0361k(bigInteger2);
        this.f26774q = new C0361k(bigInteger3);
        this.f26772j = new C0361k(bigInteger4);
        this.validationParms = dHValidationParms;
    }

    public static DHDomainParameters getInstance(AbstractC0374y abstractC0374y, boolean z2) {
        return getInstance(AbstractC0368s.getInstance(abstractC0374y, z2));
    }

    public static InterfaceC0346f getNext(Enumeration enumeration) {
        if (enumeration.hasMoreElements()) {
            return (InterfaceC0346f) enumeration.nextElement();
        }
        return null;
    }

    public C0361k getG() {
        return this.f26771g;
    }

    public C0361k getJ() {
        return this.f26772j;
    }

    public C0361k getP() {
        return this.f26773p;
    }

    public C0361k getQ() {
        return this.f26774q;
    }

    public DHValidationParms getValidationParms() {
        return this.validationParms;
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        C0347g c0347g = new C0347g();
        c0347g.a(this.f26773p);
        c0347g.a(this.f26771g);
        c0347g.a(this.f26774q);
        C0361k c0361k = this.f26772j;
        if (c0361k != null) {
            c0347g.a(c0361k);
        }
        DHValidationParms dHValidationParms = this.validationParms;
        if (dHValidationParms != null) {
            c0347g.a(dHValidationParms);
        }
        return new fa(c0347g);
    }

    public static DHDomainParameters getInstance(Object obj) {
        if (obj == null || (obj instanceof DHDomainParameters)) {
            return (DHDomainParameters) obj;
        }
        if (obj instanceof AbstractC0368s) {
            return new DHDomainParameters((AbstractC0368s) obj);
        }
        throw new IllegalArgumentException("Invalid DHDomainParameters: " + obj.getClass().getName());
    }

    public DHDomainParameters(C0361k c0361k, C0361k c0361k2, C0361k c0361k3, C0361k c0361k4, DHValidationParms dHValidationParms) {
        if (c0361k == null) {
            throw new IllegalArgumentException("'p' cannot be null");
        }
        if (c0361k2 == null) {
            throw new IllegalArgumentException("'g' cannot be null");
        }
        if (c0361k3 != null) {
            this.f26773p = c0361k;
            this.f26771g = c0361k2;
            this.f26774q = c0361k3;
            this.f26772j = c0361k4;
            this.validationParms = dHValidationParms;
            return;
        }
        throw new IllegalArgumentException("'q' cannot be null");
    }

    public DHDomainParameters(AbstractC0368s abstractC0368s) {
        if (abstractC0368s.h() >= 3 && abstractC0368s.h() <= 5) {
            Enumeration enumerationG = abstractC0368s.g();
            this.f26773p = C0361k.getInstance(enumerationG.nextElement());
            this.f26771g = C0361k.getInstance(enumerationG.nextElement());
            this.f26774q = C0361k.getInstance(enumerationG.nextElement());
            InterfaceC0346f next = getNext(enumerationG);
            if (next != null && (next instanceof C0361k)) {
                this.f26772j = C0361k.getInstance(next);
                next = getNext(enumerationG);
            }
            if (next != null) {
                this.validationParms = DHValidationParms.getInstance(next.toASN1Primitive());
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + abstractC0368s.h());
    }
}
