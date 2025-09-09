package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/* loaded from: classes4.dex */
public class dy {

    /* renamed from: a, reason: collision with root package name */
    private static int f23638a = 0;

    /* renamed from: a, reason: collision with other field name */
    private static boolean f298a = true;

    private static int a(boolean z2) {
        return z2 ? 1 : 0;
    }

    public static void b(Context context, long j2, boolean z2) {
        ah.a(context).a(new ea(context, j2, z2));
    }

    public static void c(Context context, long j2, boolean z2) {
        ah.a(context).a(new eb(context, j2, z2));
    }

    public static void d(Context context, long j2, boolean z2) {
        ah.a(context).a(new ec(context, j2, z2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void i(Context context, long j2, boolean z2) {
        int i2;
        try {
            dv.a("recordSendMsg start");
            int iA = a(z2);
            SharedPreferences sharedPreferencesM299a = m299a(context);
            long j3 = sharedPreferencesM299a.getLong(com.umeng.analytics.pro.f.f21694p, 0L);
            if (j3 <= 0) {
                a(context, sharedPreferencesM299a, j2, iA);
            }
            if (iA == 1) {
                i2 = sharedPreferencesM299a.getInt("on_up_count", 0) + 1;
                sharedPreferencesM299a.edit().putInt("on_up_count", i2).apply();
            } else {
                i2 = sharedPreferencesM299a.getInt("off_up_count", 0) + 1;
                sharedPreferencesM299a.edit().putInt("off_up_count", i2).apply();
            }
            a(context, j3, j2, i2, iA);
            dv.a("recordSendMsg complete");
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void j(Context context, long j2, boolean z2) {
        int i2;
        try {
            dv.a("recordReceiveMsg start");
            int iA = a(z2);
            SharedPreferences sharedPreferencesM299a = m299a(context);
            long j3 = sharedPreferencesM299a.getLong(com.umeng.analytics.pro.f.f21694p, 0L);
            if (j3 <= 0) {
                a(context, sharedPreferencesM299a, j2, iA);
            }
            if (iA == 1) {
                i2 = sharedPreferencesM299a.getInt("on_down_count", 0) + 1;
                sharedPreferencesM299a.edit().putInt("on_down_count", i2).apply();
            } else {
                i2 = sharedPreferencesM299a.getInt("off_down_count", 0) + 1;
                sharedPreferencesM299a.edit().putInt("off_down_count", i2).apply();
            }
            a(context, j3, j2, i2, iA);
            dv.a("recordReceiveMsg complete");
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void k(Context context, long j2, boolean z2) {
        int i2;
        try {
            dv.a("recordPing start");
            int iA = a(z2);
            SharedPreferences sharedPreferencesM299a = m299a(context);
            long j3 = sharedPreferencesM299a.getLong(com.umeng.analytics.pro.f.f21694p, 0L);
            if (j3 <= 0) {
                a(context, sharedPreferencesM299a, j2, iA);
            }
            if (iA == 1) {
                i2 = sharedPreferencesM299a.getInt("on_ping_count", 0) + 1;
                sharedPreferencesM299a.edit().putInt("on_ping_count", i2).apply();
            } else {
                i2 = sharedPreferencesM299a.getInt("off_ping_count", 0) + 1;
                sharedPreferencesM299a.edit().putInt("off_ping_count", i2).apply();
            }
            a(context, j3, j2, i2, iA);
            dv.a("recordPing complete");
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void l(Context context, long j2, boolean z2) {
        int i2;
        try {
            dv.a("recordPong start");
            int iA = a(z2);
            SharedPreferences sharedPreferencesM299a = m299a(context);
            long j3 = sharedPreferencesM299a.getLong(com.umeng.analytics.pro.f.f21694p, 0L);
            if (j3 <= 0) {
                a(context, sharedPreferencesM299a, j2, iA);
            }
            if (iA == 1) {
                i2 = sharedPreferencesM299a.getInt("on_pong_count", 0) + 1;
                sharedPreferencesM299a.edit().putInt("on_pong_count", i2).apply();
            } else {
                i2 = sharedPreferencesM299a.getInt("off_pong_count", 0) + 1;
                sharedPreferencesM299a.edit().putInt("off_pong_count", i2).apply();
            }
            a(context, j3, j2, i2, iA);
            dv.a("recordPong complete");
        } catch (Throwable th) {
            throw th;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static SharedPreferences m299a(Context context) {
        return context.getSharedPreferences("sp_power_stats", 0);
    }

    private static void b(Context context, long j2, int i2) {
        dv.a("reset");
        m299a(context).edit().clear().putLong(com.umeng.analytics.pro.f.f21694p, j2).putInt("current_screen_state", i2).putLong("current_screen_state_start_time", j2).putInt("xmsf_vc", a(context)).putInt("android_vc", Build.VERSION.SDK_INT).apply();
    }

    public static void a(Context context, long j2, boolean z2) {
        ah.a(context).a(new dz(context, j2, z2));
    }

    private static void a(Context context, SharedPreferences sharedPreferences, long j2, int i2) {
        dv.a("recordInit");
        sharedPreferences.edit().putLong(com.umeng.analytics.pro.f.f21694p, j2).putInt("current_screen_state", i2).putLong("current_screen_state_start_time", j2).putInt("xmsf_vc", a(context)).putInt("android_vc", Build.VERSION.SDK_INT).apply();
    }

    private static void a(Context context, long j2, long j3, int i2, int i3) {
        if (j2 > 0) {
            if (m301a(context) || i2 >= 1073741823 || j3 - j2 >= 86400000) {
                m299a(context).edit().putLong(com.umeng.analytics.pro.f.f21695q, j3).apply();
                a(context, j3, i3);
            }
        }
    }

    private static void a(Context context, long j2, int i2) {
        dv.a("upload");
        new dx().a(context, m300a(context));
        b(context, j2, i2);
    }

    /* renamed from: a, reason: collision with other method in class */
    private static dw m300a(Context context) {
        SharedPreferences sharedPreferencesM299a = m299a(context);
        dw dwVar = new dw();
        dwVar.a(sharedPreferencesM299a.getInt("off_up_count", 0));
        dwVar.b(sharedPreferencesM299a.getInt("off_down_count", 0));
        dwVar.c(sharedPreferencesM299a.getInt("off_ping_count", 0));
        dwVar.d(sharedPreferencesM299a.getInt("off_pong_count", 0));
        dwVar.a(sharedPreferencesM299a.getLong("off_duration", 0L));
        dwVar.e(sharedPreferencesM299a.getInt("on_up_count", 0));
        dwVar.f(sharedPreferencesM299a.getInt("on_down_count", 0));
        dwVar.g(sharedPreferencesM299a.getInt("on_ping_count", 0));
        dwVar.h(sharedPreferencesM299a.getInt("on_pong_count", 0));
        dwVar.b(sharedPreferencesM299a.getLong("on_duration", 0L));
        dwVar.c(sharedPreferencesM299a.getLong(com.umeng.analytics.pro.f.f21694p, 0L));
        dwVar.d(sharedPreferencesM299a.getLong(com.umeng.analytics.pro.f.f21695q, 0L));
        dwVar.i(sharedPreferencesM299a.getInt("xmsf_vc", 0));
        dwVar.j(sharedPreferencesM299a.getInt("android_vc", 0));
        return dwVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m301a(Context context) {
        boolean z2 = false;
        if (f298a) {
            f298a = false;
            SharedPreferences sharedPreferencesM299a = m299a(context);
            int i2 = sharedPreferencesM299a.getInt("xmsf_vc", 0);
            int i3 = sharedPreferencesM299a.getInt("android_vc", 0);
            if (i2 != 0 && i3 != 0 && (i2 != a(context) || i3 != Build.VERSION.SDK_INT)) {
                z2 = true;
            }
        }
        dv.a("isVcChanged = " + z2);
        return z2;
    }

    private static int a(Context context) {
        if (f23638a <= 0) {
            f23638a = j.b(context);
        }
        return f23638a;
    }
}
