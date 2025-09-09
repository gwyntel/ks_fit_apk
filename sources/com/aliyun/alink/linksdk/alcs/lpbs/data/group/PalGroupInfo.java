package com.aliyun.alink.linksdk.alcs.lpbs.data.group;

import com.aliyun.alink.linksdk.alcs.data.ica.ICADeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;

/* loaded from: classes2.dex */
public class PalGroupInfo {
    public PalDeviceInfo[] deviceArray;
    public String groupId;

    public ICADeviceInfo[] getIcaGroupInfo() {
        PalDeviceInfo[] palDeviceInfoArr = this.deviceArray;
        if (palDeviceInfoArr == null || palDeviceInfoArr.length <= 0) {
            return null;
        }
        ICADeviceInfo[] iCADeviceInfoArr = new ICADeviceInfo[palDeviceInfoArr.length];
        int i2 = 0;
        while (true) {
            PalDeviceInfo[] palDeviceInfoArr2 = this.deviceArray;
            if (i2 >= palDeviceInfoArr2.length) {
                return iCADeviceInfoArr;
            }
            PalDeviceInfo palDeviceInfo = palDeviceInfoArr2[i2];
            iCADeviceInfoArr[i2] = new ICADeviceInfo(palDeviceInfo.productModel, palDeviceInfo.deviceId, palDeviceInfo.ip);
            i2++;
        }
    }
}
