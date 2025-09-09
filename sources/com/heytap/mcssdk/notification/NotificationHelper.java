package com.heytap.mcssdk.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import com.heytap.mcssdk.manage.NotificatonChannelManager;
import com.heytap.msp.push.notification.PushNotification;

/* loaded from: classes3.dex */
public class NotificationHelper {
    public static Notification createDefaultGroupNotification(Context context, String str, PushNotification.Builder builder) {
        Notification.Builder builder2 = new Notification.Builder(context);
        if (Build.VERSION.SDK_INT >= 26) {
            builder2.setChannelId(NotificatonChannelManager.DEFAULT_NOTIFICATION_CHANNEL_ID);
        }
        builder2.setGroup(str);
        builder2.setGroupSummary(true);
        if (setGroupIcon(builder2, builder)) {
            return builder2.build();
        }
        return null;
    }

    public static StatusBarNotification[] getActiveNotifications(NotificationManager notificationManager, String str) {
        return notificationManager.getActiveNotifications();
    }

    public static NotificationManager getNotificationManager(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return (NotificationManager) context.getSystemService("notification");
        } catch (Throwable unused) {
            return null;
        }
    }

    public static boolean isExistNotificationsByPkgAndId(NotificationManager notificationManager, String str, int i2) {
        StatusBarNotification[] activeNotifications = getActiveNotifications(notificationManager, str);
        if (activeNotifications != null && activeNotifications.length != 0) {
            for (StatusBarNotification statusBarNotification : activeNotifications) {
                if (statusBarNotification.getId() == i2) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean setGroupIcon(Notification.Builder builder, PushNotification.Builder builder2) {
        int iconRes = builder2.getIconRes();
        int iconLevel = builder2.getIconLevel();
        Icon icon = builder2.getIcon();
        if (icon != null) {
            builder.setSmallIcon(icon);
            return true;
        }
        if (iconRes != 0 && iconLevel != 0) {
            builder.setSmallIcon(iconRes, iconLevel);
            return true;
        }
        if (iconRes == 0) {
            return false;
        }
        builder.setSmallIcon(iconRes);
        return true;
    }
}
