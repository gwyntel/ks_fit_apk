package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class DeviceParametersInfo {
    public static final int bodyGenderFemale = 0;
    public static final int bodyGenderMale = 1;
    public static final int defaultsValue = -1;
    public static final int switchStatusNo = 0;
    public static final int switchStatusYes = 1;
    private int bodyHeight = -1;
    private float bodyWeight = -1.0f;
    private int offScreenTime = -1;
    private int stepTask = -1;
    private int raiseHandBrightScreenSwitch = -1;
    private int highestRateSwitch = -1;
    private int highestRate = -1;
    private int bodyGender = -1;
    private int bodyAge = -1;
    private int deviceLostSwitch = -1;
    private int lowestRateSwitch = -1;
    private int lowestRate = -1;
    private int celsiusFahrenheitValue = -1;
    private int onlySupportEnCn = -1;

    public int getBodyAge() {
        return this.bodyAge;
    }

    public int getBodyGender() {
        return this.bodyGender;
    }

    public int getBodyHeight() {
        return this.bodyHeight;
    }

    public float getBodyWeight() {
        return this.bodyWeight;
    }

    public int getCelsiusFahrenheitValue() {
        return this.celsiusFahrenheitValue;
    }

    public int getDeviceLostSwitch() {
        return this.deviceLostSwitch;
    }

    public int getHighestRate() {
        return this.highestRate;
    }

    public int getHighestRateSwitch() {
        return this.highestRateSwitch;
    }

    public int getLowestRate() {
        return this.lowestRate;
    }

    public int getLowestRateSwitch() {
        return this.lowestRateSwitch;
    }

    public int getOffScreenTime() {
        return this.offScreenTime;
    }

    public int getOnlySupportEnCn() {
        return this.onlySupportEnCn;
    }

    public int getRaiseHandBrightScreenSwitch() {
        return this.raiseHandBrightScreenSwitch;
    }

    public int getStepTask() {
        return this.stepTask;
    }

    public void setBodyAge(int i2) {
        this.bodyAge = i2;
    }

    public void setBodyGender(int i2) {
        this.bodyGender = i2;
    }

    public void setBodyHeight(int i2) {
        this.bodyHeight = i2;
    }

    public void setBodyWeight(float f2) {
        this.bodyWeight = f2;
    }

    public void setCelsiusFahrenheitValue(int i2) {
        this.celsiusFahrenheitValue = i2;
    }

    public void setDeviceLostSwitch(int i2) {
        this.deviceLostSwitch = i2;
    }

    public void setHighestRateAndSwitch(int i2, int i3) {
        this.highestRate = i2;
        this.highestRateSwitch = i3;
    }

    public void setLowestRateAndSwitch(int i2, int i3) {
        this.lowestRate = i2;
        this.lowestRateSwitch = i3;
    }

    public void setOffScreenTime(int i2) {
        this.offScreenTime = i2;
    }

    public void setOnlySupportEnCn(int i2) {
        this.onlySupportEnCn = i2;
    }

    public void setRaiseHandBrightScreenSwitch(int i2) {
        this.raiseHandBrightScreenSwitch = i2;
    }

    public void setStepTask(int i2) {
        this.stepTask = i2;
    }
}
