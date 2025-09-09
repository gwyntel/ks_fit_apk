package com.umeng.analytics.pro;

import android.os.Build;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes4.dex */
public class ba {

    /* renamed from: a, reason: collision with root package name */
    private static final String f21398a = "ro.build.version.emui";

    /* renamed from: b, reason: collision with root package name */
    private static final String f21399b = "hw_sc.build.platform.version";

    public static boolean a() throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return !TextUtils.isEmpty((String) cls.getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class, String.class).invoke(cls, "ro.build.flyme.version", ""));
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean b() {
        return f() && !g();
    }

    public static boolean c() {
        return f() && g();
    }

    public static boolean d() {
        String str = Build.BRAND;
        if (!str.equalsIgnoreCase("huawei") && !str.equalsIgnoreCase("honor") && !str.equalsIgnoreCase("华为")) {
            String strA = a("ro.build.version.emui");
            String strA2 = a("hw_sc.build.platform.version");
            if (TextUtils.isEmpty(strA) && TextUtils.isEmpty(strA2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean e() {
        return !TextUtils.isEmpty(a("ro.coolos.version"));
    }

    private static boolean f() {
        return Build.MANUFACTURER.equalsIgnoreCase("HONOR");
    }

    private static boolean g() {
        return !TextUtils.isEmpty(a("ro.build.version.emui"));
    }

    private static String a(String str) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getDeclaredMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class).invoke(null, str);
        } catch (Throwable unused) {
            return "";
        }
    }
}
