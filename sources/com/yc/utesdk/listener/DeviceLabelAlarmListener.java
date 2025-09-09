package com.yc.utesdk.listener;

import com.yc.utesdk.bean.LabelAlarmClockInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface DeviceLabelAlarmListener {
    public static final int QUERY_DEVICE_LABEL_ALARM_CLOCK = 2;
    public static final int SET_DEVICE_LABEL_ALARM_CLOCK = 1;

    void onDeviceLabelAlarmStatus(boolean z2, int i2);

    void onQueryDeviceLabelAlarmSuccess(List<LabelAlarmClockInfo> list);
}
