package com.aliyun.alink.linksdk.tmp.device.a.f;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder;
import com.aliyun.alink.linksdk.tmp.connect.a.m;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.data.ut.ExtraData;
import com.aliyun.alink.linksdk.tmp.device.a.d;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.TDeviceShadow;
import com.aliyun.alink.linksdk.tmp.device.payload.KeyValuePair;
import com.aliyun.alink.linksdk.tmp.device.payload.property.SetPropertyRequestPayload;
import com.aliyun.alink.linksdk.tmp.device.payload.property.SetPropertyResponsePayload;
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
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class c extends d<c> implements com.aliyun.alink.linksdk.tmp.connect.c {

    /* renamed from: n, reason: collision with root package name */
    protected static final String f11351n = "[Tmp]SetPropertyTask";

    /* renamed from: o, reason: collision with root package name */
    protected List<KeyValuePair> f11352o;

    /* renamed from: p, reason: collision with root package name */
    protected WeakReference<TDeviceShadow> f11353p;

    /* renamed from: q, reason: collision with root package name */
    protected ExtraData f11354q;

    public c(TDeviceShadow tDeviceShadow, com.aliyun.alink.linksdk.tmp.device.a aVar, DeviceBasicData deviceBasicData, IDevListener iDevListener) {
        super(aVar, iDevListener);
        a(deviceBasicData);
        this.f11353p = new WeakReference<>(tDeviceShadow);
    }

    public c a(List<KeyValuePair> list) {
        this.f11352o = list;
        return this;
    }

    public c a(ExtraData extraData) {
        this.f11354q = extraData;
        return this;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        SetPropertyResponsePayload setPropertyResponsePayload;
        if (eVar != null && eVar.b() && (setPropertyResponsePayload = (SetPropertyResponsePayload) GsonUtils.fromJson(eVar.e(), new TypeToken<SetPropertyResponsePayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.f.c.1
        }.getType())) != null && setPropertyResponsePayload.getCode() == 200) {
            TDeviceShadow tDeviceShadow = this.f11353p.get();
            for (KeyValuePair keyValuePair : this.f11352o) {
                if (tDeviceShadow != null) {
                    tDeviceShadow.setPropertyValue(keyValuePair.getKey(), keyValuePair.getValueWrapper(), false, (IPublishResourceListener) null);
                }
            }
            LogCat.d(f11351n, "onLoad success");
            a((c) dVar, (com.aliyun.alink.linksdk.tmp.connect.d) eVar);
            return;
        }
        LogCat.d(f11351n, "onLoad error response:" + eVar.toString());
        b((c) dVar, new ErrorInfo(300, "response error"));
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) {
        b((c) dVar, errorInfo);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.a();
        DeviceBasicData deviceBasicData = this.f11295j;
        if (deviceBasicData != null && this.f11296k != null && this.f11294i != null) {
            SetPropertyRequestPayload setPropertyRequestPayload = new SetPropertyRequestPayload(deviceBasicData.getProductKey(), this.f11295j.getDeviceName());
            HashMap map = new HashMap();
            List<KeyValuePair> list = this.f11352o;
            if (list != null && !list.isEmpty()) {
                for (KeyValuePair keyValuePair : this.f11352o) {
                    map.put(keyValuePair.getKey(), keyValuePair.getValueWrapper());
                }
                setPropertyRequestPayload.setParams(map);
                setPropertyRequestPayload.setMethod(this.f11296k.getServiceMethod(TmpConstant.PROPERTY_IDENTIFIER_SET));
                m mVarD = m.d();
                ExtraData extraData = this.f11354q;
                m mVarB = mVarD.e(extraData == null ? "" : extraData.performanceId).a(this.f11295j.getAddr()).a(this.f11295j.getPort()).k(this.f11295j.getProductKey()).l(this.f11295j.getDeviceName()).m(setPropertyRequestPayload.getMethod()).c(CommonRequestBuilder.a(this.f11296k.getProfile(), setPropertyRequestPayload.getMethod())).a(this.f11290e).a(true).b((m) setPropertyRequestPayload);
                ExtraData extraData2 = this.f11354q;
                this.f11294i.a(mVarB.a(extraData2 != null ? extraData2.option : null).n(this.f11295j.isBleMeshDevice() ? this.f11295j.iotId : "").o(this.f11295j.isBleMeshDevice() ? "thing.attribute.set" : "").c(), this);
                LogCat.d(f11351n, "properties :" + this.f11352o + " mIsSecure:" + this.f11297l);
                return false;
            }
            b((c) null, new ErrorInfo(300, "param is invalid"));
            return false;
        }
        ALog.e(f11351n, "mDeviceBasicData or mDeviceModel or mConnect null ：" + this.f11295j + " mDeviceModel：" + this.f11296k + " mConnect：" + this.f11294i);
        b((c) null, new ErrorInfo(300, "param is invalid"));
        return false;
    }
}
