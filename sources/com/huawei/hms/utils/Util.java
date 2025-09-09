package com.huawei.hms.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AndroidException;
import com.huawei.hms.android.SystemUtils;
import com.huawei.hms.common.PackageConstants;
import com.huawei.hms.common.util.AGCUtils;
import com.huawei.hms.support.log.HMSLog;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class Util {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f18232a = false;

    /* renamed from: b, reason: collision with root package name */
    private static boolean f18233b = false;

    /* renamed from: c, reason: collision with root package name */
    private static final Object f18234c = new Object();

    /* renamed from: d, reason: collision with root package name */
    private static String f18235d;

    public static int compareHmsVersion(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        if (TextUtils.isEmpty(str2)) {
            return 1;
        }
        if (StringUtil.checkVersion(str) && StringUtil.checkVersion(str2)) {
            String[] strArrSplit = str.split("\\.");
            String[] strArrSplit2 = str2.split("\\.");
            if (2 < strArrSplit.length && 2 < strArrSplit2.length) {
                if (Integer.parseInt(strArrSplit[0]) > Integer.parseInt(strArrSplit2[0])) {
                    return 1;
                }
                if (Integer.parseInt(strArrSplit[0]) < Integer.parseInt(strArrSplit2[0])) {
                    return -1;
                }
                if (Integer.parseInt(strArrSplit[1]) > Integer.parseInt(strArrSplit2[1])) {
                    return 1;
                }
                if (Integer.parseInt(strArrSplit[1]) < Integer.parseInt(strArrSplit2[1])) {
                    return -1;
                }
                if (Integer.parseInt(strArrSplit[2]) > Integer.parseInt(strArrSplit2[2])) {
                    return 1;
                }
                if (Integer.parseInt(strArrSplit[2]) < Integer.parseInt(strArrSplit2[2])) {
                    return -1;
                }
            }
        }
        return 0;
    }

    public static Activity getActiveActivity(Activity activity, Context context) {
        return UIUtil.getActiveActivity(activity, context);
    }

    public static String getAppId(Context context) {
        return AGCUtils.getAppId(context);
    }

    public static String getAppName(Context context, String str) {
        if (context == null) {
            HMSLog.e("Util", "In getAppName, context is null.");
            return "";
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            HMSLog.e("Util", "In getAppName, Failed to get 'PackageManager' instance.");
            return "";
        }
        try {
            if (TextUtils.isEmpty(str)) {
                str = context.getPackageName();
            }
            CharSequence applicationLabel = packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 128));
            return applicationLabel == null ? "" : applicationLabel.toString();
        } catch (Resources.NotFoundException | AndroidException unused) {
            HMSLog.e("Util", "In getAppName, Failed to get app name.");
            return "";
        }
    }

    public static String getCpId(Context context) {
        return AGCUtils.getCpId(context);
    }

    public static int getHmsVersion(Context context) {
        Bundle bundle;
        Object obj;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            HMSLog.e("Util", "In getHmsVersion, Failed to get 'PackageManager' instance.");
            return 0;
        }
        try {
            ApplicationInfo applicationInfo = packageManager.getPackageInfo(context.getPackageName(), 128).applicationInfo;
            if (applicationInfo != null && (bundle = applicationInfo.metaData) != null && (obj = bundle.get("com.huawei.hms.version")) != null) {
                String strValueOf = String.valueOf(obj);
                if (!TextUtils.isEmpty(strValueOf)) {
                    return StringUtil.convertVersion2Integer(strValueOf);
                }
            }
            HMSLog.i("Util", "In getHmsVersion, Failed to read meta data for the HMS VERSION.");
            return 0;
        } catch (AndroidException unused) {
            HMSLog.e("Util", "In getHmsVersion, Failed to read meta data for the HMS VERSION.");
            return 0;
        } catch (RuntimeException e2) {
            HMSLog.e("Util", "In getHmsVersion, Failed to read meta data for the HMS VERSION.", e2);
            return 0;
        }
    }

    public static String getNetType(Context context) {
        return SystemUtils.getNetType(context);
    }

    public static String getProcessName(Context context, int i2) {
        return UIUtil.getProcessName(context, i2);
    }

    @Deprecated
    public static String getProductCountry() {
        int iLastIndexOf;
        String systemProperties = getSystemProperties("ro.product.locale.region", "");
        if (!TextUtils.isEmpty(systemProperties)) {
            return systemProperties;
        }
        String systemProperties2 = getSystemProperties("ro.product.locale", "");
        if (!TextUtils.isEmpty(systemProperties2) && (iLastIndexOf = systemProperties2.lastIndexOf(Constants.ACCEPT_TIME_SEPARATOR_SERVER)) != -1) {
            return systemProperties2.substring(iLastIndexOf + 1);
        }
        String localCountry = SystemUtils.getLocalCountry();
        return !TextUtils.isEmpty(localCountry) ? localCountry : "";
    }

    public static String getServiceActionMetadata(Context context) {
        ResolveInfo resolveInfoResolveService;
        String str = f18235d;
        if (str != null) {
            return str;
        }
        try {
            Intent intent = new Intent(PackageConstants.INTERNAL_SERVICES_ACTION);
            intent.setPackage(context.getPackageName());
            resolveInfoResolveService = context.getPackageManager().resolveService(intent, 128);
        } catch (Exception e2) {
            HMSLog.e("Util", "Exception when getServiceActionMetadata. " + e2.getMessage());
            resolveInfoResolveService = null;
        }
        if (resolveInfoResolveService != null) {
            f18235d = PackageConstants.INTERNAL_SERVICES_ACTION;
            return PackageConstants.INTERNAL_SERVICES_ACTION;
        }
        f18235d = "";
        return "";
    }

    public static String getSystemProperties(String str, String str2) {
        return SystemUtils.getSystemProperties(str, str2);
    }

    public static Activity getValidActivity(Activity activity, Activity activity2) {
        if (activity != null && !activity.isFinishing()) {
            return activity;
        }
        if (activity2 == null || activity2.isFinishing()) {
            return null;
        }
        return activity2;
    }

    public static boolean isAvailableLibExist(Context context) {
        boolean z2;
        Bundle bundle;
        Object obj;
        synchronized (f18234c) {
            if (!f18232a) {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager == null) {
                    HMSLog.e("Util", "In isAvailableLibExist, Failed to get 'PackageManager' instance.");
                } else {
                    try {
                        ApplicationInfo applicationInfo = packageManager.getPackageInfo(context.getPackageName(), 128).applicationInfo;
                        if (applicationInfo != null && (bundle = applicationInfo.metaData) != null && (obj = bundle.get("availableLoaded")) != null && String.valueOf(obj).equalsIgnoreCase("yes")) {
                            HMSLog.i("Util", "available exist: true");
                        }
                    } catch (AndroidException unused) {
                        HMSLog.e("Util", "In isAvailableLibExist, Failed to read meta data for the availableLoaded.");
                    } catch (RuntimeException e2) {
                        HMSLog.e("Util", "In isAvailableLibExist, Failed to read meta data for the availableLoaded.", e2);
                    }
                    z2 = false;
                    f18233b = z2;
                    f18232a = true;
                }
                z2 = true;
                f18233b = z2;
                f18232a = true;
            }
        }
        HMSLog.i("Util", "available exist: " + f18233b);
        return f18233b;
    }

    public static boolean isChinaROM() {
        return SystemUtils.isChinaROM();
    }

    public static boolean isEMUI() {
        return SystemUtils.isEMUI();
    }

    public static void unBindServiceCatchException(Context context, ServiceConnection serviceConnection) {
        try {
            HMSLog.i("Util", "Trying to unbind service from " + serviceConnection);
            context.unbindService(serviceConnection);
        } catch (Exception e2) {
            HMSLog.e("Util", "On unBindServiceException:" + e2.getMessage());
        }
    }
}
