package com.alibaba.ailabs.iot.mesh.lowenergy;

import a.a.a.a.b.f.a;
import com.alibaba.ailabs.iot.mesh.biz.model.request.WakeupLowEnergyDeviceRequest;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.aliyun.alink.linksdk.connectsdk.ApiHelper;

/* loaded from: classes2.dex */
public class LowEnergyAssist {
    public static void wakeupLowEnergyDevice(String str, String str2, int i2, IActionListener<Boolean> iActionListener) {
        WakeupLowEnergyDeviceRequest wakeupLowEnergyDeviceRequest = new WakeupLowEnergyDeviceRequest();
        wakeupLowEnergyDeviceRequest.setProductKey(str);
        wakeupLowEnergyDeviceRequest.setWakeUpTime(i2);
        wakeupLowEnergyDeviceRequest.setDeviceId(str2);
        ApiHelper.getInstance().send(wakeupLowEnergyDeviceRequest, new a(iActionListener));
    }
}
