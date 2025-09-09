package c.a.b.f;

import c.a.b.e;
import c.a.b.h.l;
import java.io.ByteArrayOutputStream;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.OutputLengthException;

/* loaded from: classes2.dex */
public class c implements c.a.b.f.a {

    /* renamed from: a, reason: collision with root package name */
    public c.a.b.d f8071a;

    /* renamed from: b, reason: collision with root package name */
    public int f8072b;

    /* renamed from: c, reason: collision with root package name */
    public boolean f8073c;

    /* renamed from: d, reason: collision with root package name */
    public byte[] f8074d;

    /* renamed from: e, reason: collision with root package name */
    public byte[] f8075e;

    /* renamed from: f, reason: collision with root package name */
    public int f8076f;

    /* renamed from: g, reason: collision with root package name */
    public e f8077g;

    /* renamed from: h, reason: collision with root package name */
    public byte[] f8078h;

    /* renamed from: i, reason: collision with root package name */
    public a f8079i = new a();

    /* renamed from: j, reason: collision with root package name */
    public a f8080j = new a();

    private class a extends ByteArrayOutputStream {
        public a() {
        }

        public byte[] a() {
            return ((ByteArrayOutputStream) this).buf;
        }
    }

    public c(c.a.b.d dVar) {
        this.f8071a = dVar;
        int iA = dVar.a();
        this.f8072b = iA;
        this.f8078h = new byte[iA];
        if (iA != 16) {
            throw new IllegalArgumentException("cipher required with a block size of 16.");
        }
    }

    public void a(boolean z2, e eVar) {
        e eVarB;
        this.f8073c = z2;
        if (eVar instanceof c.a.b.h.a) {
            c.a.b.h.a aVar = (c.a.b.h.a) eVar;
            this.f8074d = aVar.d();
            this.f8075e = aVar.a();
            this.f8076f = aVar.c() / 8;
            eVarB = aVar.b();
        } else {
            if (!(eVar instanceof l)) {
                throw new IllegalArgumentException("invalid parameters passed to CCM: " + eVar.getClass().getName());
            }
            l lVar = (l) eVar;
            this.f8074d = lVar.a();
            this.f8075e = null;
            this.f8076f = this.f8078h.length / 2;
            eVarB = lVar.b();
        }
        if (eVarB != null) {
            this.f8077g = eVarB;
        }
        byte[] bArr = this.f8074d;
        if (bArr == null || bArr.length < 7 || bArr.length > 13) {
            throw new IllegalArgumentException("nonce must have length from 7 to 13 octets");
        }
        c();
    }

