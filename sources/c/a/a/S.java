package c.a.a;

/* loaded from: classes2.dex */
public class S extends AbstractC0343c {
    public S(byte[] bArr, int i2) {
        super(bArr, i2);
    }

    public static S getInstance(Object obj) {
        if (obj == null || (obj instanceof S)) {
            return (S) obj;
        }
        if (obj instanceof pa) {
            pa paVar = (pa) obj;
            return new S(paVar.f7731b, paVar.f7732c);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
        try {
            return (S) r.a((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException("encoding error in getInstance: " + e2.toString());
        }
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        byte[] bArrA = AbstractC0343c.a(this.f7731b, this.f7732c);
        int length = bArrA.length;
        byte[] bArr = new byte[length + 1];
        bArr[0] = (byte) h();
        System.arraycopy(bArrA, 0, bArr, 1, length);
        c0367q.a(3, bArr);
    }

    @Override // c.a.a.r
    public int c() {
        return Ba.a(this.f7731b.length + 1) + 1 + this.f7731b.length + 1;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    public S(byte[] bArr) {
        this(bArr, 0);
    }

    public S(InterfaceC0346f interfaceC0346f) {
        super(interfaceC0346f.toASN1Primitive().getEncoded("DER"), 0);
    }
}
