package com.yc.utesdk.listener;

import com.yc.utesdk.bean.CyweeSleepTimeInfo;
import com.yc.utesdk.bean.SleepInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface SleepChangeListener {
    void onCyweeSleepSyncSuccess(List<CyweeSleepTimeInfo> list);

    void onSleepSyncFail();

    void onSleepSyncSuccess(List<SleepInfo> list);

    void onSleepSyncing();
}
