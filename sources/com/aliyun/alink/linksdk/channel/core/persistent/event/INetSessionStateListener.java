package com.aliyun.alink.linksdk.channel.core.persistent.event;

/* loaded from: classes2.dex */
public interface INetSessionStateListener {
    void onNeedLogin();

    void onSessionEffective();

    void onSessionInvalid();
}
