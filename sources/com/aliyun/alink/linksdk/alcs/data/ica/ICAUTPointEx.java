package com.aliyun.alink.linksdk.alcs.data.ica;

import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class ICAUTPointEx {
    private static final String TAG = "[alcs_coap_sdk]ICAUTPointEx";
    public static final int UNKNOWN_ERRORCODE = -1;
    private static final String UTEVENT_ALCSCONNECTED = "alcsConnected";
    private static final String UTKEY_DEVICENAME = "deviceName";
    private static final String UTKEY_PRODUCTKEY = "productKey";
    public static final String UTVALUE_ALCS = "alcs";
    protected Map<String, String> mUtProperties = new HashMap();

    public ICAUTPointEx(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "ICAUTPointEx productKey:" + str + " deviceName:" + str2);
        this.mUtProperties.put("productKey", str);
        this.mUtProperties.put("deviceName", str2);
        this.mUtProperties.put("module", "alcs");
    }

    public void trackEnd(int i2) {
        this.mUtProperties.put(AUserTrack.UTKEY_END_TIME, String.valueOf(System.currentTimeMillis()));
        this.mUtProperties.put("errorCode", String.valueOf(i2));
        AUserTrack.record(UTEVENT_ALCSCONNECTED, this.mUtProperties);
    }

    public void trackStart() {
        this.mUtProperties.put("startTime", String.valueOf(System.currentTimeMillis()));
    }
}
