package c.a.a;

/* loaded from: classes2.dex */
public class X extends r implements InterfaceC0373x {

    /* renamed from: a, reason: collision with root package name */
    public final byte[] f7688a;

    public X(byte[] bArr) {
        this.f7688a = c.a.d.a.a(bArr);
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(25, this.f7688a);
    }

    @Override // c.a.a.r
    public int c() {
        return Ba.a(this.f7688a.length) + 1 + this.f7688a.length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return c.a.d.a.b(this.f7688a);
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar instanceof X) {
            return c.a.d.a.a(this.f7688a, ((X) rVar).f7688a);
        }
        return false;
    }
}
