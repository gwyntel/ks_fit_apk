package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class p7 {

    /* renamed from: c, reason: collision with root package name */
    private static final int[] f17668c = {1, 1, 2};

    /* renamed from: a, reason: collision with root package name */
    private final n7 f17669a = new n7();

    /* renamed from: b, reason: collision with root package name */
    private final o7 f17670b = new o7();

    p7() {
    }

    s6 a(int i2, r rVar, int i3) throws a {
        int[] iArrA = q7.a(rVar, i3, false, f17668c);
        try {
            return this.f17670b.a(i2, rVar, iArrA);
        } catch (a unused) {
            return this.f17669a.a(i2, rVar, iArrA);
        }
    }
}
