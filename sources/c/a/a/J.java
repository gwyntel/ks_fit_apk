package c.a.a;

import java.util.Enumeration;

/* loaded from: classes2.dex */
public class J extends AbstractC0370u {
    public J() {
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(49);
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

    public J(InterfaceC0346f interfaceC0346f) {
        super(interfaceC0346f);
    }

    public J(C0347g c0347g) {
        super(c0347g, false);
    }

    public J(InterfaceC0346f[] interfaceC0346fArr) {
        super(interfaceC0346fArr, false);
    }
}
