package c.a.a;

import java.io.IOException;
import org.spongycastle.asn1.ASN1ParsingException;

/* loaded from: classes2.dex */
public class C implements InterfaceC0341b {

    /* renamed from: a, reason: collision with root package name */
    public final int f7662a;

    /* renamed from: b, reason: collision with root package name */
    public final C0372w f7663b;

    public C(int i2, C0372w c0372w) {
        this.f7662a = i2;
        this.f7663b = c0372w;
    }

    @Override // c.a.a.va
    public r a() {
        return new B(this.f7662a, this.f7663b.b());
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
