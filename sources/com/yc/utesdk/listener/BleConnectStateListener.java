package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface BleConnectStateListener {
    public static final int STATE_CONNECTED = 2;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_DISCONNECTED = 0;

    void onConnectException(Exception exc);

    void onConnecteStateChange(int i2);
}
