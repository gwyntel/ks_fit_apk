package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;
import datasource.NetworkCallback;
import datasource.implemention.data.GetDeviceUUIDRespData;

/* renamed from: com.alibaba.ailabs.iot.aisbase.ja, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0407ja implements NetworkCallback<GetDeviceUUIDRespData> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f8426a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8427b;

    public C0407ja(OTAPluginProxy oTAPluginProxy, IActionListener iActionListener) {
        this.f8427b = oTAPluginProxy;
        this.f8426a = iActionListener;
    }

    @Override // datasource.NetworkCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(GetDeviceUUIDRespData getDeviceUUIDRespData) {
        String model = getDeviceUUIDRespData.getModel();
        IActionListener iActionListener = this.f8426a;
        if (iActionListener != null) {
            iActionListener.onSuccess(model);
        }
    }

    @Override // datasource.NetworkCallback
    public void onFailure(String str, String str2) {
        IActionListener iActionListener = this.f8426a;
        if (iActionListener != null) {
            iActionListener.onFailure(-300, str2);
        }
    }
}
