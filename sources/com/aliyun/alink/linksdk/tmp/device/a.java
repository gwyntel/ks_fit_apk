package com.aliyun.alink.linksdk.tmp.device;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.config.DefaultServerConfig;
import com.aliyun.alink.linksdk.tmp.config.DeviceConfig;
import com.aliyun.alink.linksdk.tmp.connect.entity.cmp.l;
import com.aliyun.alink.linksdk.tmp.connect.entity.cmp.m;
import com.aliyun.alink.linksdk.tmp.data.ut.ExtraData;
import com.aliyun.alink.linksdk.tmp.device.a.c;
import com.aliyun.alink.linksdk.tmp.device.a.d.d;
import com.aliyun.alink.linksdk.tmp.device.a.d.f;
import com.aliyun.alink.linksdk.tmp.device.a.d.g;
import com.aliyun.alink.linksdk.tmp.device.a.d.h;
import com.aliyun.alink.linksdk.tmp.device.a.d.i;
import com.aliyun.alink.linksdk.tmp.device.a.d.j;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.TDeviceShadow;
import com.aliyun.alink.linksdk.tmp.device.payload.KeyValuePair;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.device.payload.cloud.EncryptGroupAuthInfo;
import com.aliyun.alink.linksdk.tmp.devicemodel.DeviceModel;
import com.aliyun.alink.linksdk.tmp.devicemodel.Event;
import com.aliyun.alink.linksdk.tmp.devicemodel.Property;
import com.aliyun.alink.linksdk.tmp.devicemodel.Service;
import com.aliyun.alink.linksdk.tmp.event.EventManager;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.listener.IDevRawDataListener;
import com.aliyun.alink.linksdk.tmp.listener.IDevStateChangeListener;
import com.aliyun.alink.linksdk.tmp.listener.IEventListener;
import com.aliyun.alink.linksdk.tmp.listener.IPublishResourceListener;
import com.aliyun.alink.linksdk.tmp.listener.ITRawDataRequestHandler;
import com.aliyun.alink.linksdk.tmp.listener.ITResRequestHandler;
import com.aliyun.alink.linksdk.tmp.resource.e;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import com.aliyun.alink.linksdk.tmp.utils.TextHelper;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    protected static final String f11211a = "[Tmp]DeviceImpl";

    /* renamed from: b, reason: collision with root package name */
    protected com.aliyun.alink.linksdk.tmp.connect.b f11212b;

    /* renamed from: c, reason: collision with root package name */
    protected DeviceBasicData f11213c;

    /* renamed from: d, reason: collision with root package name */
    protected DeviceModel f11214d;

    /* renamed from: f, reason: collision with root package name */
    protected TDeviceShadow f11216f;

    /* renamed from: g, reason: collision with root package name */
    protected DeviceConfig f11217g;

    /* renamed from: h, reason: collision with root package name */
    protected l f11218h;

    /* renamed from: i, reason: collision with root package name */
    protected m f11219i;

    /* renamed from: j, reason: collision with root package name */
    protected boolean f11220j = false;

    /* renamed from: e, reason: collision with root package name */
    protected boolean f11215e = true;

    public a(DeviceConfig deviceConfig, DeviceBasicData deviceBasicData) {
        this.f11217g = deviceConfig;
        this.f11213c = deviceBasicData;
        this.f11216f = new TDeviceShadow(this, deviceBasicData, deviceConfig);
    }

    public void a(com.aliyun.alink.linksdk.tmp.connect.b bVar) {
        this.f11212b = bVar;
        TDeviceShadow tDeviceShadow = this.f11216f;
        if (tDeviceShadow != null) {
            tDeviceShadow.setConnect(bVar);
        }
    }

    public TmpEnum.ConnectType b() {
        com.aliyun.alink.linksdk.tmp.connect.b bVar = this.f11212b;
        return bVar != null ? bVar.a() : TmpEnum.ConnectType.CONNECT_TYPE_UNKNOWN;
    }

    public boolean c() {
        return this.f11220j;
    }

    public DeviceModel d() {
        return this.f11214d;
    }

    public boolean e() {
        return this.f11215e;
    }

    public void f() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f11216f.unInit();
        Set<String> devSubedEventList = EventManager.getInstance().getDevSubedEventList(hashCode(), j());
        if (devSubedEventList == null || devSubedEventList.isEmpty()) {
            ALog.d(f11211a, "subedEventlist empty");
        } else {
            Iterator<String> it = devSubedEventList.iterator();
            while (it.hasNext()) {
                c(it.next());
            }
        }
        m mVar = this.f11219i;
        if (mVar != null) {
            mVar.a();
        }
        l lVar = this.f11218h;
        if (lVar != null) {
            lVar.a();
        }
        ALog.d(f11211a, "unInit mDeviceShadow:" + this.f11216f + " subedEventlist:" + devSubedEventList + " mUpdatePrefixHandler:" + this.f11219i + " mUpdateBlackListHandler:" + this.f11218h + " mConnect:" + this.f11212b);
    }

    public void g() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "stopConnect mConnect:" + this.f11212b);
        com.aliyun.alink.linksdk.tmp.connect.b bVar = this.f11212b;
        if (bVar != null) {
            bVar.e();
            this.f11212b = null;
        }
    }

    public String h() {
        if (this.f11213c == null) {
            return null;
        }
        return this.f11213c.getProductKey() + this.f11213c.getDeviceName();
    }

    public String i() {
        DeviceBasicData deviceBasicData = this.f11213c;
        if (deviceBasicData != null) {
            return deviceBasicData.getModelType();
        }
        return null;
    }

    public String j() {
        DeviceBasicData deviceBasicData = this.f11213c;
        if (deviceBasicData != null) {
            return deviceBasicData.getDevId();
        }
        return null;
    }

    public String k() {
        DeviceBasicData deviceBasicData = this.f11213c;
        if (deviceBasicData != null) {
            return deviceBasicData.getIotId();
        }
        return null;
    }

    public TmpEnum.DeviceState l() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        com.aliyun.alink.linksdk.tmp.connect.b bVar = this.f11212b;
        if (bVar != null) {
            return bVar.d();
        }
        ALog.d(f11211a, "getDeviceState mConnect null return DISCONNECTED");
        return TmpEnum.DeviceState.DISCONNECTED;
    }

    public List<Property> m() {
        DeviceModel deviceModel = this.f11214d;
        if (deviceModel != null) {
            return deviceModel.getProperties();
        }
        return null;
    }

    public List<Service> n() {
        DeviceModel deviceModel = this.f11214d;
        if (deviceModel != null) {
            return deviceModel.getServices();
        }
        return null;
    }

    public List<Event> o() {
        DeviceModel deviceModel = this.f11214d;
        if (deviceModel != null) {
            return deviceModel.getEvents();
        }
        return null;
    }

    public Map<String, ValueWrapper> p() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "getAllPropertyValues");
        return this.f11216f.getAllPropertyValues();
    }

    public boolean c(List<String> list, Object obj, IDevListener iDevListener) {
        return new c().b(new com.aliyun.alink.linksdk.tmp.device.a.e.a(this, iDevListener, this.f11213c).a(list)).a();
    }

    public void d(String str) throws IllegalAccessException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "updatePrefix prefix:" + str);
        a(1, str);
        DeviceConfig deviceConfig = this.f11217g;
        if (deviceConfig == null || !(deviceConfig instanceof DefaultServerConfig)) {
            return;
        }
        DefaultServerConfig defaultServerConfig = (DefaultServerConfig) deviceConfig;
        defaultServerConfig.setPrefix(str);
        TmpStorage.getInstance().saveServerEnptInfo(j(), str, defaultServerConfig.getSecret());
    }

    public void e(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "updateBlackList blackList:" + str);
        a(2, str);
        TmpStorage.getInstance().saveBlackList(j(), str);
    }

    public com.aliyun.alink.linksdk.tmp.connect.b a() {
        return this.f11212b;
    }

    public void b(boolean z2) {
        this.f11215e = z2;
    }

    public void a(boolean z2) {
        this.f11220j = z2;
    }

    public boolean b(IDevStateChangeListener iDevStateChangeListener) {
        com.aliyun.alink.linksdk.tmp.connect.b bVar = this.f11212b;
        if (bVar != null) {
            return bVar.b(iDevStateChangeListener);
        }
        return false;
    }

    public void a(DeviceModel deviceModel) {
        this.f11214d = deviceModel;
        this.f11216f.setDeviceModel(deviceModel);
    }

    public boolean b(List<KeyValuePair> list, Object obj, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "setPropertyValue propertyPair:" + list + " tag:" + obj + " handler:" + iDevListener);
        return this.f11216f.setPropertyValue(list, obj, iDevListener);
    }

    public void c(String str) {
        EventManager.getInstance().removeSubscribedEvent(hashCode(), j(), str);
        EventManager.getInstance().removeEventListener(hashCode(), j(), str);
    }

    public void a(String str, String str2) {
        if (this.f11213c != null) {
            if (!TextUtils.isEmpty(str)) {
                this.f11213c.setProductKey(str);
            }
            if (!TextUtils.isEmpty(str2)) {
                this.f11213c.setDeviceName(str2);
            }
        }
        this.f11216f.updateProDev(str, str2);
    }

    public boolean b(String str) {
        return EventManager.getInstance().isEventSubscribed(hashCode(), j(), str);
    }

    public boolean b(byte[] bArr, final IDevRawDataListener iDevRawDataListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "publishRawData handler:" + iDevRawDataListener + " data:" + iDevRawDataListener);
        return e.a().a(this.f11212b, "post", "/sys/" + this.f11213c.getProductKey() + "/" + this.f11213c.getDeviceName() + TmpConstant.URI_THING + TmpConstant.URI_MODEL + "/up_raw", bArr, new IPublishResourceListener() { // from class: com.aliyun.alink.linksdk.tmp.device.a.3
            @Override // com.aliyun.alink.linksdk.tmp.listener.IPublishResourceListener
            public void onError(String str, AError aError) {
                IDevRawDataListener iDevRawDataListener2 = iDevRawDataListener;
                if (iDevRawDataListener2 != null) {
                    iDevRawDataListener2.onFail(null, aError == null ? null : new ErrorInfo(aError.getCode(), aError.getMsg()));
                }
            }

            @Override // com.aliyun.alink.linksdk.tmp.listener.IPublishResourceListener
            public void onSuccess(String str, Object obj) {
                IDevRawDataListener iDevRawDataListener2 = iDevRawDataListener;
                if (iDevRawDataListener2 != null) {
                    iDevRawDataListener2.onSuccess(null, obj);
                }
            }
        });
    }

    public boolean a(Object obj, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "init tag:" + obj + " handler:" + iDevListener);
        if (iDevListener != null && this.f11213c != null) {
            this.f11216f.setDeviceModel(this.f11214d);
            c cVar = new c();
            if (DeviceConfig.DeviceType.CLIENT == this.f11217g.getDeviceType()) {
                return cVar.b(new g(this, this.f11213c, this.f11217g, iDevListener).a(obj)).b(new com.aliyun.alink.linksdk.tmp.device.a.d.a(this, this.f11213c, this.f11217g, iDevListener).a(obj)).b(new i(this.f11216f, iDevListener).a(obj)).a();
            }
            if (DeviceConfig.DeviceType.SERVER == this.f11217g.getDeviceType()) {
                this.f11218h = new l(this);
                this.f11219i = new m(this);
                return cVar.b(new com.aliyun.alink.linksdk.tmp.device.a.d.c(this.f11217g, iDevListener).a(obj)).b(new f(this, this.f11213c, this.f11217g, iDevListener).a(obj)).b(new j(this.f11213c, this.f11217g, iDevListener, this.f11218h, this.f11219i).a(obj)).b(new g(this, this.f11213c, this.f11217g, iDevListener).a(obj)).b(new h(this, this.f11213c, this.f11217g, iDevListener)).b(new i(this.f11216f, iDevListener).a(obj)).a();
            }
            if (DeviceConfig.DeviceType.PROVISION == this.f11217g.getDeviceType()) {
                return cVar.b(new d(this, this.f11213c, this.f11217g, iDevListener).a(obj)).b(new com.aliyun.alink.linksdk.tmp.device.a.i.a(this, obj, this.f11213c, this.f11217g, iDevListener)).a();
            }
            if (DeviceConfig.DeviceType.PROVISION_RECEIVER == this.f11217g.getDeviceType()) {
                return cVar.b(new com.aliyun.alink.linksdk.tmp.device.a.d.c(this.f11217g, iDevListener).a(obj)).b(new com.aliyun.alink.linksdk.tmp.device.a.d.e(this, this.f11213c, this.f11217g, iDevListener).a(obj)).b(new h(this, this.f11213c, this.f11217g, iDevListener)).a();
            }
            return false;
        }
        LogCat.d(f11211a, "init error handler:" + iDevListener);
        return false;
    }

    public boolean a(IDevStateChangeListener iDevStateChangeListener) {
        com.aliyun.alink.linksdk.tmp.connect.b bVar = this.f11212b;
        if (bVar != null) {
            return bVar.a(iDevStateChangeListener);
        }
        return false;
    }

    public boolean a(List<String> list, Object obj, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "getPropertyValue idList:" + list + " tag:" + obj + " handler:" + iDevListener);
        return this.f11216f.getPropertyValue(list, obj, iDevListener);
    }

    public ValueWrapper a(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "getPropertyValue propId:" + str);
        return this.f11216f.getPropertyValue(str);
    }

    public boolean a(ExtraData extraData, List<KeyValuePair> list, Object obj, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "setPropertyValue extraData:" + extraData + " propertyPair:" + list + " tag:" + obj + " handler:" + iDevListener);
        return this.f11216f.setPropertyValue(extraData, list, obj, iDevListener);
    }

    public boolean a(String str, ValueWrapper valueWrapper, boolean z2, IPublishResourceListener iPublishResourceListener) {
        return this.f11216f.setPropertyValue(str, valueWrapper, z2, iPublishResourceListener);
    }

    public boolean a(Map<String, ValueWrapper> map, boolean z2, IPublishResourceListener iPublishResourceListener) {
        return this.f11216f.setPropertyValue(map, z2, iPublishResourceListener);
    }

    public boolean a(String str, List<KeyValuePair> list, ExtraData extraData, Object obj, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "invokeService serviceId:" + str + " args:" + list + " tag:" + obj + " handler:" + iDevListener);
        return new c().b(new com.aliyun.alink.linksdk.tmp.device.a.h.a(this, this.f11213c, iDevListener).a(this.f11214d).a(str).a(obj).a(extraData).a(e()).a(list)).a();
    }

    public boolean a(final String str, Object obj, final IEventListener iEventListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "subscribeEvent eventId:" + str + " tag:" + obj + " handler:" + iEventListener);
        if (EventManager.getInstance().isOtherDeviceSubscribed(j(), str)) {
            a(str, j(), iEventListener);
            if (iEventListener != null) {
                iEventListener.onSuccess(obj, null);
            }
            return true;
        }
        if ("post".equalsIgnoreCase(str)) {
            this.f11216f.subPropertyPostEvent(new IDevListener() { // from class: com.aliyun.alink.linksdk.tmp.device.a.1
                @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
                public void onFail(Object obj2, ErrorInfo errorInfo) {
                    IEventListener iEventListener2 = iEventListener;
                    if (iEventListener2 != null) {
                        iEventListener2.onFail(obj2, null);
                    }
                }

                @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
                public void onSuccess(Object obj2, OutputParams outputParams) {
                    a aVar = a.this;
                    aVar.a(str, aVar.j(), iEventListener);
                    IEventListener iEventListener2 = iEventListener;
                    if (iEventListener2 != null) {
                        iEventListener2.onSuccess(obj2, null);
                    }
                }
            });
            return true;
        }
        return new c().b(new com.aliyun.alink.linksdk.tmp.device.a.b.c(this, this.f11213c, this.f11214d, iEventListener, obj).a(EventManager.getInstance()).a(str)).a();
    }

    public boolean a(Object obj, IEventListener iEventListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "subAllEvent  tag:" + obj + " handler:" + iEventListener + " mDeviceModel:" + this.f11214d);
        DeviceModel deviceModel = this.f11214d;
        if (deviceModel != null && deviceModel.getEvents() != null && this.f11214d.getEvents().size() <= 1) {
            return a("post", obj, iEventListener);
        }
        c cVar = new c();
        DeviceModel deviceModel2 = this.f11214d;
        if (deviceModel2 != null) {
            for (Event event : deviceModel2.getEvents()) {
                if ("post".equalsIgnoreCase(event.getIdentifier())) {
                    a("post", obj, iEventListener);
                } else if (EventManager.getInstance().isOtherDeviceSubscribed(j(), event.getIdentifier())) {
                    a(event.getIdentifier(), j(), iEventListener);
                } else {
                    cVar.b(new com.aliyun.alink.linksdk.tmp.device.a.b.c(this, this.f11213c, this.f11214d, iEventListener, obj).a(EventManager.getInstance()).a(event.getIdentifier()));
                }
            }
        }
        boolean zA = cVar.a();
        if (!zA && iEventListener != null) {
            iEventListener.onFail(obj, null);
        }
        return zA;
    }

    public boolean a(String str, Object obj, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "unsubscribeEvent eventId:" + str + " tag:" + obj + " handler:" + iDevListener);
        c(str);
        if (!"post".equalsIgnoreCase(str)) {
            return new c().b(new com.aliyun.alink.linksdk.tmp.device.a.b.a(this, this.f11213c, iDevListener).a(obj).a(e()).a(this.f11214d).a(str)).a();
        }
        if (iDevListener == null) {
            return true;
        }
        iDevListener.onSuccess(obj, null);
        return true;
    }

    public boolean a(String str, TmpEnum.GroupRoleType groupRoleType, Object obj, Object obj2, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "addGroupAuthInfo groupId:" + str + " roleType:" + groupRoleType + " authInfo:" + obj + " tag:" + obj2 + " handler:" + iDevListener);
        if (groupRoleType == null || obj == null) {
            return false;
        }
        return new c().b(new com.aliyun.alink.linksdk.tmp.device.a.e.b(this, this.f11213c, iDevListener).a(obj2).b(str).a(groupRoleType.getValue(), (EncryptGroupAuthInfo) GsonUtils.fromJson(obj.toString(), new TypeToken<EncryptGroupAuthInfo>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.2
        }.getType())).a(TmpConstant.GROUP_OP_ADD)).a();
    }

    public boolean a(String str, TmpEnum.GroupRoleType groupRoleType, Object obj, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "delGroupAUthInfo groupId:" + str + " roleType:" + groupRoleType + " tag:" + obj + " handler:" + iDevListener);
        if (groupRoleType == null) {
            groupRoleType = TmpEnum.GroupRoleType.UNKNOWN;
        }
        return new c().b(new com.aliyun.alink.linksdk.tmp.device.a.e.b(this, this.f11213c, iDevListener).a(obj).b(str).a(groupRoleType.getValue(), (EncryptGroupAuthInfo) null).a(TmpConstant.GROUP_OP_DEL)).a();
    }

    public boolean a(String str, String str2, Object obj, IDevListener iDevListener) {
        return new c().b(new com.aliyun.alink.linksdk.tmp.device.a.e.c(this, iDevListener, this.f11213c).a(str).b(str2)).a();
    }

    public boolean a(Object obj, Object obj2, IDevListener iDevListener) {
        com.aliyun.alink.linksdk.tmp.device.a.i.b bVar = new com.aliyun.alink.linksdk.tmp.device.a.i.b(obj2, this.f11213c, this, iDevListener);
        bVar.b(obj);
        return new c().b(bVar).a();
    }

    public void a(String str, String str2, IEventListener iEventListener) {
        EventManager.getInstance().addSubscribedEvent(hashCode(), j(), str);
        EventManager.getInstance().addEventListener(hashCode(), str2, str, iEventListener);
    }

    public String a(String str, boolean z2, ITResRequestHandler iTResRequestHandler) {
        return a((String) null, str, z2, iTResRequestHandler);
    }

    public String a(String str, String str2, boolean z2, ITResRequestHandler iTResRequestHandler) {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        if (TmpConstant.PROPERTY_IDENTIFIER_SET.compareToIgnoreCase(str2) == 0) {
            this.f11216f.setPropSetServiceHandler(iTResRequestHandler);
            return TextHelper.getTopicStr(this.f11214d, str2);
        }
        if (TmpConstant.PROPERTY_IDENTIFIER_GET.compareToIgnoreCase(str2) == 0) {
            this.f11216f.setPropGetServiceHandler(iTResRequestHandler);
            return TextHelper.getTopicStr(this.f11214d, str2);
        }
        return e.a().a(this.f11212b, str, this.f11214d, str2, z2, new com.aliyun.alink.linksdk.tmp.resource.h(iTResRequestHandler));
    }

    public boolean a(String str, ITResRequestHandler iTResRequestHandler) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (TmpConstant.PROPERTY_IDENTIFIER_SET.compareToIgnoreCase(str) == 0) {
            this.f11216f.setPropSetServiceHandler(null);
            return true;
        }
        if (TmpConstant.PROPERTY_IDENTIFIER_GET.compareToIgnoreCase(str) == 0) {
            this.f11216f.setPropGetServiceHandler(null);
            return true;
        }
        return e.a().a(this.f11212b, this.f11214d, str);
    }

    public boolean a(String str, OutputParams outputParams, IPublishResourceListener iPublishResourceListener) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if ("post".compareToIgnoreCase(str) == 0) {
            Map.Entry<String, ValueWrapper> next = outputParams.entrySet().iterator().next();
            this.f11216f.triggerPostEvent(next.getKey(), next.getValue(), iPublishResourceListener);
            return true;
        }
        return e.a().a(this.f11212b, this.f11214d, str, outputParams, iPublishResourceListener);
    }

    public boolean a(int i2, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11211a, "setConnectOption mConnect:" + this.f11212b + " optionType:" + i2 + " value:" + obj);
        com.aliyun.alink.linksdk.tmp.connect.b bVar = this.f11212b;
        if (bVar != null) {
            return bVar.a(bVar.a(TmpEnum.ConnectType.CONNECT_TYPE_COAP), i2, obj);
        }
        return false;
    }

    public boolean a(byte[] bArr, IDevRawDataListener iDevRawDataListener) {
        return new c().b(new com.aliyun.alink.linksdk.tmp.device.a.g.a(this, this.f11213c, iDevRawDataListener).a(bArr)).a();
    }

    public boolean a(boolean z2, ITRawDataRequestHandler iTRawDataRequestHandler) {
        return e.a().a(this.f11212b, TextHelper.formatDownRawId(this.f11213c.getProductKey(), this.f11213c.getDeviceName()), TextHelper.formatDownRawTopic(this.f11213c.getProductKey(), this.f11213c.getDeviceName()), z2, new com.aliyun.alink.linksdk.tmp.resource.c(iTRawDataRequestHandler));
    }
}
