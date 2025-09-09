package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class ActivityTimeInfo implements Comparable<ActivityTimeInfo> {
    private int ballGamesActivityDuration;
    private String calendar;
    private String calendarHour;
    private int calories;
    private int casualActivityDuration;
    private int dailyActivityDuration;
    private float distance;
    private int fitnessActivityDuration;
    private int hour;
    private int iceSnowActivityDuration;
    private int otherActivityDuration;
    private int outdoorActivitysDuration;
    private int rideActivityDuration;
    private int runActivityDuration;
    private int step;
    private int swimActivityDuration;
    private int walkingActivityDuration;
    private int waterActivityDuration;
    private int yogaActivityDuration;

    public ActivityTimeInfo() {
    }

    @Override // java.lang.Comparable
    public int compareTo(ActivityTimeInfo activityTimeInfo) {
        return activityTimeInfo.getCalendarHour().compareTo(getCalendarHour());
    }

    public int getBallGamesActivityDuration() {
        return this.ballGamesActivityDuration;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getCalendarHour() {
        return this.calendarHour;
    }

    public int getCalories() {
        return this.calories;
    }

    public int getCasualActivityDuration() {
        return this.casualActivityDuration;
    }

    public int getDailyActivityDuration() {
        return this.dailyActivityDuration;
    }

    public float getDistance() {
        return this.distance;
    }

    public int getFitnessActivityDuration() {
        return this.fitnessActivityDuration;
    }

    public int getHour() {
        return this.hour;
    }

    public int getIceSnowActivityDuration() {
        return this.iceSnowActivityDuration;
    }

    public int getOtherActivityDuration() {
        return this.otherActivityDuration;
    }

    public int getOutdoorActivitysDuration() {
        return this.outdoorActivitysDuration;
    }

    public int getRideActivityDuration() {
        return this.rideActivityDuration;
    }

    public int getRunActivityDuration() {
        return this.runActivityDuration;
    }

    public int getStep() {
        return this.step;
    }

    public int getSwimActivityDuration() {
        return this.swimActivityDuration;
    }

    public int getWalkingActivityDuration() {
        return this.walkingActivityDuration;
    }

    public int getWaterActivityDuration() {
        return this.waterActivityDuration;
    }

    public int getYogaActivityDuration() {
        return this.yogaActivityDuration;
    }

    public void setBallGamesActivityDuration(int i2) {
        this.ballGamesActivityDuration = i2;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setCalendarHour(String str) {
        this.calendarHour = str;
    }

    public void setCalories(int i2) {
        this.calories = i2;
    }

    public void setCasualActivityDuration(int i2) {
        this.casualActivityDuration = i2;
    }

    public void setDailyActivityDuration(int i2) {
        this.dailyActivityDuration = i2;
    }

    public void setDistance(float f2) {
        this.distance = f2;
    }

    public void setFitnessActivityDuration(int i2) {
        this.fitnessActivityDuration = i2;
    }

    public void setHour(int i2) {
        this.hour = i2;
    }

    public void setIceSnowActivityDuration(int i2) {
        this.iceSnowActivityDuration = i2;
    }

    public void setOtherActivityDuration(int i2) {
        this.otherActivityDuration = i2;
    }

    public void setOutdoorActivitysDuration(int i2) {
        this.outdoorActivitysDuration = i2;
    }

    public void setRideActivityDuration(int i2) {
        this.rideActivityDuration = i2;
    }

    public void setRunActivityDuration(int i2) {
        this.runActivityDuration = i2;
    }

    public void setStep(int i2) {
        this.step = i2;
    }

    public void setSwimActivityDuration(int i2) {
        this.swimActivityDuration = i2;
    }

    public void setWalkingActivityDuration(int i2) {
        this.walkingActivityDuration = i2;
    }

    public void setWaterActivityDuration(int i2) {
        this.waterActivityDuration = i2;
    }

    public void setYogaActivityDuration(int i2) {
        this.yogaActivityDuration = i2;
    }

    public ActivityTimeInfo(String str, String str2, int i2, int i3, int i4, float f2, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17) {
        this.calendar = str;
        this.calendarHour = str2;
        this.hour = i2;
        this.step = i3;
        this.calories = i4;
        this.distance = f2;
        this.dailyActivityDuration = i5;
        this.runActivityDuration = i6;
        this.rideActivityDuration = i7;
        this.walkingActivityDuration = i8;
        this.swimActivityDuration = i9;
        this.outdoorActivitysDuration = i10;
        this.fitnessActivityDuration = i11;
        this.ballGamesActivityDuration = i12;
        this.yogaActivityDuration = i13;
        this.waterActivityDuration = i14;
        this.iceSnowActivityDuration = i15;
        this.casualActivityDuration = i16;
        this.otherActivityDuration = i17;
    }
}
