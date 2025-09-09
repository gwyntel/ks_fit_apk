package c.a.a;

import java.io.IOException;
import org.spongycastle.asn1.ASN1ParsingException;

/* loaded from: classes2.dex */
public class K implements InterfaceC0371v {

    /* renamed from: a, reason: collision with root package name */
    public C0372w f7671a;

    public K(C0372w c0372w) {
        this.f7671a = c0372w;
    }

    @Override // c.a.a.va
    public r a() {
        return new J(this.f7671a.b());
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
