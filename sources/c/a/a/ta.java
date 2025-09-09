package c.a.a;

import java.io.IOException;

/* loaded from: classes2.dex */
public class ta extends AbstractC0374y {

    /* renamed from: e, reason: collision with root package name */
    public static final byte[] f7976e = new byte[0];

    public ta(boolean z2, int i2, InterfaceC0346f interfaceC0346f) {
        super(z2, i2, interfaceC0346f);
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) throws IOException {
        if (this.f7992b) {
            c0367q.a(160, this.f7991a, f7976e);
            return;
        }
        r rVarF = this.f7994d.toASN1Primitive().f();
        if (!this.f7993c) {
            c0367q.a(rVarF.d() ? 160 : 128, this.f7991a);
            c0367q.a(rVarF);
        } else {
            c0367q.a(160, this.f7991a);
            c0367q.b(rVarF.c());
            c0367q.a((InterfaceC0346f) rVarF);
        }
    }

    @Override // c.a.a.r
    public int c() {
        int iB;
        if (this.f7992b) {
            return Ba.b(this.f7991a) + 1;
        }
        int iC = this.f7994d.toASN1Primitive().f().c();
        if (this.f7993c) {
            iB = Ba.b(this.f7991a) + Ba.a(iC);
        } else {
            iC--;
            iB = Ba.b(this.f7991a);
        }
        return iB + iC;
    }

    @Override // c.a.a.r
    public boolean d() {
        if (this.f7992b || this.f7993c) {
            return true;
        }
        return this.f7994d.toASN1Primitive().f().d();
    }
}
