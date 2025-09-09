package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface MultiSportTargetListener {
    public static final int SET_MULTI_SPORT_HEART_RATE_WARNING = 1;
    public static final int SET_MULTI_SPORT_TARGET_CALORIE = 4;
    public static final int SET_MULTI_SPORT_TARGET_DISTANCE = 2;
    public static final int SET_MULTI_SPORT_TARGET_DURATION = 3;

    void onMultiSportTargetStatus(boolean z2, int i2);
}
