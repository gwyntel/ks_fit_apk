package c.a.a;

import java.io.IOException;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Exception;

/* loaded from: classes2.dex */
public class L extends AbstractC0374y {
    public L(boolean z2, int i2, InterfaceC0346f interfaceC0346f) {
        super(z2, i2, interfaceC0346f);
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) throws IOException {
        Enumeration enumerationG;
        c0367q.a(160, this.f7991a);
        c0367q.a(128);
        if (!this.f7992b) {
            if (this.f7993c) {
                c0367q.a(this.f7994d);
            } else {
                InterfaceC0346f interfaceC0346f = this.f7994d;
                if (interfaceC0346f instanceof AbstractC0365o) {
                    enumerationG = interfaceC0346f instanceof F ? ((F) interfaceC0346f).i() : new F(((AbstractC0365o) interfaceC0346f).g()).i();
                } else if (interfaceC0346f instanceof AbstractC0368s) {
                    enumerationG = ((AbstractC0368s) interfaceC0346f).g();
                } else {
                    if (!(interfaceC0346f instanceof AbstractC0370u)) {
                        throw new ASN1Exception("not implemented: " + this.f7994d.getClass().getName());
                    }
                    enumerationG = ((AbstractC0370u) interfaceC0346f).g();
                }
                while (enumerationG.hasMoreElements()) {
                    c0367q.a((InterfaceC0346f) enumerationG.nextElement());
                }
            }
        }
        c0367q.a(0);
        c0367q.a(0);
    }

    @Override // c.a.a.r
    public int c() {
        int iB;
        if (this.f7992b) {
            return Ba.b(this.f7991a) + 1;
        }
        int iC = this.f7994d.toASN1Primitive().c();
        if (this.f7993c) {
            iB = Ba.b(this.f7991a) + Ba.a(iC);
        } else {
            iC--;
            iB = Ba.b(this.f7991a);
        }
        return iB + iC;
    }

    @Override // c.a.a.r
    public boolean d() {
        if (this.f7992b || this.f7993c) {
            return true;
        }
        return this.f7994d.toASN1Primitive().e().d();
    }
}
