package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class AppLocationDataInfo {

    /* renamed from: a, reason: collision with root package name */
    double f24682a;

    /* renamed from: b, reason: collision with root package name */
    double f24683b;

    /* renamed from: c, reason: collision with root package name */
    float f24684c;

    /* renamed from: d, reason: collision with root package name */
    int f24685d;

    /* renamed from: e, reason: collision with root package name */
    int f24686e;

    public AppLocationDataInfo() {
    }

    public double getLatitude() {
        return this.f24682a;
    }

    public double getLongitude() {
        return this.f24683b;
    }

    public int getPointDistance() {
        return this.f24685d;
    }

    public float getSpeed() {
        return this.f24684c;
    }

    public int getTotalDistance() {
        return this.f24686e;
    }

    public void setLatitude(double d2) {
        this.f24682a = d2;
    }

    public void setLongitude(double d2) {
        this.f24683b = d2;
    }

    public void setPointDistance(int i2) {
        this.f24685d = i2;
    }

    public void setSpeed(float f2) {
        this.f24684c = f2;
    }

    public void setTotalDistance(int i2) {
        this.f24686e = i2;
    }

    public AppLocationDataInfo(double d2, double d3, float f2, int i2, int i3) {
        this.f24682a = d2;
        this.f24683b = d3;
        this.f24684c = f2;
        this.f24685d = i2;
        this.f24686e = i3;
    }
}
