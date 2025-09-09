package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;
import datasource.implemention.data.DeviceVersionInfo;

/* renamed from: com.alibaba.ailabs.iot.aisbase.ia, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0405ia implements IActionListener<String> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f8418a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ IActionListener f8419b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8420c;

    public C0405ia(OTAPluginProxy oTAPluginProxy, String str, IActionListener iActionListener) {
        this.f8420c = oTAPluginProxy;
        this.f8418a = str;
        this.f8419b = iActionListener;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(String str) {
        this.f8420c.f8507w = str;
        this.f8420c.f8508x = this.f8418a;
        this.f8420c.b(str, this.f8418a, (IActionListener<DeviceVersionInfo>) this.f8419b);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        IActionListener iActionListener = this.f8419b;
        if (iActionListener != null) {
            iActionListener.onFailure(i2, str);
        }
    }
}
