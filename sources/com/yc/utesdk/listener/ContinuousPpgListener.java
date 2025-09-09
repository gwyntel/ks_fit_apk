package com.yc.utesdk.listener;

import com.yc.utesdk.bean.ElbpContinuousPpgDataInfo;

/* loaded from: classes4.dex */
public interface ContinuousPpgListener {
    public static final int APP_INTERRUPT_CONTINUOUS_PPG_SAMPLING = 4;
    public static final int APP_START_CONTINUOUS_PPG_SAMPLING = 1;
    public static final int DEVICE_INTERRUPT_CONTINUOUS_PPG_SAMPLING = 5;
    public static final int INTERRUPT_CONTINUOUS_PPG_HAND_OF = 6;
    public static final int NOTIFY_APP_CONTINUOUS_PPG_SAMPLING = 2;
    public static final int NOTIFY_DEVICE_CONTINUOUS_PPG_SAMPLING = 3;

    void onContinuousPpgStatus(boolean z2, int i2);

    void onElbpContinuousPpgRealTime(ElbpContinuousPpgDataInfo elbpContinuousPpgDataInfo);
}
