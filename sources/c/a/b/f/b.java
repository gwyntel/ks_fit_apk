package c.a.b.f;

import c.a.b.e;
import c.a.b.h.l;
import org.spongycastle.crypto.DataLengthException;

/* loaded from: classes2.dex */
public class b implements c.a.b.d {

    /* renamed from: a, reason: collision with root package name */
    public byte[] f8065a;

    /* renamed from: b, reason: collision with root package name */
    public byte[] f8066b;

    /* renamed from: c, reason: collision with root package name */
    public byte[] f8067c;

    /* renamed from: d, reason: collision with root package name */
    public int f8068d;

    /* renamed from: e, reason: collision with root package name */
    public c.a.b.d f8069e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f8070f;

    public b(c.a.b.d dVar) {
        this.f8069e = dVar;
        int iA = dVar.a();
        this.f8068d = iA;
        this.f8065a = new byte[iA];
        this.f8066b = new byte[iA];
        this.f8067c = new byte[iA];
    }

    @Override // c.a.b.d
    public void a(boolean z2, e eVar) {
        boolean z3 = this.f8070f;
        this.f8070f = z2;
        if (!(eVar instanceof l)) {
            reset();
            if (eVar != null) {
                this.f8069e.a(z2, eVar);
                return;
            } else {
                if (z3 != z2) {
                    throw new IllegalArgumentException("cannot change encrypting state without providing key.");
                }
                return;
            }
        }
        l lVar = (l) eVar;
        byte[] bArrA = lVar.a();
        if (bArrA.length != this.f8068d) {
            throw new IllegalArgumentException("initialisation vector must be the same length as block size");
        }
        System.arraycopy(bArrA, 0, this.f8065a, 0, bArrA.length);
        reset();
        if (lVar.b() != null) {
            this.f8069e.a(z2, lVar.b());
        } else if (z3 != z2) {
            throw new IllegalArgumentException("cannot change encrypting state without providing key.");
        }
    }

    public final int b(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int i4 = this.f8068d;
        if (i2 + i4 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        System.arraycopy(bArr, i2, this.f8067c, 0, i4);
        int iA = this.f8069e.a(bArr, i2, bArr2, i3);
        for (int i5 = 0; i5 < this.f8068d; i5++) {
            int i6 = i3 + i5;
            bArr2[i6] = (byte) (bArr2[i6] ^ this.f8066b[i5]);
        }
        byte[] bArr3 = this.f8066b;
        this.f8066b = this.f8067c;
        this.f8067c = bArr3;
        return iA;
    }

    public final int c(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.f8068d + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        for (int i4 = 0; i4 < this.f8068d; i4++) {
            byte[] bArr3 = this.f8066b;
            bArr3[i4] = (byte) (bArr3[i4] ^ bArr[i2 + i4]);
        }
        int iA = this.f8069e.a(this.f8066b, 0, bArr2, i3);
        byte[] bArr4 = this.f8066b;
        System.arraycopy(bArr2, i3, bArr4, 0, bArr4.length);
        return iA;
    }

    @Override // c.a.b.d
    public void reset() {
        byte[] bArr = this.f8065a;
        System.arraycopy(bArr, 0, this.f8066b, 0, bArr.length);
        c.a.d.a.a(this.f8067c, (byte) 0);
        this.f8069e.reset();
    }

    @Override // c.a.b.d
    public int a() {
        return this.f8069e.a();
    }

    @Override // c.a.b.d
    public int a(byte[] bArr, int i2, byte[] bArr2, int i3) {
        return this.f8070f ? c(bArr, i2, bArr2, i3) : b(bArr, i2, bArr2, i3);
    }
}
