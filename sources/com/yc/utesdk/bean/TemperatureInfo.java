package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class TemperatureInfo implements Comparable<TemperatureInfo> {
    public static final int TYPE_NOT_SUPPORT_SAMPLING = 0;
    public static final int TYPE_SUPPORT_SAMPLING_MODE_1 = 1;
    public static final int TYPE_SUPPORT_SAMPLING_MODE_2 = 2;
    private float ambientTemperature;
    private float bodySurfaceTemperature;
    private float bodyTemperature;
    private String calendar;
    private String calendarTime;
    private int secondTime;
    private int type;

    public TemperatureInfo() {
    }

    @Override // java.lang.Comparable
    public int compareTo(TemperatureInfo temperatureInfo) {
        return temperatureInfo.getCalendarTime().compareTo(getCalendarTime());
    }

    public float getAmbientTemperature() {
        return this.ambientTemperature;
    }

    public float getBodySurfaceTemperature() {
        return this.bodySurfaceTemperature;
    }

    public float getBodyTemperature() {
        return this.bodyTemperature;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getCalendarTime() {
        return this.calendarTime;
    }

    public int getSecondTime() {
        return this.secondTime;
    }

    public int getType() {
        return this.type;
    }

    public void setAmbientTemperature(float f2) {
        this.ambientTemperature = f2;
    }

    public void setBodySurfaceTemperature(float f2) {
        this.bodySurfaceTemperature = f2;
    }

    public void setBodyTemperature(float f2) {
        this.bodyTemperature = f2;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setCalendarTime(String str) {
        this.calendarTime = str;
    }

    public void setSecondTime(int i2) {
        this.secondTime = i2;
    }

    public void setType(int i2) {
        this.type = i2;
    }

    public TemperatureInfo(int i2, String str, String str2, int i3, float f2) {
        this.type = i2;
        this.calendar = str;
        this.calendarTime = str2;
        this.secondTime = i3;
        this.bodyTemperature = f2;
    }

    public TemperatureInfo(int i2, String str, String str2, int i3, float f2, float f3, float f4) {
        this.type = i2;
        this.calendar = str;
        this.calendarTime = str2;
        this.secondTime = i3;
        this.bodySurfaceTemperature = f2;
        this.bodyTemperature = f3;
        this.ambientTemperature = f4;
    }
}
