package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class TimeZoneInfo {
    private float timeZone = 0.0f;
    private String timeZoneRegion = "";

    public TimeZoneInfo() {
    }

    public float getTimeZone() {
        return this.timeZone;
    }

    public String getTimeZoneRegion() {
        return this.timeZoneRegion;
    }

    public void setTimeZone(float f2) {
        this.timeZone = f2;
    }

    public void setTimeZoneRegion(String str) {
        this.timeZoneRegion = str;
    }

    public TimeZoneInfo(float f2, String str) {
        setTimeZone(f2);
        setTimeZoneRegion(str);
    }
}
