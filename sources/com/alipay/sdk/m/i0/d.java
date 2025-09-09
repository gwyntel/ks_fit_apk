package com.alipay.sdk.m.i0;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    public String f9302a;

    /* renamed from: b, reason: collision with root package name */
    public int f9303b;

    /* renamed from: c, reason: collision with root package name */
    public long f9304c = System.currentTimeMillis() + 86400000;

    public d(String str, int i2) {
        this.f9302a = str;
        this.f9303b = i2;
    }

    public String toString() {
        return "ValueData{value='" + this.f9302a + "', code=" + this.f9303b + ", expired=" + this.f9304c + '}';
    }
}
