package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class SportsModeControlInfo {

    /* renamed from: a, reason: collision with root package name */
    int f24839a;

    /* renamed from: b, reason: collision with root package name */
    float f24840b;

    /* renamed from: c, reason: collision with root package name */
    int f24841c;

    /* renamed from: d, reason: collision with root package name */
    float f24842d;

    /* renamed from: e, reason: collision with root package name */
    boolean f24843e;

    public SportsModeControlInfo() {
    }

    public int getSportsCalories() {
        return this.f24841c;
    }

    public float getSportsDistance() {
        return this.f24840b;
    }

    public int getSportsDurationTime() {
        return this.f24839a;
    }

    public float getSportsPace() {
        return this.f24842d;
    }

    public boolean isGPSModes() {
        return this.f24843e;
    }

    public void setGPSModes(boolean z2) {
        this.f24843e = z2;
    }

    public void setSportsCalories(int i2) {
        this.f24841c = i2;
    }

    public void setSportsDistance(float f2) {
        this.f24840b = f2;
    }

    public void setSportsDurationTime(int i2) {
        this.f24839a = i2;
    }

    public void setSportsPace(float f2) {
        this.f24842d = f2;
    }

    public SportsModeControlInfo(boolean z2, int i2) {
        this.f24843e = z2;
        setSportsDurationTime(i2);
    }

    public SportsModeControlInfo(boolean z2, int i2, float f2, int i3, float f3) {
        this.f24843e = z2;
        setSportsDurationTime(i2);
        setSportsDistance(f2);
        setSportsCalories(i3);
        setSportsPace(f3);
    }
}
