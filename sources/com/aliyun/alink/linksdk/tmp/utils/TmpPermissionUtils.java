package com.aliyun.alink.linksdk.tmp.utils;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.framework.AApplication;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class TmpPermissionUtils {
    private static final String TAG = "TmpPermissionUtils";

    public static boolean checkIsNeedRequestBleScanAndConnect() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!hasBleScanPermission(AApplication.getInstance().getApplicationContext())) {
            ALog.w(TAG, "startLoopScan hasBleScanPermission = false");
            return true;
        }
        if (hasBleConnectPermission(AApplication.getInstance().getApplicationContext())) {
            ALog.w(TAG, "startLoopScan hasBleScanPermission and hasBleConnectPermission = true");
            return false;
        }
        ALog.w(TAG, "startLoopScan hasBleConnectPermission = false");
        return true;
    }

    public static boolean checkPermission(Context context, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        ALog.d(TAG, "checkPermission: checkSelfPermission =" + ContextCompat.checkSelfPermission(context, str));
        return ContextCompat.checkSelfPermission(context, str) == 0;
    }

    public static boolean hasBleConnectPermission(Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (context == null) {
            return false;
        }
        Context applicationContext = context.getApplicationContext();
        int i2 = applicationContext instanceof Application ? applicationContext.getApplicationInfo().targetSdkVersion : 0;
        StringBuilder sb = new StringBuilder();
        sb.append("hasBleConnectPermission: var1=");
        sb.append(i2);
        sb.append("；Build.VERSION.SDK_INT=");
        int i3 = Build.VERSION.SDK_INT;
        sb.append(i3);
        ALog.d(TAG, sb.toString());
        if (i2 < 31 || i3 < 31) {
            return true;
        }
        return checkPermission(context, "android.permission.BLUETOOTH_CONNECT");
    }

    public static boolean hasBleScanPermission(Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (context == null) {
            return false;
        }
        Context applicationContext = context.getApplicationContext();
        int i2 = applicationContext instanceof Application ? applicationContext.getApplicationInfo().targetSdkVersion : 0;
        StringBuilder sb = new StringBuilder();
        sb.append("hasBleConnectPermission: var1=");
        sb.append(i2);
        sb.append("；Build.VERSION.SDK_INT=");
        int i3 = Build.VERSION.SDK_INT;
        sb.append(i3);
        ALog.d(TAG, sb.toString());
        if (i2 < 31 || i3 < 31) {
            return true;
        }
        return checkPermission(context, "android.permission.BLUETOOTH_SCAN");
    }
}
