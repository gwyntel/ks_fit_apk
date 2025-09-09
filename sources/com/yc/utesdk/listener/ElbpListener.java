package com.yc.utesdk.listener;

import com.yc.utesdk.bean.ElbpBleMiddleInfo;
import com.yc.utesdk.bean.ElbpBlePpgInfo;
import com.yc.utesdk.bean.ElbpMiddleDataInfo;
import com.yc.utesdk.bean.ElbpPpgDataInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface ElbpListener {
    public static final int ALGORITHM_ACTIVATED = 15;
    public static final int ALGORITHM_ACTIVE_FAIL = 18;
    public static final int ALGORITHM_ACTIVE_SUCCESS = 17;
    public static final int ALGORITHM_NOT_ACTIVATED = 16;
    public static final int APP_ELBP_SAMPLING_SUCCESS = 6;
    public static final int APP_START_ELBP_SAMPLING = 4;
    public static final int CLOSE_DEBUG_ELBP_PPG_MIDDLE_DATA = 12;
    public static final int DEVICE_ELBP_SAMPLING_SUCCESS = 7;
    public static final int DEVICE_START_ELBP_SAMPLING = 5;
    public static final int ELBP_SAMPLING_HANDS_OF = 10;
    public static final int ELBP_SAMPLING_INTERRUPT = 8;
    public static final int ELBP_SAMPLING_TIMEOUT = 9;
    public static final int OPEN_DEBUG_ELBP_PPG_MIDDLE_DATA = 11;
    public static final int QUERY_ELBP_SAMPLING_STATUS_FAIL = 3;
    public static final int QUERY_ELBP_SAMPLING_STATUS_NO = 2;
    public static final int QUERY_ELBP_SAMPLING_STATUS_YES = 1;
    public static final int QUERY_MOOD_TEST_STATUS_FAIL = 104;
    public static final int SAMPLING_TYPE_MIDDLE_DATA = 2;
    public static final int SAMPLING_TYPE_PPG = 1;
    public static final int SEND_ELBS_CALIBRATION_MODEL = 19;
    public static final int SET_ELBP_DATA_TO_DEVICE = 13;
    public static final int SET_ELBP_SAMPLING_INTERVAL = 14;
    public static final int SET_LOGIN_EL_SERVER_INFO = 20;

    void onElbpAlgorithmVersion(boolean z2, String str);

    void onElbpMiddleDataSyncFail();

    void onElbpMiddleDataSyncSuccess(List<ElbpBleMiddleInfo> list);

    void onElbpMiddleDataSyncing();

    void onElbpMiddleRealTime(List<ElbpMiddleDataInfo> list);

    void onElbpPpgDataSyncFail();

    void onElbpPpgDataSyncSuccess(List<ElbpBlePpgInfo> list);

    void onElbpPpgDataSyncing();

    void onElbpPpgRealTime(ElbpPpgDataInfo elbpPpgDataInfo);

    void onElbpStatus(boolean z2, int i2);
}
