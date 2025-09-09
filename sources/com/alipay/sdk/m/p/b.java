package com.alipay.sdk.m.p;

import android.text.TextUtils;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    public final String f9602a;

    /* renamed from: b, reason: collision with root package name */
    public final String f9603b;

    public b(String str, String str2) {
        this.f9602a = str;
        this.f9603b = str2;
    }

    public String a() {
        return this.f9603b;
    }

    public String b() {
        return this.f9602a;
    }

    public JSONObject c() {
        if (TextUtils.isEmpty(this.f9603b)) {
            return null;
        }
        try {
            return new JSONObject(this.f9603b);
        } catch (Exception e2) {
            com.alipay.sdk.m.u.e.a(e2);
            return null;
        }
    }

    public String toString() {
        return String.format("<Letter envelop=%s body=%s>", this.f9602a, this.f9603b);
    }
}
