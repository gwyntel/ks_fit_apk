package com.yc.utesdk.bean;

import com.yc.utesdk.utils.open.SPUtil;

/* loaded from: classes4.dex */
public class MultiSportHeartRateWarningInfo {
    public static final int defaultsValue = -1;
    public static final int switchStatusNo = 0;
    public static final int switchStatusYes = 1;

    /* renamed from: a, reason: collision with root package name */
    int f24769a;

    /* renamed from: b, reason: collision with root package name */
    int f24770b;

    /* renamed from: c, reason: collision with root package name */
    int f24771c;

    /* renamed from: d, reason: collision with root package name */
    int f24772d;

    public MultiSportHeartRateWarningInfo() {
        this.f24769a = -1;
        this.f24770b = -1;
        this.f24771c = -1;
        this.f24772d = -1;
    }

    public int getHighestHeartRate() {
        if (this.f24770b == -1) {
            this.f24770b = SPUtil.getInstance().getMultiSportHighestHeartRate();
        }
        return this.f24770b;
    }

    public int getHighestSwitch() {
        if (this.f24769a == -1) {
            this.f24769a = SPUtil.getInstance().getMultiSportHighestHeartRateSwitch();
        }
        return this.f24769a;
    }

    public int getLowestHeartRate() {
        if (this.f24772d == -1) {
            this.f24772d = SPUtil.getInstance().getMultiSportLowestHeartRate();
        }
        return this.f24772d;
    }

    public int getLowestSwitch() {
        if (this.f24771c == -1) {
            this.f24771c = SPUtil.getInstance().getMultiSportLowestHeartRateSwitch();
        }
        return this.f24771c;
    }

    public void setHighestHeartRate(int i2) {
        this.f24770b = i2;
    }

    public void setHighestSwitch(int i2) {
        this.f24769a = i2;
    }

    public void setLowestHeartRate(int i2) {
        this.f24772d = i2;
    }

    public void setLowestSwitch(int i2) {
        this.f24771c = i2;
    }

    public MultiSportHeartRateWarningInfo(int i2, int i3, int i4, int i5) {
        this.f24769a = i2;
        this.f24770b = i3;
        this.f24771c = i4;
        this.f24772d = i5;
    }
}
