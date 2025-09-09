package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class HeartRateRestInfo {
    private String calendar;
    private String calendarTime;
    private int heartRateRest;

    public HeartRateRestInfo(String str, int i2) {
        this.calendar = str;
        this.heartRateRest = i2;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getCalendarTime() {
        return this.calendarTime;
    }

    public int getHeartRateRest() {
        return this.heartRateRest;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setCalendarTime(String str) {
        this.calendarTime = str;
    }

    public void setHeartRateRest(int i2) {
        this.heartRateRest = i2;
    }

    public HeartRateRestInfo(String str, String str2, int i2) {
        this.calendar = str;
        this.calendarTime = str2;
        this.heartRateRest = i2;
    }
}
