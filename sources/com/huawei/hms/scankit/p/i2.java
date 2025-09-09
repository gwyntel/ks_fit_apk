package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public class i2 implements Comparable<i2> {

    /* renamed from: a, reason: collision with root package name */
    private float f17358a;

    /* renamed from: b, reason: collision with root package name */
    private float f17359b;

    /* renamed from: c, reason: collision with root package name */
    private float f17360c;

    /* renamed from: d, reason: collision with root package name */
    private float f17361d;

    /* renamed from: e, reason: collision with root package name */
    private float f17362e;

    /* renamed from: f, reason: collision with root package name */
    private float f17363f;

    /* renamed from: g, reason: collision with root package name */
    private float f17364g;

    /* renamed from: h, reason: collision with root package name */
    private float f17365h;

    /* renamed from: i, reason: collision with root package name */
    private float f17366i;

    /* renamed from: j, reason: collision with root package name */
    private float f17367j;

    /* renamed from: k, reason: collision with root package name */
    private float f17368k;

    /* renamed from: l, reason: collision with root package name */
    public p f17369l;

    /* renamed from: m, reason: collision with root package name */
    public float[] f17370m = new float[5];

    /* renamed from: n, reason: collision with root package name */
    public float f17371n = 0.0f;

    /* renamed from: o, reason: collision with root package name */
    public float f17372o = 0.0f;

    /* renamed from: p, reason: collision with root package name */
    public int f17373p = 0;

    /* renamed from: q, reason: collision with root package name */
    public int f17374q = 0;

    /* renamed from: r, reason: collision with root package name */
    public int f17375r = 0;

    /* renamed from: s, reason: collision with root package name */
    public int f17376s = 0;

    /* renamed from: t, reason: collision with root package name */
    public int f17377t = 0;

    /* renamed from: u, reason: collision with root package name */
    public int f17378u = 0;

    /* renamed from: v, reason: collision with root package name */
    public float f17379v = 0.0f;

    public i2(boolean z2, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11) {
        this.f17358a = f2;
        this.f17359b = f3;
        this.f17360c = f4;
        this.f17361d = f5;
        this.f17362e = f6;
        this.f17363f = f7;
        this.f17368k = f11;
        if (!z2) {
            this.f17366i = f10;
            this.f17367j = f9;
        } else {
            this.f17364g = f8;
            this.f17365h = f9;
            this.f17367j = f10;
        }
    }

    private float a(float f2, int i2, int i3) {
        float f3 = i2;
        if (f2 <= f3) {
            f2 = f3;
        }
        float f4 = i3 - 1;
        return f2 < f4 ? f2 : f4;
    }

    public void b(float f2, float f3) {
        this.f17358a = 0.0f;
        this.f17359b = 0.0f;
        this.f17360c = f2;
        this.f17361d = f3;
        this.f17362e = f2 / 2.0f;
        this.f17363f = f3 / 2.0f;
        this.f17364g = f2;
        this.f17365h = f3;
        this.f17367j = 0.0f;
    }

    public float c() {
        return this.f17361d;
    }

    public float d() {
        return this.f17358a;
    }

    public float e() {
        return this.f17359b;
    }

    public float f() {
        return this.f17360c;
    }

    public float g() {
        return this.f17368k;
    }

    public float h() {
        return this.f17366i;
    }

    public float i() {
        return this.f17367j;
    }

    public float j() {
        return this.f17362e;
    }

    public float k() {
        return this.f17363f;
    }

    public float l() {
        return this.f17365h;
    }

    public float m() {
        return this.f17364g;
    }

    public float n() {
        return this.f17379v;
    }

    public void a(int i2, int i3, float f2, float f3) {
        float f4 = this.f17358a + f2;
        this.f17358a = f4;
        float f5 = this.f17359b + f3;
        this.f17359b = f5;
        if (f4 < 0.0f) {
            this.f17360c += f4;
        }
        if (f5 < 0.0f) {
            this.f17361d += f5;
        }
        this.f17362e += f2;
        this.f17363f += f3;
        this.f17358a = a(f4, 0, i2);
        this.f17362e = a(this.f17362e, 0, i2);
        this.f17359b = a(this.f17359b, 0, i3);
        this.f17363f = a(this.f17363f, 0, i3);
        float f6 = this.f17358a;
        float f7 = i2 - f2;
        if (this.f17360c + f6 >= f7) {
            this.f17360c = (f7 - 1.0f) - f6;
        }
        float f8 = this.f17359b;
        float f9 = i3 - f3;
        if (this.f17361d + f8 >= f9) {
            this.f17361d = (f9 - 1.0f) - f8;
        }
    }

    public float b() {
        return this.f17371n;
    }

    public void a(float f2, float f3) {
        this.f17358a += f2;
        this.f17359b += f3;
        this.f17362e += f2;
        this.f17363f += f3;
    }

    public float a() {
        return this.f17372o;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(i2 i2Var) {
        return Float.compare((-i2Var.g()) + i2Var.h(), (-g()) + h());
    }
}
