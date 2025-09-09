package c.a.a;

import java.util.Enumeration;

/* loaded from: classes2.dex */
public class H extends AbstractC0368s {
    public H() {
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(48);
        c0367q.a(128);
        Enumeration enumerationG = g();
        while (enumerationG.hasMoreElements()) {
            c0367q.a((InterfaceC0346f) enumerationG.nextElement());
        }
        c0367q.a(0);
        c0367q.a(0);
    }

    @Override // c.a.a.r
    public int c() {
        Enumeration enumerationG = g();
        int iC = 0;
        while (enumerationG.hasMoreElements()) {
            iC += ((InterfaceC0346f) enumerationG.nextElement()).toASN1Primitive().c();
        }
        return iC + 4;
    }

    public H(InterfaceC0346f interfaceC0346f) {
        super(interfaceC0346f);
    }

    public H(C0347g c0347g) {
        super(c0347g);
    }
}
