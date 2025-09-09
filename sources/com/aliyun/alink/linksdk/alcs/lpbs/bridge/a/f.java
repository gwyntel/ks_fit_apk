package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a;

import com.aliyun.alink.linksdk.alcs.data.ica.ICADiscoveryDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalProbe;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalProbeResult;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalProbeListener;
import com.aliyun.alink.linksdk.alcs.pal.ica.ICAAlcsNative;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class f implements IPalProbe {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10816a = "[AlcsLPBS]ICAAlcsProbe";

    /* renamed from: b, reason: collision with root package name */
    private c f10817b;

    public f(c cVar) {
        this.f10817b = cVar;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalProbe
    public void probeDevice(PalDeviceInfo palDeviceInfo, PalProbeListener palProbeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10816a, "probeDevice deviceInfo:" + palDeviceInfo + " listener:" + palProbeListener);
        if (palProbeListener == null) {
            ALog.d(f10816a, "probeDevice listener null");
            return;
        }
        ICADiscoveryDeviceInfo iCADiscoveryDeviceInfoA = this.f10817b.a(palDeviceInfo.getDevId());
        if (iCADiscoveryDeviceInfoA != null) {
            ICAAlcsNative.probeDevice(iCADiscoveryDeviceInfoA.addr, iCADiscoveryDeviceInfoA.port, iCADiscoveryDeviceInfoA.deviceInfo, new l(palDeviceInfo, palProbeListener));
        } else {
            ALog.e(f10816a, "probeDevice icaDiscoveryDeviceInfo null");
            palProbeListener.onComplete(palDeviceInfo, new PalProbeResult(2));
        }
    }
}
