package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class l {

    /* renamed from: a, reason: collision with root package name */
    private final byte[] f17485a;

    /* renamed from: b, reason: collision with root package name */
    private int f17486b = 0;

    l(int i2) {
        this.f17485a = new byte[i2];
    }

    private void a(int i2, boolean z2) {
        if (!w7.a(this.f17485a, i2)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.f17485a[i2] = z2 ? (byte) 1 : (byte) 0;
    }

    void a(boolean z2, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = this.f17486b;
            this.f17486b = i4 + 1;
            a(i4, z2);
        }
    }

    byte[] a(int i2) {
        int length = this.f17485a.length * i2;
        byte[] bArr = new byte[length];
        for (int i3 = 0; i3 < length; i3++) {
            bArr[i3] = this.f17485a[i3 / i2];
        }
        return bArr;
    }
}
