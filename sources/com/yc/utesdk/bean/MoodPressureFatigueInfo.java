package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class MoodPressureFatigueInfo implements Comparable<MoodPressureFatigueInfo> {
    public static final int INVALID_VALUE = -1;
    public static final int MOOD_STATUS_CALM = 1;
    public static final int MOOD_STATUS_NEGATIVE = 0;
    public static final int MOOD_STATUS_POSITIVE = 2;
    private String calendar;
    private String fatigueDes;
    private int fatigueValue;
    private String moodDes;
    private int moodValue;
    private String pressureDes;
    private int pressureValue;
    private String startDate;
    private int testResultStatus;
    private int time;

    public MoodPressureFatigueInfo() {
        this.moodValue = -1;
        this.pressureValue = -1;
        this.fatigueValue = -1;
        this.moodDes = "";
        this.pressureDes = "";
        this.fatigueDes = "";
    }

    @Override // java.lang.Comparable
    public int compareTo(MoodPressureFatigueInfo moodPressureFatigueInfo) {
        return moodPressureFatigueInfo.getStartDate().compareTo(getStartDate());
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getFatigueDes() {
        return this.fatigueDes;
    }

    public int getFatigueValue() {
        return this.fatigueValue;
    }

    public String getMoodDes() {
        return this.moodDes;
    }

    public int getMoodValue() {
        return this.moodValue;
    }

    public String getPressureDes() {
        return this.pressureDes;
    }

    public int getPressureValue() {
        return this.pressureValue;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public int getTestResultStatus() {
        return this.testResultStatus;
    }

    public int getTime() {
        return this.time;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setFatigueDes(String str) {
        this.fatigueDes = str;
    }

    public void setFatigueValue(int i2) {
        this.fatigueValue = i2;
    }

    public void setMoodDes(String str) {
        this.moodDes = str;
    }

    public void setMoodValue(int i2) {
        this.moodValue = i2;
    }

    public void setPressureDes(String str) {
        this.pressureDes = str;
    }

    public void setPressureValue(int i2) {
        this.pressureValue = i2;
    }

    public void setStartDate(String str) {
        this.startDate = str;
    }

    public void setTestResultStatus(int i2) {
        this.testResultStatus = i2;
    }

    public void setTime(int i2) {
        this.time = i2;
    }

    public MoodPressureFatigueInfo(String str, String str2, int i2, int i3, int i4, int i5) {
        this.moodDes = "";
        this.pressureDes = "";
        this.fatigueDes = "";
        this.calendar = str;
        this.startDate = str2;
        this.time = i2;
        this.moodValue = i3;
        this.pressureValue = i4;
        this.fatigueValue = i5;
    }
}
