package c.a.a;

import java.io.IOException;

/* renamed from: c.a.a.a, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public abstract class AbstractC0339a extends r {

    /* renamed from: a, reason: collision with root package name */
    public final boolean f7692a;

    /* renamed from: b, reason: collision with root package name */
    public final int f7693b;

    /* renamed from: c, reason: collision with root package name */
    public final byte[] f7694c;

    public AbstractC0339a(boolean z2, int i2, byte[] bArr) {
        this.f7692a = z2;
        this.f7693b = i2;
        this.f7694c = c.a.d.a.a(bArr);
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) throws IOException {
        c0367q.a(this.f7692a ? 96 : 64, this.f7693b, this.f7694c);
    }

    @Override // c.a.a.r
    public int c() {
        return Ba.b(this.f7693b) + Ba.a(this.f7694c.length) + this.f7694c.length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return this.f7692a;
    }

    public int g() {
        return this.f7693b;
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        boolean z2 = this.f7692a;
        return ((z2 ? 1 : 0) ^ this.f7693b) ^ c.a.d.a.b(this.f7694c);
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (!(rVar instanceof AbstractC0339a)) {
            return false;
        }
        AbstractC0339a abstractC0339a = (AbstractC0339a) rVar;
        return this.f7692a == abstractC0339a.f7692a && this.f7693b == abstractC0339a.f7693b && c.a.d.a.a(this.f7694c, abstractC0339a.f7694c);
    }
}
