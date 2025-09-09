package com.huawei.hms.push.ups.entity;

/* loaded from: classes4.dex */
public class TokenResult extends CodeResult {

    /* renamed from: c, reason: collision with root package name */
    private String f16739c;

    public TokenResult() {
    }

    public String getToken() {
        return this.f16739c;
    }

    public void setToken(String str) {
        this.f16739c = str;
    }

    public TokenResult(int i2) {
        super(i2);
    }

    public TokenResult(int i2, String str) {
        super(i2, str);
    }

    public TokenResult(String str) {
        this.f16739c = str;
    }
}
