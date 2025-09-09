package com.umeng.analytics.pro;

/* loaded from: classes4.dex */
public class bp extends bw {

    /* renamed from: a, reason: collision with root package name */
    public static final int f21524a = 0;

    /* renamed from: b, reason: collision with root package name */
    public static final int f21525b = 1;

    /* renamed from: c, reason: collision with root package name */
    public static final int f21526c = 2;

    /* renamed from: d, reason: collision with root package name */
    public static final int f21527d = 3;

    /* renamed from: e, reason: collision with root package name */
    public static final int f21528e = 4;

    /* renamed from: f, reason: collision with root package name */
    public static final int f21529f = 5;

    /* renamed from: g, reason: collision with root package name */
    public static final int f21530g = 6;

    /* renamed from: h, reason: collision with root package name */
    public static final int f21531h = 7;

    /* renamed from: j, reason: collision with root package name */
    private static final cu f21532j = new cu("TApplicationException");

    /* renamed from: k, reason: collision with root package name */
    private static final ck f21533k = new ck("message", (byte) 11, 1);

    /* renamed from: l, reason: collision with root package name */
    private static final ck f21534l = new ck("type", (byte) 8, 2);

    /* renamed from: m, reason: collision with root package name */
    private static final long f21535m = 1;

    /* renamed from: i, reason: collision with root package name */
    protected int f21536i;

    public bp() {
        this.f21536i = 0;
    }

    public int a() {
        return this.f21536i;
    }

    public void b(cp cpVar) throws bw {
        cpVar.a(f21532j);
        if (getMessage() != null) {
            cpVar.a(f21533k);
            cpVar.a(getMessage());
            cpVar.c();
        }
        cpVar.a(f21534l);
        cpVar.a(this.f21536i);
        cpVar.c();
        cpVar.d();
        cpVar.b();
    }

    public static bp a(cp cpVar) throws bw {
        cpVar.j();
        String strZ = null;
        int iW = 0;
        while (true) {
            ck ckVarL = cpVar.l();
            byte b2 = ckVarL.f21619b;
            if (b2 == 0) {
                cpVar.k();
                return new bp(iW, strZ);
            }
            short s2 = ckVarL.f21620c;
            if (s2 != 1) {
                if (s2 != 2) {
                    cs.a(cpVar, b2);
                } else if (b2 == 8) {
                    iW = cpVar.w();
                } else {
                    cs.a(cpVar, b2);
                }
            } else if (b2 == 11) {
                strZ = cpVar.z();
            } else {
                cs.a(cpVar, b2);
            }
            cpVar.m();
        }
    }

    public bp(int i2) {
        this.f21536i = i2;
    }

    public bp(int i2, String str) {
        super(str);
        this.f21536i = i2;
    }

    public bp(String str) {
        super(str);
        this.f21536i = 0;
    }
}
