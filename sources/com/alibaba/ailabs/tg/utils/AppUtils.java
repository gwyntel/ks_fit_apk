package com.alibaba.ailabs.tg.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* loaded from: classes2.dex */
public class AppUtils {
    public static Drawable getAppIcon(Context context, String str) throws PackageManager.NameNotFoundException {
        if (!TextUtils.isEmpty(str) && context != null) {
            try {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
                if (packageInfo == null) {
                    return null;
                }
                return packageInfo.applicationInfo.loadIcon(packageManager);
            } catch (PackageManager.NameNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static String getAppName(Context context) throws PackageManager.NameNotFoundException {
        if (context == null || context.getPackageManager() == null) {
            return null;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            if (applicationInfo == null) {
                return null;
            }
            return (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @SuppressLint({"PackageManagerGetSignatures"})
    public static Signature[] getAppSignature(Context context, String str) throws PackageManager.NameNotFoundException {
        if (!TextUtils.isEmpty(str) && context != null) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 64);
                if (packageInfo == null) {
                    return null;
                }
                return packageInfo.signatures;
            } catch (PackageManager.NameNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static String getAppSignatureMD5(Context context, String str) throws PackageManager.NameNotFoundException {
        Signature[] appSignature = getAppSignature(context, str);
        if (appSignature == null || appSignature.length <= 0) {
            return null;
        }
        return ConvertUtils.bytes2HexString(appSignature[0].toByteArray());
    }

    public static int getAppVersionCode(Context context) throws PackageManager.NameNotFoundException {
        if (context == null || context.getPackageManager() == null) {
            return 0;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo == null) {
                return 0;
            }
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static String getAppVersionName(Context context) throws PackageManager.NameNotFoundException {
        if (context == null || context.getPackageManager() == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getPackageName(Context context) throws PackageManager.NameNotFoundException {
        if (context == null || context.getPackageManager() == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getProcessName(Context context, int i2) {
        ActivityManager activityManager;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        if (context == null || (activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)) == null || (runningAppProcesses = activityManager.getRunningAppProcesses()) == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo != null && runningAppProcessInfo.pid == i2) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    public static String getSystemProperties(String str, String str2) {
        if (str != null && str.length() <= 31) {
            try {
                return (String) Class.forName("android.os.SystemProperties").getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class, String.class).invoke(null, str, "");
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
                return str2;
            } catch (NoSuchMethodException e4) {
                e4.printStackTrace();
                return str2;
            } catch (InvocationTargetException e5) {
                e5.printStackTrace();
                return str2;
            }
        }
        return str2;
    }

    public static boolean isAppBackground(@NonNull Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        if (activityManager == null || (runningAppProcesses = activityManager.getRunningAppProcesses()) == null) {
            return true;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (context.getPackageName().equals(runningAppProcessInfo.processName)) {
                int i2 = runningAppProcessInfo.importance;
                return (i2 == 100 || i2 == 200) ? false : true;
            }
        }
        return true;
    }
}
