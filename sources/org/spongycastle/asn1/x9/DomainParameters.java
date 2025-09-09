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
public class DomainParameters extends AbstractC0363m {

    /* renamed from: g, reason: collision with root package name */
    public final C0361k f26775g;

    /* renamed from: j, reason: collision with root package name */
    public final C0361k f26776j;

    /* renamed from: p, reason: collision with root package name */
    public final C0361k f26777p;

    /* renamed from: q, reason: collision with root package name */
    public final C0361k f26778q;
    public final ValidationParams validationParams;

    public DomainParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, ValidationParams validationParams) {
        if (bigInteger == null) {
            throw new IllegalArgumentException("'p' cannot be null");
        }
        if (bigInteger2 == null) {
            throw new IllegalArgumentException("'g' cannot be null");
        }
        if (bigInteger3 == null) {
            throw new IllegalArgumentException("'q' cannot be null");
        }
        this.f26777p = new C0361k(bigInteger);
        this.f26775g = new C0361k(bigInteger2);
        this.f26778q = new C0361k(bigInteger3);
        if (bigInteger4 != null) {
            this.f26776j = new C0361k(bigInteger4);
        } else {
            this.f26776j = null;
        }
        this.validationParams = validationParams;
    }

    public static DomainParameters getInstance(AbstractC0374y abstractC0374y, boolean z2) {
        return getInstance(AbstractC0368s.getInstance(abstractC0374y, z2));
    }

    public static InterfaceC0346f getNext(Enumeration enumeration) {
        if (enumeration.hasMoreElements()) {
            return (InterfaceC0346f) enumeration.nextElement();
        }
        return null;
    }

    public BigInteger getG() {
        return this.f26775g.g();
    }

    public BigInteger getJ() {
        C0361k c0361k = this.f26776j;
        if (c0361k == null) {
            return null;
        }
        return c0361k.g();
    }

    public BigInteger getP() {
        return this.f26777p.g();
    }

    public BigInteger getQ() {
        return this.f26778q.g();
    }

    public ValidationParams getValidationParams() {
        return this.validationParams;
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        C0347g c0347g = new C0347g();
        c0347g.a(this.f26777p);
        c0347g.a(this.f26775g);
        c0347g.a(this.f26778q);
        C0361k c0361k = this.f26776j;
        if (c0361k != null) {
            c0347g.a(c0361k);
        }
        ValidationParams validationParams = this.validationParams;
        if (validationParams != null) {
            c0347g.a(validationParams);
        }
        return new fa(c0347g);
    }

    public static DomainParameters getInstance(Object obj) {
        if (obj instanceof DomainParameters) {
            return (DomainParameters) obj;
        }
        if (obj != null) {
            return new DomainParameters(AbstractC0368s.getInstance(obj));
        }
        return null;
    }

    public DomainParameters(AbstractC0368s abstractC0368s) {
        if (abstractC0368s.h() >= 3 && abstractC0368s.h() <= 5) {
            Enumeration enumerationG = abstractC0368s.g();
            this.f26777p = C0361k.getInstance(enumerationG.nextElement());
            this.f26775g = C0361k.getInstance(enumerationG.nextElement());
            this.f26778q = C0361k.getInstance(enumerationG.nextElement());
            InterfaceC0346f next = getNext(enumerationG);
            if (next != null && (next instanceof C0361k)) {
                this.f26776j = C0361k.getInstance(next);
                next = getNext(enumerationG);
            } else {
                this.f26776j = null;
            }
            if (next != null) {
                this.validationParams = ValidationParams.getInstance(next.toASN1Primitive());
                return;
            } else {
                this.validationParams = null;
                return;
            }
        }
        throw new IllegalArgumentException("Bad sequence size: " + abstractC0368s.h());
    }
}
