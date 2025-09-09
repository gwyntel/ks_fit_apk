package com.alibaba.sdk.android.utils;

import java.util.Map;

/* loaded from: classes2.dex */
public class SdkInfo {

    /* renamed from: a, reason: collision with root package name */
    String f8940a;

    /* renamed from: b, reason: collision with root package name */
    String f8941b;

    /* renamed from: c, reason: collision with root package name */
    String f8942c;

    /* renamed from: c, reason: collision with other field name */
    Map<String, String> f44c;

    public SdkInfo setAppKey(String str) {
        this.f8942c = str;
        return this;
    }

    public SdkInfo setExt(Map<String, String> map) {
        this.f44c = map;
        return this;
    }

    public SdkInfo setSdkId(String str) {
        this.f8940a = str;
        return this;
    }

    public SdkInfo setSdkVersion(String str) {
        this.f8941b = str;
        return this;
    }
}
