package com.xiaomi.push;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import java.util.Map;

/* loaded from: classes4.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    private static a f23782a;

    public interface a {
        Map<String, String> a(Context context, String str);

        /* renamed from: a, reason: collision with other method in class */
        boolean m428a(Context context, String str);

        boolean b(Context context, String str);
    }

    public enum b {
        UNKNOWN(0),
        ALLOWED(1),
        NOT_ALLOWED(2);


        /* renamed from: a, reason: collision with other field name */
        private final int f446a;

        b(int i2) {
            this.f446a = i2;
        }

        public int a() {
            return this.f446a;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m422a(Context context, String str) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 16384);
        } catch (Exception unused) {
            packageInfo = null;
        }
        return packageInfo != null ? packageInfo.versionName : "1.0";
    }

    /* renamed from: b, reason: collision with other method in class */
    public static boolean m427b(Context context, String str) {
        a aVar = f23782a;
        return aVar != null && aVar.b(context, str);
    }

    public static boolean c(Context context, String str) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            packageInfo = null;
        }
        return packageInfo != null;
    }

    public static boolean d(Context context, String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }

    public static boolean e(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            if (Settings.Secure.getInt(context.getContentResolver(), "freeform_window_state", -1) >= 0) {
                return str.equals(Settings.Secure.getString(context.getContentResolver(), "freeform_package_name"));
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public static String m426b(Context context, String str) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            return (packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null) ? str : packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (PackageManager.NameNotFoundException unused) {
            return str;
        }
    }

    public static int a(Context context, String str) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 16384);
        } catch (Exception unused) {
            packageInfo = null;
        }
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 0;
    }

    public static int a(Context context) throws PackageManager.NameNotFoundException {
        Bundle bundle;
        if (context == null) {
            return 0;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo("com.android.systemui", 128);
            if (applicationInfo == null || (bundle = applicationInfo.metaData) == null) {
                return 0;
            }
            return bundle.getInt("SupportForPushVersionCode");
        } catch (PackageManager.NameNotFoundException unused) {
            return 0;
        }
    }

    public static int b(Context context, String str) {
        ApplicationInfo applicationInfoM420a = m420a(context, str);
        if (applicationInfoM420a == null) {
            return 0;
        }
        int i2 = applicationInfoM420a.icon;
        return i2 == 0 ? applicationInfoM420a.logo : i2;
    }

    @TargetApi(19)
    public static b a(Context context, String str, boolean z2) {
        ApplicationInfo applicationInfo;
        b bVarA;
        b bVar;
        if (context != null && !TextUtils.isEmpty(str)) {
            try {
                if (str.equals(context.getPackageName())) {
                    applicationInfo = context.getApplicationInfo();
                } else {
                    applicationInfo = context.getPackageManager().getApplicationInfo(str, 0);
                }
                bVarA = a(context, applicationInfo);
                bVar = b.UNKNOWN;
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.m91a("get app op error " + th);
            }
            if (bVarA != bVar) {
                return bVarA;
            }
            Integer num = (Integer) bk.a((Class<? extends Object>) AppOpsManager.class, "OP_POST_NOTIFICATION");
            if (num == null) {
                return bVar;
            }
            Integer num2 = (Integer) bk.a(context.getSystemService("appops"), "checkOpNoThrow", num, Integer.valueOf(applicationInfo.uid), str);
            int i2 = (Integer) bk.a((Class<? extends Object>) AppOpsManager.class, "MODE_ALLOWED");
            int i3 = (Integer) bk.a((Class<? extends Object>) AppOpsManager.class, "MODE_IGNORED");
            com.xiaomi.channel.commonutils.logger.b.b(String.format("get app mode %s|%s|%s", num2, i2, i3));
            if (i2 == null) {
                i2 = 0;
            }
            if (i3 == null) {
                i3 = 1;
            }
            if (num2 != null) {
                return z2 ? !num2.equals(i3) ? b.ALLOWED : b.NOT_ALLOWED : num2.equals(i2) ? b.ALLOWED : b.NOT_ALLOWED;
            }
            return b.UNKNOWN;
        }
        return b.UNKNOWN;
    }

    private static b a(Context context, ApplicationInfo applicationInfo) {
        Object systemService;
        Boolean boolValueOf;
        int i2 = Build.VERSION.SDK_INT;
        if (applicationInfo != null && i2 >= 24) {
            try {
                if (applicationInfo.packageName.equals(context.getPackageName())) {
                    boolValueOf = Boolean.valueOf(((NotificationManager) context.getSystemService("notification")).areNotificationsEnabled());
                } else {
                    if (i2 >= 29) {
                        systemService = bk.a(context.getSystemService("notification"), "getService", new Object[0]);
                    } else {
                        systemService = context.getSystemService(AlinkConstants.KEY_SECURITY);
                    }
                    boolValueOf = systemService != null ? (Boolean) bk.b(systemService, "areNotificationsEnabledForPackage", applicationInfo.packageName, Integer.valueOf(applicationInfo.uid)) : null;
                }
                if (boolValueOf != null) {
                    return boolValueOf.booleanValue() ? b.ALLOWED : b.NOT_ALLOWED;
                }
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.m91a("are notifications enabled error " + e2);
            }
            return b.UNKNOWN;
        }
        return b.UNKNOWN;
    }

    public static void a(Context context, ApplicationInfo applicationInfo, boolean z2) {
        Object systemService;
        int i2 = Build.VERSION.SDK_INT;
        if (b.ALLOWED != a(context, applicationInfo)) {
            try {
                if (i2 >= 29) {
                    systemService = bk.a(context.getSystemService("notification"), "getService", new Object[0]);
                } else {
                    systemService = context.getSystemService(AlinkConstants.KEY_SECURITY);
                }
                if (systemService != null) {
                    bk.b(systemService, "setNotificationsEnabledForPackage", applicationInfo.packageName, Integer.valueOf(applicationInfo.uid), Boolean.valueOf(z2));
                }
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.m91a("set notifications enabled error " + e2);
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m425a(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        if (!j.m549a()) {
            return context.getPackageName().equals(str);
        }
        a aVar = f23782a;
        return aVar != null && aVar.m428a(context, str);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m424a(Context context) {
        String strA = a();
        if (TextUtils.isEmpty(strA) || context == null) {
            return false;
        }
        return strA.equals(context.getPackageName());
    }

    public static String a() {
        String processName;
        if (Build.VERSION.SDK_INT >= 28) {
            processName = Application.getProcessName();
        } else {
            processName = (String) bk.a("android.app.ActivityThread", "currentProcessName", new Object[0]);
        }
        return !TextUtils.isEmpty(processName) ? processName : "";
    }

    /* renamed from: a, reason: collision with other method in class */
    private static ApplicationInfo m420a(Context context, String str) {
        if (str.equals(context.getPackageName())) {
            return context.getApplicationInfo();
        }
        try {
            return context.getPackageManager().getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            com.xiaomi.channel.commonutils.logger.b.m91a("not found app info " + str);
            return null;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static Drawable m421a(Context context, String str) {
        ApplicationInfo applicationInfoM420a = m420a(context, str);
        Drawable drawableLoadIcon = null;
        if (applicationInfoM420a != null) {
            try {
                drawableLoadIcon = applicationInfoM420a.loadIcon(context.getPackageManager());
                if (drawableLoadIcon == null) {
                    drawableLoadIcon = applicationInfoM420a.loadLogo(context.getPackageManager());
                }
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.m91a("get app icon drawable failed, " + e2);
            }
        }
        return drawableLoadIcon != null ? drawableLoadIcon : new ColorDrawable(0);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static Map<String, String> m423a(Context context, String str) {
        a aVar = f23782a;
        if (aVar == null) {
            return null;
        }
        return aVar.a(context, str);
    }
}
