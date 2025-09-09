package com.meizu.cloud.pushsdk.e.h;

/* loaded from: classes4.dex */
final class j {

    /* renamed from: a, reason: collision with root package name */
    final byte[] f19507a;

    /* renamed from: b, reason: collision with root package name */
    int f19508b;

    /* renamed from: c, reason: collision with root package name */
    int f19509c;

    /* renamed from: d, reason: collision with root package name */
    boolean f19510d;

    /* renamed from: e, reason: collision with root package name */
    final boolean f19511e;

    /* renamed from: f, reason: collision with root package name */
    j f19512f;

    /* renamed from: g, reason: collision with root package name */
    j f19513g;

    j() {
        this.f19507a = new byte[2048];
        this.f19511e = true;
        this.f19510d = false;
    }

    public j a(int i2) {
        if (i2 <= 0 || i2 > this.f19509c - this.f19508b) {
            throw new IllegalArgumentException();
        }
        j jVar = new j(this);
        jVar.f19509c = jVar.f19508b + i2;
        this.f19508b += i2;
        this.f19513g.a(jVar);
        return jVar;
    }

    public j b() {
        j jVar = this.f19512f;
        j jVar2 = jVar != this ? jVar : null;
        j jVar3 = this.f19513g;
        jVar3.f19512f = jVar;
        this.f19512f.f19513g = jVar3;
        this.f19512f = null;
        this.f19513g = null;
        return jVar2;
    }

    j(j jVar) {
        this(jVar.f19507a, jVar.f19508b, jVar.f19509c);
    }

    public j a(j jVar) {
        jVar.f19513g = this;
        jVar.f19512f = this.f19512f;
        this.f19512f.f19513g = jVar;
        this.f19512f = jVar;
        return jVar;
    }

    j(byte[] bArr, int i2, int i3) {
        this.f19507a = bArr;
        this.f19508b = i2;
        this.f19509c = i3;
        this.f19511e = false;
        this.f19510d = true;
    }

    public void a() {
        j jVar = this.f19513g;
        if (jVar == this) {
            throw new IllegalStateException();
        }
        if (jVar.f19511e) {
            int i2 = this.f19509c - this.f19508b;
            if (i2 > (2048 - jVar.f19509c) + (jVar.f19510d ? 0 : jVar.f19508b)) {
                return;
            }
            a(jVar, i2);
            b();
            k.a(this);
        }
    }

    public void a(j jVar, int i2) {
        if (!jVar.f19511e) {
            throw new IllegalArgumentException();
        }
        int i3 = jVar.f19509c;
        int i4 = i3 + i2;
        if (i4 > 2048) {
            if (jVar.f19510d) {
                throw new IllegalArgumentException();
            }
            int i5 = jVar.f19508b;
            if (i4 - i5 > 2048) {
                throw new IllegalArgumentException();
            }
            byte[] bArr = jVar.f19507a;
            System.arraycopy(bArr, i5, bArr, 0, i3 - i5);
            jVar.f19509c -= jVar.f19508b;
            jVar.f19508b = 0;
        }
        System.arraycopy(this.f19507a, this.f19508b, jVar.f19507a, jVar.f19509c, i2);
        jVar.f19509c += i2;
        this.f19508b += i2;
    }
}
