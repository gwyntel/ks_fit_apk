package anet.channel.strategy;

import java.io.Serializable;

/* loaded from: classes2.dex */
class ConnHistoryItem implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    byte f6946a = 0;

    /* renamed from: b, reason: collision with root package name */
    long f6947b = 0;

    /* renamed from: c, reason: collision with root package name */
    long f6948c = 0;

    ConnHistoryItem() {
    }

    void a(boolean z2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - (z2 ? this.f6947b : this.f6948c) > 10000) {
            this.f6946a = (byte) ((this.f6946a << 1) | (!z2 ? 1 : 0));
            if (z2) {
                this.f6947b = jCurrentTimeMillis;
            } else {
                this.f6948c = jCurrentTimeMillis;
            }
        }
    }

    boolean b() {
        return (this.f6946a & 1) == 1;
    }

    boolean c() {
        return a() >= 3 && System.currentTimeMillis() - this.f6948c <= 300000;
    }

    boolean d() {
        long j2 = this.f6947b;
        long j3 = this.f6948c;
        if (j2 <= j3) {
            j2 = j3;
        }
        return j2 != 0 && System.currentTimeMillis() - j2 > 86400000;
    }

    int a() {
        int i2 = 0;
        for (int i3 = this.f6946a & 255; i3 > 0; i3 >>= 1) {
            i2 += i3 & 1;
        }
        return i2;
    }
}
