package com.yc.utesdk.listener;

import com.yc.utesdk.bean.ElbsPpgDataInfo;

/* loaded from: classes4.dex */
public interface ElbsListener {
    public static final int INTERRUPT_TEST = 3;
    public static final int QUERY_TEST_STATUS_FAIL = 6;
    public static final int START_TEST = 1;
    public static final int STOP_TEST = 2;
    public static final int TEST_STATUS_NO = 5;
    public static final int TEST_STATUS_YES = 4;

    void onElbsPpgRealTime(ElbsPpgDataInfo elbsPpgDataInfo);

    void onElbsStatus(boolean z2, int i2);
}
