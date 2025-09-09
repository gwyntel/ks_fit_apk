package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class x0 {

    /* renamed from: a, reason: collision with root package name */
    private final int f17980a;

    /* renamed from: b, reason: collision with root package name */
    private final int f17981b;

    /* renamed from: c, reason: collision with root package name */
    private final int f17982c;

    /* renamed from: d, reason: collision with root package name */
    private final int f17983d;

    /* renamed from: e, reason: collision with root package name */
    private int f17984e = -1;

    x0(int i2, int i3, int i4, int i5) {
        this.f17980a = i2;
        this.f17981b = i3;
        this.f17982c = i4;
        this.f17983d = i5;
    }

    boolean a(int i2) {
        return i2 != -1 && this.f17982c == (i2 % 3) * 3;
    }

    int b() {
        return this.f17981b;
    }

    int c() {
        return this.f17984e;
    }

    int d() {
        return this.f17980a;
    }

    int e() {
        return this.f17983d;
    }

    int f() {
        return this.f17981b - this.f17980a;
    }

    boolean g() {
        return a(this.f17984e);
    }

    void h() {
        this.f17984e = ((this.f17983d / 30) * 3) + (this.f17982c / 3);
    }

    public String toString() {
        return this.f17984e + "|" + this.f17983d;
    }

    int a() {
        return this.f17982c;
    }

    void b(int i2) {
        this.f17984e = i2;
    }
}
