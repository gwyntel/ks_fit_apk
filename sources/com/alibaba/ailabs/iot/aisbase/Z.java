package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTADownloadHelper;
import com.alibaba.ailabs.iot.aisbase.utils.DownloadManagerUtils;

/* loaded from: classes2.dex */
public class Z implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DownloadManagerUtils.DownloadTaskDetails f8354a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ OTADownloadHelper.a f8355b;

    public Z(OTADownloadHelper.a aVar, DownloadManagerUtils.DownloadTaskDetails downloadTaskDetails) {
        this.f8355b = aVar;
        this.f8354a = downloadTaskDetails;
    }

    @Override // java.lang.Runnable
    public void run() {
        IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener = OTADownloadHelper.this.f8475k;
        DownloadManagerUtils.DownloadTaskDetails downloadTaskDetails = this.f8354a;
        iFirmwareDownloadListener.onProgress(downloadTaskDetails.totalSize, downloadTaskDetails.downloadedSize);
    }
}
