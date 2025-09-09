package com.alipay.apmobilesecuritysdk.d;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class d {
    public static synchronized Map<String, String> a() {
        HashMap map;
        map = new HashMap();
        try {
            new com.alipay.apmobilesecuritysdk.c.b();
            map.put("AE16", "");
        } catch (Throwable unused) {
        }
        return map;
    }

    public static synchronized Map<String, String> a(Context context) {
        HashMap map;
        try {
            com.alipay.sdk.m.a0.d.a();
            com.alipay.sdk.m.a0.b.b();
            map = new HashMap();
            map.put("AE1", com.alipay.sdk.m.a0.d.b());
            StringBuilder sb = new StringBuilder();
            sb.append(com.alipay.sdk.m.a0.d.c() ? "1" : "0");
            map.put("AE2", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(com.alipay.sdk.m.a0.d.a(context) ? "1" : "0");
            map.put("AE3", sb2.toString());
            map.put("AE4", com.alipay.sdk.m.a0.d.d());
            map.put("AE5", com.alipay.sdk.m.a0.d.e());
            map.put("AE6", com.alipay.sdk.m.a0.d.f());
            map.put("AE7", com.alipay.sdk.m.a0.d.g());
            map.put("AE8", com.alipay.sdk.m.a0.d.h());
            map.put("AE9", com.alipay.sdk.m.a0.d.i());
            map.put("AE10", com.alipay.sdk.m.a0.d.j());
            map.put("AE11", com.alipay.sdk.m.a0.d.k());
            map.put("AE12", com.alipay.sdk.m.a0.d.l());
            map.put("AE13", com.alipay.sdk.m.a0.d.m());
            map.put("AE14", com.alipay.sdk.m.a0.d.n());
            map.put("AE15", com.alipay.sdk.m.a0.d.o());
            map.put("AE21", com.alipay.sdk.m.a0.b.g());
        } catch (Throwable th) {
            throw th;
        }
        return map;
    }
}
