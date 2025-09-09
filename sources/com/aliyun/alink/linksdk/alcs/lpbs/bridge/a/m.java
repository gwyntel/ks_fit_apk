package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a;

import com.aliyun.alink.linksdk.alcs.data.ica.ICADeviceInfo;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAInitData;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAOptions;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAReqMessage;
import com.aliyun.alink.linksdk.alcs.data.ica.ICARspMessage;
import com.aliyun.alink.linksdk.alcs.data.ica.ICASubMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalInitData;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalRspMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalSubMessage;

/* loaded from: classes2.dex */
public class m {
    public static ICAInitData a(PalInitData palInitData) {
        if (palInitData == null) {
            return null;
        }
        PalDeviceInfo palDeviceInfo = palInitData.deviceInfo;
        return palDeviceInfo != null ? new ICAInitData(palDeviceInfo.productModel, palDeviceInfo.deviceId, palInitData.role) : new ICAInitData(null, null, palInitData.role);
    }

    public static ICADeviceInfo a(PalDeviceInfo palDeviceInfo) {
        return new ICADeviceInfo(palDeviceInfo.productModel, palDeviceInfo.deviceId);
    }

    public static ICAReqMessage a(PalReqMessage palReqMessage) {
        ICAReqMessage iCAReqMessage = new ICAReqMessage();
        PalDeviceInfo palDeviceInfo = palReqMessage.deviceInfo;
        iCAReqMessage.deviceInfo = new ICADeviceInfo(palDeviceInfo.productModel, palDeviceInfo.deviceId);
        Object obj = palReqMessage.palOptions;
        if (obj != null && (obj instanceof ICAOptions)) {
            ICAOptions iCAOptions = (ICAOptions) obj;
            iCAReqMessage.groupId = iCAOptions.groupId;
            iCAReqMessage.code = iCAOptions.code;
            iCAReqMessage.type = iCAOptions.type;
            iCAReqMessage.rspType = iCAOptions.rspType;
        }
        iCAReqMessage.topic = palReqMessage.topic;
        iCAReqMessage.payload = palReqMessage.payload;
        return iCAReqMessage;
    }

    public static PalRspMessage a(ICARspMessage iCARspMessage) {
        if (iCARspMessage == null) {
            return null;
        }
        PalRspMessage palRspMessage = new PalRspMessage();
        palRspMessage.code = iCARspMessage.code;
        palRspMessage.cbContext = Integer.valueOf(iCARspMessage.cbContext);
        palRspMessage.payload = iCARspMessage.payload;
        return palRspMessage;
    }

    public static ICASubMessage a(PalSubMessage palSubMessage) {
        if (palSubMessage == null) {
            return null;
        }
        ICASubMessage iCASubMessage = new ICASubMessage();
        PalDeviceInfo palDeviceInfo = palSubMessage.deviceInfo;
        iCASubMessage.deviceInfo = new ICADeviceInfo(palDeviceInfo.productModel, palDeviceInfo.deviceId);
        iCASubMessage.topic = palSubMessage.topic;
        iCASubMessage.payload = palSubMessage.payload;
        return iCASubMessage;
    }
}
