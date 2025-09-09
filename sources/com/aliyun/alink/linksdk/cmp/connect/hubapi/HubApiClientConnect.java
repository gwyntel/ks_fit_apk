package com.aliyun.alink.linksdk.cmp.connect.hubapi;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.apiclient.CommonRequest;
import com.aliyun.alink.apiclient.CommonResponse;
import com.aliyun.alink.apiclient.InitializeConfig;
import com.aliyun.alink.apiclient.IoTAPIClientFactory;
import com.aliyun.alink.apiclient.IoTApiClient;
import com.aliyun.alink.apiclient.IoTCallback;
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
import com.aliyun.alink.linksdk.cmp.core.util.CallbackHelper;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class HubApiClientConnect extends AConnect {
    public static final String CONNECT_ID = "LINK_HUB_API_CLIENT";
    private static final String TAG = "HubApiClientConnect";
    private static IoTApiClient ioTAPIClient;
    private HubApiClientConnectConfig config;
    private Context context;

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void init(Context context, AConnectConfig aConnectConfig, IConnectInitListener iConnectInitListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "init()");
        if (context == null || aConnectConfig == null || !(aConnectConfig instanceof HubApiClientConnectConfig) || !aConnectConfig.checkVaild()) {
            ALog.d(TAG, "init()，params error");
            CallbackHelper.paramError(iConnectInitListener, "init, cxt or config is invalid");
            return;
        }
        this.connectId = CONNECT_ID;
        this.context = context;
        this.config = (HubApiClientConnectConfig) aConnectConfig;
        ioTAPIClient = new IoTAPIClientFactory().getClient();
        InitializeConfig initializeConfig = new InitializeConfig();
        HubApiClientConnectConfig hubApiClientConnectConfig = this.config;
        initializeConfig.productKey = hubApiClientConnectConfig.productKey;
        initializeConfig.deviceName = hubApiClientConnectConfig.deviceName;
        if (!TextUtils.isEmpty(hubApiClientConnectConfig.domain)) {
            initializeConfig.domain = this.config.domain;
        }
        if (!TextUtils.isEmpty(this.config.productSecret)) {
            initializeConfig.productSecret = this.config.productSecret;
        }
        if (!TextUtils.isEmpty(this.config.deviceSecret)) {
            initializeConfig.deviceSecret = this.config.deviceSecret;
        }
        ioTAPIClient.init(initializeConfig);
        ConnectState connectState = ConnectState.CONNECTED;
        this.connectState = connectState;
        updateConnectState(connectState);
        if (iConnectInitListener != null) {
            iConnectInitListener.onSuccess();
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void onDestroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "onDestory()");
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void send(final ARequest aRequest, final IConnectSendListener iConnectSendListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "send()");
        if (aRequest != null && (aRequest instanceof HubApiRequest)) {
            ioTAPIClient.send(((HubApiRequest) aRequest).toChannelRequest(), new IoTCallback() { // from class: com.aliyun.alink.linksdk.cmp.connect.hubapi.HubApiClientConnect.1
                @Override // com.aliyun.alink.apiclient.IoTCallback
                public void onFailure(CommonRequest commonRequest, Exception exc) {
                    if (iConnectSendListener == null) {
                        return;
                    }
                    CmpError cmpErrorHUB_API_SEND_FAIL = CmpError.HUB_API_SEND_FAIL();
                    cmpErrorHUB_API_SEND_FAIL.setSubMsg(exc.toString());
                    iConnectSendListener.onFailure(aRequest, cmpErrorHUB_API_SEND_FAIL);
                }

                @Override // com.aliyun.alink.apiclient.IoTCallback
                public void onResponse(CommonRequest commonRequest, CommonResponse commonResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    StringBuilder sb = new StringBuilder();
                    sb.append("onResponse(),rsp = ");
                    sb.append((commonResponse == null || commonResponse.getData() == null) ? "" : commonResponse.getData());
                    ALog.d(HubApiClientConnect.TAG, sb.toString());
                    if (iConnectSendListener == null) {
                        return;
                    }
                    if (commonResponse != null && commonResponse.getData() != null) {
                        HubApiResponse hubApiResponse = new HubApiResponse();
                        hubApiResponse.data = commonResponse.getData();
                        iConnectSendListener.onResponse(aRequest, hubApiResponse);
                    } else {
                        CmpError cmpErrorHUB_API_SEND_FAIL = CmpError.HUB_API_SEND_FAIL();
                        cmpErrorHUB_API_SEND_FAIL.setSubMsg("empty response or fail status");
                        cmpErrorHUB_API_SEND_FAIL.setSubCode(717);
                        iConnectSendListener.onFailure(aRequest, cmpErrorHUB_API_SEND_FAIL);
                    }
                }
            });
            return;
        }
        ALog.d(TAG, "request is invalid ");
        if (iConnectSendListener == null) {
            return;
        }
        CmpError cmpErrorPARAMS_ERROR = CmpError.PARAMS_ERROR();
        cmpErrorPARAMS_ERROR.setSubMsg("send, request is invalid");
        iConnectSendListener.onFailure(aRequest, cmpErrorPARAMS_ERROR);
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
