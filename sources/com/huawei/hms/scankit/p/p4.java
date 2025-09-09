package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public abstract class p4 {

    /* renamed from: a, reason: collision with root package name */
    private final int f17664a;

    /* renamed from: b, reason: collision with root package name */
    private final int f17665b;

    protected p4(int i2, int i3) {
        this.f17664a = i2;
        this.f17665b = i3;
    }

    public final int a() {
        return this.f17665b;
    }

    public abstract p4 a(int i2, int i3, int i4, int i5);

    public abstract byte[] a(int i2, byte[] bArr);

    public abstract byte[] b();

    public final int c() {
        return this.f17664a;
    }

    public final String toString() {
        int i2 = this.f17664a;
        byte[] bArrA = new byte[i2];
        StringBuilder sb = new StringBuilder(this.f17665b * (i2 + 1));
        for (int i3 = 0; i3 < this.f17665b; i3++) {
            bArrA = a(i3, bArrA);
            for (int i4 = 0; i4 < this.f17664a; i4++) {
                int i5 = bArrA[i4] & 255;
                sb.append(i5 < 64 ? '#' : i5 < 128 ? '+' : i5 < 192 ? '.' : ' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
