package com.yc.utesdk.listener;

import com.yc.utesdk.bean.ElHRVMiddleDataInfo;

/* loaded from: classes4.dex */
public interface ElHRVListener {
    public static final int INTERRUPT_TEST = 3;
    public static final int QUERY_TEST_STATUS_FAIL = 6;
    public static final int START_TEST = 1;
    public static final int STOP_TEST = 2;
    public static final int TEST_STATUS_NO = 5;
    public static final int TEST_STATUS_YES = 4;

    void onElHRVPpgRealTime(ElHRVMiddleDataInfo elHRVMiddleDataInfo);

    void onElHRVStatus(boolean z2, int i2);
}
