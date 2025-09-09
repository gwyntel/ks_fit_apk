package com.aliyun.alink.linksdk.tmp.timing;

/* loaded from: classes2.dex */
public enum TimerEnableType {
    TIMER_ENABLE_KNOWN(-1),
    TIMER_ENABLE_NONE(0),
    TIMER_ENABLE(1),
    TIMER_IN_FLIGHT(2);

    private int typeValue;

    TimerEnableType(int i2) {
        this.typeValue = i2;
    }

    public static TimerEnableType fromTypeValue(int i2) {
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? TIMER_ENABLE_KNOWN : TIMER_IN_FLIGHT : TIMER_ENABLE : TIMER_ENABLE_NONE;
    }

    public int getTypeValue() {
        return this.typeValue;
    }
}
