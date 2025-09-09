package org.android.spdy;

/* loaded from: classes5.dex */
public enum SpdyVersion {
    SPDY2(2),
    SPDY3(3),
    SPDY3DOT1(4);

    private int version;

    SpdyVersion(int i2) {
        this.version = i2;
    }

    int getInt() {
        return this.version;
    }
}
