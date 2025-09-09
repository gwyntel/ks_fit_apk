package c.a.a;

import java.util.Enumeration;

/* loaded from: classes2.dex */
public class sa extends AbstractC0370u {

    /* renamed from: c, reason: collision with root package name */
    public int f7975c;

    public sa() {
        this.f7975c = -1;
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        C0367q c0367qB = c0367q.b();
        int iK = k();
        c0367q.a(49);
        c0367q.b(iK);
        Enumeration enumerationG = g();
        while (enumerationG.hasMoreElements()) {
            c0367qB.a((InterfaceC0346f) enumerationG.nextElement());
        }
    }

    @Override // c.a.a.r
    public int c() {
        int iK = k();
        return Ba.a(iK) + 1 + iK;
    }

    public final int k() {
        if (this.f7975c < 0) {
            Enumeration enumerationG = g();
            int iC = 0;
            while (enumerationG.hasMoreElements()) {
                iC += ((InterfaceC0346f) enumerationG.nextElement()).toASN1Primitive().f().c();
            }
            this.f7975c = iC;
        }
        return this.f7975c;
    }

    public sa(InterfaceC0346f interfaceC0346f) {
        super(interfaceC0346f);
        this.f7975c = -1;
    }

    public sa(C0347g c0347g) {
        super(c0347g, false);
        this.f7975c = -1;
    }

    public sa(InterfaceC0346f[] interfaceC0346fArr) {
        super(interfaceC0346fArr, false);
        this.f7975c = -1;
    }
}
