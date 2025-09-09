package c.a.a;

import c.a.d.a;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/* renamed from: c.a.a.s, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public abstract class AbstractC0368s extends r implements c.a.d.f<InterfaceC0346f> {

    /* renamed from: a, reason: collision with root package name */
    public Vector f7974a;

    public AbstractC0368s() {
        this.f7974a = new Vector();
    }

    public static AbstractC0368s getInstance(Object obj) {
        if (obj == null || (obj instanceof AbstractC0368s)) {
            return (AbstractC0368s) obj;
        }
        if (obj instanceof InterfaceC0369t) {
            return getInstance(((InterfaceC0369t) obj).toASN1Primitive());
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(r.a((byte[]) obj));
            } catch (IOException e2) {
                throw new IllegalArgumentException("failed to construct sequence from byte[]: " + e2.getMessage());
            }
        }
        if (obj instanceof InterfaceC0346f) {
            r aSN1Primitive = ((InterfaceC0346f) obj).toASN1Primitive();
            if (aSN1Primitive instanceof AbstractC0368s) {
                return (AbstractC0368s) aSN1Primitive;
            }
        }
        throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
    }

    public InterfaceC0346f a(int i2) {
        return (InterfaceC0346f) this.f7974a.elementAt(i2);
    }

    @Override // c.a.a.r
    public boolean d() {
        return true;
    }

    @Override // c.a.a.r
    public r e() {
        fa faVar = new fa();
        faVar.f7974a = this.f7974a;
        return faVar;
    }

    @Override // c.a.a.r
    public r f() {
        ra raVar = new ra();
        raVar.f7974a = this.f7974a;
        return raVar;
    }

    public Enumeration g() {
        return this.f7974a.elements();
    }

    public final InterfaceC0346f getNext(Enumeration enumeration) {
        return (InterfaceC0346f) enumeration.nextElement();
    }

    public int h() {
        return this.f7974a.size();
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        Enumeration enumerationG = g();
        int iH = h();
        while (enumerationG.hasMoreElements()) {
            iH = (iH * 17) ^ getNext(enumerationG).hashCode();
        }
        return iH;
    }

    public InterfaceC0346f[] i() {
        InterfaceC0346f[] interfaceC0346fArr = new InterfaceC0346f[h()];
        for (int i2 = 0; i2 != h(); i2++) {
            interfaceC0346fArr[i2] = a(i2);
        }
        return interfaceC0346fArr;
    }

    @Override // java.lang.Iterable
    public Iterator<InterfaceC0346f> iterator() {
        return new a.C0020a(i());
    }

    public String toString() {
        return this.f7974a.toString();
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (!(rVar instanceof AbstractC0368s)) {
            return false;
        }
        AbstractC0368s abstractC0368s = (AbstractC0368s) rVar;
        if (h() != abstractC0368s.h()) {
            return false;
        }
        Enumeration enumerationG = g();
        Enumeration enumerationG2 = abstractC0368s.g();
        while (enumerationG.hasMoreElements()) {
            InterfaceC0346f next = getNext(enumerationG);
            InterfaceC0346f next2 = getNext(enumerationG2);
            r aSN1Primitive = next.toASN1Primitive();
            r aSN1Primitive2 = next2.toASN1Primitive();
            if (aSN1Primitive != aSN1Primitive2 && !aSN1Primitive.equals(aSN1Primitive2)) {
                return false;
            }
        }
        return true;
    }

    public AbstractC0368s(InterfaceC0346f interfaceC0346f) {
        Vector vector = new Vector();
        this.f7974a = vector;
        vector.addElement(interfaceC0346f);
    }

    public AbstractC0368s(C0347g c0347g) {
        this.f7974a = new Vector();
        for (int i2 = 0; i2 != c0347g.a(); i2++) {
            this.f7974a.addElement(c0347g.a(i2));
        }
    }

    public static AbstractC0368s getInstance(AbstractC0374y abstractC0374y, boolean z2) {
        if (z2) {
            if (abstractC0374y.i()) {
                return getInstance(abstractC0374y.g().toASN1Primitive());
            }
            throw new IllegalArgumentException("object implicit - explicit expected.");
        }
        if (abstractC0374y.i()) {
            if (abstractC0374y instanceof L) {
                return new H(abstractC0374y.g());
            }
            return new ra(abstractC0374y.g());
        }
        if (abstractC0374y.g() instanceof AbstractC0368s) {
            return (AbstractC0368s) abstractC0374y.g();
        }
        throw new IllegalArgumentException("unknown object in getInstance: " + abstractC0374y.getClass().getName());
    }
}
