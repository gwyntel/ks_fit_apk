package com.yc.utesdk.listener;

import com.yc.utesdk.bean.StepOneDayAllInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface StepChangeListener {
    void onStepChange(StepOneDayAllInfo stepOneDayAllInfo);

    void onStepSyncFail();

    void onStepSyncSuccess(List<StepOneDayAllInfo> list);

    void onStepSyncing();
}
