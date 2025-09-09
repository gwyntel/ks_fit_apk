package com.meizu.cloud.pushsdk.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.text.TextUtils;
import com.kingsmith.miot.KsProperty;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.d.i;
import java.io.File;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class MzSystemUtils {
    private static final String PUSH_SERVICE_PROCESS_NAME = "mzservice_v1";
    private static final String TAG = "MzSystemUtils";
    private static int flymeVersion = -1;
    private static String sCharacteristics;

    public static boolean compareVersion(String str, String str2) {
        String[] strArrSplit = str.split("\\.");
        String[] strArrSplit2 = str2.split("\\.");
        int iMin = Math.min(strArrSplit.length, strArrSplit2.length);
        int length = 0;
        for (int i2 = 0; i2 < iMin; i2++) {
            length = strArrSplit[i2].length() - strArrSplit2[i2].length();
            if (length != 0 || (length = strArrSplit[i2].compareTo(strArrSplit2[i2])) != 0) {
                break;
            }
        }
        if (length == 0) {
            length = strArrSplit.length - strArrSplit2.length;
        }
        return length >= 0;
    }

    @SuppressLint({"QueryPermissionsNeeded"})
    public static String findReceiver(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                Intent intent = new Intent(str);
                intent.setPackage(str2);
                List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
                if (listQueryBroadcastReceivers.size() > 0) {
                    return listQueryBroadcastReceivers.get(0).activityInfo.name;
                }
            } catch (Exception e2) {
                DebugLogger.e(TAG, "findReceiver error " + e2.getMessage());
            }
        }
        return null;
    }

    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception unused) {
            return 0;
        }
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getCurrentLanguage() {
        try {
            return Locale.getDefault().getLanguage();
        } catch (Exception e2) {
            DebugLogger.e(TAG, "getCurrentLanguage error " + e2.getMessage());
            return null;
        }
    }

    public static String getDocumentsPath(Context context) {
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (externalFilesDir != null) {
            return externalFilesDir.getPath();
        }
        return "/storage/emulated/0/Android/data/" + context.getPackageName() + "/files/Documents";
    }

    public static int getFlymeVersion() throws NumberFormatException {
        int i2 = flymeVersion;
        if (i2 > 0) {
            return i2;
        }
        try {
            try {
                int i3 = Integer.parseInt(i.a("ro.build.flyme.version"));
                flymeVersion = i3;
                return i3;
            } catch (Exception unused) {
                String strA = i.a("ro.flyme.version.id");
                if (TextUtils.isEmpty(strA)) {
                    strA = i.a("ro.build.display.id");
                }
                int iIntValue = Integer.valueOf(strA.replace("Flyme", "").replace(" ", "").substring(0, 1)).intValue();
                flymeVersion = iIntValue;
                return iIntValue;
            }
        } catch (Exception e2) {
            DebugLogger.e(TAG, "getFlymeVersion error " + e2.getMessage());
            return -1;
        }
    }

    public static String getMzPushServicePackageName(Context context) {
        String packageName = context.getPackageName();
        try {
            String str = isWatch() ? PushConstants.WEARABLE_PUSH_PACKAGE_NAME : PushConstants.PUSH_PACKAGE_NAME;
            String servicesByPackageName = getServicesByPackageName(context, str);
            if (!TextUtils.isEmpty(servicesByPackageName)) {
                if (servicesByPackageName.contains(PUSH_SERVICE_PROCESS_NAME)) {
                    return str;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        DebugLogger.i(TAG, "start service package name " + packageName);
        return packageName;
    }

    public static String getNetWorkType(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
            if (activeNetworkInfo != null) {
                int type = activeNetworkInfo.getType();
                if (type != 0) {
                    return type != 1 ? type != 7 ? type != 9 ? "OTHER" : "ETHERNET" : "BLUETOOTH" : "WIFI";
                }
                int subtype = activeNetworkInfo.getSubtype();
                if (subtype != 18) {
                    if (subtype == 20) {
                        return "MOBILE_5G";
                    }
                    switch (subtype) {
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11:
                            return "MOBILE_2G";
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15:
                            return "MOBILE_3G";
                        case 13:
                            break;
                        default:
                            return "MOBILE_XG";
                    }
                }
                return "MOBILE_4G";
            }
        } catch (Exception e2) {
            DebugLogger.e(TAG, "Security exception checking connection: " + e2.getMessage());
        }
        return "";
    }

    private static String getServicesByPackageName(Context context, String str) {
        ServiceInfo[] serviceInfoArr;
        try {
            serviceInfoArr = context.getPackageManager().getPackageInfo(str, 4).services;
        } catch (Exception unused) {
            serviceInfoArr = null;
        }
        if (serviceInfoArr == null) {
            return null;
        }
        for (ServiceInfo serviceInfo : serviceInfoArr) {
            if (PushConstants.MZ_PUSH_SERVICE_NAME.equals(serviceInfo.name)) {
                return serviceInfo.processName;
            }
        }
        return null;
    }

    public static boolean isBrandMeizu(Context context) {
        boolean zIsMeizu = isMeizu();
        if (!zIsMeizu) {
            com.meizu.cloud.pushsdk.c.a.c(context.getApplicationContext());
        }
        return zIsMeizu;
    }

    @SuppressLint({"QueryPermissionsNeeded"})
    public static boolean isExistReceiver(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            return false;
        }
        Intent intent = new Intent(str2);
        intent.setPackage(str);
        List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
        return (listQueryBroadcastReceivers == null || listQueryBroadcastReceivers.size() <= 0 || TextUtils.isEmpty(listQueryBroadcastReceivers.get(0).activityInfo.name)) ? false : true;
    }

    public static boolean isIndiaLocal() {
        return "india".equals(i.a("ro.meizu.locale.region"));
    }

    public static boolean isInteractive(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(KsProperty.Power);
        if (powerManager == null) {
            return true;
        }
        try {
            return powerManager.isInteractive();
        } catch (Exception e2) {
            DebugLogger.e(TAG, "isScreenOn error " + e2.getMessage());
            return true;
        }
    }

    public static boolean isInternational() {
        if (com.meizu.cloud.pushsdk.d.a.a().f19257a) {
            return com.meizu.cloud.pushsdk.d.a.a().f19258b.booleanValue();
        }
        return false;
    }

    public static boolean isMeizu() {
        String str = Build.BRAND;
        return "meizu".equalsIgnoreCase(str) || "mblu".equalsIgnoreCase(str) || !TextUtils.isEmpty(i.a("ro.vendor.meizu.product.model")) || !TextUtils.isEmpty(i.a("ro.meizu.product.model"));
    }

    public static boolean isMeizuAndFlyme() {
        if (com.meizu.cloud.pushsdk.d.a.b().f19257a) {
            return !r0.f19258b.booleanValue();
        }
        return false;
    }

    public static boolean isOverseas() {
        return isInternational() || isIndiaLocal();
    }

    public static boolean isPackageInstalled(Context context, String str) throws PackageManager.NameNotFoundException {
        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isWatch() {
        if (TextUtils.isEmpty(sCharacteristics)) {
            sCharacteristics = i.a("ro.build.characteristics");
        }
        if (!TextUtils.isEmpty(sCharacteristics)) {
            return sCharacteristics.contains("watch");
        }
        sCharacteristics = "phone";
        return false;
    }

    public static void sendMessageFromBroadcast(Context context, Intent intent, String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            intent.setAction(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            intent.setPackage(str2);
        }
        String strFindReceiver = findReceiver(context, str, str2);
        if (!TextUtils.isEmpty(strFindReceiver)) {
            intent.setClassName(str2, strFindReceiver);
        }
        context.sendBroadcast(intent);
    }

    public static String getAppVersionName(Context context, String str) {
        try {
            String str2 = context.getPackageManager().getPackageInfo(str, 0).versionName;
            if (str2 != null) {
                if (str2.length() > 0) {
                    return str2;
                }
            }
            return "";
        } catch (Exception e2) {
            DebugLogger.e(TAG, "Exception message " + e2.getMessage());
            return "";
        }
    }
}
