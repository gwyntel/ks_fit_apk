package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class StepOneHourInfo {
    private String calendar;
    public int step;
    public int time;

    public StepOneHourInfo() {
        this.calendar = "";
    }

    public String getCalendar() {
        return this.calendar;
    }

    public int getStep() {
        return this.step;
    }

    public int getTime() {
        return this.time;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setStep(int i2) {
        this.step = i2;
    }

    public void setTime(int i2) {
        this.time = i2;
    }

    public StepOneHourInfo(String str, int i2, int i3) {
        this.calendar = str;
        this.time = i2;
        this.step = i3;
    }
}
