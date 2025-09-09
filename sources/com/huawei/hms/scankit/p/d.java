package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class d extends u6 {

    /* renamed from: e, reason: collision with root package name */
    private final float f17099e;

    d(float f2, float f3, float f4) {
        super(f2, f3);
        this.f17099e = f4;
    }

    boolean b(float f2, float f3, float f4) {
        if (Math.abs(f3 - c()) > f2 || Math.abs(f4 - b()) > f2) {
            return false;
        }
        float fAbs = Math.abs(f2 - this.f17099e);
        return fAbs <= 1.0f || fAbs <= this.f17099e;
    }

    d c(float f2, float f3, float f4) {
        return new d((b() + f3) / 2.0f, (c() + f2) / 2.0f, (this.f17099e + f4) / 2.0f);
    }
}
