package com.aliyun.alink.business.devicecenter.channel.http;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes2.dex */
public class TransitoryClient {

    /* renamed from: a, reason: collision with root package name */
    public static IoTAPIClient f10321a;

    /* renamed from: b, reason: collision with root package name */
    public CopyOnWriteArraySet<String> f10322b;

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final TransitoryClient f10323a = new TransitoryClient();
    }

    public static TransitoryClient getInstance() {
        return SingletonHolder.f10323a;
    }

    public RetryTransitoryClient asynRequest(IoTRequest ioTRequest, IoTCallback ioTCallback) {
        ALog.d("TransitoryClient", "asynRequest request=" + requestToStr(ioTRequest) + ", callback=" + ioTCallback);
        if (f10321a == null) {
            f10321a = new IoTAPIClientFactory().getClient();
        }
        if (ioTRequest != null && !TextUtils.isEmpty(ioTRequest.getPath()) && !TextUtils.isEmpty(ioTRequest.getAPIVersion())) {
            RetryTransitoryClient retryTransitoryClient = new RetryTransitoryClient(!this.f10322b.contains(ioTRequest.getPath()));
            retryTransitoryClient.send(f10321a, ioTRequest, ioTCallback);
            return retryTransitoryClient;
        }
        ALog.w("TransitoryClient", "asynRequest request info error. requst=" + requestToStr(ioTRequest));
        if (ioTCallback != null) {
            ioTCallback.onFailure(null, new IllegalArgumentException("RequestParamsError"));
        }
        return null;
    }

    public String getTraceId(IoTResponse ioTResponse) {
        if (ioTResponse != null) {
            return ioTResponse.getId();
        }
        return null;
    }

    public void registerIgnoreRetryApiPath(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.f10322b.add(str);
    }

    public String requestToStr(IoTRequest ioTRequest) {
        if (ioTRequest == null) {
            return null;
        }
        return "[schema=" + ioTRequest.getScheme() + ",host=" + ioTRequest.getHost() + ",path=" + ioTRequest.getPath() + ",apiVersion=" + ioTRequest.getAPIVersion() + ",method=" + ioTRequest.getMethod() + ",authType=" + ioTRequest.getAuthType() + ",params=" + ioTRequest.getParams() + "]";
    }

    public String responseToStr(IoTResponse ioTResponse) {
        if (ioTResponse == null) {
            return null;
        }
        return "[requestId=" + ioTResponse.getCode() + ",code=" + ioTResponse.getCode() + ",data=" + ioTResponse.getData() + ",message=" + ioTResponse.getMessage() + "]";
    }

    public TransitoryClient() {
        this.f10322b = new CopyOnWriteArraySet<>();
    }
}
