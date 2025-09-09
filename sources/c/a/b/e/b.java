package c.a.b.e;

import androidx.media3.exoplayer.RendererCapabilities;
import c.a.b.d;
import c.a.b.e;
import c.a.b.k;
import c.a.d.h;

/* loaded from: classes2.dex */
public class b implements k {

    /* renamed from: a, reason: collision with root package name */
    public byte[] f8056a;

    /* renamed from: b, reason: collision with root package name */
    public byte[] f8057b;

    /* renamed from: c, reason: collision with root package name */
    public byte[] f8058c;

    /* renamed from: d, reason: collision with root package name */
    public byte[] f8059d;

    /* renamed from: e, reason: collision with root package name */
    public int f8060e;

    /* renamed from: f, reason: collision with root package name */
    public d f8061f;

    /* renamed from: g, reason: collision with root package name */
    public int f8062g;

    /* renamed from: h, reason: collision with root package name */
    public byte[] f8063h;

    /* renamed from: i, reason: collision with root package name */
    public byte[] f8064i;

    public b(d dVar) {
        this(dVar, dVar.a() * 8);
    }

    public static int a(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        int i2 = 0;
        while (true) {
            length--;
            if (length < 0) {
                return i2;
            }
            int i3 = bArr[length] & 255;
            bArr2[length] = (byte) (i2 | (i3 << 1));
            i2 = (i3 >>> 7) & 1;
        }
    }

    public void b() {
        int i2 = 0;
        while (true) {
            byte[] bArr = this.f8059d;
            if (i2 >= bArr.length) {
                this.f8060e = 0;
                this.f8061f.reset();
                return;
            } else {
                bArr[i2] = 0;
                i2++;
            }
        }
    }

    @Override // c.a.b.k
    public void init(e eVar) {
        a(eVar);
        this.f8061f.a(true, eVar);
        byte[] bArr = this.f8057b;
        byte[] bArr2 = new byte[bArr.length];
        this.f8061f.a(bArr, 0, bArr2, 0);
        byte[] bArrA = a(bArr2);
        this.f8063h = bArrA;
        this.f8064i = a(bArrA);
        b();
    }

    @Override // c.a.b.k
    public void update(byte[] bArr, int i2, int i3) {
        if (i3 < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        int iA = this.f8061f.a();
        int i4 = this.f8060e;
        int i5 = iA - i4;
        if (i3 > i5) {
            System.arraycopy(bArr, i2, this.f8059d, i4, i5);
            this.f8061f.a(this.f8059d, 0, this.f8058c, 0);
            this.f8060e = 0;
            i3 -= i5;
            i2 += i5;
            while (i3 > iA) {
                this.f8061f.a(bArr, i2, this.f8058c, 0);
                i3 -= iA;
                i2 += iA;
            }
        }
        System.arraycopy(bArr, i2, this.f8059d, this.f8060e, i3);
        this.f8060e += i3;
    }

    public b(d dVar, int i2) {
        if (i2 % 8 != 0) {
            throw new IllegalArgumentException("MAC size must be multiple of 8");
        }
        if (i2 > dVar.a() * 8) {
            throw new IllegalArgumentException("MAC size must be less or equal to " + (dVar.a() * 8));
        }
        this.f8061f = new c.a.b.f.b(dVar);
        this.f8062g = i2 / 8;
        this.f8056a = a(dVar.a());
        this.f8058c = new byte[dVar.a()];
        this.f8059d = new byte[dVar.a()];
        this.f8057b = new byte[dVar.a()];
        this.f8060e = 0;
    }

    public final byte[] a(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        int i2 = (-a(bArr, bArr2)) & 255;
        int length = bArr.length - 3;
        byte b2 = bArr2[length];
        byte[] bArr3 = this.f8056a;
        bArr2[length] = (byte) (b2 ^ (bArr3[1] & i2));
        int length2 = bArr.length - 2;
        bArr2[length2] = (byte) ((bArr3[2] & i2) ^ bArr2[length2]);
        int length3 = bArr.length - 1;
        bArr2[length3] = (byte) ((i2 & bArr3[3]) ^ bArr2[length3]);
        return bArr2;
    }

    public static byte[] a(int i2) {
        int i3;
        int i4 = i2 * 8;
        switch (i4) {
            case 64:
            case 320:
                i3 = 27;
                break;
            case 128:
            case 192:
                i3 = 135;
                break;
            case 160:
                i3 = 45;
                break;
            case 224:
                i3 = 777;
                break;
            case 256:
                i3 = 1061;
                break;
            case RendererCapabilities.DECODER_SUPPORT_MASK /* 384 */:
                i3 = 4109;
                break;
            case 448:
                i3 = 2129;
                break;
            case 512:
                i3 = 293;
                break;
            case 768:
                i3 = 655377;
                break;
            case 1024:
                i3 = 524355;
                break;
            case 2048:
                i3 = 548865;
                break;
            default:
                throw new IllegalArgumentException("Unknown block size for CMAC: " + i4);
        }
        return h.a(i3);
    }

    public void a(e eVar) {
        if (eVar != null && !(eVar instanceof c.a.b.h.k)) {
            throw new IllegalArgumentException("CMac mode only permits key to be set.");
        }
    }

    @Override // c.a.b.k
    public int a() {
        return this.f8062g;
    }

    @Override // c.a.b.k
    public void a(byte b2) {
        int i2 = this.f8060e;
        byte[] bArr = this.f8059d;
        if (i2 == bArr.length) {
            this.f8061f.a(bArr, 0, this.f8058c, 0);
            this.f8060e = 0;
        }
        byte[] bArr2 = this.f8059d;
        int i3 = this.f8060e;
        this.f8060e = i3 + 1;
        bArr2[i3] = b2;
    }

    @Override // c.a.b.k
    public int a(byte[] bArr, int i2) {
        byte[] bArr2;
        if (this.f8060e == this.f8061f.a()) {
            bArr2 = this.f8063h;
        } else {
            new c.a.b.g.b().a(this.f8059d, this.f8060e);
            bArr2 = this.f8064i;
        }
        int i3 = 0;
        while (true) {
            byte[] bArr3 = this.f8058c;
            if (i3 < bArr3.length) {
                byte[] bArr4 = this.f8059d;
                bArr4[i3] = (byte) (bArr4[i3] ^ bArr2[i3]);
                i3++;
            } else {
                this.f8061f.a(this.f8059d, 0, bArr3, 0);
                System.arraycopy(this.f8058c, 0, bArr, i2, this.f8062g);
                b();
                return this.f8062g;
            }
        }
    }
}
