package com.aliyun.alink.linksdk.tmp.device.a.c;

import com.aliyun.alink.linksdk.tmp.connect.d;
import com.aliyun.alink.linksdk.tmp.data.device.Option;
import com.aliyun.alink.linksdk.tmp.data.ut.ExtraData;
import com.aliyun.alink.linksdk.tmp.device.a.e;
import com.aliyun.alink.linksdk.tmp.device.payload.KeyValuePair;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.device.payload.service.ServiceRequestPayload;
import com.aliyun.alink.linksdk.tmp.device.payload.service.ServiceResponsePayload;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.TextHelper;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class c extends e<c> implements com.aliyun.alink.linksdk.tmp.connect.c {

    /* renamed from: i, reason: collision with root package name */
    private static final String f11283i = "[Tmp]InvokeGroupService";

    /* renamed from: f, reason: collision with root package name */
    protected String f11284f;

    /* renamed from: g, reason: collision with root package name */
    protected List<KeyValuePair> f11285g;

    /* renamed from: h, reason: collision with root package name */
    protected Option f11286h;

    public c(com.aliyun.alink.linksdk.tmp.device.b bVar, IDevListener iDevListener) {
        super(bVar, iDevListener);
    }

    public c a(String str) {
        this.f11284f = str;
        return this;
    }

    public c a(List<KeyValuePair> list) {
        this.f11285g = list;
        return this;
    }

    public c a(ExtraData extraData) {
        if (extraData != null) {
            this.f11286h = extraData.option;
        }
        return this;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        HashMap map = new HashMap();
        List<KeyValuePair> list = this.f11285g;
        if (list != null && !list.isEmpty()) {
            for (KeyValuePair keyValuePair : this.f11285g) {
                map.put(keyValuePair.getKey(), keyValuePair.getValueWrapper());
            }
        }
        com.aliyun.alink.linksdk.tmp.device.b bVar = this.f11326c.get();
        if (bVar == null) {
            ALog.e(f11283i, "groupImpl null");
            b((c) null, new ErrorInfo(300, "param is invalid"));
            return false;
        }
        com.aliyun.alink.linksdk.tmp.connect.b.b bVarB = bVar.b();
        if (bVarB == null) {
            ALog.e(f11283i, "groupConnect null");
            b((c) null, new ErrorInfo(300, "param is invalid"));
            return false;
        }
        ServiceRequestPayload serviceRequestPayload = new ServiceRequestPayload(null, null);
        serviceRequestPayload.setMethod(TextHelper.foramtMethod(this.f11284f));
        serviceRequestPayload.setParams((Map<String, ValueWrapper>) map);
        bVarB.a(com.aliyun.alink.linksdk.tmp.connect.a.a.b.a(this.f11284f, bVar.a().localGroupId).b(bVar.a().accessInfo.mAccessKey, bVar.a().accessInfo.mAccessToken).a(bVar.a().localGroupDeviceData).b((com.aliyun.alink.linksdk.tmp.connect.a.a.b) serviceRequestPayload).c(), this);
        ALog.d(f11283i, "InvokeGroupService mServiceIdentifier:" + this.f11284f + " mServiceArgs:" + this.f11285g);
        return false;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(d dVar, com.aliyun.alink.linksdk.tmp.connect.e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ServiceResponsePayload serviceResponsePayload;
        ALog.e(f11283i, "onLoad response:" + eVar);
        if (eVar != null && eVar.b() && (serviceResponsePayload = (ServiceResponsePayload) GsonUtils.fromJson(eVar.e(), new TypeToken<ServiceResponsePayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.c.c.1
        }.getType())) != null && serviceResponsePayload.getCode() == 200) {
            a((c) dVar, (d) eVar);
            return;
        }
        ALog.e(f11283i, "onLoad error response:" + eVar);
        b((c) dVar, new ErrorInfo(300, "response error"));
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(d dVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.e(f11283i, "onError errorInfo:" + errorInfo);
        b((c) null, errorInfo);
    }
}
