package com.umeng.message.common;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.browser.trusted.g;
import androidx.media3.common.util.j;
import com.umeng.message.PushAgent;

/* loaded from: classes4.dex */
public class UPushNotificationChannel {
    public static final String DEFAULT_NOTIFICATION_CHANNEL_NAME = "Default";
    public static final String DEFAULT_NOTIFICATION_SILENCE_CHANNEL_NAME = "Silence";
    public static final String PRIMARY_CHANNEL = "upush_default";
    public static final String PRIMARY_SILENCE_CHANNEL = "upush_silence";

    public static NotificationChannel getDefaultMode(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return null;
        }
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(PRIMARY_CHANNEL);
            if (notificationChannel != null) {
                return notificationChannel;
            }
            j.a();
            NotificationChannel notificationChannelA = g.a(PRIMARY_CHANNEL, PushAgent.getInstance(context).getNotificationChannelName(), 3);
            notificationManager.createNotificationChannel(notificationChannelA);
            return notificationChannelA;
        } catch (Throwable th) {
            UPLog.e("NotificationChannel", th);
            return null;
        }
    }

    public static NotificationChannel getSilenceMode(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return null;
        }
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(PRIMARY_SILENCE_CHANNEL);
            if (notificationChannel != null) {
                return notificationChannel;
            }
            j.a();
            NotificationChannel notificationChannelA = g.a(PRIMARY_SILENCE_CHANNEL, PushAgent.getInstance(context).getNotificationSilenceChannelName(), 1);
            notificationManager.createNotificationChannel(notificationChannelA);
            return notificationChannelA;
        } catch (Throwable th) {
            UPLog.e("NotificationChannel", th);
            return null;
        }
    }
}
