package c.a.a;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.spongycastle.asn1.ASN1ParsingException;

/* loaded from: classes2.dex */
public class B extends AbstractC0339a {
    public B(int i2, C0347g c0347g) {
        super(true, i2, a(c0347g));
    }

    public static byte[] a(C0347g c0347g) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i2 = 0; i2 != c0347g.a(); i2++) {
            try {
                byteArrayOutputStream.write(((AbstractC0363m) c0347g.a(i2)).getEncoded("BER"));
            } catch (IOException e2) {
                throw new ASN1ParsingException("malformed object: " + e2, e2);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override // c.a.a.AbstractC0339a, c.a.a.r
    public void a(C0367q c0367q) throws IOException {
        c0367q.a(this.f7692a ? 96 : 64, this.f7693b);
        c0367q.a(128);
        c0367q.a(this.f7694c);
        c0367q.a(0);
        c0367q.a(0);
    }
}
