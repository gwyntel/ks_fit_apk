package c.a.a;

import java.util.Enumeration;

/* loaded from: classes2.dex */
public class ra extends AbstractC0368s {

    /* renamed from: b, reason: collision with root package name */
    public int f7973b;

    public ra() {
        this.f7973b = -1;
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        C0367q c0367qB = c0367q.b();
        int iJ = j();
        c0367q.a(48);
        c0367q.b(iJ);
        Enumeration enumerationG = g();
        while (enumerationG.hasMoreElements()) {
            c0367qB.a((InterfaceC0346f) enumerationG.nextElement());
        }
    }

    @Override // c.a.a.r
    public int c() {
        int iJ = j();
        return Ba.a(iJ) + 1 + iJ;
    }

    public final int j() {
        if (this.f7973b < 0) {
            Enumeration enumerationG = g();
            int iC = 0;
            while (enumerationG.hasMoreElements()) {
                iC += ((InterfaceC0346f) enumerationG.nextElement()).toASN1Primitive().f().c();
            }
            this.f7973b = iC;
        }
        return this.f7973b;
    }

    public ra(InterfaceC0346f interfaceC0346f) {
        super(interfaceC0346f);
        this.f7973b = -1;
    }

    public ra(C0347g c0347g) {
        super(c0347g);
        this.f7973b = -1;
    }
}
