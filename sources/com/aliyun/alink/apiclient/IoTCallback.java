package com.aliyun.alink.apiclient;

/* loaded from: classes2.dex */
public interface IoTCallback {
    void onFailure(CommonRequest commonRequest, Exception exc);

    void onResponse(CommonRequest commonRequest, CommonResponse commonResponse);
}
