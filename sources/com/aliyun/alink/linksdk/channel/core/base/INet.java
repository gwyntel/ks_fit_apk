package com.aliyun.alink.linksdk.channel.core.base;

/* loaded from: classes2.dex */
public interface INet {
    ASend asyncSend(ARequest aRequest, IOnCallListener iOnCallListener);

    void retry(ASend aSend);
}
