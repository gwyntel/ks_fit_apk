package com.aliyun.alink.linksdk.cmp.core.listener;

import com.aliyun.alink.linksdk.tools.AError;

/* loaded from: classes2.dex */
public interface IBaseListener {
    void onFailure(AError aError);

    void onSuccess();
}
