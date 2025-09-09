package com.huawei.hms.activity.internal;

import android.content.Intent;

/* loaded from: classes.dex */
public class BusResponseResult {

    /* renamed from: a, reason: collision with root package name */
    private Intent f15737a;

    /* renamed from: b, reason: collision with root package name */
    private int f15738b;

    public int getCode() {
        return this.f15738b;
    }

    public Intent getIntent() {
        return this.f15737a;
    }

    public void setCode(int i2) {
        this.f15738b = i2;
    }

    public void setIntent(Intent intent) {
        this.f15737a = intent;
    }
}
