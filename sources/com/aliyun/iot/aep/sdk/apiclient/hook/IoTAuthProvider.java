package com.aliyun.iot.aep.sdk.apiclient.hook;

import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestPayload;

/* loaded from: classes3.dex */
public abstract class IoTAuthProvider implements IoTAPIHook {
    @Override // com.aliyun.iot.aep.sdk.apiclient.hook.IoTAPIHook
    public void onInterceptFailure(IoTRequest ioTRequest, IoTRequestPayload ioTRequestPayload, Exception exc, IoTCallback ioTCallback) {
        ioTCallback.onFailure(ioTRequest, exc);
    }
}
