package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class BloodSugarSamplingInfo {
    private float ambientTemperature;
    private String calendar;
    private String calendarTime;
    private int calorie;
    private List<Integer> heartRateList;
    private float skinTemperature;
    private int sleepState;
    private int sportState;
    private List<Integer> sportStateList;
    private int step;
    private int timeMinute;

    public BloodSugarSamplingInfo() {
    }

    public float getAmbientTemperature() {
        return this.ambientTemperature;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getCalendarTime() {
        return this.calendarTime;
    }

    public int getCalorie() {
        return this.calorie;
    }

    public List<Integer> getHeartRateList() {
        return this.heartRateList;
    }

    public float getSkinTemperature() {
        return this.skinTemperature;
    }

    public int getSleepState() {
        return this.sleepState;
    }

    public int getSportState() {
        return this.sportState;
    }

    public List<Integer> getSportStateList() {
        return this.sportStateList;
    }

    public int getStep() {
        return this.step;
    }

    public int getTimeMinute() {
        return this.timeMinute;
    }

    public void setAmbientTemperature(float f2) {
        this.ambientTemperature = f2;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setCalendarTime(String str) {
        this.calendarTime = str;
    }

    public void setCalorie(int i2) {
        this.calorie = i2;
    }

    public void setHeartRateList(List<Integer> list) {
        this.heartRateList = list;
    }

    public void setSkinTemperature(float f2) {
        this.skinTemperature = f2;
    }

    public void setSleepState(int i2) {
        this.sleepState = i2;
    }

    public void setSportState(int i2) {
        this.sportState = i2;
    }

    public void setSportStateList(List<Integer> list) {
        this.sportStateList = list;
    }

    public void setStep(int i2) {
        this.step = i2;
    }

    public void setTimeMinute(int i2) {
        this.timeMinute = i2;
    }

    public BloodSugarSamplingInfo(String str, String str2, int i2, int i3, int i4, int i5, int i6, float f2, float f3, List<Integer> list, List<Integer> list2) {
        this.calendar = str;
        this.calendarTime = str2;
        this.timeMinute = i2;
        this.step = i3;
        this.calorie = i4;
        this.sportState = i5;
        this.sleepState = i6;
        this.skinTemperature = f2;
        this.ambientTemperature = f3;
        this.heartRateList = list;
        this.sportStateList = list2;
    }
}
