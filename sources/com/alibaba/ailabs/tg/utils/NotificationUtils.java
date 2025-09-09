package com.alibaba.ailabs.tg.utils;

import android.app.NotificationManager;
import android.content.Context;
import androidx.appcompat.app.NotificationCompat;

/* loaded from: classes2.dex */
public class NotificationUtils {
    public static void notification(Context context, int i2, NotificationCompat.Builder builder) {
        notification(context, null, i2, builder);
    }

    public static void notification(Context context, String str, int i2, NotificationCompat.Builder builder) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager != null) {
            notificationManager.notify(i2, builder.build());
        }
    }
}
