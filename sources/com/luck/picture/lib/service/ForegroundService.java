package com.luck.picture.lib.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.browser.trusted.g;
import androidx.core.app.NotificationCompat;
import androidx.media3.common.util.j;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectorProviders;
import com.luck.picture.lib.utils.SdkVersionUtils;

/* loaded from: classes4.dex */
public class ForegroundService extends Service {
    private static final String CHANNEL_NAME = "com.luck.picture.lib";
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "com.luck.picture.lib." + ForegroundService.class.getName();
    private static boolean isForegroundServiceIng = false;

    private Notification createForegroundNotification() {
        int i2 = SdkVersionUtils.isMaxN() ? 4 : 0;
        if (SdkVersionUtils.isO()) {
            j.a();
            NotificationChannel notificationChannelA = g.a(CHANNEL_ID, "com.luck.picture.lib", i2);
            notificationChannelA.setLightColor(-16776961);
            notificationChannelA.canBypassDnd();
            notificationChannelA.setBypassDnd(true);
            notificationChannelA.setLockscreenVisibility(0);
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(notificationChannelA);
        }
        return new NotificationCompat.Builder(this, CHANNEL_ID).setSmallIcon(R.drawable.ps_ic_trans_1px).setContentTitle(getAppName()).setContentText(getString(SelectorProviders.getInstance().getSelectorConfig().chooseMode == SelectMimeType.ofAudio() ? R.string.ps_use_sound : R.string.ps_use_camera)).setOngoing(true).build();
    }

    private String getAppName() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).applicationInfo.loadLabel(getPackageManager()).toString();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static void startForegroundService(Context context, boolean z2) {
        try {
            if (!isForegroundServiceIng && z2) {
                Intent intent = new Intent(context, (Class<?>) ForegroundService.class);
                if (SdkVersionUtils.isO()) {
                    context.startForegroundService(intent);
                } else {
                    context.startService(intent);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void stopService(Context context) {
        try {
            if (isForegroundServiceIng) {
                context.stopService(new Intent(context, (Class<?>) ForegroundService.class));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        startForeground(1, createForegroundNotification());
    }

    @Override // android.app.Service
    public void onDestroy() {
        isForegroundServiceIng = false;
        stopForeground(true);
        super.onDestroy();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i2, int i3) {
        isForegroundServiceIng = true;
        return super.onStartCommand(intent, i2, i3);
    }
}
