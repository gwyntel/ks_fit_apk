package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class WeatherDayInfo {

    /* renamed from: a, reason: collision with root package name */
    int f24855a;

    /* renamed from: b, reason: collision with root package name */
    int f24856b;

    /* renamed from: c, reason: collision with root package name */
    int f24857c;

    /* renamed from: d, reason: collision with root package name */
    int f24858d;

    /* renamed from: e, reason: collision with root package name */
    int f24859e;

    /* renamed from: f, reason: collision with root package name */
    int f24860f;

    /* renamed from: g, reason: collision with root package name */
    int f24861g;

    public WeatherDayInfo() {
    }

    public int getDayHumDaytime() {
        return this.f24858d;
    }

    public int getDayHumNighttime() {
        return this.f24859e;
    }

    public int getDayUvDaytime() {
        return this.f24860f;
    }

    public int getDayUvNighttime() {
        return this.f24861g;
    }

    public int getTemperatureMax() {
        return this.f24856b;
    }

    public int getTemperatureMin() {
        return this.f24857c;
    }

    public int getWeatherCode() {
        return this.f24855a;
    }

    public void setDayHumDaytime(int i2) {
        this.f24858d = i2;
    }

    public void setDayHumNighttime(int i2) {
        this.f24859e = i2;
    }

    public void setDayUvDaytime(int i2) {
        this.f24860f = i2;
    }

    public void setDayUvNighttime(int i2) {
        this.f24861g = i2;
    }

    public void setTemperatureMax(int i2) {
        this.f24856b = i2;
    }

    public void setTemperatureMin(int i2) {
        this.f24857c = i2;
    }

    public void setWeatherCode(int i2) {
        this.f24855a = i2;
    }

    public WeatherDayInfo(int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.f24855a = i2;
        this.f24856b = i3;
        this.f24857c = i4;
        this.f24858d = i5;
        this.f24859e = i6;
        this.f24860f = i7;
        this.f24861g = i8;
    }
}
