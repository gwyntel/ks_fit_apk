package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTADownloadHelper;

/* loaded from: classes2.dex */
public class V implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTADownloadHelper f8342a;

    public V(OTADownloadHelper oTADownloadHelper) {
        this.f8342a = oTADownloadHelper;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f8342a.b(5, "Command timeout(\nWaiting for response timeout)");
    }
}
