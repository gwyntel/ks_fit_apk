package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class HeartRateBestValueInfo {
    private String calendar;
    private String calendarTime;
    private int highestRate;
    private int lowestRate;
    private int verageRate;

    public HeartRateBestValueInfo(String str, String str2, int i2, int i3, int i4) {
        this.calendar = str;
        this.calendarTime = str2;
        this.highestRate = i2;
        this.lowestRate = i3;
        this.verageRate = i4;
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

    public int getVerageRate() {
        return this.verageRate;
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

    public void setVerageRate(int i2) {
        this.verageRate = i2;
    }
}
