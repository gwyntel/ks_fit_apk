package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
abstract class i7 {

    /* renamed from: b, reason: collision with root package name */
    static final i7 f17395b = new a7(null, 0, 0);

    /* renamed from: a, reason: collision with root package name */
    private final i7 f17396a;

    i7(i7 i7Var) {
        this.f17396a = i7Var;
    }

    final i7 a() {
        return this.f17396a;
    }

    abstract void a(r rVar, byte[] bArr);

    final i7 b(int i2, int i3) {
        return new q(this, i2, i3);
    }

    final i7 a(int i2, int i3) {
        return new a7(this, i2, i3);
    }
}
