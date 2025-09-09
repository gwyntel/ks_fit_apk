package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class SleepStateInfo {
    private String calendar;
    private int durationTime;
    private int endTime;
    private int sleepStatus;
    private int startTime;

    public SleepStateInfo() {
    }

    public String getCalendar() {
        return this.calendar;
    }

    public int getDurationTime() {
        return this.durationTime;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public int getSleepStatus() {
        return this.sleepStatus;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setDurationTime(int i2) {
        this.durationTime = i2;
    }

    public void setEndTime(int i2) {
        this.endTime = i2;
    }

    public void setSleepStatus(int i2) {
        this.sleepStatus = i2;
    }

    public void setStartTime(int i2) {
        this.startTime = i2;
    }

    public SleepStateInfo(String str, int i2, int i3, int i4, int i5) {
        this.calendar = str;
        this.startTime = i2;
        this.endTime = i3;
        this.durationTime = i4;
        this.sleepStatus = i5;
    }
}
