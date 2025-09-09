package com.aliyun.alink.apiclient.biz;

/* loaded from: classes2.dex */
public interface IApiClientBizCallback {
    void onFail(Exception exc);

    void onResponse(IApiClientResponse iApiClientResponse);
}
