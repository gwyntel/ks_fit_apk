package com.aliyun.alink.business.devicecenter.api.discovery;

import com.aliyun.alink.business.devicecenter.base.DCErrorCode;

/* loaded from: classes2.dex */
public interface IOnTokenGetListerner {
    void onFail(DCErrorCode dCErrorCode);

    void onSuccess(GetTokenResult getTokenResult);
}
