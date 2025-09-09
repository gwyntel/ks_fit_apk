package com.yc.utesdk.bean;

import com.yc.utesdk.utils.open.SPUtil;

/* loaded from: classes4.dex */
public class WashHandsRemindInfo {
    public static final int defaultsValue = -1;
    public static final int switchStatusNo = 0;
    public static final int switchStatusYes = 1;

    /* renamed from: a, reason: collision with root package name */
    int f24844a;

    /* renamed from: b, reason: collision with root package name */
    int f24845b;

    /* renamed from: c, reason: collision with root package name */
    int f24846c;

    /* renamed from: d, reason: collision with root package name */
    int f24847d;

    /* renamed from: e, reason: collision with root package name */
    int f24848e;

    /* renamed from: f, reason: collision with root package name */
    int f24849f;

    /* renamed from: g, reason: collision with root package name */
    int f24850g;

    /* renamed from: h, reason: collision with root package name */
    int f24851h;

    /* renamed from: i, reason: collision with root package name */
    int f24852i;

    /* renamed from: j, reason: collision with root package name */
    int f24853j;

    /* renamed from: k, reason: collision with root package name */
    int f24854k;

    public WashHandsRemindInfo() {
        this.f24844a = -1;
        this.f24845b = -1;
        this.f24846c = -1;
        this.f24847d = -1;
        this.f24848e = -1;
        this.f24849f = -1;
        this.f24850g = -1;
        this.f24851h = -1;
        this.f24852i = -1;
        this.f24853j = -1;
        this.f24854k = -1;
    }

    public int getFromTimeHour() {
        if (this.f24845b == -1) {
            this.f24845b = SPUtil.getInstance().getWashHandsRemindFromTimeHour();
        }
        return this.f24845b;
    }

    public int getFromTimeMinute() {
        if (this.f24846c == -1) {
            this.f24846c = SPUtil.getInstance().getWashHandsRemindFromTimeMinute();
        }
        return this.f24846c;
    }

    public int getLunchBreakNoRemind() {
        if (this.f24850g == -1) {
            this.f24850g = SPUtil.getInstance().getWashHandsLunchBreakNoRemind();
        }
        return this.f24850g;
    }

    public int getNoRemindFromTimeHour() {
        if (this.f24851h == -1) {
            this.f24851h = SPUtil.getInstance().getWashHandsNoRemindFromTimeHour();
        }
        return this.f24851h;
    }

    public int getNoRemindFromTimeMinute() {
        if (this.f24852i == -1) {
            this.f24852i = SPUtil.getInstance().getWashHandsNoRemindFromTimeMinute();
        }
        return this.f24852i;
    }

    public int getNoRemindToTimeHour() {
        if (this.f24853j == -1) {
            this.f24853j = SPUtil.getInstance().getWashHandsNoRemindToTimeHour();
        }
        return this.f24853j;
    }

    public int getNoRemindToTimeMinute() {
        if (this.f24854k == -1) {
            this.f24854k = SPUtil.getInstance().getWashHandsNoRemindToTimeMinute();
        }
        return this.f24854k;
    }

    public int getRemindInterval() {
        if (this.f24849f == -1) {
            this.f24849f = SPUtil.getInstance().getWashHandsRemindInterval();
        }
        return this.f24849f;
    }

    public int getRemindSwitch() {
        if (this.f24844a == -1) {
            this.f24844a = SPUtil.getInstance().getWashHandsRemindSwitch();
        }
        return this.f24844a;
    }

    public int getToTimeHour() {
        if (this.f24847d == -1) {
            this.f24847d = SPUtil.getInstance().getWashHandsRemindToTimeHour();
        }
        return this.f24847d;
    }

    public int getToTimeMinute() {
        if (this.f24848e == -1) {
            this.f24848e = SPUtil.getInstance().getWashHandsRemindToTimeMinute();
        }
        return this.f24848e;
    }

    public void setFromTimeHour(int i2) {
        this.f24845b = i2;
    }

    public void setFromTimeMinute(int i2) {
        this.f24846c = i2;
    }

    public void setLunchBreakNoRemind(int i2) {
        this.f24850g = i2;
    }

    public void setNoRemindFromTimeHour(int i2) {
        this.f24851h = i2;
    }

    public void setNoRemindFromTimeMinute(int i2) {
        this.f24852i = i2;
    }

    public void setNoRemindToTimeHour(int i2) {
        this.f24853j = i2;
    }

    public void setNoRemindToTimeMinute(int i2) {
        this.f24854k = i2;
    }

    public void setRemindInterval(int i2) {
        this.f24849f = i2;
    }

    public void setRemindSwitch(int i2) {
        this.f24844a = i2;
    }

    public void setToTimeHour(int i2) {
        this.f24847d = i2;
    }

    public void setToTimeMinute(int i2) {
        this.f24848e = i2;
    }

    public WashHandsRemindInfo(int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.f24851h = -1;
        this.f24852i = -1;
        this.f24853j = -1;
        this.f24854k = -1;
        this.f24844a = i2;
        this.f24845b = i3;
        this.f24846c = i4;
        this.f24847d = i5;
        this.f24848e = i6;
        this.f24849f = i7;
        this.f24850g = i8;
    }

    public WashHandsRemindInfo(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
        this.f24844a = i2;
        this.f24845b = i3;
        this.f24846c = i4;
        this.f24847d = i5;
        this.f24848e = i6;
        this.f24849f = i7;
        this.f24850g = i8;
        this.f24851h = i9;
        this.f24852i = i10;
        this.f24853j = i11;
        this.f24854k = i12;
    }
}
