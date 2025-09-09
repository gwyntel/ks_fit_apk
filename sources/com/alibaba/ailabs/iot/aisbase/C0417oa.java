package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.OnDownLoadStateListener;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;
import datasource.implemention.data.DeviceVersionInfo;
import java.io.File;

/* renamed from: com.alibaba.ailabs.iot.aisbase.oa, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0417oa implements OnDownLoadStateListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceVersionInfo.DeviceInfoModel f8446a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8447b;

    public C0417oa(OTAPluginProxy oTAPluginProxy, DeviceVersionInfo.DeviceInfoModel deviceInfoModel) {
        this.f8447b = oTAPluginProxy;
        this.f8446a = deviceInfoModel;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.OnDownLoadStateListener
    public void downLoadStateCallback(String str) throws InterruptedException {
        this.f8447b.f8509y = -1L;
        this.f8447b.h();
        if (this.f8447b.A != null) {
            File file = new File(str);
            if (file.exists()) {
                if (this.f8446a.getMd5().equalsIgnoreCase(Utils.md5(file))) {
                    this.f8447b.A.onComplete(str);
                } else {
                    this.f8447b.a(-402, "md5 not match");
                }
            }
        }
    }
}
