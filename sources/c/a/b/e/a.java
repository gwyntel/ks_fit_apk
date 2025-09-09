package c.a.b.e;

import c.a.b.d;
import c.a.b.e;
import c.a.b.k;

/* loaded from: classes2.dex */
public class a implements k {

    /* renamed from: a, reason: collision with root package name */
    public byte[] f8050a;

    /* renamed from: b, reason: collision with root package name */
    public byte[] f8051b;

    /* renamed from: c, reason: collision with root package name */
    public int f8052c;

    /* renamed from: d, reason: collision with root package name */
    public d f8053d;

    /* renamed from: e, reason: collision with root package name */
    public c.a.b.g.a f8054e;

    /* renamed from: f, reason: collision with root package name */
    public int f8055f;

    public a(d dVar, int i2) {
        this(dVar, i2, null);
    }

    @Override // c.a.b.k
    public int a() {
        return this.f8055f;
    }

    public void b() {
        int i2 = 0;
        while (true) {
            byte[] bArr = this.f8051b;
            if (i2 >= bArr.length) {
                this.f8052c = 0;
                this.f8053d.reset();
                return;
            } else {
                bArr[i2] = 0;
                i2++;
            }
        }
    }

    @Override // c.a.b.k
    public void init(e eVar) {
        b();
        this.f8053d.a(true, eVar);
    }

    @Override // c.a.b.k
    public void update(byte[] bArr, int i2, int i3) {
        if (i3 < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        int iA = this.f8053d.a();
        int i4 = this.f8052c;
        int i5 = iA - i4;
        if (i3 > i5) {
            System.arraycopy(bArr, i2, this.f8051b, i4, i5);
            this.f8053d.a(this.f8051b, 0, this.f8050a, 0);
            this.f8052c = 0;
            i3 -= i5;
            i2 += i5;
            while (i3 > iA) {
                this.f8053d.a(bArr, i2, this.f8050a, 0);
                i3 -= iA;
                i2 += iA;
            }
        }
        System.arraycopy(bArr, i2, this.f8051b, this.f8052c, i3);
        this.f8052c += i3;
    }

    public a(d dVar, int i2, c.a.b.g.a aVar) {
        if (i2 % 8 != 0) {
            throw new IllegalArgumentException("MAC size must be multiple of 8");
        }
        this.f8053d = new c.a.b.f.b(dVar);
        this.f8054e = aVar;
        this.f8055f = i2 / 8;
        this.f8050a = new byte[dVar.a()];
        this.f8051b = new byte[dVar.a()];
        this.f8052c = 0;
    }

    @Override // c.a.b.k
    public void a(byte b2) {
        int i2 = this.f8052c;
        byte[] bArr = this.f8051b;
        if (i2 == bArr.length) {
            this.f8053d.a(bArr, 0, this.f8050a, 0);
            this.f8052c = 0;
        }
        byte[] bArr2 = this.f8051b;
        int i3 = this.f8052c;
        this.f8052c = i3 + 1;
        bArr2[i3] = b2;
    }

    @Override // c.a.b.k
    public int a(byte[] bArr, int i2) {
        int iA = this.f8053d.a();
        if (this.f8054e == null) {
            while (true) {
                int i3 = this.f8052c;
                if (i3 >= iA) {
                    break;
                }
                this.f8051b[i3] = 0;
                this.f8052c = i3 + 1;
            }
        } else {
            if (this.f8052c == iA) {
                this.f8053d.a(this.f8051b, 0, this.f8050a, 0);
                this.f8052c = 0;
            }
            this.f8054e.a(this.f8051b, this.f8052c);
        }
        this.f8053d.a(this.f8051b, 0, this.f8050a, 0);
        System.arraycopy(this.f8050a, 0, bArr, i2, this.f8055f);
        b();
        return this.f8055f;
    }
}
