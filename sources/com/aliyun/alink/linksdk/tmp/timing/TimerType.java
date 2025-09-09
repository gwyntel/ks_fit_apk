package com.aliyun.alink.linksdk.tmp.timing;

/* loaded from: classes2.dex */
public enum TimerType {
    TIMER_NONE(0),
    TIMER_COUNTDOWN(1),
    TIMER_NORMAL(2),
    TIMER_CIRCULATION(3);

    private int typeValue;

    TimerType(int i2) {
        this.typeValue = i2;
    }

    public static TimerType fromValue(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? TIMER_NONE : TIMER_CIRCULATION : TIMER_NORMAL : TIMER_COUNTDOWN;
    }

    public int getTypeValue() {
        return this.typeValue;
    }
}
