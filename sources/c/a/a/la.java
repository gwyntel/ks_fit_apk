package c.a.a;

/* loaded from: classes2.dex */
public class la extends r implements InterfaceC0373x {

    /* renamed from: a, reason: collision with root package name */
    public final byte[] f7959a;

    public la(byte[] bArr) {
        this.f7959a = bArr;
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar instanceof la) {
            return c.a.d.a.a(this.f7959a, ((la) rVar).f7959a);
        }
        return false;
    }

    @Override // c.a.a.r
    public int c() {
        return Ba.a(this.f7959a.length) + 1 + this.f7959a.length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    public String g() {
        return c.a.d.l.c(this.f7959a);
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return c.a.d.a.b(this.f7959a);
    }

    public String toString() {
        return g();
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(12, this.f7959a);
    }
}
