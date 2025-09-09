package com.aliyun.alink.apiclient;

/* loaded from: classes2.dex */
public interface IoTApiClient {
    void init(InitializeConfig initializeConfig);

    void send(CommonRequest commonRequest, IoTCallback ioTCallback);
}
