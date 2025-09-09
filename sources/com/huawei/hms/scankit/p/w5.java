package com.huawei.hms.scankit.p;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public class w5 {

    /* renamed from: b, reason: collision with root package name */
    private float f17958b;

    /* renamed from: c, reason: collision with root package name */
    private float f17959c;

    /* renamed from: h, reason: collision with root package name */
    private long f17964h;

    /* renamed from: i, reason: collision with root package name */
    private float f17965i;

    /* renamed from: j, reason: collision with root package name */
    private float f17966j;

    /* renamed from: l, reason: collision with root package name */
    private float f17968l;

    /* renamed from: m, reason: collision with root package name */
    private float f17969m;

    /* renamed from: n, reason: collision with root package name */
    private int f17970n;

    /* renamed from: o, reason: collision with root package name */
    private int f17971o;

    /* renamed from: p, reason: collision with root package name */
    private long f17972p;

    /* renamed from: r, reason: collision with root package name */
    private f4 f17974r;

    /* renamed from: a, reason: collision with root package name */
    private float f17957a = 1.0f;

    /* renamed from: d, reason: collision with root package name */
    private int f17960d = 255;

    /* renamed from: e, reason: collision with root package name */
    private float f17961e = 0.0f;

    /* renamed from: f, reason: collision with root package name */
    private float f17962f = 0.0f;

    /* renamed from: q, reason: collision with root package name */
    private int f17973q = 0;

    /* renamed from: k, reason: collision with root package name */
    private float f17967k = 1.0f;

    /* renamed from: g, reason: collision with root package name */
    private int f17963g = 255;

    public w5(Bitmap bitmap) {
        if (bitmap != null) {
            this.f17970n = bitmap.getWidth() / 2;
            this.f17971o = bitmap.getHeight() / 2;
        }
    }

    public void a(long j2, float f2, float f3, long j3, @NonNull f4 f4Var) {
        float f4 = f2 - this.f17970n;
        this.f17968l = f4;
        float f5 = f3 - this.f17971o;
        this.f17969m = f5;
        this.f17965i = f4;
        this.f17966j = f5;
        this.f17972p = j2;
        this.f17964h = j3;
        this.f17974r = f4Var;
    }

    public void b(float f2) {
        this.f17967k = f2;
    }

    public float c() {
        return this.f17965i;
    }

    public float d() {
        return this.f17966j;
    }

    public int e() {
        return this.f17960d;
    }

    public float f() {
        return this.f17967k;
    }

    public int b() {
        return this.f17973q;
    }

    public void b(int i2) {
        this.f17973q = i2;
    }

    public boolean a(long j2) {
        long j3 = j2 - this.f17964h;
        if (j3 > this.f17972p) {
            return false;
        }
        float f2 = j3;
        float f3 = j3 * j3;
        this.f17965i = this.f17968l + (this.f17961e * f2) + (this.f17958b * f3);
        this.f17966j = this.f17969m + (this.f17962f * f2) + (this.f17959c * f3);
        this.f17974r.a(this, j3);
        return true;
    }

    public int a() {
        return this.f17963g;
    }

    public void a(int i2) {
        this.f17963g = i2;
    }

    public void a(float f2) {
        this.f17957a = f2;
    }
}
