package c.a.a;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class da extends C0367q {
    public da(OutputStream outputStream) {
        super(outputStream);
    }

    @Override // c.a.a.C0367q
    public C0367q a() {
        return this;
    }

    @Override // c.a.a.C0367q
    public C0367q b() {
        return this;
    }

    @Override // c.a.a.C0367q
    public void a(InterfaceC0346f interfaceC0346f) {
        if (interfaceC0346f == null) {
            throw new IOException("null object detected");
        }
        interfaceC0346f.toASN1Primitive().e().a(this);
    }
}
