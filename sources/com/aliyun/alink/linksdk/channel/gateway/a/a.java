package com.aliyun.alink.linksdk.channel.gateway.a;

import android.content.Context;
import android.text.TextUtils;
import android.util.LruCache;
import anetwork.channel.util.RequestConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentNet;
import com.aliyun.alink.linksdk.channel.core.persistent.event.IOnPushListener;
import com.aliyun.alink.linksdk.channel.core.persistent.event.PersistentEventDispatcher;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttConfigure;
import com.aliyun.alink.linksdk.channel.gateway.api.GatewayChannel;
import com.aliyun.alink.linksdk.channel.gateway.api.GatewayConnectConfig;
import com.aliyun.alink.linksdk.channel.gateway.api.GatewayConnectState;
import com.aliyun.alink.linksdk.channel.gateway.api.GatewayErrorCode;
import com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel;
import com.aliyun.alink.linksdk.channel.gateway.api.IGatewayConnectListener;
import com.aliyun.alink.linksdk.channel.gateway.api.IGatewayDownstreamListener;
import com.aliyun.alink.linksdk.channel.gateway.api.IGatewayRequestListener;
import com.aliyun.alink.linksdk.channel.gateway.api.IGatewaySubscribeListener;
import com.aliyun.alink.linksdk.channel.gateway.api.subdevice.ErrorResponse;
import com.aliyun.alink.linksdk.channel.gateway.api.subdevice.ISubDeviceActionListener;
import com.aliyun.alink.linksdk.channel.gateway.api.subdevice.ISubDeviceChannel;
import com.aliyun.alink.linksdk.channel.gateway.api.subdevice.ISubDeviceConnectListener;
import com.aliyun.alink.linksdk.channel.gateway.api.subdevice.ISubDeviceRemoveListener;
import com.aliyun.alink.linksdk.channel.gateway.api.subdevice.SubDeviceInfo;
import com.aliyun.alink.linksdk.channel.gateway.api.subdevice.SubDeviceInfoWrapper;
import com.aliyun.alink.linksdk.channel.gateway.api.subdevice.SubDeviceLoginState;
import com.aliyun.alink.linksdk.channel.mobile.api.MobileChannel;
import com.aliyun.alink.linksdk.cmp.api.ConnectSDK;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttPublishRequest;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttSubscribeRequest;
import com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnectConfig;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.manager.connect.ConnectManager;
import com.aliyun.alink.linksdk.cmp.manager.connect.IRegisterConnectListener;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class a implements IGatewayChannel {

    /* renamed from: e, reason: collision with root package name */
    private Context f10929e;

    /* renamed from: f, reason: collision with root package name */
    private IGatewayConnectListener f10930f;

    /* renamed from: h, reason: collision with root package name */
    private PersistentConnectConfig f10932h;

    /* renamed from: i, reason: collision with root package name */
    private Map<String, SubDeviceInfo> f10933i;

    /* renamed from: j, reason: collision with root package name */
    private Map<String, ISubDeviceConnectListener> f10934j;

    /* renamed from: k, reason: collision with root package name */
    private Map<String, ISubDeviceChannel> f10935k;

    /* renamed from: g, reason: collision with root package name */
    private GatewayConnectState f10931g = GatewayConnectState.DISCONNECTED;

    /* renamed from: l, reason: collision with root package name */
    private b f10936l = null;

    /* renamed from: m, reason: collision with root package name */
    private AtomicBoolean f10937m = new AtomicBoolean(false);

    /* renamed from: n, reason: collision with root package name */
    private CopyOnWriteArraySet<String> f10938n = new CopyOnWriteArraySet<>();

    /* renamed from: o, reason: collision with root package name */
    private int f10939o = 64;

    /* renamed from: p, reason: collision with root package name */
    private LruCache<String, SubDeviceInfoWrapper> f10940p = new LruCache<>(this.f10939o);

    /* renamed from: q, reason: collision with root package name */
    private boolean f10941q = false;

    /* renamed from: r, reason: collision with root package name */
    private String f10942r = null;

    /* renamed from: s, reason: collision with root package name */
    private String f10943s = "427";

    /* renamed from: t, reason: collision with root package name */
    private String f10944t = "520";

    /* renamed from: u, reason: collision with root package name */
    private String f10945u = "521";

    /* renamed from: v, reason: collision with root package name */
    private String f10946v = "522";

    /* renamed from: w, reason: collision with root package name */
    private String f10947w = "6401";

    /* renamed from: a, reason: collision with root package name */
    String f10925a = "thing/sub/register";

    /* renamed from: b, reason: collision with root package name */
    String f10926b = "thing.sub.register";

    /* renamed from: c, reason: collision with root package name */
    String f10927c = "thing/sub/unregister";

    /* renamed from: d, reason: collision with root package name */
    String f10928d = "thing.sub.unregister";

    /* renamed from: x, reason: collision with root package name */
    private final String f10948x = "thing/list/found";

    /* renamed from: y, reason: collision with root package name */
    private final String f10949y = "thing.list.found";

    /* renamed from: z, reason: collision with root package name */
    private final String f10950z = "thing/topo/get";
    private final String A = "thing.topo.get";
    private final String B = "thing/topo/add";
    private final String C = "thing.topo.add";
    private final String D = "_thing/service/post";
    private final String E = "_thing.service.post";
    private final String F = "thing/topo/delete";
    private final String G = "thing.topo.delete";
    private IConnectNotifyListener H = new IConnectNotifyListener() { // from class: com.aliyun.alink.linksdk.channel.gateway.a.a.7
        @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
        public void onConnectStateChange(String str, ConnectState connectState) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (ConnectSDK.getInstance().getPersistentConnectId().equals(str)) {
                a.this.f10931g = GatewayConnectState.toGatewayConnectState(connectState);
                if (connectState == ConnectState.CONNECTED) {
                    a.this.f10941q = true;
                    a.this.c();
                }
                if (a.this.f10930f != null) {
                    a.this.f10930f.onConnectStateChange(a.this.f10931g);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
        public void onNotify(String str, String str2, AMessage aMessage) {
            if (aMessage == null || a.this.f10932h == null || TextUtils.isEmpty(a.this.f10942r) || !a.this.f10942r.equals(str2)) {
                return;
            }
            try {
                Object obj = aMessage.data;
                if (obj instanceof byte[]) {
                    String str3 = new String((byte[]) obj, "UTF-8");
                    ErrorResponse errorResponse = (ErrorResponse) JSON.parseObject(str3, new TypeReference<ErrorResponse<SubDeviceInfo>>() { // from class: com.aliyun.alink.linksdk.channel.gateway.a.a.7.1
                    }.getType(), new Feature[0]);
                    String str4 = errorResponse.code;
                    ALog.w("GatewayChannelImpl", "device error received. " + str3);
                    SubDeviceInfo subDeviceInfo = (SubDeviceInfo) errorResponse.data;
                    if (subDeviceInfo != null && subDeviceInfo.checkValid()) {
                        if (subDeviceInfo.productKey.equals(a.this.f10932h.productKey) && subDeviceInfo.deviceName.equals(a.this.f10932h.deviceName)) {
                            ALog.w("GatewayChannelImpl", "gateway device error.");
                            return;
                        }
                        if (a.this.f10946v.equals(str4)) {
                            ALog.i("GatewayChannelImpl", "device was forbidden. deviceInfo=" + subDeviceInfo);
                            return;
                        }
                        if (!a.this.f10945u.equals(str4) && !a.this.f10947w.equals(str4)) {
                            if (a.this.f10943s.equals(str4)) {
                                ALog.w("GatewayChannelImpl", "device login by other device. device need login again. devInfo=" + str3);
                                a.this.f10935k.remove(subDeviceInfo.getDeviceId());
                                return;
                            }
                            if (a.this.f10944t.equals(str4)) {
                                ALog.w("GatewayChannelImpl", "device session error. devInfo=" + str3);
                                a.this.f10935k.remove(subDeviceInfo.getDeviceId());
                                return;
                            }
                            return;
                        }
                        ALog.i("GatewayChannelImpl", "remove device topo relation! deviceInfo=" + subDeviceInfo);
                        a.this.f10935k.remove(subDeviceInfo.getDeviceId());
                        a.this.f10934j.remove(subDeviceInfo.getDeviceId());
                        a.this.f10933i.remove(subDeviceInfo.getDeviceId());
                    }
                }
            } catch (Exception unused) {
            }
        }

        @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
        public boolean shouldHandle(String str, String str2) {
            return (a.this.f10932h == null || TextUtils.isEmpty(a.this.f10942r) || !a.this.f10942r.equals(str2)) ? false : true;
        }
    };

    /* renamed from: com.aliyun.alink.linksdk.channel.gateway.a.a$a, reason: collision with other inner class name */
    private class C0072a implements IConnectSendListener {

        /* renamed from: b, reason: collision with root package name */
        private IGatewayRequestListener f10970b;

        public C0072a(IGatewayRequestListener iGatewayRequestListener) {
            this.f10970b = iGatewayRequestListener;
        }

        @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
        public void onFailure(ARequest aRequest, AError aError) {
            this.f10970b.onFailure(aError);
        }

        @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
        public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            Object obj;
            Object obj2;
            StringBuilder sb = new StringBuilder();
            sb.append("GatewayOnCallListener, onSuccess, rsp = ");
            sb.append((aResponse == null || (obj2 = aResponse.data) == null) ? TmpConstant.GROUP_ROLE_UNKNOWN : obj2.toString());
            ALog.d("GatewayChannelImpl", sb.toString());
            this.f10970b.onSuccess((aResponse == null || (obj = aResponse.data) == null) ? null : obj.toString());
        }
    }

    private class b implements IOnPushListener {
        private b() {
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.event.IOnPushListener
        public void onCommand(String str, byte[] bArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            String strB = a.this.b();
            if (!TextUtils.isEmpty(strB) && str.equals(strB)) {
                a.this.a(bArr);
            }
            if (a.this.f10932h == null || str.contains(a.this.f10932h.deviceName)) {
                return;
            }
            for (String str2 : a.this.f10934j.keySet()) {
                if (!TextUtils.isEmpty(str2) && str2.contains(GatewayChannel.DID_SEPARATOR)) {
                    try {
                        CharSequence charSequence = str2.split(GatewayChannel.DID_SEPARATOR)[0];
                        CharSequence charSequence2 = str2.split(GatewayChannel.DID_SEPARATOR)[1];
                        if (str.contains(charSequence) && str.contains(charSequence2)) {
                            ISubDeviceConnectListener iSubDeviceConnectListener = (ISubDeviceConnectListener) a.this.f10934j.get(str2);
                            AMessage aMessage = new AMessage();
                            aMessage.data = bArr;
                            iSubDeviceConnectListener.onDataPush(str, aMessage);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.event.IOnPushListener
        public boolean shouldHandle(String str) {
            return true;
        }
    }

    public a() {
        this.f10933i = null;
        this.f10934j = null;
        this.f10935k = null;
        this.f10933i = new ConcurrentHashMap();
        this.f10934j = new ConcurrentHashMap();
        this.f10935k = new ConcurrentHashMap();
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void addSubDevice(SubDeviceInfo subDeviceInfo, ISubDeviceConnectListener iSubDeviceConnectListener) {
        ALog.d("GatewayChannelImpl", "addSubDevice()");
        if (iSubDeviceConnectListener == null) {
            throw new IllegalArgumentException("addSubDevice listener cannot be null.");
        }
        if (subDeviceInfo == null || !subDeviceInfo.checkValid()) {
            ALog.d("GatewayChannelImpl", "addSubDevice(), params error");
            AError aError = new AError();
            aError.setCode(GatewayErrorCode.ERROR_INVOKE_PARAMS_INVALID);
            aError.setMsg("addSubDeviceForILopGlobal subDeviceInfoList empty.");
            iSubDeviceConnectListener.onConnectResult(false, null, aError);
            return;
        }
        if (this.f10931g == GatewayConnectState.CONNECTED) {
            this.f10934j.put(subDeviceInfo.getDeviceId(), iSubDeviceConnectListener);
            a(subDeviceInfo, iSubDeviceConnectListener);
        } else {
            AError aError2 = new AError();
            aError2.setCode(4101);
            aError2.setMsg("addSubDevice device not connected");
            iSubDeviceConnectListener.onConnectResult(false, null, aError2);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void addSubDeviceForILopGlobal(SubDeviceInfo subDeviceInfo, ISubDeviceConnectListener iSubDeviceConnectListener) {
        ALog.d("GatewayChannelImpl", "addSubDeviceForILopGlobal() called with: subDeviceInfo = [" + subDeviceInfo + "], subDeviceConnectListener = [" + iSubDeviceConnectListener + "]");
        if (iSubDeviceConnectListener == null) {
            throw new IllegalArgumentException("addSubDeviceForILopGlobal subDeviceConnectListener cannot be null.");
        }
        if (subDeviceInfo == null || !subDeviceInfo.checkValid()) {
            ALog.e("GatewayChannelImpl", "addSubDeviceForILopGlobal(), params error");
            AError aError = new AError();
            aError.setCode(GatewayErrorCode.ERROR_INVOKE_PARAMS_INVALID);
            aError.setMsg("addSubDeviceForILopGlobal subDeviceInfo invalid.");
            iSubDeviceConnectListener.onConnectResult(false, null, aError);
            return;
        }
        if (this.f10931g != GatewayConnectState.CONNECTED) {
            AError aError2 = new AError();
            aError2.setCode(4101);
            aError2.setMsg("addSubDeviceForILopGlobal device not connected");
            iSubDeviceConnectListener.onConnectResult(false, null, aError2);
            return;
        }
        PersistentConnectConfig persistentConnectConfig = this.f10932h;
        if (persistentConnectConfig != null && !TextUtils.isEmpty(persistentConnectConfig.productKey) && !TextUtils.isEmpty(this.f10932h.deviceName)) {
            this.f10934j.put(subDeviceInfo.getDeviceId(), iSubDeviceConnectListener);
            b(subDeviceInfo, iSubDeviceConnectListener);
        } else {
            AError aError3 = new AError();
            aError3.setCode(4202);
            aError3.setMsg("addSubDeviceForILopGlobal gateway connect info not set.");
            iSubDeviceConnectListener.onConnectResult(false, null, aError3);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void asyncSendRequest(String str, String str2, Map<String, Object> map, Object obj, IGatewayRequestListener iGatewayRequestListener) {
        ALog.d("GatewayChannelImpl", "asyncSendRequest()");
        if (TextUtils.isEmpty(str)) {
            ALog.e("GatewayChannelImpl", "asyncSendRequest(), params error");
            return;
        }
        if (this.f10931g == GatewayConnectState.CONNECTED) {
            ConnectSDK.getInstance().send(new com.aliyun.alink.linksdk.channel.gateway.a.b(true, this.f10932h, str, str2, map, obj), new C0072a(iGatewayRequestListener));
        } else if (iGatewayRequestListener != null) {
            AError aError = new AError();
            aError.setCode(4101);
            aError.setMsg("device not connected");
            iGatewayRequestListener.onFailure(aError);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void ayncSendPublishRequest(String str, String str2, Map<String, Object> map, Object obj, IGatewayRequestListener iGatewayRequestListener) {
        ALog.d("GatewayChannelImpl", "ayncSendPublishRequest()");
        if (TextUtils.isEmpty(str)) {
            ALog.e("GatewayChannelImpl", "ayncSendPublishRequest(), params error");
            return;
        }
        if (this.f10931g == GatewayConnectState.CONNECTED) {
            ConnectSDK.getInstance().send(new com.aliyun.alink.linksdk.channel.gateway.a.b(false, this.f10932h, str, str2, map, obj), new C0072a(iGatewayRequestListener));
        } else if (iGatewayRequestListener != null) {
            AError aError = new AError();
            aError.setCode(4101);
            aError.setMsg("device not connected");
            iGatewayRequestListener.onFailure(aError);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void destroyConnect() {
        try {
            this.f10941q = false;
            this.f10931g = GatewayConnectState.DISCONNECTED;
            Map<String, ISubDeviceChannel> map = this.f10935k;
            if (map != null) {
                map.clear();
            }
            Map<String, ISubDeviceConnectListener> map2 = this.f10934j;
            if (map2 != null) {
                map2.clear();
            }
            Map<String, SubDeviceInfo> map3 = this.f10933i;
            if (map3 != null) {
                map3.clear();
            }
            ConnectManager.getInstance().unregisterConnect(ConnectSDK.getInstance().getPersistentConnectId());
        } catch (Exception e2) {
            ALog.w("GatewayChannelImpl", "destroyConnect exception=" + e2);
        }
        try {
            ConnectSDK.getInstance().unregisterNofityListener(this.H);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void deviceListUpload(List<SubDeviceInfo> list, IConnectSendListener iConnectSendListener) {
        ALog.d("GatewayChannelImpl", "deviceListUpload()");
        if (list != null && list.size() >= 1) {
            ConnectSDK.getInstance().send(new com.aliyun.alink.linksdk.channel.gateway.a.b(true, this.f10932h, "thing/list/found", "thing.list.found", null, list), iConnectSendListener);
        } else if (iConnectSendListener != null) {
            AError aError = new AError();
            aError.setCode(1101400);
            aError.setMsg("deviceListUpload infoList empty");
            iConnectSendListener.onFailure(null, aError);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public GatewayConnectState getGatewayConnectState() {
        return this.f10931g;
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public PersistentConnectConfig getPersistentConnectConfig() {
        return this.f10932h;
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public ISubDeviceChannel getSubDeviceChannel(String str) {
        Map<String, ISubDeviceChannel> map = this.f10935k;
        if (map == null || !map.containsKey(str)) {
            return null;
        }
        return this.f10935k.get(str);
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void getSubDevices(IConnectSendListener iConnectSendListener) {
        a(iConnectSendListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void registerDownstreamListener(boolean z2, IGatewayDownstreamListener iGatewayDownstreamListener) {
        PersistentEventDispatcher.getInstance().registerOnPushListener(iGatewayDownstreamListener, z2);
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void removeSubDevice(SubDeviceInfo subDeviceInfo, ISubDeviceRemoveListener iSubDeviceRemoveListener) {
        ALog.d("GatewayChannelImpl", "removeSubDevice()");
        if (subDeviceInfo == null || !subDeviceInfo.checkValid()) {
            ALog.d("GatewayChannelImpl", "removeSubDevice(), params error");
            return;
        }
        if (this.f10931g == GatewayConnectState.CONNECTED) {
            a(subDeviceInfo, iSubDeviceRemoveListener);
        } else if (iSubDeviceRemoveListener != null) {
            AError aError = new AError();
            aError.setCode(4101);
            aError.setMsg("removeSubDevice device not connected");
            iSubDeviceRemoveListener.onFailed(aError);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void startConnect(Context context, PersistentConnectConfig persistentConnectConfig, IGatewayConnectListener iGatewayConnectListener) {
        GatewayConnectState gatewayConnectState;
        ALog.d("GatewayChannelImpl", "startConnect()");
        if (context == null || !a(persistentConnectConfig)) {
            ALog.e("GatewayChannelImpl", "startConnect(), param error, config is empty");
            return;
        }
        this.f10929e = context;
        this.f10930f = iGatewayConnectListener;
        this.f10932h = persistentConnectConfig;
        this.f10942r = "/ext/error/" + this.f10932h.productKey + "/" + this.f10932h.deviceName;
        if (this.f10941q || (gatewayConnectState = this.f10931g) == GatewayConnectState.CONNECTING || gatewayConnectState == GatewayConnectState.CONNECTED) {
            ALog.d("GatewayChannelImpl", "startConnect(), channel is connecting or connected");
        } else {
            a(context, persistentConnectConfig);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void startConnectReuseMobileChannel(Context context, IGatewayConnectListener iGatewayConnectListener) {
        String str;
        String str2;
        ALog.d("GatewayChannelImpl", "startConnectReuseMobileChannel()");
        String clientId = MobileChannel.getInstance().getClientId();
        if (TextUtils.isEmpty(clientId)) {
            str = "";
            str2 = str;
        } else {
            str2 = clientId.split("&")[1];
            str = clientId.split("&")[0];
        }
        GatewayConnectConfig gatewayConnectConfig = new GatewayConnectConfig(str2, str, "");
        if (a(gatewayConnectConfig)) {
            startConnect(context, gatewayConnectConfig, iGatewayConnectListener);
            return;
        }
        ALog.d("GatewayChannelImpl", "startConnectReuseMobileChannel(), get mobile client id error,mark sure MobileConnect connected firstly");
        if (iGatewayConnectListener != null) {
            iGatewayConnectListener.onConnectStateChange(GatewayConnectState.CONNECTFAIL);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void subDevicUnregister(List<SubDeviceInfo> list, IConnectSendListener iConnectSendListener) {
        ALog.d("GatewayChannelImpl", "subDevicUnregister() called， interface deprecated!");
        if (iConnectSendListener != null) {
            AError aError = new AError();
            aError.setCode(1102001);
            aError.setMsg("interface deprecated!");
            iConnectSendListener.onFailure(null, aError);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void subDeviceRegister(List<SubDeviceInfo> list, IConnectSendListener iConnectSendListener) {
        ALog.d("GatewayChannelImpl", "subDeviceRegister()");
        if (list == null || list.size() < 1) {
            if (iConnectSendListener != null) {
                AError aError = new AError();
                aError.setCode(1101400);
                aError.setMsg("subDeviceRegister infoList empty");
                iConnectSendListener.onFailure(null, aError);
                return;
            }
            return;
        }
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < list.size(); i2++) {
            SubDeviceInfo subDeviceInfo = list.get(i2);
            if (subDeviceInfo != null && !TextUtils.isEmpty(subDeviceInfo.productKey) && !TextUtils.isEmpty(subDeviceInfo.deviceName)) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("deviceName", (Object) subDeviceInfo.deviceName);
                jSONObject.put("productKey", (Object) subDeviceInfo.productKey);
                jSONArray.add(jSONObject);
            }
        }
        ConnectSDK.getInstance().send(new com.aliyun.alink.linksdk.channel.gateway.a.b(true, this.f10932h, this.f10925a, this.f10926b, null, jSONArray), iConnectSendListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void subscribe(String str, IGatewaySubscribeListener iGatewaySubscribeListener) {
        PersistentConnectConfig persistentConnectConfig;
        ALog.d("GatewayChannelImpl", "subscribe(), topic = " + str);
        if (TextUtils.isEmpty(str)) {
            ALog.e("GatewayChannelImpl", "subscribe(), topic is empty!");
            return;
        }
        if (this.f10931g != GatewayConnectState.CONNECTED) {
            if (iGatewaySubscribeListener != null) {
                AError aError = new AError();
                aError.setCode(4101);
                aError.setMsg("subscribe device not connected");
                iGatewaySubscribeListener.onFailure(aError);
                return;
            }
            return;
        }
        if (!str.startsWith("/sys/") && !str.startsWith("/ota/") && (persistentConnectConfig = this.f10932h) != null && a(persistentConnectConfig)) {
            str = ("/sys/" + this.f10932h.productKey + "/" + this.f10932h.deviceName + "/" + str).replace("//", "/");
        }
        MqttSubscribeRequest mqttSubscribeRequest = new MqttSubscribeRequest();
        mqttSubscribeRequest.topic = str;
        ConnectSDK.getInstance().subscribe(ConnectSDK.getInstance().getPersistentConnectId(), mqttSubscribeRequest, iGatewaySubscribeListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void unRegisterDownstreamListener(IGatewayDownstreamListener iGatewayDownstreamListener) {
        PersistentEventDispatcher.getInstance().unregisterOnPushListener(iGatewayDownstreamListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void unSubscribe(String str, IGatewaySubscribeListener iGatewaySubscribeListener) {
        ALog.d("GatewayChannelImpl", "unSubscribe()");
        if (TextUtils.isEmpty(str)) {
            ALog.e("GatewayChannelImpl", "unSubscribe(), topic is empty!");
            return;
        }
        if (this.f10931g != GatewayConnectState.CONNECTED) {
            if (iGatewaySubscribeListener != null) {
                AError aError = new AError();
                aError.setCode(4101);
                aError.setMsg("subscribe device not connected");
                iGatewaySubscribeListener.onFailure(aError);
                return;
            }
            return;
        }
        if (!str.startsWith("/sys/") && !str.startsWith("/ota/") && a(this.f10932h)) {
            str = ("/sys/" + this.f10932h.productKey + "/" + this.f10932h.deviceName + "/" + str).replace("//", "/");
        }
        MqttSubscribeRequest mqttSubscribeRequest = new MqttSubscribeRequest();
        mqttSubscribeRequest.topic = str;
        ConnectSDK.getInstance().subscribe(ConnectSDK.getInstance().getPersistentConnectId(), mqttSubscribeRequest, iGatewaySubscribeListener);
    }

    private void b(SubDeviceInfo subDeviceInfo, final ISubDeviceConnectListener iSubDeviceConnectListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String clientId;
        ALog.d("GatewayChannelImpl", "topoAddForILopGlobal()");
        if (iSubDeviceConnectListener == null) {
            if (iSubDeviceConnectListener != null) {
                AError aError = new AError();
                aError.setCode(GatewayErrorCode.ERROR_INVOKE_PARAMS_INVALID);
                aError.setMsg("topoAddForILopGlobal subDeviceInfoListener empty.");
                iSubDeviceConnectListener.onConnectResult(false, null, aError);
                return;
            }
            return;
        }
        if (TextUtils.isEmpty(iSubDeviceConnectListener.getSignMethod())) {
            AError aError2 = new AError();
            aError2.setCode(GatewayErrorCode.ERROR_INVOKE_PARAMS_INVALID);
            aError2.setMsg("topoAddForILopGlobal sign method empty.");
            iSubDeviceConnectListener.onConnectResult(false, null, aError2);
            return;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("identifier", "_LivingLink.activation.subdevice.connect");
        JSONObject jSONObject2 = new JSONObject();
        jSONObject.put("serviceParams", (Object) jSONObject2);
        JSONArray jSONArray = new JSONArray();
        final String string = UUID.randomUUID().toString();
        jSONObject2.put("requestId", (Object) string);
        jSONObject2.put("version", "2.0");
        jSONObject2.put("DeviceList", (Object) jSONArray);
        JSONObject jSONObject3 = new JSONObject();
        if (TextUtils.isEmpty(iSubDeviceConnectListener.getClientId())) {
            clientId = subDeviceInfo.deviceName + "&" + subDeviceInfo.productKey;
        } else {
            clientId = iSubDeviceConnectListener.getClientId();
        }
        jSONObject3.put(TmpConstant.KEY_CLIENT_ID, (Object) clientId);
        jSONObject3.put("sign", (Object) com.aliyun.alink.linksdk.channel.gateway.b.a.a(String.format("clientId%sdeviceName%sdeviceSecret%sproductKey%s", clientId, subDeviceInfo.deviceName, TextUtils.isEmpty(this.f10932h.deviceSecret) ? MqttConfigure.deviceSecret : this.f10932h.deviceSecret, subDeviceInfo.productKey), iSubDeviceConnectListener.getSignMethod()));
        jSONObject3.put(TmpConstant.KEY_SIGN_METHOD, (Object) iSubDeviceConnectListener.getSignMethod());
        jSONObject3.put("subSign", (Object) iSubDeviceConnectListener.getSignValue());
        if (!TextUtils.isEmpty(subDeviceInfo.resetFlag)) {
            jSONObject3.put("reset", (Object) subDeviceInfo.resetFlag);
        }
        Map<String, Object> signExtraData = iSubDeviceConnectListener.getSignExtraData();
        if (signExtraData != null && !signExtraData.isEmpty()) {
            ALog.d("GatewayChannelImpl", "topoAddForILopGlobal(), get extra data " + signExtraData);
            jSONObject3.putAll(signExtraData);
        }
        if (!"true".equals(jSONObject3.getString("cleanSession")) && !RequestConstant.FALSE.equals(jSONObject3.getString("cleanSession"))) {
            jSONObject3.put("cleanSession", (Object) "true");
        }
        jSONObject3.put("deviceName", (Object) subDeviceInfo.deviceName);
        jSONObject3.put("productKey", (Object) subDeviceInfo.productKey);
        jSONArray.add(jSONObject3);
        if (!TextUtils.isEmpty(this.f10932h.productKey) && !TextUtils.isEmpty(this.f10932h.deviceName)) {
            String strB = b();
            if (!this.f10938n.contains(strB)) {
                a(string, strB, iSubDeviceConnectListener);
            }
        }
        synchronized (this.f10940p) {
            this.f10940p.put(string, new SubDeviceInfoWrapper(subDeviceInfo, iSubDeviceConnectListener));
        }
        ConnectSDK.getInstance().send(new com.aliyun.alink.linksdk.channel.gateway.a.b(true, this.f10932h, "_thing/service/post", "_thing.service.post", null, jSONObject), new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.channel.gateway.a.a.4
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onFailure(ARequest aRequest, AError aError3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.w("GatewayChannelImpl", "topoAddForILopGlobal(), onConnectResult onFailed " + a.this.a(aError3));
                synchronized (a.this.f10940p) {
                    a.this.f10940p.remove(string);
                }
                iSubDeviceConnectListener.onConnectResult(false, null, aError3);
            }

            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                Object obj;
                StringBuilder sb = new StringBuilder();
                sb.append("topoAddForILopGlobal(), onSuceess, rsp = ");
                if (aResponse == null || (obj = aResponse.data) == null) {
                    obj = "";
                }
                sb.append(obj);
                ALog.d("GatewayChannelImpl", sb.toString());
                try {
                    int intValue = JSON.parseObject((String) aResponse.data).getIntValue("code");
                    if (intValue == 200) {
                        ALog.d("GatewayChannelImpl", "topoAddForILopGlobal service/post success，wait for async notify.");
                        return;
                    }
                    AError aError3 = new AError();
                    aError3.setCode(intValue);
                    aError3.setMsg("topoAddForILopGlobal failed, server error code =" + intValue);
                    synchronized (a.this.f10940p) {
                        a.this.f10940p.remove(string);
                    }
                    ALog.w("GatewayChannelImpl", "topoAddForILopGlobal(), onConnectResult onFailed " + a.this.a(aError3));
                    iSubDeviceConnectListener.onConnectResult(false, null, aError3);
                } catch (Exception e2) {
                    ALog.d("GatewayChannelImpl", "topoAddForILopGlobal(), onSuccess(), parse error, e" + e2.toString());
                    e2.printStackTrace();
                    AError aError4 = new AError();
                    aError4.setCode(4103);
                    aError4.setMsg("reqSuccess, parse error, e" + e2.toString());
                    synchronized (a.this.f10940p) {
                        a.this.f10940p.remove(string);
                        ALog.w("GatewayChannelImpl", "topoAddForILopGlobal(), onConnectResult onFailed " + a.this.a(aError4) + ", e=" + e2);
                        iSubDeviceConnectListener.onConnectResult(false, null, aError4);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d("GatewayChannelImpl", "autoLogin() called");
        if (this.f10937m.get()) {
            for (Map.Entry<String, ISubDeviceChannel> entry : this.f10935k.entrySet()) {
                if (entry != null) {
                    ISubDeviceChannel value = entry.getValue();
                    if (value.getSubDeviceInfo() != null && value.getSubDeviceInfo().checkValid()) {
                        if (ConnectState.CONNECTED != ConnectSDK.getInstance().getConnectState(ConnectSDK.getInstance().getPersistentConnectId())) {
                            return;
                        }
                        if (value.getSubDeviceInfo().loginState == SubDeviceLoginState.ONLINE) {
                            ALog.d("GatewayChannelImpl", "autoLogin onLine & enabled. entry=" + entry);
                            value.online(new ISubDeviceActionListener() { // from class: com.aliyun.alink.linksdk.channel.gateway.a.a.8
                                @Override // com.aliyun.alink.linksdk.channel.gateway.api.subdevice.ISubDeviceActionListener
                                public void onFailed(AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                                    String str;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("autoLogin aError=");
                                    if (aError == null) {
                                        str = "";
                                    } else {
                                        str = aError.getCode() + aError.getMsg();
                                    }
                                    sb.append(str);
                                    ALog.d("GatewayChannelImpl", sb.toString());
                                }

                                @Override // com.aliyun.alink.linksdk.channel.gateway.api.subdevice.ISubDeviceActionListener
                                public void onSuccess() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                                    ALog.d("GatewayChannelImpl", "autoLogin onSuccess");
                                }
                            });
                        } else {
                            ALog.w("GatewayChannelImpl", "autoLogin offline or disabled. entry=" + entry);
                        }
                    }
                }
            }
        }
    }

    public void a(boolean z2) {
        this.f10937m.set(z2);
    }

    private boolean a(PersistentConnectConfig persistentConnectConfig) {
        return (persistentConnectConfig == null || TextUtils.isEmpty(persistentConnectConfig.productKey) || TextUtils.isEmpty(persistentConnectConfig.deviceName)) ? false : true;
    }

    private void a(Context context, PersistentConnectConfig persistentConnectConfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        GatewayConnectState gatewayConnectState;
        GatewayConnectState gatewayConnectState2;
        GatewayConnectState gatewayConnectState3;
        ALog.d("GatewayChannelImpl", "connect()");
        if (!this.f10941q && (gatewayConnectState = this.f10931g) != (gatewayConnectState2 = GatewayConnectState.CONNECTING) && gatewayConnectState != (gatewayConnectState3 = GatewayConnectState.CONNECTED)) {
            ConnectSDK.getInstance().registerNofityListener(ConnectSDK.getInstance().getPersistentConnectId(), this.H);
            if (PersistentNet.getInstance().getConnectState() == PersistentConnectState.CONNECTED) {
                ALog.d("GatewayChannelImpl", "connect(), Persistent already Connected!");
                this.f10931g = gatewayConnectState3;
                this.f10941q = true;
                IGatewayConnectListener iGatewayConnectListener = this.f10930f;
                if (iGatewayConnectListener != null) {
                    iGatewayConnectListener.onConnectStateChange(gatewayConnectState3);
                }
                a();
                return;
            }
            ALog.d("GatewayChannelImpl", "connect(), connecting...");
            this.f10931g = gatewayConnectState2;
            IGatewayConnectListener iGatewayConnectListener2 = this.f10930f;
            if (iGatewayConnectListener2 != null) {
                iGatewayConnectListener2.onConnectStateChange(gatewayConnectState2);
            }
            ConnectSDK.getInstance().registerPersistentConnect(context, persistentConnectConfig, new IRegisterConnectListener() { // from class: com.aliyun.alink.linksdk.channel.gateway.a.a.1
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
                public void onFailure(AError aError) {
                    a.this.f10931g = GatewayConnectState.CONNECTFAIL;
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
                public void onSuccess() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    a.this.f10931g = GatewayConnectState.CONNECTED;
                    a.this.f10941q = true;
                    a.this.a();
                }
            });
            return;
        }
        ALog.d("GatewayChannelImpl", "connect(), channel is connecting or connected now");
    }

    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayChannel
    public void subDeviceRegister(ARequest aRequest, IConnectSendListener iConnectSendListener) {
        ALog.i("GatewayChannelImpl", "subDeviceRegister() called with: requestData = [" + aRequest + "], listener = [" + iConnectSendListener + "]");
        if (!(aRequest instanceof MqttPublishRequest) || iConnectSendListener == null) {
            if (iConnectSendListener != null) {
                AError aError = new AError();
                aError.setCode(1102000);
                aError.setMsg("subDeviceRegister listener is null or requestData not instance of MqttPublishRequest.");
                iConnectSendListener.onFailure(null, aError);
                return;
            }
            return;
        }
        MqttPublishRequest mqttPublishRequest = (MqttPublishRequest) aRequest;
        if (mqttPublishRequest.payloadObj == null) {
            AError aError2 = new AError();
            aError2.setCode(1102000);
            aError2.setMsg("subDeviceRegister request payload is null.");
            iConnectSendListener.onFailure(null, aError2);
            return;
        }
        if (TextUtils.isEmpty(mqttPublishRequest.topic)) {
            mqttPublishRequest.topic = "/sys/" + this.f10932h.productKey + "/" + this.f10932h.deviceName + GatewayChannel.TOPIC_PRESET_SUBDEV_REGITER;
            mqttPublishRequest.isRPC = true;
        }
        try {
            ConnectSDK.getInstance().send(aRequest, iConnectSendListener);
        } catch (Exception e2) {
            AError aError3 = new AError();
            aError3.setCode(4201);
            aError3.setMsg("subDeviceRegister send exception=" + e2);
            iConnectSendListener.onFailure(aRequest, aError3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a((IConnectSendListener) null);
        if (this.f10936l == null) {
            this.f10936l = new b();
            PersistentEventDispatcher.getInstance().registerOnPushListener(this.f10936l, true);
        }
    }

    private void a(final IConnectSendListener iConnectSendListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d("GatewayChannelImpl", "topoGetReq()");
        ConnectSDK.getInstance().send(new com.aliyun.alink.linksdk.channel.gateway.a.b(true, this.f10932h, "thing/topo/get", "thing.topo.get", null, null), new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.channel.gateway.a.a.2
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d("GatewayChannelImpl", "topoGetReq(), onFailed");
                IConnectSendListener iConnectSendListener2 = iConnectSendListener;
                if (iConnectSendListener2 != null) {
                    iConnectSendListener2.onFailure(aRequest, aError);
                }
            }

            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                Object obj;
                JSONArray jSONArray;
                StringBuilder sb = new StringBuilder();
                sb.append("topoGetReq(), onSuceess, rsp = ");
                if (aResponse == null || (obj = aResponse.data) == null) {
                    obj = "";
                }
                sb.append(obj);
                ALog.d("GatewayChannelImpl", sb.toString());
                try {
                    JSONObject object = JSON.parseObject((String) aResponse.data);
                    if (200 == object.getIntValue("code") && (jSONArray = object.getJSONArray("data")) != null && jSONArray.size() != 0) {
                        for (int i2 = 0; i2 < jSONArray.size(); i2++) {
                            SubDeviceInfo subDeviceInfo = (SubDeviceInfo) jSONArray.getObject(i2, SubDeviceInfo.class);
                            a.this.f10933i.put(subDeviceInfo.getDeviceId(), subDeviceInfo);
                        }
                    }
                } catch (Exception e2) {
                    ALog.d("GatewayChannelImpl", "topoGetReq(), onSuccess(), parse error, e" + e2.toString());
                    e2.printStackTrace();
                }
                IConnectSendListener iConnectSendListener2 = iConnectSendListener;
                if (iConnectSendListener2 != null) {
                    iConnectSendListener2.onResponse(aRequest, aResponse);
                }
            }
        });
    }

    private void a(final SubDeviceInfo subDeviceInfo, final ISubDeviceConnectListener iSubDeviceConnectListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String clientId;
        ALog.d("GatewayChannelImpl", "topoAdd()");
        if (subDeviceInfo == null || !subDeviceInfo.checkValid()) {
            ALog.e("GatewayChannelImpl", "topoAdd param:suddevice info invalid.");
            return;
        }
        if (iSubDeviceConnectListener == null) {
            ALog.e("GatewayChannelImpl", "topoAdd param:listener cannot be null.");
            return;
        }
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("sign", (Object) iSubDeviceConnectListener.getSignValue());
        jSONObject.put(TmpConstant.KEY_SIGN_METHOD, (Object) iSubDeviceConnectListener.getSignMethod());
        Map<String, Object> signExtraData = iSubDeviceConnectListener.getSignExtraData();
        if (signExtraData != null && !signExtraData.isEmpty()) {
            ALog.d("GatewayChannelImpl", "topoAdd(), get extra data " + signExtraData);
            jSONObject.putAll(signExtraData);
        }
        jSONObject.put("deviceName", (Object) subDeviceInfo.deviceName);
        jSONObject.put("productKey", (Object) subDeviceInfo.productKey);
        if (TextUtils.isEmpty(iSubDeviceConnectListener.getClientId())) {
            clientId = subDeviceInfo.deviceName + "&" + subDeviceInfo.productKey;
        } else {
            clientId = iSubDeviceConnectListener.getClientId();
        }
        jSONObject.put(TmpConstant.KEY_CLIENT_ID, (Object) clientId);
        jSONArray.add(jSONObject);
        ConnectSDK.getInstance().send(new com.aliyun.alink.linksdk.channel.gateway.a.b(true, this.f10932h, "thing/topo/add", "thing.topo.add", null, jSONArray), new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.channel.gateway.a.a.3
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d("GatewayChannelImpl", "topoAdd(), onFailed");
                iSubDeviceConnectListener.onConnectResult(false, null, aError);
            }

            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                Object obj;
                ISubDeviceChannel cVar;
                StringBuilder sb = new StringBuilder();
                sb.append("topoAdd(), onSuceess, rsp = ");
                if (aResponse == null || (obj = aResponse.data) == null) {
                    obj = "";
                }
                sb.append(obj);
                ALog.d("GatewayChannelImpl", sb.toString());
                try {
                    int intValue = JSON.parseObject((String) aResponse.data).getIntValue("code");
                    if (intValue == 200) {
                        a.this.f10933i.put(subDeviceInfo.getDeviceId(), subDeviceInfo);
                        if (a.this.f10935k.containsKey(subDeviceInfo.getDeviceId())) {
                            cVar = (ISubDeviceChannel) a.this.f10935k.get(subDeviceInfo.getDeviceId());
                        } else {
                            cVar = new c(subDeviceInfo, iSubDeviceConnectListener);
                            a.this.f10935k.put(subDeviceInfo.getDeviceId(), cVar);
                        }
                        iSubDeviceConnectListener.onConnectResult(true, cVar, null);
                        return;
                    }
                    AError aError = new AError();
                    aError.setCode(intValue);
                    aError.setMsg("topo add failed, server error code =" + intValue);
                    iSubDeviceConnectListener.onConnectResult(false, null, aError);
                } catch (Exception e2) {
                    ALog.d("GatewayChannelImpl", "topoAdd(), onSuccess(), parse error, e" + e2.toString());
                    e2.printStackTrace();
                    AError aError2 = new AError();
                    aError2.setCode(4103);
                    aError2.setMsg("reqSuccess, parse error, e" + e2.toString());
                    iSubDeviceConnectListener.onConnectResult(false, null, aError2);
                }
            }
        });
    }

    private void a(final String str, final String str2, final ISubDeviceConnectListener iSubDeviceConnectListener) {
        subscribe(str2, new IGatewaySubscribeListener() { // from class: com.aliyun.alink.linksdk.channel.gateway.a.a.5
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
            public void onFailure(AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.e("GatewayChannelImpl", "subscribeThingEventNotify failed requestId = " + str + "aError=" + a.this.a(aError));
                if (iSubDeviceConnectListener != null) {
                    synchronized (a.this.f10940p) {
                        a.this.f10940p.remove(str);
                    }
                    iSubDeviceConnectListener.onConnectResult(false, null, aError);
                }
            }

            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
            public void onSuccess() {
                a.this.f10938n.add(str2);
            }
        });
    }

    public String a(AError aError) {
        if (aError == null) {
            return null;
        }
        return "code=" + aError.getCode() + ", subCode=" + aError.getSubCode() + ", msg=" + aError.getMsg() + ", subMsg=" + aError.getSubMsg();
    }

    private void a(SubDeviceInfo subDeviceInfo, final ISubDeviceRemoveListener iSubDeviceRemoveListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d("GatewayChannelImpl", "topoDelete()");
        if (subDeviceInfo == null || !subDeviceInfo.checkValid()) {
            return;
        }
        this.f10933i.remove(subDeviceInfo.getDeviceId());
        this.f10934j.remove(subDeviceInfo.getDeviceId());
        this.f10935k.remove(subDeviceInfo.getDeviceId());
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("deviceName", (Object) subDeviceInfo.deviceName);
        jSONObject.put("productKey", (Object) subDeviceInfo.productKey);
        jSONArray.add(jSONObject);
        ConnectSDK.getInstance().send(new com.aliyun.alink.linksdk.channel.gateway.a.b(true, this.f10932h, "thing/topo/delete", "thing.topo.delete", null, jSONArray), new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.channel.gateway.a.a.6
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d("GatewayChannelImpl", "topoDelete(), onFailed");
                iSubDeviceRemoveListener.onFailed(aError);
            }

            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                Object obj;
                StringBuilder sb = new StringBuilder();
                sb.append("topoDelete(), onSuceess, rsp = ");
                if (aResponse == null || (obj = aResponse.data) == null) {
                    obj = "";
                }
                sb.append(obj);
                ALog.d("GatewayChannelImpl", sb.toString());
                try {
                    String string = JSON.parseObject((String) aResponse.data).getString("code");
                    if ("200".equals(string)) {
                        ISubDeviceRemoveListener iSubDeviceRemoveListener2 = iSubDeviceRemoveListener;
                        if (iSubDeviceRemoveListener2 != null) {
                            iSubDeviceRemoveListener2.onSuceess();
                            return;
                        }
                        return;
                    }
                    AError aError = new AError();
                    if (!TextUtils.isEmpty(string)) {
                        aError.setCode(Integer.getInteger(string).intValue());
                    }
                    aError.setMsg("code =" + string);
                    iSubDeviceRemoveListener.onFailed(aError);
                } catch (Exception e2) {
                    ALog.d("GatewayChannelImpl", "topoDelete(), onSuccess(), parse error, e" + e2.toString());
                    e2.printStackTrace();
                    AError aError2 = new AError();
                    aError2.setMsg("reqSuccess, parse error, e" + e2.toString());
                    iSubDeviceRemoveListener.onFailed(aError2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String b() {
        PersistentConnectConfig persistentConnectConfig = this.f10932h;
        if (persistentConnectConfig == null || TextUtils.isEmpty(persistentConnectConfig.productKey) || TextUtils.isEmpty(this.f10932h.deviceName)) {
            return null;
        }
        return "/sys/" + this.f10932h.productKey + "/" + this.f10932h.deviceName + "/_thing/event/notify";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(byte[] bArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        JSONObject jSONObject;
        String string;
        ALog.d("GatewayChannelImpl", "handleThingEventNotify() called with: data = [" + bArr + "]");
        try {
            jSONObject = JSON.parseObject(new String(bArr)).getJSONObject("params");
            string = jSONObject.getString("identifier");
        } catch (Exception e2) {
            ALog.w("GatewayChannelImpl", "topoAddForILopGlobal fail, data error " + e2);
            return;
        }
        if (!"_LivingLink.activation.subdevice.connect".equals(string)) {
            ALog.w("GatewayChannelImpl", "_thing/event/notify identifier invalid " + string);
            return;
        }
        JSONObject jSONObject2 = jSONObject.getJSONObject("value");
        int intValue = jSONObject2.getIntValue("bizCode");
        String string2 = jSONObject2.getString("requestId");
        if (TextUtils.isEmpty(string2)) {
            ALog.d("GatewayChannelImpl", "invalid requestId, return " + string2);
            return;
        }
        SubDeviceInfoWrapper subDeviceInfoWrapper = this.f10940p.get(string2);
        if (subDeviceInfoWrapper != null && subDeviceInfoWrapper.deviceInfo != null && subDeviceInfoWrapper.connectListener != null) {
            if (intValue == 200) {
                JSONArray jSONArray = jSONObject2.getJSONArray("DeviceList");
                if (jSONArray != null && jSONArray.size() >= 1) {
                    int size = jSONArray.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        JSONObject jSONObject3 = jSONArray.getJSONObject(i2);
                        if (jSONObject3 != null && subDeviceInfoWrapper.deviceInfo != null && !TextUtils.isEmpty(jSONObject3.getString("deviceName")) && !TextUtils.isEmpty("productKey") && jSONObject3.getString("deviceName").equals(subDeviceInfoWrapper.deviceInfo.deviceName) && jSONObject3.getString("productKey").equals(subDeviceInfoWrapper.deviceInfo.productKey)) {
                            String string3 = jSONObject3.getString(com.taobao.agoo.a.a.b.JSON_ERRORCODE);
                            if ("0".equals(string3)) {
                                this.f10933i.put(subDeviceInfoWrapper.deviceInfo.getDeviceId(), subDeviceInfoWrapper.deviceInfo);
                                c cVar = new c(subDeviceInfoWrapper.deviceInfo, subDeviceInfoWrapper.connectListener);
                                this.f10935k.put(subDeviceInfoWrapper.deviceInfo.getDeviceId(), cVar);
                                synchronized (this.f10940p) {
                                    this.f10940p.remove(string2);
                                }
                                ALog.i("GatewayChannelImpl", "topoAddForILopGlobal onConnectResult success, requestId=" + string2);
                                subDeviceInfoWrapper.connectListener.onConnectResult(true, cVar, null);
                                return;
                            }
                            AError aError = new AError();
                            aError.setCode(a(string3));
                            aError.setMsg("topoAddForILopGlobal failed, server error resultCode(1:bindAlready-2:signError-3:addTopoError-4:devNotFound-5:TBD) =" + string3);
                            synchronized (this.f10940p) {
                                this.f10940p.remove(string2);
                            }
                            ALog.w("GatewayChannelImpl", "topoAddForILopGlobal onConnectResult fail, requestId=" + string2 + ", error=" + aError.getMsg());
                            subDeviceInfoWrapper.connectListener.onConnectResult(false, null, aError);
                            return;
                        }
                    }
                } else {
                    AError aError2 = new AError();
                    aError2.setCode(4103);
                    aError2.setMsg("reqSuccess, deviceList is empty");
                    synchronized (this.f10940p) {
                        this.f10940p.remove(string2);
                    }
                    ALog.w("GatewayChannelImpl", "topoAddForILopGlobal onConnectResult fail, requestId=" + string2 + ", error=" + aError2.getMsg());
                    subDeviceInfoWrapper.connectListener.onConnectResult(false, null, aError2);
                    return;
                }
                ALog.w("GatewayChannelImpl", "topoAddForILopGlobal fail, data error " + e2);
                return;
            }
            AError aError3 = new AError();
            aError3.setCode(intValue);
            aError3.setMsg("topoAddForILopGlobal failed, server error code =" + intValue);
            synchronized (this.f10940p) {
                this.f10940p.remove(string2);
            }
            ALog.w("GatewayChannelImpl", "topoAddForILopGlobal onConnectResult fail, requestId=" + string2 + ", error=" + aError3.getMsg());
            subDeviceInfoWrapper.connectListener.onConnectResult(false, null, aError3);
            return;
        }
        ALog.w("GatewayChannelImpl", "requestId with no listener.");
    }

    private int a(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return -1;
        }
    }
}
