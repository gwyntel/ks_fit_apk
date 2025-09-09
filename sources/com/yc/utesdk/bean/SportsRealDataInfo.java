package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class SportsRealDataInfo {
    public float calories;
    public int currentHeartRate;
    public float distance;
    public int sportsCount;
    public int sportsMode;
    public int stepCount;
    public float verPace;

    public SportsRealDataInfo(int i2, int i3, float f2, float f3, int i4, int i5, float f4) {
        this.sportsMode = i2;
        this.stepCount = i3;
        this.distance = f2;
        this.calories = f3;
        this.currentHeartRate = i4;
        this.sportsCount = i5;
        this.verPace = f4;
    }

    public float getCalories() {
        return this.calories;
    }

    public int getCurrentHeartRate() {
        return this.currentHeartRate;
    }

    public float getDistance() {
        return this.distance;
    }

    public int getSportsCount() {
        return this.sportsCount;
    }

    public int getSportsMode() {
        return this.sportsMode;
    }

    public int getStepCount() {
        return this.stepCount;
    }

    public float getVerPace() {
        return this.verPace;
    }

    public void setCalories(float f2) {
        this.calories = f2;
    }

    public void setCurrentHeartRate(int i2) {
        this.currentHeartRate = i2;
    }

    public void setDistance(float f2) {
        this.distance = f2;
    }

    public void setSportsCount(int i2) {
        this.sportsCount = i2;
    }

    public void setSportsMode(int i2) {
        this.sportsMode = i2;
    }

    public void setStepCount(int i2) {
        this.stepCount = i2;
    }

    public void setVerPace(float f2) {
        this.verPace = f2;
    }
}
