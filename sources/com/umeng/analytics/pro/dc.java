package com.umeng.analytics.pro;

/* loaded from: classes4.dex */
public final class dc extends dd {

    /* renamed from: a, reason: collision with root package name */
    private byte[] f21668a;

    /* renamed from: b, reason: collision with root package name */
    private int f21669b;

    /* renamed from: c, reason: collision with root package name */
    private int f21670c;

    public dc() {
    }

    @Override // com.umeng.analytics.pro.dd
    public boolean a() {
        return true;
    }

    @Override // com.umeng.analytics.pro.dd
    public void b() throws de {
    }

    @Override // com.umeng.analytics.pro.dd
    public void c() {
    }

    public void e() {
        this.f21668a = null;
    }

    @Override // com.umeng.analytics.pro.dd
    public byte[] f() {
        return this.f21668a;
    }

    @Override // com.umeng.analytics.pro.dd
    public int g() {
        return this.f21669b;
    }

    @Override // com.umeng.analytics.pro.dd
    public int h() {
        return this.f21670c - this.f21669b;
    }

    public dc(byte[] bArr) {
        a(bArr);
    }

    public void a(byte[] bArr) {
        c(bArr, 0, bArr.length);
    }

    @Override // com.umeng.analytics.pro.dd
    public void b(byte[] bArr, int i2, int i3) throws de {
        throw new UnsupportedOperationException("No writing allowed!");
    }

    public void c(byte[] bArr, int i2, int i3) {
        this.f21668a = bArr;
        this.f21669b = i2;
        this.f21670c = i2 + i3;
    }

    @Override // com.umeng.analytics.pro.dd
    public int a(byte[] bArr, int i2, int i3) throws de {
        int iH = h();
        if (i3 > iH) {
            i3 = iH;
        }
        if (i3 > 0) {
            System.arraycopy(this.f21668a, this.f21669b, bArr, i2, i3);
            a(i3);
        }
        return i3;
    }

    public dc(byte[] bArr, int i2, int i3) {
        c(bArr, i2, i3);
    }

    @Override // com.umeng.analytics.pro.dd
    public void a(int i2) {
        this.f21669b += i2;
    }
}
