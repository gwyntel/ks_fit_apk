package c.a.a;

import java.util.Enumeration;

/* loaded from: classes2.dex */
public class ha extends AbstractC0370u {

    /* renamed from: c, reason: collision with root package name */
    public int f7903c;

    public ha() {
        this.f7903c = -1;
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        C0367q c0367qA = c0367q.a();
        int iK = k();
        c0367q.a(49);
        c0367q.b(iK);
        Enumeration enumerationG = g();
        while (enumerationG.hasMoreElements()) {
            c0367qA.a((InterfaceC0346f) enumerationG.nextElement());
        }
    }

    @Override // c.a.a.r
    public int c() {
        int iK = k();
        return Ba.a(iK) + 1 + iK;
    }

    public final int k() {
        if (this.f7903c < 0) {
            Enumeration enumerationG = g();
            int iC = 0;
            while (enumerationG.hasMoreElements()) {
                iC += ((InterfaceC0346f) enumerationG.nextElement()).toASN1Primitive().e().c();
            }
            this.f7903c = iC;
        }
        return this.f7903c;
    }

    public ha(C0347g c0347g, boolean z2) {
        super(c0347g, z2);
        this.f7903c = -1;
    }
}
