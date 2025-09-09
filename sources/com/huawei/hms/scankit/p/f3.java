package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class f3 extends u6 {

    /* renamed from: e, reason: collision with root package name */
    private final float f17246e;

    /* renamed from: f, reason: collision with root package name */
    private final int f17247f;

    f3(float f2, float f3, float f4) {
        this(f2, f3, f4, 1);
    }

    @Override // com.huawei.hms.scankit.p.u6
    public int a() {
        return this.f17247f;
    }

    boolean b(float f2, float f3, float f4) {
        if (Math.abs(f3 - c()) > f2 || Math.abs(f4 - b()) > f2) {
            return false;
        }
        float fAbs = Math.abs(f2 - this.f17246e);
        return fAbs <= 1.0f || fAbs <= this.f17246e;
    }

    f3 c(float f2, float f3, float f4) {
        int i2 = this.f17247f;
        int i3 = i2 + 1;
        float fB = (i2 * b()) + f3;
        float f5 = i3;
        return new f3(fB / f5, ((this.f17247f * c()) + f2) / f5, ((this.f17247f * this.f17246e) + f4) / f5, i3);
    }

    public float e() {
        return this.f17246e;
    }

    private f3(float f2, float f3, float f4, int i2) {
        super(f2, f3);
        this.f17246e = f4;
        this.f17247f = i2;
    }
}
