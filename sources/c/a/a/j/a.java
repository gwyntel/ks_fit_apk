package c.a.a.j;

import c.a.a.AbstractC0363m;
import c.a.a.AbstractC0368s;
import c.a.a.C0347g;
import c.a.a.C0364n;
import c.a.a.InterfaceC0346f;
import c.a.a.fa;
import c.a.a.r;

/* loaded from: classes2.dex */
public class a extends AbstractC0363m {

    /* renamed from: a, reason: collision with root package name */
    public C0364n f7952a;

    /* renamed from: b, reason: collision with root package name */
    public InterfaceC0346f f7953b;

    public a(C0364n c0364n, InterfaceC0346f interfaceC0346f) {
        this.f7952a = c0364n;
        this.f7953b = interfaceC0346f;
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

    public C0364n getAlgorithm() {
        return this.f7952a;
    }

    public InterfaceC0346f getParameters() {
        return this.f7953b;
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        C0347g c0347g = new C0347g();
        c0347g.a(this.f7952a);
        InterfaceC0346f interfaceC0346f = this.f7953b;
        if (interfaceC0346f != null) {
            c0347g.a(interfaceC0346f);
        }
        return new fa(c0347g);
    }

    public a(AbstractC0368s abstractC0368s) {
        if (abstractC0368s.h() >= 1 && abstractC0368s.h() <= 2) {
            this.f7952a = C0364n.getInstance(abstractC0368s.a(0));
            if (abstractC0368s.h() == 2) {
                this.f7953b = abstractC0368s.a(1);
                return;
            } else {
                this.f7953b = null;
                return;
            }
        }
        throw new IllegalArgumentException("Bad sequence size: " + abstractC0368s.h());
    }
}
