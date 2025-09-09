package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class UnitHourFormatInfo {
    public static final int HOUR_FORMAT_12 = 0;
    public static final int HOUR_FORMAT_24 = 1;
    public static final int NO_CHANGE = -1;
    public static final int TEMPERATURE_CELSIUS = 1;
    public static final int TEMPERATURE_FAHRENHEIT = 0;
    public static final int UNIT_IMPERIAL = 0;
    public static final int UNIT_METRIC = 1;
    public static final int switchStatusNo = 0;
    public static final int switchStatusYes = 1;
    private int deviceHourFormat;
    private int deviceUnit;

    public UnitHourFormatInfo() {
        this.deviceUnit = -1;
        this.deviceHourFormat = -1;
    }

    public int getDeviceHourFormat() {
        return this.deviceHourFormat;
    }

    public int getDeviceUnit() {
        return this.deviceUnit;
    }

    public void setDeviceHourFormat(int i2) {
        this.deviceHourFormat = i2;
    }

    public void setDeviceUnit(int i2) {
        this.deviceUnit = i2;
    }

    public UnitHourFormatInfo(int i2, int i3) {
        this.deviceUnit = i2;
        this.deviceHourFormat = i3;
    }
}
