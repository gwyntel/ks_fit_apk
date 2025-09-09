package com.aliyun.alink.linksdk.tmp.device.a.a;

import com.aliyun.alink.linksdk.cmp.manager.discovery.DiscoveryMessage;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.DeviceManager;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.connect.entity.cmp.i;
import com.aliyun.alink.linksdk.tmp.event.INotifyHandler;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class d extends com.aliyun.alink.linksdk.tmp.device.a.d<a> implements INotifyHandler {

    /* renamed from: n, reason: collision with root package name */
    protected static final String f11259n = "[Tmp]RecNotifyTask";

    public d(com.aliyun.alink.linksdk.tmp.connect.b bVar, IDevListener iDevListener) {
        super(null, iDevListener);
        a(bVar);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.a();
        ALog.d(f11259n, "action startNotifyMonitor");
        com.aliyun.alink.linksdk.tmp.connect.b bVar = this.f11294i;
        if (bVar == null) {
            return true;
        }
        bVar.a(this);
        return true;
    }

    public boolean b() {
        com.aliyun.alink.linksdk.tmp.connect.b bVar = this.f11294i;
        if (bVar == null) {
            return true;
        }
        bVar.c();
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.event.INotifyHandler
    public void onMessage(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a(dVar, eVar);
    }

    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11259n, "onDeviceFound response:" + eVar + " mDeviceHandler:" + this.f11291f);
        if (eVar != null && eVar.a() != null) {
            DiscoveryMessage discoveryMessage = (DiscoveryMessage) ((i) eVar).a().data;
            if (discoveryMessage == null) {
                ALog.e(f11259n, "onDeviceFound discoveryMessage or deviceInfo null");
            }
            DeviceBasicData deviceBasicData = new DeviceBasicData(true);
            deviceBasicData.setProductKey(discoveryMessage.productKey);
            deviceBasicData.setDeviceName(discoveryMessage.deviceName);
            deviceBasicData.setModelType(discoveryMessage.modelType);
            DeviceManager.getInstance().addDeviceBasicData(deviceBasicData);
            return;
        }
        LogCat.e(f11259n, "addDevice error response null or unsuccess");
    }
}
