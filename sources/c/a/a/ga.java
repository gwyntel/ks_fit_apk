package c.a.a;

import java.io.IOException;

/* loaded from: classes2.dex */
public class ga implements InterfaceC0369t {

    /* renamed from: a, reason: collision with root package name */
    public C0372w f7847a;

    public ga(C0372w c0372w) {
        this.f7847a = c0372w;
    }

    @Override // c.a.a.va
    public r a() {
        return new fa(this.f7847a.b());
    }

    @Override // c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        try {
            return a();
        } catch (IOException e2) {
            throw new IllegalStateException(e2.getMessage());
        }
    }
}
