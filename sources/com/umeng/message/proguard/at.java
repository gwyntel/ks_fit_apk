package com.umeng.message.proguard;

/* loaded from: classes4.dex */
abstract class at {

    /* renamed from: b, reason: collision with root package name */
    protected byte[] f22793b;

    /* renamed from: c, reason: collision with root package name */
    protected int f22794c;

    /* renamed from: d, reason: collision with root package name */
    protected boolean f22795d;

    /* renamed from: e, reason: collision with root package name */
    protected int f22796e;

    /* renamed from: f, reason: collision with root package name */
    protected int f22797f;

    /* renamed from: i, reason: collision with root package name */
    private int f22800i;

    /* renamed from: a, reason: collision with root package name */
    protected final byte f22792a = 61;

    /* renamed from: g, reason: collision with root package name */
    private final int f22798g = 3;

    /* renamed from: h, reason: collision with root package name */
    private final int f22799h = 4;

    protected at() {
    }

    protected final void a(int i2) {
        byte[] bArr = this.f22793b;
        if (bArr == null || bArr.length < this.f22794c + i2) {
            if (bArr == null) {
                this.f22793b = new byte[8192];
                this.f22794c = 0;
                this.f22800i = 0;
            } else {
                byte[] bArr2 = new byte[bArr.length * 2];
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                this.f22793b = bArr2;
            }
        }
    }

    abstract void a(byte[] bArr, int i2, int i3);

    abstract void b(byte[] bArr, int i2, int i3);

    public byte[] b(String str) {
        return e(str.getBytes());
    }

    public long c(byte[] bArr) {
        int length = bArr.length;
        int i2 = this.f22798g;
        return (((length + i2) - 1) / i2) * this.f22799h;
    }

    public byte[] d(byte[] bArr) {
        a();
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        a(bArr, 0, bArr.length);
        a(bArr, 0, -1);
        int i2 = this.f22794c - this.f22800i;
        byte[] bArr2 = new byte[i2];
        a(bArr2, i2);
        return bArr2;
    }

    public byte[] e(byte[] bArr) {
        a();
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        b(bArr, 0, bArr.length);
        b(bArr, 0, -1);
        int i2 = this.f22794c;
        byte[] bArr2 = new byte[i2];
        a(bArr2, i2);
        return bArr2;
    }

    private int a(byte[] bArr, int i2) {
        byte[] bArr2 = this.f22793b;
        if (bArr2 == null) {
            return this.f22795d ? -1 : 0;
        }
        int iMin = Math.min(bArr2 != null ? this.f22794c - this.f22800i : 0, i2);
        System.arraycopy(this.f22793b, this.f22800i, bArr, 0, iMin);
        int i3 = this.f22800i + iMin;
        this.f22800i = i3;
        if (i3 >= this.f22794c) {
            this.f22793b = null;
        }
        return iMin;
    }

    private void a() {
        this.f22793b = null;
        this.f22794c = 0;
        this.f22800i = 0;
        this.f22796e = 0;
        this.f22797f = 0;
        this.f22795d = false;
    }
}
