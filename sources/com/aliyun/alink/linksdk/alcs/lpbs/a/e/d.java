package com.aliyun.alink.linksdk.alcs.lpbs.a.e;

import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgr;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IDevInfoTrans;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDiscoveryDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDiscoveryListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class d implements PalDiscoveryListener {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10723a = "[AlcsLPBS]DiscoveryPkDnChgListener";

    /* renamed from: b, reason: collision with root package name */
    private PalDiscoveryListener f10724b;

    /* renamed from: c, reason: collision with root package name */
    private String f10725c;

    public d(String str, PalDiscoveryListener palDiscoveryListener) {
        this.f10724b = palDiscoveryListener;
        this.f10725c = str;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDiscoveryListener
    public void onDiscoveryDevice(final PalDiscoveryDeviceInfo palDiscoveryDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.f10724b == null || palDiscoveryDeviceInfo == null) {
            ALog.e(f10723a, "onDiscoveryDevice mListener or discoveryDeviceInfo null");
            return;
        }
        ALog.d(f10723a, "onDiscoveryDevice discoveryDeviceInfo:" + palDiscoveryDeviceInfo.getDevId() + " isPkDnNeedConvert:" + palDiscoveryDeviceInfo.isPkDnNeedConvert());
        if (palDiscoveryDeviceInfo.isPkDnNeedConvert()) {
            PluginMgr.getInstance().initDevTrans(palDiscoveryDeviceInfo, new IDevInfoTrans.IDevInfoTransListener() { // from class: com.aliyun.alink.linksdk.alcs.lpbs.a.e.d.1
                @Override // com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IDevInfoTrans.IDevInfoTransListener
                public void onComplete(boolean z2, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(d.f10723a, "initDevTrans onComplete isSuccess:" + z2 + " id:" + palDiscoveryDeviceInfo.getDevId());
                    if (!z2) {
                        ALog.e(d.f10723a, "initDevTrans onComplete isSuccess error onDiscoveryDevice not Success");
                        return;
                    }
                    palDiscoveryDeviceInfo.deviceInfo = PluginMgr.getInstance().toAliIoTPkDn(palDiscoveryDeviceInfo.deviceInfo, d.this.f10725c);
                    palDiscoveryDeviceInfo.pluginId = d.this.f10725c;
                    PluginMgr.getInstance().insertDiscoveryDev(palDiscoveryDeviceInfo.deviceInfo.getDevId(), d.this.f10725c, palDiscoveryDeviceInfo);
                    d.this.f10724b.onDiscoveryDevice(palDiscoveryDeviceInfo);
                }
            });
            return;
        }
        palDiscoveryDeviceInfo.pluginId = this.f10725c;
        PluginMgr.getInstance().insertDiscoveryDev(palDiscoveryDeviceInfo.deviceInfo.getDevId(), this.f10725c, palDiscoveryDeviceInfo);
        this.f10724b.onDiscoveryDevice(palDiscoveryDeviceInfo);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDiscoveryListener
    public void onDiscoveryFinish() {
        PalDiscoveryListener palDiscoveryListener = this.f10724b;
        if (palDiscoveryListener != null) {
            palDiscoveryListener.onDiscoveryFinish();
        }
    }
}
