package com.aliyun.alink.business.devicecenter.config;

import com.aliyun.alink.business.devicecenter.base.DCErrorCode;

/* loaded from: classes2.dex */
public interface IConfigExtraCallback {
    void onError(DCErrorCode dCErrorCode);

    void onSuccess();
}
