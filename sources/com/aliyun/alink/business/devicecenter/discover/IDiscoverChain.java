package com.aliyun.alink.business.devicecenter.discover;

import com.aliyun.alink.business.devicecenter.api.discovery.DiscoveryType;
import com.aliyun.alink.business.devicecenter.api.discovery.IDeviceDiscoveryListener;

/* loaded from: classes2.dex */
public interface IDiscoverChain {
    void addDiscoveryType(DiscoveryType discoveryType);

    boolean isSupport();

    void startDiscover(IDeviceDiscoveryListener iDeviceDiscoveryListener);

    void stopDiscover();
}
