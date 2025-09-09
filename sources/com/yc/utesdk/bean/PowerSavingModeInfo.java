package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class PowerSavingModeInfo {
    private int fromTimeHour;
    private int fromTimeMinute;
    private boolean powerSwitch;
    private boolean timingSwitch;
    private int toTimeHour;
    private int toTimeMinute;

    public PowerSavingModeInfo() {
        this.powerSwitch = false;
        this.timingSwitch = false;
        this.fromTimeHour = 0;
        this.fromTimeMinute = 0;
        this.toTimeHour = 0;
        this.toTimeMinute = 0;
    }

    public int getFromTimeHour() {
        return this.fromTimeHour;
    }

    public int getFromTimeMinute() {
        return this.fromTimeMinute;
    }

    public boolean getPowerSwitch() {
        return this.powerSwitch;
    }

    public boolean getTimingSwitch() {
        return this.timingSwitch;
    }

    public int getToTimeHour() {
        return this.toTimeHour;
    }

    public int getToTimeMinute() {
        return this.toTimeMinute;
    }

    public void setFromTimeHour(int i2) {
        this.fromTimeHour = i2;
    }

    public void setFromTimeMinute(int i2) {
        this.fromTimeMinute = i2;
    }

    public void setPowerSwitch(boolean z2) {
        this.powerSwitch = z2;
    }

    public void setTimingSwitch(boolean z2) {
        this.timingSwitch = z2;
    }

    public void setToTimeHour(int i2) {
        this.toTimeHour = i2;
    }

    public void setToTimeMinute(int i2) {
        this.toTimeMinute = i2;
    }

    public PowerSavingModeInfo(boolean z2) {
        this.timingSwitch = false;
        this.fromTimeHour = 0;
        this.fromTimeMinute = 0;
        this.toTimeHour = 0;
        this.toTimeMinute = 0;
        this.powerSwitch = z2;
    }
}
