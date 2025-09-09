package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class StepWalkHourInfo implements Comparable<StepWalkHourInfo> {
    private String calendar;
    private int endWalkTime;
    private int startWalkTime;
    private int time;
    private float walkCalorie;
    private float walkDistance;
    private int walkDurationTime;
    private int walkSteps;

    public StepWalkHourInfo() {
        this.calendar = "";
    }

    @Override // java.lang.Comparable
    public int compareTo(StepWalkHourInfo stepWalkHourInfo) {
        return stepWalkHourInfo.getCalendar().compareTo(getCalendar());
    }

    public String getCalendar() {
        return this.calendar;
    }

    public int getEndWalkTime() {
        return this.endWalkTime;
    }

    public int getStartWalkTime() {
        return this.startWalkTime;
    }

    public int getTime() {
        return this.time;
    }

    public float getWalkCalorie() {
        return this.walkCalorie;
    }

    public float getWalkDistance() {
        return this.walkDistance;
    }

    public int getWalkDurationTime() {
        return this.walkDurationTime;
    }

    public int getWalkSteps() {
        return this.walkSteps;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setEndWalkTime(int i2) {
        this.endWalkTime = i2;
    }

    public void setStartWalkTime(int i2) {
        this.startWalkTime = i2;
    }

    public void setTime(int i2) {
        this.time = i2;
    }

    public void setWalkCalorie(float f2) {
        this.walkCalorie = f2;
    }

    public void setWalkDistance(float f2) {
        this.walkDistance = f2;
    }

    public void setWalkDurationTime(int i2) {
        this.walkDurationTime = i2;
    }

    public void setWalkSteps(int i2) {
        this.walkSteps = i2;
    }

    public StepWalkHourInfo(String str, int i2, int i3, int i4, int i5, int i6, float f2, float f3) {
        this.calendar = str;
        this.time = i2;
        this.startWalkTime = i3;
        this.endWalkTime = i4;
        this.walkDurationTime = i5;
        this.walkSteps = i6;
        this.walkDistance = f2;
        this.walkCalorie = f3;
    }
}
