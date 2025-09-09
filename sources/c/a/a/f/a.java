package c.a.a.f;

import c.a.a.AbstractC0363m;
import c.a.a.AbstractC0365o;
import c.a.a.AbstractC0368s;
import c.a.a.AbstractC0370u;
import c.a.a.AbstractC0374y;
import c.a.a.C0342ba;
import c.a.a.C0347g;
import c.a.a.C0361k;
import c.a.a.InterfaceC0346f;
import c.a.a.fa;
import c.a.a.ka;
import c.a.a.r;
import java.util.Enumeration;

/* loaded from: classes2.dex */
public class a extends AbstractC0363m {

    /* renamed from: a, reason: collision with root package name */
    public AbstractC0365o f7824a;

    /* renamed from: b, reason: collision with root package name */
    public c.a.a.j.a f7825b;

    /* renamed from: c, reason: collision with root package name */
    public AbstractC0370u f7826c;

    public a(c.a.a.j.a aVar, InterfaceC0346f interfaceC0346f) {
        this(aVar, interfaceC0346f, null);
    }

    public static a getInstance(Object obj) {
        if (obj instanceof a) {
            return (a) obj;
        }
        if (obj != null) {
            return new a(AbstractC0368s.getInstance(obj));
        }
        return null;
    }

    public c.a.a.j.a c() {
        return this.f7825b;
    }

    public InterfaceC0346f d() {
        return r.a(this.f7824a.g());
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        C0347g c0347g = new C0347g();
        c0347g.a(new C0361k(0L));
        c0347g.a(this.f7825b);
        c0347g.a(this.f7824a);
        AbstractC0370u abstractC0370u = this.f7826c;
        if (abstractC0370u != null) {
            c0347g.a(new ka(false, 0, abstractC0370u));
        }
        return new fa(c0347g);
    }

    public a(c.a.a.j.a aVar, InterfaceC0346f interfaceC0346f, AbstractC0370u abstractC0370u) {
        this.f7824a = new C0342ba(interfaceC0346f.toASN1Primitive().getEncoded("DER"));
        this.f7825b = aVar;
        this.f7826c = abstractC0370u;
    }

    public a(AbstractC0368s abstractC0368s) {
        Enumeration enumerationG = abstractC0368s.g();
        if (((C0361k) enumerationG.nextElement()).getValue().intValue() == 0) {
            this.f7825b = c.a.a.j.a.getInstance(enumerationG.nextElement());
            this.f7824a = AbstractC0365o.getInstance(enumerationG.nextElement());
            if (enumerationG.hasMoreElements()) {
                this.f7826c = AbstractC0370u.getInstance((AbstractC0374y) enumerationG.nextElement(), false);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("wrong version for private key info");
    }
}
