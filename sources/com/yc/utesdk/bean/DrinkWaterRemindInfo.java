package com.yc.utesdk.bean;

import com.yc.utesdk.utils.open.SPUtil;

/* loaded from: classes4.dex */
public class DrinkWaterRemindInfo {
    public static final int defaultsValue = -1;
    public static final int switchStatusNo = 0;
    public static final int switchStatusYes = 1;

    /* renamed from: a, reason: collision with root package name */
    int f24708a;

    /* renamed from: b, reason: collision with root package name */
    int f24709b;

    /* renamed from: c, reason: collision with root package name */
    int f24710c;

    /* renamed from: d, reason: collision with root package name */
    int f24711d;

    /* renamed from: e, reason: collision with root package name */
    int f24712e;

    /* renamed from: f, reason: collision with root package name */
    int f24713f;

    /* renamed from: g, reason: collision with root package name */
    int f24714g;

    /* renamed from: h, reason: collision with root package name */
    int f24715h;

    /* renamed from: i, reason: collision with root package name */
    int f24716i;

    /* renamed from: j, reason: collision with root package name */
    int f24717j;

    /* renamed from: k, reason: collision with root package name */
    int f24718k;

    public DrinkWaterRemindInfo() {
        this.f24708a = -1;
        this.f24709b = -1;
        this.f24710c = -1;
        this.f24711d = -1;
        this.f24712e = -1;
        this.f24713f = -1;
        this.f24714g = -1;
        this.f24715h = -1;
        this.f24716i = -1;
        this.f24717j = -1;
        this.f24718k = -1;
    }

    public int getFromTimeHour() {
        if (this.f24709b == -1) {
            this.f24709b = SPUtil.getInstance().getDrinkWaterRemindFromTimeHour();
        }
        return this.f24709b;
    }

    public int getFromTimeMinute() {
        if (this.f24710c == -1) {
            this.f24710c = SPUtil.getInstance().getDrinkWaterRemindFromTimeMinute();
        }
        return this.f24710c;
    }

    public int getLunchBreakNoRemind() {
        if (this.f24714g == -1) {
            this.f24714g = SPUtil.getInstance().getDrinkWaterLunchBreakNoRemind();
        }
        return this.f24714g;
    }

    public int getNoRemindFromTimeHour() {
        if (this.f24715h == -1) {
            this.f24715h = SPUtil.getInstance().getDrinkWaterNoRemindFromTimeHour();
        }
        return this.f24715h;
    }

    public int getNoRemindFromTimeMinute() {
        if (this.f24716i == -1) {
            this.f24716i = SPUtil.getInstance().getDrinkWaterNoRemindFromTimeMinute();
        }
        return this.f24716i;
    }

    public int getNoRemindToTimeHour() {
        if (this.f24717j == -1) {
            this.f24717j = SPUtil.getInstance().getDrinkWaterNoRemindToTimeHour();
        }
        return this.f24717j;
    }

    public int getNoRemindToTimeMinute() {
        if (this.f24718k == -1) {
            this.f24718k = SPUtil.getInstance().getDrinkWaterNoRemindToTimeMinute();
        }
        return this.f24718k;
    }

    public int getRemindInterval() {
        if (this.f24713f == -1) {
            this.f24713f = SPUtil.getInstance().getDrinkWaterRemindInterval();
        }
        return this.f24713f;
    }

    public int getRemindSwitch() {
        if (this.f24708a == -1) {
            this.f24708a = SPUtil.getInstance().getDrinkWaterRemindSwitch();
        }
        return this.f24708a;
    }

    public int getToTimeHour() {
        if (this.f24711d == -1) {
            this.f24711d = SPUtil.getInstance().getDrinkWaterRemindToTimeHour();
        }
        return this.f24711d;
    }

    public int getToTimeMinute() {
        if (this.f24712e == -1) {
            this.f24712e = SPUtil.getInstance().getDrinkWaterRemindToTimeMinute();
        }
        return this.f24712e;
    }

    public void setFromTimeHour(int i2) {
        this.f24709b = i2;
    }

    public void setFromTimeMinute(int i2) {
        this.f24710c = i2;
    }

    public void setLunchBreakNoRemind(int i2) {
        this.f24714g = i2;
    }

    public void setNoRemindFromTimeHour(int i2) {
        this.f24715h = i2;
    }

    public void setNoRemindFromTimeMinute(int i2) {
        this.f24716i = i2;
    }

    public void setNoRemindToTimeHour(int i2) {
        this.f24717j = i2;
    }

    public void setNoRemindToTimeMinute(int i2) {
        this.f24718k = i2;
    }

    public void setRemindInterval(int i2) {
        this.f24713f = i2;
    }

    public void setRemindSwitch(int i2) {
        this.f24708a = i2;
    }

    public void setToTimeHour(int i2) {
        this.f24711d = i2;
    }

    public void setToTimeMinute(int i2) {
        this.f24712e = i2;
    }

    public DrinkWaterRemindInfo(int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.f24715h = -1;
        this.f24716i = -1;
        this.f24717j = -1;
        this.f24718k = -1;
        this.f24708a = i2;
        this.f24709b = i3;
        this.f24710c = i4;
        this.f24711d = i5;
        this.f24712e = i6;
        this.f24713f = i7;
        this.f24714g = i8;
    }

    public DrinkWaterRemindInfo(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
        this.f24708a = i2;
        this.f24709b = i3;
        this.f24710c = i4;
        this.f24711d = i5;
        this.f24712e = i6;
        this.f24713f = i7;
        this.f24714g = i8;
        this.f24715h = i9;
        this.f24716i = i10;
        this.f24717j = i11;
        this.f24718k = i12;
    }
}
