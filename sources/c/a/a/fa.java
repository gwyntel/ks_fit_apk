package c.a.a;

import java.util.Enumeration;

/* loaded from: classes2.dex */
public class fa extends AbstractC0368s {

    /* renamed from: b, reason: collision with root package name */
    public int f7827b;

    public fa() {
        this.f7827b = -1;
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        C0367q c0367qA = c0367q.a();
        int iJ = j();
        c0367q.a(48);
        c0367q.b(iJ);
        Enumeration enumerationG = g();
        while (enumerationG.hasMoreElements()) {
            c0367qA.a((InterfaceC0346f) enumerationG.nextElement());
        }
    }

    @Override // c.a.a.r
    public int c() {
        int iJ = j();
        return Ba.a(iJ) + 1 + iJ;
    }

    public final int j() {
        if (this.f7827b < 0) {
            Enumeration enumerationG = g();
            int iC = 0;
            while (enumerationG.hasMoreElements()) {
                iC += ((InterfaceC0346f) enumerationG.nextElement()).toASN1Primitive().e().c();
            }
            this.f7827b = iC;
        }
        return this.f7827b;
    }

    public fa(C0347g c0347g) {
        super(c0347g);
        this.f7827b = -1;
    }
}
