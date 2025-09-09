package com.aliyun.alink.linksdk.tmp.device.a.e;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.device.a.d;
import com.aliyun.alink.linksdk.tmp.device.payload.CommonResponsePayload;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* loaded from: classes2.dex */
public class a extends d<a> implements com.aliyun.alink.linksdk.tmp.connect.c {

    /* renamed from: n, reason: collision with root package name */
    protected static final String f11329n = "[Tmp]DeleteAuthUserTask";

    /* renamed from: o, reason: collision with root package name */
    protected List<String> f11330o;

    public a(com.aliyun.alink.linksdk.tmp.device.a aVar, IDevListener iDevListener, DeviceBasicData deviceBasicData) {
        super(aVar, iDevListener);
        a(deviceBasicData);
    }

    public a a(List<String> list) {
        this.f11330o = list;
        return this;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) {
        CommonResponsePayload commonResponsePayload;
        if (eVar == null || !eVar.b() || (commonResponsePayload = (CommonResponsePayload) GsonUtils.fromJson(eVar.e(), new TypeToken<CommonResponsePayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.e.a.1
        }.getType())) == null || !commonResponsePayload.payloadSuccess()) {
            a(dVar, new ErrorInfo(300, "response error"));
        } else {
            a((a) dVar, (com.aliyun.alink.linksdk.tmp.connect.d) eVar);
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) {
        b((a) dVar, errorInfo);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.a();
        this.f11294i.a(com.aliyun.alink.linksdk.tmp.connect.a.b.a(this.f11295j.getProductKey(), this.f11295j.getDeviceName()).a(this.f11330o).a(true).c(), this);
        return true;
    }
}
