package com.aliyun.alink.linksdk.alcs.lpbs.a.e;

import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgr;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalConnectListener;
import java.util.Map;

/* loaded from: classes2.dex */
public class i implements PalConnectListener {

    /* renamed from: a, reason: collision with root package name */
    private PalConnectListener f10751a;

    /* renamed from: b, reason: collision with root package name */
    private String f10752b;

    public i(PalConnectListener palConnectListener, String str) {
        this.f10751a = palConnectListener;
        this.f10752b = str;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalConnectListener
    public void onLoad(int i2, Map<String, Object> map, PalDeviceInfo palDeviceInfo) {
        PalDeviceInfo aliIoTPkDn = PluginMgr.getInstance().toAliIoTPkDn(palDeviceInfo, this.f10752b);
        PalConnectListener palConnectListener = this.f10751a;
        if (palConnectListener != null) {
            palConnectListener.onLoad(i2, map, aliIoTPkDn);
        }
    }
}
