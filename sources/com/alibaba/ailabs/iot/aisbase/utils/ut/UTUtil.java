package com.alibaba.ailabs.iot.aisbase.utils.ut;

import com.alibaba.ailabs.iot.aisbase.Ma;
import com.alibaba.ailabs.iot.aisbase.Na;
import com.alibaba.ailabs.iot.aisbase.env.AppEnv;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;

/* loaded from: classes2.dex */
public class UTUtil {
    public static void updateBusInfo(String str, BluetoothDeviceWrapper bluetoothDeviceWrapper, String str2, int i2, String str3) {
        if (AppEnv.IS_GENIE_ENV) {
            Na.a(str, bluetoothDeviceWrapper, str2, i2, str3);
        } else {
            Ma.a(str, bluetoothDeviceWrapper, str2, i2, str3);
        }
    }

    public static void updateBusInfo(String str, String str2, String str3) {
        if (AppEnv.IS_GENIE_ENV) {
            Na.a(str, str2, str3);
        } else {
            Ma.a(str, str2, str3);
        }
    }
}
