package com.heytap.mcssdk.manage;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.browser.trusted.g;
import com.heytap.mcssdk.utils.SharedPreferenceManager;
import com.heytap.mcssdk.utils.ThreadUtil;
import com.pushsdk.R;

/* loaded from: classes3.dex */
public class NotificatonChannelManager {
    private static final int DEFAULT_CHANNEL_IMPORTANCE = 3;
    public static final String DEFAULT_NOTIFICATION_CHANNEL_ID = "Heytap PUSH";
    private static final String DEFAULT_NOTIFICATION_CHANNEL_NAME = "System Default Channel";

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(26)
    public boolean createChannel(Context context, String str, String str2, int i2) {
        NotificationManager notificationManager;
        if (context == null || (notificationManager = (NotificationManager) context.getSystemService("notification")) == null) {
            return false;
        }
        notificationManager.createNotificationChannel(g.a(str, str2, i2));
        return true;
    }

    public void createDefaultChannel(final Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        ThreadUtil.executeOnBackground(new Runnable() { // from class: com.heytap.mcssdk.manage.NotificatonChannelManager.1
            @Override // java.lang.Runnable
            public void run() {
                if (SharedPreferenceManager.getInstance().hasDefaultChannelCreated()) {
                    return;
                }
                String string = context.getString(R.string.system_default_channel);
                if (TextUtils.isEmpty(string)) {
                    string = NotificatonChannelManager.DEFAULT_NOTIFICATION_CHANNEL_NAME;
                }
                SharedPreferenceManager.getInstance().setHasDefaultChannelCreated(NotificatonChannelManager.this.createChannel(context, NotificatonChannelManager.DEFAULT_NOTIFICATION_CHANNEL_ID, string, 3));
            }
        });
    }
}
