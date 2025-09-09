package com.aliyun.alink.linksdk.cmp.core.util;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.dynamicdatastore.IDynamicDataStoreComponent;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class SecurityGuardDataStoreUtil {
    private static final String TAG = "SecurityGuardDataStoreUtil";

    public static String getString(Context context, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IDynamicDataStoreComponent dynamicDataStoreComp;
        ALog.d(TAG, "getString, key = " + str);
        if (context != null && !TextUtils.isEmpty(str)) {
            try {
                SecurityGuardManager securityGuardManager = SecurityGuardManager.getInstance(context);
                if (securityGuardManager != null && (dynamicDataStoreComp = securityGuardManager.getDynamicDataStoreComp()) != null) {
                    return dynamicDataStoreComp.getStringDDpEx(str, 0);
                }
            } catch (Exception e2) {
                ALog.d(TAG, "getString(),error, " + e2.toString());
            }
        }
        return null;
    }

    public static boolean putString(Context context, String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IDynamicDataStoreComponent dynamicDataStoreComp;
        ALog.d(TAG, "putString, key = " + str + ", value=" + str2);
        if (context != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                SecurityGuardManager securityGuardManager = SecurityGuardManager.getInstance(context);
                if (securityGuardManager != null && (dynamicDataStoreComp = securityGuardManager.getDynamicDataStoreComp()) != null) {
                    return dynamicDataStoreComp.putStringDDpEx(str, str2, 0);
                }
            } catch (Exception e2) {
                ALog.d(TAG, "putString(),error, " + e2.toString());
            }
        }
        return false;
    }

    public static void removeString(Context context, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IDynamicDataStoreComponent dynamicDataStoreComp;
        ALog.d(TAG, "removeString, key = " + str);
        if (context == null || TextUtils.isEmpty(str)) {
            return;
        }
        try {
            SecurityGuardManager securityGuardManager = SecurityGuardManager.getInstance(context);
            if (securityGuardManager == null || (dynamicDataStoreComp = securityGuardManager.getDynamicDataStoreComp()) == null) {
                return;
            }
            dynamicDataStoreComp.removeStringDDpEx(str, 0);
        } catch (Exception e2) {
            ALog.d(TAG, "removeString(),error, " + e2.toString());
        }
    }
}
