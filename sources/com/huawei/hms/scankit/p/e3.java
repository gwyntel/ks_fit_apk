package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class e3 extends u6 {

    /* renamed from: e, reason: collision with root package name */
    private final float f17197e;

    /* renamed from: f, reason: collision with root package name */
    private final int f17198f;

    /* renamed from: g, reason: collision with root package name */
    private final boolean f17199g;

    e3(float f2, float f3, float f4, boolean z2) {
        this(f2, f3, f4, z2, 1);
    }

    e3 a(float f2, float f3, float f4, boolean z2) {
        int i2 = this.f17198f;
        int i3 = i2 + 1;
        float fB = (i2 * b()) + f3;
        float f5 = i3;
        float f6 = fB / f5;
        float fC = ((this.f17198f * c()) + f2) / f5;
        float f7 = ((this.f17198f * this.f17197e) + f4) / f5;
        boolean z3 = this.f17199g;
        return new e3(f6, fC, f7, z3 ? z2 : z3, i3);
    }

    boolean b(float f2, float f3, float f4) {
        if (Math.abs(f3 - c()) > f2 || Math.abs(f4 - b()) > f2) {
            return false;
        }
        float fAbs = Math.abs(f2 - this.f17197e);
        return fAbs <= 1.0f || fAbs <= this.f17197e;
    }

    @Override // com.huawei.hms.scankit.p.u6
    public boolean d() {
        return this.f17199g;
    }

    public float e() {
        return this.f17197e;
    }

    public e3(float f2, float f3, float f4, boolean z2, int i2) {
        super(f2, f3, i2);
        this.f17197e = f4;
        this.f17198f = i2;
        this.f17199g = z2;
    }
}
