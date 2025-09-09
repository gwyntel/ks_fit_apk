package c.a.a;

import c.a.d.a;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/* renamed from: c.a.a.u, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public abstract class AbstractC0370u extends r implements c.a.d.f<InterfaceC0346f> {

    /* renamed from: a, reason: collision with root package name */
    public Vector f7977a;

    /* renamed from: b, reason: collision with root package name */
    public boolean f7978b;

    public AbstractC0370u() {
        this.f7977a = new Vector();
        this.f7978b = false;
    }

    public static AbstractC0370u getInstance(AbstractC0374y abstractC0374y, boolean z2) {
        if (z2) {
            if (abstractC0374y.i()) {
                return (AbstractC0370u) abstractC0374y.g();
            }
            throw new IllegalArgumentException("object implicit - explicit expected.");
        }
        if (abstractC0374y.i()) {
            return abstractC0374y instanceof L ? new J(abstractC0374y.g()) : new sa(abstractC0374y.g());
        }
        if (abstractC0374y.g() instanceof AbstractC0370u) {
            return (AbstractC0370u) abstractC0374y.g();
        }
        if (abstractC0374y.g() instanceof AbstractC0368s) {
            AbstractC0368s abstractC0368s = (AbstractC0368s) abstractC0374y.g();
            return abstractC0374y instanceof L ? new J(abstractC0368s.i()) : new sa(abstractC0368s.i());
        }
        throw new IllegalArgumentException("unknown object in getInstance: " + abstractC0374y.getClass().getName());
    }

    public InterfaceC0346f a(int i2) {
        return (InterfaceC0346f) this.f7977a.elementAt(i2);
    }

    @Override // c.a.a.r
    public boolean d() {
        return true;
    }

    @Override // c.a.a.r
    public r e() {
        if (this.f7978b) {
            ha haVar = new ha();
            haVar.f7977a = this.f7977a;
            return haVar;
        }
        Vector vector = new Vector();
        for (int i2 = 0; i2 != this.f7977a.size(); i2++) {
            vector.addElement(this.f7977a.elementAt(i2));
        }
        ha haVar2 = new ha();
        haVar2.f7977a = vector;
        haVar2.i();
        return haVar2;
    }

    @Override // c.a.a.r
    public r f() {
        sa saVar = new sa();
        saVar.f7977a = this.f7977a;
        return saVar;
    }

    public Enumeration g() {
        return this.f7977a.elements();
    }

    public final InterfaceC0346f getNext(Enumeration enumeration) {
        InterfaceC0346f interfaceC0346f = (InterfaceC0346f) enumeration.nextElement();
        return interfaceC0346f == null ? Z.f7690a : interfaceC0346f;
    }

    public int h() {
        return this.f7977a.size();
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

    public void i() {
        if (this.f7978b) {
            return;
        }
        this.f7978b = true;
        if (this.f7977a.size() > 1) {
            int size = this.f7977a.size() - 1;
            boolean z2 = true;
            while (z2) {
                int i2 = 0;
                byte[] bArrA = a((InterfaceC0346f) this.f7977a.elementAt(0));
                z2 = false;
                int i3 = 0;
                while (i3 != size) {
                    int i4 = i3 + 1;
                    byte[] bArrA2 = a((InterfaceC0346f) this.f7977a.elementAt(i4));
                    if (a(bArrA, bArrA2)) {
                        bArrA = bArrA2;
                    } else {
                        Object objElementAt = this.f7977a.elementAt(i3);
                        Vector vector = this.f7977a;
                        vector.setElementAt(vector.elementAt(i4), i3);
                        this.f7977a.setElementAt(objElementAt, i4);
                        z2 = true;
                        i2 = i3;
                    }
                    i3 = i4;
                }
                size = i2;
            }
        }
    }

    @Override // java.lang.Iterable
    public Iterator<InterfaceC0346f> iterator() {
        return new a.C0020a(j());
    }

    public InterfaceC0346f[] j() {
        InterfaceC0346f[] interfaceC0346fArr = new InterfaceC0346f[h()];
        for (int i2 = 0; i2 != h(); i2++) {
            interfaceC0346fArr[i2] = a(i2);
        }
        return interfaceC0346fArr;
    }

    public String toString() {
        return this.f7977a.toString();
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (!(rVar instanceof AbstractC0370u)) {
            return false;
        }
        AbstractC0370u abstractC0370u = (AbstractC0370u) rVar;
        if (h() != abstractC0370u.h()) {
            return false;
        }
        Enumeration enumerationG = g();
        Enumeration enumerationG2 = abstractC0370u.g();
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

    public AbstractC0370u(InterfaceC0346f interfaceC0346f) {
        Vector vector = new Vector();
        this.f7977a = vector;
        this.f7978b = false;
        vector.addElement(interfaceC0346f);
    }

    public AbstractC0370u(C0347g c0347g, boolean z2) {
        this.f7977a = new Vector();
        this.f7978b = false;
        for (int i2 = 0; i2 != c0347g.a(); i2++) {
            this.f7977a.addElement(c0347g.a(i2));
        }
        if (z2) {
            i();
        }
    }

    public final boolean a(byte[] bArr, byte[] bArr2) {
        int iMin = Math.min(bArr.length, bArr2.length);
        for (int i2 = 0; i2 != iMin; i2++) {
            byte b2 = bArr[i2];
            byte b3 = bArr2[i2];
            if (b2 != b3) {
                return (b2 & 255) < (b3 & 255);
            }
        }
        return iMin == bArr.length;
    }

    public AbstractC0370u(InterfaceC0346f[] interfaceC0346fArr, boolean z2) {
        this.f7977a = new Vector();
        this.f7978b = false;
        for (int i2 = 0; i2 != interfaceC0346fArr.length; i2++) {
            this.f7977a.addElement(interfaceC0346fArr[i2]);
        }
        if (z2) {
            i();
        }
    }

    public final byte[] a(InterfaceC0346f interfaceC0346f) {
        try {
            return interfaceC0346f.toASN1Primitive().getEncoded("DER");
        } catch (IOException unused) {
            throw new IllegalArgumentException("cannot encode object added to SET");
        }
    }
}
