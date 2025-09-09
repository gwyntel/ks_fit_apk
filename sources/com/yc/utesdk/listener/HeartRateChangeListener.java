package com.yc.utesdk.listener;

import com.yc.utesdk.bean.HeartRateBestValueInfo;
import com.yc.utesdk.bean.HeartRateHourBestValueInfo;
import com.yc.utesdk.bean.HeartRateInfo;
import com.yc.utesdk.bean.HeartRateRestInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface HeartRateChangeListener {
    public static final int CLOSE_AUTO_TEST_HEART_RATE = 2;
    public static final int CLOSE_AUTO_TEST_HEART_RATE_INTERVAL = 10;
    public static final int HEART_RATE_COMMON_CONTINUE = 12;
    public static final int HEART_RATE_COMMON_INTERVAL = 14;
    public static final int HEART_RATE_COMMON_STATIC = 13;
    public static final int HEART_RATE_STORAGE_INTERVAL = 11;
    public static final int OPEN_AUTO_TEST_HEART_RATE = 1;
    public static final int OPEN_AUTO_TEST_HEART_RATE_INTERVAL = 9;
    public static final int REPORT_REST_HEART_RATE = 7;
    public static final int REPORT_REST_HEART_RATE_HOUR_BEST = 8;
    public static final int SET_MAX_HEART_RATE = 6;
    public static final int START_TEST_HEART_RATE = 3;
    public static final int STOP_TEST_HEART_RATE = 4;
    public static final int STOP_TEST_HEART_RATE_INVALID_DATA = 5;

    void onHeartRateBestValue(HeartRateBestValueInfo heartRateBestValueInfo);

    void onHeartRateHourRestBestValue(HeartRateHourBestValueInfo heartRateHourBestValueInfo);

    void onHeartRateRealTime(HeartRateInfo heartRateInfo);

    void onHeartRateRest(HeartRateRestInfo heartRateRestInfo);

    void onHeartRateRestSyncFail();

    void onHeartRateRestSyncSuccess(List<HeartRateRestInfo> list);

    void onHeartRateRestSyncing();

    void onHeartRateStatus(boolean z2, int i2);

    void onHeartRateSyncFail();

    void onHeartRateSyncSuccess(List<HeartRateInfo> list);

    void onHeartRateSyncing();
}
