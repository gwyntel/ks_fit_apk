package org.android.spdy;

/* loaded from: classes5.dex */
enum SslMod {
    SLIGHT_SLL_NOT_ENCRYT(0),
    SLIGHT_SSL_0_RTT(1);

    private int code;

    SslMod(int i2) {
        this.code = i2;
    }

    int getint() {
        return this.code;
    }
}
