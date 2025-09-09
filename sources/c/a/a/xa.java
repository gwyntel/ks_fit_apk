package c.a.a;

import java.io.IOException;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1ParsingException;

/* loaded from: classes2.dex */
public class xa implements Enumeration {

    /* renamed from: a, reason: collision with root package name */
    public C0360j f7989a;

    /* renamed from: b, reason: collision with root package name */
    public Object f7990b = a();

    public xa(byte[] bArr) {
        this.f7989a = new C0360j(bArr, true);
    }

    public final Object a() {
        try {
            return this.f7989a.d();
        } catch (IOException e2) {
            throw new ASN1ParsingException("malformed DER construction: " + e2, e2);
        }
    }

    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        return this.f7990b != null;
    }

    @Override // java.util.Enumeration
    public Object nextElement() {
        Object obj = this.f7990b;
        this.f7990b = a();
        return obj;
    }
}
