package com.huawei.hms.scankit.p;

import com.yc.utesdk.ble.close.KeyType;

/* loaded from: classes4.dex */
public final class o3 {

    /* renamed from: h, reason: collision with root package name */
    public static final o3 f17629h = new o3(4201, 4096, 1);

    /* renamed from: i, reason: collision with root package name */
    public static final o3 f17630i = new o3(1033, 1024, 1);

    /* renamed from: j, reason: collision with root package name */
    public static final o3 f17631j;

    /* renamed from: k, reason: collision with root package name */
    public static final o3 f17632k;

    /* renamed from: l, reason: collision with root package name */
    public static final o3 f17633l;

    /* renamed from: m, reason: collision with root package name */
    public static final o3 f17634m;

    /* renamed from: n, reason: collision with root package name */
    public static final o3 f17635n;

    /* renamed from: o, reason: collision with root package name */
    public static final o3 f17636o;

    /* renamed from: a, reason: collision with root package name */
    private final int[] f17637a;

    /* renamed from: b, reason: collision with root package name */
    private final int[] f17638b;

    /* renamed from: c, reason: collision with root package name */
    private final p3 f17639c;

    /* renamed from: d, reason: collision with root package name */
    private final p3 f17640d;

    /* renamed from: e, reason: collision with root package name */
    private final int f17641e;

    /* renamed from: f, reason: collision with root package name */
    private final int f17642f;

    /* renamed from: g, reason: collision with root package name */
    private final int f17643g;

    static {
        o3 o3Var = new o3(67, 64, 1);
        f17631j = o3Var;
        f17632k = new o3(19, 16, 1);
        f17633l = new o3(KeyType.QUERY_IMAGE_WATCH_FACE_PARAMS, 256, 0);
        o3 o3Var2 = new o3(301, 256, 1);
        f17634m = o3Var2;
        f17635n = o3Var2;
        f17636o = o3Var;
    }

    public o3(int i2, int i3, int i4) {
        this.f17642f = i2;
        this.f17641e = i3;
        this.f17643g = i4;
        this.f17637a = new int[i3];
        this.f17638b = new int[i3];
        int i5 = 1;
        for (int i6 = 0; i6 < i3; i6++) {
            this.f17637a[i6] = i5;
            i5 *= 2;
            if (i5 >= i3) {
                i5 = (i5 ^ i2) & (i3 - 1);
            }
        }
        for (int i7 = 0; i7 < i3 - 1; i7++) {
            this.f17638b[this.f17637a[i7]] = i7;
        }
        this.f17639c = new p3(this, new int[]{0});
        this.f17640d = new p3(this, new int[]{1});
    }

    static int a(int i2, int i3) {
        return i2 ^ i3;
    }

    p3 b() {
        return this.f17640d;
    }

    int c(int i2) {
        if (i2 != 0) {
            return this.f17638b[i2];
        }
        throw new IllegalArgumentException();
    }

    p3 d() {
        return this.f17639c;
    }

    public String toString() {
        return "GF(0x" + Integer.toHexString(this.f17642f) + ',' + this.f17641e + ')';
    }

    int a(int i2) {
        if (w7.a(this.f17637a, i2)) {
            return this.f17637a[i2];
        }
        return -1;
    }

    p3 b(int i2, int i3) {
        if (i2 < 0) {
            throw new IllegalArgumentException();
        }
        if (i3 == 0) {
            return this.f17639c;
        }
        int[] iArr = new int[i2 + 1];
        iArr[0] = i3;
        return new p3(this, iArr);
    }

    int c(int i2, int i3) {
        if (i2 == 0 || i3 == 0) {
            return 0;
        }
        int[] iArr = this.f17637a;
        int[] iArr2 = this.f17638b;
        return iArr[(iArr2[i2] + iArr2[i3]) % (this.f17641e - 1)];
    }

    public int a() {
        return this.f17643g;
    }

    public int c() {
        return this.f17641e;
    }

    int b(int i2) {
        if (i2 != 0) {
            return this.f17637a[(this.f17641e - this.f17638b[i2]) - 1];
        }
        throw new ArithmeticException();
    }
}
