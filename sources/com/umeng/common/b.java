package com.umeng.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.pro.ay;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;

/* loaded from: classes4.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    private static b f22087a;

    /* renamed from: b, reason: collision with root package name */
    private static Context f22088b;

    /* renamed from: c, reason: collision with root package name */
    private static String f22089c;

    /* renamed from: d, reason: collision with root package name */
    private static final String f22090d = ay.b().b(ay.f21378m);

    private static final class a {

        /* renamed from: a, reason: collision with root package name */
        private static final b f22091a = new b();

        private a() {
        }
    }

    public static synchronized b a(Context context) {
        try {
            if (f22088b == null && context != null) {
                f22088b = context.getApplicationContext();
            }
            if (f22088b != null) {
                f22089c = context.getPackageName();
            }
        } catch (Throwable th) {
            throw th;
        }
        return a.f22091a;
    }

    private SharedPreferences e() {
        Context context = f22088b;
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences(f22090d + f22089c, 0);
    }

    public void b() {
        SharedPreferences sharedPreferencesE = e();
        if (sharedPreferencesE != null) {
            sharedPreferencesE.edit().remove("au_p").remove("au_u").commit();
        }
    }

    public String c() {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f22088b);
        if (sharedPreferences != null) {
            return sharedPreferences.getString("st", null);
        }
        return null;
    }

    public int d() {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f22088b);
        if (sharedPreferences != null) {
            return sharedPreferences.getInt("vt", 0);
        }
        return 0;
    }

    public void a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        SharedPreferences.Editor editorEdit = e().edit();
        editorEdit.putString("au_p", str);
        editorEdit.putString("au_u", str2);
        editorEdit.commit();
    }

    public String[] a() {
        SharedPreferences sharedPreferencesE = e();
        if (sharedPreferencesE != null) {
            String string = sharedPreferencesE.getString("au_p", null);
            String string2 = sharedPreferencesE.getString("au_u", null);
            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                return new String[]{string, string2};
            }
        }
        return null;
    }

    public void a(String str) {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f22088b);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString("st", str).commit();
        }
    }

    public void a(int i2) {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f22088b);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putInt("vt", i2).commit();
        }
    }

    public static String a(Context context, String str, String str2) {
        SharedPreferences sharedPreferences;
        if (context == null || (sharedPreferences = context.getApplicationContext().getSharedPreferences(str, 0)) == null) {
            return "";
        }
        return sharedPreferences.getString(str2, "");
    }

    public static void a(Context context, String str, String str2, String str3) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editorEdit;
        if (context == null || (sharedPreferences = context.getApplicationContext().getSharedPreferences(str, 0)) == null || (editorEdit = sharedPreferences.edit()) == null) {
            return;
        }
        editorEdit.putString(str2, str3);
        editorEdit.commit();
    }

    public static void a(Context context, String str) {
        SharedPreferences.Editor editorEdit = context.getApplicationContext().getSharedPreferences(str, 0).edit();
        editorEdit.remove(AnalyticsConfig.DEBUG_KEY);
        editorEdit.remove("period");
        editorEdit.remove("startTime");
        editorEdit.clear();
        editorEdit.commit();
    }
}
