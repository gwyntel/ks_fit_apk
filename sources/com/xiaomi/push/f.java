package com.xiaomi.push;

/* loaded from: classes4.dex */
public final class f {

    /* renamed from: a, reason: collision with root package name */
    static final int f23737a = a(1, 3);

    /* renamed from: b, reason: collision with root package name */
    static final int f23738b = a(1, 4);

    /* renamed from: c, reason: collision with root package name */
    static final int f23739c = a(2, 0);

    /* renamed from: d, reason: collision with root package name */
    static final int f23740d = a(3, 2);

    static int a(int i2) {
        return i2 & 7;
    }

    public static int b(int i2) {
        return i2 >>> 3;
    }

    static int a(int i2, int i3) {
        return (i2 << 3) | i3;
    }
}
