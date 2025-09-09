package com.aliyun.alink.linksdk.alcs.lpbs.bridge;

import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDiscoveryConfig;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDiscoveryListener;

/* loaded from: classes2.dex */
public interface IPalDiscovery {
    public static final int DISCOVERY_TYPE_FINISH = 0;
    public static final int DISCOVERY_TYPE_FOUND = 1;

    boolean startDiscovery(int i2, PalDiscoveryConfig palDiscoveryConfig, PalDiscoveryListener palDiscoveryListener);

    boolean startDiscovery(int i2, PalDiscoveryListener palDiscoveryListener);

    boolean startNotifyMonitor(PalDiscoveryListener palDiscoveryListener);

    boolean stopDiscovery();

    boolean stopNotifyMonitor();
}
