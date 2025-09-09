package com.alibaba.ailabs.iot.aisbase.channel;

/* loaded from: classes2.dex */
public enum LayerState {
    NONE,
    BT_OPEN,
    BT_CLOSED,
    UNBIND,
    BINDING,
    BOUND,
    CONNECTING,
    CONNECTED,
    AUTH_FAILED,
    AUTH_SUCCESSFUL,
    DISCONNECTING,
    DISCONNECTED,
    A2DP_CONNECTING,
    A2DP_CONNECTED,
    A2DP_DISCONNECTING,
    A2DP_DISCONNECTED;

    public static LayerState parserFromIntValue(int i2) {
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? NONE : DISCONNECTING : CONNECTED : CONNECTING : DISCONNECTED;
    }
}
