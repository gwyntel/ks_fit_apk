package com.aliyun.alink.linksdk.tmp.device.request;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.aliyun.alink.linksdk.cmp.connect.apigw.ApiGatewayRequest;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.tmp.connect.mix.MTopAndApiGMixRequest;
import com.aliyun.alink.linksdk.tmp.connect.mix.a;
import com.aliyun.alink.linksdk.tmp.connect.mix.c;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.aliyun.alink.linksdk.tmp.error.UnknownError;
import com.aliyun.alink.linksdk.tmp.listener.IProcessListener;
import com.aliyun.alink.linksdk.tmp.service.DevService;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class GateWayRequest implements Serializable {

    @JSONField(deserialize = false, serialize = false)
    private static final String TAG = "[Tmp]GateWayRequest";

    @JSONField(deserialize = false, serialize = false)
    public transient String path;

    @JSONField(deserialize = false, serialize = false)
    public transient Class<? extends GateWayResponse> responseClass;

    @JSONField(deserialize = false, serialize = false)
    public transient String version;

    public GateWayRequest(Class<? extends GateWayResponse> cls) {
        this.path = "unknown";
        this.version = "1.0.0";
        this.responseClass = cls;
    }

    public static void sendApiGateWayRequest(GateWayRequest gateWayRequest, IConnectSendListener iConnectSendListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        JSONObject jSONObject;
        JSONObject jSONObject2 = null;
        try {
            jSONObject = (JSONObject) JSON.toJSON(gateWayRequest);
        } catch (Exception e2) {
            ALog.e(TAG, "sendApiGateWayMsg toJSON e:" + e2.toString());
            jSONObject = null;
        }
        try {
            String str = gateWayRequest.path;
            String str2 = gateWayRequest.version;
            if (jSONObject != null && !jSONObject.isEmpty()) {
                jSONObject2 = jSONObject;
            }
            ApiGatewayRequest apiGatewayRequestBuild = ApiGatewayRequest.build(str, str2, jSONObject2);
            apiGatewayRequestBuild.scheme = Scheme.HTTPS;
            c.a(new MTopAndApiGMixRequest(apiGatewayRequestBuild), new a(iConnectSendListener));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void sendRequest(GateWayRequest gateWayRequest, final IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        sendRequest(gateWayRequest, new IGateWayRequestListener<GateWayRequest, GateWayResponse>() { // from class: com.aliyun.alink.linksdk.tmp.device.request.GateWayRequest.1
            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onFail(GateWayRequest gateWayRequest2, AError aError) {
                IProcessListener iProcessListener2 = iProcessListener;
                if (iProcessListener2 != null) {
                    iProcessListener2.onFail(new ErrorInfo(aError));
                }
            }

            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onSuccess(GateWayRequest gateWayRequest2, GateWayResponse gateWayResponse) {
                IProcessListener iProcessListener2 = iProcessListener;
                if (iProcessListener2 != null) {
                    iProcessListener2.onSuccess(JSON.toJSONString(gateWayResponse));
                }
            }
        });
    }

    @NonNull
    public String toString() {
        return "path:" + this.path;
    }

    public void sendRequest(GateWayRequest gateWayRequest, final DevService.ServiceListenerEx serviceListenerEx) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        sendRequest(gateWayRequest, new IGateWayRequestListener<GateWayRequest, GateWayResponse>() { // from class: com.aliyun.alink.linksdk.tmp.device.request.GateWayRequest.2
            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onFail(GateWayRequest gateWayRequest2, AError aError) {
                DevService.ServiceListenerEx serviceListenerEx2 = serviceListenerEx;
                if (serviceListenerEx2 != null) {
                    serviceListenerEx2.onComplete(false, JSON.toJSONString(aError));
                }
            }

            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onSuccess(GateWayRequest gateWayRequest2, GateWayResponse gateWayResponse) {
                DevService.ServiceListenerEx serviceListenerEx2 = serviceListenerEx;
                if (serviceListenerEx2 != null) {
                    serviceListenerEx2.onComplete(true, JSON.toJSONString(gateWayResponse));
                }
            }
        });
    }

    public void sendRequest(final GateWayRequest gateWayRequest, final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (gateWayRequest == null) {
            ALog.e(TAG, "sendRequest presenterRequest or listener empty");
        } else {
            sendApiGateWayRequest(gateWayRequest, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.tmp.device.request.GateWayRequest.3
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(GateWayRequest.TAG, "sendPresenterMsg onFailure request:" + aRequest + " error:" + aError);
                    IPanelCallback iPanelCallback2 = iPanelCallback;
                    if (iPanelCallback2 != null) {
                        iPanelCallback2.onComplete(false, aError);
                    } else {
                        ALog.e(GateWayRequest.TAG, "sendApiGateWayRequest listener empty");
                    }
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    Object obj;
                    GateWayResponse gateWayResponse;
                    ALog.d(GateWayRequest.TAG, "sendPresenterMsg onResponse request:" + aRequest + " response:" + aResponse);
                    if (aResponse == null || (obj = aResponse.data) == null) {
                        IPanelCallback iPanelCallback2 = iPanelCallback;
                        if (iPanelCallback2 != null) {
                            iPanelCallback2.onComplete(false, new UnknownError());
                            return;
                        } else {
                            ALog.e(GateWayRequest.TAG, "sendApiGateWayRequest listener empty");
                            return;
                        }
                    }
                    try {
                        gateWayResponse = (GateWayResponse) JSON.parseObject(obj.toString(), gateWayRequest.responseClass);
                    } catch (Exception e2) {
                        ALog.e(GateWayRequest.TAG, "sendPresenterMsg parseObject e:" + e2.toString());
                        gateWayResponse = null;
                    }
                    if (gateWayResponse == null) {
                        ALog.e(GateWayRequest.TAG, "sendApiGateWayMsg presenterResponse empty");
                        AError aError = new AError();
                        aError.setCode(4201);
                        onFailure(aRequest, aError);
                        return;
                    }
                    if (gateWayResponse.code == 200) {
                        IPanelCallback iPanelCallback3 = iPanelCallback;
                        if (iPanelCallback3 != null) {
                            iPanelCallback3.onComplete(true, JSON.toJSONString(gateWayResponse));
                            return;
                        } else {
                            ALog.e(GateWayRequest.TAG, "sendApiGateWayRequest listener empty");
                            return;
                        }
                    }
                    ALog.e(GateWayRequest.TAG, "sendApiGateWayMsg code not success code:" + gateWayResponse.code);
                    AError aError2 = new AError();
                    aError2.setCode(gateWayResponse.code);
                    aError2.setMsg(TextUtils.isEmpty(gateWayResponse.localizedMsg) ? gateWayResponse.message : gateWayResponse.localizedMsg);
                    onFailure(aRequest, aError2);
                }
            });
        }
    }

    public GateWayRequest() {
        this(GateWayResponse.class);
    }

    public void sendRequest(final GateWayRequest gateWayRequest, final IGateWayRequestListener iGateWayRequestListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (gateWayRequest == null) {
            ALog.e(TAG, "sendRequest presenterRequest empty");
        } else {
            sendApiGateWayRequest(gateWayRequest, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.tmp.device.request.GateWayRequest.4
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(GateWayRequest.TAG, "sendPresenterMsg onFailure request:" + aRequest + " error:" + aError);
                    IGateWayRequestListener iGateWayRequestListener2 = iGateWayRequestListener;
                    if (iGateWayRequestListener2 != null) {
                        iGateWayRequestListener2.onFail(gateWayRequest, aError);
                    } else {
                        ALog.e(GateWayRequest.TAG, "sendApiGateWayRequest listener empty");
                    }
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    Object obj;
                    GateWayResponse gateWayResponse;
                    ALog.d(GateWayRequest.TAG, "sendPresenterMsg onResponse request:" + aRequest + " response:" + aResponse);
                    if (aResponse == null || (obj = aResponse.data) == null) {
                        if (aResponse == null || aResponse.data == null) {
                            if (iGateWayRequestListener == null) {
                                ALog.e(GateWayRequest.TAG, "sendApiGateWayRequest listener empty");
                                return;
                            }
                            AError aError = new AError();
                            aError.setCode(4201);
                            iGateWayRequestListener.onFail(gateWayRequest, aError);
                            return;
                        }
                        return;
                    }
                    try {
                        gateWayResponse = (GateWayResponse) JSON.parseObject(obj.toString(), gateWayRequest.responseClass);
                    } catch (Exception e2) {
                        ALog.e(GateWayRequest.TAG, "sendPresenterMsg parseObject e:" + e2.toString());
                        gateWayResponse = null;
                    }
                    if (gateWayResponse == null) {
                        ALog.e(GateWayRequest.TAG, "sendApiGateWayMsg presenterResponse empty");
                        AError aError2 = new AError();
                        aError2.setCode(4201);
                        onFailure(aRequest, aError2);
                        return;
                    }
                    if (gateWayResponse.code == 200) {
                        IGateWayRequestListener iGateWayRequestListener2 = iGateWayRequestListener;
                        if (iGateWayRequestListener2 != null) {
                            iGateWayRequestListener2.onSuccess(gateWayRequest, gateWayResponse);
                            return;
                        } else {
                            ALog.e(GateWayRequest.TAG, "sendApiGateWayRequest listener empty");
                            return;
                        }
                    }
                    ALog.e(GateWayRequest.TAG, "sendApiGateWayMsg code not success code:" + gateWayResponse.code);
                    AError aError3 = new AError();
                    aError3.setCode(gateWayResponse.code);
                    aError3.setMsg(TextUtils.isEmpty(gateWayResponse.localizedMsg) ? gateWayResponse.message : gateWayResponse.localizedMsg);
                    onFailure(aRequest, aError3);
                }
            });
        }
    }
}
