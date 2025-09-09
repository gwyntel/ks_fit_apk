package c.a.b.f;

import c.a.b.e;
import c.a.b.h.l;
import c.a.b.m;
import c.a.b.n;

/* loaded from: classes2.dex */
public class d extends n implements m {

    /* renamed from: b, reason: collision with root package name */
    public final c.a.b.d f8082b;

    /* renamed from: c, reason: collision with root package name */
    public final int f8083c;

    /* renamed from: d, reason: collision with root package name */
    public byte[] f8084d;

    /* renamed from: e, reason: collision with root package name */
    public byte[] f8085e;

    /* renamed from: f, reason: collision with root package name */
    public byte[] f8086f;

    /* renamed from: g, reason: collision with root package name */
    public int f8087g;

    public d(c.a.b.d dVar) {
        super(dVar);
        this.f8082b = dVar;
        int iA = dVar.a();
        this.f8083c = iA;
        this.f8084d = new byte[iA];
        this.f8085e = new byte[iA];
        this.f8086f = new byte[iA];
        this.f8087g = 0;
    }

    @Override // c.a.b.d
    public void a(boolean z2, e eVar) {
        if (!(eVar instanceof l)) {
            throw new IllegalArgumentException("CTR/SIC mode requires ParametersWithIV");
        }
        l lVar = (l) eVar;
        byte[] bArrA = c.a.d.a.a(lVar.a());
        this.f8084d = bArrA;
        int i2 = this.f8083c;
        if (i2 < bArrA.length) {
            throw new IllegalArgumentException("CTR/SIC mode requires IV no greater than: " + this.f8083c + " bytes.");
        }
        int i3 = i2 / 2;
        if (8 <= i3) {
            i3 = 8;
        }
        if (i2 - bArrA.length <= i3) {
            if (lVar.b() != null) {
                this.f8082b.a(true, lVar.b());
            }
            reset();
        } else {
            throw new IllegalArgumentException("CTR/SIC mode requires IV of at least: " + (this.f8083c - i3) + " bytes.");
        }
    }

    public final void b() {
        if (this.f8084d.length >= this.f8083c) {
            return;
        }
        int i2 = 0;
        while (true) {
            byte[] bArr = this.f8084d;
            if (i2 == bArr.length) {
                return;
            }
            if (this.f8085e[i2] != bArr[i2]) {
                throw new IllegalStateException("Counter in CTR/SIC mode out of range.");
            }
            i2++;
        }
    }

    @Override // c.a.b.d
    public void reset() {
        c.a.d.a.a(this.f8085e, (byte) 0);
        byte[] bArr = this.f8084d;
        System.arraycopy(bArr, 0, this.f8085e, 0, bArr.length);
        this.f8082b.reset();
        this.f8087g = 0;
    }

    @Override // c.a.b.d
    public int a() {
        return this.f8082b.a();
    }

    @Override // c.a.b.d
    public int a(byte[] bArr, int i2, byte[] bArr2, int i3) {
        a(bArr, i2, this.f8083c, bArr2, i3);
        return this.f8083c;
    }

    @Override // c.a.b.n
    public byte a(byte b2) {
        int i2 = this.f8087g;
        if (i2 == 0) {
            this.f8082b.a(this.f8085e, 0, this.f8086f, 0);
            byte[] bArr = this.f8086f;
            int i3 = this.f8087g;
            this.f8087g = i3 + 1;
            return (byte) (b2 ^ bArr[i3]);
        }
        byte[] bArr2 = this.f8086f;
        int i4 = i2 + 1;
        this.f8087g = i4;
        byte b3 = (byte) (b2 ^ bArr2[i2]);
        if (i4 == this.f8085e.length) {
            this.f8087g = 0;
            a(0);
            b();
        }
        return b3;
    }

    public final void a(int i2) {
        byte b2;
        int length = this.f8085e.length - i2;
        do {
            length--;
            if (length < 0) {
                return;
            }
            byte[] bArr = this.f8085e;
            b2 = (byte) (bArr[length] + 1);
            bArr[length] = b2;
        } while (b2 == 0);
    }
}
