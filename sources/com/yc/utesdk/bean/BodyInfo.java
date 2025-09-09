package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class BodyInfo implements Comparable<BodyInfo> {
    private int bodyAge;
    private float bodyBMI;
    private int bodyBMR;
    private float bodyBoneSalt;
    private float bodyFat;
    private int bodyGender;
    private int bodyHeight;
    private float bodyMuscle;
    private float bodyProtein;
    private int bodyScore;
    private float bodyWater;
    private float bodyWeight;
    private String calendar;
    private String calendarTime;
    private int startTime;
    private String yearMonth;

    public BodyInfo() {
    }

    @Override // java.lang.Comparable
    public int compareTo(BodyInfo bodyInfo) {
        return bodyInfo.getYearMonth().compareTo(getYearMonth());
    }

    public int getBodyAge() {
        return this.bodyAge;
    }

    public float getBodyBMI() {
        return this.bodyBMI;
    }

    public int getBodyBMR() {
        return this.bodyBMR;
    }

    public float getBodyBoneSalt() {
        return this.bodyBoneSalt;
    }

    public float getBodyFat() {
        return this.bodyFat;
    }

    public int getBodyGender() {
        return this.bodyGender;
    }

    public int getBodyHeight() {
        return this.bodyHeight;
    }

    public float getBodyMuscle() {
        return this.bodyMuscle;
    }

    public float getBodyProtein() {
        return this.bodyProtein;
    }

    public int getBodyScore() {
        return this.bodyScore;
    }

    public float getBodyWater() {
        return this.bodyWater;
    }

    public float getBodyWeight() {
        return this.bodyWeight;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getCalendarTime() {
        return this.calendarTime;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public String getYearMonth() {
        return this.yearMonth;
    }

    public void setBodyAge(int i2) {
        this.bodyAge = i2;
    }

    public void setBodyBMI(float f2) {
        this.bodyBMI = f2;
    }

    public void setBodyBMR(int i2) {
        this.bodyBMR = i2;
    }

    public void setBodyBoneSalt(float f2) {
        this.bodyBoneSalt = f2;
    }

    public void setBodyFat(float f2) {
        this.bodyFat = f2;
    }

    public void setBodyGender(int i2) {
        this.bodyGender = i2;
    }

    public void setBodyHeight(int i2) {
        this.bodyHeight = i2;
    }

    public void setBodyMuscle(float f2) {
        this.bodyMuscle = f2;
    }

    public void setBodyProtein(float f2) {
        this.bodyProtein = f2;
    }

    public void setBodyScore(int i2) {
        this.bodyScore = i2;
    }

    public void setBodyWater(float f2) {
        this.bodyWater = f2;
    }

    public void setBodyWeight(float f2) {
        this.bodyWeight = f2;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setCalendarTime(String str) {
        this.calendarTime = str;
    }

    public void setStartTime(int i2) {
        this.startTime = i2;
    }

    public void setYearMonth(String str) {
        this.yearMonth = str;
    }

    public BodyInfo(String str, String str2, String str3, int i2, int i3, int i4, int i5, float f2, float f3, float f4, float f5, int i6, float f6, float f7, float f8, int i7) {
        setCalendar(str);
        setCalendarTime(str2);
        setYearMonth(str3);
        setStartTime(i2);
        setBodyFat(f3);
        setBodyWater(f4);
        setBodyProtein(f5);
        setBodyBMR(i6);
        setBodyBoneSalt(f6);
        setBodyMuscle(f7);
        setBodyWeight(f2);
        setBodyHeight(i5);
        setBodyAge(i4);
        setBodyGender(i3);
        setBodyBMI(f8);
        setBodyScore(i7);
    }
}
