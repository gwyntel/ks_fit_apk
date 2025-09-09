package com.aliyun.iot.aep.sdk.apiclient.hook;

import android.text.TextUtils;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestPayload;
import com.umeng.analytics.pro.bc;

/* loaded from: classes3.dex */
public class IoTGlobalAPIHook implements IoTAPIHook {

    /* renamed from: a, reason: collision with root package name */
    public String f11643a = "zh-CN";

    @Override // com.aliyun.iot.aep.sdk.apiclient.hook.IoTAPIHook
    public void onInterceptFailure(IoTRequest ioTRequest, IoTRequestPayload ioTRequestPayload, Exception exc, IoTCallback ioTCallback) {
        ioTCallback.onFailure(ioTRequest, exc);
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.hook.IoTAPIHook
    public void onInterceptResponse(IoTRequest ioTRequest, IoTRequestPayload ioTRequestPayload, IoTResponse ioTResponse, IoTCallback ioTCallback) {
        ioTCallback.onResponse(ioTRequest, ioTResponse);
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.hook.IoTAPIHook
    public void onInterceptSend(IoTRequest ioTRequest, IoTRequestPayload ioTRequestPayload, IoTRequestPayloadCallback ioTRequestPayloadCallback) {
        ioTRequestPayload.getRequest().put(bc.N, this.f11643a);
        ioTRequestPayloadCallback.onIoTRequestPayloadReady();
    }

    public void setLanguage(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.f11643a = str;
    }
}
