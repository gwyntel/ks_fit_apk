package com.yc.utesdk.listener;

import com.yc.utesdk.bean.TemperatureInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface TemperatureChangeListener {
    public static final int CALIBRATION_TEMPERATURE = 7;
    public static final int DELETE_TEMPERATURE_HISTORY_DATA = 6;
    public static final int SET_MAX_MIN_ALARM_TEMPERATURE = 5;
    public static final int START_TEST_TEMPERATURE = 1;
    public static final int TEMPERATURE_ALARM = 8;
    public static final int TEMPERATURE_AUTOMATICTEST_TEST = 3;
    public static final int TEMPERATURE_TEST_TIME_OUT = 2;
    public static final int TEMPERATURE_TIME_PERIOD = 4;
    public static final int TEMPERATURE_UNIT_CHANGE = 9;

    void onTemperatureRealTime(TemperatureInfo temperatureInfo);

    void onTemperatureStatus(boolean z2, int i2);

    void onTemperatureSyncFail();

    void onTemperatureSyncSuccess(List<TemperatureInfo> list);

    void onTemperatureSyncing();
}
