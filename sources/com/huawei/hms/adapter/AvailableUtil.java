package com.huawei.hms.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.AndroidException;
import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes.dex */
public class AvailableUtil {

    /* renamed from: a, reason: collision with root package name */
    private static final Object f15749a = new Object();

    /* renamed from: b, reason: collision with root package name */
    private static boolean f15750b = false;

    /* renamed from: c, reason: collision with root package name */
    private static boolean f15751c = false;

    public static boolean isInstallerLibExist(Context context) {
        Bundle bundle;
        Object obj;
        boolean z2;
        if (f15750b) {
            HMSLog.i("AvailableUtil", "installerInit exist: " + f15751c);
            return f15751c;
        }
        synchronized (f15749a) {
            if (!f15750b) {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager == null) {
                    HMSLog.e("AvailableUtil", "In isAvailableLibExist, Failed to get 'PackageManager' instance.");
                    try {
                        Class.forName("com.huawei.hms.update.manager.UpdateManager");
                        z2 = true;
                    } catch (ClassNotFoundException unused) {
                        HMSLog.e("AvailableUtil", "In isInstallerLibExist, Failed to find class UpdateManager.");
                    }
                } else {
                    try {
                        ApplicationInfo applicationInfo = packageManager.getPackageInfo(context.getPackageName(), 128).applicationInfo;
                        if (applicationInfo != null && (bundle = applicationInfo.metaData) != null && (obj = bundle.get("availableHMSCoreInstaller")) != null && String.valueOf(obj).equalsIgnoreCase("yes")) {
                            HMSLog.i("AvailableUtil", "available exist: true");
                            z2 = true;
                        }
                    } catch (AndroidException unused2) {
                        HMSLog.e("AvailableUtil", "In isInstallerLibExist, Failed to read meta data for the availableHMSCoreInstaller.");
                    } catch (RuntimeException e2) {
                        HMSLog.e("AvailableUtil", "In isInstallerLibExist, Failed to read meta data for the availableHMSCoreInstaller.", e2);
                    }
                    z2 = false;
                }
                f15751c = z2;
                f15750b = true;
            }
        }
        HMSLog.i("AvailableUtil", "available exist: " + f15751c);
        return f15751c;
    }
}
