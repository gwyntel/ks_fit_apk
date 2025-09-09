package com.aliyun.linksdk.alcs;

import com.aliyun.alink.linksdk.alcs.api.client.AlcsClientConfig;
import com.aliyun.alink.linksdk.alcs.api.client.IDeviceHandler;
import com.aliyun.alink.linksdk.alcs.api.client.IDeviceStateListener;
import com.aliyun.alink.linksdk.alcs.api.utils.ErrorInfo;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPContext;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPResponse;
import com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPReqHandler;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAAuthParams;
import com.aliyun.alink.linksdk.alcs.lpbs.api.IAlcsPal;
import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgr;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalConnectParams;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalRspMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalSubMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalConnectListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDeviceStateListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/* loaded from: classes3.dex */
public class AlcsClientWrapper implements IAlcsClient {
    private static final String TAG = "AlcsClientWrapper";
    private IAlcsPal alcsClient = null;
    private AlcsClientConfig config = null;
    private IClientNotify clientNotify = null;

    private class AlcsCoapUnsubscribeHandle implements IAlcsCoAPReqHandler {
        private boolean flag = false;
        private IDeviceHandler handler;

        public AlcsCoapUnsubscribeHandle(IDeviceHandler iDeviceHandler) {
            this.handler = iDeviceHandler;
        }

        @Override // com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPReqHandler
        public void onReqComplete(AlcsCoAPContext alcsCoAPContext, int i2, AlcsCoAPResponse alcsCoAPResponse) {
            IDeviceHandler iDeviceHandler;
            if (this.flag || (iDeviceHandler = this.handler) == null) {
                return;
            }
            if (i2 == 0) {
                iDeviceHandler.onSuccess(null);
            } else {
                iDeviceHandler.onFail(null, null);
            }
            this.flag = true;
        }
    }

    private class AlcsMsgTriggerHandler implements PalMsgListener {
        private PalMsgListener handler;
        private String topic;

        public AlcsMsgTriggerHandler(String str, PalMsgListener palMsgListener) {
            this.topic = str;
            this.handler = palMsgListener;
        }

        @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener
        public void onLoad(PalRspMessage palRspMessage) {
            AlcsClientWrapper.this.clientNotify.onNotify(this.topic, palRspMessage);
        }
    }

    private class AlcsSubScribleMsgHandler implements PalMsgListener {
        private PalMsgListener handler;
        private String topic;

        public AlcsSubScribleMsgHandler(String str, PalMsgListener palMsgListener) {
            this.topic = str;
            this.handler = palMsgListener;
        }

