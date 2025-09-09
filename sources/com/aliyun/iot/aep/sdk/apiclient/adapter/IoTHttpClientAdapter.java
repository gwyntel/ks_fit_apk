package com.aliyun.iot.aep.sdk.apiclient.adapter;

import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestWrapper;

/* loaded from: classes3.dex */
public interface IoTHttpClientAdapter {
    void send(IoTRequestWrapper ioTRequestWrapper, IoTCallback ioTCallback);

    void setDefaultHost(String str);
}
