package c.a.a;

/* loaded from: classes2.dex */
public class ja extends r implements InterfaceC0373x {

    /* renamed from: a, reason: collision with root package name */
    public byte[] f7956a;

    public ja(byte[] bArr) {
        this.f7956a = c.a.d.a.a(bArr);
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(20, this.f7956a);
    }

    @Override // c.a.a.r
    public int c() {
        return Ba.a(this.f7956a.length) + 1 + this.f7956a.length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    public String g() {
        return c.a.d.l.b(this.f7956a);
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return c.a.d.a.b(this.f7956a);
    }

    public String toString() {
        return g();
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar instanceof ja) {
            return c.a.d.a.a(this.f7956a, ((ja) rVar).f7956a);
        }
        return false;
    }
}
