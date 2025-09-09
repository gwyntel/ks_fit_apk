package com.alibaba.sdk.android.httpdns;

import org.json.JSONObject;

/* loaded from: classes2.dex */
class g {

    /* renamed from: b, reason: collision with root package name */
    private int f8856b;

    /* renamed from: e, reason: collision with root package name */
    private String f8857e;

    g(int i2, String str) {
        this.f8856b = i2;
        this.f8857e = new JSONObject(str).getString("code");
    }

    public String b() {
        return this.f8857e;
    }

    public int getErrorCode() {
        return this.f8856b;
    }
}
