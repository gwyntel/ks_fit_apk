package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.OnDownLoadStateListener;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;
import datasource.implemention.data.DeviceVersionInfo;
import java.io.File;

/* renamed from: com.alibaba.ailabs.iot.aisbase.na, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0415na implements OnDownLoadStateListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceVersionInfo f8442a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8443b;

    public C0415na(OTAPluginProxy oTAPluginProxy, DeviceVersionInfo deviceVersionInfo) {
        this.f8443b = oTAPluginProxy;
        this.f8442a = deviceVersionInfo;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.OnDownLoadStateListener
    public void downLoadStateCallback(String str) throws InterruptedException {
        this.f8443b.f8509y = -1L;
        this.f8443b.h();
        if (this.f8443b.A != null) {
            File file = new File(str);
            if (file.exists()) {
                if (this.f8442a.getModel().getMd5().equalsIgnoreCase(Utils.md5(file))) {
                    this.f8443b.A.onComplete(str);
                } else {
                    this.f8443b.a(-402, "md5 not match");
                }
            }
        }
    }
}
