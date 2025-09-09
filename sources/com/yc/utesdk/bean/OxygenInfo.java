package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class OxygenInfo {
    private String calendar;
    private String calendarTime;
    private int oxygen;
    private int time;

    public OxygenInfo(String str, String str2, int i2, int i3) {
        this.calendar = str;
        this.calendarTime = str2;
        this.time = i2;
        this.oxygen = i3;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getCalendarTime() {
        return this.calendarTime;
    }

    public int getOxygen() {
        return this.oxygen;
    }

    public int getTime() {
        return this.time;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setCalendarTime(String str) {
        this.calendarTime = str;
    }

    public void setOxygen(int i2) {
        this.oxygen = i2;
    }

    public void setTime(int i2) {
        this.time = i2;
    }
}
