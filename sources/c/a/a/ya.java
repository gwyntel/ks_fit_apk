package c.a.a;

import java.util.Enumeration;

/* loaded from: classes2.dex */
public class ya extends AbstractC0368s {

    /* renamed from: b, reason: collision with root package name */
    public byte[] f7995b;

    public ya(byte[] bArr) {
        this.f7995b = bArr;
    }

    @Override // c.a.a.AbstractC0368s
    public synchronized InterfaceC0346f a(int i2) {
        try {
            if (this.f7995b != null) {
                j();
            }
        } catch (Throwable th) {
            throw th;
        }
        return super.a(i2);
    }

    @Override // c.a.a.r
    public int c() {
        byte[] bArr = this.f7995b;
        return bArr != null ? Ba.a(bArr.length) + 1 + this.f7995b.length : super.f().c();
    }

    @Override // c.a.a.AbstractC0368s, c.a.a.r
    public r e() {
        if (this.f7995b != null) {
            j();
        }
        return super.e();
    }

    @Override // c.a.a.AbstractC0368s, c.a.a.r
    public r f() {
        if (this.f7995b != null) {
            j();
        }
        return super.f();
    }

    @Override // c.a.a.AbstractC0368s
    public synchronized Enumeration g() {
        byte[] bArr = this.f7995b;
        if (bArr == null) {
            return super.g();
        }
        return new xa(bArr);
    }

    @Override // c.a.a.AbstractC0368s
    public synchronized int h() {
        try {
            if (this.f7995b != null) {
                j();
            }
        } catch (Throwable th) {
            throw th;
        }
        return super.h();
    }

    public final void j() {
        xa xaVar = new xa(this.f7995b);
        while (xaVar.hasMoreElements()) {
            this.f7974a.addElement(xaVar.nextElement());
        }
        this.f7995b = null;
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        byte[] bArr = this.f7995b;
        if (bArr != null) {
            c0367q.a(48, bArr);
        } else {
            super.f().a(c0367q);
        }
    }
}
