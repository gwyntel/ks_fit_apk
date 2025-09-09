package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.channel.TransmissionLayer;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.iot.aisbase.utils.ut.UTUtil;
import com.alibaba.fastjson.JSONObject;

/* loaded from: classes2.dex */
public class UTLogUtils {
    public static boolean mLogEnable = true;

    public static String buildAuthBusInfo(String str, String str2, String str3, int i2, String str4) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("step", (Object) str);
        jSONObject.put("pid", (Object) str2);
        jSONObject.put("address", (Object) str3);
        if ("error".equals(str)) {
            jSONObject.put("errorCode", (Object) Integer.valueOf(i2));
            jSONObject.put("errorMessage", (Object) str4);
        }
        return jSONObject.toJSONString();
    }

    public static String buildConnectionBusInfo(String str, TransmissionLayer transmissionLayer, int i2, String str2) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("step", (Object) str);
        jSONObject.put("channel", (Object) (transmissionLayer == TransmissionLayer.BLE ? "ble" : "rfcomm"));
        if ("error".equals(str)) {
            jSONObject.put("errorCode", (Object) Integer.valueOf(i2));
            jSONObject.put("errorStatus", (Object) str2);
        }
        return jSONObject.toJSONString();
    }

    public static String buildDecodeErrorBusInfo(String str) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("decoder", (Object) str);
        return jSONObject.toJSONString();
    }

    public static String buildDeviceInfo(BluetoothDeviceWrapper bluetoothDeviceWrapper) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("macAddrss", (Object) bluetoothDeviceWrapper.getAddress());
        if (bluetoothDeviceWrapper.getAisManufactureDataADV() != null) {
            jSONObject.put("pid", (Object) bluetoothDeviceWrapper.getAisManufactureDataADV().getPidStr());
        }
        jSONObject.put("subType", (Object) bluetoothDeviceWrapper.getSubtype().toString());
        return jSONObject.toJSONString();
    }

    public static String buildDisconnectionBusInfo(TransmissionLayer transmissionLayer) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("channel", (Object) (transmissionLayer == TransmissionLayer.BLE ? "ble" : "rfcomm"));
        return jSONObject.toJSONString();
    }

    public static String buildOtaBusInfo(String str, int i2, int i3, String str2) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("step", (Object) str);
        jSONObject.put("otaTaskId", (Object) 0);
        if ("start".equals(str)) {
            jSONObject.put("mtu", (Object) Integer.valueOf(i2));
        }
        if ("error".equals(str)) {
            jSONObject.put("errorCode", (Object) Integer.valueOf(i3));
            jSONObject.put("errorMessage", (Object) str2);
        }
        return jSONObject.toJSONString();
    }

    public static void setUTLogEnable(boolean z2) {
        mLogEnable = z2;
    }

    public static void updateBusInfo(String str, String str2, String str3) {
        if (mLogEnable) {
            UTUtil.updateBusInfo(str, str2, str3);
        }
    }
}
