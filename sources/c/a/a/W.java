package c.a.a;

/* loaded from: classes2.dex */
public class W extends r implements InterfaceC0373x {

    /* renamed from: a, reason: collision with root package name */
    public final byte[] f7687a;

    public W(byte[] bArr) {
        this.f7687a = bArr;
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(27, this.f7687a);
    }

    @Override // c.a.a.r
    public int c() {
        return Ba.a(this.f7687a.length) + 1 + this.f7687a.length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    public String g() {
        return c.a.d.l.b(this.f7687a);
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return c.a.d.a.b(this.f7687a);
    }

    public String toString() {
        return g();
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar instanceof W) {
            return c.a.d.a.a(this.f7687a, ((W) rVar).f7687a);
        }
        return false;
    }
}
