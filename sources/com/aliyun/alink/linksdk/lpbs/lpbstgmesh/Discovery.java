package com.aliyun.alink.linksdk.lpbs.lpbstgmesh;

import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDiscoveryConfig;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDiscoveryListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class Discovery implements IPalDiscovery {
    private static final String TAG = Utils.TAG + "Discovery";

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startDiscovery(int i2, PalDiscoveryListener palDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "startDiscovery timeout" + i2);
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startNotifyMonitor(PalDiscoveryListener palDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "startNotifyMonitor");
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean stopDiscovery() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "stopDiscovery");
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean stopNotifyMonitor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "stopNotifyMonitor");
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startDiscovery(int i2, PalDiscoveryConfig palDiscoveryConfig, PalDiscoveryListener palDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "startDiscovery timeout" + i2 + " palDiscoveryConfig:" + palDiscoveryConfig);
        return false;
    }
}
