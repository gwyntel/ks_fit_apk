package com.yc.utesdk.listener;

import com.yc.utesdk.bean.BreatheInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface BreathingRateChangeListener {
    public static final int BREATHING_RATE_AUTOMATICTEST_TEST = 4;
    public static final int BREATHING_RATE_TEST_STATUS_NO = 8;
    public static final int BREATHING_RATE_TEST_STATUS_YES = 7;
    public static final int BREATHING_RATE_TEST_TIME_OUT = 3;
    public static final int BREATHING_RATE_TIME_PERIOD = 5;
    public static final int QUERY_BREATHING_RATE_TEST_STATUS = 6;
    public static final int START_TEST_BREATHING_RATE = 1;
    public static final int STOP_TEST_BREATHING_RATE = 2;

    void onBreathingRateRealTime(BreatheInfo breatheInfo);

    void onBreathingRateStatus(boolean z2, int i2);

    void onBreathingRateSyncFail();

    void onBreathingRateSyncSuccess(List<BreatheInfo> list);

    void onBreathingRateSyncing();
}
