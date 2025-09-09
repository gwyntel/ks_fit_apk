package com.huawei.hms.scankit.p;

import java.lang.reflect.Array;

/* loaded from: classes4.dex */
public final class j {

    /* renamed from: a, reason: collision with root package name */
    private final l[] f17397a;

    /* renamed from: b, reason: collision with root package name */
    private int f17398b;

    /* renamed from: c, reason: collision with root package name */
    private final int f17399c;

    /* renamed from: d, reason: collision with root package name */
    private final int f17400d;

    j(int i2, int i3) {
        this.f17397a = new l[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            this.f17397a[i4] = new l(((i3 + 4) * 17) + 1);
        }
        this.f17400d = i3 * 17;
        this.f17399c = i2;
        this.f17398b = -1;
    }

    l a() {
        int i2 = this.f17398b;
        if (i2 >= 0) {
            l[] lVarArr = this.f17397a;
            if (i2 < lVarArr.length) {
                return lVarArr[i2];
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    void b() {
        this.f17398b++;
    }

    public byte[][] a(int i2, int i3) {
        int i4 = this.f17399c * i3;
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, i4, this.f17400d * i2);
        for (int i5 = 0; i5 < i4; i5++) {
            bArr[(i4 - i5) - 1] = this.f17397a[i5 / i3].a(i2);
        }
        return bArr;
    }
}
