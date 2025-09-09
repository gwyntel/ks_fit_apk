package c.a.a;

/* loaded from: classes2.dex */
public class pa extends AbstractC0343c {
    public pa(byte[] bArr, int i2) {
        super(bArr, i2);
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        byte[] bArr = this.f7731b;
        int length = bArr.length;
        byte[] bArr2 = new byte[length + 1];
        bArr2[0] = (byte) h();
        System.arraycopy(bArr, 0, bArr2, 1, length);
        c0367q.a(3, bArr2);
    }

    @Override // c.a.a.r
    public int c() {
        return Ba.a(this.f7731b.length + 1) + 1 + this.f7731b.length + 1;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }
}
