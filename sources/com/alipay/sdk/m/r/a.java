package com.alipay.sdk.m.r;

import android.text.TextUtils;

/* loaded from: classes2.dex */
public enum a {
    None("none"),
    WapPay("js://wappay"),
    Update("js://update"),
    OpenWeb("loc:openweb"),
    SetResult("loc:setResult"),
    Exit("loc:exit");


    /* renamed from: a, reason: collision with root package name */
    public String f9691a;

    a(String str) {
        this.f9691a = str;
    }

    public static a a(String str) {
        if (TextUtils.isEmpty(str)) {
            return None;
        }
        a aVar = None;
        for (a aVar2 : values()) {
            if (str.startsWith(aVar2.f9691a)) {
                return aVar2;
            }
        }
        return aVar;
    }
}
