package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a;

import com.aliyun.alink.linksdk.alcs.api.ICAProbeListener;
import com.aliyun.alink.linksdk.alcs.data.ica.ICADeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalProbeResult;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalProbeListener;

/* loaded from: classes2.dex */
public class l implements ICAProbeListener {

    /* renamed from: a, reason: collision with root package name */
    private PalProbeListener f10834a;

    /* renamed from: b, reason: collision with root package name */
    private PalDeviceInfo f10835b;

    public l(PalDeviceInfo palDeviceInfo, PalProbeListener palProbeListener) {
        this.f10834a = palProbeListener;
        this.f10835b = palDeviceInfo;
    }

    @Override // com.aliyun.alink.linksdk.alcs.api.ICAProbeListener
    public void onComplete(ICADeviceInfo iCADeviceInfo, int i2) {
        PalProbeListener palProbeListener = this.f10834a;
        if (palProbeListener != null) {
            palProbeListener.onComplete(this.f10835b, new PalProbeResult(i2, "iot_ica"));
        }
    }
}
