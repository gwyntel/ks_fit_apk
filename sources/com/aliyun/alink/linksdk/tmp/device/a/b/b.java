package com.aliyun.alink.linksdk.tmp.device.a.b;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder;
import com.aliyun.alink.linksdk.tmp.connect.a.o;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.device.a.d;
import com.aliyun.alink.linksdk.tmp.device.payload.CommonResponsePayload;
import com.aliyun.alink.linksdk.tmp.device.payload.event.EventRequestPayload;
import com.aliyun.alink.linksdk.tmp.event.INotifyHandler;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class b extends d<b> implements com.aliyun.alink.linksdk.tmp.connect.c {

    /* renamed from: n, reason: collision with root package name */
    protected static final String f11263n = "PropertySubEventTask";

    /* renamed from: o, reason: collision with root package name */
    protected INotifyHandler f11264o;

    /* renamed from: p, reason: collision with root package name */
    protected String f11265p;

    public b(com.aliyun.alink.linksdk.tmp.device.a aVar, DeviceBasicData deviceBasicData, IDevListener iDevListener) {
        super(aVar, iDevListener);
        a(deviceBasicData);
        a(aVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public b a(INotifyHandler iNotifyHandler) {
        this.f11264o = iNotifyHandler;
        return (b) this.f11289d;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public b a(String str) {
        this.f11265p = str;
        return (b) this.f11289d;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (eVar == null || !eVar.b()) {
            LogCat.d(f11263n, "onLoad error");
            b((b) dVar, new ErrorInfo(300, "response error"));
            return;
        }
        CommonResponsePayload commonResponsePayload = (CommonResponsePayload) GsonUtils.fromJson(eVar.e(), new TypeToken<CommonResponsePayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.b.b.1
        }.getType());
        if (commonResponsePayload == null || commonResponsePayload.getCode() != 200) {
            LogCat.d(f11263n, "onLoad normal error");
        } else {
            LogCat.d(f11263n, "onLoad normal success");
            a((b) dVar, (com.aliyun.alink.linksdk.tmp.connect.d) eVar);
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) {
        b((b) dVar, errorInfo);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        INotifyHandler iNotifyHandler;
        com.aliyun.alink.linksdk.tmp.device.a aVar;
        super.a();
        EventRequestPayload eventRequestPayload = new EventRequestPayload(this.f11295j.getProductKey(), this.f11295j.getDeviceName());
        if (this.f11296k == null && (aVar = this.f11293h) != null && aVar != null) {
            this.f11296k = aVar.d();
        }
        eventRequestPayload.setMethod(this.f11296k.getEventMethod(this.f11265p));
        com.aliyun.alink.linksdk.tmp.connect.d dVarC = o.d().a(this.f11290e).a(this.f11295j.getPort()).a(this.f11295j.getAddr()).k(this.f11295j.getProductKey()).l(this.f11295j.getDeviceName()).c(CommonRequestBuilder.a(this.f11295j.getProductKey(), this.f11295j.getDeviceName(), eventRequestPayload.getMethod(), "sys")).m(eventRequestPayload.getMethod()).c(true).a(true).b((o) eventRequestPayload).c();
        com.aliyun.alink.linksdk.tmp.connect.b bVar = this.f11294i;
        if (bVar != null && (iNotifyHandler = this.f11264o) != null) {
            bVar.a(dVarC, this, iNotifyHandler);
        }
        LogCat.d(f11263n, "action mEventNameList:" + this.f11265p + " devId:" + this.f11295j.getDeviceName());
        return true;
    }
}
