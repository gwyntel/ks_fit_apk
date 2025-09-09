package com.huawei.hms.scankit.p;

import com.google.zxing.pdf417.PDF417Common;

/* loaded from: classes4.dex */
public final class w4 {

    /* renamed from: f, reason: collision with root package name */
    public static final w4 f17951f = new w4(PDF417Common.NUMBER_OF_CODEWORDS, 3);

    /* renamed from: a, reason: collision with root package name */
    private final int[] f17952a;

    /* renamed from: b, reason: collision with root package name */
    private final int[] f17953b;

    /* renamed from: c, reason: collision with root package name */
    private final x4 f17954c;

    /* renamed from: d, reason: collision with root package name */
    private final x4 f17955d;

    /* renamed from: e, reason: collision with root package name */
    private final int f17956e;

    private w4(int i2, int i3) {
        this.f17956e = i2;
        this.f17952a = new int[i2];
        this.f17953b = new int[i2];
        int i4 = 1;
        for (int i5 = 0; i5 < i2; i5++) {
            this.f17952a[i5] = i4;
            i4 = (i4 * i3) % i2;
        }
        for (int i6 = 0; i6 < i2 - 1; i6++) {
            this.f17953b[this.f17952a[i6]] = i6;
        }
        this.f17954c = new x4(this, new int[]{0});
        this.f17955d = new x4(this, new int[]{1});
    }

    x4 a() {
        return this.f17955d;
    }

    x4 b(int i2, int i3) {
        if (i2 < 0) {
            throw new IllegalArgumentException();
        }
        if (i3 == 0) {
            return this.f17954c;
        }
        int[] iArr = new int[i2 + 1];
        iArr[0] = i3;
        return new x4(this, iArr);
    }

    x4 c() {
        return this.f17954c;
    }

    int d(int i2, int i3) {
        int i4 = this.f17956e;
        return ((i2 + i4) - i3) % i4;
    }

    int a(int i2, int i3) {
        return (i2 + i3) % this.f17956e;
    }

    int c(int i2) {
        if (i2 != 0) {
            return this.f17953b[i2];
        }
        throw new IllegalArgumentException();
    }

    int a(int i2) {
        return this.f17952a[i2];
    }

    int c(int i2, int i3) {
        if (i2 <= 0 || i3 <= 0) {
            return 0;
        }
        int[] iArr = this.f17952a;
        int[] iArr2 = this.f17953b;
        return iArr[(iArr2[i2] + iArr2[i3]) % (this.f17956e - 1)];
    }

    int b(int i2) {
        if (i2 != 0) {
            return this.f17952a[(this.f17956e - this.f17953b[i2]) - 1];
        }
        throw new ArithmeticException();
    }

    int b() {
        return this.f17956e;
    }
}
