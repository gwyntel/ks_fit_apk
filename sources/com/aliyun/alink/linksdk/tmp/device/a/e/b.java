package com.aliyun.alink.linksdk.tmp.device.a.e;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.connect.a.f;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.device.a.d;
import com.aliyun.alink.linksdk.tmp.device.payload.CommonResponsePayload;
import com.aliyun.alink.linksdk.tmp.device.payload.cloud.EncryptGroupAuthInfo;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class b extends d<b> implements com.aliyun.alink.linksdk.tmp.connect.c {

    /* renamed from: n, reason: collision with root package name */
    protected static final String f11332n = "[Tmp]GroupAuthTask";

    /* renamed from: o, reason: collision with root package name */
    protected String f11333o;

    /* renamed from: p, reason: collision with root package name */
    protected String f11334p;

    /* renamed from: q, reason: collision with root package name */
    protected String f11335q;

    /* renamed from: r, reason: collision with root package name */
    protected String f11336r;

    /* renamed from: s, reason: collision with root package name */
    protected String f11337s;

    public b(com.aliyun.alink.linksdk.tmp.device.a aVar, DeviceBasicData deviceBasicData, IDevListener iDevListener) {
        super(aVar, iDevListener);
        a(aVar);
        a(deviceBasicData);
    }

    public b a(String str) {
        this.f11334p = str;
        return this;
    }

    public b b(String str) {
        this.f11333o = str;
        return this;
    }

    public b a(String str, EncryptGroupAuthInfo encryptGroupAuthInfo) {
        this.f11335q = str;
        if (encryptGroupAuthInfo != null) {
            if (TmpConstant.GROUP_ROLE_DEVICE.equalsIgnoreCase(str)) {
                this.f11336r = encryptGroupAuthInfo.encryptGroupKeyPrefix;
                this.f11337s = encryptGroupAuthInfo.encryptGroupSecret;
            } else if (TmpConstant.GROUP_ROLE_CONTROLLER.equalsIgnoreCase(this.f11335q)) {
                this.f11336r = encryptGroupAuthInfo.encryptAccessKey;
                this.f11337s = encryptGroupAuthInfo.encryptAccessToken;
            }
        }
        return this;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) {
        CommonResponsePayload commonResponsePayload;
        if (eVar != null && eVar.b() && (commonResponsePayload = (CommonResponsePayload) GsonUtils.fromJson(eVar.e(), new TypeToken<CommonResponsePayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.e.b.1
        }.getType())) != null && commonResponsePayload.payloadSuccess()) {
            a((b) dVar, (com.aliyun.alink.linksdk.tmp.connect.d) eVar);
        } else {
            a(dVar, new ErrorInfo(300, "response error"));
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) {
        b((b) dVar, errorInfo);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.a();
        ALog.d(f11332n, "dealGroupAUthInfo groupId:" + this.f11333o + " groupAuthType:" + this.f11335q + " mOp:" + this.f11334p + " mParam1:" + this.f11336r + " mParam2" + this.f11337s);
        this.f11294i.a(f.a(this.f11295j.getProductKey(), this.f11295j.getDeviceName()).a(this.f11295j.getAddr()).a(this.f11295j.getPort()).g(this.f11333o).e(this.f11334p).f(this.f11335q).h(this.f11336r).i(this.f11337s).a(true).c(), this);
        return true;
    }
}
