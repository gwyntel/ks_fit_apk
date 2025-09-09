package com.yc.utesdk.listener;

import com.yc.utesdk.bean.CSBPDevicePmInfo;
import com.yc.utesdk.bean.CSBPHeartRateAndOxygenInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface CsbpChangeListener {
    public static final int CSBP_CALIBRATE_DEVICE_EXCEPTION = 13;
    public static final int CSBP_CALIBRATE_DEVICE_HANDOFF = 12;
    public static final int CSBP_CALIBRATE_TIME_OUT = 11;
    public static final int CSBP_CALIBRATION_STATUS_NO = 3;
    public static final int CSBP_CALIBRATION_STATUS_YES = 2;
    public static final int CSBP_DEVICE_ACTIVATE_STATUS_NO = 16;
    public static final int CSBP_DEVICE_NO_SUPPORT_ACTIVATE_FUNCTION = 17;
    public static final int CSBP_DEVICE_STOP_CALIBRATION = 6;
    public static final int CSBP_PM_DATA_FAIL = 10;
    public static final int CSBP_QUERY_CALIBRATION_STATUS = 1;
    public static final int CSBP_QUERY_DEVICE_ACTIVATE_STATUS = 15;
    public static final int CSBP_QUERY_DEVICE_CHIP_FAIL = 14;
    public static final int CSBP_QUERY_DEVICE_MODULE_ID = 18;
    public static final int CSBP_RESET_CALIBRATE_PARAM = 8;
    public static final int CSBP_SEND_ACTIVATE_CODE_TO_DEVICE = 19;
    public static final int CSBP_SEND_CO_DATA = 9;
    public static final int CSBP_SET_MEDICATION_HIGHT_BP = 7;
    public static final int CSBP_START_CALIBRATION = 4;
    public static final int CSBP_STOP_CALIBRATION = 5;

    void onCsbpStatus(boolean z2, int i2);

    void onDevicePmSuccess(List<CSBPDevicePmInfo> list);

    void onDeviceSnSuccess(String str);

    void onQueryDeviceChipSuccess(int i2, int i3, String str);

    void onQueryDeviceModuleIdSuccess(int i2, String str);

    void onSyncHeartRateAndOxygenSuccess(boolean z2, List<CSBPHeartRateAndOxygenInfo> list);
}
