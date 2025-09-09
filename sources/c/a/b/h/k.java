package c.a.b.h;

/* loaded from: classes2.dex */
public class k implements c.a.b.e {

    /* renamed from: a, reason: collision with root package name */
    public byte[] f8106a;

    public k(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public byte[] a() {
        return this.f8106a;
    }

    public k(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        this.f8106a = bArr2;
        System.arraycopy(bArr, i2, bArr2, 0, i3);
    }
}
