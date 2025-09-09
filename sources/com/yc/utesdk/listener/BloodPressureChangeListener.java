package com.yc.utesdk.listener;

import com.yc.utesdk.bean.BloodPressureInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface BloodPressureChangeListener {
    public static final int BLOOD_PRESSURE_TEST_ERROR = 4;
    public static final int BLOOD_PRESSURE_TEST_TIME_OUT = 3;
    public static final int START_TEST_BLOOD_PRESSURE = 1;
    public static final int STOP_TEST_BLOOD_PRESSURE = 2;

    void onBloodPressureRealTime(BloodPressureInfo bloodPressureInfo);

    void onBloodPressureStatus(boolean z2, int i2);

    void onBloodPressureSyncFail();

    void onBloodPressureSyncSuccess(List<BloodPressureInfo> list);

    void onBloodPressureSyncing();
}
