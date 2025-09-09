package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTADownloadHelper;

/* loaded from: classes2.dex */
public class W implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTADownloadHelper f8345a;

    public W(OTADownloadHelper oTADownloadHelper) {
        this.f8345a = oTADownloadHelper;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f8345a.b(5, "OTA activity timed out");
    }
}
