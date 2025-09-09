package com.alibaba.sdk.android.httpdns;

/* loaded from: classes2.dex */
public class h extends Exception {

    /* renamed from: b, reason: collision with root package name */
    private int f8858b;

    public h(int i2, String str) {
        super(str);
        this.f8858b = i2;
    }

    public int getErrorCode() {
        return this.f8858b;
    }
}
