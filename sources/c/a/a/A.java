package c.a.a;

/* loaded from: classes2.dex */
public class A extends r {

    /* renamed from: a, reason: collision with root package name */
    public byte[] f7658a;

    public A(byte[] bArr) {
        this.f7658a = bArr;
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(23);
        int length = this.f7658a.length;
        c0367q.b(length);
        for (int i2 = 0; i2 != length; i2++) {
            c0367q.a(this.f7658a[i2]);
        }
    }

    @Override // c.a.a.r
    public int c() {
        int length = this.f7658a.length;
        return Ba.a(length) + 1 + length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return c.a.d.a.b(this.f7658a);
    }

    public String toString() {
        return c.a.d.l.b(this.f7658a);
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar instanceof A) {
            return c.a.d.a.a(this.f7658a, ((A) rVar).f7658a);
        }
        return false;
    }
}
