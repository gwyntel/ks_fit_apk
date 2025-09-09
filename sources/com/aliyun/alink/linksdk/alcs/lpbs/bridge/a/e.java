package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a;

import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDiscoveryConfig;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDiscoveryListener;
import com.aliyun.alink.linksdk.alcs.pal.ica.ICAAlcsNative;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class e implements IPalDiscovery {

    /* renamed from: b, reason: collision with root package name */
    private static final String f10814b = "[AlcsLPBS]ICAAlcsDiscovery";

    /* renamed from: a, reason: collision with root package name */
    private c f10815a;

    public e(c cVar) {
        this.f10815a = cVar;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startDiscovery(int i2, PalDiscoveryListener palDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10814b, "startDiscovery timeOut:" + i2 + " listener:" + palDiscoveryListener);
        return ICAAlcsNative.discoveryDevice(i2, new i(this.f10815a, palDiscoveryListener));
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startNotifyMonitor(PalDiscoveryListener palDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10814b, "startNotifyMonitor listener:" + palDiscoveryListener);
        return ICAAlcsNative.regDeviceNotifyListener(new i(this.f10815a, palDiscoveryListener));
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean stopDiscovery() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10814b, "stopDiscovery");
        return ICAAlcsNative.stopDiscoveryDevice();
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean stopNotifyMonitor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10814b, "stopNotifyMonitor");
        return ICAAlcsNative.unregDeviceNotifyListener();
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startDiscovery(int i2, PalDiscoveryConfig palDiscoveryConfig, PalDiscoveryListener palDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10814b, "startDiscovery timeOut:" + i2 + " PalDiscoveryConfig:" + palDiscoveryConfig + " listener:" + palDiscoveryListener);
        return ICAAlcsNative.discoveryDevice(i2, new i(this.f10815a, palDiscoveryListener));
    }
}
