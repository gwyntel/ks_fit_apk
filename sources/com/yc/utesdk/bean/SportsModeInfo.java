package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class SportsModeInfo implements Comparable<SportsModeInfo> {
    private int displayStatus;
    private int position;
    private int sportsMode;

    public SportsModeInfo() {
    }

    @Override // java.lang.Comparable
    public int compareTo(SportsModeInfo sportsModeInfo) {
        return getPosition() - sportsModeInfo.getPosition();
    }

    public int getDisplayStatus() {
        return this.displayStatus;
    }

    public int getPosition() {
        return this.position;
    }

    public int getSportsMode() {
        return this.sportsMode;
    }

    public void setPosition(int i2) {
        this.position = i2;
    }

    public void setSportsMode(int i2) {
        this.sportsMode = i2;
    }

    public SportsModeInfo(int i2) {
        this.sportsMode = i2;
    }

    public SportsModeInfo(int i2, int i3) {
        this.sportsMode = i2;
        this.position = i3;
    }

    public SportsModeInfo(int i2, int i3, int i4) {
        this.sportsMode = i2;
        this.displayStatus = i3;
        this.position = i4;
    }
}
