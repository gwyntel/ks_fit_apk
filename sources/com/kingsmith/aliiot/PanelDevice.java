package com.kingsmith.aliiot;

import com.aliyun.alink.linksdk.tmp.device.panel.data.PanelMethodExtraData;

/* loaded from: classes4.dex */
public class PanelDevice extends com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice {
    private DeviceInfoBean device;
    private int sensitivity;

    public PanelDevice(String str, DeviceInfoBean deviceInfoBean, int i2) {
        super(str);
        this.device = deviceInfoBean;
        this.sensitivity = i2;
    }

    public DeviceInfoBean getDevice() {
        return this.device;
    }

    boolean isWp() {
        return this.sensitivity > 0;
    }

    public PanelDevice(String str, DeviceInfoBean deviceInfoBean, PanelMethodExtraData panelMethodExtraData) {
        super(str, panelMethodExtraData);
        this.device = deviceInfoBean;
    }
}
