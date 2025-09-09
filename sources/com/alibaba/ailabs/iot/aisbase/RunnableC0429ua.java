package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;

/* renamed from: com.alibaba.ailabs.iot.aisbase.ua, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0429ua implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8575a;

    public RunnableC0429ua(OTAPluginProxy oTAPluginProxy) {
        this.f8575a = oTAPluginProxy;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f8575a.b(5, "OTA activity timed out");
    }
}
