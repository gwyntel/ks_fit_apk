package com.xiaomi.accountsdk.diagnosis.e;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes4.dex */
public class a {
    public static boolean a(Context context) {
        return b(context).getBoolean("diagnosis_enabled", false);
    }

    private static SharedPreferences b(Context context) {
        return context.getSharedPreferences("passport_diagnosis", 4);
    }

    public static boolean a(Context context, boolean z2) {
        return b(context).edit().putBoolean("diagnosis_enabled", z2).commit();
    }
}
