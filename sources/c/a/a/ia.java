package c.a.a;

import java.io.IOException;
import org.spongycastle.asn1.ASN1ParsingException;

/* loaded from: classes2.dex */
public class ia implements InterfaceC0371v {

    /* renamed from: a, reason: collision with root package name */
    public C0372w f7948a;

    public ia(C0372w c0372w) {
        this.f7948a = c0372w;
    }

    @Override // c.a.a.va
    public r a() {
        return new ha(this.f7948a.b(), false);
    }

    @Override // c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        try {
            return a();
        } catch (IOException e2) {
            throw new ASN1ParsingException(e2.getMessage(), e2);
        }
    }
}
