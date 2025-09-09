package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static SharedPreferences f8794a = null;

    /* renamed from: a, reason: collision with other field name */
    private static boolean f4a = true;

    /* renamed from: b, reason: collision with root package name */
    private static boolean f8795b = true;

    static void a(Context context) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("httpdns_config_enable", 0);
            f8794a = sharedPreferences;
            if (sharedPreferences != null) {
                f4a = sharedPreferences.getBoolean("key_enable", true);
            }
        }
    }

    public static void b(boolean z2) {
        f8795b = z2;
    }

    public static void a(boolean z2) {
        f4a = z2;
        SharedPreferences sharedPreferences = f8794a;
        if (sharedPreferences != null) {
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putBoolean("key_enable", z2);
            editorEdit.apply();
        }
    }

    public static boolean a() {
        return f4a && f8795b;
    }
}
