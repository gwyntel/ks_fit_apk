package c.a.a;

/* renamed from: c.a.a.aa, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0340aa extends r implements InterfaceC0373x {

    /* renamed from: a, reason: collision with root package name */
    public final byte[] f7700a;

    public C0340aa(byte[] bArr) {
        this.f7700a = bArr;
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(18, this.f7700a);
    }

    @Override // c.a.a.r
    public int c() {
        return Ba.a(this.f7700a.length) + 1 + this.f7700a.length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    public String g() {
        return c.a.d.l.b(this.f7700a);
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return c.a.d.a.b(this.f7700a);
    }

    public String toString() {
        return g();
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar instanceof C0340aa) {
            return c.a.d.a.a(this.f7700a, ((C0340aa) rVar).f7700a);
        }
        return false;
    }
}
