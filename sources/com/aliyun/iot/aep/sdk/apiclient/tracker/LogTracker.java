package com.aliyun.iot.aep.sdk.apiclient.tracker;

import android.text.TextUtils;
import anet.channel.util.HttpConstant;
import com.alibaba.fastjson.JSON;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientImpl;
import com.aliyun.iot.aep.sdk.apiclient.c;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestWrapper;
import org.apache.commons.io.IOUtils;

/* loaded from: classes3.dex */
public class LogTracker implements Tracker {
    public static String a(IoTRequest ioTRequest) {
        StringBuilder sb = new StringBuilder("Request:");
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        sb.append("id:");
        sb.append(ioTRequest.getId());
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        sb.append("url:");
        sb.append(ioTRequest.getScheme());
        sb.append(HttpConstant.SCHEME_SPLIT);
        sb.append(TextUtils.isEmpty(ioTRequest.getHost()) ? IoTAPIClientImpl.getInstance().getDefaultHost() : "");
        sb.append(ioTRequest.getPath());
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        sb.append("apiVersion:");
        sb.append(ioTRequest.getAPIVersion());
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        sb.append("params:");
        sb.append(ioTRequest.getParams() != null ? JSON.toJSONString(ioTRequest.getParams()) : "");
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        return sb.toString();
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker
    public void onFailure(IoTRequest ioTRequest, Exception exc) {
        c.a("APIGatewayTracker", "onFailure:\r\n" + a(ioTRequest) + "ERROR-MESSAGE:" + exc.getMessage());
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker
    public void onRawFailure(IoTRequestWrapper ioTRequestWrapper, Exception exc) {
        c.a("APIGatewayTracker", "onRawFailure:\r\n" + a(ioTRequestWrapper) + "ERROR-MESSAGE:" + exc.getMessage());
        exc.printStackTrace();
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker
    public void onRawResponse(IoTRequestWrapper ioTRequestWrapper, IoTResponse ioTResponse) {
        c.a("APIGatewayTracker", "onRawResponse:\r\n" + a(ioTRequestWrapper) + a(ioTResponse));
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker
    public void onRealSend(IoTRequestWrapper ioTRequestWrapper) {
        c.a("APIGatewayTracker", "onRealSend:\r\n" + a(ioTRequestWrapper));
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker
    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
        c.a("APIGatewayTracker", "onResponse:\r\n" + a(ioTRequest) + a(ioTResponse));
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker
    public void onSend(IoTRequest ioTRequest) {
        c.b("APIGatewayTracker", "onSend:\r\n" + a(ioTRequest));
    }

    public static String a(IoTRequestWrapper ioTRequestWrapper) {
        IoTRequest ioTRequest = ioTRequestWrapper.request;
        StringBuilder sb = new StringBuilder("Request:");
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        sb.append("id:");
        sb.append(ioTRequestWrapper.payload.getId());
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        sb.append("url:");
        sb.append(ioTRequest.getScheme());
        sb.append(HttpConstant.SCHEME_SPLIT);
        sb.append(TextUtils.isEmpty(ioTRequestWrapper.request.getHost()) ? IoTAPIClientImpl.getInstance().getDefaultHost() : "");
        sb.append(ioTRequest.getPath());
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        sb.append("apiVersion:");
        sb.append(ioTRequest.getAPIVersion());
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        sb.append("params:");
        sb.append(ioTRequest.getParams() != null ? JSON.toJSONString(ioTRequest.getParams()) : "");
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        sb.append("payload:");
        sb.append(JSON.toJSONString(ioTRequestWrapper.payload));
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        return sb.toString();
    }

    public static String a(IoTResponse ioTResponse) {
        StringBuilder sb = new StringBuilder("Response:");
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        sb.append("id:");
        sb.append(ioTResponse.getId());
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        sb.append("code:");
        sb.append(ioTResponse.getCode());
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        sb.append("message:");
        sb.append(ioTResponse.getMessage());
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        sb.append("localizedMsg:");
        sb.append(ioTResponse.getLocalizedMsg());
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        sb.append("data:");
        sb.append(ioTResponse.getData() == null ? "" : ioTResponse.getData().toString());
        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
        return sb.toString();
    }
}
