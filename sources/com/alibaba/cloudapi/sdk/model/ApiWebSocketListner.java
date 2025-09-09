package com.alibaba.cloudapi.sdk.model;

/* loaded from: classes2.dex */
public interface ApiWebSocketListner {
    void onFailure(Throwable th, ApiResponse apiResponse);

    void onNotify(String str);
}
