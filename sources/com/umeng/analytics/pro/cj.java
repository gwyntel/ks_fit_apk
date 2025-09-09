package com.umeng.analytics.pro;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class cj extends cp {

    /* renamed from: d, reason: collision with root package name */
    private static final cu f21588d = new cu("");

    /* renamed from: e, reason: collision with root package name */
    private static final ck f21589e = new ck("", (byte) 0, 0);

    /* renamed from: f, reason: collision with root package name */
    private static final byte[] f21590f = {0, 0, 1, 3, 7, 0, 4, 0, 5, 0, 6, 8, 12, 11, 10, 9};

    /* renamed from: h, reason: collision with root package name */
    private static final byte f21591h = -126;

    /* renamed from: i, reason: collision with root package name */
    private static final byte f21592i = 1;

    /* renamed from: j, reason: collision with root package name */
    private static final byte f21593j = 31;

    /* renamed from: k, reason: collision with root package name */
    private static final byte f21594k = -32;

    /* renamed from: l, reason: collision with root package name */
    private static final int f21595l = 5;

    /* renamed from: a, reason: collision with root package name */
    byte[] f21596a;

    /* renamed from: b, reason: collision with root package name */
    byte[] f21597b;

    /* renamed from: c, reason: collision with root package name */
    byte[] f21598c;

    /* renamed from: m, reason: collision with root package name */
    private bo f21599m;

    /* renamed from: n, reason: collision with root package name */
    private short f21600n;

    /* renamed from: o, reason: collision with root package name */
    private ck f21601o;

    /* renamed from: p, reason: collision with root package name */
    private Boolean f21602p;

    /* renamed from: q, reason: collision with root package name */
    private final long f21603q;

    /* renamed from: r, reason: collision with root package name */
    private byte[] f21604r;

    private static class b {

        /* renamed from: a, reason: collision with root package name */
        public static final byte f21606a = 1;

        /* renamed from: b, reason: collision with root package name */
        public static final byte f21607b = 2;

        /* renamed from: c, reason: collision with root package name */
        public static final byte f21608c = 3;

        /* renamed from: d, reason: collision with root package name */
        public static final byte f21609d = 4;

        /* renamed from: e, reason: collision with root package name */
        public static final byte f21610e = 5;

        /* renamed from: f, reason: collision with root package name */
        public static final byte f21611f = 6;

        /* renamed from: g, reason: collision with root package name */
        public static final byte f21612g = 7;

        /* renamed from: h, reason: collision with root package name */
        public static final byte f21613h = 8;

        /* renamed from: i, reason: collision with root package name */
        public static final byte f21614i = 9;

        /* renamed from: j, reason: collision with root package name */
        public static final byte f21615j = 10;

        /* renamed from: k, reason: collision with root package name */
        public static final byte f21616k = 11;

        /* renamed from: l, reason: collision with root package name */
        public static final byte f21617l = 12;

        private b() {
        }
    }

    public cj(dd ddVar, long j2) {
        super(ddVar);
        this.f21599m = new bo(15);
        this.f21600n = (short) 0;
        this.f21601o = null;
        this.f21602p = null;
        this.f21596a = new byte[5];
        this.f21597b = new byte[10];
        this.f21604r = new byte[1];
        this.f21598c = new byte[1];
        this.f21603q = j2;
    }

    private int E() throws bw {
        int i2 = 0;
        if (this.f21633g.h() >= 5) {
            byte[] bArrF = this.f21633g.f();
            int iG = this.f21633g.g();
            int i3 = 0;
            int i4 = 0;
            while (true) {
                byte b2 = bArrF[iG + i2];
                i3 |= (b2 & Byte.MAX_VALUE) << i4;
                if ((b2 & 128) != 128) {
                    this.f21633g.a(i2 + 1);
                    return i3;
                }
                i4 += 7;
                i2++;
            }
        } else {
            int i5 = 0;
            while (true) {
                byte bU = u();
                i2 |= (bU & Byte.MAX_VALUE) << i5;
                if ((bU & 128) != 128) {
                    return i2;
                }
                i5 += 7;
            }
        }
    }

    private long F() throws bw {
        int i2 = 0;
        long j2 = 0;
        if (this.f21633g.h() >= 10) {
            byte[] bArrF = this.f21633g.f();
            int iG = this.f21633g.g();
            long j3 = 0;
            int i3 = 0;
            while (true) {
                j3 |= (r7 & Byte.MAX_VALUE) << i3;
                if ((bArrF[iG + i2] & 128) != 128) {
                    this.f21633g.a(i2 + 1);
                    return j3;
                }
                i3 += 7;
                i2++;
            }
        } else {
            while (true) {
                j2 |= (r0 & Byte.MAX_VALUE) << i2;
                if ((u() & 128) != 128) {
                    return j2;
                }
                i2 += 7;
            }
        }
    }

    private int c(int i2) {
        return (i2 >> 31) ^ (i2 << 1);
    }

    private long d(long j2) {
        return (-(j2 & 1)) ^ (j2 >>> 1);
    }

    private int g(int i2) {
        return (-(i2 & 1)) ^ (i2 >>> 1);
    }

    @Override // com.umeng.analytics.pro.cp
    public ByteBuffer A() throws bw {
        int iE = E();
        f(iE);
        if (iE == 0) {
            return ByteBuffer.wrap(new byte[0]);
        }
        byte[] bArr = new byte[iE];
        this.f21633g.d(bArr, 0, iE);
        return ByteBuffer.wrap(bArr);
    }

    @Override // com.umeng.analytics.pro.cp
    public void B() {
        this.f21599m.c();
        this.f21600n = (short) 0;
    }

    @Override // com.umeng.analytics.pro.cp
    public void a() throws bw {
    }

    @Override // com.umeng.analytics.pro.cp
    public void b() throws bw {
        this.f21600n = this.f21599m.a();
    }

    @Override // com.umeng.analytics.pro.cp
    public void e() throws bw {
    }

    @Override // com.umeng.analytics.pro.cp
    public void f() throws bw {
    }

    @Override // com.umeng.analytics.pro.cp
    public cn h() throws bw {
        byte bU = u();
        if (bU != -126) {
            throw new cq("Expected protocol id " + Integer.toHexString(-126) + " but got " + Integer.toHexString(bU));
        }
        byte bU2 = u();
        byte b2 = (byte) (bU2 & 31);
        if (b2 == 1) {
            return new cn(z(), (byte) ((bU2 >> 5) & 3), E());
        }
        throw new cq("Expected version 1 but got " + ((int) b2));
    }

    @Override // com.umeng.analytics.pro.cp
    public void i() throws bw {
    }

    @Override // com.umeng.analytics.pro.cp
    public cu j() throws bw {
        this.f21599m.a(this.f21600n);
        this.f21600n = (short) 0;
        return f21588d;
    }

    @Override // com.umeng.analytics.pro.cp
    public void k() throws bw {
        this.f21600n = this.f21599m.a();
    }

    @Override // com.umeng.analytics.pro.cp
    public ck l() throws bw {
        byte bU = u();
        if (bU == 0) {
            return f21589e;
        }
        short s2 = (short) ((bU & 240) >> 4);
        byte b2 = (byte) (bU & 15);
        ck ckVar = new ck("", d(b2), s2 == 0 ? v() : (short) (this.f21600n + s2));
        if (c(bU)) {
            this.f21602p = b2 == 1 ? Boolean.TRUE : Boolean.FALSE;
        }
        this.f21600n = ckVar.f21620c;
        return ckVar;
    }

    @Override // com.umeng.analytics.pro.cp
    public void m() throws bw {
    }

    @Override // com.umeng.analytics.pro.cp
    public cm n() throws bw {
        int iE = E();
        byte bU = iE == 0 ? (byte) 0 : u();
        return new cm(d((byte) (bU >> 4)), d((byte) (bU & 15)), iE);
    }

    @Override // com.umeng.analytics.pro.cp
    public void o() throws bw {
    }

    @Override // com.umeng.analytics.pro.cp
    public cl p() throws bw {
        byte bU = u();
        int iE = (bU >> 4) & 15;
        if (iE == 15) {
            iE = E();
        }
        return new cl(d(bU), iE);
    }

    @Override // com.umeng.analytics.pro.cp
    public void q() throws bw {
    }

    @Override // com.umeng.analytics.pro.cp
    public ct r() throws bw {
        return new ct(p());
    }

    @Override // com.umeng.analytics.pro.cp
    public void s() throws bw {
    }

    @Override // com.umeng.analytics.pro.cp
    public boolean t() throws bw {
        Boolean bool = this.f21602p;
        if (bool == null) {
            return u() == 1;
        }
        boolean zBooleanValue = bool.booleanValue();
        this.f21602p = null;
        return zBooleanValue;
    }

    @Override // com.umeng.analytics.pro.cp
    public byte u() throws bw {
        if (this.f21633g.h() <= 0) {
            this.f21633g.d(this.f21598c, 0, 1);
            return this.f21598c[0];
        }
        byte b2 = this.f21633g.f()[this.f21633g.g()];
        this.f21633g.a(1);
        return b2;
    }

    @Override // com.umeng.analytics.pro.cp
    public short v() throws bw {
        return (short) g(E());
    }

    @Override // com.umeng.analytics.pro.cp
    public int w() throws bw {
        return g(E());
    }

    @Override // com.umeng.analytics.pro.cp
    public long x() throws bw {
        return d(F());
    }

    @Override // com.umeng.analytics.pro.cp
    public double y() throws bw {
        byte[] bArr = new byte[8];
        this.f21633g.d(bArr, 0, 8);
        return Double.longBitsToDouble(a(bArr));
    }

    @Override // com.umeng.analytics.pro.cp
    public String z() throws bw {
        int iE = E();
        f(iE);
        if (iE == 0) {
            return "";
        }
        try {
            if (this.f21633g.h() < iE) {
                return new String(e(iE), "UTF-8");
            }
            String str = new String(this.f21633g.f(), this.f21633g.g(), iE, "UTF-8");
            this.f21633g.a(iE);
            return str;
        } catch (UnsupportedEncodingException unused) {
            throw new bw("UTF-8 not supported!");
        }
    }

    public static class a implements cr {

        /* renamed from: a, reason: collision with root package name */
        private final long f21605a;

        public a() {
            this.f21605a = -1L;
        }

        @Override // com.umeng.analytics.pro.cr
        public cp a(dd ddVar) {
            return new cj(ddVar, this.f21605a);
        }

        public a(int i2) {
            this.f21605a = i2;
        }
    }

    private void b(int i2) throws bw {
        int i3 = 0;
        while ((i2 & (-128)) != 0) {
            this.f21596a[i3] = (byte) ((i2 & 127) | 128);
            i2 >>>= 7;
            i3++;
        }
        byte[] bArr = this.f21596a;
        bArr[i3] = (byte) i2;
        this.f21633g.b(bArr, 0, i3 + 1);
    }

    private long c(long j2) {
        return (j2 >> 63) ^ (j2 << 1);
    }

    private byte[] e(int i2) throws bw {
        if (i2 == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[i2];
        this.f21633g.d(bArr, 0, i2);
        return bArr;
    }

    private void f(int i2) throws cq {
        if (i2 < 0) {
            throw new cq("Negative length: " + i2);
        }
        long j2 = this.f21603q;
        if (j2 == -1 || i2 <= j2) {
            return;
        }
        throw new cq("Length exceeded max allowed: " + i2);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(cn cnVar) throws bw {
        b(f21591h);
        d(((cnVar.f21627b << 5) & (-32)) | 1);
        b(cnVar.f21628c);
        a(cnVar.f21626a);
    }

    @Override // com.umeng.analytics.pro.cp
    public void d() throws bw {
        b((byte) 0);
    }

    @Override // com.umeng.analytics.pro.cp
    public void g() throws bw {
    }

    private void d(int i2) throws bw {
        b((byte) i2);
    }

    @Override // com.umeng.analytics.pro.cp
    public void c() throws bw {
    }

    private boolean c(byte b2) {
        int i2 = b2 & 15;
        return i2 == 1 || i2 == 2;
    }

    private byte d(byte b2) throws cq {
        byte b3 = (byte) (b2 & 15);
        switch (b3) {
            case 0:
                return (byte) 0;
            case 1:
            case 2:
                return (byte) 2;
            case 3:
                return (byte) 3;
            case 4:
                return (byte) 6;
            case 5:
                return (byte) 8;
            case 6:
                return (byte) 10;
            case 7:
                return (byte) 4;
            case 8:
                return (byte) 11;
            case 9:
                return (byte) 15;
            case 10:
                return (byte) 14;
            case 11:
                return (byte) 13;
            case 12:
                return (byte) 12;
            default:
                throw new cq("don't know what type: " + ((int) b3));
        }
    }

    private void b(long j2) throws bw {
        int i2 = 0;
        while (((-128) & j2) != 0) {
            this.f21597b[i2] = (byte) ((127 & j2) | 128);
            j2 >>>= 7;
            i2++;
        }
        byte[] bArr = this.f21597b;
        bArr[i2] = (byte) j2;
        this.f21633g.b(bArr, 0, i2 + 1);
    }

    private byte e(byte b2) {
        return f21590f[b2];
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(cu cuVar) throws bw {
        this.f21599m.a(this.f21600n);
        this.f21600n = (short) 0;
    }

    private void b(byte b2) throws bw {
        byte[] bArr = this.f21604r;
        bArr[0] = b2;
        this.f21633g.b(bArr);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(ck ckVar) throws bw {
        if (ckVar.f21619b == 2) {
            this.f21601o = ckVar;
        } else {
            a(ckVar, (byte) -1);
        }
    }

    public cj(dd ddVar) {
        this(ddVar, -1L);
    }

    private void a(ck ckVar, byte b2) throws bw {
        if (b2 == -1) {
            b2 = e(ckVar.f21619b);
        }
        short s2 = ckVar.f21620c;
        short s3 = this.f21600n;
        if (s2 > s3 && s2 - s3 <= 15) {
            d(b2 | ((s2 - s3) << 4));
        } else {
            b(b2);
            a(ckVar.f21620c);
        }
        this.f21600n = ckVar.f21620c;
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(cm cmVar) throws bw {
        int i2 = cmVar.f21625c;
        if (i2 == 0) {
            d(0);
            return;
        }
        b(i2);
        d(e(cmVar.f21624b) | (e(cmVar.f21623a) << 4));
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(cl clVar) throws bw {
        a(clVar.f21621a, clVar.f21622b);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(ct ctVar) throws bw {
        a(ctVar.f21643a, ctVar.f21644b);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(boolean z2) throws bw {
        ck ckVar = this.f21601o;
        if (ckVar != null) {
            a(ckVar, z2 ? (byte) 1 : (byte) 2);
            this.f21601o = null;
        } else {
            b(z2 ? (byte) 1 : (byte) 2);
        }
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(byte b2) throws bw {
        b(b2);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(short s2) throws bw {
        b(c((int) s2));
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(int i2) throws bw {
        b(c(i2));
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(long j2) throws bw {
        b(c(j2));
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(double d2) throws bw {
        byte[] bArr = {0, 0, 0, 0, 0, 0, 0, 0};
        a(Double.doubleToLongBits(d2), bArr, 0);
        this.f21633g.b(bArr);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(String str) throws bw {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            a(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException unused) {
            throw new bw("UTF-8 not supported!");
        }
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(ByteBuffer byteBuffer) throws bw {
        a(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), byteBuffer.limit() - byteBuffer.position());
    }

    private void a(byte[] bArr, int i2, int i3) throws bw {
        b(i3);
        this.f21633g.b(bArr, i2, i3);
    }

    protected void a(byte b2, int i2) throws bw {
        if (i2 <= 14) {
            d(e(b2) | (i2 << 4));
        } else {
            d(e(b2) | 240);
            b(i2);
        }
    }

    private void a(long j2, byte[] bArr, int i2) {
        bArr[i2] = (byte) (j2 & 255);
        bArr[i2 + 1] = (byte) ((j2 >> 8) & 255);
        bArr[i2 + 2] = (byte) ((j2 >> 16) & 255);
        bArr[i2 + 3] = (byte) ((j2 >> 24) & 255);
        bArr[i2 + 4] = (byte) ((j2 >> 32) & 255);
        bArr[i2 + 5] = (byte) ((j2 >> 40) & 255);
        bArr[i2 + 6] = (byte) ((j2 >> 48) & 255);
        bArr[i2 + 7] = (byte) ((j2 >> 56) & 255);
    }

    private long a(byte[] bArr) {
        return ((bArr[7] & 255) << 56) | ((bArr[6] & 255) << 48) | ((bArr[5] & 255) << 40) | ((bArr[4] & 255) << 32) | ((bArr[3] & 255) << 24) | ((bArr[2] & 255) << 16) | ((bArr[1] & 255) << 8) | (255 & bArr[0]);
    }
}
