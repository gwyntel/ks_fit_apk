package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;

/* renamed from: com.alibaba.ailabs.iot.aisbase.ea, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0397ea implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8399a;

    public RunnableC0397ea(OTAPluginProxy oTAPluginProxy) {
        this.f8399a = oTAPluginProxy;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f8399a.f8504t.connectDevice(this.f8399a.f8506v, new C0395da(this));
    }
}
