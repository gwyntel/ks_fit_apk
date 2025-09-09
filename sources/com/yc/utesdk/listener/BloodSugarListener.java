package com.yc.utesdk.listener;

import com.yc.utesdk.bean.BloodSugarSamplingInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface BloodSugarListener {
    public static final int CLEAR_SAMPLED_DATA = 5;
    public static final int GLUCOSE_METABOLISM_ALGORITHM_SWITCH = 1;
    public static final int QUERY_GLUCOSE_METABOLISM_ALGORITHM_FAIL = 2;
    public static final int QUERY_GLUCOSE_METABOLISM_ALGORITHM_SWITCH_NO = 4;
    public static final int QUERY_GLUCOSE_METABOLISM_ALGORITHM_SWITCH_YES = 3;
    public static final int SEND_BLOOD_SUGAR_LEVEL = 8;
    public static final int SEND_BLOOD_SUGAR_TIR = 6;
    public static final int SEND_BLOOD_SUGAR_VALUE = 9;
    public static final int SYNC_GLUCOSE_METABOLISM_SAMPLED_CRC = 7;

    void onBloodSugarSampling(boolean z2, List<BloodSugarSamplingInfo> list);

    void onBloodSugarStatus(boolean z2, int i2);
}
