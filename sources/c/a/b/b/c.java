package c.a.b.b;

import com.alibaba.ailabs.iot.aisbase.Constants;

/* loaded from: classes2.dex */
public class c extends b {

    /* renamed from: p, reason: collision with root package name */
    public int f8016p;

    /* renamed from: q, reason: collision with root package name */
    public long f8017q;

    /* renamed from: r, reason: collision with root package name */
    public long f8018r;

    /* renamed from: s, reason: collision with root package name */
    public long f8019s;

    /* renamed from: t, reason: collision with root package name */
    public long f8020t;

    /* renamed from: u, reason: collision with root package name */
    public long f8021u;

    /* renamed from: v, reason: collision with root package name */
    public long f8022v;

    /* renamed from: w, reason: collision with root package name */
    public long f8023w;

    /* renamed from: x, reason: collision with root package name */
    public long f8024x;

    public c(int i2) {
        if (i2 >= 512) {
            throw new IllegalArgumentException("bitLength cannot be >= 512");
        }
        if (i2 % 8 != 0) {
            throw new IllegalArgumentException("bitLength needs to be a multiple of 8");
        }
        if (i2 == 384) {
            throw new IllegalArgumentException("bitLength cannot be 384 use SHA384 instead");
        }
        int i3 = i2 / 8;
        this.f8016p = i3;
        a(i3 * 8);
        d();
    }

    public final void a(int i2) {
        this.f8006f = -3482333909917012819L;
        this.f8007g = 2216346199247487646L;
        this.f8008h = -7364697282686394994L;
        this.f8009i = 65953792586715988L;
        this.f8010j = -816286391624063116L;
        this.f8011k = 4512832404995164602L;
        this.f8012l = -5033199132376557362L;
        this.f8013m = -124578254951840548L;
        a((byte) 83);
        a((byte) 72);
        a((byte) 65);
        a((byte) 45);
        a(Constants.CMD_TYPE.CMD_SIGNATURE_RES);
        a(Constants.CMD_TYPE.CMD_STATUS_REPORT);
        a(Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO);
        a(Constants.CMD_TYPE.CMD_OTA);
        if (i2 > 100) {
            a((byte) ((i2 / 100) + 48));
            int i3 = i2 % 100;
            a((byte) ((i3 / 10) + 48));
            a((byte) ((i3 % 10) + 48));
        } else if (i2 > 10) {
            a((byte) ((i2 / 10) + 48));
            a((byte) ((i2 % 10) + 48));
        } else {
            a((byte) (i2 + 48));
        }
        b();
        this.f8017q = this.f8006f;
        this.f8018r = this.f8007g;
        this.f8019s = this.f8008h;
        this.f8020t = this.f8009i;
        this.f8021u = this.f8010j;
        this.f8022v = this.f8011k;
        this.f8023w = this.f8012l;
        this.f8024x = this.f8013m;
    }

    public int b(byte[] bArr, int i2) {
        b();
        a(this.f8006f, bArr, i2, this.f8016p);
        a(this.f8007g, bArr, i2 + 8, this.f8016p - 8);
        a(this.f8008h, bArr, i2 + 16, this.f8016p - 16);
        a(this.f8009i, bArr, i2 + 24, this.f8016p - 24);
        a(this.f8010j, bArr, i2 + 32, this.f8016p - 32);
        a(this.f8011k, bArr, i2 + 40, this.f8016p - 40);
        a(this.f8012l, bArr, i2 + 48, this.f8016p - 48);
        a(this.f8013m, bArr, i2 + 56, this.f8016p - 56);
        d();
        return this.f8016p;
    }

    @Override // c.a.b.b.b
    public void d() {
        super.d();
        this.f8006f = this.f8017q;
        this.f8007g = this.f8018r;
        this.f8008h = this.f8019s;
        this.f8009i = this.f8020t;
        this.f8010j = this.f8021u;
        this.f8011k = this.f8022v;
        this.f8012l = this.f8023w;
        this.f8013m = this.f8024x;
    }

    public int e() {
        return this.f8016p;
    }

    public static void a(long j2, byte[] bArr, int i2, int i3) {
        if (i3 > 0) {
            a((int) (j2 >>> 32), bArr, i2, i3);
            if (i3 > 4) {
                a((int) (j2 & 4294967295L), bArr, i2 + 4, i3 - 4);
            }
        }
    }

    public static void a(int i2, byte[] bArr, int i3, int i4) {
        int iMin = Math.min(4, i4);
        while (true) {
            iMin--;
            if (iMin < 0) {
                return;
            } else {
                bArr[i3 + iMin] = (byte) (i2 >>> ((3 - iMin) * 8));
            }
        }
    }
}
