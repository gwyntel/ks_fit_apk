package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class ElbsDataInfo {
    private float bloodSugarValue;
    private String calendar;
    private String calendarTime;
    private int time;

    public ElbsDataInfo(String str, String str2, int i2, float f2) {
        this.calendar = str;
        this.calendarTime = str2;
        this.time = i2;
        this.bloodSugarValue = f2;
    }

    public float getBloodSugarValue() {
        return this.bloodSugarValue;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getCalendarTime() {
        return this.calendarTime;
    }

    public int getTime() {
        return this.time;
    }

    public void setBloodSugarValue(float f2) {
        this.bloodSugarValue = f2;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setCalendarTime(String str) {
        this.calendarTime = str;
    }

    public void setTime(int i2) {
        this.time = i2;
    }
}
