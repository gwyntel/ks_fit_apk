package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class StepRunHourInfo implements Comparable<StepRunHourInfo> {
    private String calendar;
    private int endRunTime;
    private float runCalorie;
    private float runDistance;
    private int runDurationTime;
    private int runSteps;
    private int startRunTime;
    private int time;

    public StepRunHourInfo() {
        this.calendar = "";
    }

    @Override // java.lang.Comparable
    public int compareTo(StepRunHourInfo stepRunHourInfo) {
        return stepRunHourInfo.getCalendar().compareTo(getCalendar());
    }

    public String getCalendar() {
        return this.calendar;
    }

    public int getEndRunTime() {
        return this.endRunTime;
    }

    public float getRunCalorie() {
        return this.runCalorie;
    }

    public float getRunDistance() {
        return this.runDistance;
    }

    public int getRunDurationTime() {
        return this.runDurationTime;
    }

    public int getRunSteps() {
        return this.runSteps;
    }

    public int getStartRunTime() {
        return this.startRunTime;
    }

    public int getTime() {
        return this.time;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setEndRunTime(int i2) {
        this.endRunTime = i2;
    }

    public void setRunCalorie(float f2) {
        this.runCalorie = f2;
    }

    public void setRunDistance(float f2) {
        this.runDistance = f2;
    }

    public void setRunDurationTime(int i2) {
        this.runDurationTime = i2;
    }

    public void setRunSteps(int i2) {
        this.runSteps = i2;
    }

    public void setStartRunTime(int i2) {
        this.startRunTime = i2;
    }

    public void setTime(int i2) {
        this.time = i2;
    }

    public StepRunHourInfo(String str, int i2, int i3, int i4, int i5, int i6, float f2, float f3) {
        this.calendar = str;
        this.time = i2;
        this.startRunTime = i3;
        this.endRunTime = i4;
        this.runDurationTime = i5;
        this.runSteps = i6;
        this.runDistance = f2;
        this.runCalorie = f3;
    }
}
