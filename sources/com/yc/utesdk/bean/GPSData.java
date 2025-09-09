package com.yc.utesdk.bean;

import java.io.Serializable;

/* loaded from: classes4.dex */
public class GPSData implements Serializable {
    private double accuracy;
    private int altitude;
    private double bearing;
    private double gcj02Latitude;
    private double gcj02Longitude;
    private double gpsLatitude;
    private double gpsLongitude;
    private int speed;
    private String startTime;

    public double getAccuracy() {
        return this.accuracy;
    }

    public int getAltitude() {
        return this.altitude;
    }

    public double getBearing() {
        return this.bearing;
    }

    public double getGcj02Latitude() {
        return this.gcj02Latitude;
    }

    public double getGcj02Longitude() {
        return this.gcj02Longitude;
    }

    public double getGpsLatitude() {
        return this.gpsLatitude;
    }

    public double getGpsLongitude() {
        return this.gpsLongitude;
    }

    public int getSpeed() {
        return this.speed;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setAccuracy(double d2) {
        this.accuracy = d2;
    }

    public void setAltitude(int i2) {
        this.altitude = i2;
    }

    public void setBearing(double d2) {
        this.bearing = d2;
    }

    public void setGcj02Latitude(double d2) {
        this.gcj02Latitude = d2;
    }

    public void setGcj02Longitude(double d2) {
        this.gcj02Longitude = d2;
    }

    public void setGpsLatitude(double d2) {
        this.gpsLatitude = d2;
    }

    public void setGpsLongitude(double d2) {
        this.gpsLongitude = d2;
    }

    public void setSpeed(int i2) {
        this.speed = i2;
    }

    public void setStartTime(String str) {
        this.startTime = str;
    }
}
