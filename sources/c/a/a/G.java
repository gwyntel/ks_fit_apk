package c.a.a;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.asn1.ASN1ParsingException;

/* loaded from: classes2.dex */
public class G implements InterfaceC0366p {

    /* renamed from: a, reason: collision with root package name */
    public C0372w f7669a;

    public G(C0372w c0372w) {
        this.f7669a = c0372w;
    }

    @Override // c.a.a.va
    public r a() {
        return new F(c.a.d.b.a.a(b()));
    }

    @Override // c.a.a.InterfaceC0366p
    public InputStream b() {
        return new O(this.f7669a);
    }

    @Override // c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        try {
            return a();
        } catch (IOException e2) {
            throw new ASN1ParsingException("IOException converting stream to byte array: " + e2.getMessage(), e2);
        }
    }
}
