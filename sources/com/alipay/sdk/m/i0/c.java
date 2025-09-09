package com.alipay.sdk.m.i0;

import android.text.TextUtils;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public String f9300a;

    /* renamed from: b, reason: collision with root package name */
    public Boolean f9301b;

    public void a(boolean z2) {
        this.f9301b = Boolean.valueOf(z2);
    }

    public void b(String str) {
        this.f9300a = str;
    }

    public boolean a() {
        return this.f9301b != null;
    }

    public boolean b() {
        Boolean bool = this.f9301b;
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return TextUtils.equals(this.f9300a, str);
    }
}