        @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener
        public void onLoad(PalRspMessage palRspMessage) {
            PalMsgListener palMsgListener = this.handler;
            if (palMsgListener != null) {
                palMsgListener.onLoad(palRspMessage);
            }
        }
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsClient
    public void destroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "destroy()");
        IAlcsPal iAlcsPal = this.alcsClient;
        if (iAlcsPal == null) {
            return;
        }
        iAlcsPal.stopConnect(new PalDeviceInfo(this.config.getProductKey(), this.config.getDeviceName()));
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsClient
    public String getDstAddr() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AlcsClientConfig alcsClientConfig;
        ALog.d(TAG, "getDstAddr()");
        if (this.alcsClient == null || (alcsClientConfig = this.config) == null) {
            return null;
        }
        return alcsClientConfig.getDstAddr();
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [ExtraConnectparams, ExtraParams] */
    @Override // com.aliyun.linksdk.alcs.IAlcsClient
    public void init(AlcsClientConfig alcsClientConfig, final IDeviceHandler iDeviceHandler) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "init()");
        if (this.alcsClient != null) {
            return;
        }
        this.alcsClient = PluginMgr.getInstance();
        this.config = alcsClientConfig;
        this.alcsClient = PluginMgr.getInstance();
        ICAAuthParams iCAAuthParams = new ICAAuthParams();
        iCAAuthParams.accessKey = alcsClientConfig.getAccessKey();
        iCAAuthParams.accessToken = alcsClientConfig.getAccessToken();
        PalConnectParams palConnectParams = new PalConnectParams();
        palConnectParams.deviceInfo = new PalDeviceInfo(alcsClientConfig.getProductKey(), alcsClientConfig.getDeviceName());
        palConnectParams.authInfo = iCAAuthParams;
        palConnectParams.dataFormat = alcsClientConfig.mDataFormat;
        palConnectParams.connectKeepStrategy = alcsClientConfig.mConnectKeepType;
        palConnectParams.extraConnectParams = alcsClientConfig.mExtraParams;
        palConnectParams.pluginId = alcsClientConfig.mPluginId;
        this.alcsClient.startConnect(palConnectParams, new PalConnectListener() { // from class: com.aliyun.linksdk.alcs.AlcsClientWrapper.1
            @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalConnectListener
            public void onLoad(int i2, Map<String, Object> map, PalDeviceInfo palDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(AlcsClientWrapper.TAG, "alcsClientwrapper connectDevice onLoad errorCode:" + i2);
                if (i2 != 0) {
                    iDeviceHandler.onFail(null, new ErrorInfo(i2, ""));
                } else {
                    iDeviceHandler.onSuccess(null);
                    AlcsClientWrapper.this.alcsClient.regDeviceStateListener(new PalDeviceInfo(AlcsClientWrapper.this.config.getProductKey(), AlcsClientWrapper.this.config.getDeviceName()), new AlcsServerStateListener());
                }
            }
        });
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsClient
    public boolean isServerOnline() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "isServerOnline()");
        if (this.alcsClient == null) {
            return false;
        }
        ALog.d(TAG, "isServerOnline(), call coap sdk");
        return this.alcsClient.isDeviceConnected(new PalDeviceInfo(this.config.getProductKey(), this.config.getDeviceName()));
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsClient
    public boolean sendRequest(boolean z2, PalReqMessage palReqMessage, PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "sendRequest()");
        IAlcsPal iAlcsPal = this.alcsClient;
        if (iAlcsPal == null) {
            return false;
        }
        return iAlcsPal.asyncSendRequest(palReqMessage, palMsgListener);
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsClient
    public boolean sendResponse(boolean z2, AlcsCoAPResponse alcsCoAPResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "sendResponse()");
        return false;
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsClient
    public void setNotifyListener(IClientNotify iClientNotify) {
        this.clientNotify = iClientNotify;
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsClient
    public void subscribe(boolean z2, PalSubMessage palSubMessage, PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "subscribe()");
        IAlcsPal iAlcsPal = this.alcsClient;
        if (iAlcsPal == null) {
            return;
        }
        iAlcsPal.subscribe(palSubMessage, new AlcsSubScribleMsgHandler(palSubMessage.topic, palMsgListener), new AlcsMsgTriggerHandler(palSubMessage.topic, palMsgListener));
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsClient
    public void unsubscribe(boolean z2, PalSubMessage palSubMessage, PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "unsubscribe()");
        IAlcsPal iAlcsPal = this.alcsClient;
        if (iAlcsPal == null) {
            return;
        }
        iAlcsPal.unsubscribe(palSubMessage, palMsgListener);
    }

    private class AlcsServerStateListener implements IDeviceStateListener, PalDeviceStateListener {
        private AlcsServerStateListener() {
        }

        @Override // com.aliyun.alink.linksdk.alcs.api.client.IDeviceStateListener
        public void onDeviceStateChange(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(AlcsClientWrapper.TAG, "onDeviceStateChange(), state = " + i2);
            boolean z2 = i2 == 1;
            if (AlcsClientWrapper.this.clientNotify != null) {
                AlcsClientWrapper.this.clientNotify.onServerStateChange(z2);
            }
        }

        @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDeviceStateListener
        public void onDeviceStateChange(PalDeviceInfo palDeviceInfo, int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(AlcsClientWrapper.TAG, "onDeviceStateChange(), state = " + i2);
            boolean z2 = i2 == 1;
            if (AlcsClientWrapper.this.clientNotify != null) {
                AlcsClientWrapper.this.clientNotify.onServerStateChange(z2);
            }
        }
    }
}
