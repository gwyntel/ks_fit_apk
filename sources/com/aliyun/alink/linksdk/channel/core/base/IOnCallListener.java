package com.aliyun.alink.linksdk.channel.core.base;

/* loaded from: classes2.dex */
public interface IOnCallListener {
    boolean needUISafety();

    void onFailed(ARequest aRequest, AError aError);

    void onSuccess(ARequest aRequest, AResponse aResponse);
}
