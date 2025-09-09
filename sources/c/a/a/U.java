package c.a.a;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Exception;
import org.spongycastle.asn1.ASN1ParsingException;

/* loaded from: classes2.dex */
public class U implements InterfaceC0346f, va {

    /* renamed from: a, reason: collision with root package name */
    public C0372w f7684a;

    public U(C0372w c0372w) {
        this.f7684a = c0372w;
    }

    @Override // c.a.a.va
    public r a() throws ASN1Exception {
        try {
            return new T(this.f7684a.b());
        } catch (IllegalArgumentException e2) {
            throw new ASN1Exception(e2.getMessage(), e2);
        }
    }

    @Override // c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        try {
            return a();
        } catch (IOException e2) {
            throw new ASN1ParsingException("unable to get DER object", e2);
        } catch (IllegalArgumentException e3) {
            throw new ASN1ParsingException("unable to get DER object", e3);
        }
    }
}
