package com.aliyun.iot.aep.sdk.apiclient;

/* loaded from: classes3.dex */
public class IoTAPIClientFactory {
    public IoTAPIClient getClient() {
        return IoTAPIClientImpl.getInstance();
    }
}
