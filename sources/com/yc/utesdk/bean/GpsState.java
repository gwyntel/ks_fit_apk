package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class GpsState {
    public static final int STATE_INVALIDITY = 0;
    public static final int STATE_VALIDITY = 1;
    private int state;

    public GpsState(int i2) {
        this.state = i2;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int i2) {
        this.state = i2;
    }
}
