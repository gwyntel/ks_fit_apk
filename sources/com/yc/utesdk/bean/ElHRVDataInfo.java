package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class ElHRVDataInfo {
    private int HRVValue;
    private String calendar;
    private String calendarTime;
    private int time;

    public ElHRVDataInfo(String str, String str2, int i2, int i3) {
        this.calendar = str;
        this.calendarTime = str2;
        this.time = i2;
        this.HRVValue = i3;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getCalendarTime() {
        return this.calendarTime;
    }

    public int getHRVValue() {
        return this.HRVValue;
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

    public void setHRVValue(int i2) {
        this.HRVValue = i2;
    }

    public void setTime(int i2) {
        this.time = i2;
    }
}
