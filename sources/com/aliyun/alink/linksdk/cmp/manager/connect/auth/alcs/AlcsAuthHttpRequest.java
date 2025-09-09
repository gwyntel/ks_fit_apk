package com.aliyun.alink.linksdk.cmp.manager.connect.auth.alcs;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.cmp.connect.apigw.ApiGatewayRequest;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttPublishRequest;
import com.aliyun.alink.linksdk.cmp.core.base.AConnect;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.base.CmpError;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.manager.connect.ConnectManager;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class AlcsAuthHttpRequest {
    private static final String PATH_CLIENT = "/alcs/device/accessInfo/get";
    private static final String TAG = "AlcsAuthHttpRequest";
    private static final String TOPIC_SERVER = "/thing/lan/prefix/get";

    public interface IAlcsAuthCallback {
        void onFailed(AError aError);

        void onSuccess(Object obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void clientFailCallback(String str, IAlcsAuthCallback iAlcsAuthCallback) {
        CmpError cmpErrorALCS_REQUEST_CLIENT_AUTH_FAIL = CmpError.ALCS_REQUEST_CLIENT_AUTH_FAIL();
        cmpErrorALCS_REQUEST_CLIENT_AUTH_FAIL.setSubMsg(str);
        iAlcsAuthCallback.onFailed(cmpErrorALCS_REQUEST_CLIENT_AUTH_FAIL);
    }

    public static void requestClientInfo(String str, final IAlcsAuthCallback iAlcsAuthCallback) {
        ALog.d(TAG, "requestClientInfo");
        if (iAlcsAuthCallback == null) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            iAlcsAuthCallback.onFailed(CmpError.PARAMS_ERROR());
            return;
        }
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(str);
            AConnect apiGatewayConnect = ConnectManager.getInstance().getApiGatewayConnect();
            ApiGatewayRequest apiGatewayRequestBuild = ApiGatewayRequest.build(PATH_CLIENT, "1.0.0", null);
            apiGatewayRequestBuild.addParams("iotIdList", arrayList);
            apiGatewayConnect.send(apiGatewayRequestBuild, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.cmp.manager.connect.auth.alcs.AlcsAuthHttpRequest.1
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.e(AlcsAuthHttpRequest.TAG, "requestClientInfo,onErrorResponse(), error = " + aError.getMsg());
                    AlcsAuthHttpRequest.clientFailCallback(aError.getMsg(), iAlcsAuthCallback);
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    Object obj;
                    StringBuilder sb = new StringBuilder();
                    sb.append("requestClientInfo, onResponse(),rsp = ");
                    sb.append((aResponse == null || (obj = aResponse.data) == null) ? "" : (String) obj);
                    ALog.d(AlcsAuthHttpRequest.TAG, sb.toString());
                    try {
                        JSONObject object = JSON.parseObject((String) aResponse.data);
                        String string = object.getString("msg");
                        if (object.getIntValue("code") == 200) {
                            AlcsClientAuthValue alcsClientAuthValue = (AlcsClientAuthValue) object.getJSONObject("data").getJSONArray("alcsDeviceDTOList").getObject(0, AlcsClientAuthValue.class);
                            if (alcsClientAuthValue != null && alcsClientAuthValue.checkValid()) {
                                iAlcsAuthCallback.onSuccess(alcsClientAuthValue);
                            }
                        } else {
                            AlcsAuthHttpRequest.clientFailCallback(string, iAlcsAuthCallback);
                        }
                    } catch (Exception e2) {
                        ALog.e(AlcsAuthHttpRequest.TAG, "requestClientInfo,onResponse(), error = " + e2.toString());
                        AlcsAuthHttpRequest.clientFailCallback(e2.toString(), iAlcsAuthCallback);
                    }
                }
            });
        } catch (Exception e2) {
            ALog.d(TAG, "requestClientInfo, request error, e = " + e2.toString());
            e2.printStackTrace();
            clientFailCallback(e2.toString(), iAlcsAuthCallback);
        }
    }

    public static void requestServerInfo(final IAlcsAuthCallback iAlcsAuthCallback) {
        ALog.d(TAG, "requestServerInfo");
        if (iAlcsAuthCallback == null) {
            ALog.e(TAG, "requestServerInfo callback null");
            return;
        }
        try {
            AConnect persistentConnect = ConnectManager.getInstance().getPersistentConnect();
            if (persistentConnect == null) {
                serverFailCallback("connect not found", iAlcsAuthCallback);
                return;
            }
            MqttPublishRequest mqttPublishRequest = new MqttPublishRequest();
            mqttPublishRequest.isRPC = true;
            mqttPublishRequest.topic = TOPIC_SERVER;
            persistentConnect.send(mqttPublishRequest, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.cmp.manager.connect.auth.alcs.AlcsAuthHttpRequest.2
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.e(AlcsAuthHttpRequest.TAG, "requestServerInfo,onErrorResponse(), error = " + aError.getMsg());
                    AlcsAuthHttpRequest.serverFailCallback(aError.getMsg(), iAlcsAuthCallback);
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    Object obj;
                    StringBuilder sb = new StringBuilder();
                    sb.append("requestServerInfo, onResponse(),rsp = ");
                    sb.append((aResponse == null || (obj = aResponse.data) == null) ? "" : (String) obj);
                    ALog.d(AlcsAuthHttpRequest.TAG, sb.toString());
                    try {
                        JSONObject object = JSON.parseObject((String) aResponse.data);
                        String string = object.getString("msg");
                        if (object.getIntValue("code") == 200) {
                            AlcsServerAuthValue alcsServerAuthValue = (AlcsServerAuthValue) object.getObject("data", AlcsServerAuthValue.class);
                            if (alcsServerAuthValue != null && alcsServerAuthValue.checkValid()) {
                                iAlcsAuthCallback.onSuccess(alcsServerAuthValue);
                            }
                        } else {
                            AlcsAuthHttpRequest.serverFailCallback(string, iAlcsAuthCallback);
                        }
                    } catch (Exception e2) {
                        ALog.e(AlcsAuthHttpRequest.TAG, "requestServerInfo,onResponse(), error = " + e2.toString());
                        AlcsAuthHttpRequest.serverFailCallback(e2.toString(), iAlcsAuthCallback);
                    }
                }
            });
        } catch (Exception e2) {
            ALog.d(TAG, "requestServerInfo, request error, e = " + e2.toString());
            e2.printStackTrace();
            serverFailCallback(e2.toString(), iAlcsAuthCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void serverFailCallback(String str, IAlcsAuthCallback iAlcsAuthCallback) {
        CmpError cmpErrorALCS_REQUEST_SERVER_AUTH_FAIL = CmpError.ALCS_REQUEST_SERVER_AUTH_FAIL();
        cmpErrorALCS_REQUEST_SERVER_AUTH_FAIL.setSubMsg(str);
        iAlcsAuthCallback.onFailed(cmpErrorALCS_REQUEST_SERVER_AUTH_FAIL);
    }
}
