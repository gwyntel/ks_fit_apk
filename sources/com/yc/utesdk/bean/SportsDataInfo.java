package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class SportsDataInfo {
    public static final int DATA_FORMAT_TYPE_0 = 0;
    public static final int DATA_FORMAT_TYPE_1 = 1;
    public static final int DATA_FORMAT_TYPE_2 = 2;
    public static final int DATA_FORMAT_TYPE_3 = 3;
    public static final int DATA_FORMAT_TYPE_4 = 4;
    public static final int DATA_FORMAT_TYPE_5 = 5;
    public static final int DATA_FORMAT_TYPE_6 = 6;
    public static final int DATA_FORMAT_TYPE_7 = 7;
    public static final int DATA_FORMAT_TYPE_8 = 8;
    public static final int DATA_FORMAT_TYPE_9 = 9;

    /* renamed from: a, reason: collision with root package name */
    int f24810a;

    /* renamed from: b, reason: collision with root package name */
    int f24811b;
    private int boatingAvgRate;
    private int boatingMaxRate;
    private int boatingMinRate;
    private int boatingPullTimes;

    /* renamed from: c, reason: collision with root package name */
    List f24812c;
    public String calendar;
    public float calories;

    /* renamed from: d, reason: collision with root package name */
    int f24813d;
    public float distance;
    public int durationTime;

    /* renamed from: e, reason: collision with root package name */
    int f24814e;
    public String endDate;

    /* renamed from: f, reason: collision with root package name */
    int f24815f;

    /* renamed from: g, reason: collision with root package name */
    int f24816g;

    /* renamed from: h, reason: collision with root package name */
    int f24817h;

    /* renamed from: i, reason: collision with root package name */
    int f24818i;

    /* renamed from: j, reason: collision with root package name */
    int f24819j;

    /* renamed from: k, reason: collision with root package name */
    int f24820k;

    /* renamed from: l, reason: collision with root package name */
    float f24821l;

    /* renamed from: m, reason: collision with root package name */
    float f24822m;
    public int maxHeartRate;
    public int minHeartRate;

    /* renamed from: n, reason: collision with root package name */
    int f24823n;

    /* renamed from: o, reason: collision with root package name */
    int f24824o;

    /* renamed from: p, reason: collision with root package name */
    int f24825p;

    /* renamed from: q, reason: collision with root package name */
    int f24826q;

    /* renamed from: r, reason: collision with root package name */
    int f24827r;

    /* renamed from: s, reason: collision with root package name */
    int f24828s;
    public int sportsCount;
    public int sportsMode;
    public String startDate;
    public int stepCount;
    public int swimAverageStrokeFrequency;
    public int swimAverageSwolf;
    public int swimBestSwolf;
    public int swimLaps;
    public int swimStrokeFrequency;
    public int swimStrokeTimes;
    public int swimSwolf;
    public int swimType;
    public int verHeartRate;
    public float verPace;

    public int getAerobicEndurancHRDuration() {
        return this.f24815f;
    }

    public int getAnaerobicEnduranceHRDuration() {
        return this.f24816g;
    }

    public int getAverageStride() {
        return this.f24818i;
    }

    public int getBoatingAvgRate() {
        return this.boatingAvgRate;
    }

    public int getBoatingMaxRate() {
        return this.boatingMaxRate;
    }

    public int getBoatingMinRate() {
        return this.boatingMinRate;
    }

    public int getBoatingPullTimes() {
        return this.boatingPullTimes;
    }

    public String getCalendar() {
        return this.calendar;
    }

    public float getCalories() {
        return this.calories;
    }

    public int getDataFormatType() {
        return this.f24823n;
    }

    public float getDistance() {
        return this.distance;
    }

    public int getDurationTime() {
        return this.durationTime;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public int getFatBurningHRDuration() {
        return this.f24814e;
    }

    public int getHeartRateCount() {
        return this.f24811b;
    }

    public List<SportsHeartRateInfo> getHeartRateInfoList() {
        return this.f24812c;
    }

    public int getLimitHRDuration() {
        return this.f24817h;
    }

    public int getLongestStreak() {
        return this.f24828s;
    }

    public int getMaxHeartRate() {
        return this.maxHeartRate;
    }

    public float getMaxPace() {
        return this.f24821l;
    }

    public int getMaxPace2() {
        return this.f24824o;
    }

    public int getMaxStride() {
        return this.f24819j;
    }

    public int getMinHeartRate() {
        return this.minHeartRate;
    }

    public float getMinPace() {
        return this.f24822m;
    }

    public int getMinPace2() {
        return this.f24825p;
    }

    public int getMinStride() {
        return this.f24820k;
    }

    public int getSportsCount() {
        return this.sportsCount;
    }

    public int getSportsMode() {
        return this.sportsMode;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public int getStepCount() {
        return this.stepCount;
    }

    public int getSwimAverageStrokeFrequency() {
        return this.swimAverageStrokeFrequency;
    }

    public int getSwimAverageSwolf() {
        return this.swimAverageSwolf;
    }

    public int getSwimBestSwolf() {
        return this.swimBestSwolf;
    }

    public int getSwimLaps() {
        return this.swimLaps;
    }

    public int getSwimStrokeFrequency() {
        return this.swimStrokeFrequency;
    }

    public int getSwimStrokeTimes() {
        return this.swimStrokeTimes;
    }

    public int getSwimSwolf() {
        return this.swimSwolf;
    }

    public int getSwimType() {
        return this.swimType;
    }

    public int getTimeTnterval() {
        return this.f24810a;
    }

    public int getTrippedCount() {
        return this.f24827r;
    }

    public int getVerHeartRate() {
        return this.verHeartRate;
    }

    public float getVerPace() {
        return this.verPace;
    }

    public int getVerPace2() {
        return this.f24826q;
    }

    public int getWarmUpHRDuration() {
        return this.f24813d;
    }

    public void setAerobicEndurancHRDuration(int i2) {
        this.f24815f = i2;
    }

    public void setAnaerobicEnduranceHRDuration(int i2) {
        this.f24816g = i2;
    }

    public void setAverageStride(int i2) {
        this.f24818i = i2;
    }

    public void setBoatingAvgRate(int i2) {
        this.boatingAvgRate = i2;
    }

    public void setBoatingMaxRate(int i2) {
        this.boatingMaxRate = i2;
    }

    public void setBoatingMinRate(int i2) {
        this.boatingMinRate = i2;
    }

    public void setBoatingPullTimes(int i2) {
        this.boatingPullTimes = i2;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setCalories(float f2) {
        this.calories = f2;
    }

    public void setDataFormatType(int i2) {
        this.f24823n = i2;
    }

    public void setDistance(float f2) {
        this.distance = f2;
    }

    public void setDurationTime(int i2) {
        this.durationTime = i2;
    }

    public void setEndDate(String str) {
        this.endDate = str;
    }

    public void setFatBurningHRDuration(int i2) {
        this.f24814e = i2;
    }

    public void setHeartRateCount(int i2) {
        this.f24811b = i2;
    }

    public void setHeartRateInfoList(List<SportsHeartRateInfo> list) {
        this.f24812c = list;
    }

    public void setLimitHRDuration(int i2) {
        this.f24817h = i2;
    }

    public void setLongestStreak(int i2) {
        this.f24828s = i2;
    }

    public void setMaxHeartRate(int i2) {
        this.maxHeartRate = i2;
    }

    public void setMaxPace(float f2) {
        this.f24821l = f2;
    }

    public void setMaxPace2(int i2) {
        this.f24824o = i2;
    }

    public void setMaxStride(int i2) {
        this.f24819j = i2;
    }

    public void setMinHeartRate(int i2) {
        this.minHeartRate = i2;
    }

    public void setMinPace(float f2) {
        this.f24822m = f2;
    }

    public void setMinPace2(int i2) {
        this.f24825p = i2;
    }

    public void setMinStride(int i2) {
        this.f24820k = i2;
    }

    public void setSportsCount(int i2) {
        this.sportsCount = i2;
    }

    public void setSportsMode(int i2) {
        this.sportsMode = i2;
    }

    public void setStartDate(String str) {
        this.startDate = str;
    }

    public void setStepCount(int i2) {
        this.stepCount = i2;
    }

    public void setSwimAverageStrokeFrequency(int i2) {
        this.swimAverageStrokeFrequency = i2;
    }

    public void setSwimAverageSwolf(int i2) {
        this.swimAverageSwolf = i2;
    }

    public void setSwimBestSwolf(int i2) {
        this.swimBestSwolf = i2;
    }

    public void setSwimLaps(int i2) {
        this.swimLaps = i2;
    }

    public void setSwimStrokeFrequency(int i2) {
        this.swimStrokeFrequency = i2;
    }

    public void setSwimStrokeTimes(int i2) {
        this.swimStrokeTimes = i2;
    }

    public void setSwimSwolf(int i2) {
        this.swimSwolf = i2;
    }

    public void setSwimType(int i2) {
        this.swimType = i2;
    }

    public void setTimeTnterval(int i2) {
        this.f24810a = i2;
    }

    public void setTrippedCount(int i2) {
        this.f24827r = i2;
    }

    public void setVerHeartRate(int i2) {
        this.verHeartRate = i2;
    }

    public void setVerPace(float f2) {
        this.verPace = f2;
    }

    public void setVerPace2(int i2) {
        this.f24826q = i2;
    }

    public void setWarmUpHRDuration(int i2) {
        this.f24813d = i2;
    }
}
