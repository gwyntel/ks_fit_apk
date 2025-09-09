package c.a.b.h;

/* loaded from: classes2.dex */
public class l implements c.a.b.e {

    /* renamed from: a, reason: collision with root package name */
    public byte[] f8107a;

    /* renamed from: b, reason: collision with root package name */
    public c.a.b.e f8108b;

    public l(c.a.b.e eVar, byte[] bArr) {
        this(eVar, bArr, 0, bArr.length);
    }

    public byte[] a() {
        return this.f8107a;
    }

    public c.a.b.e b() {
        return this.f8108b;
    }

    public l(c.a.b.e eVar, byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        this.f8107a = bArr2;
        this.f8108b = eVar;
        System.arraycopy(bArr, i2, bArr2, 0, i3);
    }
}
