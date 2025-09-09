package com.yc.utesdk.bean;

import com.yc.utesdk.utils.open.SPUtil;

/* loaded from: classes4.dex */
public class BrightScreenInfo {
    public static final int defaultsValue = -1;
    public static final int switchStatusNo = 0;
    public static final int switchStatusYes = 1;

    /* renamed from: a, reason: collision with root package name */
    int f24690a;

    /* renamed from: b, reason: collision with root package name */
    int f24691b;

    /* renamed from: c, reason: collision with root package name */
    int f24692c;

    /* renamed from: d, reason: collision with root package name */
    int f24693d;

    /* renamed from: e, reason: collision with root package name */
    int f24694e;

    public BrightScreenInfo() {
        this.f24690a = -1;
        this.f24691b = -1;
        this.f24692c = -1;
        this.f24693d = -1;
        this.f24694e = -1;
    }

    public int getFromTimeHour() {
        if (this.f24691b == -1) {
            this.f24691b = SPUtil.getInstance().getBrightScreenFromHour();
        }
        return this.f24691b;
    }

    public int getFromTimeMinute() {
        if (this.f24692c == -1) {
            this.f24692c = SPUtil.getInstance().getBrightScreenFromMinute();
        }
        return this.f24692c;
    }

    public int getScreenSwitch() {
        if (this.f24690a == -1) {
            this.f24690a = SPUtil.getInstance().getBrightScreenSwitch();
        }
        return this.f24690a;
    }

    public int getToTimeHour() {
        if (this.f24693d == -1) {
            this.f24693d = SPUtil.getInstance().getBrightScreenToHour();
        }
        return this.f24693d;
    }

    public int getToTimeMinute() {
        if (this.f24694e == -1) {
            this.f24694e = SPUtil.getInstance().getBrightScreenToMinute();
        }
        return this.f24694e;
    }

    public void setFromTimeHour(int i2) {
        this.f24691b = i2;
    }

    public void setFromTimeMinute(int i2) {
        this.f24692c = i2;
    }

    public void setScreenSwitch(int i2) {
        this.f24690a = i2;
    }

    public void setToTimeHour(int i2) {
        this.f24693d = i2;
    }

    public void setToTimeMinute(int i2) {
        this.f24694e = i2;
    }

    public BrightScreenInfo(int i2, int i3, int i4, int i5, int i6) {
        this.f24690a = i2;
        this.f24691b = i3;
        this.f24692c = i4;
        this.f24693d = i5;
        this.f24694e = i6;
    }
}
