package com.aliyun.alink.linksdk.channel.core.persistent.event;

/* loaded from: classes2.dex */
public interface IConnectionStateListener {
    void onConnectFail(String str);

    void onConnected();

    void onDisconnect();
}
