package com.yc.utesdk.bean;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class WeatherInfo {

    /* renamed from: a, reason: collision with root package name */
    String f24866a;

    /* renamed from: b, reason: collision with root package name */
    int f24867b;

    /* renamed from: c, reason: collision with root package name */
    int f24868c;

    /* renamed from: d, reason: collision with root package name */
    int f24869d;

    /* renamed from: e, reason: collision with root package name */
    int f24870e;

    /* renamed from: f, reason: collision with root package name */
    int f24871f;

    /* renamed from: g, reason: collision with root package name */
    int f24872g;

    /* renamed from: h, reason: collision with root package name */
    List f24873h = new ArrayList();

    /* renamed from: i, reason: collision with root package name */
    List f24874i = new ArrayList();

    public String getCityName() {
        return this.f24866a;
    }

    public int getTodayAqi() {
        return this.f24869d;
    }

    public int getTodayPm25() {
        return this.f24868c;
    }

    public int getTodayTmpCurrent() {
        return this.f24867b;
    }

    public int getTodayTmpMax() {
        return this.f24871f;
    }

    public int getTodayTmpMin() {
        return this.f24872g;
    }

    public int getTodayWeatherCode() {
        return this.f24870e;
    }

    public List<WeatherDayInfo> getWeatherDayInfoList() {
        return this.f24874i;
    }

    public List<WeatherHourInfo> getWeatherHourInfoList() {
        return this.f24873h;
    }

    public void setCityName(String str) {
        this.f24866a = str;
    }

    public void setTodayAqi(int i2) {
        this.f24869d = i2;
    }

    public void setTodayPm25(int i2) {
        this.f24868c = i2;
    }

    public void setTodayTmpCurrent(int i2) {
        this.f24867b = i2;
    }

    public void setTodayTmpMax(int i2) {
        this.f24871f = i2;
    }

    public void setTodayTmpMin(int i2) {
        this.f24872g = i2;
    }

    public void setTodayWeatherCode(int i2) {
        this.f24870e = i2;
    }

    public void setWeatherDayInfoList(List<WeatherDayInfo> list) {
        this.f24874i = list;
    }

    public void setWeatherHourInfoList(List<WeatherHourInfo> list) {
        this.f24873h = list;
    }
}
