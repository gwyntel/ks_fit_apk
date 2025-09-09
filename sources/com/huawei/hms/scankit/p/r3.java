package com.huawei.hms.scankit.p;

import java.util.Stack;

/* loaded from: classes4.dex */
public class r3 {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f17714a = true;

    /* renamed from: b, reason: collision with root package name */
    public static boolean f17715b = false;

    /* renamed from: c, reason: collision with root package name */
    public static boolean f17716c = false;

    /* renamed from: d, reason: collision with root package name */
    public static boolean f17717d = false;

    /* renamed from: e, reason: collision with root package name */
    public static boolean f17718e = false;

    /* renamed from: f, reason: collision with root package name */
    public static boolean f17719f = true;

    /* renamed from: g, reason: collision with root package name */
    public static boolean f17720g = false;

    /* renamed from: h, reason: collision with root package name */
    public static boolean f17721h = false;

    /* renamed from: i, reason: collision with root package name */
    public static float f17722i = -1.0f;

    /* renamed from: j, reason: collision with root package name */
    public static int f17723j = 0;

    /* renamed from: k, reason: collision with root package name */
    public static int f17724k = 0;

    /* renamed from: l, reason: collision with root package name */
    public static boolean f17725l = false;

    /* renamed from: m, reason: collision with root package name */
    public static boolean f17726m = false;

    /* renamed from: n, reason: collision with root package name */
    public static boolean f17727n = false;

    /* renamed from: o, reason: collision with root package name */
    public static boolean f17728o = false;

    /* renamed from: p, reason: collision with root package name */
    public static boolean f17729p = false;

    /* renamed from: q, reason: collision with root package name */
    public static boolean f17730q = false;

    /* renamed from: r, reason: collision with root package name */
    public static boolean f17731r = false;

    /* renamed from: s, reason: collision with root package name */
    public static boolean f17732s = false;

    /* renamed from: t, reason: collision with root package name */
    public static boolean f17733t = false;

    /* renamed from: u, reason: collision with root package name */
    public static boolean f17734u = false;

    /* renamed from: v, reason: collision with root package name */
    public static boolean[] f17735v = new boolean[8];

    /* renamed from: w, reason: collision with root package name */
    public static Stack<Integer> f17736w = new Stack<>();

    /* renamed from: x, reason: collision with root package name */
    public static boolean f17737x = true;

    /* renamed from: y, reason: collision with root package name */
    public static float[] f17738y = new float[4];

    /* renamed from: z, reason: collision with root package name */
    public static int f17739z = 0;
    public static boolean A = false;

    public static void a() {
        f17721h = false;
        f17722i = -1.0f;
        f17723j = 0;
        f17729p = false;
        f17730q = false;
        f17731r = false;
        f17733t = false;
        f17726m = false;
        f17727n = false;
        f17728o = false;
        f17732s = false;
        f17735v = new boolean[8];
        f17736w = new Stack<>();
        f17737x = true;
        f17718e = false;
        f17717d = false;
    }

    public static void a(int i2) {
        if (i2 % 2 == 1) {
            f17729p = true;
        }
        if (i2 % 3 == 2) {
            f17730q = true;
        }
        if (i2 % 5 == 4) {
            f17731r = true;
        }
    }

    public static void a(x6 x6Var) {
        a();
        boolean z2 = x6Var.f17994e;
        f17716c = z2;
        f17737x = x6Var.f17996g;
        if (!z2) {
            a(x6Var.f17995f);
        } else {
            a(1);
        }
    }
}
