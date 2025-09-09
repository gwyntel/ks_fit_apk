package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class MenstrualParam {
    public static final int SWITCH_NO = 0;
    public static final int SWITCH_YES = 1;
    private String calendar;
    private int durationDay;
    private boolean isOpen;
    private int menstrualCycle;

    public MenstrualParam() {
    }

    public String getCalendar() {
        return this.calendar;
    }

    public int getDurationDay() {
        return this.durationDay;
    }

    public int getMenstrualCycle() {
        return this.menstrualCycle;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setDurationDay(int i2) {
        this.durationDay = i2;
    }

    public void setMenstrualCycle(int i2) {
        this.menstrualCycle = i2;
    }

    public void setOpen(boolean z2) {
        this.isOpen = z2;
    }

    public MenstrualParam(boolean z2, String str, int i2, int i3) {
        setOpen(z2);
        setCalendar(str);
        setDurationDay(i2);
        setMenstrualCycle(i3);
    }
}
