package com.aliyun.alink.linksdk.tmp.device.a.h;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.connect.a.l;
import com.aliyun.alink.linksdk.tmp.connect.c;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.data.device.Option;
import com.aliyun.alink.linksdk.tmp.data.ut.ExtraData;
import com.aliyun.alink.linksdk.tmp.device.a.d;
import com.aliyun.alink.linksdk.tmp.device.payload.KeyValuePair;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.device.payload.service.ServiceRequestPayload;
import com.aliyun.alink.linksdk.tmp.device.payload.service.ServiceResponsePayload;
import com.aliyun.alink.linksdk.tmp.devicemodel.DeviceModel;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import com.aliyun.alink.linksdk.tmp.utils.TextHelper;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class a extends d<a> implements c {

    /* renamed from: n, reason: collision with root package name */
    protected static final String f11358n = "[Tmp]InvokeServiceTask";

    /* renamed from: o, reason: collision with root package name */
    protected String f11359o;

    /* renamed from: p, reason: collision with root package name */
    protected List<KeyValuePair> f11360p;

    /* renamed from: q, reason: collision with root package name */
    protected Option f11361q;

    public a(com.aliyun.alink.linksdk.tmp.device.a aVar, DeviceBasicData deviceBasicData, IDevListener iDevListener) {
        super(aVar, iDevListener);
        a(deviceBasicData);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public a a(DeviceModel deviceModel) {
        this.f11296k = deviceModel;
        return this;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    /* renamed from: b */
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.f11291f == null) {
            LogCat.e(f11358n, "onFlowComplete handler empty error");
        } else if (dVar == null) {
            super.a(dVar, eVar, errorInfo);
        } else {
            this.f11291f.onSuccess(dVar.b(), new OutputParams(((ServiceResponsePayload) GsonUtils.fromJson(eVar.e(), new TypeToken<ServiceResponsePayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.h.a.1
            }.getType())).getData()));
        }
    }

    public a a(String str) {
        this.f11359o = str;
        return this;
    }

    public a a(List<KeyValuePair> list) {
        this.f11360p = list;
        return this;
    }

    public a a(ExtraData extraData) {
        if (extraData != null) {
            this.f11361q = extraData.option;
        }
        return this;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.a();
        HashMap map = new HashMap();
        List<KeyValuePair> list = this.f11360p;
        if (list != null && !list.isEmpty()) {
            for (KeyValuePair keyValuePair : this.f11360p) {
                map.put(keyValuePair.getKey(), keyValuePair.getValueWrapper());
            }
        }
        DeviceBasicData deviceBasicData = this.f11295j;
        if (deviceBasicData != null && this.f11296k != null && this.f11294i != null) {
            ServiceRequestPayload serviceRequestPayload = new ServiceRequestPayload(deviceBasicData.getProductKey(), this.f11295j.getDeviceName());
            serviceRequestPayload.setMethod(TextHelper.foramtMethod(this.f11359o));
            serviceRequestPayload.setParams((Map<String, ValueWrapper>) map);
            this.f11294i.a(l.d().a(this.f11295j.getAddr()).a(this.f11295j.getPort()).a(this.f11290e).k(this.f11296k.getProfile().getProdKey()).l(this.f11296k.getProfile().getName()).m(TextHelper.foramtMethod(this.f11359o)).a(true).a(this.f11361q).b((l) serviceRequestPayload).c(), this);
            LogCat.d(f11358n, "mServiceIdentifier:" + this.f11359o + " mServiceArgs:" + this.f11360p);
            return true;
        }
        ALog.e(f11358n, "mDeviceBasicData or mDeviceModel or mConnect null");
        b((a) null, new ErrorInfo(300, "param is invalid"));
        return false;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ServiceResponsePayload serviceResponsePayload;
        if (eVar != null && eVar.b() && (serviceResponsePayload = (ServiceResponsePayload) GsonUtils.fromJson(eVar.e(), new TypeToken<ServiceResponsePayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.h.a.2
        }.getType())) != null && serviceResponsePayload.getCode() == 200) {
            a((a) dVar, (com.aliyun.alink.linksdk.tmp.connect.d) eVar);
            return;
        }
        LogCat.e(f11358n, "onLoad error response:" + eVar);
        b((a) dVar, new ErrorInfo(300, "response error"));
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) {
        b((a) dVar, errorInfo);
    }
}
