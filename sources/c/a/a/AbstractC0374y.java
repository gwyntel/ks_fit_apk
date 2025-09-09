package c.a.a;

/* renamed from: c.a.a.y, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public abstract class AbstractC0374y extends r implements InterfaceC0375z {

    /* renamed from: a, reason: collision with root package name */
    public int f7991a;

    /* renamed from: b, reason: collision with root package name */
    public boolean f7992b = false;

    /* renamed from: c, reason: collision with root package name */
    public boolean f7993c;

    /* renamed from: d, reason: collision with root package name */
    public InterfaceC0346f f7994d;

    public AbstractC0374y(boolean z2, int i2, InterfaceC0346f interfaceC0346f) {
        this.f7993c = true;
        this.f7994d = null;
        if (interfaceC0346f instanceof InterfaceC0345e) {
            this.f7993c = true;
        } else {
            this.f7993c = z2;
        }
        this.f7991a = i2;
        if (this.f7993c) {
            this.f7994d = interfaceC0346f;
        } else {
            boolean z3 = interfaceC0346f.toASN1Primitive() instanceof AbstractC0370u;
            this.f7994d = interfaceC0346f;
        }
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (!(rVar instanceof AbstractC0374y)) {
            return false;
        }
        AbstractC0374y abstractC0374y = (AbstractC0374y) rVar;
        if (this.f7991a != abstractC0374y.f7991a || this.f7992b != abstractC0374y.f7992b || this.f7993c != abstractC0374y.f7993c) {
            return false;
        }
        InterfaceC0346f interfaceC0346f = this.f7994d;
        return interfaceC0346f == null ? abstractC0374y.f7994d == null : interfaceC0346f.toASN1Primitive().equals(abstractC0374y.f7994d.toASN1Primitive());
    }

    @Override // c.a.a.r
    public r e() {
        return new ka(this.f7993c, this.f7991a, this.f7994d);
    }

    @Override // c.a.a.r
    public r f() {
        return new ta(this.f7993c, this.f7991a, this.f7994d);
    }

    public r g() {
        InterfaceC0346f interfaceC0346f = this.f7994d;
        if (interfaceC0346f != null) {
            return interfaceC0346f.toASN1Primitive();
        }
        return null;
    }

    public int h() {
        return this.f7991a;
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        int i2 = this.f7991a;
        InterfaceC0346f interfaceC0346f = this.f7994d;
        return interfaceC0346f != null ? i2 ^ interfaceC0346f.hashCode() : i2;
    }

    public boolean i() {
        return this.f7993c;
    }

    public String toString() {
        return "[" + this.f7991a + "]" + this.f7994d;
    }

    @Override // c.a.a.va
    public r a() {
        return toASN1Primitive();
    }
}
