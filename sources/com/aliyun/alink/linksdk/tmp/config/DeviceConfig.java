package com.aliyun.alink.linksdk.tmp.config;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;

/* loaded from: classes2.dex */
public class DeviceConfig {
    protected DeviceBasicData mBasicData;
    protected DeviceType mDeviceType;

    public enum DeviceType {
        CLIENT,
        SERVER,
        PROVISION,
        PROVISION_RECEIVER
    }

    public DeviceBasicData getBasicData() {
        return this.mBasicData;
    }

    public String getDevId() {
        DeviceBasicData deviceBasicData = this.mBasicData;
        if (deviceBasicData != null) {
            return deviceBasicData.getDevId();
        }
        return null;
    }

    public DeviceType getDeviceType() {
        return this.mDeviceType;
    }

    public void setBasicData(DeviceBasicData deviceBasicData) {
        this.mBasicData = deviceBasicData;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.mDeviceType = deviceType;
    }
}
