package c.a.a.h;

import c.a.a.AbstractC0363m;
import c.a.a.AbstractC0365o;
import c.a.a.AbstractC0368s;
import c.a.a.AbstractC0374y;
import c.a.a.C0342ba;
import c.a.a.C0347g;
import c.a.a.C0361k;
import c.a.a.InterfaceC0346f;
import c.a.a.S;
import c.a.a.fa;
import c.a.a.ka;
import java.math.BigInteger;
import java.util.Enumeration;

/* renamed from: c.a.a.h.a, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0349a extends AbstractC0363m {

    /* renamed from: a, reason: collision with root package name */
    public AbstractC0368s f7902a;

    public C0349a(AbstractC0368s abstractC0368s) {
        this.f7902a = abstractC0368s;
    }

    public static C0349a getInstance(Object obj) {
        if (obj instanceof C0349a) {
            return (C0349a) obj;
        }
        if (obj != null) {
            return new C0349a(AbstractC0368s.getInstance(obj));
        }
        return null;
    }

    public final c.a.a.r a(int i2) {
        Enumeration enumerationG = this.f7902a.g();
        while (enumerationG.hasMoreElements()) {
            InterfaceC0346f interfaceC0346f = (InterfaceC0346f) enumerationG.nextElement();
            if (interfaceC0346f instanceof AbstractC0374y) {
                AbstractC0374y abstractC0374y = (AbstractC0374y) interfaceC0346f;
                if (abstractC0374y.h() == i2) {
                    return abstractC0374y.g().toASN1Primitive();
                }
            }
        }
        return null;
    }

    public BigInteger c() {
        return new BigInteger(1, ((AbstractC0365o) this.f7902a.a(1)).g());
    }

    public S d() {
        return (S) a(1);
    }

    @Override // c.a.a.AbstractC0363m, c.a.a.InterfaceC0346f
    public c.a.a.r toASN1Primitive() {
        return this.f7902a;
    }

    public C0349a(int i2, BigInteger bigInteger, InterfaceC0346f interfaceC0346f) {
        this(i2, bigInteger, null, interfaceC0346f);
    }

    public C0349a(int i2, BigInteger bigInteger, S s2, InterfaceC0346f interfaceC0346f) {
        byte[] bArrA = c.a.d.b.a((i2 + 7) / 8, bigInteger);
        C0347g c0347g = new C0347g();
        c0347g.a(new C0361k(1L));
        c0347g.a(new C0342ba(bArrA));
        if (interfaceC0346f != null) {
            c0347g.a(new ka(true, 0, interfaceC0346f));
        }
        if (s2 != null) {
            c0347g.a(new ka(true, 1, s2));
        }
        this.f7902a = new fa(c0347g);
    }
}
