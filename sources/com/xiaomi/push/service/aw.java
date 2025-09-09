package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import com.taobao.accs.AccsClientConfig;
import com.xiaomi.push.is;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public class aw {

    /* renamed from: a, reason: collision with root package name */
    private static Context f24475a;

    /* renamed from: a, reason: collision with other field name */
    private static Object f1019a;

    /* renamed from: a, reason: collision with other field name */
    private static WeakHashMap<Integer, aw> f1020a = new WeakHashMap<>();

    /* renamed from: a, reason: collision with other field name */
    private static boolean f1021a;

    /* renamed from: a, reason: collision with other field name */
    private String f1022a;

    /* renamed from: b, reason: collision with root package name */
    private String f24476b;

    private aw(String str) {
        this.f1022a = str;
    }

    private String b(String str) {
        return a(m740a() ? "mipush|%s|%s" : "mipush_%s_%s", this.f1022a, str);
    }

    /* renamed from: a, reason: collision with other method in class */
    public Context m744a() {
        return f24475a;
    }

    public String toString() {
        return "NotificationManagerHelper{" + this.f1022a + com.alipay.sdk.m.u.i.f9804d;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m745a() {
        return this.f1022a;
    }

    public static aw a(Context context, String str) {
        a(context);
        int iHashCode = str.hashCode();
        aw awVar = f1020a.get(Integer.valueOf(iHashCode));
        if (awVar != null) {
            return awVar;
        }
        aw awVar2 = new aw(str);
        f1020a.put(Integer.valueOf(iHashCode), awVar2);
        return awVar2;
    }

    String b() {
        if (TextUtils.isEmpty(this.f24476b)) {
            this.f24476b = b(AccsClientConfig.DEFAULT_CONFIGTAG);
        }
        return this.f24476b;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m741a(Context context) {
        a(context);
        return m740a();
    }

    String b(String str, String str2) {
        return m740a() ? str : str2;
    }

    /* renamed from: b, reason: collision with other method in class */
    public List<StatusBarNotification> m749b() {
        String str = this.f1022a;
        NotificationManager notificationManagerA = a();
        List<StatusBarNotification> list = null;
        try {
            if (m740a()) {
                int iA = com.xiaomi.push.i.a();
                if (iA != -1) {
                    list = (List) a(com.xiaomi.push.bk.a(f1019a, "getAppActiveNotifications", str, Integer.valueOf(iA)));
                }
            } else {
                StatusBarNotification[] activeNotifications = notificationManagerA.getActiveNotifications();
                if (activeNotifications != null && activeNotifications.length > 0) {
                    ArrayList arrayList = new ArrayList();
                    try {
                        for (StatusBarNotification statusBarNotification : activeNotifications) {
                            if (str.equals(ax.c(statusBarNotification.getNotification()))) {
                                arrayList.add(statusBarNotification);
                            }
                        }
                        list = arrayList;
                    } catch (Throwable th) {
                        th = th;
                        list = arrayList;
                        m739a("getActiveNotifications error " + th);
                        return list;
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
        return list;
    }

    public static String a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        String strA = a("mipush|%s|%s", str2, "");
        return str.startsWith(strA) ? a("mipush_%s_%s", str2, str.replace(strA, "")) : str;
    }

    private static void a(Context context) {
        if (f24475a == null) {
            f24475a = context.getApplicationContext();
            NotificationManager notificationManagerA = a();
            Boolean bool = (Boolean) com.xiaomi.push.bk.a((Object) notificationManagerA, "isSystemConditionProviderEnabled", "xmsf_fake_condition_provider_path");
            m739a("fwk is support.init:" + bool);
            boolean zBooleanValue = bool != null ? bool.booleanValue() : false;
            f1021a = zBooleanValue;
            if (zBooleanValue) {
                f1019a = com.xiaomi.push.bk.a((Object) notificationManagerA, "getService", new Object[0]);
            }
        }
    }

    private static NotificationManager a() {
        return (NotificationManager) f24475a.getSystemService("notification");
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m740a() {
        if (com.xiaomi.push.j.m549a() && az.a(f24475a).a(is.NotificationBelongToAppSwitch.a(), true)) {
            return f1021a;
        }
        return false;
    }

    private static int a(String str) {
        if (Build.VERSION.SDK_INT < 24) {
            return -1;
        }
        try {
            return f24475a.getPackageManager().getPackageUid(str, 0);
        } catch (Exception unused) {
            return -1;
        }
    }

    private static Object a(List list) {
        return Class.forName("android.content.pm.ParceledListSlice").getConstructor(List.class).newInstance(list);
    }

    private static <T> T a(Object obj) {
        if (obj != null) {
            try {
                return (T) obj.getClass().getMethod("getList", null).invoke(obj, null);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    private static String a(String str, String str2, String str3) {
        return TextUtils.isEmpty(str) ? "" : String.format(str, str2, str3);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m748a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith(b(""));
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m746a(String str) {
        if (TextUtils.isEmpty(str)) {
            return b();
        }
        return com.xiaomi.push.j.m550a(m744a()) ? b(str) : str;
    }

    @TargetApi(26)
    void a(NotificationChannel notificationChannel) {
        String str = this.f1022a;
        try {
            if (!m740a()) {
                a().createNotificationChannel(notificationChannel);
            } else {
                int iA = a(str);
                if (iA != -1) {
                    com.xiaomi.push.bk.b(f1019a, "createNotificationChannelsForPackage", str, Integer.valueOf(iA), a(Arrays.asList(notificationChannel)));
                }
            }
        } catch (Exception e2) {
            m739a("createNotificationChannel error" + e2);
        }
    }

    @TargetApi(26)
    /* renamed from: a, reason: collision with other method in class */
    public NotificationChannel m743a(String str) {
        NotificationChannel notificationChannel = null;
        try {
            if (!m740a()) {
                notificationChannel = a().getNotificationChannel(str);
            } else {
                List<NotificationChannel> listM747a = m747a();
                if (listM747a != null) {
                    Iterator<NotificationChannel> it = listM747a.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            NotificationChannel notificationChannelA = androidx.core.app.i.a(it.next());
                            if (str.equals(notificationChannelA.getId())) {
                                notificationChannel = notificationChannelA;
                                break;
                            }
                        }
                    }
                }
            }
            break;
        } catch (Exception e2) {
            m739a("getNotificationChannel error" + e2);
        }
        return notificationChannel;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @TargetApi(26)
    /* renamed from: a, reason: collision with other method in class */
    public List<NotificationChannel> m747a() {
        String str;
        String str2 = this.f1022a;
        List<NotificationChannel> notificationChannels = null;
        try {
            if (!m740a()) {
                notificationChannels = a().getNotificationChannels();
                str = "mipush_%s_%s";
            } else {
                int iA = a(str2);
                if (iA != -1) {
                    Object obj = f1019a;
                    Object[] objArr = {str2, Integer.valueOf(iA), Boolean.FALSE};
                    str = "mipush|%s|%s";
                    notificationChannels = (List) a(com.xiaomi.push.bk.a(obj, "getNotificationChannelsForPackage", objArr));
                } else {
                    str = null;
                }
            }
            if (!com.xiaomi.push.j.m549a() || notificationChannels == null) {
                return notificationChannels;
            }
            ArrayList arrayList = new ArrayList();
            String strA = a(str, str2, "");
            Iterator<NotificationChannel> it = notificationChannels.iterator();
            while (it.hasNext()) {
                NotificationChannel notificationChannelA = androidx.core.app.i.a(it.next());
                if (notificationChannelA.getId().startsWith(strA)) {
                    arrayList.add(notificationChannelA);
                }
            }
            return arrayList;
        } catch (Exception e2) {
            m739a("getNotificationChannels error " + e2);
            return notificationChannels;
        }
    }

    void a(NotificationChannel notificationChannel, boolean z2) {
        String str = this.f1022a;
        try {
            if (z2) {
                int iA = a(str);
                if (iA != -1) {
                    com.xiaomi.push.bk.b(f1019a, "updateNotificationChannelForPackage", str, Integer.valueOf(iA), notificationChannel);
                }
            } else {
                a(notificationChannel);
            }
        } catch (Exception e2) {
            m739a("updateNotificationChannel error " + e2);
        }
    }

    public void a(int i2, Notification notification) {
        String str = this.f1022a;
        NotificationManager notificationManagerA = a();
        try {
            int i3 = Build.VERSION.SDK_INT;
            if (m740a()) {
                notification.extras.putString("xmsf_target_package", str);
                if (i3 >= 29) {
                    notificationManagerA.notifyAsPackage(str, null, i2, notification);
                } else {
                    notificationManagerA.notify(i2, notification);
                }
            } else {
                notificationManagerA.notify(i2, notification);
            }
        } catch (Exception unused) {
        }
    }

    public void a(int i2) {
        String str = this.f1022a;
        try {
            if (m740a()) {
                int iA = com.xiaomi.push.i.a();
                String packageName = m744a().getPackageName();
                if (Build.VERSION.SDK_INT >= 30) {
                    com.xiaomi.push.bk.b(f1019a, "cancelNotificationWithTag", str, packageName, null, Integer.valueOf(i2), Integer.valueOf(iA));
                } else {
                    com.xiaomi.push.bk.b(f1019a, "cancelNotificationWithTag", str, null, Integer.valueOf(i2), Integer.valueOf(iA));
                }
                m739a("cancel succ:" + i2);
                return;
            }
            a().cancel(i2);
        } catch (Exception e2) {
            m739a("cancel error" + e2);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private StatusBarNotification[] m742a() {
        if (!com.xiaomi.push.j.m550a(m744a())) {
            return null;
        }
        try {
            Object objA = com.xiaomi.push.bk.a(f1019a, "getActiveNotifications", m744a().getPackageName());
            if (objA instanceof StatusBarNotification[]) {
                return (StatusBarNotification[]) objA;
            }
            return null;
        } catch (Throwable th) {
            m739a("getAllNotifications error " + th);
            return null;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    static void m739a(String str) {
        com.xiaomi.channel.commonutils.logger.b.m91a("NMHelper:" + str);
    }
}
