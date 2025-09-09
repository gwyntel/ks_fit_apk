package c.a.a;

/* renamed from: c.a.a.h, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0348h extends r {

    /* renamed from: a, reason: collision with root package name */
    public static C0348h[] f7848a = new C0348h[12];

    /* renamed from: b, reason: collision with root package name */
    public final byte[] f7849b;

    public C0348h(byte[] bArr) {
        if (!c.a.d.j.b("org.spongycastle.asn1.allow_unsafe_integer") && C0361k.b(bArr)) {
            throw new IllegalArgumentException("malformed enumerated");
        }
        this.f7849b = c.a.d.a.a(bArr);
    }

    public static C0348h b(byte[] bArr) {
        if (bArr.length > 1) {
            return new C0348h(bArr);
        }
        if (bArr.length == 0) {
            throw new IllegalArgumentException("ENUMERATED has zero length");
        }
        int i2 = bArr[0] & 255;
        C0348h[] c0348hArr = f7848a;
        if (i2 >= c0348hArr.length) {
            return new C0348h(c.a.d.a.a(bArr));
        }
        C0348h c0348h = c0348hArr[i2];
        if (c0348h != null) {
            return c0348h;
        }
        C0348h c0348h2 = new C0348h(c.a.d.a.a(bArr));
        c0348hArr[i2] = c0348h2;
        return c0348h2;
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(10, this.f7849b);
    }

    @Override // c.a.a.r
    public int c() {
        return Ba.a(this.f7849b.length) + 1 + this.f7849b.length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return c.a.d.a.b(this.f7849b);
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar instanceof C0348h) {
            return c.a.d.a.a(this.f7849b, ((C0348h) rVar).f7849b);
        }
        return false;
    }
}
