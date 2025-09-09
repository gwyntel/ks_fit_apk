package com.aliyun.alink.linksdk.tmp.device.panel.listener;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice;

/* loaded from: classes2.dex */
public class AlcsMulChannelEventListenerWrapper extends AlcsEventListenerWrapper {
    public AlcsMulChannelEventListenerWrapper(LocalChannelDevice localChannelDevice, DeviceBasicData deviceBasicData, IPanelCallback iPanelCallback, IPanelEventCallback iPanelEventCallback) {
        super(localChannelDevice, deviceBasicData, iPanelCallback, iPanelEventCallback);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.AlcsEventListenerWrapper
    public void stopLocalConnect() {
    }
}
