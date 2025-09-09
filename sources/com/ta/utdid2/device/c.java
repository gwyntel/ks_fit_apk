package com.ta.utdid2.device;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.ta.a.c.f;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
class c {

    /* renamed from: e, reason: collision with root package name */
    private static String f20040e;

    private static PackageInfo a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
        } catch (Exception e2) {
            f.a("", e2, new Object[0]);
            return null;
        }
    }

    /* renamed from: c, reason: collision with other method in class */
    public static boolean m79c(Context context) {
        try {
            String strE = e(context);
            String strC = c(context);
            f.m77a("", "curProcessName", strC);
            if (!TextUtils.isEmpty(strC) && !TextUtils.isEmpty(strE)) {
                return strC.equals(strE);
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static String d(Context context) {
        try {
            int iMyPid = Process.myPid();
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == iMyPid) {
                    return runningAppProcessInfo.processName;
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    private static String e(Context context) {
        PackageInfo packageInfoA = a(context);
        return packageInfoA != null ? packageInfoA.packageName : "";
    }

    private static String n() {
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                return Application.getProcessName();
            } catch (Exception unused) {
            }
        }
        return null;
    }

    private static String o() {
        try {
            Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader()).getDeclaredMethod("currentProcessName", new Class[0]);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(null, null);
            if (objInvoke instanceof String) {
                return (String) objInvoke;
            }
            return null;
        } catch (Throwable th) {
            f.a("", th, new Object[0]);
            return null;
        }
    }

    private static String c(Context context) {
        if (!TextUtils.isEmpty(f20040e)) {
            return f20040e;
        }
        String strN = n();
        f20040e = strN;
        f.m77a("", "currentProcessName", strN);
        if (!TextUtils.isEmpty(f20040e)) {
            return f20040e;
        }
        String strO = o();
        f20040e = strO;
        f.m77a("", "currentProcessName2", strO);
        if (!TextUtils.isEmpty(f20040e)) {
            return f20040e;
        }
        String strD = d(context);
        f20040e = strD;
        f.m77a("", "currentProcessName3", strD);
        return f20040e;
    }
}
