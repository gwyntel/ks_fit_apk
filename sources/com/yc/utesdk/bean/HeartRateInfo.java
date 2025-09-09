package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class HeartRateInfo {
    private String calendar;
    private String calendarTime;
    private int heartRate;
    private int time;

    public HeartRateInfo(String str, String str2, int i2, int i3) {
        this.calendar = str;
        this.calendarTime = str2;
        this.time = i2;
        this.heartRate = i3;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getCalendarTime() {
        return this.calendarTime;
    }

    public int getHeartRate() {
        return this.heartRate;
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

    public void setHeartRate(int i2) {
        this.heartRate = i2;
    }

    public void setTime(int i2) {
        this.time = i2;
    }
}
