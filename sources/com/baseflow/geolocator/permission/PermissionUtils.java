package com.baseflow.geolocator.permission;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.media.session.PlaybackStateCompat;

/* loaded from: classes3.dex */
public class PermissionUtils {
    private static PackageInfo getPackageInfo(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        return Build.VERSION.SDK_INT < 33 ? packageManager.getPackageInfo(packageName, 4096) : packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM));
    }

    public static boolean hasPermissionInManifest(Context context, String str) {
        try {
            String[] strArr = getPackageInfo(context).requestedPermissions;
            if (strArr != null) {
                for (String str2 : strArr) {
                    if (str2.equals(str)) {
                        return true;
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }
}
