package com.aliyun.alink.business.devicecenter.api.discovery;

import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import java.util.List;

/* loaded from: classes2.dex */
public interface IDeviceDiscoveryListener {
    void onDeviceFound(DiscoveryType discoveryType, List<DeviceInfo> list);
}
