package com.aliyun.alink.linksdk.channel.mobile.b;

import android.text.TextUtils;

/* loaded from: classes2.dex */
public class l {

    /* renamed from: a, reason: collision with root package name */
    public String f11051a;

    /* renamed from: b, reason: collision with root package name */
    public String f11052b;

    /* renamed from: c, reason: collision with root package name */
    public String f11053c;

    /* renamed from: d, reason: collision with root package name */
    public String f11054d;

    public l(String str, String str2, String str3) {
        this.f11052b = str;
        this.f11053c = str2;
        this.f11054d = str3;
    }

    public boolean a() {
        return (TextUtils.isEmpty(this.f11052b) || TextUtils.isEmpty(this.f11053c) || TextUtils.isEmpty(this.f11054d)) ? false : true;
    }
}
