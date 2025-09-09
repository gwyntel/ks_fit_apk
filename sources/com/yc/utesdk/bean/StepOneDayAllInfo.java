package com.yc.utesdk.bean;

import java.util.ArrayList;

/* loaded from: classes4.dex */
public class StepOneDayAllInfo {
    private String calendar;
    private float calories;
    private float distance;
    private float runCalories;
    private float runDistance;
    private int runDurationTime;
    private int runSteps;
    private int step;
    private ArrayList<StepOneHourInfo> stepOneHourArrayInfo;
    private ArrayList<StepRunHourInfo> stepRunHourArrayInfo;
    private ArrayList<StepWalkHourInfo> stepWalkHourArrayInfo;
    private float walkCalories;
    private float walkDistance;
    private int walkDurationTime;
    private int walkSteps;

    public StepOneDayAllInfo(int i2, float f2, float f3, int i3, float f4, float f5, int i4, int i5, float f6, float f7, int i6) {
        setStep(i2);
        setCalories(f2);
        setDistance(f3);
        setRunSteps(i3);
        setRunCalories(f4);
        setRunDistance(f5);
        setRunDurationTime(i4);
        setWalkSteps(i5);
        setWalkCalories(f6);
        setWalkDistance(f7);
        setWalkDurationTime(i6);
    }

    public String getCalendar() {
        return this.calendar;
    }

    public float getCalories() {
        return this.calories;
    }

    public float getDistance() {
        return this.distance;
    }

    public float getRunCalories() {
        return this.runCalories;
    }

    public float getRunDistance() {
        return this.runDistance;
    }

    public int getRunDurationTime() {
        return this.runDurationTime;
    }

    public int getRunSteps() {
        return this.runSteps;
    }

    public int getStep() {
        return this.step;
    }

    public ArrayList<StepOneHourInfo> getStepOneHourArrayInfo() {
        return this.stepOneHourArrayInfo;
    }

    public ArrayList<StepRunHourInfo> getStepRunHourArrayInfo() {
        return this.stepRunHourArrayInfo;
    }

    public ArrayList<StepWalkHourInfo> getStepWalkHourArrayInfo() {
        return this.stepWalkHourArrayInfo;
    }

    public float getWalkCalories() {
        return this.walkCalories;
    }

    public float getWalkDistance() {
        return this.walkDistance;
    }

    public int getWalkDurationTime() {
        return this.walkDurationTime;
    }

    public int getWalkSteps() {
        return this.walkSteps;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setCalories(float f2) {
        this.calories = f2;
    }

    public void setDistance(float f2) {
        this.distance = f2;
    }

    public void setRunCalories(float f2) {
        this.runCalories = f2;
    }

    public void setRunDistance(float f2) {
        this.runDistance = f2;
    }

    public void setRunDurationTime(int i2) {
        this.runDurationTime = i2;
    }

    public void setRunSteps(int i2) {
        this.runSteps = i2;
    }

    public void setStep(int i2) {
        this.step = i2;
    }

    public void setStepOneHourArrayInfo(ArrayList<StepOneHourInfo> arrayList) {
        this.stepOneHourArrayInfo = arrayList;
    }

    public void setStepRunHourArrayInfo(ArrayList<StepRunHourInfo> arrayList) {
        this.stepRunHourArrayInfo = arrayList;
    }

    public void setStepWalkHourArrayInfo(ArrayList<StepWalkHourInfo> arrayList) {
        this.stepWalkHourArrayInfo = arrayList;
    }

    public void setWalkCalories(float f2) {
        this.walkCalories = f2;
    }

    public void setWalkDistance(float f2) {
        this.walkDistance = f2;
    }

    public void setWalkDurationTime(int i2) {
        this.walkDurationTime = i2;
    }

    public void setWalkSteps(int i2) {
        this.walkSteps = i2;
    }

    public StepOneDayAllInfo(String str, int i2, float f2, float f3, int i3, float f4, float f5, int i4, int i5, float f6, float f7, int i6, ArrayList<StepOneHourInfo> arrayList, ArrayList<StepRunHourInfo> arrayList2, ArrayList<StepWalkHourInfo> arrayList3) {
        setCalendar(str);
        setStep(i2);
        setCalories(f2);
        setDistance(f3);
        setRunSteps(i3);
        setRunCalories(f4);
        setRunDistance(f5);
        setRunDurationTime(i4);
        setWalkSteps(i5);
        setWalkCalories(f6);
        setWalkDistance(f7);
        setWalkDurationTime(i6);
        setStepOneHourArrayInfo(arrayList);
        setStepRunHourArrayInfo(arrayList2);
        setStepWalkHourArrayInfo(arrayList3);
    }
}
