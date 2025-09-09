package com.yc.utesdk.utils.close;

import com.yc.utesdk.bean.MoodPressureFatigueInfo;

/* loaded from: classes4.dex */
public interface MoodServerListener {
    public static final int RESULT_STATUS_ACCESS_PARAM_EXCEPTION = 1;
    public static final int RESULT_STATUS_NETWORK_EXCEPTION = 5;
    public static final int RESULT_STATUS_PARSING_FAIL = 4;
    public static final int RESULT_STATUS_RETURN_DATA_EMPTY = 2;
    public static final int RESULT_STATUS_RETURN_INVALID_VALUE = 3;
    public static final int RESULT_STATUS_SUCCESS = 0;
    public static final int RESULT_STATUS_UNKNOWN_EXCEPTION = 6;

    void onMoodServerStatus(MoodPressureFatigueInfo moodPressureFatigueInfo);
}
