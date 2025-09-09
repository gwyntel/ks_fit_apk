package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class StandingTimeInfo implements Comparable<StandingTimeInfo> {
    private String calendar;
    private int durationTime;
    private String endDate;
    private String startDate;

    public StandingTimeInfo() {
    }

    @Override // java.lang.Comparable
    public int compareTo(StandingTimeInfo standingTimeInfo) {
        return standingTimeInfo.getStartDate().compareTo(getStartDate());
    }

    public String getCalendar() {
        return this.calendar;
    }

    public int getDurationTime() {
        return this.durationTime;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setDurationTime(int i2) {
        this.durationTime = i2;
    }

    public void setEndDate(String str) {
        this.endDate = str;
    }

    public void setStartDate(String str) {
        this.startDate = str;
    }

    public StandingTimeInfo(String str, String str2, String str3, int i2) {
        this.calendar = str;
        this.startDate = str2;
        this.endDate = str3;
        this.durationTime = i2;
    }
}
