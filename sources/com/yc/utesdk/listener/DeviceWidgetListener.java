package com.yc.utesdk.listener;

import com.yc.utesdk.bean.DeviceWidgetInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface DeviceWidgetListener {
    public static final int QUERY_DEVICE_WIDGET = 1;
    public static final int SET_DEVICE_WIDGET = 2;

    void onDeviceWidgetStatus(boolean z2, int i2);

    void onQueryDeviceWidgetSuccess(List<DeviceWidgetInfo> list, List<DeviceWidgetInfo> list2, List<DeviceWidgetInfo> list3);
}
