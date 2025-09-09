package com.aliyun.alink.business.devicecenter.api.add;

import com.aliyun.alink.business.devicecenter.base.DCErrorCode;

/* loaded from: classes2.dex */
public interface IAddDeviceListener {
    void onPreCheck(boolean z2, DCErrorCode dCErrorCode);

    void onProvisionPrepare(int i2);

    void onProvisionStatus(ProvisionStatus provisionStatus);

    void onProvisionedResult(boolean z2, DeviceInfo deviceInfo, DCErrorCode dCErrorCode);

    void onProvisioning();
}
