package com.yc.utesdk.listener;

import com.yc.utesdk.bean.EcgInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface EcgChangeListener {
    public static final int ECG_PARSING_FAIL = 8;
    public static final int ECG_PARSING_SUCCESS = 9;
    public static final int ECG_TESTING_GET_OFF = 5;
    public static final int ECG_TESTING_WEAR = 4;
    public static final int ECG_TEST_FAIL = 2;
    public static final int QUERY_ECG_SAMPLING_FREQUENCY = 3;
    public static final int START_TEST_ECG = 1;
    public static final int SYNC_ECG_HISTORY_DATA_TEST_RESULT = 6;
    public static final int SYNC_ECG_SAMPLING = 7;

    void onEcgRealTime(EcgInfo ecgInfo);

    void onEcgRealTimeData(ArrayList<Double> arrayList);

    void onEcgSamplingFrequency(int i2, int i3);

    void onEcgStatus(boolean z2, int i2);

    void onEcgSyncFail();

    void onEcgSyncSuccess(boolean z2, int i2, List<EcgInfo> list);

    void onEcgSyncing();
}
