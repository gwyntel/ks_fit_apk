package com.aliyun.alink.linksdk.tmp.device.a.d;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.cmp.api.ConnectSDK;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.config.DeviceConfig;
import com.aliyun.alink.linksdk.tmp.device.payload.discovery.GetTslResponsePayload;
import com.aliyun.alink.linksdk.tmp.devicemodel.DeviceModel;
import com.aliyun.alink.linksdk.tmp.devicemodel.loader.ILoaderHandler;
import com.aliyun.alink.linksdk.tmp.devicemodel.loader.RootDeviceModelSerializer;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class g extends com.aliyun.alink.linksdk.tmp.device.a.d<g> implements com.aliyun.alink.linksdk.tmp.connect.c, ILoaderHandler {

    /* renamed from: n, reason: collision with root package name */
    protected static final String f11317n = "[Tmp]SerializeTask";

    /* renamed from: o, reason: collision with root package name */
    protected DeviceConfig f11318o;

    public g(com.aliyun.alink.linksdk.tmp.device.a aVar, DeviceBasicData deviceBasicData, DeviceConfig deviceConfig, IDevListener iDevListener) {
        super(aVar, iDevListener);
        a(aVar);
        a(deviceBasicData);
        this.f11318o = deviceConfig;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    /* renamed from: b */
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, com.aliyun.alink.linksdk.tmp.connect.e eVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a(dVar, errorInfo);
    }

    @Override // com.aliyun.alink.linksdk.tmp.devicemodel.loader.ILoaderHandler
    public void onDeserialize(DeviceModel deviceModel) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LogCat.d(f11317n, "onDeserialize:" + deviceModel);
        if (deviceModel != null) {
            RootDeviceModelSerializer.getInstance().insertModelObject(this.f11295j.getDevId(), deviceModel);
        }
        com.aliyun.alink.linksdk.tmp.device.a aVar = this.f11293h;
        if (aVar != null) {
            aVar.a(deviceModel);
        }
        a((g) null, (Object) null);
    }

    @Override // com.aliyun.alink.linksdk.tmp.devicemodel.loader.ILoaderHandler
    public void onDeserializeError(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LogCat.d(f11317n, "onDeserializeError:" + str);
        b((g) null, new ErrorInfo(300, "param is invalid"));
    }

    @Override // com.aliyun.alink.linksdk.tmp.devicemodel.loader.ILoaderHandler
    public void onSerialize(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LogCat.e(f11317n, "onSerialize");
    }

    @Override // com.aliyun.alink.linksdk.tmp.devicemodel.loader.ILoaderHandler
    public void onSerializeError(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LogCat.e(f11317n, "onSerializeError");
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String tsl;
        DeviceModel modelObject = RootDeviceModelSerializer.getInstance().getModelObject(this.f11295j.getDevId());
        if (modelObject != null) {
            com.aliyun.alink.linksdk.tmp.device.a aVar = this.f11293h;
            ALog.d(f11317n, "action deviceModel found ret deviceImpl:" + aVar);
            if (aVar != null) {
                aVar.a(modelObject);
            } else {
                ALog.d(f11317n, "action deviceModel found setDeviceModel deviceImpl empty");
            }
            a((g) null, (Object) null);
            return true;
        }
        if (TextUtils.isEmpty(this.f11295j.getDeviceModelJson())) {
            if (TextUtils.isEmpty(this.f11295j.getDevId())) {
                TmpStorage.DeviceInfo deviceInfo = TmpStorage.getInstance().getDeviceInfo(this.f11318o.getBasicData().getIotId());
                tsl = deviceInfo != null ? TmpStorage.getInstance().getTsl(deviceInfo.getId()) : null;
            } else {
                tsl = TmpStorage.getInstance().getTsl(this.f11295j.getDevId());
            }
            if (!TextUtils.isEmpty(tsl)) {
                this.f11295j.setDeviceModelJson(tsl);
                a((com.aliyun.alink.linksdk.tmp.connect.d) null, (com.aliyun.alink.linksdk.tmp.connect.e) null);
            } else if (!TextUtils.isEmpty(this.f11318o.getBasicData().getIotId())) {
                this.f11294i = com.aliyun.alink.linksdk.tmp.connect.a.a(ConnectSDK.getInstance().getApiGatewayConnectId(), (String) null, (String) null);
                return this.f11294i.a(com.aliyun.alink.linksdk.tmp.connect.a.e.d().a(this.f11290e).e(this.f11318o.getBasicData().getIotId()).c(), this);
            }
        } else {
            a((com.aliyun.alink.linksdk.tmp.connect.d) null, (com.aliyun.alink.linksdk.tmp.connect.e) null);
        }
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, com.aliyun.alink.linksdk.tmp.connect.e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        GetTslResponsePayload getTslResponsePayload;
        Object obj;
        ALog.d(f11317n, "onLoad response:" + eVar);
        if (eVar != null && (getTslResponsePayload = (GetTslResponsePayload) GsonUtils.fromJson(eVar.e(), new TypeToken<GetTslResponsePayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.d.g.1
        }.getType())) != null && (obj = getTslResponsePayload.data) != null) {
            String json = GsonUtils.toJson(obj);
            this.f11295j.setDeviceModelJson(json);
            TmpStorage.getInstance().saveTsl(this.f11295j.getDevId(), json);
            TmpStorage.getInstance().saveDeviceInfo(this.f11318o.getBasicData().getIotId(), this.f11295j.getProdKey(), this.f11295j.getDeviceName());
            TmpStorage.getInstance().saveIotId(this.f11295j.getProdKey(), this.f11295j.getDeviceName(), this.f11318o.getBasicData().getIotId());
        }
        RootDeviceModelSerializer.getInstance().deserialize(RootDeviceModelSerializer.SINGLEEXTEND_DEVICEMODELSERIALIZER_ID, this.f11295j.getDeviceModelJson(), this);
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) {
        b((g) dVar, errorInfo);
    }
}
