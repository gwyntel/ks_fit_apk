package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface DeviceBatteryListener {
    public static final int BATTERY_STATE_CHARGING = 1;
    public static final int BATTERY_STATE_FULLY = 2;
    public static final int BATTERY_STATE_IDLE = 0;

    void onBatteryChange(int i2, int i3);

    void queryDeviceBatteryFail();

    void queryDeviceBatterySuccess(int i2);
}
