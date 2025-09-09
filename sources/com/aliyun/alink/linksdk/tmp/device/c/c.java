package com.aliyun.alink.linksdk.tmp.device.c;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.DeviceManager;
import com.aliyun.alink.linksdk.tmp.api.IProvision;
import com.aliyun.alink.linksdk.tmp.config.DeviceConfig;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class c implements IProvision {

    /* renamed from: a, reason: collision with root package name */
    private static final String f11391a = "[Tmp]Provision";

    /* renamed from: b, reason: collision with root package name */
    private com.aliyun.alink.linksdk.tmp.device.a f11392b;

    public c(DeviceConfig deviceConfig) {
        DeviceBasicData deviceBasicData = DeviceManager.getInstance().getDeviceBasicData(deviceConfig.getBasicData().getDevId());
        this.f11392b = new com.aliyun.alink.linksdk.tmp.device.a(deviceConfig, deviceBasicData == null ? new DeviceBasicData(deviceConfig.getBasicData()) : deviceBasicData);
    }

    @Override // com.aliyun.alink.linksdk.tmp.api.IProvision
    public void provisionInit(Object obj, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11391a, "init tag");
        this.f11392b.a(obj, iDevListener);
    }

    @Override // com.aliyun.alink.linksdk.tmp.api.IProvision
    public boolean setConfiData(Object obj, Object obj2, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11391a, "setup configData:" + obj);
        return this.f11392b.a(obj, obj2, iDevListener);
    }

    @Override // com.aliyun.alink.linksdk.tmp.api.IProvision
    public void unInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11391a, "unInit");
        com.aliyun.alink.linksdk.tmp.device.a aVar = this.f11392b;
        if (aVar != null) {
            aVar.f();
        }
    }
}
