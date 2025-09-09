package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class w {

    /* renamed from: a, reason: collision with root package name */
    private final byte[] f17902a;

    /* renamed from: b, reason: collision with root package name */
    private int f17903b;

    /* renamed from: c, reason: collision with root package name */
    private int f17904c;

    public w(byte[] bArr) {
        this.f17902a = bArr;
    }

    public int a(int i2) {
        if (i2 < 1 || i2 > 32 || i2 > a()) {
            throw new IllegalArgumentException(String.valueOf(i2));
        }
        int i3 = this.f17904c;
        int i4 = 0;
        if (i3 > 0) {
            int i5 = 8 - i3;
            int i6 = i2 < i5 ? i2 : i5;
            int i7 = i5 - i6;
            int i8 = w7.a(this.f17902a, this.f17903b) ? (((255 >> (8 - i6)) << i7) & this.f17902a[this.f17903b]) >> i7 : 0;
            i2 -= i6;
            int i9 = this.f17904c + i6;
            this.f17904c = i9;
            if (i9 == 8) {
                this.f17904c = 0;
                this.f17903b++;
            }
            i4 = i8;
        }
        if (i2 > 0) {
            while (i2 >= 8) {
                if (w7.a(this.f17902a, this.f17903b)) {
                    i4 = (i4 << 8) | (this.f17902a[this.f17903b] & 255);
                }
                this.f17903b++;
                i2 -= 8;
            }
            if (i2 > 0) {
                int i10 = 8 - i2;
                int i11 = (255 >> i10) << i10;
                if (w7.a(this.f17902a, this.f17903b)) {
                    i4 = ((i11 & this.f17902a[this.f17903b]) >> i10) | (i4 << i2);
                }
                this.f17904c += i2;
            }
        }
        return i4;
    }

    public int b() {
        return this.f17904c;
    }

    public int c() {
        return this.f17903b;
    }

    public int a() {
        return ((this.f17902a.length - this.f17903b) * 8) - this.f17904c;
    }
}
