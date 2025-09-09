package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class BloodPressureInfo {
    private String calendar;
    private String calendarTime;
    private int diastolicPressure;
    private int systolicPressure;
    private int time;

    public BloodPressureInfo(String str, String str2, int i2, int i3, int i4) {
        this.calendar = str;
        this.calendarTime = str2;
        this.time = i2;
        this.systolicPressure = i3;
        this.diastolicPressure = i4;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getCalendarTime() {
        return this.calendarTime;
    }

    public int getDiastolicPressure() {
        return this.diastolicPressure;
    }

    public int getSystolicPressure() {
        return this.systolicPressure;
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

    public void setDiastolicPressure(int i2) {
        this.diastolicPressure = i2;
    }

    public void setSystolicPressure(int i2) {
        this.systolicPressure = i2;
    }

    public void setTime(int i2) {
        this.time = i2;
    }
}
