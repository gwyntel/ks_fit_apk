package com.alibaba.cloudapi.sdk.model;

/* loaded from: classes2.dex */
public class WebSocketClientBuilderParams extends BaseClientInitialParam {
    ApiWebSocketListner apiWebSocketListner;
    int requestExpiredTime = 10000;
    int callbackThreadPoolCount = 1;

    public ApiWebSocketListner getApiWebSocketListner() {
        return this.apiWebSocketListner;
    }

    public int getCallbackThreadPoolCount() {
        return this.callbackThreadPoolCount;
    }

    public int getRequestExpiredTime() {
        return this.requestExpiredTime;
    }

    public void setApiWebSocketListner(ApiWebSocketListner apiWebSocketListner) {
        this.apiWebSocketListner = apiWebSocketListner;
    }

    public void setCallbackThreadPoolCount(int i2) {
        this.callbackThreadPoolCount = i2;
    }

    public void setRequestExpiredTime(int i2) {
        this.requestExpiredTime = i2;
    }
}
