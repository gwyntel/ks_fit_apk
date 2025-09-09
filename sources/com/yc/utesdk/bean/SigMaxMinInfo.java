package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class SigMaxMinInfo {
    private int mIdx;
    private double mValue;

    public SigMaxMinInfo() {
    }

    public int getIdx() {
        return this.mIdx;
    }

    public double getValue() {
        return this.mValue;
    }

    public SigMaxMinInfo(double d2, int i2) {
        this.mValue = d2;
        this.mIdx = i2;
    }
}
