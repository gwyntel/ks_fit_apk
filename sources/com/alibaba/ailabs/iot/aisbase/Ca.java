package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;
import com.alibaba.ailabs.iot.aisbase.utils.DownloadManagerUtils;

/* loaded from: classes2.dex */
public class Ca implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DownloadManagerUtils.DownloadTaskDetails f8273a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy.a f8274b;

    public Ca(OTAPluginProxy.a aVar, DownloadManagerUtils.DownloadTaskDetails downloadTaskDetails) {
        this.f8274b = aVar;
        this.f8273a = downloadTaskDetails;
    }

    @Override // java.lang.Runnable
    public void run() {
        IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener = OTAPluginProxy.this.A;
        DownloadManagerUtils.DownloadTaskDetails downloadTaskDetails = this.f8273a;
        iFirmwareDownloadListener.onProgress(downloadTaskDetails.totalSize, downloadTaskDetails.downloadedSize);
    }
}
