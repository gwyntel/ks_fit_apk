package com.yc.utesdk.listener;

import com.yc.utesdk.bean.MoodPressureFatigueInfo;
import com.yc.utesdk.bean.MoodSensorInterfaceInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface MoodPressureListener {
    public static final int ALGORITHM_ACTIVATED = 107;
    public static final int ALGORITHM_ACTIVE_FAIL = 110;
    public static final int ALGORITHM_ACTIVE_SUCCESS = 109;
    public static final int ALGORITHM_NOT_ACTIVATED = 108;
    public static final int AUTO_TEST_MOOD = 102;
    public static final int AUTO_TEST_TIME_PERIOD = 103;
    public static final int DELETE_MOOD_PRESSURE_DATA = 111;
    public static final int QUERY_MOOD_TEST_STATUS_FAIL = 104;
    public static final int QUERY_MOOD_TEST_STATUS_NO = 106;
    public static final int QUERY_MOOD_TEST_STATUS_YES = 105;
    public static final int START_TEST_MOOD = 100;
    public static final int STOP_APP_EXIT = 247;
    public static final int STOP_DEVICE_EXIT = 245;
    public static final int STOP_DEVICE_TESTING = 246;
    public static final int STOP_HANDS_OF = 243;
    public static final int STOP_IN_MOTION = 244;
    public static final int STOP_NORMAL = 0;
    public static final int STOP_SERVER_NETWORK_ERRO = 240;
    public static final int STOP_SERVER_PARSING_EXCEPTION = 241;
    public static final int STOP_TEST_TIMEOUT = 242;

    void onMoodPressureRealTime(MoodPressureFatigueInfo moodPressureFatigueInfo);

    void onMoodPressureSensor(MoodSensorInterfaceInfo moodSensorInterfaceInfo);

    void onMoodPressureStatus(boolean z2, int i2);

    void onMoodPressureSyncFail();

    void onMoodPressureSyncSuccess(List<MoodPressureFatigueInfo> list);

    void onMoodPressureSyncing();
}
