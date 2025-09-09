package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class CyweeSleepTimeInfo {
    private List<SleepStateInfo> allCyweeSleepInfos;
    private String calendar;
    private List<CyweeSleepSectionInfo> cyweeSleepSectionInfos;
    private int endSleepTime;
    private int startSleepTime;
    private int totalAwakeCount;
    private int totalAwakeSleepTime;
    private int totalDeepSleepTime;
    private int totalLightSleepTime;
    private int totalRemSleepTime;
    private int totalSleepTime;

    public List<SleepStateInfo> getAllCyweeSleepInfos() {
        return this.allCyweeSleepInfos;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public List<CyweeSleepSectionInfo> getCyweeSleepSectionInfos() {
        return this.cyweeSleepSectionInfos;
    }

    public int getEndSleepTime() {
        return this.endSleepTime;
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

    public void setAllCyweeSleepInfos(List<SleepStateInfo> list) {
        this.allCyweeSleepInfos = list;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setCyweeSleepSectionInfos(List<CyweeSleepSectionInfo> list) {
        this.cyweeSleepSectionInfos = list;
    }

    public void setEndSleepTime(int i2) {
        this.endSleepTime = i2;
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
}
