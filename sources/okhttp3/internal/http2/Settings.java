package okhttp3.internal.http2;

import java.util.Arrays;

/* loaded from: classes5.dex */
public final class Settings {
    private int set;
    private final int[] values = new int[10];

    void a() {
        this.set = 0;
        Arrays.fill(this.values, 0);
    }

    int b(int i2) {
        return this.values[i2];
    }

    int c() {
        if ((this.set & 2) != 0) {
            return this.values[1];
        }
        return -1;
    }

    int d() {
        if ((this.set & 128) != 0) {
            return this.values[7];
        }
        return 65535;
    }

    int e(int i2) {
        return (this.set & 16) != 0 ? this.values[4] : i2;
    }

    int f(int i2) {
        return (this.set & 32) != 0 ? this.values[5] : i2;
    }

    boolean g(int i2) {
        return ((1 << i2) & this.set) != 0;
    }

    void h(Settings settings) {
        for (int i2 = 0; i2 < 10; i2++) {
            if (settings.g(i2)) {
                i(i2, settings.b(i2));
            }
        }
    }

    Settings i(int i2, int i3) {
        if (i2 >= 0) {
            int[] iArr = this.values;
            if (i2 < iArr.length) {
                this.set = (1 << i2) | this.set;
                iArr[i2] = i3;
            }
        }
        return this;
    }

    int j() {
        return Integer.bitCount(this.set);
    }
}
