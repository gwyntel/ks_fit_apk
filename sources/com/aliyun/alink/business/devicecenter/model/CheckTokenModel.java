package com.aliyun.alink.business.devicecenter.model;

import com.aliyun.alink.business.devicecenter.discover.CloudEnrolleeDeviceModel;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class CheckTokenModel implements Serializable {
    public String productKey = null;
    public String bindToken = null;
    public String deviceName = null;

    public static List<CheckTokenModel> getCheckModelList(List<CloudEnrolleeDeviceModel> list, String str) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            CloudEnrolleeDeviceModel cloudEnrolleeDeviceModel = list.get(i2);
            if (cloudEnrolleeDeviceModel != null) {
                CheckTokenModel checkTokenModel = new CheckTokenModel();
                checkTokenModel.productKey = cloudEnrolleeDeviceModel.enrolleeProductKey;
                checkTokenModel.deviceName = cloudEnrolleeDeviceModel.enrolleeDeviceName;
                checkTokenModel.bindToken = str;
                arrayList.add(checkTokenModel);
            }
        }
        return arrayList;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CheckTokenModel)) {
            return super.equals(obj);
        }
        CheckTokenModel checkTokenModel = (CheckTokenModel) obj;
        return StringUtils.isEqualString(this.productKey, checkTokenModel.productKey) && StringUtils.isEqualString(this.deviceName, checkTokenModel.deviceName) && StringUtils.isEqualString(this.bindToken, checkTokenModel.bindToken);
    }

    public String toString() {
        return "{\"bindToken\":\"" + this.bindToken + "\",\"deviceName\":\"" + this.deviceName + "\",\"productKey\":\"" + this.productKey + "\"}";
    }
}
