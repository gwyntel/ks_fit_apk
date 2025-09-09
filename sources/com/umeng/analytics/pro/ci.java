package com.umeng.analytics.pro;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class ci extends cp {

    /* renamed from: a, reason: collision with root package name */
    protected static final int f21570a = -65536;

    /* renamed from: b, reason: collision with root package name */
    protected static final int f21571b = -2147418112;

    /* renamed from: h, reason: collision with root package name */
    private static final cu f21572h = new cu();

    /* renamed from: c, reason: collision with root package name */
    protected boolean f21573c;

    /* renamed from: d, reason: collision with root package name */
    protected boolean f21574d;

    /* renamed from: e, reason: collision with root package name */
    protected int f21575e;

    /* renamed from: f, reason: collision with root package name */
    protected boolean f21576f;

    /* renamed from: i, reason: collision with root package name */
    private byte[] f21577i;

    /* renamed from: j, reason: collision with root package name */
    private byte[] f21578j;

    /* renamed from: k, reason: collision with root package name */
    private byte[] f21579k;

    /* renamed from: l, reason: collision with root package name */
    private byte[] f21580l;

    /* renamed from: m, reason: collision with root package name */
    private byte[] f21581m;

    /* renamed from: n, reason: collision with root package name */
    private byte[] f21582n;

    /* renamed from: o, reason: collision with root package name */
    private byte[] f21583o;

    /* renamed from: p, reason: collision with root package name */
    private byte[] f21584p;

    public static class a implements cr {

        /* renamed from: a, reason: collision with root package name */
        protected boolean f21585a;

        /* renamed from: b, reason: collision with root package name */
        protected boolean f21586b;

        /* renamed from: c, reason: collision with root package name */
        protected int f21587c;

        public a() {
            this(false, true);
        }

        @Override // com.umeng.analytics.pro.cr
        public cp a(dd ddVar) {
            ci ciVar = new ci(ddVar, this.f21585a, this.f21586b);
            int i2 = this.f21587c;
            if (i2 != 0) {
                ciVar.c(i2);
            }
            return ciVar;
        }

        public a(boolean z2, boolean z3) {
            this(z2, z3, 0);
        }

        public a(boolean z2, boolean z3, int i2) {
            this.f21585a = z2;
            this.f21586b = z3;
            this.f21587c = i2;
        }
    }

    public ci(dd ddVar) {
        this(ddVar, false, true);
    }

    @Override // com.umeng.analytics.pro.cp
    public ByteBuffer A() throws bw {
        int iW = w();
        d(iW);
        if (this.f21633g.h() >= iW) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(this.f21633g.f(), this.f21633g.g(), iW);
            this.f21633g.a(iW);
            return byteBufferWrap;
        }
        byte[] bArr = new byte[iW];
        this.f21633g.d(bArr, 0, iW);
        return ByteBuffer.wrap(bArr);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a() {
    }

    @Override // com.umeng.analytics.pro.cp
    public void b() {
    }

    @Override // com.umeng.analytics.pro.cp
    public void c() {
    }

    @Override // com.umeng.analytics.pro.cp
    public void d() throws bw {
        a((byte) 0);
    }

    @Override // com.umeng.analytics.pro.cp
    public void e() {
    }

    @Override // com.umeng.analytics.pro.cp
    public void f() {
    }

    @Override // com.umeng.analytics.pro.cp
    public void g() {
    }

    @Override // com.umeng.analytics.pro.cp
    public cn h() throws bw {
        int iW = w();
        if (iW < 0) {
            if (((-65536) & iW) == f21571b) {
                return new cn(z(), (byte) (iW & 255), w());
            }
            throw new cq(4, "Bad version in readMessageBegin");
        }
        if (this.f21573c) {
            throw new cq(4, "Missing version in readMessageBegin, old client?");
        }
        return new cn(b(iW), u(), w());
    }

    @Override // com.umeng.analytics.pro.cp
    public void i() {
    }

    @Override // com.umeng.analytics.pro.cp
    public cu j() {
        return f21572h;
    }

    @Override // com.umeng.analytics.pro.cp
    public void k() {
    }

    @Override // com.umeng.analytics.pro.cp
    public ck l() throws bw {
        byte bU = u();
        return new ck("", bU, bU == 0 ? (short) 0 : v());
    }

    @Override // com.umeng.analytics.pro.cp
    public void m() {
    }

    @Override // com.umeng.analytics.pro.cp
    public cm n() throws bw {
        return new cm(u(), u(), w());
    }

    @Override // com.umeng.analytics.pro.cp
    public void o() {
    }

    @Override // com.umeng.analytics.pro.cp
    public cl p() throws bw {
        return new cl(u(), w());
    }

    @Override // com.umeng.analytics.pro.cp
    public void q() {
    }

    @Override // com.umeng.analytics.pro.cp
    public ct r() throws bw {
        return new ct(u(), w());
    }

    @Override // com.umeng.analytics.pro.cp
    public void s() {
    }

    @Override // com.umeng.analytics.pro.cp
    public boolean t() throws bw {
        return u() == 1;
    }

    @Override // com.umeng.analytics.pro.cp
    public byte u() throws bw {
        if (this.f21633g.h() < 1) {
            a(this.f21581m, 0, 1);
            return this.f21581m[0];
        }
        byte b2 = this.f21633g.f()[this.f21633g.g()];
        this.f21633g.a(1);
        return b2;
    }

    @Override // com.umeng.analytics.pro.cp
    public short v() throws bw {
        int iG;
        byte[] bArrF = this.f21582n;
        if (this.f21633g.h() >= 2) {
            bArrF = this.f21633g.f();
            iG = this.f21633g.g();
            this.f21633g.a(2);
        } else {
            a(this.f21582n, 0, 2);
            iG = 0;
        }
        return (short) ((bArrF[iG + 1] & 255) | ((bArrF[iG] & 255) << 8));
    }

    @Override // com.umeng.analytics.pro.cp
    public int w() throws bw {
        int iG;
        byte[] bArrF = this.f21583o;
        if (this.f21633g.h() >= 4) {
            bArrF = this.f21633g.f();
            iG = this.f21633g.g();
            this.f21633g.a(4);
        } else {
            a(this.f21583o, 0, 4);
            iG = 0;
        }
        return (bArrF[iG + 3] & 255) | ((bArrF[iG] & 255) << 24) | ((bArrF[iG + 1] & 255) << 16) | ((bArrF[iG + 2] & 255) << 8);
    }

    @Override // com.umeng.analytics.pro.cp
    public long x() throws bw {
        int iG;
        byte[] bArrF = this.f21584p;
        if (this.f21633g.h() >= 8) {
            bArrF = this.f21633g.f();
            iG = this.f21633g.g();
            this.f21633g.a(8);
        } else {
            a(this.f21584p, 0, 8);
            iG = 0;
        }
        return (bArrF[iG + 7] & 255) | ((bArrF[iG] & 255) << 56) | ((bArrF[iG + 1] & 255) << 48) | ((bArrF[iG + 2] & 255) << 40) | ((bArrF[iG + 3] & 255) << 32) | ((bArrF[iG + 4] & 255) << 24) | ((bArrF[iG + 5] & 255) << 16) | ((bArrF[iG + 6] & 255) << 8);
    }

    @Override // com.umeng.analytics.pro.cp
    public double y() throws bw {
        return Double.longBitsToDouble(x());
    }

    @Override // com.umeng.analytics.pro.cp
    public String z() throws bw {
        int iW = w();
        if (this.f21633g.h() < iW) {
            return b(iW);
        }
        try {
            String str = new String(this.f21633g.f(), this.f21633g.g(), iW, "UTF-8");
            this.f21633g.a(iW);
            return str;
        } catch (UnsupportedEncodingException unused) {
            throw new bw("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    public ci(dd ddVar, boolean z2, boolean z3) {
        super(ddVar);
        this.f21576f = false;
        this.f21577i = new byte[1];
        this.f21578j = new byte[2];
        this.f21579k = new byte[4];
        this.f21580l = new byte[8];
        this.f21581m = new byte[1];
        this.f21582n = new byte[2];
        this.f21583o = new byte[4];
        this.f21584p = new byte[8];
        this.f21573c = z2;
        this.f21574d = z3;
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(cu cuVar) {
    }

    public String b(int i2) throws bw {
        try {
            d(i2);
            byte[] bArr = new byte[i2];
            this.f21633g.d(bArr, 0, i2);
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            throw new bw("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    public void c(int i2) {
        this.f21575e = i2;
        this.f21576f = true;
    }

    protected void d(int i2) throws bw {
        if (i2 < 0) {
            throw new cq("Negative length: " + i2);
        }
        if (this.f21576f) {
            int i3 = this.f21575e - i2;
            this.f21575e = i3;
            if (i3 >= 0) {
                return;
            }
            throw new cq("Message length exceeded: " + i2);
        }
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(cn cnVar) throws bw, UnsupportedEncodingException {
        if (this.f21574d) {
            a(cnVar.f21627b | f21571b);
            a(cnVar.f21626a);
            a(cnVar.f21628c);
        } else {
            a(cnVar.f21626a);
            a(cnVar.f21627b);
            a(cnVar.f21628c);
        }
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(ck ckVar) throws bw {
        a(ckVar.f21619b);
        a(ckVar.f21620c);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(cm cmVar) throws bw {
        a(cmVar.f21623a);
        a(cmVar.f21624b);
        a(cmVar.f21625c);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(cl clVar) throws bw {
        a(clVar.f21621a);
        a(clVar.f21622b);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(ct ctVar) throws bw {
        a(ctVar.f21643a);
        a(ctVar.f21644b);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(boolean z2) throws bw {
        a(z2 ? (byte) 1 : (byte) 0);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(byte b2) throws bw {
        byte[] bArr = this.f21577i;
        bArr[0] = b2;
        this.f21633g.b(bArr, 0, 1);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(short s2) throws bw {
        byte[] bArr = this.f21578j;
        bArr[0] = (byte) ((s2 >> 8) & 255);
        bArr[1] = (byte) (s2 & 255);
        this.f21633g.b(bArr, 0, 2);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(int i2) throws bw {
        byte[] bArr = this.f21579k;
        bArr[0] = (byte) ((i2 >> 24) & 255);
        bArr[1] = (byte) ((i2 >> 16) & 255);
        bArr[2] = (byte) ((i2 >> 8) & 255);
        bArr[3] = (byte) (i2 & 255);
        this.f21633g.b(bArr, 0, 4);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(long j2) throws bw {
        byte[] bArr = this.f21580l;
        bArr[0] = (byte) ((j2 >> 56) & 255);
        bArr[1] = (byte) ((j2 >> 48) & 255);
        bArr[2] = (byte) ((j2 >> 40) & 255);
        bArr[3] = (byte) ((j2 >> 32) & 255);
        bArr[4] = (byte) ((j2 >> 24) & 255);
        bArr[5] = (byte) ((j2 >> 16) & 255);
        bArr[6] = (byte) ((j2 >> 8) & 255);
        bArr[7] = (byte) (j2 & 255);
        this.f21633g.b(bArr, 0, 8);
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(double d2) throws bw {
        a(Double.doubleToLongBits(d2));
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(String str) throws bw, UnsupportedEncodingException {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            a(bytes.length);
            this.f21633g.b(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException unused) {
            throw new bw("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override // com.umeng.analytics.pro.cp
    public void a(ByteBuffer byteBuffer) throws bw {
        int iLimit = byteBuffer.limit() - byteBuffer.position();
        a(iLimit);
        this.f21633g.b(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), iLimit);
    }

    private int a(byte[] bArr, int i2, int i3) throws bw {
        d(i3);
        return this.f21633g.d(bArr, i2, i3);
    }
}
