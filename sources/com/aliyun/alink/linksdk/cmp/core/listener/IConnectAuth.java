package com.aliyun.alink.linksdk.cmp.core.listener;

import com.aliyun.alink.linksdk.tools.AError;

/* loaded from: classes2.dex */
public interface IConnectAuth<T> {
    void onAuth(T t2);

    void onPrepareAuthFail(AError aError);
}
