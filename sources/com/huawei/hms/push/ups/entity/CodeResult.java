package com.huawei.hms.push.ups.entity;

/* loaded from: classes4.dex */
public class CodeResult {

    /* renamed from: a, reason: collision with root package name */
    private int f16737a;

    /* renamed from: b, reason: collision with root package name */
    private String f16738b;

    public CodeResult() {
    }

    public String getReason() {
        return this.f16738b;
    }

    public int getReturnCode() {
        return this.f16737a;
    }

    public void setReason(String str) {
        this.f16738b = str;
    }

    public void setReturnCode(int i2) {
        this.f16737a = i2;
    }

    public CodeResult(int i2) {
        this.f16737a = i2;
    }

    public CodeResult(int i2, String str) {
        this.f16737a = i2;
        this.f16738b = str;
    }
}
