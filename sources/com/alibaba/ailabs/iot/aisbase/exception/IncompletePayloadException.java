package com.alibaba.ailabs.iot.aisbase.exception;

/* loaded from: classes2.dex */
public class IncompletePayloadException extends Exception {

    /* renamed from: a, reason: collision with root package name */
    public int f8400a;

    /* renamed from: b, reason: collision with root package name */
    public int f8401b;

    public IncompletePayloadException(String str, int i2, int i3) {
        super(str);
        this.f8400a = i2;
        this.f8401b = i3;
    }

    public int getmCurrentLength() {
        return this.f8401b;
    }

    public int getmRequiredLength() {
        return this.f8400a;
    }

    public void setmCurrentLength(int i2) {
        this.f8401b = i2;
    }

    public void setmRequiredLength(int i2) {
        this.f8400a = i2;
    }
}
