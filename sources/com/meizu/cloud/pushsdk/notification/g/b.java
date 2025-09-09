package com.meizu.cloud.pushsdk.notification.g;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static Field f19758a;

    /* renamed from: b, reason: collision with root package name */
    private static Field f19759b;

    /* renamed from: c, reason: collision with root package name */
    private static Field f19760c;

    /* renamed from: d, reason: collision with root package name */
    private static final Object f19761d = new Object();

    /* renamed from: e, reason: collision with root package name */
    private static final Map<String, Set<String>> f19762e = new ConcurrentHashMap();

    /* renamed from: f, reason: collision with root package name */
    private static Map<String, Uri> f19763f;

    static {
        try {
            f19758a = Notification.class.getDeclaredField("mFlymeNotification");
            Field declaredField = Class.forName("android.app.NotificationExt").getDeclaredField("internalApp");
            f19759b = declaredField;
            declaredField.setAccessible(true);
        } catch (Exception e2) {
            DebugLogger.e("NotificationUtils", "init NotificationUtils error " + e2.getMessage());
        }
        try {
            Field declaredField2 = Notification.class.getDeclaredField("replyIntent");
            f19760c = declaredField2;
            declaredField2.setAccessible(true);
        } catch (Exception e3) {
            DebugLogger.e("NotificationUtils", "init NotificationUtils error " + e3.getMessage());
        }
    }

    public static void a(Notification notification, PendingIntent pendingIntent) throws IllegalAccessException, IllegalArgumentException {
        Field field = f19760c;
        if (field != null) {
            try {
                field.set(notification, pendingIntent);
            } catch (IllegalAccessException e2) {
                DebugLogger.e("NotificationUtils", "setReplyIntent error " + e2.getMessage());
            }
        }
    }

    public static Uri b(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str) || RingtoneManager.getActualDefaultRingtoneUri(context, 2) == null) {
            return null;
        }
        if (f19763f == null) {
            b(context);
        }
        Map<String, Uri> map = f19763f;
        if (map != null && map.size() != 0) {
            return f19763f.get(str.toLowerCase());
        }
        return null;
    }

    public static void c(Context context, String str, int i2) {
        Map<String, Set<String>> map = f19762e;
        Set<String> set = map.get(str);
        DebugLogger.i("NotificationUtils", "store notifyId " + i2);
        if (set != null) {
            set.add(String.valueOf(i2));
            return;
        }
        HashSet hashSet = new HashSet();
        hashSet.add(String.valueOf(i2));
        map.put(str, hashSet);
    }

    public static void a(Notification notification, boolean z2) throws IllegalAccessException, IllegalArgumentException {
        Field field = f19758a;
        if (field == null || f19759b == null) {
            return;
        }
        try {
            f19759b.set(field.get(notification), Integer.valueOf(z2 ? 1 : 0));
        } catch (IllegalAccessException e2) {
            DebugLogger.e("NotificationUtils", "setInternalApp error " + e2.getMessage());
        }
    }

    private static synchronized void b(Context context) {
        if (f19763f != null) {
            return;
        }
        Cursor cursor = null;
        try {
            try {
                RingtoneManager ringtoneManager = new RingtoneManager(context);
                ringtoneManager.setType(2);
                cursor = ringtoneManager.getCursor();
            } catch (Exception e2) {
                DebugLogger.e("NotificationUtils", "get ringtone info error, " + e2.getMessage());
                if (0 != 0) {
                }
            }
            if (cursor == null) {
                if (cursor != null) {
                    cursor.close();
                }
                return;
            }
            f19763f = new HashMap(cursor.getCount());
            for (boolean zMoveToFirst = cursor.moveToFirst(); zMoveToFirst; zMoveToFirst = cursor.moveToNext()) {
                String string = cursor.getString(1);
                Uri uriWithAppendedId = ContentUris.withAppendedId(Uri.parse(cursor.getString(2)), cursor.getLong(0));
                if (!TextUtils.isEmpty(string) && uriWithAppendedId != null) {
                    f19763f.put(string.toLowerCase(), uriWithAppendedId);
                }
            }
            cursor.close();
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    public static void a(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
    }

    public static void b(Context context, String str, int i2) {
        Set<String> set = f19762e.get(str);
        if (set != null) {
            set.remove(String.valueOf(i2));
            DebugLogger.i("NotificationUtils", "remove notifyId " + i2);
        }
    }

    public static void a(Context context, String str) {
        Set<String> set;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager == null || TextUtils.isEmpty(str) || (set = f19762e.get(str)) == null) {
            return;
        }
        for (String str2 : set) {
            DebugLogger.i("NotificationUtils", "clear notifyId " + str2 + " notification");
            notificationManager.cancel(Integer.parseInt(str2));
        }
        set.clear();
    }

    public static void a(Context context, String str, int i2) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager != null) {
            DebugLogger.i("NotificationUtils", "clear clearNotification notifyId " + i2);
            notificationManager.cancel(i2);
            Set<String> set = f19762e.get(str);
            if (set != null) {
                set.remove(String.valueOf(i2));
            }
        }
    }

    public static boolean a(Context context, String str, String str2) {
        synchronized (f19761d) {
            try {
                if (TextUtils.isEmpty(str2)) {
                    return false;
                }
                int iC = com.meizu.cloud.pushsdk.util.b.c(context, str, str2);
                DebugLogger.e("NotificationUtils", "removeNotifyKey " + str2 + " notifyId " + iC);
                b(context, str, iC);
                return com.meizu.cloud.pushsdk.util.b.i(context, str, str2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
