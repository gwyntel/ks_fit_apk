package com.huawei.hms.scankit.p;

import java.lang.reflect.Array;
import java.util.Arrays;

/* loaded from: classes4.dex */
public final class c0 {

    /* renamed from: a, reason: collision with root package name */
    private final byte[][] f17045a;

    /* renamed from: b, reason: collision with root package name */
    private final int f17046b;

    /* renamed from: c, reason: collision with root package name */
    private final int f17047c;

    public c0(int i2, int i3) {
        this.f17045a = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, i3, i2);
        this.f17046b = i2;
        this.f17047c = i3;
    }

    public byte a(int i2, int i3) {
        if (w7.a(this.f17045a, i3) && w7.a(this.f17045a[i3], i2)) {
            return this.f17045a[i3][i2];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int b() {
        return this.f17047c;
    }

    public int c() {
        return this.f17046b;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder((this.f17046b * 2 * this.f17047c) + 2);
        for (int i2 = 0; i2 < this.f17047c; i2++) {
            byte[] bArr = this.f17045a[i2];
            for (int i3 = 0; i3 < this.f17046b; i3++) {
                byte b2 = bArr[i3];
                if (b2 == 0) {
                    sb.append(" 0");
                } else if (b2 != 1) {
                    sb.append("  ");
                } else {
                    sb.append(" 1");
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public byte[][] a() {
        return this.f17045a;
    }

    public void a(int i2, int i3, int i4) {
        if (w7.a(this.f17045a, i3) && w7.a(this.f17045a[i3], i2)) {
            this.f17045a[i3][i2] = (byte) i4;
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void a(int i2, int i3, boolean z2) {
        if (w7.a(this.f17045a, i3) && w7.a(this.f17045a[i3], i2)) {
            this.f17045a[i3][i2] = z2 ? (byte) 1 : (byte) 0;
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void a(byte b2) {
        for (byte[] bArr : this.f17045a) {
            Arrays.fill(bArr, b2);
        }
    }
}
