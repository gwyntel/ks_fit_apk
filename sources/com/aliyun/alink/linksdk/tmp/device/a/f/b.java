package com.aliyun.alink.linksdk.tmp.device.a.f;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.device.a.d;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.TDeviceShadow;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.device.payload.property.GetPropertyRequestPayload;
import com.aliyun.alink.linksdk.tmp.device.payload.property.GetPropertyResponsePayload;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.listener.IPublishResourceListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class b extends d<b> implements com.aliyun.alink.linksdk.tmp.connect.c {

    /* renamed from: n, reason: collision with root package name */
    protected static final String f11347n = "AllPropertyTask";

    /* renamed from: o, reason: collision with root package name */
    protected List<String> f11348o;

    /* renamed from: p, reason: collision with root package name */
    protected WeakReference<TDeviceShadow> f11349p;

    public b(TDeviceShadow tDeviceShadow, com.aliyun.alink.linksdk.tmp.device.a aVar, DeviceBasicData deviceBasicData, IDevListener iDevListener) {
        super(aVar, iDevListener);
        this.f11349p = new WeakReference<>(tDeviceShadow);
        a(deviceBasicData);
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (eVar != null && eVar.b()) {
            LogCat.i(f11347n, "onLoad response success");
            GetPropertyResponsePayload getPropertyResponsePayload = (GetPropertyResponsePayload) GsonUtils.fromJson(eVar.e(), new TypeToken<GetPropertyResponsePayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.f.b.1
            }.getType());
            if (getPropertyResponsePayload != null && getPropertyResponsePayload.getCode() == 200) {
                LogCat.i(f11347n, "onLoad response payload success");
                Map<String, ValueWrapper> property = getPropertyResponsePayload.getProperty();
                TDeviceShadow tDeviceShadow = this.f11349p.get();
                if (property != null && !property.isEmpty() && tDeviceShadow != null) {
                    for (Map.Entry<String, ValueWrapper> entry : property.entrySet()) {
                        tDeviceShadow.setPropertyValue(entry.getKey(), entry.getValue(), false, (IPublishResourceListener) null);
                    }
                }
                LogCat.i(f11347n, "onLoad taskSuccess");
                a((b) dVar, (com.aliyun.alink.linksdk.tmp.connect.d) eVar);
                return;
            }
        }
        LogCat.i(f11347n, "onLoad taskError");
        b((b) dVar, new ErrorInfo(300, "response error"));
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) {
        b((b) dVar, errorInfo);
    }

    public b a(List<String> list) {
        this.f11348o = list;
        return this;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str;
        super.a();
        DeviceBasicData deviceBasicData = this.f11295j;
        if (deviceBasicData != null && this.f11296k != null && this.f11294i != null) {
            GetPropertyRequestPayload getPropertyRequestPayload = new GetPropertyRequestPayload(deviceBasicData.getProductKey(), this.f11295j.getDeviceName());
            getPropertyRequestPayload.setProperty(this.f11348o);
            getPropertyRequestPayload.setMethod(this.f11296k.getServiceMethod(TmpConstant.PROPERTY_IDENTIFIER_GET));
            this.f11294i.a(com.aliyun.alink.linksdk.tmp.connect.a.d.d().k(this.f11295j.getProductKey()).l(this.f11295j.getDeviceName()).a(this.f11295j.getAddr()).a(this.f11295j.getPort()).m(getPropertyRequestPayload.getMethod()).c(CommonRequestBuilder.a(this.f11295j.getProductKey(), this.f11295j.getDeviceName(), getPropertyRequestPayload.getMethod(), "sys")).a(this.f11290e).a(true).n(this.f11295j.isBleMeshDevice() ? this.f11295j.iotId : "").o(this.f11295j.isBleMeshDevice() ? "thing.attribute.get" : "").b((com.aliyun.alink.linksdk.tmp.connect.a.d) getPropertyRequestPayload).c(), this);
            if (("properties :" + this.f11348o) == null) {
                str = "empty";
            } else {
                str = this.f11348o.toString() + " mIsSecure:" + this.f11297l;
            }
            LogCat.d(f11347n, str);
            return true;
        }
        ALog.e(f11347n, "mDeviceBasicData or mDeviceModel or mConnect null");
        b((b) null, new ErrorInfo(300, "param is invalid"));
        return false;
    }
}
