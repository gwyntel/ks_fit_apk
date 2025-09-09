package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class MoodSensorInterfaceInfo {
    public static final int SWITCH_NO = 0;
    public static final int SWITCH_YES = 1;

    /* renamed from: a, reason: collision with root package name */
    String f24765a;

    /* renamed from: b, reason: collision with root package name */
    int f24766b;

    /* renamed from: c, reason: collision with root package name */
    int f24767c;

    /* renamed from: d, reason: collision with root package name */
    int f24768d;

    public MoodSensorInterfaceInfo() {
    }

    public int getFatigueInterfaceSwitch() {
        return this.f24768d;
    }

    public int getMoodInterfaceSwitch() {
        return this.f24766b;
    }

    public int getPressureInterfaceSwitch() {
        return this.f24767c;
    }

    public String getSensorType() {
        return this.f24765a;
    }

    public void setFatigueInterfaceSwitch(int i2) {
        this.f24768d = i2;
    }

    public void setMoodInterfaceSwitch(int i2) {
        this.f24766b = i2;
    }

    public void setPressureInterfaceSwitch(int i2) {
        this.f24767c = i2;
    }

    public void setSensorType(String str) {
        this.f24765a = str;
    }

    public MoodSensorInterfaceInfo(String str, int i2, int i3, int i4) {
        this.f24765a = str;
        this.f24766b = i2;
        this.f24767c = i3;
        this.f24768d = i4;
    }
}
