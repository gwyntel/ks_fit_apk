package com.aliyun.alink.linksdk.tmp.listener;

import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;

/* loaded from: classes2.dex */
public interface IDevListener {
    void onFail(Object obj, ErrorInfo errorInfo);

    void onSuccess(Object obj, OutputParams outputParams);
}
