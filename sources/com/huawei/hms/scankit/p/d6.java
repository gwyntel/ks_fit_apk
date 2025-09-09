package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class d6 {

    /* renamed from: a, reason: collision with root package name */
    private final float f17110a;

    /* renamed from: b, reason: collision with root package name */
    private final float f17111b;

    /* renamed from: c, reason: collision with root package name */
    private final float f17112c;

    /* renamed from: d, reason: collision with root package name */
    private final float f17113d;

    /* renamed from: e, reason: collision with root package name */
    private final float f17114e;

    /* renamed from: f, reason: collision with root package name */
    private final float f17115f;

    /* renamed from: g, reason: collision with root package name */
    private final float f17116g;

    /* renamed from: h, reason: collision with root package name */
    private final float f17117h;

    /* renamed from: i, reason: collision with root package name */
    private final float f17118i;

    /* renamed from: j, reason: collision with root package name */
    private float f17119j;

    /* renamed from: k, reason: collision with root package name */
    private float f17120k;

    /* renamed from: l, reason: collision with root package name */
    private float f17121l;

    /* renamed from: m, reason: collision with root package name */
    private float f17122m;

    /* renamed from: n, reason: collision with root package name */
    private float f17123n;

    /* renamed from: o, reason: collision with root package name */
    private float f17124o;

    /* renamed from: p, reason: collision with root package name */
    private float f17125p;

    /* renamed from: q, reason: collision with root package name */
    private float f17126q;

    /* renamed from: r, reason: collision with root package name */
    private float f17127r;

    /* renamed from: s, reason: collision with root package name */
    private float f17128s = 0.0f;

    /* renamed from: t, reason: collision with root package name */
    private float f17129t = 0.0f;

    /* renamed from: u, reason: collision with root package name */
    private float f17130u = 0.0f;

    /* renamed from: v, reason: collision with root package name */
    private float f17131v = 0.0f;

    /* renamed from: w, reason: collision with root package name */
    private float f17132w = 0.0f;

    public d6(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        this.f17110a = f2;
        this.f17111b = f5;
        this.f17112c = f8;
        this.f17113d = f3;
        this.f17114e = f6;
        this.f17115f = f9;
        this.f17116g = f4;
        this.f17117h = f7;
        this.f17118i = f10;
        this.f17119j = f2;
        this.f17120k = f3;
        this.f17121l = f4;
        this.f17122m = f5;
        this.f17123n = f6;
        this.f17124o = f7;
        this.f17125p = f8;
        this.f17126q = f9;
        this.f17127r = f10;
    }

    public void a(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15) {
        this.f17119j = f2;
        this.f17120k = f3;
        this.f17121l = f4;
        this.f17122m = f5;
        this.f17123n = f6;
        this.f17124o = f7;
        this.f17125p = f8;
        this.f17126q = f9;
        this.f17127r = f10;
        this.f17128s = f11;
        this.f17129t = f12;
        this.f17130u = f13;
        this.f17131v = f14;
        this.f17132w = f15;
    }

    public void b(float[] fArr) {
        int length = fArr.length;
        for (int i2 = 0; i2 < length; i2 += 2) {
            float f2 = fArr[i2];
            int i3 = i2 + 1;
            float f3 = fArr[i3];
            float f4 = (this.f17129t * f2) + (this.f17130u * f3) + (this.f17131v * f2 * f2) + (this.f17132w * f3 * f3) + 1.0f;
            fArr[i2] = (((((this.f17119j * f2) + (this.f17120k * f3)) + ((this.f17121l * f2) * f2)) + ((this.f17122m * f3) * f3)) + this.f17123n) / f4;
            fArr[i3] = (((((this.f17124o * f2) + (this.f17125p * f3)) + ((this.f17126q * f2) * f2)) + ((this.f17127r * f3) * f3)) + this.f17128s) / f4;
        }
    }

    public static d6 b(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        float f10 = ((f2 - f4) + f6) - f8;
        float f11 = ((f3 - f5) + f7) - f9;
        if (f10 == 0.0f && f11 == 0.0f) {
            return new d6(f4 - f2, f6 - f4, f2, f5 - f3, f7 - f5, f3, 0.0f, 0.0f, 1.0f);
        }
        float f12 = f4 - f6;
        float f13 = f8 - f6;
        float f14 = f5 - f7;
        float f15 = f9 - f7;
        float f16 = (f12 * f15) - (f13 * f14);
        float f17 = ((f15 * f10) - (f13 * f11)) / f16;
        float f18 = ((f12 * f11) - (f10 * f14)) / f16;
        return new d6((f17 * f4) + (f4 - f2), (f18 * f8) + (f8 - f2), f2, (f5 - f3) + (f17 * f5), (f9 - f3) + (f18 * f9), f3, f17, f18, 1.0f);
    }

    public static d6 a(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, float f17) {
        return b(f10, f11, f12, f13, f14, f15, f16, f17).a(a(f2, f3, f4, f5, f6, f7, f8, f9));
    }

    public void a(float[] fArr) {
        int length = fArr.length;
        float f2 = this.f17110a;
        float f3 = this.f17111b;
        float f4 = this.f17112c;
        float f5 = this.f17113d;
        float f6 = this.f17114e;
        float f7 = this.f17115f;
        float f8 = this.f17116g;
        float f9 = this.f17117h;
        float f10 = this.f17118i;
        for (int i2 = 0; i2 < length; i2 += 2) {
            float f11 = fArr[i2];
            int i3 = i2 + 1;
            float f12 = fArr[i3];
            float f13 = (f4 * f11) + (f7 * f12) + f10;
            fArr[i2] = (((f2 * f11) + (f5 * f12)) + f8) / f13;
            fArr[i3] = (((f11 * f3) + (f12 * f6)) + f9) / f13;
        }
    }

    public static d6 a(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        return b(f2, f3, f4, f5, f6, f7, f8, f9).a();
    }

    d6 a() {
        float f2 = this.f17114e;
        float f3 = this.f17118i;
        float f4 = this.f17115f;
        float f5 = this.f17117h;
        float f6 = (f2 * f3) - (f4 * f5);
        float f7 = this.f17116g;
        float f8 = this.f17113d;
        float f9 = (f4 * f7) - (f8 * f3);
        float f10 = (f8 * f5) - (f2 * f7);
        float f11 = this.f17112c;
        float f12 = this.f17111b;
        float f13 = (f11 * f5) - (f12 * f3);
        float f14 = this.f17110a;
        return new d6(f6, f9, f10, f13, (f3 * f14) - (f11 * f7), (f7 * f12) - (f5 * f14), (f12 * f4) - (f11 * f2), (f11 * f8) - (f4 * f14), (f14 * f2) - (f12 * f8));
    }

    d6 a(d6 d6Var) {
        float f2 = this.f17110a;
        float f3 = d6Var.f17110a;
        float f4 = this.f17113d;
        float f5 = d6Var.f17111b;
        float f6 = this.f17116g;
        float f7 = d6Var.f17112c;
        float f8 = (f2 * f3) + (f4 * f5) + (f6 * f7);
        float f9 = d6Var.f17113d;
        float f10 = d6Var.f17114e;
        float f11 = d6Var.f17115f;
        float f12 = (f2 * f9) + (f4 * f10) + (f6 * f11);
        float f13 = d6Var.f17116g;
        float f14 = d6Var.f17117h;
        float f15 = d6Var.f17118i;
        float f16 = (f2 * f13) + (f4 * f14) + (f6 * f15);
        float f17 = this.f17111b;
        float f18 = this.f17114e;
        float f19 = this.f17117h;
        float f20 = (f17 * f3) + (f18 * f5) + (f19 * f7);
        float f21 = (f17 * f9) + (f18 * f10) + (f19 * f11);
        float f22 = (f19 * f15) + (f17 * f13) + (f18 * f14);
        float f23 = this.f17112c;
        float f24 = this.f17115f;
        float f25 = (f3 * f23) + (f5 * f24);
        float f26 = this.f17118i;
        return new d6(f8, f12, f16, f20, f21, f22, (f7 * f26) + f25, (f9 * f23) + (f10 * f24) + (f11 * f26), (f23 * f13) + (f24 * f14) + (f26 * f15));
    }
}
