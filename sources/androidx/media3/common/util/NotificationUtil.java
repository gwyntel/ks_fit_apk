package androidx.media3.common.util;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressLint({"InlinedApi"})
@UnstableApi
/* loaded from: classes.dex */
public final class NotificationUtil {
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Importance {
    }

    private NotificationUtil() {
    }

    public static void createNotificationChannel(Context context, String str, @StringRes int i2, @StringRes int i3, int i4) {
        if (Util.SDK_INT >= 26) {
            NotificationManager notificationManager = (NotificationManager) Assertions.checkNotNull((NotificationManager) context.getSystemService("notification"));
            j.a();
            NotificationChannel notificationChannelA = androidx.browser.trusted.g.a(str, context.getString(i2), i4);
            if (i3 != 0) {
                notificationChannelA.setDescription(context.getString(i3));
            }
            notificationManager.createNotificationChannel(notificationChannelA);
        }
    }

    public static void setNotification(Context context, int i2, @Nullable Notification notification) {
        NotificationManager notificationManager = (NotificationManager) Assertions.checkNotNull((NotificationManager) context.getSystemService("notification"));
        if (notification != null) {
            notificationManager.notify(i2, notification);
        } else {
            notificationManager.cancel(i2);
        }
    }
}
