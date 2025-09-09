package com.yc.utesdk.watchface.close;

import android.graphics.Bitmap;

/* loaded from: classes4.dex */
public class BitmapInfo {

    /* renamed from: a, reason: collision with root package name */
    int f24954a;

    /* renamed from: b, reason: collision with root package name */
    int f24955b;
    private Bitmap bitmap;

    /* renamed from: c, reason: collision with root package name */
    int f24956c;

    /* renamed from: d, reason: collision with root package name */
    int f24957d;

    /* renamed from: e, reason: collision with root package name */
    int f24958e;

    public BitmapInfo(int i2, Bitmap bitmap, int i3, int i4, int i5, int i6) {
        this.f24954a = i2;
        this.bitmap = bitmap;
        this.f24955b = i3;
        this.f24956c = i4;
        this.f24957d = i5;
        this.f24958e = i6;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public int getPicHeight() {
        return this.f24956c;
    }

    public int getPicWidth() {
        return this.f24955b;
    }

    public int getType() {
        return this.f24954a;
    }

    public int getX() {
        return this.f24957d;
    }

    public int getY() {
        return this.f24958e;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setPicHeight(int i2) {
        this.f24956c = i2;
    }

    public void setPicWidth(int i2) {
        this.f24955b = i2;
    }

    public void setType(int i2) {
        this.f24954a = i2;
    }

    public void setX(int i2) {
        this.f24957d = i2;
    }

    public void setY(int i2) {
        this.f24958e = i2;
    }
}
