package com.alipay.android.phone.mrpc.core;

/* loaded from: classes2.dex */
public final class p extends u {

    /* renamed from: c, reason: collision with root package name */
    public int f9017c;

    /* renamed from: d, reason: collision with root package name */
    public String f9018d;

    /* renamed from: e, reason: collision with root package name */
    public long f9019e;

    /* renamed from: f, reason: collision with root package name */
    public long f9020f;

    /* renamed from: g, reason: collision with root package name */
    public String f9021g;

    /* renamed from: h, reason: collision with root package name */
    public HttpUrlHeader f9022h;

    public p(HttpUrlHeader httpUrlHeader, int i2, String str, byte[] bArr) {
        this.f9022h = httpUrlHeader;
        this.f9017c = i2;
        this.f9018d = str;
        this.f9043a = bArr;
    }

    public final HttpUrlHeader a() {
        return this.f9022h;
    }

    public final void b(long j2) {
        this.f9020f = j2;
    }

    public final void a(long j2) {
        this.f9019e = j2;
    }

    public final void a(String str) {
        this.f9021g = str;
    }
}
