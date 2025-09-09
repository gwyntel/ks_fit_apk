package c.a.a;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

/* loaded from: classes2.dex */
public class F extends AbstractC0365o {

    /* renamed from: b, reason: collision with root package name */
    public AbstractC0365o[] f7668b;

    public F(byte[] bArr) {
        super(bArr);
    }

    @Override // c.a.a.r
    public int c() {
        Enumeration enumerationI = i();
        int iC = 0;
        while (enumerationI.hasMoreElements()) {
            iC += ((InterfaceC0346f) enumerationI.nextElement()).toASN1Primitive().c();
        }
        return iC + 4;
    }

    @Override // c.a.a.r
    public boolean d() {
        return true;
    }

    @Override // c.a.a.AbstractC0365o
    public byte[] g() {
        return this.f7968a;
    }

    public final Vector h() {
        Vector vector = new Vector();
        int i2 = 0;
        while (true) {
            byte[] bArr = this.f7968a;
            if (i2 >= bArr.length) {
                return vector;
            }
            int i3 = i2 + 1000;
            int length = (i3 > bArr.length ? bArr.length : i3) - i2;
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, i2, bArr2, 0, length);
            vector.addElement(new C0342ba(bArr2));
            i2 = i3;
        }
    }

    public Enumeration i() {
        return this.f7668b == null ? h().elements() : new E(this);
    }

    public F(AbstractC0365o[] abstractC0365oArr) {
        super(a(abstractC0365oArr));
        this.f7668b = abstractC0365oArr;
    }

    public static byte[] a(AbstractC0365o[] abstractC0365oArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i2 = 0; i2 != abstractC0365oArr.length; i2++) {
            try {
                byteArrayOutputStream.write(((C0342ba) abstractC0365oArr[i2]).g());
            } catch (IOException e2) {
                throw new IllegalArgumentException("exception converting octets " + e2.toString());
            } catch (ClassCastException unused) {
                throw new IllegalArgumentException(abstractC0365oArr[i2].getClass().getName() + " found in input should only contain DEROctetString");
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(36);
        c0367q.a(128);
        Enumeration enumerationI = i();
        while (enumerationI.hasMoreElements()) {
            c0367q.a((InterfaceC0346f) enumerationI.nextElement());
        }
        c0367q.a(0);
        c0367q.a(0);
    }
}
