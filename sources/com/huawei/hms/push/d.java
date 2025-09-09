package com.huawei.hms.push;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.media3.common.C;
import com.huawei.hms.support.log.HMSLog;
import java.util.Date;
import java.util.List;

/* loaded from: classes4.dex */
public class d {
    public static String a(Context context, String str) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 128)).toString();
        } catch (PackageManager.NameNotFoundException unused) {
            HMSLog.i("PushSelfShowLog", "get the app name of package:" + str + " failed.");
            return null;
        }
    }

    public static Intent b(Context context, String str) {
        try {
            return context.getPackageManager().getLaunchIntentForPackage(str);
        } catch (Exception unused) {
            HMSLog.w("PushSelfShowLog", str + " not have launch activity");
            return null;
        }
    }

    private static boolean c() {
        return true;
    }

    private static boolean d() {
        return Build.VERSION.SDK_INT >= 30;
    }

    public static boolean e() {
        return Build.VERSION.SDK_INT >= 33;
    }

    public static boolean f() {
        return true;
    }

    public static boolean c(Context context, String str) {
        if (context != null && str != null && !"".equals(str)) {
            try {
                if (context.getPackageManager().getApplicationInfo(str, 8192) == null) {
                    return false;
                }
                HMSLog.d("PushSelfShowLog", str + " is installed");
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static int b() {
        return c() ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : C.BUFFER_FLAG_FIRST_SAMPLE;
    }

    public static Boolean a(Context context, String str, Intent intent) {
        try {
            List<ResolveInfo> listQueryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
            if (listQueryIntentActivities != null && listQueryIntentActivities.size() > 0) {
                int size = listQueryIntentActivities.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (listQueryIntentActivities.get(i2).activityInfo != null && str.equals(listQueryIntentActivities.get(i2).activityInfo.applicationInfo.packageName)) {
                        return Boolean.TRUE;
                    }
                }
            }
        } catch (Exception e2) {
            HMSLog.e("PushSelfShowLog", e2.toString(), e2);
        }
        return Boolean.FALSE;
    }

    public static long a(String str) {
        if (str == null) {
            str = "";
        }
        try {
            Date date = new Date();
            int hours = (date.getHours() * 2) + (date.getMinutes() / 30);
            String strConcat = str.concat(str);
            HMSLog.i("PushSelfShowLog", "startIndex is " + hours + ",ap is:" + strConcat + ",length is:" + strConcat.length());
            int length = strConcat.length();
            for (int i2 = hours; i2 < length; i2++) {
                if (strConcat.charAt(i2) != '0') {
                    long minutes = (((i2 - hours) * 30) - (date.getMinutes() % 30)) * 60000;
                    HMSLog.d("PushSelfShowLog", "startIndex is:" + hours + " i is:" + i2 + " delay:" + minutes);
                    if (minutes >= 0) {
                        return minutes;
                    }
                    return 0L;
                }
            }
        } catch (Exception e2) {
            HMSLog.e("PushSelfShowLog", "error ", e2);
        }
        return 0L;
    }

    public static void a(Context context, Intent intent, long j2) {
        try {
            HMSLog.d("PushSelfShowLog", "enter setAPDelayAlarm(interval:" + j2 + "ms.");
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
            if (alarmManager != null) {
                alarmManager.set(0, System.currentTimeMillis() + j2, PendingIntent.getBroadcast(context, (int) (System.currentTimeMillis() / 1000), intent, b()));
            }
        } catch (Exception e2) {
            HMSLog.w("PushSelfShowLog", "set DelayAlarm error" + e2.toString());
        }
    }

    public static boolean a(Context context) {
        return "com.huawei.hwid".equals(context.getPackageName());
    }

    public static void a(Context context, int i2) {
        if (context == null) {
            HMSLog.e("PushSelfShowLog", "context is null");
            return;
        }
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            if (notificationManager != null) {
                notificationManager.cancel(i2);
            }
        } catch (Exception e2) {
            HMSLog.e("PushSelfShowLog", "removeNotifiCationById err:" + e2.toString());
        }
    }

    public static void a(Context context, Intent intent) {
        try {
            int intExtra = intent.getIntExtra("selfshow_auto_clear_id", 0);
            HMSLog.d("PushSelfShowLog", "setDelayAlarm(cancel) alarmNotityId " + intExtra);
            if (intExtra == 0) {
                return;
            }
            Intent intent2 = new Intent("com.huawei.intent.action.PUSH_DELAY_NOTIFY");
            intent2.setPackage(context.getPackageName()).setFlags(32);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, intExtra, intent2, a());
            if (broadcast != null && alarmManager != null) {
                HMSLog.d("PushSelfShowLog", "alarm cancel");
                alarmManager.cancel(broadcast);
            } else {
                HMSLog.d("PushSelfShowLog", "alarm not exist");
            }
        } catch (Exception e2) {
            HMSLog.e("PushSelfShowLog", "cancelAlarm err:" + e2.toString());
        }
    }

    private static int a() {
        if (d()) {
            return 603979776;
        }
        return C.BUFFER_FLAG_LAST_SAMPLE;
    }
}
