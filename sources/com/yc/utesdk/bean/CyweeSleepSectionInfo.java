package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class CyweeSleepSectionInfo {
    private int awakeCount;
    private int awakeTime;
    private String calendar;
    private int deepTime;
    private int endTime;
    private int lightTime;
    private int remTime;
    private List<SleepStateInfo> sleepInfoList;
    private int sleepTotalTime;
    private int startTime;

    public int getAwakeCount() {
        return this.awakeCount;
    }

    public int getAwakeTime() {
        return this.awakeTime;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public int getDeepTime() {
        return this.deepTime;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public int getLightTime() {
        return this.lightTime;
    }

    public int getRemTime() {
        return this.remTime;
    }

    public List<SleepStateInfo> getSleepInfoList() {
        return this.sleepInfoList;
    }

    public int getSleepTotalTime() {
        return this.sleepTotalTime;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setAwakeCount(int i2) {
        this.awakeCount = i2;
    }

    public void setAwakeTime(int i2) {
        this.awakeTime = i2;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setDeepTime(int i2) {
        this.deepTime = i2;
    }

    public void setEndTime(int i2) {
        this.endTime = i2;
    }

    public void setLightTime(int i2) {
        this.lightTime = i2;
    }

    public void setRemTime(int i2) {
        this.remTime = i2;
    }

    public void setSleepInfoList(List<SleepStateInfo> list) {
        this.sleepInfoList = list;
    }

    public void setSleepTotalTime(int i2) {
        this.sleepTotalTime = i2;
    }

    public void setStartTime(int i2) {
        this.startTime = i2;
    }
}
