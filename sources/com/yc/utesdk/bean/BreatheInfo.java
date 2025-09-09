package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class BreatheInfo implements Comparable<BreatheInfo> {
    private int breatheValue;
    private int breatheValueStatus = -1;
    private String calendar;
    private String startDate;
    private int time;

    public BreatheInfo() {
    }

    @Override // java.lang.Comparable
    public int compareTo(BreatheInfo breatheInfo) {
        return breatheInfo.getStartDate().compareTo(getStartDate());
    }

    public int getBreatheValue() {
        return this.breatheValue;
    }

    public int getBreatheValueStatus() {
        return this.breatheValueStatus;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public int getTime() {
        return this.time;
    }

    public void setBreatheValue(int i2) {
        this.breatheValue = i2;
    }

    public void setBreatheValueStatus(int i2) {
        this.breatheValueStatus = i2;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setStartDate(String str) {
        this.startDate = str;
    }

    public void setTime(int i2) {
        this.time = i2;
    }

    public BreatheInfo(String str, String str2, int i2, int i3) {
        this.calendar = str;
        this.startDate = str2;
        this.time = i2;
        this.breatheValue = i3;
    }
}
