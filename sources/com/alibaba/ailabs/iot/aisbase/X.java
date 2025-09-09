package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.OnDownLoadStateListener;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTADownloadHelper;
import datasource.implemention.data.DeviceVersionInfo;
import java.io.File;

/* loaded from: classes2.dex */
public class X implements OnDownLoadStateListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceVersionInfo f8348a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ OTADownloadHelper f8349b;

    public X(OTADownloadHelper oTADownloadHelper, DeviceVersionInfo deviceVersionInfo) {
        this.f8349b = oTADownloadHelper;
        this.f8348a = deviceVersionInfo;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.OnDownLoadStateListener
    public void downLoadStateCallback(String str) throws InterruptedException {
        this.f8349b.f8473i = -1L;
        this.f8349b.b();
        if (this.f8349b.f8475k != null) {
            File file = new File(str);
            if (file.exists()) {
                if (this.f8348a.getModel().getMd5().equalsIgnoreCase(Utils.md5(file))) {
                    this.f8349b.f8475k.onComplete(str);
                } else {
                    this.f8349b.a(-402, "md5 not match");
                }
            }
        }
    }
}
