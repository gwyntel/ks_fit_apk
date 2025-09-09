package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class AlarmClockInfo {
    public static final int DEVICE_ALARM_CLOCK_1 = 1;
    public static final int DEVICE_ALARM_CLOCK_10 = 10;
    public static final int DEVICE_ALARM_CLOCK_2 = 2;
    public static final int DEVICE_ALARM_CLOCK_3 = 3;
    public static final int DEVICE_ALARM_CLOCK_4 = 4;
    public static final int DEVICE_ALARM_CLOCK_5 = 5;
    public static final int DEVICE_ALARM_CLOCK_6 = 6;
    public static final int DEVICE_ALARM_CLOCK_7 = 7;
    public static final int DEVICE_ALARM_CLOCK_8 = 8;
    public static final int DEVICE_ALARM_CLOCK_9 = 9;
    public static final int EVERYDAY = 127;
    public static final int FRIDAY = 32;
    public static final int MONDAY = 2;
    public static final int SATURDAY = 64;
    public static final int SUNDAY = 1;
    public static final int THURSDAY = 16;
    public static final int TUESDAY = 4;
    public static final int WEDNESDAY = 8;
    public static final int WORKING_DAY = 62;
    public static final int switchStatusNo = 0;
    public static final int switchStatusYes = 1;
    private int alarmCycle;
    private int alarmHour;
    private int alarmId;
    private int alarmMinute;
    private int alarmShakeCount;
    private int alarmStatus;

    public AlarmClockInfo() {
        this.alarmId = 1;
        this.alarmStatus = 1;
        this.alarmHour = 12;
        this.alarmMinute = 0;
        this.alarmShakeCount = 10;
        this.alarmCycle = 62;
    }

    public int getAlarmCycle() {
        return this.alarmCycle;
    }

    public int getAlarmHour() {
        return this.alarmHour;
    }

    public int getAlarmId() {
        return this.alarmId;
    }

    public int getAlarmMinute() {
        return this.alarmMinute;
    }

    public int getAlarmShakeCount() {
        return this.alarmShakeCount;
    }

    public int getAlarmStatus() {
        return this.alarmStatus;
    }

    public void setAlarmCycle(int i2) {
        this.alarmCycle = i2;
    }

    public void setAlarmHour(int i2) {
        this.alarmHour = i2;
    }

    public void setAlarmId(int i2) {
        this.alarmId = i2;
    }

    public void setAlarmMinute(int i2) {
        this.alarmMinute = i2;
    }

    public void setAlarmShakeCount(int i2) {
        this.alarmShakeCount = i2;
    }

    public void setAlarmStatus(int i2) {
        this.alarmStatus = i2;
    }

    public AlarmClockInfo(int i2, int i3, int i4, int i5, int i6, int i7) {
        this.alarmId = i2;
        this.alarmStatus = i3;
        this.alarmHour = i4;
        this.alarmMinute = i5;
        this.alarmShakeCount = i6;
        this.alarmCycle = i7;
    }
}
