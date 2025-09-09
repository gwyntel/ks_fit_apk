package c.a.a;

/* loaded from: classes2.dex */
public class ea extends r implements InterfaceC0373x {

    /* renamed from: a, reason: collision with root package name */
    public final byte[] f7823a;

    public ea(byte[] bArr) {
        this.f7823a = bArr;
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(19, this.f7823a);
    }

    @Override // c.a.a.r
    public int c() {
        return Ba.a(this.f7823a.length) + 1 + this.f7823a.length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    public String g() {
        return c.a.d.l.b(this.f7823a);
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return c.a.d.a.b(this.f7823a);
    }

    public String toString() {
        return g();
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar instanceof ea) {
            return c.a.d.a.a(this.f7823a, ((ea) rVar).f7823a);
        }
        return false;
    }
}
