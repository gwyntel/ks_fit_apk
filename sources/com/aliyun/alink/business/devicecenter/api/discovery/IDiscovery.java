package com.aliyun.alink.business.devicecenter.api.discovery;

import com.aliyun.alink.business.devicecenter.base.DCErrorCode;

/* loaded from: classes2.dex */
public interface IDiscovery extends IDeviceDiscoveryListener {
    void onFail(DCErrorCode dCErrorCode);
}
