package com.aliyun.alink.linksdk.tmp.device.a.d;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.config.DefaultServerConfig;
import com.aliyun.alink.linksdk.tmp.config.DeviceConfig;
import com.aliyun.alink.linksdk.tmp.connect.entity.cmp.l;
import com.aliyun.alink.linksdk.tmp.connect.entity.cmp.m;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.CloudUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class j extends com.aliyun.alink.linksdk.tmp.device.a.d<j> {

    /* renamed from: n, reason: collision with root package name */
    protected static final String f11323n = "[Tmp]UpdateSvrInfoTask";

    /* renamed from: o, reason: collision with root package name */
    protected l f11324o;

    /* renamed from: p, reason: collision with root package name */
    protected m f11325p;

    public j(DeviceBasicData deviceBasicData, DeviceConfig deviceConfig, IDevListener iDevListener, l lVar, m mVar) {
        super(null, iDevListener);
        a(deviceBasicData);
        a(deviceConfig);
        this.f11324o = lVar;
        this.f11325p = mVar;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        DeviceConfig deviceConfig = this.f11298m;
        if (deviceConfig != null && (deviceConfig instanceof DefaultServerConfig)) {
            ALog.d(f11323n, "action mConfig DefaultServerConfig true");
            b();
            c();
        }
        a((j) null, (Object) null);
        return true;
    }

    protected void b() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11323n, "updatePrefx start");
        CloudUtils.subPrefixUpdateRrpc(this.f11295j.getProductKey(), this.f11295j.getDeviceName(), this.f11325p);
    }

    protected void c() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11323n, "updateBlackList start");
        CloudUtils.subBlacklistUpdateRrpc(this.f11295j.getProductKey(), this.f11295j.getDeviceName(), this.f11324o);
    }
}
