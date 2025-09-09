package com.aliyun.alink.linksdk.channel.mobile.b;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.linksdk.channel.core.base.AError;
import com.aliyun.alink.linksdk.channel.core.base.ARequest;
import com.aliyun.alink.linksdk.channel.core.base.AResponse;
import com.aliyun.alink.linksdk.channel.core.base.IOnCallListener;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentNet;
import com.aliyun.alink.linksdk.channel.core.persistent.event.IConnectionStateListener;
import com.aliyun.alink.linksdk.channel.core.persistent.event.IOnPushListener;
import com.aliyun.alink.linksdk.channel.core.persistent.event.PersistentEventDispatcher;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttConfigure;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttInitParams;
import com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel;
import com.aliyun.alink.linksdk.channel.mobile.api.IMobileConnectListener;
import com.aliyun.alink.linksdk.channel.mobile.api.IMobileDownstreamListener;
import com.aliyun.alink.linksdk.channel.mobile.api.IMobileRequestListener;
import com.aliyun.alink.linksdk.channel.mobile.api.IMobileSubscrbieListener;
import com.aliyun.alink.linksdk.channel.mobile.api.MobileConnectConfig;
import com.aliyun.alink.linksdk.channel.mobile.api.MobileConnectState;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ThreadTools;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public class d implements IMobileChannel {

    /* renamed from: e, reason: collision with root package name */
    private Context f11017e;

    /* renamed from: f, reason: collision with root package name */
    private IMobileConnectListener f11018f;

    /* renamed from: h, reason: collision with root package name */
    private a f11020h;

    /* renamed from: i, reason: collision with root package name */
    private HashMap<IMobileConnectListener, IConnectionStateListener> f11021i;

    /* renamed from: j, reason: collision with root package name */
    private HashMap<IMobileDownstreamListener, Boolean> f11022j;

    /* renamed from: k, reason: collision with root package name */
    private b f11023k;

    /* renamed from: o, reason: collision with root package name */
    private Queue<String> f11027o;

    /* renamed from: a, reason: collision with root package name */
    private final String f11013a = "1.5.2.1-2e74cf8";

    /* renamed from: b, reason: collision with root package name */
    private final String f11014b = "/account/bind";

    /* renamed from: c, reason: collision with root package name */
    private final String f11015c = "/account/unbind";

    /* renamed from: d, reason: collision with root package name */
    private final String f11016d = MqttTopic.MULTI_LEVEL_WILDCARD;

    /* renamed from: g, reason: collision with root package name */
    private MobileConnectState f11019g = MobileConnectState.DISCONNECTED;

    /* renamed from: l, reason: collision with root package name */
    private boolean f11024l = false;

    /* renamed from: m, reason: collision with root package name */
    private boolean f11025m = false;

    /* renamed from: n, reason: collision with root package name */
    private int f11026n = 100;

    private class a implements IConnectionStateListener {

        /* renamed from: b, reason: collision with root package name */
        private boolean f11029b = false;

        /* renamed from: c, reason: collision with root package name */
        private IMobileConnectListener f11030c;

        public a(IMobileConnectListener iMobileConnectListener) {
            this.f11030c = iMobileConnectListener;
        }

        public void a(boolean z2) {
            this.f11029b = z2;
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.event.IConnectionStateListener
        public void onConnectFail(String str) {
            d dVar = d.this;
            MobileConnectState mobileConnectState = MobileConnectState.CONNECTFAIL;
            dVar.f11019g = mobileConnectState;
            if (d.this.f11018f != null) {
                this.f11030c.onConnectStateChange(mobileConnectState);
            }
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.event.IConnectionStateListener
        public void onConnected() {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "onConnected() called");
            d dVar = d.this;
            MobileConnectState mobileConnectState = MobileConnectState.CONNECTED;
            dVar.f11019g = mobileConnectState;
            d.this.f11024l = true;
            if (d.this.f11018f != null) {
                this.f11030c.onConnectStateChange(mobileConnectState);
            }
            d.this.b();
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.event.IConnectionStateListener
        public void onDisconnect() {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "onDisconnect() called");
            d dVar = d.this;
            MobileConnectState mobileConnectState = MobileConnectState.DISCONNECTED;
            dVar.f11019g = mobileConnectState;
            if (d.this.f11018f != null) {
                this.f11030c.onConnectStateChange(mobileConnectState);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class b implements IOnPushListener {
        public b() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String c(String str) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "getParams(),payload = " + str);
            try {
                JSONObject object = JSON.parseObject(str);
                if (object == null || !object.containsKey("params")) {
                    return null;
                }
                return object.getString("params");
            } catch (Exception e2) {
                com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "getParams(),error = " + e2.toString());
                e2.printStackTrace();
                return null;
            }
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.event.IOnPushListener
        public void onCommand(String str, byte[] bArr) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "Common Listener,onCommand, s = " + str);
            if (d.this.f11022j == null || d.this.f11022j.size() == 0) {
                return;
            }
            com.aliyun.alink.linksdk.channel.mobile.b.a aVar = new com.aliyun.alink.linksdk.channel.mobile.b.a(str, bArr);
            String strA = aVar.a();
            if (TextUtils.isEmpty(strA)) {
                return;
            }
            String strB = b(strA);
            if (!TextUtils.isEmpty(strB)) {
                if (d.this.f11027o == null) {
                    d.this.f11027o = new LinkedList();
                }
                String str2 = aVar.b() + OpenAccountUIConstants.UNDER_LINE + strB;
                if (d.this.f11027o.contains(str2)) {
                    return;
                }
                if (d.this.f11027o.size() < d.this.f11026n) {
                    d.this.f11027o.offer(str2);
                } else {
                    d.this.f11027o.poll();
                    d.this.f11027o.offer(str2);
                }
            }
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "Common Listener,onCommand,loop,size = " + d.this.f11022j.size());
            for (IMobileDownstreamListener iMobileDownstreamListener : d.this.f11022j.keySet()) {
                if (iMobileDownstreamListener.shouldHandle(a(aVar.b()))) {
                    com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "Common Listener,onCommand,notify = " + iMobileDownstreamListener);
                    if (((Boolean) d.this.f11022j.get(iMobileDownstreamListener)).booleanValue()) {
                        ThreadTools.runOnUiThread(new j(this, strA, iMobileDownstreamListener, aVar));
                    } else {
                        String strC = c(strA);
                        String strA2 = a(aVar.b());
                        if (strC == null) {
                            strC = strA;
                        }
                        iMobileDownstreamListener.onCommand(strA2, strC);
                    }
                }
            }
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.event.IOnPushListener
        public boolean shouldHandle(String str) {
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String a(String str) {
            l lVarA;
            if (TextUtils.isEmpty(str) || (lVarA = m.a().a(null)) == null) {
                return str;
            }
            String str2 = "/sys/" + lVarA.f11052b + "/" + lVarA.f11053c;
            if (str.contains(str2)) {
                str = str.replace(str2, "");
            }
            return str.contains("/app/down") ? str.replace("/app/down", "") : str;
        }

        private String b(String str) {
            try {
                JSONObject object = JSON.parseObject(str);
                if (object == null || !object.containsKey("id")) {
                    return null;
                }
                return object.getString("id");
            } catch (Exception e2) {
                com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "getMsgId(),error = " + e2.toString());
                return null;
            }
        }
    }

    private class c implements IOnCallListener {

        /* renamed from: b, reason: collision with root package name */
        private IMobileRequestListener f11033b;

        public c(IMobileRequestListener iMobileRequestListener) {
            this.f11033b = iMobileRequestListener;
        }

        @Override // com.aliyun.alink.linksdk.channel.core.base.IOnCallListener
        public boolean needUISafety() {
            return true;
        }

        @Override // com.aliyun.alink.linksdk.channel.core.base.IOnCallListener
        public void onFailed(ARequest aRequest, AError aError) {
            this.f11033b.onFailure(aError);
        }

        @Override // com.aliyun.alink.linksdk.channel.core.base.IOnCallListener
        public void onSuccess(ARequest aRequest, AResponse aResponse) {
            Object obj;
            Object obj2;
            StringBuilder sb = new StringBuilder();
            sb.append("MobileOnCallListener, onSuccess, rsp = ");
            sb.append((aResponse == null || (obj2 = aResponse.data) == null) ? TmpConstant.GROUP_ROLE_UNKNOWN : obj2.toString());
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", sb.toString());
            this.f11033b.onSuccess((aResponse == null || (obj = aResponse.data) == null) ? null : obj.toString());
        }
    }

    public d() {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "MobileChannelImpl(),SDK Version = 1.5.2.1-2e74cf8");
        this.f11021i = new HashMap<>();
        this.f11022j = new HashMap<>();
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void asyncSendRequest(String str, Map<String, Object> map, Object obj, IMobileRequestListener iMobileRequestListener) {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "asyncSendRequest(), topic = " + str);
        if (a()) {
            PersistentNet.getInstance().asyncSend(new k(true, str, map, obj), new c(iMobileRequestListener));
        } else if (iMobileRequestListener != null) {
            AError aError = new AError();
            aError.setCode(4101);
            aError.setMsg("mqtt not not connected.");
            iMobileRequestListener.onFailure(aError);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void ayncSendPublishRequest(String str, Object obj, IMobileRequestListener iMobileRequestListener) {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "ayncSendPublishRequest(), topic =" + str);
        if (a()) {
            PersistentNet.getInstance().asyncSend(new k(false, str, null, obj), new c(iMobileRequestListener));
        } else if (iMobileRequestListener != null) {
            AError aError = new AError();
            aError.setCode(4101);
            aError.setMsg("mqtt not not connected.");
            iMobileRequestListener.onFailure(aError);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void bindAccount(String str, IMobileRequestListener iMobileRequestListener) {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "bindAccount(), iotToken = " + str);
        if (a()) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("iotToken", (Object) str);
            PersistentNet.getInstance().asyncSend(new k(true, "/account/bind", null, jSONObject), new f(this, iMobileRequestListener));
        } else if (iMobileRequestListener != null) {
            AError aError = new AError();
            aError.setCode(4101);
            aError.setMsg("mqtt not not connected.");
            iMobileRequestListener.onFailure(aError);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void endConnect() {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "endConnect() called");
        this.f11019g = MobileConnectState.DISCONNECTED;
        this.f11024l = false;
        this.f11025m = false;
        m.a().b();
        PersistentNet.getInstance().destroy();
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public String getClientId() {
        l lVarA = m.a().a(this.f11017e);
        if (lVarA == null || !lVarA.a()) {
            return null;
        }
        return lVarA.f11053c + "&" + lVarA.f11052b;
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public MobileConnectState getMobileConnectState() {
        return this.f11019g;
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void registerConnectListener(boolean z2, IMobileConnectListener iMobileConnectListener) {
        if (iMobileConnectListener == null || this.f11021i.containsKey(iMobileConnectListener)) {
            return;
        }
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "registerConnectListener()");
        a aVar = new a(iMobileConnectListener);
        PersistentEventDispatcher.getInstance().registerOnTunnelStateListener(aVar, z2);
        this.f11021i.put(iMobileConnectListener, aVar);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void registerDownstreamListener(boolean z2, IMobileDownstreamListener iMobileDownstreamListener) {
        if (iMobileDownstreamListener == null || this.f11022j.containsKey(iMobileDownstreamListener)) {
            return;
        }
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "registerDownstreamListener()");
        if (this.f11023k == null) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "registerDownstreamListener(), register common");
            this.f11023k = new b();
            PersistentEventDispatcher.getInstance().registerOnPushListener(this.f11023k, false);
        }
        this.f11022j.put(iMobileDownstreamListener, Boolean.valueOf(z2));
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void startConnect(Context context, MobileConnectConfig mobileConnectConfig, IMobileConnectListener iMobileConnectListener) {
        MobileConnectState mobileConnectState;
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "startConnect()," + mobileConnectConfig);
        if (context == null || mobileConnectConfig == null || !mobileConnectConfig.checkValid()) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.d("MobileChannelImpl", "startConnect(), param error, config is empty");
            return;
        }
        if (this.f11024l || (mobileConnectState = this.f11019g) == MobileConnectState.CONNECTING || mobileConnectState == MobileConnectState.CONNECTED) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.c("MobileChannelImpl", "startConnect(), channel is connecting or connected");
            return;
        }
        if (PersistentNet.getInstance().isDeiniting()) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.c("MobileChannelImpl", "startConnect(), channel is deiniting, please wait for deinitig to finish.");
            return;
        }
        this.f11017e = context;
        this.f11018f = iMobileConnectListener;
        if (this.f11027o == null) {
            this.f11027o = new LinkedList();
        }
        a aVar = this.f11020h;
        if (aVar == null && iMobileConnectListener != null) {
            a aVar2 = new a(iMobileConnectListener);
            this.f11020h = aVar2;
            aVar2.a(true);
            PersistentEventDispatcher.getInstance().registerOnTunnelStateListener(this.f11020h, true);
            this.f11021i.put(iMobileConnectListener, this.f11020h);
        } else if (aVar != null) {
            aVar.a(true);
            PersistentEventDispatcher.getInstance().registerOnTunnelStateListener(this.f11020h, true);
            this.f11021i.put(iMobileConnectListener, this.f11020h);
        }
        this.f11025m = false;
        MqttConfigure.mqttRootCrtFile = MobileConnectConfig.channelRootCrtFile;
        MqttConfigure.isCheckRootCrt = mobileConnectConfig.isCheckChannelRootCrt;
        if (!TextUtils.isEmpty(mobileConnectConfig.channelHost)) {
            MqttConfigure.mqttHost = mobileConnectConfig.channelHost;
            a(mobileConnectConfig);
        } else {
            if (!mobileConnectConfig.autoSelectChannelHost) {
                a(mobileConnectConfig);
                return;
            }
            if (!TextUtils.isEmpty(mobileConnectConfig.serverUrlForAutoSelectChannel)) {
                com.aliyun.alink.linksdk.channel.mobile.a.b.f10995a = mobileConnectConfig.serverUrlForAutoSelectChannel;
            }
            com.aliyun.alink.linksdk.channel.mobile.a.b.a(new e(this, mobileConnectConfig));
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void subscrbie(String str, IMobileSubscrbieListener iMobileSubscrbieListener) {
        if (TextUtils.isEmpty(str)) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.d("MobileChannelImpl", "subscrbie(), topic is Empty");
            return;
        }
        if (!a()) {
            if (iMobileSubscrbieListener != null) {
                AError aError = new AError();
                aError.setCode(4101);
                aError.setMsg("mqtt not not connected.");
                iMobileSubscrbieListener.onFailed(str, aError);
                return;
            }
            return;
        }
        l lVarA = m.a().a(null);
        if (!str.startsWith("/sys/") && lVarA != null) {
            str = ("/sys/" + lVarA.f11052b + "/" + lVarA.f11053c + "/app/down/" + str).replace("//", "/");
        }
        PersistentNet.getInstance().subscribe(str, iMobileSubscrbieListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void unBindAccount(IMobileRequestListener iMobileRequestListener) {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "unBindAccount()");
        if (a()) {
            PersistentNet.getInstance().asyncSend(new k(true, "/account/unbind", null, null), new g(this, iMobileRequestListener));
        } else if (iMobileRequestListener != null) {
            AError aError = new AError();
            aError.setCode(4101);
            aError.setMsg("mqtt not not connected.");
            iMobileRequestListener.onFailure(aError);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void unRegisterConnectListener(IMobileConnectListener iMobileConnectListener) {
        if (iMobileConnectListener == null || !this.f11021i.containsKey(iMobileConnectListener)) {
            return;
        }
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "unRegisterConnectListener()");
        PersistentEventDispatcher.getInstance().unregisterOnTunnelStateListener(this.f11021i.get(iMobileConnectListener));
        this.f11021i.remove(iMobileConnectListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void unRegisterDownstreamListener(IMobileDownstreamListener iMobileDownstreamListener) {
        if (iMobileDownstreamListener == null || !this.f11022j.containsKey(iMobileDownstreamListener)) {
            return;
        }
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "unRegisterDownstreamListener(),remove ");
        this.f11022j.remove(iMobileDownstreamListener);
        if (this.f11022j.size() != 0 || this.f11023k == null) {
            return;
        }
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "unRegisterDownstreamListener(),remove common");
        PersistentEventDispatcher.getInstance().unregisterOnPushListener(this.f11023k);
        this.f11023k = null;
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void unSubscrbie(String str, IMobileSubscrbieListener iMobileSubscrbieListener) {
        if (TextUtils.isEmpty(str)) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.d("MobileChannelImpl", "subscrbie(), topic is Empty");
            return;
        }
        if (!a()) {
            if (iMobileSubscrbieListener != null) {
                AError aError = new AError();
                aError.setCode(4101);
                aError.setMsg("mqtt not not connected.");
                iMobileSubscrbieListener.onFailed(str, aError);
                return;
            }
            return;
        }
        l lVarA = m.a().a(null);
        if (!str.startsWith("/sys/") && lVarA != null) {
            str = ("/sys/" + lVarA.f11052b + "/" + lVarA.f11053c + "/app/down/" + str).replace("//", "/");
        }
        PersistentNet.getInstance().unSubscribe(str, iMobileSubscrbieListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "afterConnect() isSubFlag=" + this.f11025m);
        if (this.f11025m) {
            return;
        }
        subscrbie(MqttTopic.MULTI_LEVEL_WILDCARD, new i(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(MobileConnectConfig mobileConnectConfig) {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "getTripleValueAndConnect");
        l lVarA = m.a().a(this.f11017e);
        if (lVarA != null && lVarA.a()) {
            a(lVarA);
        } else {
            com.aliyun.alink.linksdk.channel.mobile.b.b.a(this.f11017e, mobileConnectConfig, new h(this));
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void endConnect(long j2, IMqttActionListener iMqttActionListener) {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "endConnect() called with: waitTime = [" + j2 + "], listener = [" + iMqttActionListener + "]");
        this.f11019g = MobileConnectState.DISCONNECTED;
        this.f11024l = false;
        this.f11025m = false;
        m.a().b();
        PersistentNet.getInstance().destroy(j2, null, iMqttActionListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(l lVar) {
        MobileConnectState mobileConnectState;
        MobileConnectState mobileConnectState2;
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "connect(),mqttHost = " + MqttConfigure.mqttHost + ", crt = " + MqttConfigure.isCheckRootCrt);
        if (!this.f11024l && (mobileConnectState = this.f11019g) != (mobileConnectState2 = MobileConnectState.CONNECTING) && mobileConnectState != MobileConnectState.CONNECTED) {
            this.f11019g = mobileConnectState2;
            IMobileConnectListener iMobileConnectListener = this.f11018f;
            if (iMobileConnectListener != null) {
                iMobileConnectListener.onConnectStateChange(mobileConnectState2);
            }
            PersistentNet.getInstance().init(this.f11017e, new MqttInitParams(lVar.f11052b, lVar.f11053c, lVar.f11054d));
            return;
        }
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "connect(), channel is connecting or connected now");
    }

    private boolean a() {
        return PersistentNet.getInstance().getConnectState() == PersistentConnectState.CONNECTED;
    }
}
