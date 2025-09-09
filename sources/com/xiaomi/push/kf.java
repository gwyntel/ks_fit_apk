package com.xiaomi.push;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class kf extends kj {

    /* renamed from: a, reason: collision with root package name */
    private static final ko f24369a = new ko();

    /* renamed from: a, reason: collision with other field name */
    protected int f915a;

    /* renamed from: a, reason: collision with other field name */
    protected boolean f916a;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f917a;

    /* renamed from: b, reason: collision with root package name */
    protected boolean f24370b;

    /* renamed from: b, reason: collision with other field name */
    private byte[] f918b;

    /* renamed from: c, reason: collision with root package name */
    protected boolean f24371c;

    /* renamed from: c, reason: collision with other field name */
    private byte[] f919c;

    /* renamed from: d, reason: collision with root package name */
    private byte[] f24372d;

    /* renamed from: e, reason: collision with root package name */
    private byte[] f24373e;

    /* renamed from: f, reason: collision with root package name */
    private byte[] f24374f;

    /* renamed from: g, reason: collision with root package name */
    private byte[] f24375g;

    /* renamed from: h, reason: collision with root package name */
    private byte[] f24376h;

    public static class a implements kl {

        /* renamed from: a, reason: collision with root package name */
        protected int f24377a;

        /* renamed from: a, reason: collision with other field name */
        protected boolean f920a;

        /* renamed from: b, reason: collision with root package name */
        protected boolean f24378b;

        public a() {
            this(false, true);
        }

        @Override // com.xiaomi.push.kl
        public kj a(kt ktVar) {
            kf kfVar = new kf(ktVar, this.f920a, this.f24378b);
            int i2 = this.f24377a;
            if (i2 != 0) {
                kfVar.b(i2);
            }
            return kfVar;
        }

        public a(boolean z2, boolean z3) {
            this(z2, z3, 0);
        }

        public a(boolean z2, boolean z3, int i2) {
            this.f920a = z2;
            this.f24378b = z3;
            this.f24377a = i2;
        }
    }

    public kf(kt ktVar, boolean z2, boolean z3) {
        super(ktVar);
        this.f24371c = false;
        this.f917a = new byte[1];
        this.f918b = new byte[2];
        this.f919c = new byte[4];
        this.f24372d = new byte[8];
        this.f24373e = new byte[1];
        this.f24374f = new byte[2];
        this.f24375g = new byte[4];
        this.f24376h = new byte[8];
        this.f916a = z2;
        this.f24370b = z3;
    }

    @Override // com.xiaomi.push.kj
    /* renamed from: a, reason: collision with other method in class */
    public void mo678a() {
    }

    @Override // com.xiaomi.push.kj
    public void b() {
    }

    @Override // com.xiaomi.push.kj
    public void c() {
        a((byte) 0);
    }

    @Override // com.xiaomi.push.kj
    public void d() {
    }

    @Override // com.xiaomi.push.kj
    public void e() {
    }

    @Override // com.xiaomi.push.kj
    public void f() {
    }

    @Override // com.xiaomi.push.kj
    public void g() {
    }

    @Override // com.xiaomi.push.kj
    public void h() {
    }

    @Override // com.xiaomi.push.kj
    public void i() {
    }

    @Override // com.xiaomi.push.kj
    public void j() {
    }

    @Override // com.xiaomi.push.kj
    public void a(ko koVar) {
    }

    public void b(int i2) {
        this.f915a = i2;
        this.f24371c = true;
    }

    protected void c(int i2) throws kd {
        if (i2 < 0) {
            throw new kd("Negative length: " + i2);
        }
        if (this.f24371c) {
            int i3 = this.f915a - i2;
            this.f915a = i3;
            if (i3 >= 0) {
                return;
            }
            throw new kd("Message length exceeded: " + i2);
        }
    }

    @Override // com.xiaomi.push.kj
    public void a(kg kgVar) {
        a(kgVar.f24379a);
        a(kgVar.f922a);
    }

    @Override // com.xiaomi.push.kj
    public void a(ki kiVar) {
        a(kiVar.f24381a);
        a(kiVar.f24382b);
        mo679a(kiVar.f924a);
    }

    @Override // com.xiaomi.push.kj
    public void a(kh khVar) {
        a(khVar.f24380a);
        mo679a(khVar.f923a);
    }

    @Override // com.xiaomi.push.kj
    public void a(boolean z2) {
        a(z2 ? (byte) 1 : (byte) 0);
    }

    @Override // com.xiaomi.push.kj
    public void a(byte b2) {
        byte[] bArr = this.f917a;
        bArr[0] = b2;
        ((kj) this).f24383a.mo681a(bArr, 0, 1);
    }

    @Override // com.xiaomi.push.kj
    public void a(short s2) {
        byte[] bArr = this.f918b;
        bArr[0] = (byte) ((s2 >> 8) & 255);
        bArr[1] = (byte) (s2 & 255);
        ((kj) this).f24383a.mo681a(bArr, 0, 2);
    }

    @Override // com.xiaomi.push.kj
    /* renamed from: a, reason: collision with other method in class */
    public void mo679a(int i2) {
        byte[] bArr = this.f919c;
        bArr[0] = (byte) ((i2 >> 24) & 255);
        bArr[1] = (byte) ((i2 >> 16) & 255);
        bArr[2] = (byte) ((i2 >> 8) & 255);
        bArr[3] = (byte) (i2 & 255);
        ((kj) this).f24383a.mo681a(bArr, 0, 4);
    }

    @Override // com.xiaomi.push.kj
    public void a(long j2) {
        byte[] bArr = this.f24372d;
        bArr[0] = (byte) ((j2 >> 56) & 255);
        bArr[1] = (byte) ((j2 >> 48) & 255);
        bArr[2] = (byte) ((j2 >> 40) & 255);
        bArr[3] = (byte) ((j2 >> 32) & 255);
        bArr[4] = (byte) ((j2 >> 24) & 255);
        bArr[5] = (byte) ((j2 >> 16) & 255);
        bArr[6] = (byte) ((j2 >> 8) & 255);
        bArr[7] = (byte) (j2 & 255);
        ((kj) this).f24383a.mo681a(bArr, 0, 8);
    }

    @Override // com.xiaomi.push.kj
    public void a(String str) throws UnsupportedEncodingException, kd {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            mo679a(bytes.length);
            ((kj) this).f24383a.mo681a(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException unused) {
            throw new kd("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override // com.xiaomi.push.kj
    public void a(ByteBuffer byteBuffer) {
        int iLimit = (byteBuffer.limit() - byteBuffer.position()) - byteBuffer.arrayOffset();
        mo679a(iLimit);
        ((kj) this).f24383a.mo681a(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), iLimit);
    }

    @Override // com.xiaomi.push.kj
    /* renamed from: a, reason: collision with other method in class */
    public ko mo674a() {
        return f24369a;
    }

    @Override // com.xiaomi.push.kj
    /* renamed from: a, reason: collision with other method in class */
    public kg mo670a() throws kd {
        byte bA = a();
        return new kg("", bA, bA == 0 ? (short) 0 : mo677a());
    }

    @Override // com.xiaomi.push.kj
    /* renamed from: a, reason: collision with other method in class */
    public ki mo672a() {
        return new ki(a(), a(), mo668a());
    }

    @Override // com.xiaomi.push.kj
    /* renamed from: a, reason: collision with other method in class */
    public kh mo671a() {
        return new kh(a(), mo668a());
    }

    @Override // com.xiaomi.push.kj
    /* renamed from: a, reason: collision with other method in class */
    public kn mo673a() {
        return new kn(a(), mo668a());
    }

    @Override // com.xiaomi.push.kj
    /* renamed from: a, reason: collision with other method in class */
    public boolean mo680a() {
        return a() == 1;
    }

    @Override // com.xiaomi.push.kj
    public byte a() throws kd {
        if (((kj) this).f24383a.b() >= 1) {
            byte b2 = ((kj) this).f24383a.mo682a()[((kj) this).f24383a.a()];
            ((kj) this).f24383a.a(1);
            return b2;
        }
        a(this.f24373e, 0, 1);
        return this.f24373e[0];
    }

    @Override // com.xiaomi.push.kj
    /* renamed from: a, reason: collision with other method in class */
    public short mo677a() throws kd {
        int iA;
        byte[] bArrMo682a = this.f24374f;
        if (((kj) this).f24383a.b() >= 2) {
            bArrMo682a = ((kj) this).f24383a.mo682a();
            iA = ((kj) this).f24383a.a();
            ((kj) this).f24383a.a(2);
        } else {
            a(this.f24374f, 0, 2);
            iA = 0;
        }
        return (short) ((bArrMo682a[iA + 1] & 255) | ((bArrMo682a[iA] & 255) << 8));
    }

    @Override // com.xiaomi.push.kj
    /* renamed from: a, reason: collision with other method in class */
    public int mo668a() throws kd {
        int iA;
        byte[] bArrMo682a = this.f24375g;
        if (((kj) this).f24383a.b() >= 4) {
            bArrMo682a = ((kj) this).f24383a.mo682a();
            iA = ((kj) this).f24383a.a();
            ((kj) this).f24383a.a(4);
        } else {
            a(this.f24375g, 0, 4);
            iA = 0;
        }
        return (bArrMo682a[iA + 3] & 255) | ((bArrMo682a[iA] & 255) << 24) | ((bArrMo682a[iA + 1] & 255) << 16) | ((bArrMo682a[iA + 2] & 255) << 8);
    }

    @Override // com.xiaomi.push.kj
    /* renamed from: a, reason: collision with other method in class */
    public long mo669a() throws kd {
        int iA;
        byte[] bArrMo682a = this.f24376h;
        if (((kj) this).f24383a.b() >= 8) {
            bArrMo682a = ((kj) this).f24383a.mo682a();
            iA = ((kj) this).f24383a.a();
            ((kj) this).f24383a.a(8);
        } else {
            a(this.f24376h, 0, 8);
            iA = 0;
        }
        return (bArrMo682a[iA + 7] & 255) | ((bArrMo682a[iA] & 255) << 56) | ((bArrMo682a[iA + 1] & 255) << 48) | ((bArrMo682a[iA + 2] & 255) << 40) | ((bArrMo682a[iA + 3] & 255) << 32) | ((bArrMo682a[iA + 4] & 255) << 24) | ((bArrMo682a[iA + 5] & 255) << 16) | ((bArrMo682a[iA + 6] & 255) << 8);
    }

    @Override // com.xiaomi.push.kj
    /* renamed from: a, reason: collision with other method in class */
    public double mo667a() {
        return Double.longBitsToDouble(mo669a());
    }

    @Override // com.xiaomi.push.kj
    /* renamed from: a, reason: collision with other method in class */
    public String mo675a() throws kd {
        int iMo668a = mo668a();
        if (((kj) this).f24383a.b() >= iMo668a) {
            try {
                String str = new String(((kj) this).f24383a.mo682a(), ((kj) this).f24383a.a(), iMo668a, "UTF-8");
                ((kj) this).f24383a.a(iMo668a);
                return str;
            } catch (UnsupportedEncodingException unused) {
                throw new kd("JVM DOES NOT SUPPORT UTF-8");
            }
        }
        return a(iMo668a);
    }

    public String a(int i2) throws kd {
        try {
            c(i2);
            byte[] bArr = new byte[i2];
            ((kj) this).f24383a.b(bArr, 0, i2);
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            throw new kd("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override // com.xiaomi.push.kj
    /* renamed from: a, reason: collision with other method in class */
    public ByteBuffer mo676a() throws kd {
        int iMo668a = mo668a();
        c(iMo668a);
        if (((kj) this).f24383a.b() >= iMo668a) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(((kj) this).f24383a.mo682a(), ((kj) this).f24383a.a(), iMo668a);
            ((kj) this).f24383a.a(iMo668a);
            return byteBufferWrap;
        }
        byte[] bArr = new byte[iMo668a];
        ((kj) this).f24383a.b(bArr, 0, iMo668a);
        return ByteBuffer.wrap(bArr);
    }

    private int a(byte[] bArr, int i2, int i3) throws kd {
        c(i3);
        return ((kj) this).f24383a.b(bArr, i2, i3);
    }
}
