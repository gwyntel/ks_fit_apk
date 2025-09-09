package c.a.a.j;

import c.a.a.AbstractC0363m;
import c.a.a.AbstractC0368s;
import c.a.a.C0347g;
import c.a.a.InterfaceC0346f;
import c.a.a.S;
import c.a.a.fa;
import c.a.a.r;
import java.util.Enumeration;

/* loaded from: classes2.dex */
public class b extends AbstractC0363m {

    /* renamed from: a, reason: collision with root package name */
    public a f7954a;

    /* renamed from: b, reason: collision with root package name */
    public S f7955b;

    public b(a aVar, InterfaceC0346f interfaceC0346f) {
        this.f7955b = new S(interfaceC0346f);
        this.f7954a = aVar;
    }

    public static b getInstance(Object obj) {
        if (obj instanceof b) {
            return (b) obj;
        }
        if (obj != null) {
            return new b(AbstractC0368s.getInstance(obj));
        }
        return null;
    }

    public S c() {
        return this.f7955b;
    }

    public a getAlgorithm() {
        return this.f7954a;
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        C0347g c0347g = new C0347g();
        c0347g.a(this.f7954a);
        c0347g.a(this.f7955b);
        return new fa(c0347g);
    }

    public b(a aVar, byte[] bArr) {
        this.f7955b = new S(bArr);
        this.f7954a = aVar;
    }

    public b(AbstractC0368s abstractC0368s) {
        if (abstractC0368s.h() == 2) {
            Enumeration enumerationG = abstractC0368s.g();
            this.f7954a = a.getInstance(enumerationG.nextElement());
            this.f7955b = S.getInstance(enumerationG.nextElement());
        } else {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0368s.h());
        }
    }
}
