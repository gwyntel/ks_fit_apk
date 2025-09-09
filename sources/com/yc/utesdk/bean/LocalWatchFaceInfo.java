package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class LocalWatchFaceInfo {

    /* renamed from: a, reason: collision with root package name */
    int f24760a;

    /* renamed from: b, reason: collision with root package name */
    int f24761b;

    /* renamed from: c, reason: collision with root package name */
    int f24762c;

    /* renamed from: d, reason: collision with root package name */
    int f24763d;

    /* renamed from: e, reason: collision with root package name */
    int f24764e;

    public LocalWatchFaceInfo() {
    }

    public int getCurrentWatchFaceIndex() {
        return this.f24764e;
    }

    public int getLocalWatchFaceCount() {
        return this.f24763d;
    }

    public int getWatchFaceHeight() {
        return this.f24761b;
    }

    public int getWatchFaceNumber() {
        return this.f24762c;
    }

    public int getWatchFaceWidth() {
        return this.f24760a;
    }

    public void setCurrentWatchFaceIndex(int i2) {
        this.f24764e = i2;
    }

    public void setLocalWatchFaceCount(int i2) {
        this.f24763d = i2;
    }

    public void setWatchFaceHeight(int i2) {
        this.f24761b = i2;
    }

    public void setWatchFaceNumber(int i2) {
        this.f24762c = i2;
    }

    public void setWatchFaceWidth(int i2) {
        this.f24760a = i2;
    }

    public LocalWatchFaceInfo(int i2, int i3, int i4, int i5, int i6) {
        this.f24760a = i2;
        this.f24761b = i3;
        this.f24762c = i4;
        this.f24763d = i5;
        this.f24764e = i6;
    }
}
