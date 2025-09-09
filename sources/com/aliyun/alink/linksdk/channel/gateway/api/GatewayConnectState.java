package com.aliyun.alink.linksdk.channel.gateway.api;

import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;

/* loaded from: classes2.dex */
public enum GatewayConnectState {
    CONNECTED,
    DISCONNECTED,
    CONNECTING,
    CONNECTFAIL;

    public static GatewayConnectState toGatewayConnectState(ConnectState connectState) {
        return connectState == ConnectState.CONNECTED ? CONNECTED : connectState == ConnectState.DISCONNECTED ? DISCONNECTED : connectState == ConnectState.CONNECTING ? CONNECTING : CONNECTFAIL;
    }
}
