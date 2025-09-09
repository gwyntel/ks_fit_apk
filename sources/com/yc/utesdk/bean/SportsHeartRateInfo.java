package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class SportsHeartRateInfo {

    /* renamed from: a, reason: collision with root package name */
    String f24829a;

    /* renamed from: b, reason: collision with root package name */
    int f24830b;

    /* renamed from: c, reason: collision with root package name */
    float f24831c;

    /* renamed from: d, reason: collision with root package name */
    int f24832d;

    /* renamed from: e, reason: collision with root package name */
    float f24833e;

    /* renamed from: f, reason: collision with root package name */
    double f24834f;

    /* renamed from: g, reason: collision with root package name */
    double f24835g;

    /* renamed from: h, reason: collision with root package name */
    float f24836h;

    /* renamed from: i, reason: collision with root package name */
    int f24837i;

    /* renamed from: j, reason: collision with root package name */
    int f24838j;

    public SportsHeartRateInfo(String str, int i2) {
        this.f24829a = str;
        this.f24830b = i2;
    }

    public String getDateTime() {
        return this.f24829a;
    }

    public int getElevation() {
        return this.f24838j;
    }

    public int getFrequency() {
        return this.f24837i;
    }

    public int getHeartRate() {
        return this.f24830b;
    }

    public double getLatitude() {
        return this.f24834f;
    }

    public double getLongitude() {
        return this.f24835g;
    }

    public float getRealTimePace() {
        return this.f24831c;
    }

    public int getRealTimePace2() {
        return this.f24832d;
    }

    public float getRealTimeSpeed() {
        return this.f24836h;
    }

    public float getRealTimeStride() {
        return this.f24833e;
    }

    public void setDateTime(String str) {
        this.f24829a = str;
    }

    public void setElevation(int i2) {
        this.f24838j = i2;
    }

    public void setFrequency(int i2) {
        this.f24837i = i2;
    }

    public void setHeartRate(int i2) {
        this.f24830b = i2;
    }

    public void setLatitude(double d2) {
        this.f24834f = d2;
    }

    public void setLongitude(double d2) {
        this.f24835g = d2;
    }

    public void setRealTimePace(float f2) {
        this.f24831c = f2;
    }

    public void setRealTimePace2(int i2) {
        this.f24832d = i2;
    }

    public void setRealTimeSpeed(float f2) {
        this.f24836h = f2;
    }

    public void setRealTimeStride(float f2) {
        this.f24833e = f2;
    }

    public SportsHeartRateInfo(String str, int i2, float f2) {
        this.f24829a = str;
        this.f24830b = i2;
        this.f24836h = f2;
    }

    public SportsHeartRateInfo(String str, int i2, float f2, double d2, double d3) {
        this.f24829a = str;
        this.f24830b = i2;
        this.f24836h = f2;
        this.f24834f = d2;
        this.f24835g = d3;
    }

    public SportsHeartRateInfo(String str, int i2, float f2, double d2, double d3, int i3) {
        this.f24829a = str;
        this.f24830b = i2;
        this.f24836h = f2;
        this.f24834f = d2;
        this.f24835g = d3;
        this.f24838j = i3;
    }

    public SportsHeartRateInfo(String str, int i2, float f2, float f3) {
        this.f24829a = str;
        this.f24830b = i2;
        this.f24831c = f2;
        this.f24833e = f3;
    }

    public SportsHeartRateInfo(String str, int i2, float f2, float f3, double d2, double d3) {
        this.f24829a = str;
        this.f24830b = i2;
        this.f24831c = f2;
        this.f24833e = f3;
        this.f24834f = d2;
        this.f24835g = d3;
    }

    public SportsHeartRateInfo(String str, int i2, float f2, float f3, double d2, double d3, int i3) {
        this.f24829a = str;
        this.f24830b = i2;
        this.f24831c = f2;
        this.f24833e = f3;
        this.f24834f = d2;
        this.f24835g = d3;
        this.f24838j = i3;
    }

    public SportsHeartRateInfo(String str, int i2, int i3) {
        this.f24829a = str;
        this.f24830b = i2;
        this.f24837i = i3;
    }

    public SportsHeartRateInfo(String str, int i2, int i3, int i4) {
        this.f24829a = str;
        this.f24830b = i2;
        this.f24832d = i3;
        this.f24837i = i4;
    }

    public SportsHeartRateInfo(String str, int i2, int i3, int i4, double d2, double d3) {
        this.f24829a = str;
        this.f24830b = i2;
        this.f24832d = i3;
        this.f24837i = i4;
        this.f24834f = d2;
        this.f24835g = d3;
    }
}
