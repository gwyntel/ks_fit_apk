package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class SleepInfo {
    public static final int SLEEP_STATUS_AWAKE = 3;
    public static final int SLEEP_STATUS_DEEP = 1;
    public static final int SLEEP_STATUS_LIGHT = 2;
    public static final int SLEEP_STATUS_REM = 4;
    private String calendar;
    private int endSleepTime;
    private List<SleepStateInfo> sleepStateInfoList;
    private int startSleepTime;
    private int totalAwakeCount;
    private int totalAwakeSleepTime;
    private int totalDeepSleepTime;
    private int totalLightSleepTime;
    private int totalRemSleepTime;
    private int totalSleepTime;

    public SleepInfo() {
    }

    public String getCalendar() {
        return this.calendar;
    }

    public int getEndSleepTime() {
        return this.endSleepTime;
    }

    public List<SleepStateInfo> getSleepStateInfoList() {
        return this.sleepStateInfoList;
    }

    public int getStartSleepTime() {
        return this.startSleepTime;
    }

    public int getTotalAwakeCount() {
        return this.totalAwakeCount;
    }

    public int getTotalAwakeSleepTime() {
        return this.totalAwakeSleepTime;
    }

    public int getTotalDeepSleepTime() {
        return this.totalDeepSleepTime;
    }

    public int getTotalLightSleepTime() {
        return this.totalLightSleepTime;
    }

    public int getTotalRemSleepTime() {
        return this.totalRemSleepTime;
    }

    public int getTotalSleepTime() {
        return this.totalSleepTime;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setEndSleepTime(int i2) {
        this.endSleepTime = i2;
    }

    public void setSleepStateInfoList(List<SleepStateInfo> list) {
        this.sleepStateInfoList = list;
    }

    public void setStartSleepTime(int i2) {
        this.startSleepTime = i2;
    }

    public void setTotalAwakeCount(int i2) {
        this.totalAwakeCount = i2;
    }

    public void setTotalAwakeSleepTime(int i2) {
        this.totalAwakeSleepTime = i2;
    }

    public void setTotalDeepSleepTime(int i2) {
        this.totalDeepSleepTime = i2;
    }

    public void setTotalLightSleepTime(int i2) {
        this.totalLightSleepTime = i2;
    }

    public void setTotalRemSleepTime(int i2) {
        this.totalRemSleepTime = i2;
    }

    public void setTotalSleepTime(int i2) {
        this.totalSleepTime = i2;
    }

    public SleepInfo(String str, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, List<SleepStateInfo> list) {
        this.calendar = str;
        this.startSleepTime = i2;
        this.endSleepTime = i3;
        this.totalSleepTime = i4;
        this.totalDeepSleepTime = i5;
        this.totalLightSleepTime = i6;
        this.totalAwakeSleepTime = i7;
        this.totalAwakeCount = i8;
        this.totalRemSleepTime = i9;
        this.sleepStateInfoList = list;
    }
}
