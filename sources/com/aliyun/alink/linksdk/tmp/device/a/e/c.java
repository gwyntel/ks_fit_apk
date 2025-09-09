package com.aliyun.alink.linksdk.tmp.device.a.e;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.connect.a.h;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.device.a.d;
import com.aliyun.alink.linksdk.tmp.device.payload.CommonResponsePayload;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class c extends d<c> implements com.aliyun.alink.linksdk.tmp.connect.c {

    /* renamed from: n, reason: collision with root package name */
    protected static final String f11339n = "[Tmp]PutAuthUserTask";

    /* renamed from: o, reason: collision with root package name */
    protected String f11340o;

    /* renamed from: p, reason: collision with root package name */
    protected String f11341p;

    public c(com.aliyun.alink.linksdk.tmp.device.a aVar, IDevListener iDevListener, DeviceBasicData deviceBasicData) {
        super(aVar, iDevListener);
        a(deviceBasicData);
    }

    public c a(String str) {
        this.f11340o = str;
        return this;
    }

    public c b(String str) {
        this.f11341p = str;
        return this;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.a();
        this.f11294i.a(h.a(this.f11295j.getProductKey(), this.f11295j.getDeviceName()).e(this.f11340o).f(this.f11341p).a(true).c(), this);
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) {
        CommonResponsePayload commonResponsePayload;
        if (eVar != null && eVar.b() && (commonResponsePayload = (CommonResponsePayload) GsonUtils.fromJson(eVar.e(), new TypeToken<CommonResponsePayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.e.c.1
        }.getType())) != null && commonResponsePayload.payloadSuccess()) {
            a((c) dVar, (com.aliyun.alink.linksdk.tmp.connect.d) eVar);
        } else {
            a(dVar, new ErrorInfo(300, "response error"));
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) {
        b((c) dVar, errorInfo);
    }
}
