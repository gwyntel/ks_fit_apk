package com.yc.utesdk.listener;

import com.yc.utesdk.bean.PrayerInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface DevicePrayerListener {
    public static final int ON_NOTIFY_DEVICE_PRAYER = 3;
    public static final int QUERY_DEVICE_PRAYER = 2;
    public static final int QUERY_PRAYER_SWITCH_STATUS_FAIL = 7;
    public static final int QUERY_PRAYER_SWITCH_STATUS_NO = 5;
    public static final int QUERY_PRAYER_SWITCH_STATUS_YES = 6;
    public static final int SET_DEVICE_PRAYER = 1;
    public static final int SHOW_DEVICE_PRAYER = 4;

    void onDevicePrayerInfo(List<PrayerInfo> list, int i2);

    void onDevicePrayerStatus(boolean z2, int i2);
}
