package com.yc.utesdk.watchface.bean;

/* loaded from: classes4.dex */
public class AppLocalCustomWatchFaceInfo {
    private String assetPath;
    private String dpi;
    private int watchFaceShapeType;

    public AppLocalCustomWatchFaceInfo() {
    }

    public String getAssetPath() {
        return this.assetPath;
    }

    public String getDpi() {
        return this.dpi;
    }

    public int getWatchFaceShapeType() {
        return this.watchFaceShapeType;
    }

    public void setAssetPath(String str) {
        this.assetPath = str;
    }

    public void setDpi(String str) {
        this.dpi = str;
    }

    public void setWatchFaceShapeType(int i2) {
        this.watchFaceShapeType = i2;
    }

    public AppLocalCustomWatchFaceInfo(int i2, String str, String str2) {
        this.watchFaceShapeType = i2;
        this.dpi = str;
        this.assetPath = str2;
    }
}
