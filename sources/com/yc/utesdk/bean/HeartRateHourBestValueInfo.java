package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class HeartRateHourBestValueInfo {
    private String calendar;
    private String calendarTime;
    private int highestRate;
    private int lowestRate;

    public HeartRateHourBestValueInfo(String str, String str2, int i2, int i3) {
        this.calendar = str;
        this.calendarTime = str2;
        this.highestRate = i2;
        this.lowestRate = i3;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getCalendarTime() {
        return this.calendarTime;
    }

    public int getHighestRate() {
        return this.highestRate;
    }

    public int getLowestRate() {
        return this.lowestRate;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setCalendarTime(String str) {
        this.calendarTime = str;
    }

    public void setHighestRate(int i2) {
        this.highestRate = i2;
    }

    public void setLowestRate(int i2) {
        this.lowestRate = i2;
    }
}
