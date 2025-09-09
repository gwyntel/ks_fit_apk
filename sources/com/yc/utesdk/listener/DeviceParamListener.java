package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface DeviceParamListener {
    public static final int BRIGHT_SCREEN_TIME = 1;
    public static final int QUERY_BRIGHT_SCREEN_PARAM = 8;
    public static final int QUERY_DO_NOT_DISTURB = 102;
    public static final int QUERY_DRINK_WATER_REMIND = 101;
    public static final int QUERY_HEART_RATE_PARAM = 7;
    public static final int QUERY_HEART_RATE_SWITCH = 5;
    public static final int QUERY_PRESSURE_SWITCH = 6;
    public static final int QUERY_SEDENTARY_REMIND = 100;
    public static final int QUERY_SN_CODE = 4;
    public static final int QUERY_TEMPERATURE_FORMAT = 3;
    public static final int QUERY_TIME_FORMAT = 2;
    public static final int SET_BRIGHT_SCREEN_PARAM = 9;

    <T> void onQueryDeviceParam(boolean z2, int i2, T t2);
}
