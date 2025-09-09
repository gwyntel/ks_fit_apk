package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class WeatherHourInfo {
    public static final int NO_DATA = 255;

    /* renamed from: a, reason: collision with root package name */
    int f24862a;

    /* renamed from: b, reason: collision with root package name */
    int f24863b;

    /* renamed from: c, reason: collision with root package name */
    int f24864c;

    /* renamed from: d, reason: collision with root package name */
    int f24865d;

    public WeatherHourInfo() {
        this.f24862a = 255;
        this.f24863b = 255;
        this.f24864c = 255;
        this.f24865d = 255;
    }

    public int getHum() {
        return this.f24863b;
    }

    public int getTemperature() {
        return this.f24862a;
    }

    public int getUv() {
        return this.f24864c;
    }

    public int getWeatherCode() {
        return this.f24865d;
    }

    public void setHum(int i2) {
        this.f24863b = i2;
    }

    public void setTemperature(int i2) {
        this.f24862a = i2;
    }

    public void setUv(int i2) {
        this.f24864c = i2;
    }

    public void setWeatherCode(int i2) {
        this.f24865d = i2;
    }

    public WeatherHourInfo(int i2, int i3, int i4, int i5) {
        this.f24862a = i2;
        this.f24863b = i3;
        this.f24864c = i4;
        this.f24865d = i5;
    }
}
