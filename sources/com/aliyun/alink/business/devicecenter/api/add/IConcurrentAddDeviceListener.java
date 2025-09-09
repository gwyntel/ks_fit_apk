package com.aliyun.alink.business.devicecenter.api.add;

import com.aliyun.alink.business.devicecenter.base.DCErrorCode;

/* loaded from: classes2.dex */
public interface IConcurrentAddDeviceListener {
    void onPreCheck(DeviceInfo deviceInfo, boolean z2, DCErrorCode dCErrorCode);

    void onProvisionPrepare(DeviceInfo deviceInfo, int i2);

    void onProvisionStatus(DeviceInfo deviceInfo, ProvisionStatus provisionStatus);

    void onProvisionedResult(boolean z2, DeviceInfo deviceInfo, DCErrorCode dCErrorCode);

    void onProvisioning(DeviceInfo deviceInfo);
}
