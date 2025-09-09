package com.aliyun.alink.linksdk.alcs.lpbs.a.e;

import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgr;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalProbeResult;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalProbeListener;

/* loaded from: classes2.dex */
public class j implements PalProbeListener {

    /* renamed from: a, reason: collision with root package name */
    private PalProbeListener f10753a;

    /* renamed from: b, reason: collision with root package name */
    private String f10754b;

    public j(PalProbeListener palProbeListener, String str) {
        this.f10753a = palProbeListener;
        this.f10754b = str;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalProbeListener
    public void onComplete(PalDeviceInfo palDeviceInfo, PalProbeResult palProbeResult) {
        PalDeviceInfo aliIoTPkDn = PluginMgr.getInstance().toAliIoTPkDn(palDeviceInfo, this.f10754b);
        PalProbeListener palProbeListener = this.f10753a;
        if (palProbeListener != null) {
            palProbeListener.onComplete(aliIoTPkDn, palProbeResult);
        }
    }
}
