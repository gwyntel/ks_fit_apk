package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;
import datasource.NetworkCallback;
import datasource.implemention.data.UpdateDeviceVersionRespData;

/* renamed from: com.alibaba.ailabs.iot.aisbase.la, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0411la implements NetworkCallback<UpdateDeviceVersionRespData> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f8435a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8436b;

    public C0411la(OTAPluginProxy oTAPluginProxy, IActionListener iActionListener) {
        this.f8436b = oTAPluginProxy;
        this.f8435a = iActionListener;
    }

    @Override // datasource.NetworkCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(UpdateDeviceVersionRespData updateDeviceVersionRespData) {
        IActionListener iActionListener = this.f8435a;
        if (iActionListener != null) {
            iActionListener.onSuccess(updateDeviceVersionRespData);
        }
    }

    @Override // datasource.NetworkCallback
    public void onFailure(String str, String str2) {
        IActionListener iActionListener = this.f8435a;
        if (iActionListener != null) {
            iActionListener.onFailure(-300, str2);
        }
    }
}
