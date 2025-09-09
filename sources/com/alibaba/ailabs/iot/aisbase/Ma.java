package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.fastjson.JSONObject;

/* loaded from: classes2.dex */
public class Ma {
    public static void a(String str, String str2, String str3) {
    }

    public static void a(String str, BluetoothDeviceWrapper bluetoothDeviceWrapper, String str2, int i2, String str3) {
        UTLogUtils.buildDeviceInfo(bluetoothDeviceWrapper);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("step", (Object) str2);
        jSONObject.put("channel", (Object) "ble");
        if ("error".equals(str2)) {
            jSONObject.put("errorCode", (Object) Integer.valueOf(i2));
            jSONObject.put("errorDesc", (Object) str3);
        }
    }
}
