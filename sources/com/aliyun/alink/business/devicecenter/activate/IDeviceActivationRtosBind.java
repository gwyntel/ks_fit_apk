package com.aliyun.alink.business.devicecenter.activate;

import com.aliyun.alink.business.devicecenter.channel.http.services.IActivationRtosRequestService;
import java.util.Map;

/* loaded from: classes2.dex */
public interface IDeviceActivationRtosBind {
    void activateDevice(Map<String, Object> map, IActivateRtosDeviceCallback iActivateRtosDeviceCallback);

    void init(IActivationRtosRequestService iActivationRtosRequestService);
}
