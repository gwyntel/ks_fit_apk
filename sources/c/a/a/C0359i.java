package c.a.a;

/* renamed from: c.a.a.i, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0359i extends r {

    /* renamed from: a, reason: collision with root package name */
    public byte[] f7904a;

    public C0359i(byte[] bArr) {
        this.f7904a = bArr;
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(24, this.f7904a);
    }

    @Override // c.a.a.r
    public int c() {
        int length = this.f7904a.length;
        return Ba.a(length) + 1 + length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return c.a.d.a.b(this.f7904a);
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar instanceof C0359i) {
            return c.a.d.a.a(this.f7904a, ((C0359i) rVar).f7904a);
        }
        return false;
    }
}
