package com.aliyun.alink.linksdk.cmp.connect.alcs;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.api.client.IDeviceHandler;
import com.aliyun.alink.linksdk.alcs.api.utils.ErrorInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalRspMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.cmp.core.base.AConnect;
import com.aliyun.alink.linksdk.cmp.core.base.AConnectConfig;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.CmpError;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectAuth;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectInitListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectUnscribeListener;
import com.aliyun.alink.linksdk.cmp.core.util.CallbackHelper;
import com.aliyun.alink.linksdk.cmp.core.util.ClassSwitchHelper;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.linksdk.alcs.AlcsCmpSDK;
import com.aliyun.linksdk.alcs.IAlcsClient;
import com.aliyun.linksdk.alcs.IClientNotify;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public class AlcsConnect extends AConnect implements IConnectAuth<Map<String, String>> {
    public static final String PerformanceTag = "PerformanceTag";
    private static final String TAG = "AlcsConnect";
    private IAlcsClient alcsClient = null;
    private List<CacheAction> cacheActionList = null;
    private AlcsConnectConfig config;
    private Context context;
    private IConnectInitListener initListener;

    /* renamed from: com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsConnect$6, reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$alink$linksdk$cmp$connect$alcs$AlcsConnect$ActionEnum;

        static {
            int[] iArr = new int[ActionEnum.values().length];
            $SwitchMap$com$aliyun$alink$linksdk$cmp$connect$alcs$AlcsConnect$ActionEnum = iArr;
            try {
                iArr[ActionEnum.Send.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$cmp$connect$alcs$AlcsConnect$ActionEnum[ActionEnum.Subscribe.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$cmp$connect$alcs$AlcsConnect$ActionEnum[ActionEnum.Unsubscribe.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private enum ActionEnum {
        Send,
        Subscribe,
        Unsubscribe
    }

    private class CacheAction {
        public ActionEnum action;
        public Object listener;
        public ARequest request;

        private CacheAction() {
        }
    }

    private void cacheAction(ActionEnum actionEnum, ARequest aRequest, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "cacheAction");
        CacheAction cacheAction = new CacheAction();
        cacheAction.action = actionEnum;
        cacheAction.request = aRequest;
        cacheAction.listener = obj;
        if (this.cacheActionList == null) {
            this.cacheActionList = new CopyOnWriteArrayList();
        }
        this.cacheActionList.add(cacheAction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCacheActions(boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object obj;
        Object obj2;
        Object obj3;
        ALog.d(TAG, "handleCacheActions(),isConnect = " + z2);
        List<CacheAction> list = this.cacheActionList;
        if (list == null || list.size() == 0) {
            return;
        }
        for (CacheAction cacheAction : this.cacheActionList) {
            ALog.d(TAG, "handleCacheActions(),item");
            if (cacheAction == null) {
                ALog.e(TAG, "handleCacheActions(),action null");
            } else {
                int i2 = AnonymousClass6.$SwitchMap$com$aliyun$alink$linksdk$cmp$connect$alcs$AlcsConnect$ActionEnum[cacheAction.action.ordinal()];
                if (i2 != 1) {
                    if (i2 != 2) {
                        if (i2 == 3) {
                            if (z2 || (obj = cacheAction.listener) == null) {
                                unsubscribe(cacheAction.request, (IConnectUnscribeListener) cacheAction.listener);
                            } else {
                                ((IConnectUnscribeListener) obj).onFailure(CmpError.CONNECT_FAIL_DISCONNECT());
                            }
                        }
                    } else if (z2 || (obj2 = cacheAction.listener) == null) {
                        subscribe(cacheAction.request, (IConnectSubscribeListener) cacheAction.listener);
                    } else {
                        ((IConnectSubscribeListener) obj2).onFailure(CmpError.CONNECT_FAIL_DISCONNECT());
                    }
                } else if (z2 || (obj3 = cacheAction.listener) == null) {
                    send(cacheAction.request, (IConnectSendListener) cacheAction.listener);
                } else {
                    ((IConnectSendListener) obj3).onFailure(cacheAction.request, CmpError.CONNECT_FAIL_DISCONNECT());
                }
            }
        }
        this.cacheActionList.clear();
        this.cacheActionList = null;
    }

    private void initClientConnect() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "initClientConnect()");
        IAlcsClient iAlcsClientInitClientConnect = AlcsCmpSDK.initClientConnect(ClassSwitchHelper.alcsConfigTransfer(this.config), new IDeviceHandler() { // from class: com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsConnect.1
            @Override // com.aliyun.alink.linksdk.alcs.api.client.IDeviceHandler
            public void onFail(Object obj, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                String str;
                ALog.d(AlcsConnect.TAG, "initClientConnect(), onFail connectId:" + ((AConnect) AlcsConnect.this).connectId);
                AlcsConnect.this.updateConnectState(ConnectState.CONNECTFAIL);
                if (AlcsConnect.this.initListener != null) {
                    CmpError cmpErrorALCS_INIT_ERROR = CmpError.ALCS_INIT_ERROR();
                    cmpErrorALCS_INIT_ERROR.setSubCode(errorInfo != null ? errorInfo.getErrorCode() : 0);
                    if (errorInfo != null) {
                        str = errorInfo.getErrorCode() + "," + errorInfo.getErrorMsg();
                    } else {
                        str = "";
                    }
                    cmpErrorALCS_INIT_ERROR.setSubMsg(str);
                    AlcsConnect.this.initListener.onFailure(cmpErrorALCS_INIT_ERROR);
                }
                AlcsConnect.this.handleCacheActions(false);
            }

            @Override // com.aliyun.alink.linksdk.alcs.api.client.IDeviceHandler
            public void onSuccess(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(AlcsConnect.TAG, "initClientConnect(), onSuccess connectId:" + ((AConnect) AlcsConnect.this).connectId);
                AlcsConnect.this.updateConnectState(ConnectState.CONNECTED);
                if (AlcsConnect.this.initListener != null) {
                    AlcsConnect.this.initListener.onSuccess();
                }
                AlcsConnect.this.handleCacheActions(true);
            }
        });
        this.alcsClient = iAlcsClientInitClientConnect;
        iAlcsClientInitClientConnect.setNotifyListener(new IClientNotify() { // from class: com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsConnect.2
            @Override // com.aliyun.linksdk.alcs.IClientNotify
            public void onNotify(String str, PalRspMessage palRspMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(AlcsConnect.TAG, "onNotify(), topic  = " + str);
                if (((AConnect) AlcsConnect.this).notifyListener == null) {
                    return;
                }
                try {
                    str = new URI(str).getPath();
                } catch (Exception e2) {
                    ALog.d(AlcsConnect.TAG, "onNotify(), e = " + e2.toString());
                }
                ALog.d(AlcsConnect.TAG, "onNotify(), path = " + str);
                if (((AConnect) AlcsConnect.this).notifyListener.shouldHandle(AlcsConnect.this.getConnectId(), str)) {
                    ALog.d(AlcsConnect.TAG, "onNotify(), notify");
                    AMessage aMessage = new AMessage();
                    aMessage.setData(palRspMessage.payload);
                    ((AConnect) AlcsConnect.this).notifyListener.onNotify(AlcsConnect.this.getConnectId(), str, aMessage);
                }
            }

            @Override // com.aliyun.linksdk.alcs.IClientNotify
            public void onServerStateChange(boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(AlcsConnect.TAG, "onServerStateChange(), isConnected = " + z2);
                if (z2) {
                    AlcsConnect.this.updateConnectState(ConnectState.CONNECTED);
                } else {
                    AlcsConnect.this.updateConnectState(ConnectState.DISCONNECTED);
                }
            }
        });
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public ConnectState getConnectState() {
        IAlcsClient iAlcsClient = this.alcsClient;
        if (iAlcsClient != null && this.connectState != ConnectState.CONNECTING) {
            if (iAlcsClient.isServerOnline()) {
                updateConnectState(ConnectState.CONNECTED);
            } else {
                updateConnectState(ConnectState.DISCONNECTED);
            }
        }
        return this.connectState;
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void init(Context context, AConnectConfig aConnectConfig, IConnectInitListener iConnectInitListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "init()");
        if (context == null || aConnectConfig == null || !(aConnectConfig instanceof AlcsConnectConfig) || !aConnectConfig.checkVaild()) {
            ALog.d(TAG, "init()，params error");
            CallbackHelper.paramError(iConnectInitListener, "init, cxt or config is invalid");
            return;
        }
        this.context = context;
        AlcsConnectConfig alcsConnectConfig = (AlcsConnectConfig) aConnectConfig;
        this.config = alcsConnectConfig;
        this.initListener = iConnectInitListener;
        updateConnectState(ConnectState.CONNECTING);
        if (alcsConnectConfig.isNeedAuthInfo()) {
            iConnectInitListener.onPrepareAuth(this);
        } else {
            initClientConnect();
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void onDestroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "onDestroy()");
        IAlcsClient iAlcsClient = this.alcsClient;
        if (iAlcsClient != null) {
            iAlcsClient.destroy();
            this.alcsClient = null;
        }
        updateConnectState(ConnectState.DISCONNECTED);
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectAuth
    public void onPrepareAuthFail(AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "onPrepareFail()");
        IConnectInitListener iConnectInitListener = this.initListener;
        if (iConnectInitListener != null) {
            iConnectInitListener.onFailure(aError);
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void send(final ARequest aRequest, final IConnectSendListener iConnectSendListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ConnectState connectState;
        ALog.d(TAG, "send()");
        if (this.alcsClient == null || !((connectState = this.connectState) == ConnectState.CONNECTED || connectState == ConnectState.CONNECTING)) {
            if (iConnectSendListener != null) {
                iConnectSendListener.onFailure(aRequest, CmpError.CONNECT_FAIL_DISCONNECT());
            }
        } else {
            if (connectState == ConnectState.CONNECTING) {
                cacheAction(ActionEnum.Send, aRequest, iConnectSendListener);
                return;
            }
            CoAPRequest coAPRequest = (CoAPRequest) aRequest;
            final String str = coAPRequest.traceId;
            if (str == null) {
                str = "";
            }
            String str2 = coAPRequest.alinkIdForTracker;
            String str3 = str2 != null ? str2 : "";
            if (!TextUtils.isEmpty(str)) {
                ALog.d("PerformanceTag", "{\"mod\":\"cmp\",\"tunnel\":\"alcs\",\"id\":\"_id_\",\"alinkid\":\"_alinkid_\",\"event\":\"req\"}".replace("_id_", str).replace("_alinkid_", str3));
            }
            this.alcsClient.sendRequest(coAPRequest.isSecurity, ClassSwitchHelper.alcsRequestToIotReqMsg(this.config.getProductKey(), this.config.getDeviceName(), coAPRequest), new PalMsgListener() { // from class: com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsConnect.3
                @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener
                public void onLoad(PalRspMessage palRspMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    if (iConnectSendListener == null) {
                        return;
                    }
                    if (palRspMessage == null || palRspMessage.code != 0) {
                        if (!TextUtils.isEmpty(str)) {
                            ALog.d("PerformanceTag", "{\"mod\":\"cmp\",\"tunnel\":\"alcs\",\"id\":\"_id_\",\"event\":\"fail\"}".replace("_id_", str));
                        }
                        iConnectSendListener.onFailure(aRequest, CmpError.ALCS_SEND_FAIL(palRspMessage.code));
                    } else {
                        if (!TextUtils.isEmpty(str)) {
                            ALog.d("PerformanceTag", "{\"mod\":\"cmp\",\"tunnel\":\"alcs\",\"id\":\"_id_\",\"event\":\"res\"}".replace("_id_", str));
                        }
                        iConnectSendListener.onResponse(aRequest, ClassSwitchHelper.IotResTransfer(palRspMessage));
                    }
                }
            });
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void subscribe(ARequest aRequest, final IConnectSubscribeListener iConnectSubscribeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ConnectState connectState;
        ALog.d(TAG, "subscribe()");
        if (this.alcsClient == null || !((connectState = this.connectState) == ConnectState.CONNECTED || connectState == ConnectState.CONNECTING)) {
            if (iConnectSubscribeListener != null) {
                iConnectSubscribeListener.onFailure(CmpError.CONNECT_FAIL_DISCONNECT());
            }
        } else if (connectState == ConnectState.CONNECTING) {
            cacheAction(ActionEnum.Subscribe, aRequest, iConnectSubscribeListener);
        } else {
            CoAPRequest coAPRequest = (CoAPRequest) aRequest;
            this.alcsClient.subscribe(coAPRequest.isSecurity, ClassSwitchHelper.alcsRequestToIotSubMsg(this.config.getProductKey(), this.config.getDeviceName(), coAPRequest), new PalMsgListener() { // from class: com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsConnect.4
                @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener
                public void onLoad(PalRspMessage palRspMessage) {
                    IConnectSubscribeListener iConnectSubscribeListener2 = iConnectSubscribeListener;
                    if (iConnectSubscribeListener2 != null) {
                        if (palRspMessage.code == 0) {
                            iConnectSubscribeListener2.onSuccess();
                            return;
                        }
                        CmpError cmpErrorALCS_SUBSCRIBE_FAIL = CmpError.ALCS_SUBSCRIBE_FAIL();
                        cmpErrorALCS_SUBSCRIBE_FAIL.setSubMsg(String.valueOf(palRspMessage.code));
                        iConnectSubscribeListener.onFailure(cmpErrorALCS_SUBSCRIBE_FAIL);
                    }
                }
            });
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void unsubscribe(ARequest aRequest, final IConnectUnscribeListener iConnectUnscribeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ConnectState connectState;
        ALog.d(TAG, "unsubscribe()");
        if (aRequest == null || this.alcsClient == null || !((connectState = this.connectState) == ConnectState.CONNECTED || connectState == ConnectState.CONNECTING)) {
            if (iConnectUnscribeListener != null) {
                iConnectUnscribeListener.onFailure(CmpError.CONNECT_FAIL_DISCONNECT());
            }
        } else {
            if (connectState == ConnectState.CONNECTING) {
                cacheAction(ActionEnum.Unsubscribe, aRequest, iConnectUnscribeListener);
                return;
            }
            try {
                this.alcsClient.unsubscribe(((CoAPRequest) aRequest).isSecurity, ClassSwitchHelper.alcsRequestToIotSubMsg(this.config.getProductKey(), this.config.getDeviceName(), (CoAPRequest) aRequest), new PalMsgListener() { // from class: com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsConnect.5
                    @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener
                    public void onLoad(PalRspMessage palRspMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        if (palRspMessage != null && palRspMessage.code == 0) {
                            ALog.d(AlcsConnect.TAG, "unsubscribe(),onSuccess");
                            IConnectUnscribeListener iConnectUnscribeListener2 = iConnectUnscribeListener;
                            if (iConnectUnscribeListener2 != null) {
                                iConnectUnscribeListener2.onSuccess();
                                return;
                            }
                            return;
                        }
                        ALog.d(AlcsConnect.TAG, "unsubscribe(),onFail");
                        if (iConnectUnscribeListener == null) {
                            return;
                        }
                        CmpError cmpErrorALCS_UNSUBSCRIBE_FAIL = CmpError.ALCS_UNSUBSCRIBE_FAIL();
                        cmpErrorALCS_UNSUBSCRIBE_FAIL.setSubMsg(palRspMessage != null ? String.valueOf(palRspMessage.code) : "1");
                        iConnectUnscribeListener.onFailure(cmpErrorALCS_UNSUBSCRIBE_FAIL);
                    }
                });
            } catch (Exception e2) {
                ALog.d(TAG, "unsubscribe(), error" + e2.toString());
                e2.printStackTrace();
            }
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectAuth
    public void onAuth(Map<String, String> map) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "auth()");
        if (map == null || !map.containsKey("PK") || !map.containsKey("DN") || !map.containsKey("TOKEN") || !map.containsKey("KEY")) {
            IConnectInitListener iConnectInitListener = this.initListener;
            if (iConnectInitListener != null) {
                iConnectInitListener.onFailure(CmpError.CONNECT_AUTH_INFO_ERROR());
                return;
            }
            return;
        }
        this.config.setProductKey(map.get("PK"));
        this.config.setDeviceName(map.get("DN"));
        this.config.setAccessKey(map.get("KEY"));
        this.config.setAccessToken(map.get("TOKEN"));
        initClientConnect();
    }
}
