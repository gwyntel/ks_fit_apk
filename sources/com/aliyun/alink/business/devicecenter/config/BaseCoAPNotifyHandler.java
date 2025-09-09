package com.aliyun.alink.business.devicecenter.config;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.channel.coap.CoAPClient;
import com.aliyun.alink.business.devicecenter.channel.coap.request.CoapRequestPayload;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.ThreadPool;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPConstant;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPContext;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPRequest;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPResponse;
import com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPResHandler;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class BaseCoAPNotifyHandler<T, K> implements IAlcsCoAPResHandler {
    public T configCallback;
    public AlcsCoAPResponse response = null;
    public CoapRequestPayload<K> payload = null;

    public BaseCoAPNotifyHandler(T t2) {
        this.configCallback = t2;
    }

    public void ack(AlcsCoAPRequest alcsCoAPRequest) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AlcsCoAPResponse alcsCoAPResponseCreateResponse = AlcsCoAPResponse.createResponse(alcsCoAPRequest, AlcsCoAPConstant.ResponseCode._UNKNOWN_SUCCESS_CODE);
        this.response = alcsCoAPResponseCreateResponse;
        alcsCoAPResponseCreateResponse.setToken(alcsCoAPRequest.getToken());
        this.response.setMID(alcsCoAPRequest.getMID());
        CoapRequestPayload<K> coapRequestPayload = this.payload;
        String str = (coapRequestPayload == null || TextUtils.isEmpty(coapRequestPayload.id)) ? "1" : this.payload.id;
        this.response.setPayload("{\"id\":" + str + ", \"code\":200, \"data\":{}}");
        ALog.i("BaseCoAPNotifyHandler", "ack token=" + alcsCoAPRequest.getTokenString() + ",msgId=" + alcsCoAPRequest.getMID());
        ThreadPool.submit(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.config.BaseCoAPNotifyHandler.1
            @Override // java.lang.Runnable
            public void run() {
                CoAPClient.getInstance().sendResponse(BaseCoAPNotifyHandler.this.response);
            }
        });
    }

    @Override // com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPResHandler
    public void onRecRequest(AlcsCoAPContext alcsCoAPContext, AlcsCoAPRequest alcsCoAPRequest) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (alcsCoAPRequest == null) {
            return;
        }
        try {
            parsePayload(alcsCoAPRequest);
        } catch (Exception e2) {
            ALog.w("BaseCoAPNotifyHandler", "parsePayloadException= " + e2);
            this.payload = null;
        }
        CoapRequestPayload<K> coapRequestPayload = this.payload;
        if (coapRequestPayload == null || TextUtils.isEmpty(coapRequestPayload.method)) {
            ack(alcsCoAPRequest);
        }
    }

    public void parsePayload(AlcsCoAPRequest alcsCoAPRequest) {
    }
}
