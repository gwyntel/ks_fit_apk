package androidx.core.app;

import android.app.Notification;
import android.app.Service;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class ServiceCompat {
    private static final int FOREGROUND_SERVICE_TYPE_ALLOWED_SINCE_Q = 255;
    private static final int FOREGROUND_SERVICE_TYPE_ALLOWED_SINCE_U = 1073745919;
    public static final int START_STICKY = 1;
    public static final int STOP_FOREGROUND_DETACH = 2;
    public static final int STOP_FOREGROUND_REMOVE = 1;

    @RequiresApi(24)
    static class Api24Impl {
        private Api24Impl() {
        }

        @DoNotInline
        static void a(Service service, int i2) {
            service.stopForeground(i2);
        }
    }

    @RequiresApi(29)
    static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        static void a(Service service, int i2, Notification notification, int i3) {
            if (i3 == 0 || i3 == -1) {
                service.startForeground(i2, notification, i3);
            } else {
                service.startForeground(i2, notification, i3 & 255);
            }
        }
    }

    @RequiresApi(34)
    static class Api34Impl {
        private Api34Impl() {
        }

        @DoNotInline
        static void a(Service service, int i2, Notification notification, int i3) {
            if (i3 == 0 || i3 == -1) {
                service.startForeground(i2, notification, i3);
            } else {
                service.startForeground(i2, notification, i3 & ServiceCompat.FOREGROUND_SERVICE_TYPE_ALLOWED_SINCE_U);
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface StopForegroundFlags {
    }

    private ServiceCompat() {
    }

    public static void startForeground(@NonNull Service service, int i2, @NonNull Notification notification, int i3) {
        int i4 = Build.VERSION.SDK_INT;
        if (i4 >= 34) {
            Api34Impl.a(service, i2, notification, i3);
        } else if (i4 >= 29) {
            Api29Impl.a(service, i2, notification, i3);
        } else {
            service.startForeground(i2, notification);
        }
    }

    public static void stopForeground(@NonNull Service service, int i2) {
        if (Build.VERSION.SDK_INT >= 24) {
            Api24Impl.a(service, i2);
        } else {
            service.stopForeground((i2 & 1) != 0);
        }
    }
}
