package com.aliyun.alink.business.devicecenter.discover;

import com.aliyun.alink.business.devicecenter.api.discovery.DiscoveryType;
import com.aliyun.alink.business.devicecenter.api.discovery.IDeviceDiscoveryListener;
import com.aliyun.alink.business.devicecenter.channel.BaseChainProcessor;

/* loaded from: classes2.dex */
public class DiscoverChainProcessor extends BaseChainProcessor<IDiscoverChain> implements IDiscoverChain {
    @Override // com.aliyun.alink.business.devicecenter.discover.IDiscoverChain
    public void addDiscoveryType(DiscoveryType discoveryType) {
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.IDiscoverChain
    public boolean isSupport() {
        return true;
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.IDiscoverChain
    public void startDiscover(IDeviceDiscoveryListener iDeviceDiscoveryListener) {
        for (int i2 = 0; i2 < getChainSize(); i2++) {
            IDiscoverChain chainItem = getChainItem(i2);
            if (chainItem != null) {
                chainItem.startDiscover(iDeviceDiscoveryListener);
            }
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.IDiscoverChain
    public void stopDiscover() {
        int chainSize = getChainSize();
        while (true) {
            chainSize--;
            if (chainSize <= -1) {
                clearChain();
                return;
            }
            IDiscoverChain chainItem = getChainItem(chainSize);
            if (chainItem != null) {
                chainItem.stopDiscover();
                removeChain(chainItem);
            }
        }
    }
}
