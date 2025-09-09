package com.aliyun.alink.linksdk.alcs.lpbs.data;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class PalDiscoveryDeviceInfo {
    public static final String EXTRA_KEY_BREEZE_RESET = "breezeReset";
    public static final String EXTRA_KEY_BREEZE_SUB_TYPE = "breezeSubType";
    public String dataFormat;
    public PalDeviceInfo deviceInfo;
    public Map<String, Object> extraData;
    public boolean isDataToCloud;
    public String modelType;
    public String pluginId;
    public String tslData;

    public PalDiscoveryDeviceInfo() {
        this.isDataToCloud = true;
        this.modelType = "0";
        this.extraData = new HashMap();
    }

    public String getDevId() {
        PalDeviceInfo palDeviceInfo = this.deviceInfo;
        if (palDeviceInfo != null) {
            return palDeviceInfo.getDevId();
        }
        return null;
    }

    public boolean isPkDnNeedConvert() {
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("deviceInfo:");
        Object obj = this.deviceInfo;
        if (obj == null) {
            obj = TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        sb.append(obj);
        sb.append("pluginId:");
        sb.append(this.pluginId);
        sb.append("modelType:");
        sb.append(this.modelType);
        sb.append("extradata:");
        sb.append(this.extraData);
        return sb.toString();
    }

    public PalDiscoveryDeviceInfo(PalDeviceInfo palDeviceInfo) {
        this();
        this.deviceInfo = palDeviceInfo;
    }
}
