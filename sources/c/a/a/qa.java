package c.a.a;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class qa extends C0367q {
    public qa(OutputStream outputStream) {
        super(outputStream);
    }

    @Override // c.a.a.C0367q
    public void a(InterfaceC0346f interfaceC0346f) {
        if (interfaceC0346f == null) {
            throw new IOException("null object detected");
        }
        interfaceC0346f.toASN1Primitive().f().a(this);
    }
}
