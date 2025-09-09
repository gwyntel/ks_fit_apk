package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class WatchHeartRateReminderBean {
    private boolean enableDailyHigh;
    private boolean enableDailyLow;
    private boolean enableSportHigh;
    private int thresholdDailyHigh;
    private int thresholdDailyLow;
    private int thresholdSportHigh;

    public WatchHeartRateReminderBean() {
    }

    public int getThresholdDailyHigh() {
        return this.thresholdDailyHigh;
    }

    public int getThresholdDailyLow() {
        return this.thresholdDailyLow;
    }

    public int getThresholdSportHigh() {
        return this.thresholdSportHigh;
    }

    public boolean isEnableDailyHigh() {
        return this.enableDailyHigh;
    }

    public boolean isEnableDailyLow() {
        return this.enableDailyLow;
    }

    public boolean isEnableSportHigh() {
        return this.enableSportHigh;
    }

    public void setEnableDailyHigh(boolean z2) {
        this.enableDailyHigh = z2;
    }

    public void setEnableDailyLow(boolean z2) {
        this.enableDailyLow = z2;
    }

    public void setEnableSportHigh(boolean z2) {
        this.enableSportHigh = z2;
    }

    public void setThresholdDailyHigh(int i2) {
        this.thresholdDailyHigh = i2;
    }

    public void setThresholdDailyLow(int i2) {
        this.thresholdDailyLow = i2;
    }

    public void setThresholdSportHigh(int i2) {
        this.thresholdSportHigh = i2;
    }

    public WatchHeartRateReminderBean(boolean z2, int i2, boolean z3, int i3, boolean z4, int i4) {
        this.enableDailyLow = z2;
        this.thresholdDailyLow = i2;
        this.enableDailyHigh = z3;
        this.thresholdDailyHigh = i3;
        this.enableSportHigh = z4;
        this.thresholdSportHigh = i4;
    }
}
