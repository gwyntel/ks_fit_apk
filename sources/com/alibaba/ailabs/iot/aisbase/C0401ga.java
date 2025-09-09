package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;
import com.alibaba.ailabs.tg.utils.LogUtils;

/* renamed from: com.alibaba.ailabs.iot.aisbase.ga, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0401ga implements IActionListener<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8407a;

    public C0401ga(OTAPluginProxy oTAPluginProxy) {
        this.f8407a = oTAPluginProxy;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f8407a.b(1, str);
        LogUtils.d(this.f8407a.f8485a, "getFirmwareVersionCommand failed, code is: " + i2 + ", desc: " + str);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onSuccess(Object obj) {
        this.f8407a.a(IOTAPlugin.OTAState.FINISH);
        String strAdapterToOsUpdateVersion = obj instanceof Integer ? Utils.adapterToOsUpdateVersion(((Integer) obj).intValue()) : obj instanceof String ? (String) obj : "";
        if (this.f8407a.f8487c != null) {
            this.f8407a.f8487c.onSuccess(strAdapterToOsUpdateVersion);
        }
        OTAPluginProxy oTAPluginProxy = this.f8407a;
        oTAPluginProxy.updateDeviceVersion(oTAPluginProxy.f8507w, strAdapterToOsUpdateVersion, new C0399fa(this));
    }
}
