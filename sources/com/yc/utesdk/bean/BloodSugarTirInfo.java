package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class BloodSugarTirInfo {
    private int bad;
    private int common;
    private int perfect;
    private int tir;

    public BloodSugarTirInfo() {
    }

    public int getBad() {
        return this.bad;
    }

    public int getCommon() {
        return this.common;
    }

    public int getPerfect() {
        return this.perfect;
    }

    public int getTir() {
        return this.tir;
    }

    public void setBad(int i2) {
        this.bad = i2;
    }

    public void setCommon(int i2) {
        this.common = i2;
    }

    public void setPerfect(int i2) {
        this.perfect = i2;
    }

    public void setTir(int i2) {
        this.tir = i2;
    }

    public BloodSugarTirInfo(int i2, int i3, int i4, int i5) {
        this.tir = i2;
        this.perfect = i3;
        this.common = i4;
        this.bad = i5;
    }
}
