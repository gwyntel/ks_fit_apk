package com.aliyun.alink.linksdk.alcs.api.client;

import com.aliyun.alink.linksdk.alcs.api.utils.ErrorInfo;

/* loaded from: classes2.dex */
public interface IDeviceHandler {
    void onFail(Object obj, ErrorInfo errorInfo);

    void onSuccess(Object obj);
}
