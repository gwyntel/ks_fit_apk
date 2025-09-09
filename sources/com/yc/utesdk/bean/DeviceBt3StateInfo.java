package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class DeviceBt3StateInfo {
    public static final int DEVICE_BT3_STATUS_NO = 0;
    public static final int DEVICE_BT3_STATUS_YES = 1;

    /* renamed from: a, reason: collision with root package name */
    String f24695a;

    /* renamed from: b, reason: collision with root package name */
    String f24696b;

    /* renamed from: c, reason: collision with root package name */
    int f24697c;

    /* renamed from: d, reason: collision with root package name */
    int f24698d;

    /* renamed from: e, reason: collision with root package name */
    int f24699e;

    public DeviceBt3StateInfo() {
    }

    public String getDeviceAddressBt3() {
        return this.f24696b;
    }

    public int getDeviceBtConnectStatus() {
        return this.f24699e;
    }

    public int getDeviceBtPairingStatus() {
        return this.f24698d;
    }

    public int getDeviceBtSwitch() {
        return this.f24697c;
    }

    public String getDeviceNameBt3() {
        return this.f24695a;
    }

    public void setDeviceAddressBt3(String str) {
        this.f24696b = str;
    }

    public void setDeviceBtConnectStatus(int i2) {
        this.f24699e = i2;
    }

    public void setDeviceBtPairingStatus(int i2) {
        this.f24698d = i2;
    }

    public void setDeviceBtSwitch(int i2) {
        this.f24697c = i2;
    }

    public void setDeviceNameBt3(String str) {
        this.f24695a = str;
    }

    public DeviceBt3StateInfo(String str, String str2, int i2, int i3, int i4) {
        this.f24695a = str;
        this.f24696b = str2;
        this.f24697c = i2;
        this.f24698d = i3;
        this.f24699e = i4;
    }
}
