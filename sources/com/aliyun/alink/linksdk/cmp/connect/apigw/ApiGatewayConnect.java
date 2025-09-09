package com.aliyun.alink.linksdk.cmp.connect.apigw;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.cmp.core.base.AConnect;
import com.aliyun.alink.linksdk.cmp.core.base.AConnectConfig;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.CmpError;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectInitListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectUnscribeListener;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientImpl;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Env;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class ApiGatewayConnect extends AConnect {
    public static String CONFIGE_HOST = null;
    public static final String CONNECT_ID = "LINK_API_GATEWAY";
    public static final String PerformanceTag = "PerformanceTag";
    private static final String TAG = "ApiGatewayConnect";
    private static IoTAPIClient ioTAPIClient;
    private ApiGatewayConnectConfig config;
    private Context context;

    private boolean isSupport() {
        return true;
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void init(Context context, AConnectConfig aConnectConfig, IConnectInitListener iConnectInitListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "init()");
        this.connectId = CONNECT_ID;
        this.context = context;
        this.config = (ApiGatewayConnectConfig) aConnectConfig;
        ConnectState connectState = ConnectState.CONNECTED;
        this.connectState = connectState;
        updateConnectState(connectState);
        if (aConnectConfig != null) {
            CONFIGE_HOST = ((ApiGatewayConnectConfig) aConnectConfig).host;
        }
        if (iConnectInitListener != null) {
            iConnectInitListener.onSuccess();
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void onDestroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "onDestory()");
        if (isSupport()) {
            return;
        }
        ALog.d(TAG, " not support");
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void send(final ARequest aRequest, final IConnectSendListener iConnectSendListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "send()");
        if (!isSupport()) {
            ALog.d(TAG, " not support");
            return;
        }
        if (aRequest == null || !(aRequest instanceof ApiGatewayRequest)) {
            ALog.d(TAG, "request is invalid ");
            if (iConnectSendListener == null) {
                return;
            }
            CmpError cmpErrorPARAMS_ERROR = CmpError.PARAMS_ERROR();
            cmpErrorPARAMS_ERROR.setSubMsg("send, request is invalid");
            iConnectSendListener.onFailure(aRequest, cmpErrorPARAMS_ERROR);
            return;
        }
        if (ioTAPIClient == null) {
            try {
                if (this.config != null) {
                    IoTAPIClientImpl.InitializeConfig initializeConfig = new IoTAPIClientImpl.InitializeConfig();
                    ApiGatewayConnectConfig apiGatewayConnectConfig = this.config;
                    initializeConfig.appKey = apiGatewayConnectConfig.appkey;
                    initializeConfig.host = apiGatewayConnectConfig.host;
                    initializeConfig.apiEnv = Env.RELEASE;
                    IoTAPIClientImpl.getInstance().init(this.context, initializeConfig);
                } else {
                    ALog.d(TAG, "init api gateway config empty");
                }
            } catch (Exception e2) {
                ALog.d(TAG, "init api gateway error," + e2.toString());
            }
            ioTAPIClient = new IoTAPIClientFactory().getClient();
            ALog.d(TAG, "register tracker");
        }
        ApiGatewayRequest apiGatewayRequest = (ApiGatewayRequest) aRequest;
        IoTRequest iotRequest = apiGatewayRequest.toIotRequest();
        final String str = apiGatewayRequest.traceId;
        if (str == null) {
            str = "";
        }
        String str2 = apiGatewayRequest.alinkIdForTracker;
        String str3 = str2 != null ? str2 : "";
        if (!TextUtils.isEmpty(str)) {
            ALog.d("PerformanceTag", "{\"mod\":\"cmp\",\"tunnel\":\"cloud\",\"id\":\"_id_\",\"alinkid\":\"_alinkid_\",\"event\":\"req\"}".replace("_id_", str).replace("_alinkid_", str3));
        }
        ioTAPIClient.send(iotRequest, new IoTCallback() { // from class: com.aliyun.alink.linksdk.cmp.connect.apigw.ApiGatewayConnect.1
            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onFailure(IoTRequest ioTRequest, Exception exc) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (!TextUtils.isEmpty(str)) {
                    ALog.d("PerformanceTag", "{\"mod\":\"cmp\",\"tunnel\":\"cloud\",\"id\":\"_id_\",\"event\":\"fail\"}".replace("_id_", str));
                }
                if (iConnectSendListener == null) {
                    return;
                }
                CmpError cmpErrorAPIGW_SEND_FAIL = CmpError.APIGW_SEND_FAIL();
                cmpErrorAPIGW_SEND_FAIL.setSubMsg(exc.toString());
                iConnectSendListener.onFailure(aRequest, cmpErrorAPIGW_SEND_FAIL);
            }

            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                StringBuilder sb = new StringBuilder();
                sb.append("onResponse(),rsp = ");
                sb.append((ioTResponse == null || ioTResponse.getRawData() == null) ? "" : new String(ioTResponse.getRawData()));
                ALog.d(ApiGatewayConnect.TAG, sb.toString());
                if (!TextUtils.isEmpty(str)) {
                    ALog.d("PerformanceTag", "{\"mod\":\"cmp\",\"tunnel\":\"cloud\",\"id\":\"_id_\",\"event\":\"res\"}".replace("_id_", str));
                }
                if (iConnectSendListener == null) {
                    return;
                }
                if (ioTResponse == null || ioTResponse.getRawData() == null) {
                    CmpError cmpErrorAPIGW_SEND_FAIL = CmpError.APIGW_SEND_FAIL();
                    cmpErrorAPIGW_SEND_FAIL.setSubMsg("empty response");
                    iConnectSendListener.onFailure(aRequest, cmpErrorAPIGW_SEND_FAIL);
                } else {
                    ApiGatewayResponse apiGatewayResponse = new ApiGatewayResponse();
                    apiGatewayResponse.data = new String(ioTResponse.getRawData());
                    iConnectSendListener.onResponse(aRequest, apiGatewayResponse);
                }
            }
        });
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void setNotifyListener(IConnectNotifyListener iConnectNotifyListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setNotifyListener,unsupport");
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void subscribe(ARequest aRequest, IConnectSubscribeListener iConnectSubscribeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "subscribe,unsupport");
        if (iConnectSubscribeListener != null) {
            iConnectSubscribeListener.onFailure(CmpError.UNSUPPORT());
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void unsubscribe(ARequest aRequest, IConnectUnscribeListener iConnectUnscribeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "unsubscribe,unsupport");
        if (iConnectUnscribeListener != null) {
            iConnectUnscribeListener.onFailure(CmpError.UNSUPPORT());
        }
    }
}
