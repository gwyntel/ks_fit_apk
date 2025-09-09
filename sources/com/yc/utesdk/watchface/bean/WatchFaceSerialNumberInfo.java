package com.yc.utesdk.watchface.bean;

/* loaded from: classes4.dex */
public class WatchFaceSerialNumberInfo {
    private String bleID;
    private int displayIndex;

    public WatchFaceSerialNumberInfo() {
        this.bleID = "";
    }

    public String getBleID() {
        return this.bleID;
    }

    public int getDisplayIndex() {
        return this.displayIndex;
    }

    public void setBleID(String str) {
        this.bleID = str;
    }

    public void setDisplayIndex(int i2) {
        this.displayIndex = i2;
    }

    public WatchFaceSerialNumberInfo(int i2, String str) {
        this.displayIndex = i2;
        this.bleID = str;
    }
}
