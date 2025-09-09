package com.alipay.sdk.m.u;

import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public class j {

    /* renamed from: a, reason: collision with root package name */
    public static String f9808a;

    public static synchronized boolean a(Context context, String str) {
        boolean zContains;
        try {
            zContains = PreferenceManager.getDefaultSharedPreferences(context).contains(str);
        } catch (Throwable th) {
            e.a(th);
            zContains = false;
        }
        return zContains;
    }

    public static synchronized void b(Context context, String str) {
        try {
            PreferenceManager.getDefaultSharedPreferences(context).edit().remove(str).apply();
        } finally {
        }
    }

    public static synchronized String a(com.alipay.sdk.m.s.a aVar, Context context, String str, String str2) {
        String strA;
        try {
            try {
                String string = PreferenceManager.getDefaultSharedPreferences(context).getString(str, str2);
                strA = TextUtils.isEmpty(string) ? null : com.alipay.sdk.m.n.e.a(a(context), string, str);
                if (!TextUtils.isEmpty(string) && TextUtils.isEmpty(strA)) {
                    com.alipay.sdk.m.k.a.b(aVar, com.alipay.sdk.m.k.b.f9366m, com.alipay.sdk.m.k.b.F, String.format("%s,%s", str, string));
                }
            } catch (Exception e2) {
                e.a(e2);
            }
        } catch (Throwable th) {
            throw th;
        }
        return strA;
    }

    public static synchronized void b(com.alipay.sdk.m.s.a aVar, Context context, String str, String str2) {
        try {
            String strB = com.alipay.sdk.m.n.e.b(a(context), str2, str);
            if (!TextUtils.isEmpty(str2) && TextUtils.isEmpty(strB)) {
                com.alipay.sdk.m.k.a.b(aVar, com.alipay.sdk.m.k.b.f9366m, com.alipay.sdk.m.k.b.G, String.format("%s,%s", str, str2));
            }
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(str, strB).apply();
        } catch (Throwable th) {
            try {
                e.a(th);
            } finally {
            }
        }
    }

    public static String a(Context context) {
        String packageName;
        if (TextUtils.isEmpty(f9808a)) {
            try {
                packageName = context.getApplicationContext().getPackageName();
            } catch (Throwable th) {
                e.a(th);
                packageName = "";
            }
            f9808a = (packageName + "0000000000000000000000000000").substring(0, 24);
        }
        return f9808a;
    }
}
