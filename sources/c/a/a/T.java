package c.a.a;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* loaded from: classes2.dex */
public class T extends r {

    /* renamed from: a, reason: collision with root package name */
    public C0364n f7679a;

    /* renamed from: b, reason: collision with root package name */
    public C0361k f7680b;

    /* renamed from: c, reason: collision with root package name */
    public r f7681c;

    /* renamed from: d, reason: collision with root package name */
    public int f7682d;

    /* renamed from: e, reason: collision with root package name */
    public r f7683e;

    public T(C0347g c0347g) {
        int i2 = 0;
        r rVarA = a(c0347g, 0);
        if (rVarA instanceof C0364n) {
            this.f7679a = (C0364n) rVarA;
            rVarA = a(c0347g, 1);
            i2 = 1;
        }
        if (rVarA instanceof C0361k) {
            this.f7680b = (C0361k) rVarA;
            i2++;
            rVarA = a(c0347g, i2);
        }
        if (!(rVarA instanceof AbstractC0374y)) {
            this.f7681c = rVarA;
            i2++;
            rVarA = a(c0347g, i2);
        }
        if (c0347g.a() != i2 + 1) {
            throw new IllegalArgumentException("input vector too large");
        }
        if (!(rVarA instanceof AbstractC0374y)) {
            throw new IllegalArgumentException("No tagged object found in vector. Structure doesn't seem to be of type External");
        }
        AbstractC0374y abstractC0374y = (AbstractC0374y) rVarA;
        a(abstractC0374y.h());
        this.f7683e = abstractC0374y.g();
    }

    public final r a(C0347g c0347g, int i2) {
        if (c0347g.a() > i2) {
            return c0347g.a(i2).toASN1Primitive();
        }
        throw new IllegalArgumentException("too few objects in input vector");
    }

    @Override // c.a.a.r
    public int c() {
        return getEncoded().length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return true;
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        C0364n c0364n = this.f7679a;
        int iHashCode = c0364n != null ? c0364n.hashCode() : 0;
        C0361k c0361k = this.f7680b;
        if (c0361k != null) {
            iHashCode ^= c0361k.hashCode();
        }
        r rVar = this.f7681c;
        if (rVar != null) {
            iHashCode ^= rVar.hashCode();
        }
        return iHashCode ^ this.f7683e.hashCode();
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        C0364n c0364n = this.f7679a;
        if (c0364n != null) {
            byteArrayOutputStream.write(c0364n.getEncoded("DER"));
        }
        C0361k c0361k = this.f7680b;
        if (c0361k != null) {
            byteArrayOutputStream.write(c0361k.getEncoded("DER"));
        }
        r rVar = this.f7681c;
        if (rVar != null) {
            byteArrayOutputStream.write(rVar.getEncoded("DER"));
        }
        byteArrayOutputStream.write(new ka(true, this.f7682d, this.f7683e).getEncoded("DER"));
        c0367q.a(32, 8, byteArrayOutputStream.toByteArray());
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        r rVar2;
        C0361k c0361k;
        C0364n c0364n;
        if (!(rVar instanceof T)) {
            return false;
        }
        if (this == rVar) {
            return true;
        }
        T t2 = (T) rVar;
        C0364n c0364n2 = this.f7679a;
        if (c0364n2 != null && ((c0364n = t2.f7679a) == null || !c0364n.equals(c0364n2))) {
            return false;
        }
        C0361k c0361k2 = this.f7680b;
        if (c0361k2 != null && ((c0361k = t2.f7680b) == null || !c0361k.equals(c0361k2))) {
            return false;
        }
        r rVar3 = this.f7681c;
        if (rVar3 == null || ((rVar2 = t2.f7681c) != null && rVar2.equals(rVar3))) {
            return this.f7683e.equals(t2.f7683e);
        }
        return false;
    }

    public final void a(int i2) {
        if (i2 >= 0 && i2 <= 2) {
            this.f7682d = i2;
            return;
        }
        throw new IllegalArgumentException("invalid encoding value: " + i2);
    }
}
