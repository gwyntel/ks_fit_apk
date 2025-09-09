package com.yc.utesdk.listener;

import com.yc.utesdk.bean.OxygenInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface OxygenChangeListener {
    public static final int OXYGEN_AUTOMATIC_TEST = 6;
    public static final int OXYGEN_TEST_TIME_OUT = 5;
    public static final int OXYGEN_TIME_PERIOD = 7;
    public static final int START_TEST_OXYGEN_NO_VALUE = 1;
    public static final int STOP_TEST_OXYGEN_HAS_VALUE = 4;
    public static final int STOP_TEST_OXYGEN_NO_VALUE = 3;

    void onOxygenRealTime(OxygenInfo oxygenInfo);

    void onOxygenStatus(boolean z2, int i2);

    void onOxygenSyncFail();

    void onOxygenSyncSuccess(List<OxygenInfo> list);

    void onOxygenSyncing();
}
