package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface SimpleCallbackListener {
    public static final int BIND_CONNECT_BAND_CLICK_CANCEL = 6;
    public static final int BIND_CONNECT_BAND_CLICK_CONFIRM = 3;
    public static final int BIND_CONNECT_COMPARE_SUCCESS = 2;
    public static final int BIND_CONNECT_IDVALID_ID = 5;
    public static final int BIND_CONNECT_SEND_ACCOUNT_ID = 1;
    public static final int BIND_CONNECT_VALID_ID = 4;
    public static final int CANCEL_UPGRADE_JX = 276;
    public static final int CLOSE_DEVICE_ON_SCREEN_DURATION = 31;
    public static final int COMMAND_WRITE_CALLBACK = 0;
    public static final int CONTROL_DEVICE_POWER_OFF = 28;
    public static final int CONTROL_DEVICE_RESTART = 29;
    public static final int DELETE_DEVICE_TIME_ZONE = 27;
    public static final int DEVICE_FACTORY_DATA_RESET = 38;
    public static final int DEVICE_POWER_SAVING_MODE = 32;
    public static final int DEVICE_RING_STATUS = 17;
    public static final int DEVICE_UNIT_HOUR_FORMATE = 22;
    public static final int DEVICE_VIBRATION_START_STOP = 14;
    public static final int DO_NOT_DISTURB = 21;
    public static final int DRINK_WATER_REMIND = 19;
    public static final int FEMALE_MENSTRUAL_CYCLE = 23;
    public static final int FEMALE_MENSTRUAL_FUNCTION_SWITCH_CLOSE = 37;
    public static final int FEMALE_MENSTRUAL_FUNCTION_SWITCH_OPEN = 36;
    public static final int FIND_PHONE_FUNCTION = 15;
    public static final int LONG_PRESS_BUTTON_SECOND = 10;
    public static final int LONG_PRESS_BUTTON_THIRD = 11;
    public static final int OPEN_DEVICE_ON_SCREEN_DURATION = 30;
    public static final int PHONE_RING_STATUS = 16;
    public static final int PRESS_FIND_PHONE_BUTTON = 8;
    public static final int PRESS_SOS_BUTTON = 7;
    public static final int PRESS_SWITCH_SCREEN_BUTTON = 9;
    public static final int QUERY_FEMALE_MENSTRUAL_SWITCH_FAIL = 33;
    public static final int QUERY_FEMALE_MENSTRUAL_SWITCH_NO = 35;
    public static final int QUERY_FEMALE_MENSTRUAL_SWITCH_YES = 34;
    public static final int SEDENTARY_REMIND = 18;
    public static final int SEND_MENSTRUAL_END_DAY = 40;
    public static final int SEND_MENSTRUAL_START_DAY = 39;
    public static final int SET_DEVICE_TIME_ZONE = 26;
    public static final int SET_DEVICE_WEATHER = 24;
    public static final int SET_DEVICE_WEATHER_CITY_NAME = 25;
    public static final int SYNC_DEVICE_PARAMETERS = 13;
    public static final int SYNC_DEVICE_TIME = 12;
    public static final int WASH_HANDS_REMIND = 20;

    void onSimpleCallback(boolean z2, int i2);
}
