package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.luck.picture.lib.permissions.PermissionConfig;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.push.ei;
import com.xiaomi.push.ej;
import java.io.File;

/* loaded from: classes4.dex */
public class Logger {
    private static boolean sDisablePushLog = false;
    private static LoggerInterface sUserLogger;

    public static void disablePushFileLog(Context context) {
        sDisablePushLog = true;
        setPushLog(context);
    }

    public static void enablePushFileLog(Context context) {
        sDisablePushLog = false;
        setPushLog(context);
    }

    @Deprecated
    public static File getLogFile(String str) {
        return null;
    }

    protected static LoggerInterface getUserLogger() {
        return sUserLogger;
    }

    private static boolean hasWritePermission(Context context) {
        try {
            String[] strArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
            if (strArr != null) {
                for (String str : strArr) {
                    if (PermissionConfig.WRITE_EXTERNAL_STORAGE.equals(str)) {
                        return true;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static void setLogger(Context context, LoggerInterface loggerInterface) {
        sUserLogger = loggerInterface;
        setPushLog(context);
    }

    public static void setPushLog(Context context) {
        boolean z2;
        boolean z3 = false;
        boolean z4 = sUserLogger != null;
        if (sDisablePushLog) {
            z2 = false;
        } else {
            z2 = hasWritePermission(context);
            z3 = z4;
        }
        com.xiaomi.channel.commonutils.logger.b.a(new ei(z3 ? sUserLogger : null, z2 ? ej.a(context) : null));
    }

    @Deprecated
    public static void uploadLogFile(Context context, boolean z2) {
    }
}
