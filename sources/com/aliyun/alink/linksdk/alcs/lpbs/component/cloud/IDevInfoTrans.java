package com.aliyun.alink.linksdk.alcs.lpbs.component.cloud;

import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDiscoveryDeviceInfo;

/* loaded from: classes2.dex */
public interface IDevInfoTrans {

    public interface IDevInfoTransListener {
        void onComplete(boolean z2, Object obj);
    }

    void init(PalDiscoveryDeviceInfo palDiscoveryDeviceInfo, IDevInfoTransListener iDevInfoTransListener);

    PalDeviceInfo toAliIoTDeviceInfo(PalDeviceInfo palDeviceInfo, String str);

    PalDeviceInfo toPrivateDeviceInfo(PalDeviceInfo palDeviceInfo, String str);
}
