package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;
import com.alibaba.ailabs.tg.utils.LogUtils;
import datasource.NetworkCallback;
import datasource.implemention.data.DeviceVersionInfo;

/* renamed from: com.alibaba.ailabs.iot.aisbase.ka, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0409ka implements NetworkCallback<DeviceVersionInfo> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f8431a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8432b;

    public C0409ka(OTAPluginProxy oTAPluginProxy, IActionListener iActionListener) {
        this.f8432b = oTAPluginProxy;
        this.f8431a = iActionListener;
    }

    @Override // datasource.NetworkCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(DeviceVersionInfo deviceVersionInfo) {
        LogUtils.d(this.f8432b.f8485a, "Successful got ota info: " + deviceVersionInfo);
        this.f8432b.C = deviceVersionInfo;
        IActionListener iActionListener = this.f8431a;
        if (iActionListener != null) {
            iActionListener.onSuccess(deviceVersionInfo);
        }
    }

    @Override // datasource.NetworkCallback
    public void onFailure(String str, String str2) {
        LogUtils.e(this.f8432b.f8485a, "Failed to query OTA info: " + str + "(" + str2 + ")");
        IActionListener iActionListener = this.f8431a;
        if (iActionListener != null) {
            iActionListener.onFailure(-300, str2);
        }
    }
}
