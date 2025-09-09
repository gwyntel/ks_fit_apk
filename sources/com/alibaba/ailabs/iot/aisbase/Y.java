package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.OnDownLoadStateListener;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTADownloadHelper;
import datasource.implemention.data.DeviceVersionInfo;
import java.io.File;

/* loaded from: classes2.dex */
public class Y implements OnDownLoadStateListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceVersionInfo.DeviceInfoModel f8352a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ OTADownloadHelper f8353b;

    public Y(OTADownloadHelper oTADownloadHelper, DeviceVersionInfo.DeviceInfoModel deviceInfoModel) {
        this.f8353b = oTADownloadHelper;
        this.f8352a = deviceInfoModel;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.OnDownLoadStateListener
    public void downLoadStateCallback(String str) throws InterruptedException {
        this.f8353b.f8473i = -1L;
        this.f8353b.b();
        if (this.f8353b.f8475k != null) {
            File file = new File(str);
            if (file.exists()) {
                if (this.f8352a.getMd5().equalsIgnoreCase(Utils.md5(file))) {
                    this.f8353b.f8475k.onComplete(str);
                } else {
                    this.f8353b.a(-402, "md5 not match");
                }
            }
        }
    }
}
