package com.aliyun.alink.linksdk.cmp.connect.channel;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.channel.core.base.IOnCallListener;
import com.aliyun.alink.linksdk.channel.core.persistent.IOnRrpcResponseHandle;
import com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener;
import com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentInitParams;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentNet;
import com.aliyun.alink.linksdk.channel.core.persistent.event.IConnectionStateListener;
import com.aliyun.alink.linksdk.channel.core.persistent.event.IOnPushListener;
import com.aliyun.alink.linksdk.channel.core.persistent.event.PersistentEventDispatcher;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttConfigure;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttInitParams;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttSubscribeRequestParams;
import com.aliyun.alink.linksdk.cmp.api.CommonResource;
import com.aliyun.alink.linksdk.cmp.api.ResourceRequest;
import com.aliyun.alink.linksdk.cmp.core.base.AConnect;
import com.aliyun.alink.linksdk.cmp.core.base.AConnectConfig;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResource;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.base.CmpError;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectAuth;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectInitListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectResourceRegister;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectRrpcHandle;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectRrpcListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectUnscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IResourceRequestListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IResourceResponseListener;
import com.aliyun.alink.linksdk.cmp.core.util.CallbackHelper;
import com.aliyun.alink.linksdk.cmp.core.util.ClassSwitchHelper;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class PersistentConnect extends AConnect implements IConnectResourceRegister, IConnectAuth<Map<String, String>> {
    public static final String CONNECT_ID = "LINK_PERSISTENT";
    private static final String TAG = "PersistentConnect";
    private PersistentConnectConfig config;
    private Context context;
    private IConnectInitListener initListener;
    private MqttInitParams initParams;
    private Queue<String> pushMsgIdQueue;
    private ChannelStateListener channelStateListener = null;
    private DownstreamListener downstreamListener = null;
    private PersistentConnectInfo persistentConnectInfo = null;
    private AtomicBoolean isDestroyed = new AtomicBoolean(false);
    private int PUSH_MSGID_QUEUE_MAX = 100;

    private class ChannelStateListener implements IConnectionStateListener {
        private ChannelStateListener() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r5v8, types: [com.aliyun.alink.linksdk.tools.AError] */
        @Override // com.aliyun.alink.linksdk.channel.core.persistent.event.IConnectionStateListener
        public void onConnectFail(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(PersistentConnect.TAG, "onConnectFail()");
            if (PersistentConnect.this.initListener != null) {
                CmpError cmpErrorMQTT_CONNECT_FAIL = CmpError.MQTT_CONNECT_FAIL();
                cmpErrorMQTT_CONNECT_FAIL.setSubMsg(str);
                try {
                    cmpErrorMQTT_CONNECT_FAIL = (AError) JSON.parseObject(str, AError.class);
                } catch (Exception e2) {
                    ALog.d(PersistentConnect.TAG, "onConnectFail() e:" + e2.toString());
                }
                PersistentConnect.this.initListener.onFailure(cmpErrorMQTT_CONNECT_FAIL);
            }
            PersistentConnect.this.updateConnectState(ConnectState.CONNECTFAIL);
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.event.IConnectionStateListener
        public void onConnected() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(PersistentConnect.TAG, "onConnected()");
            if (PersistentConnect.this.initListener != null) {
                PersistentConnect.this.initListener.onSuccess();
                PersistentConnect.this.initListener = null;
            }
            PersistentConnect.this.updateConnectState(ConnectState.CONNECTED);
            PersistentConnect.this.updateConnectInfo();
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.event.IConnectionStateListener
        public void onDisconnect() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(PersistentConnect.TAG, "onDisconnect()");
            PersistentConnect.this.updateConnectState(ConnectState.DISCONNECTED);
        }
    }

    private class DownstreamListener implements IOnPushListener {
        private DownstreamListener() {
        }

        private String getMsgId(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            try {
                JSONObject object = JSON.parseObject(str);
                if (object == null || !object.containsKey("id")) {
                    return null;
                }
                return object.getString("id");
            } catch (Exception e2) {
                ALog.d(PersistentConnect.TAG, "getMsgId(),error = " + e2.toString());
                return null;
            }
        }

        private String getShortTopic(String str) {
            if (TextUtils.isEmpty(str)) {
                return str;
            }
            String str2 = "/sys/" + PersistentConnect.this.initParams.productKey + "/" + PersistentConnect.this.initParams.deviceName;
            if (str.contains(str2)) {
                str = str.replace(str2, "");
            }
            return str.contains("/app/down") ? str.replace("/app/down", "") : str;
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0052  */
        @Override // com.aliyun.alink.linksdk.channel.core.persistent.event.IOnPushListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onCommand(final java.lang.String r5, byte[] r6) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
            /*
                r4 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "onCommand(),topic = "
                r0.append(r1)
                r0.append(r5)
                java.lang.String r0 = r0.toString()
                java.lang.String r1 = "PersistentConnect"
                com.aliyun.alink.linksdk.tools.ALog.d(r1, r0)
                com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect r0 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.this
                com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener r0 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.access$400(r0)
                if (r0 != 0) goto L1f
                return
            L1f:
                java.lang.String r0 = ""
                java.lang.String r2 = new java.lang.String     // Catch: java.lang.Exception -> L42
                java.lang.String r3 = "UTF-8"
                r2.<init>(r6, r3)     // Catch: java.lang.Exception -> L42
                java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L41
                r0.<init>()     // Catch: java.lang.Exception -> L41
                java.lang.String r3 = "onCommand(),raw data = "
                r0.append(r3)     // Catch: java.lang.Exception -> L41
                java.lang.String r3 = java.util.Arrays.toString(r6)     // Catch: java.lang.Exception -> L41
                r0.append(r3)     // Catch: java.lang.Exception -> L41
                java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> L41
                com.aliyun.alink.linksdk.tools.ALog.d(r1, r0)     // Catch: java.lang.Exception -> L41
                goto L48
            L41:
                r0 = r2
            L42:
                java.lang.String r2 = "onCommand(), to data error"
                com.aliyun.alink.linksdk.tools.ALog.d(r1, r2)
                r2 = r0
            L48:
                java.lang.String r0 = r4.getMsgId(r2)
                boolean r1 = android.text.TextUtils.isEmpty(r0)
                if (r1 != 0) goto Lb3
                com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect r1 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.this
                java.util.Queue r1 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.access$500(r1)
                if (r1 != 0) goto L64
                com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect r1 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.this
                java.util.LinkedList r2 = new java.util.LinkedList
                r2.<init>()
                com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.access$502(r1, r2)
            L64:
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                r1.append(r5)
                java.lang.String r2 = "_"
                r1.append(r2)
                r1.append(r0)
                java.lang.String r0 = r1.toString()
                com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect r1 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.this
                java.util.Queue r1 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.access$500(r1)
                boolean r1 = r1.contains(r0)
                if (r1 == 0) goto L85
                return
            L85:
                com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect r1 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.this
                java.util.Queue r1 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.access$500(r1)
                int r1 = r1.size()
                com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect r2 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.this
                int r2 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.access$600(r2)
                if (r1 >= r2) goto La1
                com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect r1 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.this
                java.util.Queue r1 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.access$500(r1)
                r1.offer(r0)
                goto Lb3
            La1:
                com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect r1 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.this
                java.util.Queue r1 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.access$500(r1)
                r1.poll()
                com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect r1 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.this
                java.util.Queue r1 = com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.access$500(r1)
                r1.offer(r0)
            Lb3:
                com.aliyun.alink.linksdk.cmp.core.base.AMessage r0 = new com.aliyun.alink.linksdk.cmp.core.base.AMessage
                r0.<init>()
                r0.data = r6
                com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect$DownstreamListener$1 r6 = new com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect$DownstreamListener$1
                r6.<init>()
                com.aliyun.alink.apiclient.threadpool.ThreadPool.execute(r6)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.DownstreamListener.onCommand(java.lang.String, byte[]):void");
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.event.IOnPushListener
        public boolean shouldHandle(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(PersistentConnect.TAG, "shouldHandle(),topic =" + str);
            if (((AConnect) PersistentConnect.this).notifyListener == null) {
                return false;
            }
            return ((AConnect) PersistentConnect.this).notifyListener.shouldHandle(PersistentConnect.CONNECT_ID, str);
        }
    }

    private class SubscribeRrpcListener implements IOnSubscribeRrpcListener {
        private IResourceRequestListener listener;
        private AResource resource;

        public SubscribeRrpcListener(AResource aResource, IResourceRequestListener iResourceRequestListener) {
            this.resource = aResource;
            this.listener = iResourceRequestListener;
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener
        public boolean needUISafety() {
            return true;
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener
        public void onReceived(String str, com.aliyun.alink.linksdk.channel.core.persistent.PersistentRequest persistentRequest, final IOnRrpcResponseHandle iOnRrpcResponseHandle) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(PersistentConnect.TAG, "SubscribeRrpcListener, onReceived(), topic = " + str);
            if (this.listener != null) {
                ResourceRequest resourceRequest = new ResourceRequest();
                resourceRequest.topic = str;
                resourceRequest.payloadObj = persistentRequest.payloadObj;
                resourceRequest.context = persistentRequest;
                this.listener.onHandleRequest(this.resource, resourceRequest, new IResourceResponseListener() { // from class: com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.SubscribeRrpcListener.1
                    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IResourceResponseListener
                    public void onResponse(AResource aResource, ResourceRequest resourceRequest2, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        ALog.d(PersistentConnect.TAG, "SubscribeRrpcListener, onReceived(),onResponse() call");
                        if (aResource != null) {
                            boolean z2 = aResource instanceof MqttResource;
                            if ((z2 || (aResource instanceof CommonResource)) && obj != null && (obj instanceof AResponse)) {
                                String str2 = z2 ? ((MqttResource) aResource).replyTopic : aResource instanceof CommonResource ? ((CommonResource) aResource).replyTopic : "";
                                ALog.d(PersistentConnect.TAG, "SubscribeRrpcListener, onReceived(), onResponse(), rrpc rsp, replytopic = " + str2);
                                iOnRrpcResponseHandle.onRrpcResponse(str2, ClassSwitchHelper.aRspCmpToChannel((AResponse) obj));
                            }
                        }
                    }
                });
            }
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener
        public void onResponseFailed(String str, com.aliyun.alink.linksdk.channel.core.base.AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(PersistentConnect.TAG, "SubscribeRrpcListener, onResponseFailed(), topic = " + str);
            IResourceRequestListener iResourceRequestListener = this.listener;
            if (iResourceRequestListener != null) {
                iResourceRequestListener.onFailure(ClassSwitchHelper.aErrorChannelToCmp(aError));
            }
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener
        public void onResponseSuccess(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(PersistentConnect.TAG, "SubscribeRrpcListener, onResponseSuccess(), topic = " + str);
            IResourceRequestListener iResourceRequestListener = this.listener;
            if (iResourceRequestListener != null) {
                iResourceRequestListener.onSuccess();
            }
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener
        public void onSubscribeFailed(String str, com.aliyun.alink.linksdk.channel.core.base.AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(PersistentConnect.TAG, "SubscribeRrpcListener, onSubscribeFailed(), topic = " + str);
            IResourceRequestListener iResourceRequestListener = this.listener;
            if (iResourceRequestListener != null) {
                iResourceRequestListener.onFailure(ClassSwitchHelper.aErrorChannelToCmp(aError));
            }
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener
        public void onSubscribeSuccess(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(PersistentConnect.TAG, "SubscribeRrpcListener, onSubscribeSuccess(), topic = " + str);
            IResourceRequestListener iResourceRequestListener = this.listener;
            if (iResourceRequestListener != null) {
                iResourceRequestListener.onSuccess();
            }
        }
    }

    private void initChannel() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "initChannel()");
        if (PersistentNet.getInstance().getConnectState() != PersistentConnectState.CONNECTED) {
            PersistentNet.getInstance().init(this.context, this.initParams);
            return;
        }
        ALog.d(TAG, "initChannel(), already connected");
        updateConnectState(ConnectState.CONNECTED);
        updateConnectInfo();
        IConnectInitListener iConnectInitListener = this.initListener;
        if (iConnectInitListener != null) {
            iConnectInitListener.onSuccess();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateConnectInfo() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "updateConnectInfo()");
        PersistentInitParams initParams = PersistentNet.getInstance().getInitParams();
        if (initParams == null || !(initParams instanceof MqttInitParams)) {
            return;
        }
        if (this.persistentConnectInfo == null) {
            this.persistentConnectInfo = new PersistentConnectInfo();
        }
        PersistentConnectInfo persistentConnectInfo = this.persistentConnectInfo;
        MqttInitParams mqttInitParams = (MqttInitParams) initParams;
        persistentConnectInfo.productKey = mqttInitParams.productKey;
        persistentConnectInfo.deviceName = mqttInitParams.deviceName;
        this.connectInfo = persistentConnectInfo;
    }

    public boolean getIsDestroyed() {
        AtomicBoolean atomicBoolean = this.isDestroyed;
        return atomicBoolean != null && atomicBoolean.get();
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void init(Context context, AConnectConfig aConnectConfig, IConnectInitListener iConnectInitListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "init(),call");
        if (context == null || aConnectConfig == null || !(aConnectConfig instanceof PersistentConnectConfig) || !aConnectConfig.checkVaild()) {
            ALog.d(TAG, "init()，params error");
            CallbackHelper.paramError(iConnectInitListener, "init, cxt or config is invalid");
            return;
        }
        this.isDestroyed.set(false);
        this.connectId = CONNECT_ID;
        if (this.channelStateListener == null) {
            this.channelStateListener = new ChannelStateListener();
        }
        PersistentEventDispatcher.getInstance().registerOnTunnelStateListener(this.channelStateListener, true);
        if (this.downstreamListener == null) {
            this.downstreamListener = new DownstreamListener();
            PersistentEventDispatcher.getInstance().registerOnPushListener(this.downstreamListener, false);
        }
        if (PersistentNet.getInstance().getConnectState() == PersistentConnectState.CONNECTED) {
            ALog.d(TAG, "initChannel(), already connected");
            updateConnectState(ConnectState.CONNECTED);
            updateConnectInfo();
            if (iConnectInitListener != null) {
                iConnectInitListener.onSuccess();
                return;
            }
            return;
        }
        this.context = context;
        PersistentConnectConfig persistentConnectConfig = (PersistentConnectConfig) aConnectConfig;
        this.config = persistentConnectConfig;
        this.initListener = iConnectInitListener;
        if (persistentConnectConfig.isInitUpdateFlag) {
            if (iConnectInitListener != null) {
                iConnectInitListener.onFailure(null);
                return;
            }
            return;
        }
        updateConnectState(ConnectState.CONNECTING);
        if (!TextUtils.isEmpty(persistentConnectConfig.channelHost)) {
            ALog.d(TAG, "init(),update host env config");
            MqttConfigure.mqttHost = persistentConnectConfig.channelHost;
            MqttConfigure.isCheckRootCrt = persistentConnectConfig.isCheckChannelRootCrt;
            MqttConfigure.mqttRootCrtFile = persistentConnectConfig.channelRootCrtFile;
        }
        if (TextUtils.isEmpty(persistentConnectConfig.productKey) || TextUtils.isEmpty(persistentConnectConfig.deviceName)) {
            ALog.d(TAG, "init(),need prepare auth");
            if (iConnectInitListener != null) {
                iConnectInitListener.onPrepareAuth(this);
                return;
            }
            return;
        }
        MqttInitParams mqttInitParams = new MqttInitParams(persistentConnectConfig.productKey, persistentConnectConfig.productSecret, persistentConnectConfig.deviceName, persistentConnectConfig.deviceSecret, persistentConnectConfig.secureMode);
        this.initParams = mqttInitParams;
        mqttInitParams.receiveOfflineMsg = persistentConnectConfig.receiveOfflineMsg;
        initChannel();
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void onDestroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "onDestroy()");
        this.isDestroyed.set(true);
        if (this.channelStateListener != null) {
            PersistentEventDispatcher.getInstance().unregisterOnTunnelStateListener(this.channelStateListener);
            this.channelStateListener = null;
        }
        if (this.downstreamListener != null) {
            PersistentEventDispatcher.getInstance().unregisterOnPushListener(this.downstreamListener);
            this.downstreamListener = null;
        }
        PersistentNet.getInstance().destroy();
        updateConnectState(ConnectState.DISCONNECTED);
        this.connectInfo = null;
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectAuth
    public void onPrepareAuthFail(AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "onPrepareFail()");
        IConnectInitListener iConnectInitListener = this.initListener;
        if (iConnectInitListener != null) {
            iConnectInitListener.onFailure(aError);
            this.initListener = null;
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectResourceRegister
    public void publishResource(AResource aResource, final IBaseListener iBaseListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "publishResource()");
        boolean z2 = aResource instanceof MqttResource;
        if (!z2 && !(aResource instanceof CommonResource)) {
            if (iBaseListener != null) {
                iBaseListener.onFailure(CmpError.UNSUPPORT());
                return;
            }
            return;
        }
        MqttPublishRequest mqttPublishRequest = new MqttPublishRequest();
        mqttPublishRequest.isRPC = false;
        if (z2) {
            MqttResource mqttResource = (MqttResource) aResource;
            mqttPublishRequest.topic = mqttResource.topic;
            mqttPublishRequest.payloadObj = mqttResource.content;
        } else if (aResource instanceof CommonResource) {
            CommonResource commonResource = (CommonResource) aResource;
            mqttPublishRequest.topic = commonResource.topic;
            mqttPublishRequest.payloadObj = commonResource.payload;
        }
        ALog.d(TAG, "send()");
        send(mqttPublishRequest, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.5
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onFailure(ARequest aRequest, AError aError) {
                IBaseListener iBaseListener2 = iBaseListener;
                if (iBaseListener2 != null) {
                    iBaseListener2.onFailure(aError);
                }
            }

            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onResponse(ARequest aRequest, AResponse aResponse) {
                IBaseListener iBaseListener2 = iBaseListener;
                if (iBaseListener2 != null) {
                    iBaseListener2.onSuccess();
                }
            }
        });
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectResourceRegister
    public void registerResource(AResource aResource, IResourceRequestListener iResourceRequestListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "registerResource()");
        boolean z2 = aResource instanceof MqttResource;
        if (z2 || (aResource instanceof CommonResource)) {
            PersistentNet.getInstance().subscribeRrpc((z2 ? (MqttResource) aResource : aResource instanceof CommonResource ? ClassSwitchHelper.commonResToMqttRes((CommonResource) aResource) : null).topic, new SubscribeRrpcListener(aResource, iResourceRequestListener));
        } else if (iResourceRequestListener != null) {
            iResourceRequestListener.onFailure(CmpError.UNSUPPORT());
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void send(final ARequest aRequest, final IConnectSendListener iConnectSendListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "send()");
        if (aRequest instanceof MqttPublishRequest) {
            PersistentNet.getInstance().asyncSend(ClassSwitchHelper.mqttPubReqCmpToChannel((MqttPublishRequest) aRequest), new IOnCallListener() { // from class: com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.1
                @Override // com.aliyun.alink.linksdk.channel.core.base.IOnCallListener
                public boolean needUISafety() {
                    return true;
                }

                @Override // com.aliyun.alink.linksdk.channel.core.base.IOnCallListener
                public void onFailed(com.aliyun.alink.linksdk.channel.core.base.ARequest aRequest2, com.aliyun.alink.linksdk.channel.core.base.AError aError) {
                    IConnectSendListener iConnectSendListener2 = iConnectSendListener;
                    if (iConnectSendListener2 == null) {
                        return;
                    }
                    iConnectSendListener2.onFailure(aRequest, ClassSwitchHelper.aErrorChannelToCmp(aError));
                }

                @Override // com.aliyun.alink.linksdk.channel.core.base.IOnCallListener
                public void onSuccess(com.aliyun.alink.linksdk.channel.core.base.ARequest aRequest2, com.aliyun.alink.linksdk.channel.core.base.AResponse aResponse) {
                    IConnectSendListener iConnectSendListener2 = iConnectSendListener;
                    if (iConnectSendListener2 == null) {
                        return;
                    }
                    iConnectSendListener2.onResponse(aRequest, ClassSwitchHelper.aRspChannelToCmp(aResponse));
                }
            });
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void setNotifyListener(IConnectNotifyListener iConnectNotifyListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setNotifyListener(), listener = " + iConnectNotifyListener);
        if (this.downstreamListener == null) {
            this.downstreamListener = new DownstreamListener();
            PersistentEventDispatcher.getInstance().registerOnPushListener(this.downstreamListener, true);
        }
        super.setNotifyListener(iConnectNotifyListener);
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void subscribe(ARequest aRequest, final IConnectSubscribeListener iConnectSubscribeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "subscribe()");
        if (aRequest instanceof MqttSubscribeRequest) {
            MqttSubscribeRequestParams mqttSubscribeRequestParams = new MqttSubscribeRequestParams();
            MqttSubscribeRequest mqttSubscribeRequest = (MqttSubscribeRequest) aRequest;
            mqttSubscribeRequestParams.qos = mqttSubscribeRequest.qos;
            PersistentNet.getInstance().subscribe(mqttSubscribeRequest.topic, mqttSubscribeRequestParams, new IOnSubscribeListener() { // from class: com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.2
                @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener
                public boolean needUISafety() {
                    return true;
                }

                @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener
                public void onFailed(String str, com.aliyun.alink.linksdk.channel.core.base.AError aError) {
                    IConnectSubscribeListener iConnectSubscribeListener2 = iConnectSubscribeListener;
                    if (iConnectSubscribeListener2 != null) {
                        iConnectSubscribeListener2.onFailure(ClassSwitchHelper.aErrorChannelToCmp(aError));
                    }
                }

                @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener
                public void onSuccess(String str) {
                    IConnectSubscribeListener iConnectSubscribeListener2 = iConnectSubscribeListener;
                    if (iConnectSubscribeListener2 != null) {
                        iConnectSubscribeListener2.onSuccess();
                    }
                }
            });
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void subscribeRrpc(final ARequest aRequest, final IConnectRrpcListener iConnectRrpcListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "subscribeRrpc()");
        if (aRequest instanceof MqttRrpcRegisterRequest) {
            PersistentNet.getInstance().subscribeRrpc(((MqttRrpcRegisterRequest) aRequest).topic, new IOnSubscribeRrpcListener() { // from class: com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.4
                @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener
                public boolean needUISafety() {
                    return true;
                }

                @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener
                public void onReceived(String str, com.aliyun.alink.linksdk.channel.core.persistent.PersistentRequest persistentRequest, final IOnRrpcResponseHandle iOnRrpcResponseHandle) {
                    if (iConnectRrpcListener != null) {
                        MqttRrpcRequest mqttRrpcRequest = new MqttRrpcRequest();
                        mqttRrpcRequest.topic = str;
                        mqttRrpcRequest.payloadObj = persistentRequest.payloadObj;
                        if (!TextUtils.isEmpty(((MqttRrpcRegisterRequest) aRequest).replyTopic)) {
                            mqttRrpcRequest.replyTopic = ((MqttRrpcRegisterRequest) aRequest).replyTopic;
                        } else if (persistentRequest instanceof com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttRrpcRequest) {
                            mqttRrpcRequest.replyTopic = ((com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttRrpcRequest) persistentRequest).replyTopic;
                        }
                        iConnectRrpcListener.onReceived(mqttRrpcRequest, new IConnectRrpcHandle() { // from class: com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.4.1
                            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectRrpcHandle
                            public void onRrpcResponse(String str2, AResponse aResponse) {
                                iOnRrpcResponseHandle.onRrpcResponse(str2, ClassSwitchHelper.aRspCmpToChannel(aResponse));
                            }
                        });
                    }
                }

                @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener
                public void onResponseFailed(String str, com.aliyun.alink.linksdk.channel.core.base.AError aError) {
                    IConnectRrpcListener iConnectRrpcListener2 = iConnectRrpcListener;
                    if (iConnectRrpcListener2 != null) {
                        iConnectRrpcListener2.onResponseFailed(aRequest, ClassSwitchHelper.aErrorChannelToCmp(aError));
                    }
                }

                @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener
                public void onResponseSuccess(String str) {
                    IConnectRrpcListener iConnectRrpcListener2 = iConnectRrpcListener;
                    if (iConnectRrpcListener2 != null) {
                        iConnectRrpcListener2.onResponseSuccess(aRequest);
                    }
                }

                @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener
                public void onSubscribeFailed(String str, com.aliyun.alink.linksdk.channel.core.base.AError aError) {
                    IConnectRrpcListener iConnectRrpcListener2 = iConnectRrpcListener;
                    if (iConnectRrpcListener2 != null) {
                        iConnectRrpcListener2.onSubscribeFailed(aRequest, ClassSwitchHelper.aErrorChannelToCmp(aError));
                    }
                }

                @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener
                public void onSubscribeSuccess(String str) {
                    IConnectRrpcListener iConnectRrpcListener2 = iConnectRrpcListener;
                    if (iConnectRrpcListener2 != null) {
                        iConnectRrpcListener2.onSubscribeSuccess(aRequest);
                    }
                }
            });
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectResourceRegister
    public void unregisterResource(AResource aResource, IBaseListener iBaseListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "unregisterResource()");
        if (iBaseListener != null) {
            iBaseListener.onFailure(CmpError.UNSUPPORT());
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void unsubscribe(ARequest aRequest, final IConnectUnscribeListener iConnectUnscribeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "unsubscribe()");
        if (aRequest instanceof MqttSubscribeRequest) {
            PersistentNet.getInstance().unSubscribe(((MqttSubscribeRequest) aRequest).topic, new IOnSubscribeListener() { // from class: com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.3
                @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener
                public boolean needUISafety() {
                    return true;
                }

                @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener
                public void onFailed(String str, com.aliyun.alink.linksdk.channel.core.base.AError aError) {
                    IConnectUnscribeListener iConnectUnscribeListener2 = iConnectUnscribeListener;
                    if (iConnectUnscribeListener2 != null) {
                        iConnectUnscribeListener2.onFailure(ClassSwitchHelper.aErrorChannelToCmp(aError));
                    }
                }

                @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener
                public void onSuccess(String str) {
                    IConnectUnscribeListener iConnectUnscribeListener2 = iConnectUnscribeListener;
                    if (iConnectUnscribeListener2 != null) {
                        iConnectUnscribeListener2.onSuccess();
                    }
                }
            });
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectAuth
    public void onAuth(Map<String, String> map) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "auth()");
        if (map != null && map.containsKey("PK") && map.containsKey("DN") && map.containsKey("DS")) {
            ALog.d(TAG, "onAuth(), connect");
            this.initParams = new MqttInitParams(map.get("PK"), map.get("DN"), map.get("DS"));
            initChannel();
        } else {
            IConnectInitListener iConnectInitListener = this.initListener;
            if (iConnectInitListener != null) {
                iConnectInitListener.onFailure(CmpError.CONNECT_AUTH_INFO_ERROR());
                this.initListener = null;
            }
        }
    }
}
