package com.alipay.sdk.m.g0;

import android.content.Context;
import com.alipay.sdk.m.f0.c;
import com.alipay.sdk.m.f0.d;

/* loaded from: classes2.dex */
public class b implements a {

    /* renamed from: a, reason: collision with root package name */
    public static a f9273a;

    /* renamed from: b, reason: collision with root package name */
    public static com.alipay.sdk.m.d0.a f9274b;

    @Override // com.alipay.sdk.m.g0.a
    public c a(d dVar) {
        return com.alipay.sdk.m.f0.b.a(f9274b.a(com.alipay.sdk.m.f0.b.a(dVar)));
    }

    @Override // com.alipay.sdk.m.g0.a
    public boolean logCollect(String str) {
        return f9274b.logCollect(str);
    }

    public static a a(Context context, String str) {
        if (context == null) {
            return null;
        }
        if (f9273a == null) {
            f9274b = com.alipay.sdk.m.d0.d.a(context, str);
            f9273a = new b();
        }
        return f9273a;
    }
}
