package com.umeng.commonsdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.analytics.pro.ay;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.service.UMGlobalContext;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: b, reason: collision with root package name */
    private static final String f22547b = "lastReqTime";

    /* renamed from: c, reason: collision with root package name */
    private static final int f22548c = 48;

    /* renamed from: d, reason: collision with root package name */
    private static final int f22549d = 1;

    /* renamed from: e, reason: collision with root package name */
    private static final int f22550e = 720;

    /* renamed from: f, reason: collision with root package name */
    private static final String f22551f = "iss";

    /* renamed from: g, reason: collision with root package name */
    private static final String f22552g = "sinr";

    /* renamed from: h, reason: collision with root package name */
    private static final String f22553h = "clean";

    /* renamed from: i, reason: collision with root package name */
    private static boolean f22554i;

    /* renamed from: j, reason: collision with root package name */
    private static int f22555j;

    /* renamed from: a, reason: collision with root package name */
    private static final String f22546a = ay.b().b(ay.f21391z);

    /* renamed from: k, reason: collision with root package name */
    private static Object f22556k = new Object();

    static {
        f22554i = false;
        f22555j = f22550e;
        Context appContext = UMGlobalContext.getAppContext();
        if (appContext != null) {
            String strImprintProperty = UMEnvelopeBuild.imprintProperty(appContext, f22551f, "");
            if (TextUtils.isEmpty(strImprintProperty) || !"1".equals(strImprintProperty)) {
                return;
            }
            synchronized (f22556k) {
                f22554i = true;
            }
            String strImprintProperty2 = UMEnvelopeBuild.imprintProperty(appContext, f22552g, "");
            if (TextUtils.isEmpty(strImprintProperty)) {
                f22555j = 48;
                return;
            }
            try {
                f22555j = a(Integer.parseInt(strImprintProperty2));
            } catch (Throwable unused) {
                f22555j = 48;
            }
        }
    }

    private static int a(int i2) {
        if (i2 > f22550e) {
            return f22550e;
        }
        if (i2 < 1) {
            return 1;
        }
        return i2;
    }

    public static long b(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(f22546a, 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(f22547b, 0L);
        }
        return 0L;
    }

    public static void c(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(f22546a, 0);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putBoolean("clean", true).commit();
        }
    }

    public static void d(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(f22546a, 0);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putBoolean("clean", false).commit();
        }
    }

    public static boolean e(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(f22546a, 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean("clean", false);
        }
        return false;
    }

    public static boolean a() {
        boolean z2;
        synchronized (f22556k) {
            z2 = f22554i;
        }
        return z2;
    }

    public static int a(Context context) {
        int i2;
        synchronized (f22556k) {
            i2 = f22555j;
        }
        return i2;
    }

    public static boolean a(long j2, long j3, int i2) {
        Date date = new Date(j3);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(j2));
        calendar.add(10, i2);
        return date.after(calendar.getTime());
    }

    public static void a(Context context, long j2) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(f22546a, 0);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putLong(f22547b, j2).commit();
        }
    }
}
