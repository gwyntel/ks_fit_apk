package com.aliyun.alink.linksdk.tmp.device.a.b;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.device.a.d;
import com.aliyun.alink.linksdk.tmp.device.payload.CommonResponsePayload;
import com.aliyun.alink.linksdk.tmp.device.payload.event.EventRequestPayload;
import com.aliyun.alink.linksdk.tmp.devicemodel.DeviceModel;
import com.aliyun.alink.linksdk.tmp.event.INotifyHandler;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class a extends d<a> implements com.aliyun.alink.linksdk.tmp.connect.c {

    /* renamed from: n, reason: collision with root package name */
    protected String f11260n;

    /* renamed from: o, reason: collision with root package name */
    protected INotifyHandler f11261o;

    public a(com.aliyun.alink.linksdk.tmp.device.a aVar, DeviceBasicData deviceBasicData, IDevListener iDevListener) {
        super(aVar, iDevListener);
        a(deviceBasicData);
        a(aVar);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.a
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) {
        super.a((a) dVar, (com.aliyun.alink.linksdk.tmp.connect.d) eVar);
    }

    public a a(String str) {
        this.f11260n = str;
        return this;
    }

    protected boolean b() {
        com.aliyun.alink.linksdk.tmp.device.a aVar = this.f11293h;
        if (aVar == null || TextUtils.isEmpty(this.f11260n)) {
            return false;
        }
        aVar.c(this.f11260n);
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        com.aliyun.alink.linksdk.tmp.device.a aVar;
        super.a();
        EventRequestPayload eventRequestPayload = new EventRequestPayload(this.f11295j.getProductKey(), this.f11295j.getDeviceName());
        if (this.f11296k == null && (aVar = this.f11293h) != null && aVar != null) {
            this.f11296k = aVar.d();
        }
        DeviceModel deviceModel = this.f11296k;
        if (deviceModel != null) {
            eventRequestPayload.setMethod(deviceModel.getEventMethod(this.f11260n));
        }
        com.aliyun.alink.linksdk.tmp.connect.d dVarC = com.aliyun.alink.linksdk.tmp.connect.a.a.d().a(this.f11295j.getAddr()).a(this.f11295j.getPort()).k(this.f11295j.getProductKey()).l(this.f11295j.getDeviceName()).a(this.f11290e).a(true).c(false).m(eventRequestPayload.getMethod()).c(CommonRequestBuilder.a(this.f11295j.getProductKey(), this.f11295j.getDeviceName(), eventRequestPayload.getMethod(), "sys")).b((com.aliyun.alink.linksdk.tmp.connect.a.a) eventRequestPayload).c();
        com.aliyun.alink.linksdk.tmp.connect.b bVar = this.f11294i;
        if (bVar != null) {
            bVar.b(dVarC, this);
        }
        LogCat.d("[Tmp]DeviceAsyncTask", "action mEventNameList:" + this.f11260n + " devId:" + this.f11295j.getDeviceName());
        return true;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (eVar == null || !eVar.b() || ((CommonResponsePayload) GsonUtils.fromJson(eVar.e(), new TypeToken<CommonResponsePayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.b.a.1
        }.getType())).getCode() != 200) {
            LogCat.d("[Tmp]DeviceAsyncTask", "onLoad normal error");
            b((a) dVar, new ErrorInfo(300, "response error"));
        } else {
            LogCat.d("[Tmp]DeviceAsyncTask", "onLoad normal success");
            a(dVar, eVar);
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) {
        b((a) dVar, errorInfo);
    }
}
