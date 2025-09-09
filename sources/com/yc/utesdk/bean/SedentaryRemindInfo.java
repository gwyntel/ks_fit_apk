package com.yc.utesdk.bean;

import com.yc.utesdk.utils.open.SPUtil;

/* loaded from: classes4.dex */
public class SedentaryRemindInfo {
    public static final int defaultsValue = -1;
    public static final int switchStatusNo = 0;
    public static final int switchStatusYes = 1;

    /* renamed from: a, reason: collision with root package name */
    int f24773a;

    /* renamed from: b, reason: collision with root package name */
    int f24774b;

    /* renamed from: c, reason: collision with root package name */
    int f24775c;

    /* renamed from: d, reason: collision with root package name */
    int f24776d;

    /* renamed from: e, reason: collision with root package name */
    int f24777e;

    /* renamed from: f, reason: collision with root package name */
    int f24778f;

    /* renamed from: g, reason: collision with root package name */
    int f24779g;

    /* renamed from: h, reason: collision with root package name */
    int f24780h;

    /* renamed from: i, reason: collision with root package name */
    int f24781i;

    /* renamed from: j, reason: collision with root package name */
    int f24782j;

    /* renamed from: k, reason: collision with root package name */
    int f24783k;

    public SedentaryRemindInfo() {
        this.f24773a = -1;
        this.f24774b = -1;
        this.f24775c = -1;
        this.f24776d = -1;
        this.f24777e = -1;
        this.f24778f = -1;
        this.f24779g = -1;
        this.f24780h = -1;
        this.f24781i = -1;
        this.f24782j = -1;
        this.f24783k = -1;
    }

    public int getFromTimeHour() {
        if (this.f24774b == -1) {
            this.f24774b = SPUtil.getInstance().getSedentaryRemindFromTimeHour();
        }
        return this.f24774b;
    }

    public int getFromTimeMinute() {
        if (this.f24775c == -1) {
            this.f24775c = SPUtil.getInstance().getSedentaryRemindFromTimeMinute();
        }
        return this.f24775c;
    }

    public int getLunchBreakNoRemind() {
        if (this.f24779g == -1) {
            this.f24779g = SPUtil.getInstance().getSedentaryLunchBreakNoRemind();
        }
        return this.f24779g;
    }

    public int getNoRemindFromTimeHour() {
        if (this.f24780h == -1) {
            this.f24780h = SPUtil.getInstance().getSedentaryNoRemindFromTimeHour();
        }
        return this.f24780h;
    }

    public int getNoRemindFromTimeMinute() {
        if (this.f24781i == -1) {
            this.f24781i = SPUtil.getInstance().getSedentaryNoRemindFromTimeMinute();
        }
        return this.f24781i;
    }

    public int getNoRemindToTimeHour() {
        if (this.f24782j == -1) {
            this.f24782j = SPUtil.getInstance().getSedentaryNoRemindToTimeHour();
        }
        return this.f24782j;
    }

    public int getNoRemindToTimeMinute() {
        if (this.f24783k == -1) {
            this.f24783k = SPUtil.getInstance().getSedentaryNoRemindToTimeMinute();
        }
        return this.f24783k;
    }

    public int getRemindInterval() {
        if (this.f24778f == -1) {
            this.f24778f = SPUtil.getInstance().getSedentaryRemindInterval();
        }
        return this.f24778f;
    }

    public int getRemindSwitch() {
        if (this.f24773a == -1) {
            this.f24773a = SPUtil.getInstance().getSedentaryRemindSwitch();
        }
        return this.f24773a;
    }

    public int getToTimeHour() {
        if (this.f24776d == -1) {
            this.f24776d = SPUtil.getInstance().getSedentaryRemindToTimeHour();
        }
        return this.f24776d;
    }

    public int getToTimeMinute() {
        if (this.f24777e == -1) {
            this.f24777e = SPUtil.getInstance().getSedentaryRemindToTimeMinute();
        }
        return this.f24777e;
    }

    public void setFromTimeHour(int i2) {
        this.f24774b = i2;
    }

    public void setFromTimeMinute(int i2) {
        this.f24775c = i2;
    }

    public void setLunchBreakNoRemind(int i2) {
        this.f24779g = i2;
    }

    public void setNoRemindFromTimeHour(int i2) {
        this.f24780h = i2;
    }

    public void setNoRemindFromTimeMinute(int i2) {
        this.f24781i = i2;
    }

    public void setNoRemindToTimeHour(int i2) {
        this.f24782j = i2;
    }

    public void setNoRemindToTimeMinute(int i2) {
        this.f24783k = i2;
    }

    public void setRemindInterval(int i2) {
        this.f24778f = i2;
    }

    public void setRemindSwitch(int i2) {
        this.f24773a = i2;
    }

    public void setToTimeHour(int i2) {
        this.f24776d = i2;
    }

    public void setToTimeMinute(int i2) {
        this.f24777e = i2;
    }

    public SedentaryRemindInfo(int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.f24780h = -1;
        this.f24781i = -1;
        this.f24782j = -1;
        this.f24783k = -1;
        this.f24773a = i2;
        this.f24774b = i3;
        this.f24775c = i4;
        this.f24776d = i5;
        this.f24777e = i6;
        this.f24778f = i7;
        this.f24779g = i8;
    }

    public SedentaryRemindInfo(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
        this.f24773a = i2;
        this.f24774b = i3;
        this.f24775c = i4;
        this.f24776d = i5;
        this.f24777e = i6;
        this.f24778f = i7;
        this.f24779g = i8;
        this.f24780h = i9;
        this.f24781i = i10;
        this.f24782j = i11;
        this.f24783k = i12;
    }
}
