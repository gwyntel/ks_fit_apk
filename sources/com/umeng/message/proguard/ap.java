package com.umeng.message.proguard;

import android.content.SharedPreferences;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class ap {

    /* renamed from: b, reason: collision with root package name */
    private static volatile ap f22783b;

    /* renamed from: a, reason: collision with root package name */
    public final SharedPreferences f22784a = x.a().getSharedPreferences("umeng_push", 0);

    private ap() {
    }

    public static ap a() {
        if (f22783b == null) {
            synchronized (ap.class) {
                try {
                    if (f22783b == null) {
                        f22783b = new ap();
                    }
                } finally {
                }
            }
        }
        return f22783b;
    }

    public final void a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.f22784a.edit().putString(str, str2).apply();
    }

    public final void a(String str) {
        if (this.f22784a.contains(str)) {
            this.f22784a.edit().remove(str).apply();
        }
    }
}
