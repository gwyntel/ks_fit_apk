package com.aliyun.alink.apiclient;

/* loaded from: classes2.dex */
public class IoTAPIClientFactory {
    public IoTApiClient getClient() {
        return IoTAPIClientImpl.getInstance();
    }
}
