package com.baseflow.geolocator.location;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.media3.common.C;

/* loaded from: classes3.dex */
public class BackgroundNotification {

    @NonNull
    private NotificationCompat.Builder builder;

    @NonNull
    private final String channelId;

    @NonNull
    private final Context context;

    @NonNull
    private final Integer notificationId;

    public BackgroundNotification(@NonNull Context context, @NonNull String str, @NonNull Integer num, ForegroundNotificationOptions foregroundNotificationOptions) {
        this.context = context;
        this.notificationId = num;
        this.channelId = str;
        this.builder = new NotificationCompat.Builder(context, str).setPriority(1);
        updateNotification(foregroundNotificationOptions, false);
    }

    @SuppressLint({"UnspecifiedImmutableFlag"})
    private PendingIntent buildBringToFrontIntent() {
        Intent launchIntentForPackage = this.context.getPackageManager().getLaunchIntentForPackage(this.context.getPackageName());
        if (launchIntentForPackage == null) {
            return null;
        }
        launchIntentForPackage.setPackage(null);
        launchIntentForPackage.setFlags(270532608);
        return PendingIntent.getActivity(this.context, 0, launchIntentForPackage, Build.VERSION.SDK_INT > 23 ? 201326592 : C.BUFFER_FLAG_FIRST_SAMPLE);
    }

    private int getDrawableId(String str, String str2) {
        return this.context.getResources().getIdentifier(str, str2, this.context.getPackageName());
    }

    private void updateNotification(ForegroundNotificationOptions foregroundNotificationOptions, boolean z2) {
        int drawableId = getDrawableId(foregroundNotificationOptions.getNotificationIcon().getName(), foregroundNotificationOptions.getNotificationIcon().getDefType());
        if (drawableId == 0) {
            getDrawableId("ic_launcher.png", "mipmap");
        }
        this.builder = this.builder.setContentTitle(foregroundNotificationOptions.getNotificationTitle()).setSmallIcon(drawableId).setContentText(foregroundNotificationOptions.getNotificationText()).setContentIntent(buildBringToFrontIntent()).setOngoing(foregroundNotificationOptions.isSetOngoing());
        Integer color = foregroundNotificationOptions.getColor();
        if (color != null) {
            this.builder = this.builder.setColor(color.intValue());
        }
        if (z2) {
            NotificationManagerCompat.from(this.context).notify(this.notificationId.intValue(), this.builder.build());
        }
    }

    public Notification build() {
        return this.builder.build();
    }

    public void updateChannel(String str) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManagerCompat notificationManagerCompatFrom = NotificationManagerCompat.from(this.context);
            androidx.media3.common.util.j.a();
            NotificationChannel notificationChannelA = androidx.browser.trusted.g.a(this.channelId, str, 0);
            notificationChannelA.setLockscreenVisibility(0);
            notificationManagerCompatFrom.createNotificationChannel(notificationChannelA);
        }
    }

    public void updateOptions(ForegroundNotificationOptions foregroundNotificationOptions, boolean z2) {
        updateNotification(foregroundNotificationOptions, z2);
    }
}
