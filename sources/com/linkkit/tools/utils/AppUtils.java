package com.linkkit.tools.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* loaded from: classes4.dex */
public class AppUtils {
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            return String.valueOf(packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getApplicationContext().getPackageName(), 0)));
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public static Context getContext() throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object objInvoke = cls.getMethod("currentActivityThread", null).invoke(cls, null);
            return (Context) objInvoke.getClass().getMethod("getApplication", null).invoke(objInvoke, null);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getCurrentThreadInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ThreadInfo[threadId=");
        stringBuffer.append(Thread.currentThread().getId());
        stringBuffer.append(": threadName=" + Thread.currentThread().getName());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    private String getProcessName(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        try {
            runningAppProcesses = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        } catch (Exception unused) {
        }
        if (runningAppProcesses == null) {
            return null;
        }
        int iMyPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    public static String getVersionName(Context context) {
        try {
            return context.getApplicationContext().getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0).versionName;
        } catch (Throwable unused) {
            return "";
        }
    }
}
