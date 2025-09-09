package com.yc.utesdk.listener;

import com.yc.utesdk.bean.ActivityTimeInfo;
import com.yc.utesdk.bean.StandingTimeInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface TodayTargetListener {
    public static final int SET_TODAY_TARGET_ACTIVITY_TIME = 6;
    public static final int SET_TODAY_TARGET_CALORIE = 3;
    public static final int SET_TODAY_TARGET_DISTANCE = 5;
    public static final int SET_TODAY_TARGET_EXERCISE_TIME = 2;
    public static final int SET_TODAY_TARGET_STANDING_TIME = 1;
    public static final int SET_TODAY_TARGET_STEP = 4;

    void onActivityTimeSyncFail();

    void onActivityTimeSyncSuccess(List<ActivityTimeInfo> list);

    void onActivityTimeSyncing();

    void onQueryTodayTarget(boolean z2, int i2, int i3);

    void onStandingTimeSyncFail();

    void onStandingTimeSyncSuccess(List<StandingTimeInfo> list);

    void onStandingTimeSyncing();

    void onTodayTargetStatus(boolean z2, int i2);
}
