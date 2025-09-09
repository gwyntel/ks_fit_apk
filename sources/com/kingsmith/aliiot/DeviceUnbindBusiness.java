package com.kingsmith.aliiot;

import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class DeviceUnbindBusiness {
    public static final String TAG = "DeviceUnbindBusiness";

    public void unbind(String str, IoTCallback ioTCallback) {
        HashMap map = new HashMap();
        map.put("iotId", str);
        new IoTAPIClientFactory().getClient().send(new IoTRequestBuilder().setPath("uc/unbindAccountAndDev").setApiVersion("1.0.2").setAuthType(AlinkConstants.KEY_IOT_AUTH).setParams(map).build(), ioTCallback);
    }
}
