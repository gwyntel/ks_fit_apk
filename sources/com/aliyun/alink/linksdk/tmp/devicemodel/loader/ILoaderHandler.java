package com.aliyun.alink.linksdk.tmp.devicemodel.loader;

import com.aliyun.alink.linksdk.tmp.devicemodel.DeviceModel;

/* loaded from: classes2.dex */
public interface ILoaderHandler {
    void onDeserialize(DeviceModel deviceModel);

    void onDeserializeError(String str);

    void onSerialize(String str);

    void onSerializeError(String str);
}
