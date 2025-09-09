package com.squareup.okhttp.internal.framed;

import java.util.Arrays;

/* loaded from: classes4.dex */
public final class Settings {
    private int persistValue;
    private int persisted;
    private int set;
    private final int[] values = new int[10];

    void a() {
        this.persisted = 0;
        this.persistValue = 0;
        this.set = 0;
        Arrays.fill(this.values, 0);
    }

    int b(int i2) {
        int i3 = g(i2) ? 2 : 0;
        return j(i2) ? i3 | 1 : i3;
    }

    int c(int i2) {
        return this.values[i2];
    }

    int d() {
        if ((this.set & 2) != 0) {
            return this.values[1];
        }
        return -1;
    }

    int e(int i2) {
        return (this.set & 128) != 0 ? this.values[7] : i2;
    }

    int f(int i2) {
        return (this.set & 32) != 0 ? this.values[5] : i2;
    }

    boolean g(int i2) {
        return ((1 << i2) & this.persisted) != 0;
    }

    boolean h(int i2) {
        return ((1 << i2) & this.set) != 0;
    }

    void i(Settings settings) {
        for (int i2 = 0; i2 < 10; i2++) {
            if (settings.h(i2)) {
                k(i2, settings.b(i2), settings.c(i2));
            }
        }
    }

    boolean j(int i2) {
        return ((1 << i2) & this.persistValue) != 0;
    }

    Settings k(int i2, int i3, int i4) {
        int[] iArr = this.values;
        if (i2 >= iArr.length) {
            return this;
        }
        int i5 = 1 << i2;
        this.set |= i5;
        if ((i3 & 1) != 0) {
            this.persistValue |= i5;
        } else {
            this.persistValue &= ~i5;
        }
        if ((i3 & 2) != 0) {
            this.persisted |= i5;
        } else {
            this.persisted &= ~i5;
        }
        iArr[i2] = i4;
        return this;
    }

    int l() {
        return Integer.bitCount(this.set);
    }
}
