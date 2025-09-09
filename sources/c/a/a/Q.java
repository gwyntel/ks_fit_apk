package c.a.a;

/* loaded from: classes2.dex */
public class Q extends r implements InterfaceC0373x {

    /* renamed from: a, reason: collision with root package name */
    public final char[] f7678a;

    public Q(char[] cArr) {
        this.f7678a = cArr;
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar instanceof Q) {
            return c.a.d.a.a(this.f7678a, ((Q) rVar).f7678a);
        }
        return false;
    }

    @Override // c.a.a.r
    public int c() {
        return Ba.a(this.f7678a.length * 2) + 1 + (this.f7678a.length * 2);
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    public String g() {
        return new String(this.f7678a);
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return c.a.d.a.a(this.f7678a);
    }

    public String toString() {
        return g();
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(30);
        c0367q.b(this.f7678a.length * 2);
        int i2 = 0;
        while (true) {
            char[] cArr = this.f7678a;
            if (i2 == cArr.length) {
                return;
            }
            char c2 = cArr[i2];
            c0367q.a((byte) (c2 >> '\b'));
            c0367q.a((byte) c2);
            i2++;
        }
    }
}
