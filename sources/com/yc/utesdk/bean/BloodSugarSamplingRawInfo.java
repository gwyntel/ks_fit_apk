package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class BloodSugarSamplingRawInfo {
    private float ambientTemperature;
    private String calendar;
    private String calendarTime;
    private int calorie;
    private List<Integer> heartRateList;
    private byte[] rawData;
    private float skinTemperature;
    private int sleepState;
    private int sportState;
    private List<Integer> sportStateList;
    private int step;
    private int timeMinute;

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

    public byte[] getRawData() {
        return this.rawData;
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

    public void setRawData(byte[] bArr) {
        this.rawData = bArr;
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
}
