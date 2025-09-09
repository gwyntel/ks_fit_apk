package com.alibaba.cloudapi.sdk.model;

/* loaded from: classes2.dex */
public interface ApiCallback {
    void onFailure(ApiRequest apiRequest, Exception exc);

    void onResponse(ApiRequest apiRequest, ApiResponse apiResponse);
}
