package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class LabelAlarmClockInfo {
    public static final int EVERYDAY = 127;
    public static final int FRIDAY = 32;
    public static final int LABEL_TYPE_ALARM = 0;
    public static final int LABEL_TYPE_APPOINTMENT = 3;
    public static final int LABEL_TYPE_DINNER = 4;
    public static final int LABEL_TYPE_MEETING = 2;
    public static final int LABEL_TYPE_WAKEUP = 1;
    public static final int MONDAY = 2;
    public static final int ONLY_ONCE = 0;
    public static final int SATURDAY = 64;
    public static final int SHAKE_LEVEL_1 = 1;
    public static final int SHAKE_LEVEL_2 = 2;
    public static final int SHAKE_LEVEL_3 = 3;
    public static final int SUNDAY = 1;
    public static final int THURSDAY = 16;
    public static final int TUESDAY = 4;
    public static final int WEDNESDAY = 8;
    public static final int WORKING_DAY = 62;
    public static final int switchStatusNo = 0;
    public static final int switchStatusYes = 1;
    private int alarmCycle;
    private String alarmDescription;
    private int alarmHour;
    private int alarmId;
    private int alarmLabelType;
    private int alarmMinute;
    private int alarmShakeCount;
    private int alarmStatus;
    private long alarmTimeStamp;
    private int shakeLevel;

    public LabelAlarmClockInfo() {
        this.alarmId = 0;
        this.alarmStatus = 1;
        this.alarmHour = 12;
        this.alarmMinute = 0;
        this.alarmShakeCount = 10;
        this.alarmCycle = 62;
        this.alarmTimeStamp = 0L;
        this.shakeLevel = 3;
        this.alarmLabelType = 0;
    }

    public int getAlarmCycle() {
        return this.alarmCycle;
    }

    public String getAlarmDescription() {
        return this.alarmDescription;
    }

    public int getAlarmHour() {
        return this.alarmHour;
    }

    public int getAlarmId() {
        return this.alarmId;
    }

    public int getAlarmLabelType() {
        return this.alarmLabelType;
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

    public long getAlarmTimeStamp() {
        return this.alarmTimeStamp;
    }

    public int getShakeLevel() {
        return this.shakeLevel;
    }

    public void setAlarmCycle(int i2) {
        this.alarmCycle = i2;
    }

    public void setAlarmDescription(String str) {
        this.alarmDescription = str;
    }

    public void setAlarmHour(int i2) {
        this.alarmHour = i2;
    }

    public void setAlarmId(int i2) {
        this.alarmId = i2;
    }

    public void setAlarmLabelType(int i2) {
        this.alarmLabelType = i2;
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

    public void setAlarmTimeStamp(long j2) {
        this.alarmTimeStamp = j2;
    }

    public void setShakeLevel(int i2) {
        this.shakeLevel = i2;
    }

    public LabelAlarmClockInfo(int i2, int i3, int i4, int i5, int i6, int i7, String str, long j2, int i8) {
        this.alarmLabelType = 0;
        this.alarmId = i2;
        this.alarmStatus = i3;
        this.alarmHour = i4;
        this.alarmMinute = i5;
        this.alarmShakeCount = i6;
        this.alarmCycle = i7;
        this.alarmDescription = str;
        this.alarmTimeStamp = j2;
        this.shakeLevel = i8;
    }

    public LabelAlarmClockInfo(int i2, int i3, int i4, int i5, int i6, int i7, String str, long j2, int i8, int i9) {
        this(i2, i3, i4, i5, i6, i7, str, j2, i8);
        this.alarmLabelType = i9;
    }
}
