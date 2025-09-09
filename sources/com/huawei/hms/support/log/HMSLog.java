package com.huawei.hms.support.log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.AndroidException;
import com.huawei.hms.base.log.a;
import com.huawei.hms.base.log.b;

/* loaded from: classes4.dex */
public class HMSLog {

    /* renamed from: a, reason: collision with root package name */
    private static final b f18147a = new b();

    private static String a(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 16384);
                return "HMS-" + packageInfo.versionName + "(" + packageInfo.versionCode + ")";
            } catch (AndroidException | RuntimeException unused) {
            }
        }
        return "HMS-[unknown-version]";
    }

    public static void d(String str, String str2) {
        f18147a.a(3, str, str2);
    }

    public static void e(String str, String str2) {
        f18147a.a(6, str, str2);
    }

    public static void i(String str, String str2) {
        f18147a.a(4, str, str2);
    }

    public static void init(Context context, int i2, String str) {
        b bVar = f18147a;
        bVar.a(context, i2, str);
        bVar.a(str, "============================================================================\n====== " + a(context) + "\n============================================================================");
    }

    public static boolean isErrorEnable() {
        return f18147a.a(6);
    }

    public static boolean isInfoEnable() {
        return f18147a.a(4);
    }

    public static boolean isWarnEnable() {
        return f18147a.a(5);
    }

    public static void setExtLogger(HMSExtLogger hMSExtLogger, boolean z2) throws IllegalArgumentException {
        if (hMSExtLogger == null) {
            throw new IllegalArgumentException("extLogger is not able to be null");
        }
        a aVar = new a(hMSExtLogger);
        if (z2) {
            f18147a.a(aVar);
        } else {
            f18147a.a().a(aVar);
        }
    }

    public static void w(String str, String str2) {
        f18147a.a(5, str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        f18147a.b(6, str, str2, th);
    }

    public static void e(String str, long j2, String str2) {
        f18147a.a(6, str, "[" + j2 + "] " + str2);
    }

    public static void e(String str, long j2, String str2, Throwable th) {
        f18147a.b(6, str, "[" + j2 + "] " + str2, th);
    }
}
