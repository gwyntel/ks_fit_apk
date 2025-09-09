package com.alipay.sdk.m.u;

import android.content.Context;
import android.net.NetworkInfo;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: b, reason: collision with root package name */
    public static final String f9759b = "00:00:00:00:00:00";

    /* renamed from: c, reason: collision with root package name */
    public static c f9760c;

    /* renamed from: a, reason: collision with root package name */
    public String f9761a;

    public c(Context context) {
        try {
            try {
                String macAddress = com.alipay.sdk.m.w.b.d(null, context).getMacAddress();
                this.f9761a = macAddress;
                if (!TextUtils.isEmpty(macAddress)) {
                    return;
                }
            } catch (Exception e2) {
                e.a(e2);
                if (!TextUtils.isEmpty(this.f9761a)) {
                    return;
                }
            }
            this.f9761a = "00:00:00:00:00:00";
        } catch (Throwable th) {
            if (TextUtils.isEmpty(this.f9761a)) {
                this.f9761a = "00:00:00:00:00:00";
            }
            throw th;
        }
    }

    public String a() {
        String str = b() + "|";
        String strC = c();
        if (TextUtils.isEmpty(strC)) {
            return str + "000000000000000";
        }
        return str + strC;
    }

    public String b() {
        return "000000000000000";
    }

    public String c() {
        return "000000000000000";
    }

    public String d() {
        return this.f9761a;
    }

    public static c b(Context context) {
        if (f9760c == null) {
            f9760c = new c(context);
        }
        return f9760c;
    }

    public static String c(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getResources().getConfiguration().locale.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static g d(Context context) {
        try {
            NetworkInfo networkInfoA = com.alipay.sdk.m.w.b.a(null, context);
            return (networkInfoA == null || networkInfoA.getType() != 0) ? (networkInfoA == null || networkInfoA.getType() != 1) ? g.NONE : g.WIFI : g.a(networkInfoA.getSubtype());
        } catch (Exception unused) {
            return g.NONE;
        }
    }

    public static String a(Context context) {
        return b(context).a().substring(0, 8);
    }
}
