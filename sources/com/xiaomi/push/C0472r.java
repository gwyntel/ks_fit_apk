package com.xiaomi.push;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.umeng.commonsdk.statistics.AnalyticsConstants;

/* renamed from: com.xiaomi.push.r, reason: case insensitive filesystem */
/* loaded from: classes4.dex */
public class C0472r {

    /* renamed from: a, reason: collision with root package name */
    private static Context f24411a;

    /* renamed from: a, reason: collision with other field name */
    private static String f939a;

    public static void a(Context context) {
        f24411a = context.getApplicationContext();
    }

    /* renamed from: b, reason: collision with other method in class */
    public static boolean m688b() {
        try {
            return a(null, "miui.os.Build").getField("IS_GLOBAL_BUILD").getBoolean(Boolean.FALSE);
        } catch (ClassNotFoundException unused) {
            com.xiaomi.channel.commonutils.logger.b.d("miui.os.Build ClassNotFound");
            return false;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return false;
        }
    }

    private static String c() {
        String strA = q.a("ro.build.version.opporom", "");
        if (!TextUtils.isEmpty(strA) && !strA.startsWith("ColorOS_")) {
            f939a = "ColorOS_" + strA;
        }
        return f939a;
    }

    private static String d() {
        String strA = q.a("ro.vivo.os.version", "");
        if (!TextUtils.isEmpty(strA) && !strA.startsWith("FuntouchOS_")) {
            f939a = "FuntouchOS_" + strA;
        }
        return f939a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static Context m684a() {
        return f24411a;
    }

    public static int a() {
        try {
            Class<?> clsA = a(null, "miui.os.Build");
            if (clsA.getField("IS_STABLE_VERSION").getBoolean(null)) {
                return 3;
            }
            return clsA.getField("IS_DEVELOPMENT_VERSION").getBoolean(null) ? 2 : 1;
        } catch (Exception unused) {
            return 0;
        }
    }

    private static String b() {
        String strA = q.a(com.alipay.sdk.m.c.a.f9195a, "");
        f939a = strA;
        return strA;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m687a(Context context) {
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return false;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m686a() {
        return TextUtils.equals((String) bk.a("android.os.SystemProperties", TmpConstant.PROPERTY_IDENTIFIER_GET, "sys.boot_completed"), "1");
    }

    /* renamed from: a, reason: collision with other method in class */
    public static synchronized String m685a() {
        try {
            String str = f939a;
            if (str != null) {
                return str;
            }
            String strValueOf = Build.VERSION.INCREMENTAL;
            if (a() <= 0) {
                String strB = b();
                if (TextUtils.isEmpty(strB)) {
                    strB = c();
                    if (TextUtils.isEmpty(strB)) {
                        strB = d();
                        if (TextUtils.isEmpty(strB)) {
                            strValueOf = String.valueOf(q.a("ro.product.brand", AnalyticsConstants.SDK_TYPE) + OpenAccountUIConstants.UNDER_LINE + strValueOf);
                        }
                    }
                    strValueOf = strB;
                } else {
                    strValueOf = strB;
                }
            }
            f939a = strValueOf;
            return strValueOf;
        } catch (Throwable th) {
            throw th;
        }
    }

    public static Class<?> a(Context context, String str) throws ClassNotFoundException {
        if (str != null && str.trim().length() != 0) {
            boolean z2 = context != null;
            if (z2 && Build.VERSION.SDK_INT >= 29) {
                try {
                    return context.getClassLoader().loadClass(str);
                } catch (Throwable unused) {
                }
            }
            try {
                return Class.forName(str);
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.m91a(String.format("loadClass fail hasContext= %s, errMsg = %s", Boolean.valueOf(z2), th.getLocalizedMessage()));
                throw new ClassNotFoundException("loadClass fail ", th);
            }
        }
        throw new ClassNotFoundException("class is empty");
    }
}
