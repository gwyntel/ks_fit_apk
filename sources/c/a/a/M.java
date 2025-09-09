package c.a.a;

import java.io.IOException;
import org.spongycastle.asn1.ASN1ParsingException;

/* loaded from: classes2.dex */
public class M implements InterfaceC0375z {

    /* renamed from: a, reason: collision with root package name */
    public boolean f7672a;

    /* renamed from: b, reason: collision with root package name */
    public int f7673b;

    /* renamed from: c, reason: collision with root package name */
    public C0372w f7674c;

    public M(boolean z2, int i2, C0372w c0372w) {
        this.f7672a = z2;
        this.f7673b = i2;
        this.f7674c = c0372w;
    }

    @Override // c.a.a.va
    public r a() {
        return this.f7674c.a(this.f7672a, this.f7673b);
    }

    @Override // c.a.a.InterfaceC0346f
    public r toASN1Primitive() {
        try {
            return a();
        } catch (IOException e2) {
            throw new ASN1ParsingException(e2.getMessage());
        }
    }
}
