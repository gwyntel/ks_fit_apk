package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;

/* renamed from: com.alibaba.ailabs.iot.aisbase.ma, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0413ma implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8439a;

    public RunnableC0413ma(OTAPluginProxy oTAPluginProxy) {
        this.f8439a = oTAPluginProxy;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f8439a.b(5, "Command timeout(\nWaiting for response timeout)");
    }
}
