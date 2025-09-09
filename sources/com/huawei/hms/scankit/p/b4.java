package com.huawei.hms.scankit.p;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.internal.AnalyticsEvents;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class b4 {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f17005a = false;

    /* renamed from: b, reason: collision with root package name */
    public static String f17006b;

    public static String a(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.getType() == 1) {
            return "wifi";
        }
        if (activeNetworkInfo == null || activeNetworkInfo.getType() != 0) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        String subtypeName = activeNetworkInfo.getSubtypeName();
        switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return "2G";
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                break;
            case 13:
                return "4G";
            default:
                if (!subtypeName.equalsIgnoreCase("TD-SCDMA") && !subtypeName.equalsIgnoreCase("WCDMA") && !subtypeName.equalsIgnoreCase("CDMA2000")) {
                    return subtypeName;
                }
                break;
        }
        return "3G";
    }

    public static String b(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getNetworkOperator();
    }

    public static String c() {
        return Build.MODEL;
    }

    public static String d() throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getDeclaredMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class).invoke(cls, com.alipay.sdk.m.c.a.f9195a);
        } catch (ClassNotFoundException | NoSuchMethodException unused) {
            return null;
        } catch (RuntimeException | InvocationTargetException | Exception unused2) {
            return "";
        }
    }

    public static String b() {
        return "";
    }

    public static String a() {
        return Build.VERSION.RELEASE;
    }

    public static String a(Context context, boolean z2) {
        return new a1(context, z2).a();
    }
}
