package com.huawei.hms.hatool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.internal.AnalyticsEvents;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public abstract class o {

    private static class a extends Exception {
        a(String str) {
            super(str);
        }
    }

    private static Object a(Class cls, String str, Class[] clsArr, Object[] objArr) throws a {
        String str2;
        if (cls == null) {
            throw new a("class is null in invokeStaticFun");
        }
        if (clsArr == null) {
            if (objArr != null) {
                throw new a("paramsType is null, but params is not null");
            }
        } else {
            if (objArr == null) {
                throw new a("paramsType or params should be same");
            }
            if (clsArr.length != objArr.length) {
                throw new a("paramsType len:" + clsArr.length + " should equal params.len:" + objArr.length);
            }
        }
        try {
            try {
                return cls.getMethod(str, clsArr).invoke(null, objArr);
            } catch (IllegalAccessException unused) {
                str2 = "invokeStaticFun(): method invoke Exception!";
                v.f("hmsSdk", str2);
                return null;
            } catch (IllegalArgumentException unused2) {
                str2 = "invokeStaticFun(): Illegal Argument!";
                v.f("hmsSdk", str2);
                return null;
            } catch (InvocationTargetException unused3) {
                str2 = "invokeStaticFun(): Invocation Target Exception!";
                v.f("hmsSdk", str2);
                return null;
            }
        } catch (NoSuchMethodException unused4) {
            v.f("hmsSdk", "invokeStaticFun(): cls.getMethod(),No Such Method !");
        }
    }

    public static String b() {
        String strA = a("com.huawei.android.os.SystemPropertiesEx", "ro.huawei.build.display.id", "");
        v.c("hmsSdk", "SystemPropertiesEx: get rom_ver: " + strA);
        if (!TextUtils.isEmpty(strA)) {
            return strA;
        }
        String str = Build.DISPLAY;
        v.c("hmsSdk", "SystemProperties: get rom_ver: " + str);
        return str;
    }

    public static String c(Context context) {
        return context == null ? "" : context.getPackageName();
    }

    public static String d(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getPackageManager().getPackageInfo(c(context), 16384).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            v.f("hmsSdk", "getVersion(): The package name is not correct!");
            return "";
        }
    }

    private static Object a(String str, String str2, Class[] clsArr, Object[] objArr) {
        String str3;
        try {
            return a(Class.forName(str), str2, clsArr, objArr);
        } catch (a unused) {
            str3 = "invokeStaticFun(): Static function call Exception ";
            v.f("hmsSdk", str3);
            return null;
        } catch (ClassNotFoundException unused2) {
            str3 = "invokeStaticFun() Not found class!";
            v.f("hmsSdk", str3);
            return null;
        }
    }

    public static String b(Context context) throws PackageManager.NameNotFoundException {
        Bundle bundle;
        Object obj;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null || (bundle = applicationInfo.metaData) == null || (obj = bundle.get("CHANNEL")) == null) {
                return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
            }
            String string = obj.toString();
            return string.length() > 256 ? AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN : string;
        } catch (PackageManager.NameNotFoundException unused) {
            v.f("hmsSdk", "getChannel(): The packageName is not correct!");
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
    }

    public static String a() {
        return a(com.alipay.sdk.m.c.a.f9195a, "");
    }

    @SuppressLint({"HardwareIds"})
    public static String a(Context context) {
        return context == null ? "" : Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    public static String a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        String strA = a("android.os.SystemProperties", str, str2);
        return TextUtils.isEmpty(strA) ? a("com.huawei.android.os.SystemPropertiesEx", str, str2) : strA;
    }

    private static String a(String str, String str2, String str3) {
        Object objA = a(str, TmpConstant.PROPERTY_IDENTIFIER_GET, new Class[]{String.class, String.class}, new Object[]{str2, str3});
        return objA != null ? (String) objA : str3;
    }
}
