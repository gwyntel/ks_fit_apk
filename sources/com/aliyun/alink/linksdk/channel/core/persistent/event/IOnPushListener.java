package com.aliyun.alink.linksdk.channel.core.persistent.event;

/* loaded from: classes2.dex */
public interface IOnPushListener {
    void onCommand(String str, byte[] bArr);

    boolean shouldHandle(String str);
}
