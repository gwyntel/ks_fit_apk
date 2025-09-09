package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;
import com.alibaba.ailabs.tg.utils.LogUtils;

/* renamed from: com.alibaba.ailabs.iot.aisbase.ha, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0403ha implements IActionListener<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8409a;

    public C0403ha(OTAPluginProxy oTAPluginProxy) {
        this.f8409a = oTAPluginProxy;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onSuccess(Object obj) {
        LogUtils.d(this.f8409a.f8485a, "Get the firmware version successfully: " + obj);
        if (obj instanceof Integer) {
            this.f8409a.K = Utils.adapterToOsUpdateVersion(((Integer) obj).intValue());
        } else if (obj instanceof String) {
            this.f8409a.K = (String) obj;
        }
        LogUtils.d(this.f8409a.f8485a, "OsUpdate version: " + this.f8409a.K);
    }
}
