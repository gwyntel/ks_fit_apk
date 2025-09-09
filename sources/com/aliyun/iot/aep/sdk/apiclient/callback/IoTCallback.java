package com.aliyun.iot.aep.sdk.apiclient.callback;

import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;

/* loaded from: classes3.dex */
public interface IoTCallback {
    void onFailure(IoTRequest ioTRequest, Exception exc);

    void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse);
}
