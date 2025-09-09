package com.aliyun.alink.linksdk.cmp.api;

import android.content.Context;
import com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsConnectConfig;
import com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsDiscoveryConnect;
import com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsServerConnect;
import com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsServerConnectConfig;
import com.aliyun.alink.linksdk.cmp.connect.alcs.CoAPResource;
import com.aliyun.alink.linksdk.cmp.connect.apigw.ApiGatewayConnect;
import com.aliyun.alink.linksdk.cmp.connect.apigw.ApiGatewayConnectConfig;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttPublishRequest;
import com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect;
import com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnectConfig;
import com.aliyun.alink.linksdk.cmp.connect.hubapi.HubApiClientConnect;
import com.aliyun.alink.linksdk.cmp.connect.hubapi.HubApiClientConnectConfig;
import com.aliyun.alink.linksdk.cmp.core.base.AConnect;
import com.aliyun.alink.linksdk.cmp.core.base.AConnectInfo;
import com.aliyun.alink.linksdk.cmp.core.base.AConnectOption;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResource;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.base.CmpError;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectDiscovery;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectPublishResourceListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectResourceRegister;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectRrpcListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectUnscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IResourceRequestListener;
import com.aliyun.alink.linksdk.cmp.core.util.ClassSwitchHelper;
import com.aliyun.alink.linksdk.cmp.manager.connect.ConnectManager;
import com.aliyun.alink.linksdk.cmp.manager.connect.IRegisterConnectListener;
import com.aliyun.alink.linksdk.cmp.manager.discovery.ConnectDiscoveryManager;
import com.aliyun.alink.linksdk.cmp.manager.resource.ConnectResourceManger;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class ConnectSDK implements IConnectSDK {
    private static final String TAG = "ConnectSDK";

    private class ConnectPublishResourceListenerWrapper {
        private IConnectPublishResourceListener listener;
        private AResource resource;
        private boolean isMqttConnectSuccess = false;
        private boolean isAlcsConnectSuccess = false;

        public ConnectPublishResourceListenerWrapper(AResource aResource, IConnectPublishResourceListener iConnectPublishResourceListener) {
            this.resource = aResource;
            this.listener = iConnectPublishResourceListener;
        }

        public void onAlcsFail(AError aError) {
            if (this.listener == null) {
                return;
            }
            CmpError cmpErrorPUBLISH_RESOURCE_ERROR = CmpError.PUBLISH_RESOURCE_ERROR();
            cmpErrorPUBLISH_RESOURCE_ERROR.setSubCode(aError.getCode());
            cmpErrorPUBLISH_RESOURCE_ERROR.setSubMsg(aError.getMsg());
            this.listener.onFailure(this.resource, cmpErrorPUBLISH_RESOURCE_ERROR);
            this.listener = null;
        }

        public void onAlcsSuccess() {
            IConnectPublishResourceListener iConnectPublishResourceListener = this.listener;
            if (iConnectPublishResourceListener == null) {
                return;
            }
            AResource aResource = this.resource;
            if (!(aResource instanceof CoAPResource) && !this.isMqttConnectSuccess) {
                this.isAlcsConnectSuccess = true;
            } else {
                iConnectPublishResourceListener.onSuccess(aResource);
                this.listener = null;
            }
        }

        public void onMqttFail(AError aError) {
            if (this.listener == null) {
                return;
            }
            CmpError cmpErrorPUBLISH_RESOURCE_ERROR = CmpError.PUBLISH_RESOURCE_ERROR();
            cmpErrorPUBLISH_RESOURCE_ERROR.setSubCode(aError.getCode());
            cmpErrorPUBLISH_RESOURCE_ERROR.setSubMsg(aError.getMsg());
            this.listener.onFailure(this.resource, cmpErrorPUBLISH_RESOURCE_ERROR);
            this.listener = null;
        }

        public void onMqttSuccess() {
            IConnectPublishResourceListener iConnectPublishResourceListener = this.listener;
            if (iConnectPublishResourceListener == null) {
                return;
            }
            iConnectPublishResourceListener.onSuccess(this.resource);
            this.listener = null;
            this.isMqttConnectSuccess = true;
        }
    }

    private static class InstanceHolder {
        private static final ConnectSDK sInstance = new ConnectSDK();

        private InstanceHolder() {
        }
    }

    public static IConnectSDK getInstance() {
        return InstanceHolder.sInstance;
    }

    private void publishResourceWithAlcsServer(AResource aResource, final ConnectPublishResourceListenerWrapper connectPublishResourceListenerWrapper) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "publishResourceWithAlcsServer");
        IConnectResourceRegister alcsResourceRegister = getAlcsResourceRegister();
        if (alcsResourceRegister == null) {
            connectPublishResourceListenerWrapper.onAlcsFail(CmpError.SEND_ERROR_CONNECT_NOT_FOUND());
        } else {
            alcsResourceRegister.publishResource(aResource, new IBaseListener() { // from class: com.aliyun.alink.linksdk.cmp.api.ConnectSDK.1
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
                public void onFailure(AError aError) {
                    connectPublishResourceListenerWrapper.onAlcsFail(aError);
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
                public void onSuccess() {
                    connectPublishResourceListenerWrapper.onAlcsSuccess();
                }
            });
        }
    }

    private void publishResourceWithMqtt(AResource aResource, final ConnectPublishResourceListenerWrapper connectPublishResourceListenerWrapper) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "publishResourceWithMqtt");
        AConnect connect = ConnectManager.getInstance().getConnect(PersistentConnect.CONNECT_ID);
        if (connect == null) {
            connectPublishResourceListenerWrapper.onMqttFail(CmpError.SEND_ERROR_CONNECT_NOT_FOUND());
            return;
        }
        MqttPublishRequest mqttPublishRequest = new MqttPublishRequest();
        mqttPublishRequest.isRPC = false;
        CommonResource commonResource = (CommonResource) aResource;
        mqttPublishRequest.topic = commonResource.topic;
        mqttPublishRequest.payloadObj = commonResource.payload;
        connect.send(mqttPublishRequest, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.cmp.api.ConnectSDK.2
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onFailure(ARequest aRequest, AError aError) {
                connectPublishResourceListenerWrapper.onMqttFail(aError);
            }

            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onResponse(ARequest aRequest, AResponse aResponse) {
                connectPublishResourceListenerWrapper.onMqttSuccess();
            }
        });
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void destoryConnect(String str) {
        ConnectManager.getInstance().destroyConnect(str);
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public IConnectDiscovery getAlcsDiscovery() {
        return ConnectDiscoveryManager.getAlcsDiscovery();
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public String getAlcsDiscoveryConnectId() {
        return AlcsDiscoveryConnect.CONNECT_ID;
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public IConnectResourceRegister getAlcsResourceRegister() {
        return ConnectResourceManger.getAlcsResourceRegister();
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public String getAlcsServerConnectId() {
        return AlcsServerConnect.CONNECT_ID;
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public String getApiGatewayConnectId() {
        return ApiGatewayConnect.CONNECT_ID;
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public IConnectDiscovery getConnectDiscovery(String str) {
        return ConnectDiscoveryManager.getDiscovery(str);
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public AConnectInfo getConnectInfo(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getConnectIndo");
        return ConnectManager.getInstance().getConnectInfo(str);
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public IConnectResourceRegister getConnectResourceRegister(String str) {
        return ConnectResourceManger.getResourceRegister(str);
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public ConnectState getConnectState(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getConnectState()");
        return ConnectManager.getInstance().getConnectState(str);
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public String getHubApiClientConnectId() {
        return HubApiClientConnect.CONNECT_ID;
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public String getPersistentConnectId() {
        return PersistentConnect.CONNECT_ID;
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void init(Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "init()");
        ConnectManager.getInstance().registerApiGatewayConnect(context, null, null);
        ConnectManager.getInstance().registerAlcsDiscoveryConnect(context, null, null);
        PersistentConnectConfig persistentConnectConfig = new PersistentConnectConfig();
        persistentConnectConfig.isInitUpdateFlag = true;
        ConnectManager.getInstance().registerPersistentConnect(context, persistentConnectConfig, null);
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public boolean isConnectRegisted(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "isConnectRegisted(), connectId = " + str);
        boolean z2 = ConnectManager.getInstance().getConnect(str) != null;
        ALog.d(TAG, "isConnectRegisted(), connectId = " + str + ", flag = " + z2);
        return z2;
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void publishResource(AResource aResource, IConnectPublishResourceListener iConnectPublishResourceListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "publishResource() common Resource");
        if (aResource == null) {
            if (iConnectPublishResourceListener != null) {
                iConnectPublishResourceListener.onFailure(aResource, CmpError.PARAMS_ERROR());
                return;
            }
            return;
        }
        ConnectPublishResourceListenerWrapper connectPublishResourceListenerWrapper = new ConnectPublishResourceListenerWrapper(aResource, iConnectPublishResourceListener);
        if (aResource instanceof CoAPResource) {
            publishResourceWithAlcsServer(aResource, connectPublishResourceListenerWrapper);
            return;
        }
        if (aResource instanceof CommonResource) {
            if (ConnectManager.getInstance().getConnect(AlcsServerConnect.CONNECT_ID) != null) {
                publishResourceWithAlcsServer(aResource, connectPublishResourceListenerWrapper);
            }
            publishResourceWithMqtt(aResource, connectPublishResourceListenerWrapper);
        } else if (iConnectPublishResourceListener != null) {
            CmpError cmpErrorPARAMS_ERROR = CmpError.PARAMS_ERROR();
            cmpErrorPARAMS_ERROR.setSubMsg("resource type not define");
            iConnectPublishResourceListener.onFailure(aResource, cmpErrorPARAMS_ERROR);
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void registerAlcsConnect(Context context, String str, AlcsConnectConfig alcsConnectConfig, IRegisterConnectListener iRegisterConnectListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "registerAlcsConnect()");
        ConnectManager.getInstance().registerAlcsConnect(context, str, alcsConnectConfig, iRegisterConnectListener);
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void registerAlcsServerConnect(Context context, AlcsServerConnectConfig alcsServerConnectConfig, IRegisterConnectListener iRegisterConnectListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "registerAlcsServerConnect()");
        ConnectManager.getInstance().registerAlcsServerConnect(context, alcsServerConnectConfig, iRegisterConnectListener);
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void registerApiGatewayConnect(Context context, ApiGatewayConnectConfig apiGatewayConnectConfig, IRegisterConnectListener iRegisterConnectListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "registerApiGatewayConnect()");
        ConnectManager.getInstance().registerApiGatewayConnect(context, apiGatewayConnectConfig, iRegisterConnectListener);
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void registerHubApiClientConnect(Context context, HubApiClientConnectConfig hubApiClientConnectConfig, IRegisterConnectListener iRegisterConnectListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "registerHubApiConnect()");
        ConnectManager.getInstance().registerHubApiClientConnect(context, hubApiClientConnectConfig, iRegisterConnectListener);
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void registerNofityListener(String str, IConnectNotifyListener iConnectNotifyListener) {
        ConnectManager.getInstance().registerNofityListener(str, iConnectNotifyListener);
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void registerPersistentConnect(Context context, PersistentConnectConfig persistentConnectConfig, IRegisterConnectListener iRegisterConnectListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "registerPersistentConnect()");
        ConnectManager.getInstance().registerPersistentConnect(context, persistentConnectConfig, iRegisterConnectListener);
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void registerResource(AResource aResource, IResourceRequestListener iResourceRequestListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "registerResource()");
        if (aResource == null) {
            ALog.d(TAG, "registerResource(), resource is null");
            return;
        }
        if (aResource instanceof CoAPResource) {
            if (ConnectResourceManger.getAlcsResourceRegister() != null) {
                ConnectResourceManger.getAlcsResourceRegister().registerResource(aResource, iResourceRequestListener);
            }
        } else {
            if (!(aResource instanceof CommonResource)) {
                ALog.d(TAG, "registerResource(), resource is ");
                return;
            }
            if (ConnectResourceManger.getAlcsResourceRegister() != null) {
                ConnectResourceManger.getAlcsResourceRegister().registerResource(aResource, iResourceRequestListener);
            }
            if (ConnectResourceManger.getPersistentResourceRegister() != null) {
                ConnectResourceManger.getPersistentResourceRegister().registerResource(aResource, iResourceRequestListener);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0080  */
    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void send(com.aliyun.alink.linksdk.cmp.core.base.ARequest r7, com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener r8) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r6 = this;
            java.lang.String r0 = "send() common request"
            java.lang.String r1 = "ConnectSDK"
            com.aliyun.alink.linksdk.tools.ALog.d(r1, r0)
            if (r7 != 0) goto L13
            if (r8 == 0) goto L12
            com.aliyun.alink.linksdk.cmp.core.base.CmpError r0 = com.aliyun.alink.linksdk.cmp.core.base.CmpError.PARAMS_ERROR()
            r8.onFailure(r7, r0)
        L12:
            return
        L13:
            boolean r0 = r7 instanceof com.aliyun.alink.linksdk.cmp.connect.apigw.ApiGatewayRequest
            if (r0 == 0) goto L1d
            java.lang.String r0 = r6.getApiGatewayConnectId()
        L1b:
            r1 = r7
            goto L6c
        L1d:
            boolean r0 = r7 instanceof com.aliyun.alink.linksdk.cmp.connect.hubapi.HubApiRequest
            if (r0 == 0) goto L26
            java.lang.String r0 = r6.getHubApiClientConnectId()
            goto L1b
        L26:
            boolean r0 = r7 instanceof com.aliyun.alink.linksdk.cmp.connect.channel.PersistentRequest
            if (r0 == 0) goto L2f
            java.lang.String r0 = r6.getPersistentConnectId()
            goto L1b
        L2f:
            boolean r0 = r7 instanceof com.aliyun.alink.linksdk.cmp.api.CommonRequest
            if (r0 == 0) goto L6a
            r0 = r7
            com.aliyun.alink.linksdk.cmp.api.CommonRequest r0 = (com.aliyun.alink.linksdk.cmp.api.CommonRequest) r0
            java.lang.String r2 = r0.ip
            com.aliyun.alink.linksdk.cmp.manager.connect.ConnectManager r3 = com.aliyun.alink.linksdk.cmp.manager.connect.ConnectManager.getInstance()
            java.lang.String r2 = r3.getAlcsConnectIdWithIp(r2)
            com.aliyun.alink.linksdk.cmp.manager.connect.ConnectManager r3 = com.aliyun.alink.linksdk.cmp.manager.connect.ConnectManager.getInstance()
            com.aliyun.alink.linksdk.cmp.core.base.AConnect r3 = r3.getConnect(r2)
            if (r3 == 0) goto L5e
            com.aliyun.alink.linksdk.cmp.core.base.ConnectState r3 = r3.getConnectState()
            com.aliyun.alink.linksdk.cmp.core.base.ConnectState r4 = com.aliyun.alink.linksdk.cmp.core.base.ConnectState.CONNECTED
            if (r3 != r4) goto L5e
            java.lang.String r3 = "send() common request, auto select alcs"
            com.aliyun.alink.linksdk.tools.ALog.d(r1, r3)
            com.aliyun.alink.linksdk.cmp.connect.alcs.CoAPRequest r0 = com.aliyun.alink.linksdk.cmp.core.util.ClassSwitchHelper.commonReqToCoapReq(r0)
            r1 = r0
            r0 = r2
            goto L6c
        L5e:
            java.lang.String r1 = r6.getApiGatewayConnectId()
            com.aliyun.alink.linksdk.cmp.connect.apigw.ApiGatewayRequest r0 = com.aliyun.alink.linksdk.cmp.core.util.ClassSwitchHelper.commonReqToApiGwReq(r0)
            r5 = r1
            r1 = r0
            r0 = r5
            goto L6c
        L6a:
            r0 = 0
            goto L1b
        L6c:
            com.aliyun.alink.linksdk.cmp.manager.connect.ConnectManager r2 = com.aliyun.alink.linksdk.cmp.manager.connect.ConnectManager.getInstance()
            com.aliyun.alink.linksdk.cmp.core.base.AConnect r0 = r2.getConnect(r0)
            if (r0 != 0) goto L80
            if (r8 == 0) goto L7f
            com.aliyun.alink.linksdk.cmp.core.base.CmpError r0 = com.aliyun.alink.linksdk.cmp.core.base.CmpError.SEND_ERROR_CONNECT_NOT_FOUND()
            r8.onFailure(r7, r0)
        L7f:
            return
        L80:
            r0.send(r1, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.linksdk.cmp.api.ConnectSDK.send(com.aliyun.alink.linksdk.cmp.core.base.ARequest, com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener):void");
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void subscribe(String str, ARequest aRequest, IConnectSubscribeListener iConnectSubscribeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "subscribe()");
        if (aRequest == null) {
            if (iConnectSubscribeListener != null) {
                iConnectSubscribeListener.onFailure(CmpError.PARAMS_ERROR());
                return;
            }
            return;
        }
        AConnect connect = ConnectManager.getInstance().getConnect(str);
        if (connect == null) {
            if (iConnectSubscribeListener != null) {
                iConnectSubscribeListener.onFailure(CmpError.SEND_ERROR_CONNECT_NOT_FOUND());
            }
        } else if (!(aRequest instanceof CommonRequest)) {
            connect.subscribe(aRequest, iConnectSubscribeListener);
        } else if (getPersistentConnectId().equals(str)) {
            connect.subscribe(ClassSwitchHelper.commonReqToMqttSubReq((CommonRequest) aRequest), iConnectSubscribeListener);
        } else {
            connect.subscribe(ClassSwitchHelper.commonReqToCoapReq((CommonRequest) aRequest), iConnectSubscribeListener);
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void subscribeRrpc(String str, ARequest aRequest, IConnectRrpcListener iConnectRrpcListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "subscribeRrpc(),connectId = " + str);
        if (aRequest == null) {
            if (iConnectRrpcListener != null) {
                iConnectRrpcListener.onSubscribeFailed(aRequest, CmpError.PARAMS_ERROR());
                return;
            }
            return;
        }
        AConnect connect = ConnectManager.getInstance().getConnect(str);
        if (connect == null) {
            if (iConnectRrpcListener != null) {
                iConnectRrpcListener.onSubscribeFailed(aRequest, CmpError.SEND_ERROR_CONNECT_NOT_FOUND());
            }
        } else if (aRequest instanceof CommonRequest) {
            connect.subscribeRrpc(ClassSwitchHelper.commonReqToMqttRrpcRegReq((CommonRequest) aRequest), iConnectRrpcListener);
        } else {
            connect.subscribeRrpc(aRequest, iConnectRrpcListener);
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void unregisterConnect(String str) {
        ConnectManager.getInstance().unregisterConnect(str);
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void unregisterNofityListener(IConnectNotifyListener iConnectNotifyListener) {
        ConnectManager.getInstance().unregisterNofityListener(iConnectNotifyListener);
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void unsubscribe(String str, ARequest aRequest, IConnectUnscribeListener iConnectUnscribeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "unsubscribe()");
        if (aRequest == null) {
            if (iConnectUnscribeListener != null) {
                iConnectUnscribeListener.onFailure(CmpError.PARAMS_ERROR());
                return;
            }
            return;
        }
        AConnect connect = ConnectManager.getInstance().getConnect(str);
        if (connect == null) {
            if (iConnectUnscribeListener != null) {
                iConnectUnscribeListener.onFailure(CmpError.SEND_ERROR_CONNECT_NOT_FOUND());
            }
        } else if (!(aRequest instanceof CommonRequest)) {
            connect.unsubscribe(aRequest, iConnectUnscribeListener);
        } else if (getPersistentConnectId().equals(str)) {
            connect.unsubscribe(ClassSwitchHelper.commonReqToMqttUnsubReq((CommonRequest) aRequest), iConnectUnscribeListener);
        } else {
            connect.unsubscribe(ClassSwitchHelper.commonReqToCoapReq((CommonRequest) aRequest), iConnectUnscribeListener);
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void updateConnectOption(String str, AConnectOption aConnectOption) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "updateConnectOption, id = " + str);
        AConnect connect = ConnectManager.getInstance().getConnect(str);
        if (connect != null) {
            connect.updateConnectOption(aConnectOption);
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.api.IConnectSDK
    public void send(String str, ARequest aRequest, IConnectSendListener iConnectSendListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "send()");
        if (aRequest == null) {
            if (iConnectSendListener != null) {
                iConnectSendListener.onFailure(aRequest, CmpError.PARAMS_ERROR());
                return;
            }
            return;
        }
        AConnect connect = ConnectManager.getInstance().getConnect(str);
        if (connect == null) {
            if (iConnectSendListener != null) {
                iConnectSendListener.onFailure(aRequest, CmpError.SEND_ERROR_CONNECT_NOT_FOUND());
            }
        } else {
            if (aRequest instanceof CommonRequest) {
                if (getApiGatewayConnectId().equals(str)) {
                    connect.send(ClassSwitchHelper.commonReqToApiGwReq((CommonRequest) aRequest), iConnectSendListener);
                    return;
                } else if (getHubApiClientConnectId().equals(str)) {
                    connect.send(ClassSwitchHelper.commonReqToHubApiReq((CommonRequest) aRequest), iConnectSendListener);
                    return;
                } else {
                    connect.send(ClassSwitchHelper.commonReqToCoapReq((CommonRequest) aRequest), iConnectSendListener);
                    return;
                }
            }
            connect.send(aRequest, iConnectSendListener);
        }
    }
}
