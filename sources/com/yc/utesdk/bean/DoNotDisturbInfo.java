package com.yc.utesdk.bean;

import com.yc.utesdk.utils.open.SPUtil;

/* loaded from: classes4.dex */
public class DoNotDisturbInfo {
    public static final int defaultsValue = -1;
    public static final int switchStatusNo = 0;
    public static final int switchStatusYes = 1;

    /* renamed from: a, reason: collision with root package name */
    int f24700a;

    /* renamed from: b, reason: collision with root package name */
    int f24701b;

    /* renamed from: c, reason: collision with root package name */
    int f24702c;

    /* renamed from: d, reason: collision with root package name */
    int f24703d;

    /* renamed from: e, reason: collision with root package name */
    int f24704e;

    /* renamed from: f, reason: collision with root package name */
    int f24705f;

    /* renamed from: g, reason: collision with root package name */
    int f24706g;

    /* renamed from: h, reason: collision with root package name */
    int f24707h;

    public DoNotDisturbInfo() {
        this.f24700a = -1;
        this.f24701b = -1;
        this.f24702c = -1;
        this.f24703d = -1;
        this.f24704e = -1;
        this.f24705f = -1;
        this.f24706g = -1;
        this.f24707h = -1;
    }

    public int getDisturbTimeSwitch() {
        if (this.f24703d == -1) {
            this.f24703d = SPUtil.getInstance().getDoNotDisturbTimeSwitch();
        }
        return this.f24703d;
    }

    public int getEndCallSwitch() {
        if (this.f24700a == -1) {
            this.f24700a = SPUtil.getInstance().getEndCallSwitch();
        }
        return this.f24700a;
    }

    public int getFromTimeHour() {
        if (this.f24704e == -1) {
            this.f24704e = SPUtil.getInstance().getDoNotDisturbFromHour();
        }
        return this.f24704e;
    }

    public int getFromTimeMinute() {
        if (this.f24705f == -1) {
            this.f24705f = SPUtil.getInstance().getDoNotDisturbFromMinute();
        }
        return this.f24705f;
    }

    public int getMessageSwitch() {
        if (this.f24701b == -1) {
            this.f24701b = SPUtil.getInstance().getDoNotDisturbMessageSwitch();
        }
        return this.f24701b;
    }

    public int getMotorSwitch() {
        if (this.f24702c == -1) {
            this.f24702c = SPUtil.getInstance().getDoNotDisturbMotorSwitch();
        }
        return this.f24702c;
    }

    public int getToTimeHour() {
        if (this.f24706g == -1) {
            this.f24706g = SPUtil.getInstance().getDoNotDisturbToHour();
        }
        return this.f24706g;
    }

    public int getToTimeMinute() {
        if (this.f24707h == -1) {
            this.f24707h = SPUtil.getInstance().getDoNotDisturbToMinute();
        }
        return this.f24707h;
    }

    public void setDisturbTimeSwitch(int i2) {
        this.f24703d = i2;
    }

    public void setEndCallSwitch(int i2) {
        this.f24700a = i2;
    }

    public void setFromTimeHour(int i2) {
        this.f24704e = i2;
    }

    public void setFromTimeMinute(int i2) {
        this.f24705f = i2;
    }

    public void setMessageSwitch(int i2) {
        this.f24701b = i2;
    }

    public void setMotorSwitch(int i2) {
        this.f24702c = i2;
    }

    public void setToTimeHour(int i2) {
        this.f24706g = i2;
    }

    public void setToTimeMinute(int i2) {
        this.f24707h = i2;
    }

    public DoNotDisturbInfo(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this.f24700a = i2;
        this.f24701b = i3;
        this.f24702c = i4;
        this.f24703d = i5;
        this.f24704e = i6;
        this.f24705f = i7;
        this.f24706g = i8;
        this.f24707h = i9;
    }
}
