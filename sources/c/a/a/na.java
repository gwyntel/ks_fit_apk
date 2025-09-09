package c.a.a;

/* loaded from: classes2.dex */
public class na extends r implements InterfaceC0373x {

    /* renamed from: a, reason: collision with root package name */
    public final byte[] f7967a;

    public na(byte[] bArr) {
        this.f7967a = c.a.d.a.a(bArr);
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(21, this.f7967a);
    }

    @Override // c.a.a.r
    public int c() {
        return Ba.a(this.f7967a.length) + 1 + this.f7967a.length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return c.a.d.a.b(this.f7967a);
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar instanceof na) {
            return c.a.d.a.a(this.f7967a, ((na) rVar).f7967a);
        }
        return false;
    }
}
