package com.aliyun.alink.linksdk.tmp.device.a.a;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.connect.a.i;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class c extends com.aliyun.alink.linksdk.tmp.device.a.d<a> implements com.aliyun.alink.linksdk.tmp.connect.c {
    public c(com.aliyun.alink.linksdk.tmp.device.a aVar, com.aliyun.alink.linksdk.tmp.connect.b bVar, DeviceBasicData deviceBasicData, IDevListener iDevListener) {
        super(aVar, iDevListener);
        a(deviceBasicData);
        a(bVar);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.a();
        if (this.f11295j == null) {
            ALog.w("[Tmp]DeviceAsyncTask", "ProbeTask action mDeviceBasicData: null");
            return false;
        }
        com.aliyun.alink.linksdk.tmp.connect.d dVarC = i.d().k(this.f11295j.getProductKey()).l(this.f11295j.getDeviceName()).c();
        StringBuilder sb = new StringBuilder();
        sb.append("ProbeTask action mDeviceBasicData:");
        DeviceBasicData deviceBasicData = this.f11295j;
        sb.append(deviceBasicData == null ? TmpConstant.GROUP_ROLE_UNKNOWN : deviceBasicData.toString());
        ALog.d("[Tmp]DeviceAsyncTask", sb.toString());
        return this.f11294i.c(dVarC, this);
    }

    public void b() {
        ALog.d("[Tmp]DeviceAsyncTask", "stop set mDeviceHandler null");
        this.f11291f = null;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d("[Tmp]DeviceAsyncTask", "ProbeTask onLoad online  mDeviceBasicData:" + this.f11295j.getDevId());
        a((c) dVar, (com.aliyun.alink.linksdk.tmp.connect.d) eVar);
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d("[Tmp]DeviceAsyncTask", "ProbeTask onError offline mDeviceBasicData:" + this.f11295j.getDevId());
        b((c) dVar, errorInfo);
    }
}
