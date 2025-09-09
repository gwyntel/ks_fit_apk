package c.a.a;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.asn1.ASN1ParsingException;

/* loaded from: classes2.dex */
public class ca implements InterfaceC0366p {

    /* renamed from: a, reason: collision with root package name */
    public ua f7764a;

    public ca(ua uaVar) {
        this.f7764a = uaVar;
    }

    @Override // c.a.a.va
    public r a() {
        return new C0342ba(this.f7764a.b());
    }

    @Override // c.a.a.InterfaceC0366p
    public InputStream b() {
        return this.f7764a;
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
