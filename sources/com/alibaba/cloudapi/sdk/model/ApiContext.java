package com.alibaba.cloudapi.sdk.model;

import java.util.Date;

/* loaded from: classes2.dex */
public class ApiContext {
    ApiCallback callback;
    ApiRequest request;
    long startTime = new Date().getTime();

    public ApiContext(ApiCallback apiCallback, ApiRequest apiRequest) {
        this.callback = apiCallback;
        this.request = apiRequest;
    }

    public ApiCallback getCallback() {
        return this.callback;
    }

    public ApiRequest getRequest() {
        return this.request;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setCallback(ApiCallback apiCallback) {
        this.callback = apiCallback;
    }

    public void setRequest(ApiRequest apiRequest) {
        this.request = apiRequest;
    }

    public void setStartTime(long j2) {
        this.startTime = j2;
    }
}
