package com.aliyun.alink.linksdk.tmp.connect.entity.cmp;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgr;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.cmp.api.CommonResource;
import com.aliyun.alink.linksdk.cmp.api.ConnectSDK;
import com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsServerConnectOption;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResource;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectDiscovery;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectResourceRegister;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.data.discovery.DiscoveryConfig;
import com.aliyun.alink.linksdk.tmp.device.payload.CommonRequestPayload;
import com.aliyun.alink.linksdk.tmp.event.INotifyHandler;
import com.aliyun.alink.linksdk.tmp.listener.IDevStateChangeListener;
import com.aliyun.alink.linksdk.tmp.listener.IPublishResourceListener;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class a extends com.aliyun.alink.linksdk.tmp.connect.b {

    /* renamed from: d, reason: collision with root package name */
    protected static final String f11151d = "[Tmp]CmpConnect";

    /* renamed from: e, reason: collision with root package name */
    protected String f11152e;

    /* renamed from: f, reason: collision with root package name */
    protected Map<String, AResource> f11153f = new HashMap();

    /* renamed from: g, reason: collision with root package name */
    protected String f11154g;

    /* renamed from: h, reason: collision with root package name */
    protected String f11155h;

    /* renamed from: i, reason: collision with root package name */
    protected PalDeviceInfo f11156i;

    public a(String str, String str2, String str3) {
        this.f11152e = str;
        ConnectSDK.getInstance().registerNofityListener(this.f11152e, CmpNotifyManager.getInstance());
        this.f11156i = new PalDeviceInfo(str2, str3);
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.b, com.aliyun.alink.linksdk.tmp.connect.IConnect
    public TmpEnum.ConnectType a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int connectType = PluginMgr.getInstance().getConnectType(this.f11156i);
        return connectType == 1 ? TmpEnum.ConnectType.CONNECT_TYPE_COAP : connectType == 4 ? TmpEnum.ConnectType.CONNECT_TYPE_BLE : connectType == 5 ? TmpEnum.ConnectType.CONNECT_TYPE_TGMESH : super.a();
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean b(com.aliyun.alink.linksdk.tmp.connect.d dVar, com.aliyun.alink.linksdk.tmp.connect.c cVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "unsubscribe request:" + dVar);
        ConnectSDK.getInstance().unsubscribe(this.f11152e, (ARequest) dVar.c(), new k(dVar, new com.aliyun.alink.linksdk.tmp.connect.f(cVar, dVar)));
        a(dVar);
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean c(com.aliyun.alink.linksdk.tmp.connect.d dVar, com.aliyun.alink.linksdk.tmp.connect.c cVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "reDiscoveryDevice() called with: request = [" + dVar + "], handler = [" + cVar + "]");
        try {
            ConnectSDK.getInstance().getConnectDiscovery(this.f11152e).discoveryCertainDevice((ARequest) dVar.c(), new b(cVar, dVar));
            return true;
        } catch (Throwable th) {
            ALog.w(f11151d, "reDiscoveryDevice t:" + th.toString());
            return true;
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public TmpEnum.DeviceState d() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ConnectState connectState = ConnectSDK.getInstance().getConnectState(this.f11152e);
        ALog.d(f11151d, "getConnectState mConnectId:" + this.f11152e + " connectState:" + connectState);
        return TmpEnum.DeviceState.createConnectState(connectState);
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean e() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "stopConnect() called");
        ConnectSDK.getInstance().destoryConnect(this.f11152e);
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean f() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "unInit mConnectId:" + this.f11152e);
        this.f11153f.clear();
        ConnectSDK.getInstance().unregisterConnect(this.f11152e);
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean b() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "stopDiscovery() called");
        IConnectDiscovery connectDiscovery = ConnectSDK.getInstance().getConnectDiscovery(this.f11152e);
        if (connectDiscovery == null) {
            return true;
        }
        connectDiscovery.stopDiscovery();
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean c() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "stopNotifyMonitor() called");
        try {
            ConnectSDK.getInstance().getConnectDiscovery(this.f11152e).stopNotifyMonitor();
            return true;
        } catch (Throwable th) {
            ALog.w(f11151d, "stopNotifyMonitor error:" + th.toString());
            return true;
        }
    }

    public a(String str, String str2) {
        this.f11154g = str;
        this.f11155h = str2;
        if (TextUtils.isEmpty(str2)) {
            this.f11152e = str;
            ConnectSDK.getInstance().registerNofityListener(this.f11152e, CmpNotifyManager.getInstance());
        } else if (TextUtils.isEmpty(str)) {
            this.f11152e = str2;
            ConnectSDK.getInstance().registerNofityListener(this.f11152e, CmpNotifyManager.getInstance());
        } else {
            ConnectSDK.getInstance().registerNofityListener(this.f11155h, CmpNotifyManager.getInstance());
            ConnectSDK.getInstance().registerNofityListener(this.f11154g, CmpNotifyManager.getInstance());
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public String a(TmpEnum.ConnectType connectType) {
        if (TmpEnum.ConnectType.CONNECT_TYPE_COAP == connectType) {
            return this.f11155h;
        }
        if (TmpEnum.ConnectType.CONNECT_TYPE_MQTT == connectType) {
            return this.f11154g;
        }
        return this.f11152e;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean b(IDevStateChangeListener iDevStateChangeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "removeConnectChangeListener() called with: listener = [" + iDevStateChangeListener + "]");
        CmpNotifyManager.getInstance().removeConnectStateChangeListener(this.f11152e, iDevStateChangeListener);
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean a(com.aliyun.alink.linksdk.tmp.connect.d dVar, com.aliyun.alink.linksdk.tmp.connect.c cVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "send() called with: request = [" + dVar + "], handler = [" + cVar + "]");
        ConnectSDK.getInstance().send(this.f11152e, (ARequest) dVar.c(), new d(dVar, cVar));
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean a(com.aliyun.alink.linksdk.tmp.connect.d dVar, com.aliyun.alink.linksdk.tmp.connect.c cVar, INotifyHandler iNotifyHandler) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "subscribe request:" + dVar);
        a(dVar, new g(this.f11152e, dVar, iNotifyHandler));
        ConnectSDK.getInstance().subscribe(this.f11152e, (ARequest) dVar.c(), new j(dVar, new com.aliyun.alink.linksdk.tmp.connect.f(cVar, dVar)));
        return true;
    }

    public boolean a(com.aliyun.alink.linksdk.tmp.connect.d dVar, g gVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "registerNofityListener request:" + dVar);
        if (gVar != null && dVar != null && !TextUtils.isEmpty(dVar.d())) {
            CmpNotifyManager.getInstance().addHandler(hashCode(), this.f11152e, dVar.d(), gVar);
            return true;
        }
        ALog.w(f11151d, "registerNofityListener null");
        return false;
    }

    public boolean a(com.aliyun.alink.linksdk.tmp.connect.d dVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "unregisterNofityListener request:" + dVar);
        if (dVar != null && !TextUtils.isEmpty(dVar.d())) {
            CmpNotifyManager.getInstance().removeHandler(hashCode(), this.f11152e, dVar.d());
            return true;
        }
        ALog.w(f11151d, "unregisterNofityListener null");
        return false;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean a(String str, String str2, String str3, OutputParams outputParams, IPublishResourceListener iPublishResourceListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "resourcePublish() called with: identifier = [" + str + "], method = [" + str2 + "], topic = [" + str3 + "], params = [" + outputParams + "], listener = [" + iPublishResourceListener + "]");
        CommonResource commonResource = new CommonResource();
        commonResource.topic = str3;
        CommonRequestPayload commonRequestPayload = new CommonRequestPayload(null, null);
        commonRequestPayload.setMethod(str2);
        commonRequestPayload.setVersion("1.0");
        commonRequestPayload.setParams(outputParams);
        String json = GsonUtils.toJson(commonRequestPayload);
        if (!TextUtils.isEmpty(json)) {
            commonResource.payload = json.getBytes();
        }
        ConnectSDK.getInstance().publishResource(commonResource, new com.aliyun.alink.linksdk.tmp.resource.f(str, iPublishResourceListener));
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean a(String str, String str2, byte[] bArr, IPublishResourceListener iPublishResourceListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "rawResourcePublish() called with: identifier = [" + str + "], topic = [" + str2 + "], data = [" + bArr + "], listener = [" + iPublishResourceListener + "]");
        CommonResource commonResource = new CommonResource();
        commonResource.topic = str2;
        commonResource.payload = bArr;
        ConnectSDK.getInstance().publishResource(commonResource, new com.aliyun.alink.linksdk.tmp.resource.f(str, iPublishResourceListener));
        return true;
    }

    public boolean a(String str, String str2, boolean z2, com.aliyun.alink.linksdk.tmp.resource.b bVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "regTopic identifier:" + str + " topic:" + str2);
        CommonResource commonResource = new CommonResource();
        commonResource.topic = str2;
        commonResource.isNeedAuth = z2;
        this.f11153f.put(str, commonResource);
        ConnectSDK.getInstance().registerResource(commonResource, new com.aliyun.alink.linksdk.tmp.resource.g(str, bVar));
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean a(String str, String str2, String str3, boolean z2, com.aliyun.alink.linksdk.tmp.resource.b bVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (TextUtils.isEmpty(str)) {
            return a(str2, str3, z2, bVar);
        }
        ALog.d(f11151d, "regTopic connectId:" + str + " identifier:" + str2 + " topic:" + str3);
        IConnectResourceRegister connectResourceRegister = ConnectSDK.getInstance().getConnectResourceRegister(str);
        if (connectResourceRegister == null) {
            ALog.d(f11151d, "regTopic faile getConnectResourceRegister null mConnectId:" + this.f11152e);
            return false;
        }
        CommonResource commonResource = new CommonResource();
        commonResource.topic = str3;
        commonResource.isNeedAuth = z2;
        this.f11153f.put(str2, commonResource);
        connectResourceRegister.registerResource(commonResource, new com.aliyun.alink.linksdk.tmp.resource.g(str2, bVar));
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean a(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "unRegTopic() called with: identifier = [" + str + "], topic = [" + str2 + "]");
        IConnectResourceRegister connectResourceRegister = ConnectSDK.getInstance().getConnectResourceRegister(this.f11152e);
        AResource aResourceRemove = this.f11153f.remove(str);
        if (connectResourceRegister == null) {
            return true;
        }
        connectResourceRegister.unregisterResource(aResourceRemove, null);
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean a(int i2, DiscoveryConfig discoveryConfig, INotifyHandler iNotifyHandler) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "startDiscovery() called with: timeOut = [" + i2 + "], config = [" + discoveryConfig + "], handler = [" + iNotifyHandler + "]");
        try {
            IConnectDiscovery connectDiscovery = ConnectSDK.getInstance().getConnectDiscovery(this.f11152e);
            if (connectDiscovery == null) {
                return true;
            }
            connectDiscovery.startDiscovery(i2, discoveryConfig != null ? discoveryConfig.translateToALCSDiscoveryConfig() : null, new e(iNotifyHandler));
            return true;
        } catch (Throwable th) {
            ALog.w(f11151d, "startDiscovery t:" + th.toString());
            return true;
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean a(INotifyHandler iNotifyHandler) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "startNotifyMonitor() called with: handler = [" + iNotifyHandler + "]");
        try {
            ConnectSDK.getInstance().getConnectDiscovery(this.f11152e).startNotifyMonitor(new e(iNotifyHandler));
            return true;
        } catch (Throwable th) {
            ALog.w(f11151d, "startNotifyMonitor t:" + th.toString());
            return true;
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.b, com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean a(String str, int i2, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "setOption option:" + i2 + " value:" + obj);
        if (obj == null) {
            return false;
        }
        if (TextUtils.isEmpty(str)) {
            str = this.f11155h;
        }
        if (i2 == 1) {
            if (!(obj instanceof String)) {
                return false;
            }
            AlcsServerConnectOption alcsServerConnectOption = new AlcsServerConnectOption();
            alcsServerConnectOption.setPrefix((String) obj);
            ConnectSDK.getInstance().updateConnectOption(this.f11152e, alcsServerConnectOption);
            return false;
        }
        if (i2 != 2) {
            return super.a(str, i2, obj);
        }
        if (!(obj instanceof String)) {
            return false;
        }
        AlcsServerConnectOption alcsServerConnectOption2 = new AlcsServerConnectOption();
        alcsServerConnectOption2.setBlackClients((String) obj);
        ConnectSDK.getInstance().updateConnectOption(this.f11152e, alcsServerConnectOption2);
        return false;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean a(IDevStateChangeListener iDevStateChangeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11151d, "addConnectChangeListener() called with: listener = [" + iDevStateChangeListener + "]");
        CmpNotifyManager.getInstance().addConnectStateChangeListener(this.f11152e, iDevStateChangeListener);
        return true;
    }
}
