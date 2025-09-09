package com.yc.utesdk.watchface.bean;

import android.graphics.Bitmap;

/* loaded from: classes4.dex */
public class CustomViewPositionInfo implements Comparable {
    public static final boolean DISPLAY_NO = false;
    public static final boolean DISPLAY_YES = true;
    public static final int NO_CHANGE = -1;

    /* renamed from: a, reason: collision with root package name */
    int f24946a;

    /* renamed from: b, reason: collision with root package name */
    boolean f24947b;

    /* renamed from: c, reason: collision with root package name */
    int f24948c;

    /* renamed from: d, reason: collision with root package name */
    int f24949d;

    /* renamed from: e, reason: collision with root package name */
    int f24950e;

    /* renamed from: f, reason: collision with root package name */
    int f24951f;

    /* renamed from: g, reason: collision with root package name */
    Bitmap f24952g;

    public CustomViewPositionInfo() {
        this.f24946a = -1;
        this.f24947b = true;
        this.f24948c = -1;
        this.f24949d = -1;
        this.f24950e = 0;
        this.f24951f = 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        int i2 = this.f24946a;
        int i3 = ((CustomViewPositionInfo) obj).f24946a;
        if (i2 > i3) {
            return 1;
        }
        return i2 < i3 ? -1 : 0;
    }

    public Bitmap getViewBitmap() {
        return this.f24952g;
    }

    public int getViewHeight() {
        return this.f24951f;
    }

    public int getViewType() {
        return this.f24946a;
    }

    public int getViewWith() {
        return this.f24950e;
    }

    public int getViewX() {
        return this.f24948c;
    }

    public int getViewY() {
        return this.f24949d;
    }

    public boolean isViewDisplay() {
        return this.f24947b;
    }

    public void setViewBitmap(Bitmap bitmap) {
        this.f24952g = bitmap;
    }

    public void setViewDisplay(boolean z2) {
        this.f24947b = z2;
    }

    public void setViewHeight(int i2) {
        this.f24951f = i2;
    }

    public void setViewType(int i2) {
        this.f24946a = i2;
    }

    public void setViewWith(int i2) {
        this.f24950e = i2;
    }

    public void setViewX(int i2) {
        this.f24948c = i2;
    }

    public void setViewY(int i2) {
        this.f24949d = i2;
    }

    public CustomViewPositionInfo(int i2, boolean z2, int i3, int i4) {
        this.f24950e = 0;
        this.f24951f = 0;
        this.f24946a = i2;
        this.f24947b = z2;
        this.f24948c = i3;
        this.f24949d = i4;
    }
}
