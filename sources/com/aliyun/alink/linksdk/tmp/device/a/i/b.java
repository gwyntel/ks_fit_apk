package com.aliyun.alink.linksdk.tmp.device.a.i;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.connect.a.n;
import com.aliyun.alink.linksdk.tmp.connect.c;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.data.auth.AccessInfo;
import com.aliyun.alink.linksdk.tmp.data.auth.SetupData;
import com.aliyun.alink.linksdk.tmp.device.a.d;
import com.aliyun.alink.linksdk.tmp.device.payload.setup.SetupRequestPayload;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.AuthInfoCreater;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class b extends d<com.aliyun.alink.linksdk.tmp.device.a.h.a> implements c {

    /* renamed from: o, reason: collision with root package name */
    private static final String f11369o = "[Tmp]SetupTask";

    /* renamed from: n, reason: collision with root package name */
    protected SetupData f11370n;

    public b(Object obj, DeviceBasicData deviceBasicData, com.aliyun.alink.linksdk.tmp.device.a aVar, IDevListener iDevListener) {
        super(aVar, iDevListener);
        a(obj);
        a(deviceBasicData);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.a
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) {
        SetupData setupData = this.f11370n;
        if (setupData != null && "ServerAuthInfo".equalsIgnoreCase(setupData.configType)) {
            ALog.d(f11369o, "setup success change provisioner key");
            for (int i2 = 0; i2 < this.f11370n.configValue.size(); i2++) {
                AccessInfo accessInfoCreateAccessInfo = AuthInfoCreater.getInstance().createAccessInfo(this.f11370n.configValue.get(i2).authCode, this.f11370n.configValue.get(i2).authSecret, "2");
                TmpStorage.getInstance().saveAccessInfo(this.f11295j.getDevId(), accessInfoCreateAccessInfo.mAccessKey, accessInfoCreateAccessInfo.mAccessToken, true, TmpStorage.FLAG_PROVISIONER);
            }
        }
        super.a((b) dVar, (com.aliyun.alink.linksdk.tmp.connect.d) eVar);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (eVar == null || !eVar.b()) {
            ALog.d(f11369o, "SetupTask onLoad onError");
            a(dVar, new ErrorInfo(300, "errror"));
        } else {
            ALog.d(f11369o, "SetupTask onLoad taskSuccess");
            a(dVar, eVar);
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11369o, "SetupTask onError");
        b((b) dVar, errorInfo);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.a();
        SetupRequestPayload setupRequestPayload = new SetupRequestPayload(this.f11295j.getProductKey(), this.f11295j.getDeviceName());
        setupRequestPayload.setParams(this.f11370n);
        boolean zA = this.f11294i.a(n.a(this.f11295j.getProductKey(), this.f11295j.getDeviceName()).a(this.f11295j.getAddr()).a(this.f11295j.getPort()).a(this.f11290e).k(this.f11295j.getProductKey()).l(this.f11295j.getDeviceName()).a(true).b((n) setupRequestPayload).c(), this);
        ALog.d(f11369o, "SetupTask action bRet：" + zA + " ConfigData:" + this.f11370n.toString());
        return zA;
    }

    public void b(Object obj) {
        this.f11370n = (SetupData) GsonUtils.fromJson(String.valueOf(obj), new TypeToken<SetupData>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.i.b.1
        }.getType());
    }
}