    public int b(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws InvalidCipherTextException {
        int i5;
        if (this.f8077g == null) {
            throw new IllegalStateException("CCM cipher unitialized.");
        }
        byte[] bArr3 = this.f8074d;
        int length = bArr3.length;
        int i6 = 15 - length;
        if (i6 < 4 && i3 >= (1 << (i6 * 8))) {
            throw new IllegalStateException("CCM packet too large for choice of q.");
        }
        byte[] bArr4 = new byte[this.f8072b];
        bArr4[0] = (byte) ((14 - length) & 7);
        System.arraycopy(bArr3, 0, bArr4, 1, bArr3.length);
        d dVar = new d(this.f8071a);
        dVar.a(this.f8073c, new l(this.f8077g, bArr4));
        if (!this.f8073c) {
            int i7 = this.f8076f;
            if (i3 < i7) {
                throw new InvalidCipherTextException("data too short");
            }
            int i8 = i3 - i7;
            if (bArr2.length < i8 + i4) {
                throw new OutputLengthException("Output buffer too short.");
            }
            int i9 = i2 + i8;
            System.arraycopy(bArr, i9, this.f8078h, 0, i7);
            byte[] bArr5 = this.f8078h;
            dVar.a(bArr5, 0, bArr5, 0);
            int i10 = this.f8076f;
            while (true) {
                byte[] bArr6 = this.f8078h;
                if (i10 == bArr6.length) {
                    break;
                }
                bArr6[i10] = 0;
                i10++;
            }
            int i11 = i2;
            int i12 = i4;
            while (true) {
                i5 = this.f8072b;
                if (i11 >= i9 - i5) {
                    break;
                }
                dVar.a(bArr, i11, bArr2, i12);
                int i13 = this.f8072b;
                i12 += i13;
                i11 += i13;
            }
            byte[] bArr7 = new byte[i5];
            int i14 = i8 - (i11 - i2);
            System.arraycopy(bArr, i11, bArr7, 0, i14);
            dVar.a(bArr7, 0, bArr7, 0);
            System.arraycopy(bArr7, 0, bArr2, i12, i14);
            byte[] bArr8 = new byte[this.f8072b];
            a(bArr2, i4, i8, bArr8);
            if (c.a.d.a.c(this.f8078h, bArr8)) {
                return i8;
            }
            throw new InvalidCipherTextException("mac check in CCM failed");
        }
        int i15 = this.f8076f + i3;
        if (bArr2.length < i15 + i4) {
            throw new OutputLengthException("Output buffer too short.");
        }
        a(bArr, i2, i3, this.f8078h);
        byte[] bArr9 = new byte[this.f8072b];
        dVar.a(this.f8078h, 0, bArr9, 0);
        int i16 = i2;
        int i17 = i4;
        while (true) {
            int i18 = i2 + i3;
            int i19 = this.f8072b;
            if (i16 >= i18 - i19) {
                byte[] bArr10 = new byte[i19];
                int i20 = i18 - i16;
                System.arraycopy(bArr, i16, bArr10, 0, i20);
                dVar.a(bArr10, 0, bArr10, 0);
                System.arraycopy(bArr10, 0, bArr2, i17, i20);
                System.arraycopy(bArr9, 0, bArr2, i4 + i3, this.f8076f);
                return i15;
            }
            dVar.a(bArr, i16, bArr2, i17);
            int i21 = this.f8072b;
            i17 += i21;
            i16 += i21;
        }
    }

    public void c() {
        this.f8071a.reset();
        this.f8079i.reset();
        this.f8080j.reset();
    }

    public int a(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (bArr.length >= i2 + i3) {
            this.f8080j.write(bArr, i2, i3);
            return 0;
        }
        throw new DataLengthException("Input buffer too short");
    }

    public int a(byte[] bArr, int i2) {
        int iB = b(this.f8080j.a(), 0, this.f8080j.size(), bArr, i2);
        c();
        return iB;
    }

    public final int a(byte[] bArr, int i2, int i3, byte[] bArr2) {
        c.a.b.e.a aVar = new c.a.b.e.a(this.f8071a, this.f8076f * 8);
        aVar.init(this.f8077g);
        byte[] bArr3 = new byte[16];
        if (b()) {
            bArr3[0] = (byte) (bArr3[0] | 64);
        }
        int i4 = 2;
        byte bA = (byte) (bArr3[0] | ((((aVar.a() - 2) / 2) & 7) << 3));
        bArr3[0] = bA;
        byte[] bArr4 = this.f8074d;
        bArr3[0] = (byte) (bA | ((14 - bArr4.length) & 7));
        System.arraycopy(bArr4, 0, bArr3, 1, bArr4.length);
        int i5 = i3;
        int i6 = 1;
        while (i5 > 0) {
            bArr3[16 - i6] = (byte) (i5 & 255);
            i5 >>>= 8;
            i6++;
        }
        aVar.update(bArr3, 0, 16);
        if (b()) {
            int iA = a();
            if (iA < 65280) {
                aVar.a((byte) (iA >> 8));
                aVar.a((byte) iA);
            } else {
                aVar.a((byte) -1);
                aVar.a((byte) -2);
                aVar.a((byte) (iA >> 24));
                aVar.a((byte) (iA >> 16));
                aVar.a((byte) (iA >> 8));
                aVar.a((byte) iA);
                i4 = 6;
            }
            byte[] bArr5 = this.f8075e;
            if (bArr5 != null) {
                aVar.update(bArr5, 0, bArr5.length);
            }
            if (this.f8079i.size() > 0) {
                aVar.update(this.f8079i.a(), 0, this.f8079i.size());
            }
            int i7 = (i4 + iA) % 16;
            if (i7 != 0) {
                while (i7 != 16) {
                    aVar.a((byte) 0);
                    i7++;
                }
            }
        }
        aVar.update(bArr, i2, i3);
        return aVar.a(bArr2, 0);
    }

    public final boolean b() {
        return a() > 0;
    }

    public final int a() {
        int size = this.f8079i.size();
        byte[] bArr = this.f8075e;
        return size + (bArr == null ? 0 : bArr.length);
    }
}
