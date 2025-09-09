package com.aliyun.alink.business.devicecenter.api.discovery;

import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import java.util.List;

@Deprecated
/* loaded from: classes2.dex */
public interface IDiscoveryListener {
    void onEnrolleeDeviceFound(List<DeviceInfo> list);

    void onLocalDeviceFound(DeviceInfo deviceInfo);
}
