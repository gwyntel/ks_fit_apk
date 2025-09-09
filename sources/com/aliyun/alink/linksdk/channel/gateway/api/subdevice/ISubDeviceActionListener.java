package com.aliyun.alink.linksdk.channel.gateway.api.subdevice;

import com.aliyun.alink.linksdk.tools.AError;

/* loaded from: classes2.dex */
public interface ISubDeviceActionListener {
    void onFailed(AError aError);

    void onSuccess();
}
