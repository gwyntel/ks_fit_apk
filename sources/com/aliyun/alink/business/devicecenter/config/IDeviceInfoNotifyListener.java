package com.aliyun.alink.business.devicecenter.config;

import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;

/* loaded from: classes2.dex */
public interface IDeviceInfoNotifyListener {
    void onDeviceFound(DeviceInfo deviceInfo);

    void onFailure(DCErrorCode dCErrorCode);
}
