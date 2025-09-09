package com.umeng.commonsdk.statistics.common;

import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.taobao.accs.common.Constants;

/* loaded from: classes4.dex */
public enum DeviceTypeEnum {
    IMEI(Constants.KEY_IMEI, Constants.KEY_IMEI),
    OAID("oaid", "oaid"),
    ANDROIDID("android_id", "android_id"),
    MAC(AlinkConstants.KEY_MAC, AlinkConstants.KEY_MAC),
    SERIALNO("serial_no", "serial_no"),
    IDFA("idfa", "idfa"),
    DEFAULT(TmpConstant.GROUP_ROLE_UNKNOWN, TmpConstant.GROUP_ROLE_UNKNOWN);

    private String description;
    private String deviceIdType;

    DeviceTypeEnum(String str, String str2) {
        this.deviceIdType = str;
        this.description = str2;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDeviceIdType() {
        return this.deviceIdType;
    }
}
